/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.SubDivisionSelectionBean;
import com.ble.command.model.SubDivisionSelectionModel;
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
public class SubDivisionSelectionController extends HttpServlet {

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
        SubDivisionSelectionModel subDivisionSelectionModel = new SubDivisionSelectionModel();
        subDivisionSelectionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        subDivisionSelectionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        subDivisionSelectionModel.setDb_username(ctx.getInitParameter("db_username"));
        subDivisionSelectionModel.setDb_password(ctx.getInitParameter("db_password"));
        subDivisionSelectionModel.setConnection();

        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        String sub_division_no = request.getParameter("sub_division_no");
        int sub_byte_division_id = Integer.parseInt(request.getParameter("sub_byte_division_id"));
        String parameter = request.getParameter("parameter_name");

        

        String searchSelectionName = "";

        searchSelectionName = request.getParameter("searchSelectionName");

        System.out.println("searching.......... " + searchSelectionName);

        noOfRowsInTable = subDivisionSelectionModel.getNoOfRows(searchSelectionName);
        
        if (task.equals("Save")) {
            int selection_value_id;
            try {
                selection_value_id = Integer.parseInt(request.getParameter("selection_value_id"));
            } catch (Exception e) {
                selection_value_id = 0;
            }           

            if (sub_division_no != null) {
                int selection = Integer.parseInt(sub_division_no);
                for (int i = 1; i <= selection; i++) {
                    SubDivisionSelectionBean subDivisionSelectionBean = new SubDivisionSelectionBean();
                    subDivisionSelectionBean.setDisplay_value(request.getParameter("display_value" + i));
                    subDivisionSelectionBean.setBit_value(request.getParameter("bit_value" + i));
                    subDivisionSelectionBean.setSub_byte_division_id(sub_byte_division_id);
                    if (selection_value_id == 0) {
                        System.out.println("Inserting values by model......");
                        subDivisionSelectionModel.insertRecord(subDivisionSelectionBean);
                    } else {
                        System.out.println("Update values by model........");
//                        subDivisionSelectionModel.reviseRecords( subDivisionSelectionBean);
                    }

                }
            }
        }

        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }

        

        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchSelectionName = "";

        }
        // Logic to show data in the table.

//        lowerLimit = lowerLimit + selectionList.size();
//        noOfRowsTraversed = selectionList.size();
        // Now set request scoped attributes, and then forward the request to view.
        List<SubDivisionSelectionBean> subDivisionSelectionListById = subDivisionSelectionModel.showData(lowerLimit, noOfRowsToDisplay, searchSelectionName, parameter, sub_byte_division_id);
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

        System.out.println("color is :" + subDivisionSelectionModel.getMsgBgColor());

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", subDivisionSelectionModel.getMessage());
        request.setAttribute("msgBgColor", subDivisionSelectionModel.getMsgBgColor());
        request.setAttribute("parameter_name", parameter);
        request.setAttribute("sub_byte_division_id", sub_byte_division_id);
        request.setAttribute("sub_division_no", sub_division_no);

        request.setAttribute("subDivisionSelectionListById", subDivisionSelectionListById);
        request.getRequestDispatcher("/sub_division_selection").forward(request, response);

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
