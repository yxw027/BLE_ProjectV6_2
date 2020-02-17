/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.ServiceBean;
import com.ble.dataEntry.model.ServiceModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class ServiceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        serviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        serviceModel.setDb_username(ctx.getInitParameter("db_username"));
        serviceModel.setDb_password(ctx.getInitParameter("db_password"));
        serviceModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getManufacturer")) {
                    list = serviceModel.getManufacturer(q);
                }
                
                else if(JQstring.equals("getDeviceType")) {
                    String manufacturer=request.getParameter("action2");
                    list = serviceModel.getDeviceType(q,manufacturer);
                }
                
                else if(JQstring.equals("getModelType")) {
                    String manufacturer=request.getParameter("action2");
                    String device_type=request.getParameter("action3");
                    list = serviceModel.getModelType(q,manufacturer,device_type);
                }
                
                else if(JQstring.equals("getSearchServiceName")) {
                    
                    list = serviceModel.getSearchServiceName(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                serviceModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
//          if(task.equals("generateMapReport")){
//                String searchDivisionType="";
//                List listAll = null;
//                String jrxmlFilePath;
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                listAll=divisionModel.showAllData(searchDivisionType);
//                jrxmlFilePath = ctx.getRealPath("/report/division_m.jrxml");
//                byte[] reportInbytes = divisionModel.generateMapReport(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            }
         if (task.equals("Cancel")) {
            serviceModel.deleteRecord(Integer.parseInt(request.getParameter("service_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int service_id;
            try {
                service_id = Integer.parseInt(request.getParameter("service_id"));
            } catch (Exception e) {
                service_id = 0;
            }
            if (task.equals("Save AS New")) {
                service_id = 0;
            }
            ServiceBean serviceBean = new ServiceBean();
            serviceBean.setService_id(service_id);
            serviceBean.setService_name(request.getParameter("service_name"));
            serviceBean.setService_uuid(request.getParameter("service_uuid"));
            
            serviceBean.setManufacturer(request.getParameter("manufacturer"));
            serviceBean.setDevice_type(request.getParameter("device_type"));
            serviceBean.setModel(request.getParameter("model"));
            serviceBean.setRemark(request.getParameter("remark"));

            if (service_id == 0) {
                System.out.println("Inserting values by model......");
                serviceModel.insertRecord(serviceBean);
            } else {
                System.out.println("Update values by model........");
                serviceModel.reviseRecords(serviceBean);
            }
        }

        String searchDeviceType = "";

        searchDeviceType = request.getParameter("searchServiceName");

         try {
            if (searchDeviceType == null) {
                searchDeviceType="";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }

         try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
         String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        System.out.println("searching.......... " + searchDeviceType);

         noOfRowsInTable = serviceModel.getNoOfRows(searchDeviceType);

         if (buttonAction.equals("Next")){
             searchDeviceType=request.getParameter("manname");
                  noOfRowsInTable = serviceModel.getNoOfRows(searchDeviceType);
             
         }  // lowerLimit already has value such that it shows forward records, so do nothing here.
         else if (buttonAction.equals("Previous")) {
               searchDeviceType=request.getParameter("manname");
                  noOfRowsInTable = serviceModel.getNoOfRows(searchDeviceType);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchDeviceType=request.getParameter("manname");
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
              searchDeviceType=request.getParameter("manname");
                  noOfRowsInTable = serviceModel.getNoOfRows(searchDeviceType);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchDeviceType="";

        }
           // Logic to show data in the table.
        List<ServiceBean> commandTypeList = serviceModel.showData(lowerLimit, noOfRowsToDisplay,searchDeviceType);
        lowerLimit = lowerLimit + commandTypeList.size();
        noOfRowsTraversed = commandTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("divisionTypeList", commandTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + serviceModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        request.setAttribute("manname", searchDeviceType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchDeviceType",searchDeviceType );
        request.setAttribute("message", serviceModel.getMessage());
        request.setAttribute("msgBgColor", serviceModel.getMsgBgColor());
        request.getRequestDispatcher("/serviceview").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
