/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.SelectionBean;
import com.ble.command.model.SelectionModel;
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
public class SelectionController extends HttpServlet {

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
        SelectionModel selectionModel = new SelectionModel();
        selectionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        selectionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        selectionModel.setDb_username(ctx.getInitParameter("db_username"));
        selectionModel.setDb_password(ctx.getInitParameter("db_password"));
        selectionModel.setConnection();
        int selection_id;
        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        
        String selection_no = request.getParameter("selection_no1");
        String command_id = request.getParameter("command_id1");
        String command123 = request.getParameter("command_name");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getCommand")) {
                list = selectionModel.getCommandName();
            }
            if (action1.equals("getParameter")) {
                list = selectionModel.getParameter();
            }
            if (action1.equals("getParameterType")) {
                list = selectionModel.getParameterType();
            }
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            selectionModel.closeConnection();
            return;

        }

//        String searchCommandName = "";
//
//        searchCommandName = request.getParameter("searchCommandName");
//
//        System.out.println("searching.......... " + searchCommandName);
        noOfRowsInTable = selectionModel.getNoOfRows();
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
//        List<SelectionBean> selectionList = selectionModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName);
        if (task.equals("Save") || task.equals("Save AS New")) {

            try {
                selection_id = Integer.parseInt(request.getParameter("selection_id"));
            } catch (Exception e) {
                selection_id = 0;
            }
            if (task.equals("Save AS New")) {
                selection_id = 0;
            }

            if (selection_no != null && command123 != null) {
                int selection = Integer.parseInt(selection_no);
                for (int i = 1; i <= selection; i++) {
                    SelectionBean bean = new SelectionBean();
                    bean.setSelection_id(selection_id);
                    bean.setCommand_name(request.getParameter("command_name" + i));
                    bean.setParameter(request.getParameter("parameter" + i));
                    bean.setParameter_type(request.getParameter("parameter_type" + i));
                    bean.setSelection_value_no(Integer.parseInt(request.getParameter("selection_value_no" + i)));
                    bean.setRemark(request.getParameter("remark" + i));
                    if (selection_id == 0) {
                        System.out.println("Inserting values by model......");
                        selectionModel.insertRecord(bean,Integer.parseInt(command_id));
                    } else {
                        System.out.println("Update values by model........");
                        selectionModel.reviseRecords(bean);
                    }

                }
            } 

        }
        if (task.equals("update")) {
            int count = Integer.parseInt(request.getParameter("count"));
            selection_id = Integer.parseInt(request.getParameter("selection_id" + count));
            int selection_value_no = Integer.parseInt(request.getParameter("selection_value_no" + count));
            selection_no = request.getParameter("selection_no");
            command_id = request.getParameter("command_id");
            String command_name = request.getParameter("command_name" + count);
            String parameter = request.getParameter("parameter" + count);
            String parameter_type = request.getParameter("parameter_type" + count);
            
            SelectionBean bean = new SelectionBean();
            bean.setSelection_id(selection_id);
            bean.setSelection_value_no(selection_value_no);
            bean.setParameter(parameter);
            bean.setParameter_type(parameter_type);
            bean.setCommand_name(command_name);
            bean.setRemark(request.getParameter("remark" + count));

            System.out.println("Update values by model........");
            selectionModel.updateRecords(bean);

        }
            request.setAttribute("selection_no", selection_no);
            String command = selectionModel.getCommandNameByCommand_id(Integer.parseInt(command_id));
            int length = command.length();
            request.setAttribute("command_name", command);
            request.setAttribute("command_id", command_id);
            List<SelectionBean> selectionListById = selectionModel.showDataByCommandId(lowerLimit, noOfRowsToDisplay, selection_no, command_id);
            request.setAttribute("selectionListById", selectionListById);
            request.getRequestDispatcher("/selection_command").forward(request, response);

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
