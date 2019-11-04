/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.device.controller;

import com.ble.device.bean.DeviceBean;
import com.ble.device.model.DeviceModel;
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
public class DeviceController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getManufactureName")) {
                    list = deviceModel.getManufactureName(q);
                }else if(JQstring.equals("getDeviceTypeName")) {
                    list = deviceModel.getDeviceTypeName(q);
                }else if(JQstring.equals("getDeviceName")) {
                    list = deviceModel.getDeviceName(q);
                }else if(JQstring.equals("getDeviceNo")) {
                    list = deviceModel.getDeviceNo(q,request.getParameter("action2"));
                }else if(JQstring.equals("getSearchManufactureName")) {
                    list = deviceModel.getSearchManufactureName(q);
                }else if(JQstring.equals("getSearchDeviceType")) {
                    list = deviceModel.getSearchDeviceType(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                deviceModel.closeConnection();
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
            deviceModel.deleteRecord(Integer.parseInt(request.getParameter("device_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int device_id;
            try {
                device_id = Integer.parseInt(request.getParameter("device_id"));
            } catch (Exception e) {
                device_id = 0;
            }
            if (task.equals("Save AS New")) {
                device_id = 0;
            }
            DeviceBean deviceBean = new DeviceBean();
            deviceBean.setDevice_id(device_id);
            deviceBean.setManufacture_name(request.getParameter("manufacturer_name"));
            deviceBean.setDevice_type_name(request.getParameter("device_type_name"));
            deviceBean.setDevice_name(request.getParameter("device_name"));
            deviceBean.setDevice_no(Integer.parseInt(request.getParameter("device_no")));

            deviceBean.setRemark(request.getParameter("remark"));

            if (device_id == 0) {
                System.out.println("Inserting values by model......");
                int noOfRowsAffected = deviceModel.insertRecord(deviceBean);
                if(noOfRowsAffected > 0){
                    request.setAttribute("confirmation", "confirmation");
                } else {
                    request.setAttribute("confirmation", "");
                }
                
            } else {
                System.out.println("Update values by model........");
                deviceModel.reviseRecords(deviceBean);
            }
        }

        String searchManufacturerName = "";
        String searchDeviceTypeName = "";

        searchManufacturerName = request.getParameter("searchManufactureName");
        searchDeviceTypeName = request.getParameter("searchDeviceType");
         try {
            if (searchManufacturerName == null || searchDeviceTypeName == null) {
                searchManufacturerName="";
                searchDeviceTypeName="";
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
        System.out.println("searching.......... " + searchManufacturerName);
        System.out.println("searching.......... " + searchDeviceTypeName);

         noOfRowsInTable = deviceModel.getNoOfRows(searchManufacturerName,searchDeviceTypeName);

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
            searchManufacturerName="";
            searchDeviceTypeName="";

        }
           // Logic to show data in the table.
        List<DeviceBean> commandTypeList = deviceModel.showData(lowerLimit, noOfRowsToDisplay,searchManufacturerName,searchDeviceTypeName);
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

        System.out.println("color is :" + deviceModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchManufacturerName",searchManufacturerName );
        request.setAttribute("searchDeviceTypeName",searchDeviceTypeName );
        request.setAttribute("message", deviceModel.getMessage());
        request.setAttribute("msgBgColor", deviceModel.getMsgBgColor());
        request.getRequestDispatcher("/device").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
