/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.NtripRoverBean;
import com.ble.dataEntry.model.NtripRoverModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
//@WebServlet(name = "NtripRoverController", urlPatterns = {"/NtripRoverController"})
public class NtripRoverController extends HttpServlet {

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
        NtripRoverModel ntripRoverModel = new NtripRoverModel();
        ntripRoverModel.setDriverClass(ctx.getInitParameter("driverClass"));
        ntripRoverModel.setConnectionString(ctx.getInitParameter("connectionString"));
        ntripRoverModel.setDb_username(ctx.getInitParameter("db_username"));
        ntripRoverModel.setDb_password(ctx.getInitParameter("db_password"));
        ntripRoverModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getRoverName")) {
                    list = ntripRoverModel.getRoverName(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                ntripRoverModel.closeConnection();
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
        
         if (task.equals("Cancel")) {
            ntripRoverModel.deleteRecord(Integer.parseInt(request.getParameter("ntrip_rover_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int ntrip_rover_id;
            try {
                ntrip_rover_id = Integer.parseInt(request.getParameter("ntrip_rover_id"));
            } catch (Exception e) {
                ntrip_rover_id = 0;
            }
            if (task.equals("Save AS New")) {
                ntrip_rover_id = 0;
            }
            NtripRoverBean ntripRoverBean = new NtripRoverBean();
            ntripRoverBean.setNtrip_rover_id(ntrip_rover_id);
            ntripRoverBean.setRover_name(request.getParameter("rover_name"));
            ntripRoverBean.setRover_password(request.getParameter("rover_password"));
            
            ntripRoverBean.setRemark(request.getParameter("remark"));

            if (ntrip_rover_id == 0) {
                System.out.println("Inserting values by model......");
                int numberOfRows = ntripRoverModel.insertRecord(ntripRoverBean);
                if(numberOfRows > 0){      
                 request.getRequestDispatcher("/ModelCont.do?task=''");
                }
                
            } else {
                System.out.println("Update values by model........");
                ntripRoverModel.reviseRecords(ntripRoverBean);
            }
        }

        String searchroverName = "";

        searchroverName = request.getParameter("searchroverName");

         try {
            if (searchroverName == null) {
                searchroverName="";
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
        System.out.println("searching.......... " + searchroverName);

         noOfRowsInTable = ntripRoverModel.getNoOfRows(searchroverName);

         if (buttonAction.equals("Next"))
{
             searchroverName=request.getParameter("manname");
              noOfRowsInTable = ntripRoverModel.getNoOfRows(searchroverName);
             
         }         
 
         else if (buttonAction.equals("Previous")) {
             searchroverName=request.getParameter("manname");
              noOfRowsInTable = ntripRoverModel.getNoOfRows(searchroverName);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchroverName=request.getParameter("manname");
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchroverName=request.getParameter("manname");
              noOfRowsInTable = ntripRoverModel.getNoOfRows(searchroverName);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchroverName="";

        }
           // Logic to show data in the table.
        List<NtripRoverBean> roverTypeList = ntripRoverModel.showData(lowerLimit, noOfRowsToDisplay,searchroverName);
        lowerLimit = lowerLimit + roverTypeList.size();
        noOfRowsTraversed = roverTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("roverTypeList", roverTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + ntripRoverModel.getMsgBgColor());
        request.setAttribute("rover_name", request.getParameter("rover_name"));
        request.setAttribute("rover_password", request.getParameter("rover_password"));
       
        request.setAttribute("manname", searchroverName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchroverName",searchroverName);
        request.setAttribute("message", ntripRoverModel.getMessage());
        request.setAttribute("msgBgColor", ntripRoverModel.getMsgBgColor());
        request.getRequestDispatcher("/ntriprover").forward(request, response);
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
