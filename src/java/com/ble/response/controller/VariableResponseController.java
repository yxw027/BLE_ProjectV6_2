/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.controller;

import com.ble.response.bean.VariableResponse;
import com.ble.response.model.VariableResponseModel;
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
public class VariableResponseController extends HttpServlet {

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
        VariableResponseModel variableResponseModel = new VariableResponseModel();
        variableResponseModel.setDriverClass(ctx.getInitParameter("driverClass"));
        variableResponseModel.setConnectionString(ctx.getInitParameter("connectionString"));
        variableResponseModel.setDb_username(ctx.getInitParameter("db_username"));
        variableResponseModel.setDb_password(ctx.getInitParameter("db_password"));
        variableResponseModel.setConnection();
        
        String task = request.getParameter("task");
        
        if (task == null) {
            task = "";
        }
        String variable_response = request.getParameter("variable_response");
        String response_id = request.getParameter("response_id");
        String response123 = request.getParameter("response1");
        
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        

        String searchResponseName = "";

        searchResponseName = request.getParameter("searchResponseName");
        
        System.out.println("searching.......... " + searchResponseName);

         noOfRowsInTable = variableResponseModel.getNoOfRows(searchResponseName);
        
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        
        List<VariableResponse> inputList = variableResponseModel.showData(lowerLimit, noOfRowsToDisplay,searchResponseName);
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
            if(action1.equals("getResponse")) {
                list = variableResponseModel.getResponseName();
            } if(action1.equals("getParameter")) {
                list = variableResponseModel.getParameter();
            } 
            Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
               variableResponseModel.closeConnection();
                return;
        }
        
        if (task.equals("Save") || task.equals("Save AS New")) {
            int variable_response_id;
            try {
                variable_response_id = Integer.parseInt(request.getParameter("variable_response_id"));
            } catch (Exception e) {
                variable_response_id = 0;
            }
            if (task.equals("Save AS New")) {
                variable_response_id = 0;
            }
            
            if (variable_response != null && response123 != null) {
                int input = Integer.parseInt(variable_response);
                for (int i = 1; i <= input; i++) {
                    VariableResponse bean = new VariableResponse();
                   bean.setVariable_response_id(variable_response_id);
                bean.setResponse(request.getParameter("response"+i));
                bean.setParameter(request.getParameter("parameter"+i));
                 bean.setStart_pos(Integer.parseInt(request.getParameter("start_pos"+i)));
                 bean.setNo_of_byte(Integer.parseInt(request.getParameter("no_of_byte"+i)));
                bean.setRemark(request.getParameter("remark"+i));
                    if (variable_response_id == 0) {
                        System.out.println("Inserting values by model......");
                        variableResponseModel.insertRecord(bean);
                    } else {
                        System.out.println("Update values by model........");
                        variableResponseModel.reviseRecords(bean);
                    }
                }
            } else {
                VariableResponse bean = new VariableResponse();
                bean.setVariable_response_id(variable_response_id);
                bean.setResponse(request.getParameter("response"));
                bean.setParameter(request.getParameter("parameter"));
                 bean.setStart_pos(Integer.parseInt(request.getParameter("start_pos")));
                 bean.setNo_of_byte(Integer.parseInt(request.getParameter("no_of_byte")));
                bean.setRemark(request.getParameter("remark"));
                if (variable_response_id == 0) {
                    System.out.println("Inserting values by model......");
                    variableResponseModel.insertRecord(bean);
                } else {
                    System.out.println("Update values by model........");
                   variableResponseModel.reviseRecords(bean);
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
            searchResponseName="";

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

        System.out.println("color is :" + variableResponseModel.getMsgBgColor());
        
        
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", variableResponseModel.getMessage());
        request.setAttribute("msgBgColor", variableResponseModel.getMsgBgColor());
            request.setAttribute("variable_response", variable_response);
            String response1 = variableResponseModel.getResponseNameByResponse_id(Integer.parseInt(response_id));
            int length =response1.length();
            request.setAttribute("response1", response1);
            request.setAttribute("response_id", response_id);
            List<VariableResponse> inputListById = variableResponseModel.showDataByResponseId(lowerLimit, noOfRowsToDisplay, variable_response, response_id);
            request.setAttribute("inputListById", inputListById);
            request.getRequestDispatcher("/variable_response_input").forward(request, response);
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
