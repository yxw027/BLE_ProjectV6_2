/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.controller;

import com.ble.device.bean.DeviceMapBean;
import com.ble.device.bean.DeviceRegistrationBean;
import com.ble.device.model.DeviceMapModel;
import com.ble.device.model.DeviceRegistrationModel;
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
 * @author apogee
 */
public class DeviceMapController extends HttpServlet {
   
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
        DeviceMapModel deviceMapModel = new DeviceMapModel();
        deviceMapModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceMapModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceMapModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceMapModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceMapModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getManufactureName")) {
                    list = deviceMapModel.getManufactureName(q);
                }else if(JQstring.equals("getDeviceTypeName")) {
                    list = deviceMapModel.getDeviceTypeName(q,request.getParameter("action2"));
                }else if(JQstring.equals("getDeviceName")) {
                    list = deviceMapModel.getDeviceName(q);
                }else if(JQstring.equals("getDeviceNo")) {
                    list = deviceMapModel.getDeviceNo(q,request.getParameter("action2"));
                }else if(JQstring.equals("getSearchManufactureName")) {
                    list = deviceMapModel.getSearchManufactureName(q);
                }else if(JQstring.equals("getSearchDeviceType")) {
                    list = deviceMapModel.getSearchDeviceType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                deviceMapModel.closeConnection();
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
            deviceMapModel.deleteRecord(Integer.parseInt(request.getParameter("device_map_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int device_map_id;
            try {
                device_map_id = Integer.parseInt(request.getParameter("device_map_id"));
            } catch (Exception e) {
                device_map_id = 0;
            }
            if (task.equals("Save AS New")) {
                device_map_id = 0;
            }
            DeviceMapBean deviceMapBean = new DeviceMapBean();
            deviceMapBean.setDevice_map_id(device_map_id);
            deviceMapBean.setFinished_manufacture_name(request.getParameter("finished_manufacture_name"));
            deviceMapBean.setFinished_device_type(request.getParameter("finished_device_type"));
            deviceMapBean.setFinished_model_name(request.getParameter("finished_model_name"));
            deviceMapBean.setModule_manufacture_name(request.getParameter("module_manufacture_name"));
            deviceMapBean.setModule_device_type(request.getParameter("module_device_type"));
            deviceMapBean.setModule_model_name(request.getParameter("module_model_name"));
            deviceMapBean.setBle_manufacture_name(request.getParameter("ble_manufacture_name"));
            deviceMapBean.setBle_device_type(request.getParameter("ble_device_type"));
            deviceMapBean.setBle_model_name(request.getParameter("ble_model_name"));
            deviceMapBean.setRemark(request.getParameter("remark"));

            if (device_map_id == 0) {
                System.out.println("Inserting values by model......");
                deviceMapModel.insertRecord(deviceMapBean);
            } else {
                System.out.println("Update values by model........");
                deviceMapModel.reviseRecords(deviceMapBean);
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

         noOfRowsInTable = deviceMapModel.getNoOfRows(searchManufacturerName,searchDeviceTypeName);

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
        List<DeviceMapBean> deviceMapList = deviceMapModel.showData(lowerLimit, noOfRowsToDisplay,searchManufacturerName,searchDeviceTypeName);
        lowerLimit = lowerLimit + deviceMapList.size();
        noOfRowsTraversed = deviceMapList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("deviceMapList", deviceMapList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + deviceMapModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchManufacturerName",searchManufacturerName );
        request.setAttribute("searchDeviceTypeName",searchDeviceTypeName );
        request.setAttribute("message", deviceMapModel.getMessage());
        request.setAttribute("msgBgColor", deviceMapModel.getMsgBgColor());
        request.getRequestDispatcher("/device_map").forward(request, response);
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
