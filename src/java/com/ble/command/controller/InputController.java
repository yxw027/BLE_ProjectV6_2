/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.InputBean;
import com.ble.command.bean.SelectionBean;
import com.ble.command.model.InputModel;
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
public class InputController extends HttpServlet {

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
        System.out.println("this is input Controller....");
        ServletContext ctx = getServletContext();
        InputModel inputModel = new InputModel();
        inputModel.setDriverClass(ctx.getInitParameter("driverClass"));
        inputModel.setConnectionString(ctx.getInitParameter("connectionString"));
        inputModel.setDb_username(ctx.getInitParameter("db_username"));
        inputModel.setDb_password(ctx.getInitParameter("db_password"));
        inputModel.setConnection();
        
        String task = request.getParameter("task");
        
        if (task == null) {
            task = "";
        }
        String input_no = request.getParameter("input_no");
        String command_id = request.getParameter("command_id");
        String command123 = request.getParameter("command");
        
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        

        String searchCommandName = "";

        searchCommandName = request.getParameter("searchCommandName");
        
        System.out.println("searching.......... " + searchCommandName);

         noOfRowsInTable = inputModel.getNoOfRows(searchCommandName);
        
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        
        List<InputBean> inputList = inputModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
        lowerLimit = lowerLimit + inputList.size();
        noOfRowsTraversed = inputList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("inputList", inputList);
        
        if(action1 == null) {
            action1 = "";
        } else if(action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;
            if(action1.equals("getCommand")) {
                list = inputModel.getCommandName();
            } if(action1.equals("getParameter")) {
                list = inputModel.getParameter();
            } if(action1.equals("getParameterType")) {
                list = inputModel.getParameterType();
            }
            Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                inputModel.closeConnection();
                return;
        }
        
        if (task.equals("Save") || task.equals("Save AS New")) {
            int input_id;
            try {
                input_id = Integer.parseInt(request.getParameter("input_id"));
            } catch (Exception e) {
                input_id = 0;
            }
            if (task.equals("Save AS New")) {
                input_id = 0;
            }
            
            if (input_no != null && command123 != null) {
                int input = Integer.parseInt(input_no);
                for (int i = 1; i <= input; i++) {
                    InputBean bean = new InputBean();
                    bean.setInput_id(input_id);
                    bean.setCommand_name(request.getParameter("command_name"+i));
                    bean.setParameter(request.getParameter("parameter"+i));
                    bean.setParameter_type(request.getParameter("parameter_type"+i));
                    bean.setRemark(request.getParameter("remark"+i));
                    if (input_id == 0) {
                        System.out.println("Inserting values by model......");
                        inputModel.insertRecord(bean);
                    } else {
                        System.out.println("Update values by model........");
                        inputModel.reviseRecords(bean);
                    }
                }
            } else {
                InputBean bean = new InputBean();
                bean.setInput_id(input_id);
                bean.setCommand_name(request.getParameter("command_name"));
                bean.setParameter(request.getParameter("parameter"));
                bean.setParameter_type(request.getParameter("parameter_type"));
                bean.setRemark(request.getParameter("remark"));
                if (input_id == 0) {
                    System.out.println("Inserting values by model......");
                    inputModel.insertRecord(bean);
                } else {
                    System.out.println("Update values by model........");
                    inputModel.reviseRecords(bean);
                }
            }
            
        }
        
        
        
        
        

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
            searchCommandName="";

        }
           // Logic to show data in the table.
        
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + inputModel.getMsgBgColor());
        
        
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", inputModel.getMessage());
        request.setAttribute("msgBgColor", inputModel.getMsgBgColor());
        if (input_no != null) {
            request.setAttribute("input_no", input_no);
            String command = inputModel.getCommandNameByCommand_id(Integer.parseInt(command_id));
            int length = command.length();
            request.setAttribute("command", command);
            request.setAttribute("command_id", command_id);
            List<InputBean> inputListById = inputModel.showDataByCommandId(lowerLimit, noOfRowsToDisplay, input_no, command_id);
            request.setAttribute("inputListById", inputListById);
            request.getRequestDispatcher("/input_command").forward(request, response);
        } else {
            request.getRequestDispatcher("/input").forward(request, response);
        }
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
