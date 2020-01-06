/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.controller;

import com.ble.response.bean.FixedResponseValue;
import com.ble.response.model.FixedResponseValueModel;
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
public class FixedResponseValueController extends HttpServlet {

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

        ServletContext ctx = getServletContext();
        FixedResponseValueModel  fixedResponseValueModel = new FixedResponseValueModel();
        fixedResponseValueModel.setDriverClass(ctx.getInitParameter("driverClass"));
        fixedResponseValueModel.setConnectionString(ctx.getInitParameter("connectionString"));
        fixedResponseValueModel.setDb_username(ctx.getInitParameter("db_username"));
        fixedResponseValueModel.setDb_password(ctx.getInitParameter("db_password"));
        fixedResponseValueModel.setConnection();

        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        String fixed_response_value_no = request.getParameter("fixed_response_value_no");
        int fixed_response_id = Integer.parseInt(request.getParameter("fixed_response_id"));
        String parameter = request.getParameter("parameter");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getParameter")) {
                list = fixedResponseValueModel.getParameter();
            }

            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            fixedResponseValueModel.closeConnection();
            return;

        }

        String searchSelectionName = "";

        searchSelectionName = request.getParameter("searchSelectionName");

        System.out.println("searching.......... " + searchSelectionName);

        noOfRowsInTable = fixedResponseValueModel.getNoOfRows(searchSelectionName);
//        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
//        if (buttonAction == null) {
//            buttonAction = "none";
//        }

        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }

//        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
//        else if (buttonAction.equals("Previous")) {
//            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
//            if (temp < 0) {
//                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
//                lowerLimit = 0;
//            } else {
//                lowerLimit = temp;
//            }
//        } else if (buttonAction.equals("First")) {
//            lowerLimit = 0;
//        } else if (buttonAction.equals("Last")) {
//            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
//            if (lowerLimit < 0) {
//                lowerLimit = 0;
//            }
//        }
//        List<SelectionValueBean> selectionValueList = selectionValueModel.showData(lowerLimit, noOfRowsToDisplay, searchSelectionName);
        
        if (task.equals("Save") || task.equals("Save AS New")) {
            int fixed_response_value_id;
            try {
                fixed_response_value_id = Integer.parseInt(request.getParameter("fixed_response_value_id"));
            } catch (Exception e) {
                fixed_response_value_id = 0;
            }
            if (task.equals("Save AS New")) {
                fixed_response_value_id = 0;
            }

            if (fixed_response_value_no != null && parameter != null) {
                int fixed_response_noselection = Integer.parseInt(fixed_response_value_no);
                for (int i = 1; i <= fixed_response_noselection; i++) {
                    FixedResponseValue  fixedResponseValue = new FixedResponseValue();
                    fixedResponseValue.setDisplay_value(request.getParameter("display_value" + i));
                    fixedResponseValue.setSelect_value(request.getParameter("select_value" + i));
                    fixedResponseValue.setParameter(request.getParameter("parameter" + i));
                    fixedResponseValue.setRemark(request.getParameter("remark" + i));
                    fixedResponseValue.setFixed_response_id(fixed_response_id);
                    if (fixed_response_value_id == 0) {
                        System.out.println("Inserting values by model......");
                        fixedResponseValueModel.insertRecord(fixedResponseValue);
                    } else {
                        System.out.println("Update values by model........");
                        fixedResponseValueModel.reviseRecords(fixedResponseValue);
                    }

                }
            } else {
                FixedResponseValue fixedResponseValue = new FixedResponseValue();
                fixedResponseValue.setFixed_response_value_id(fixed_response_value_id);
                fixedResponseValue.setDisplay_value(request.getParameter("display_value"));
                fixedResponseValue.setSelect_value(request.getParameter("select_value"));
                fixedResponseValue.setParameter(request.getParameter("parameter"));
                fixedResponseValue.setRemark(request.getParameter("remark"));
                if (fixed_response_value_id == 0) {
                    System.out.println("Inserting values by model......");
                    fixedResponseValueModel.insertRecord(fixedResponseValue);
                } else {
                    System.out.println("Update values by model........");
                    fixedResponseValueModel.reviseRecords(fixedResponseValue);
                }
            }

        }
        
        List<FixedResponseValue> fixedResponseValueListById = fixedResponseValueModel.showData(lowerLimit, noOfRowsToDisplay, searchSelectionName, parameter, fixed_response_id);

        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchSelectionName = "";

        }
        // Logic to show data in the table.

//        lowerLimit = lowerLimit + selectionList.size();
//        noOfRowsTraversed = selectionList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
//        request.setAttribute("selectionList", selectionList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + fixedResponseValueModel.getMsgBgColor());

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", fixedResponseValueModel.getMessage());
        request.setAttribute("msgBgColor", fixedResponseValueModel.getMsgBgColor());
        request.setAttribute("parameter", parameter);
        request.setAttribute("fixed_response_id", fixed_response_id);
        request.setAttribute("fixed_response_value_no", fixed_response_value_no);
        request.setAttribute("parameter", parameter);
        request.setAttribute("fixed_response_id", fixed_response_id);
        request.setAttribute("fixedResponseValueListById", fixedResponseValueListById);
        request.getRequestDispatcher("/fixed_response_value").forward(request, response);
      
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
