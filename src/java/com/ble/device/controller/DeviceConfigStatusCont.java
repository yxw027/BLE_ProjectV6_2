/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.controller;

import com.ble.device.bean.DeviceConfigStatusBean;
import com.ble.device.model.DeviceConfigStatusModel;
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
 * @author Administrator
 */
public class DeviceConfigStatusCont extends HttpServlet {

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
        DeviceConfigStatusModel deviceConfigStatusModel = new DeviceConfigStatusModel();
        deviceConfigStatusModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceConfigStatusModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceConfigStatusModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceConfigStatusModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceConfigStatusModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getsearchRegistrationNo")) {
                    list = deviceConfigStatusModel.getsearchRegistrationNo(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                deviceConfigStatusModel.closeConnection();
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
//        if (task.equals("Next Page")) {
//            response.sendRedirect("/model");
//        }
        //Object searchbaseName;
        
//         if (task.equals("Cancel")) {
//            ntripBaseModel.deleteRecord(Integer.parseInt(request.getParameter("ntrip_base_id")));  // Pretty sure that organisation_type_id will be available.
//        } else if (task.equals("Save") || task.equals("Save AS New")) {
//            int ntrip_base_id;
//            try {
//                ntrip_base_id = Integer.parseInt(request.getParameter("ntrip_base_id"));
//            } catch (Exception e) {
//                ntrip_base_id = 0;
//            }
//            if (task.equals("Save AS New")) {
//                ntrip_base_id = 0;
//            }
//            NtripBaseBean ntripBaseBean = new NtripBaseBean();
//            ntripBaseBean.setNtrip_base_id(ntrip_base_id);
//            ntripBaseBean.setBase_name(request.getParameter("base_name"));
//            ntripBaseBean.setBase_password(request.getParameter("base_password"));
//            
//            ntripBaseBean.setRemark(request.getParameter("remark"));
//
//            if (ntrip_base_id == 0) {
//                System.out.println("Inserting values by model......");
//                int numberOfRows = ntripBaseModel.insertRecord(ntripBaseBean);
//                if(numberOfRows > 0){      
//                 request.getRequestDispatcher("/ModelCont.do?task=''");
//                }
//                
//            } else {
//                System.out.println("Update values by model........");
//                ntripBaseModel.reviseRecords(ntripBaseBean);
//            }
//        }
//
        String searchdeviceconfigStatusName = "";

        searchdeviceconfigStatusName = request.getParameter("searchRegistrationNo");

         try {
            if (searchdeviceconfigStatusName == null) {
                searchdeviceconfigStatusName="";
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
        System.out.println("searching.......... " + searchdeviceconfigStatusName);

         noOfRowsInTable = deviceConfigStatusModel.getNoOfRows(searchdeviceconfigStatusName);

         if (buttonAction.equals("Next"))
{
             searchdeviceconfigStatusName=request.getParameter("manname");
              noOfRowsInTable = deviceConfigStatusModel.getNoOfRows(searchdeviceconfigStatusName);
             
         }         
 
         else if (buttonAction.equals("Previous")) {
             searchdeviceconfigStatusName=request.getParameter("manname");
              noOfRowsInTable = deviceConfigStatusModel.getNoOfRows(searchdeviceconfigStatusName);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchdeviceconfigStatusName=request.getParameter("manname");
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchdeviceconfigStatusName=request.getParameter("manname");
              noOfRowsInTable = deviceConfigStatusModel.getNoOfRows(searchdeviceconfigStatusName);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
//        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
//            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
//        } else if (task.equals("Show All Records")) {
//            searchbaseName="";
//
//        }
           // Logic to show data in the table.
        List<DeviceConfigStatusBean> configStatusList = deviceConfigStatusModel.showData(lowerLimit, noOfRowsToDisplay,searchdeviceconfigStatusName);
        lowerLimit = lowerLimit + configStatusList.size();
        noOfRowsTraversed = configStatusList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("configStatusList", configStatusList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + deviceConfigStatusModel.getMsgBgColor());
        request.setAttribute("reg_no", request.getParameter("reg_no"));
        request.setAttribute("command", request.getParameter("command"));
       
        request.setAttribute("manname", searchdeviceconfigStatusName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchbaseName",searchdeviceconfigStatusName );
        request.setAttribute("message", deviceConfigStatusModel.getMessage());
        request.setAttribute("msgBgColor", deviceConfigStatusModel.getMsgBgColor());
        request.getRequestDispatcher("/deviceConfiguration_status").forward(request, response);
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
