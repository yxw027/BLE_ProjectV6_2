/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.ParameterBean;
import com.ble.command.model.ParameterModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Parameter;
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
public class ParameterController extends HttpServlet {

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
        System.out.println("this is rule Controller....");
        ServletContext ctx = getServletContext();
        ParameterModel parameterModel = new ParameterModel();
        parameterModel.setDriverClass(ctx.getInitParameter("driverClass"));
        parameterModel.setConnectionString(ctx.getInitParameter("connectionString"));
        parameterModel.setDb_username(ctx.getInitParameter("db_username"));
        parameterModel.setDb_password(ctx.getInitParameter("db_password"));
        parameterModel.setConnection();

        //HttpSession session = request.getSession();
        String task = request.getParameter("task");
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if(JQstring.equals("getParameterName")) {
                    list = parameterModel.getParameterName(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                parameterModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --RuleController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
//       
        if (task.equals("Save") || task.equals("Save AS New")) {
            int parameter_id;
            try {
                parameter_id = Integer.parseInt(request.getParameter("parameter_id"));
            } catch (Exception e) {
                parameter_id = 0;
            }
            if (task.equals("Save AS New")) {
                parameter_id = 0;
            }
            ParameterBean parameterBean = new ParameterBean();
            parameterBean.setParameter_id(parameter_id);
            parameterBean.setParameter_name(request.getParameter("parameter_name"));
            parameterBean.setParameter_type(request.getParameter("parameter_type"));
            parameterBean.setRemark(request.getParameter("remark"));

            if (parameter_id == 0) {
                System.out.println("Inserting values by model......");
                parameterModel.insertRecord(parameterBean);
            } else {
                System.out.println("Update values by model........");
                parameterModel.reviseRecords(parameterBean);
            }
        }
        
        if (task.equals("Cancel")) {
            int parameter_id = Integer.parseInt(request.getParameter("parameter_id"));
            parameterModel.deleteRecord(parameter_id);
        }

        String searchCommandName = "";

        searchCommandName = request.getParameter("searchParameterName");

        try {
            if (searchCommandName == null) {
                searchCommandName = "";
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
        System.out.println("searching.......... " + searchCommandName);

        noOfRowsInTable = parameterModel.getNoOfRows(searchCommandName);

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
            searchCommandName = "";

        }
        // Logic to show data in the table.
        List<ParameterBean> parameterList = parameterModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName);
        lowerLimit = lowerLimit + parameterList.size();
        noOfRowsTraversed = parameterList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("parameterList", parameterList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + parameterModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        //request.setAttribute("operationName", request.getParameter("operation_name"));
        //request.setAttribute("commandName", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("message", parameterModel.getMessage());
        request.setAttribute("msgBgColor", parameterModel.getMsgBgColor());
        request.getRequestDispatcher("/parameter").forward(request, response);
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
