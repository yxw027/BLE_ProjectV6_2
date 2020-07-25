/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.controller;


import com.ble.response.bean.Response;
import com.ble.response.model.ResponseModel;
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
public class ResponseController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setDriverClass(ctx.getInitParameter("driverClass"));
        responseModel.setConnectionString(ctx.getInitParameter("connectionString"));
        responseModel.setDb_username(ctx.getInitParameter("db_username"));
        responseModel.setDb_password(ctx.getInitParameter("db_password"));
        responseModel.setConnection();
        
        String task = request.getParameter("task");
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
               
                if (JQstring.equals("getSearchCommand")) {
                    list = responseModel.getSearchCommand(q);
                } else if (JQstring.equals("getSearchResponse")) {
                    list = responseModel.getSearchResponse(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                responseModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
        
        if (task.equals("Cancel")) {
            responseModel.deleteRecord(Integer.parseInt(request.getParameter("response_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int response_id;
            try {
                response_id = Integer.parseInt(request.getParameter("response_id"));
            } catch (Exception e) {
                response_id = 0;
            }
            if (task.equals("Save AS New")) {
                response_id = 0;
            }
            Response responseBean = new Response();

            responseBean.setCommand(request.getParameter("command"));
            responseBean.setResponse(request.getParameter("response"));
            responseBean.setFixed_response(Integer.parseInt(request.getParameter("fixed_response")));
            responseBean.setVariable_response(Integer.parseInt(request.getParameter("variable_response")));
            responseBean.setBitwise_response(Integer.parseInt(request.getParameter("bitwise_response")));
            responseBean.setData_extract_type(request.getParameter("data_extract_type"));
            responseBean.setFormat(request.getParameter("format"));//          
            responseBean.setRemark(request.getParameter("remark"));

         
            if (response_id == 0) {
                System.out.println("Inserting values by model......");
                responseModel.insertRecord(responseBean);
            } else {
                System.out.println("Update values by model........");
                responseModel.reviseRecords(responseBean);
            }
        }
        
        String searchCommandName = "";
        String searchResponse = "";

        searchCommandName = request.getParameter("searchCommandName");
        searchResponse = request.getParameter("searchResponse");
        
        try {
            if (searchCommandName == null | searchResponse == null) {
                searchCommandName = "";
                searchResponse = "";
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
        System.out.println("searching.......... " + searchResponse);
        noOfRowsInTable = responseModel.getNoOfRows(searchResponse);

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
            searchResponse = "";
        }
        // Logic to show data in the table.
        List<Response> responseList = responseModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName, searchResponse);
        lowerLimit = lowerLimit + responseList.size();
        noOfRowsTraversed = responseList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("responseList", responseList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + responseModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        //request.setAttribute("operationName", request.getParameter("operation_name"));
        //request.setAttribute("commandName", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("searchResponse", searchResponse);
        
        request.setAttribute("message", responseModel.getMessage());
        request.setAttribute("msgBgColor", responseModel.getMsgBgColor());
        request.getRequestDispatcher("/response").forward(request, response);
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
