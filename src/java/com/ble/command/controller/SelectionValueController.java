/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.SelectionValueBean;

import com.ble.command.model.SelectionValueModel;
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
public class SelectionValueController extends HttpServlet {

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
        SelectionValueModel selectionValueModel = new SelectionValueModel();
        selectionValueModel.setDriverClass(ctx.getInitParameter("driverClass"));
        selectionValueModel.setConnectionString(ctx.getInitParameter("connectionString"));
        selectionValueModel.setDb_username(ctx.getInitParameter("db_username"));
        selectionValueModel.setDb_password(ctx.getInitParameter("db_password"));
        selectionValueModel.setConnection();

        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        String selection_value_no = request.getParameter("selection_value_no");
        int selection_id = Integer.parseInt(request.getParameter("selection_id"));
        String parameter = request.getParameter("parameter");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getParameter")) {
                list = selectionValueModel.getParameter();
            }

            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            selectionValueModel.closeConnection();
            return;

        }

        String searchSelectionName = "";

        searchSelectionName = request.getParameter("searchSelectionName");

        System.out.println("searching.......... " + searchSelectionName);

        noOfRowsInTable = selectionValueModel.getNoOfRows(searchSelectionName);
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
            int selection_value_id;
            try {
                selection_value_id = Integer.parseInt(request.getParameter("selection_value_id"));
            } catch (Exception e) {
                selection_value_id = 0;
            }
            if (task.equals("Save AS New")) {
                selection_value_id = 0;
            }

            if (selection_value_no != null && parameter != null) {
                int selection = Integer.parseInt(selection_value_no);
                for (int i = 1; i <= selection; i++) {
                    SelectionValueBean selectionValuebean = new SelectionValueBean();
//                    selectionValuebean.setSelection_value_id(selection_value_id);
                    selectionValuebean.setDisplay_value(request.getParameter("display_value" + i));
                    selectionValuebean.setByte_value(request.getParameter("byte_value" + i));
                    selectionValuebean.setParameter(request.getParameter("parameter" + i));
                    selectionValuebean.setRemark(request.getParameter("remark" + i));
                    selectionValuebean.setSelection_id(selection_id);
                    if (selection_value_id == 0) {
                        System.out.println("Inserting values by model......");
                        selectionValueModel.insertRecord(selectionValuebean);
                    } else {
                        System.out.println("Update values by model........");
                        selectionValueModel.reviseRecords(selectionValuebean);
                    }

                }
            } else {
                SelectionValueBean selectionValuebean = new SelectionValueBean();
                selectionValuebean.setSelection_value_id(selection_value_id);
                selectionValuebean.setDisplay_value(request.getParameter("display_value"));
                selectionValuebean.setByte_value(request.getParameter("byte_value"));
                selectionValuebean.setParameter(request.getParameter("parameter"));
                selectionValuebean.setRemark(request.getParameter("remark"));
                if (selection_value_id == 0) {
                    System.out.println("Inserting values by model......");
                    selectionValueModel.insertRecord(selectionValuebean);
                } else {
                    System.out.println("Update values by model........");
                    selectionValueModel.reviseRecords(selectionValuebean);
                }
            }

        }
        
        List<SelectionValueBean> selectionValueList = selectionValueModel.showData(lowerLimit, noOfRowsToDisplay, searchSelectionName, parameter, selection_id);

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

        System.out.println("color is :" + selectionValueModel.getMsgBgColor());

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", selectionValueModel.getMessage());
        request.setAttribute("msgBgColor", selectionValueModel.getMsgBgColor());
        request.setAttribute("parameter", parameter);
        request.setAttribute("selection_id", selection_id);
        request.setAttribute("selection_value_no", selection_value_no);
        request.setAttribute("parameter", parameter);
        request.setAttribute("selection_id", selection_id);
        request.setAttribute("selectionListById", selectionValueList);
        request.getRequestDispatcher("/selection_value").forward(request, response);

//
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
