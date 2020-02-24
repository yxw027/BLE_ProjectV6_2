/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.CrcTypeBean;
import com.ble.dataEntry.bean.OperationNameBean;
import com.ble.dataEntry.model.CrcTypeModel;
import com.ble.dataEntry.model.OperationNameModel;
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
 * @author DELL
 */
public class CrcTypeController extends HttpServlet {

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
        CrcTypeModel crcModel = new CrcTypeModel();
        crcModel.setDriverClass(ctx.getInitParameter("driverClass"));
        crcModel.setConnectionString(ctx.getInitParameter("connectionString"));
        crcModel.setDb_username(ctx.getInitParameter("db_username"));
        crcModel.setDb_password(ctx.getInitParameter("db_password"));
        crcModel.setConnection();
        String IsSuperChild="";
//  String IsSuperParent="";
//  String NoChild="";
        String task = request.getParameter("task");
        // IsSuperChild = request.getParameter("IsSuperChild");
//          IsSuperParent = request.getParameter("IsSuperParent");
//          NoChild = request.getParameter("noChild");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getCrcType")) {
                    list = crcModel.getCrcType(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                crcModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
 
         if (task.equals("Cancel")) {
            crcModel.deleteRecord(Integer.parseInt(request.getParameter("crc_type_id")));  // Pretty sure that organisation_type_id will be available.
        } 
//         
//       Insert Record  
//         
         else if (task.equals("Save") || task.equals("Save AS New")) {
            int crc_type_id;
            try {
                crc_type_id = Integer.parseInt(request.getParameter("crc_type_id"));
            } catch (Exception e) {
                crc_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                crc_type_id = 0;
            }
             CrcTypeBean ctypebean = new CrcTypeBean();
             ctypebean.setCrc_type_id(crc_type_id);
             ctypebean.setCrc_type(request.getParameter("crc_type"));
            ctypebean.setRemark(request.getParameter("remark"));
         
            if (crc_type_id == 0) {
                System.out.println("Inserting values by model......");
                crcModel.insertRecord(ctypebean);
            } else {
                System.out.println("Update values by model........");
                crcModel.reviseRecords(ctypebean);
            }
        }

        String searchcrctype = "";

        searchcrctype = request.getParameter("searchcrctype");

         try {
            if (searchcrctype == null) {
                searchcrctype="";
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
        System.out.println("searching.......... " + searchcrctype);

         noOfRowsInTable = crcModel.getNoOfRows(searchcrctype);

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
            searchcrctype="";

        }
           // Logic to show data in the table.
        List<CrcTypeBean> crcTypeList = crcModel.showData(lowerLimit, noOfRowsToDisplay,searchcrctype);
        lowerLimit = lowerLimit + crcTypeList.size();
        noOfRowsTraversed = crcTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("crcTypeList", crcTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + crcModel.getMsgBgColor());
 

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchcrctype",searchcrctype );
        request.setAttribute("message", crcModel.getMessage());
        request.setAttribute("msgBgColor", crcModel.getMsgBgColor());
        request.getRequestDispatcher("/crcjsp").forward(request, response);
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
