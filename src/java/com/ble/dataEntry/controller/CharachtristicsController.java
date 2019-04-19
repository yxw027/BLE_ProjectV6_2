/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.CharachtristicsBean;
import com.ble.dataEntry.model.CharachtristicsModel;
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
public class CharachtristicsController extends HttpServlet {

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
        CharachtristicsModel charachtristicsModel = new CharachtristicsModel();
        charachtristicsModel.setDriverClass(ctx.getInitParameter("driverClass"));
        charachtristicsModel.setConnectionString(ctx.getInitParameter("connectionString"));
        charachtristicsModel.setDb_username(ctx.getInitParameter("db_username"));
        charachtristicsModel.setDb_password(ctx.getInitParameter("db_password"));
        charachtristicsModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getServiceName")) {
                    list = charachtristicsModel.getServiceName(q);
                }
                
                else if(JQstring.equals("getServiceUuid")) {
                    String service_name=request.getParameter("action2");
                    list = charachtristicsModel.getServiceUuid(q,service_name);
                }
                
//                else if(JQstring.equals("getModelType")) {
//                    String manufacturer=request.getParameter("action2");
//                    String device_type=request.getParameter("action3");
//                    list = charachtristicsModel.getModelType(q,manufacturer,device_type);
//                }
                
                else if(JQstring.equals("getSearchCharachtersticName")) {
                    
                    list = charachtristicsModel.getSearchCharachtersticName(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                charachtristicsModel.closeConnection();
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
            charachtristicsModel.deleteRecord(Integer.parseInt(request.getParameter("charachtristics_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int charachtristics_id;
            try {
                charachtristics_id = Integer.parseInt(request.getParameter("charachtristics_id"));
            } catch (Exception e) {
                charachtristics_id = 0;
            }
            if (task.equals("Save AS New")) {
                charachtristics_id = 0;
            }
            CharachtristicsBean charachtristicsBean = new CharachtristicsBean();
            charachtristicsBean.setCharachtristics_id(charachtristics_id);
            charachtristicsBean.setCharachtristics_name(request.getParameter("charachtristics_name"));
            charachtristicsBean.setService_name(request.getParameter("service_name"));
            charachtristicsBean.setService_uuid(request.getParameter("service_uuid"));
            
            charachtristicsBean.setUuid(request.getParameter("uuid"));
            
            charachtristicsBean.setRemark(request.getParameter("remark"));

            if (charachtristics_id == 0) {
                System.out.println("Inserting values by model......");
                charachtristicsModel.insertRecord(charachtristicsBean);
            } else {
                System.out.println("Update values by model........");
                charachtristicsModel.reviseRecords(charachtristicsBean);
            }
        }

        String searchDeviceType = "";

        searchDeviceType = request.getParameter("searchCharachtersticName");

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

         noOfRowsInTable = charachtristicsModel.getNoOfRows(searchDeviceType);

         if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
         else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
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
        List<CharachtristicsBean> commandTypeList = charachtristicsModel.showData(lowerLimit, noOfRowsToDisplay,searchDeviceType);
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

        System.out.println("color is :" + charachtristicsModel.getMsgBgColor());
        request.setAttribute("charachtristics_name", request.getParameter("charachtristics_name"));
        request.setAttribute("service_name", request.getParameter("service_name"));
        request.setAttribute("service_uuid", request.getParameter("service_uuid"));
        request.setAttribute("uuid", request.getParameter("uuid"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchDeviceType",searchDeviceType );
        request.setAttribute("message", charachtristicsModel.getMessage());
        request.setAttribute("msgBgColor", charachtristicsModel.getMsgBgColor());
        request.getRequestDispatcher("/charachteristic").forward(request, response);
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
