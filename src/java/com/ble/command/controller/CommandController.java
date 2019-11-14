/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.CommandBean;
import com.ble.command.model.CommandModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Shobha
 */
public class CommandController extends HttpServlet {

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
        CommandModel commandModel = new CommandModel();
        commandModel.setDriverClass(ctx.getInitParameter("driverClass"));
        commandModel.setConnectionString(ctx.getInitParameter("connectionString"));
        commandModel.setDb_username(ctx.getInitParameter("db_username"));
        commandModel.setDb_password(ctx.getInitParameter("db_password"));
        commandModel.setConnection();

        HttpSession session = request.getSession();

        String task = request.getParameter("task");
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDeviceType")) {
                    list = commandModel.getDeviceType(q);
                } else if (JQstring.equals("getManufacturer")) {
                    list = commandModel.getManufacturer(q);
                } else if (JQstring.equals("getDeviceName")) {
                    list = commandModel.getDeviceName(q);
                } else if (JQstring.equals("getDeviceNo")) {
                    list = commandModel.getDeviceNo(q);
                } else if (JQstring.equals("getOperationName")) {
                    list = commandModel.getOperationName(q);
                } else if (JQstring.equals("getCommandType")) {
                    list = commandModel.getCommandType(q);
                } else if (JQstring.equals("getSearchCommandName")) {
                    list = commandModel.getSearchCommandName(q);
                } else if (JQstring.equals("getManufacturerName")) {
                    list = commandModel.getSearchManufacturerName(q);
                } else if (JQstring.equals("getSearchDeviceType")) {
                    list = commandModel.getSearchDeviceType(q);
                } else if (JQstring.equals("getSearchDeviceName")) {
                    list = commandModel.getSearchDeviceName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                commandModel.closeConnection();
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
        if (task.equals("Cancel")) {
            commandModel.deleteRecord(Integer.parseInt(request.getParameter("command_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int command_id;
            try {
                command_id = Integer.parseInt(request.getParameter("command_id"));
            } catch (Exception e) {
                command_id = 0;
            }
            if (task.equals("Save AS New")) {
                command_id = 0;
            }
            CommandBean commandBean = new CommandBean();
            commandBean.setCommand_id(command_id);

            commandBean.setManufacturer(request.getParameter("manufacturer"));
            commandBean.setDevice_type(request.getParameter("device_type"));
            commandBean.setDevice_name(request.getParameter("device_name"));
            commandBean.setDevice_no(request.getParameter("device_no"));
            commandBean.setFormat(request.getParameter("command_format"));
//            if (request.getParameter("command_format").equals("hex")) {
//                String command = request.getParameter("command").trim();
//                String hex = "";
//                if (!command.isEmpty()) {
//                    byte[] hexaByte = DatatypeConverter.parseHexBinary(command);
//                    String jaya = Arrays.toString(hexaByte);
//                    commandBean.setCommand(jaya);
//                    
//                }
//            } else if (request.getParameter("command_format").equals("string")) {
//                String command = request.getParameter("command");
//                if (!command.isEmpty()) {
//                    command = command + "\r\n";  
//                    byte[] hexaByte = command.getBytes();
//                    
//                    System.out.println(Arrays.toString(hexaByte));
//                    commandBean.setCommand(Arrays.toString(hexaByte));
//                }
//
//            }
            //commandBean.setCommand(request.getParameter("command"));
             commandBean.setCommand(request.getParameter("command"));
            commandBean.setCommand_type(request.getParameter("command_type"));
            commandBean.setOrder_no(request.getParameter("order_no"));
            commandBean.setDelay(request.getParameter("delay"));
            commandBean.setOperation_name(request.getParameter("operation_name"));
            commandBean.setStarting_del(request.getParameter("starting_del"));
            commandBean.setEnd_del(request.getParameter("end_del"));
            commandBean.setInput_no(Integer.parseInt(request.getParameter("input_no")));
            commandBean.setSelection_no(Integer.parseInt(request.getParameter("selection_no")));
            commandBean.setBitwise(Integer.parseInt(request.getParameter("bitwise")));
            commandBean.setRemark(request.getParameter("remark"));

         
            if (command_id == 0) {
                System.out.println("Inserting values by model......");
                commandModel.insertRecord(commandBean);
            } else {
                System.out.println("Update values by model........");
                commandModel.reviseRecords(commandBean);
            }
        }

        String searchCommandName = "";
        String searchDeviceName = "";
        String searchManufacturerName = "";
        String searchDeviceType = "";

        searchCommandName = request.getParameter("searchCommandName");
        searchDeviceName = request.getParameter("searchDeviceName");
        searchManufacturerName = request.getParameter("searchManufacturerName");
        searchDeviceType = request.getParameter("searchDeviceType");
        try {
            if (searchCommandName == null | searchDeviceName == null | searchManufacturerName == null | searchDeviceType == null) {
                searchCommandName = "";
                searchDeviceName = "";
                searchManufacturerName = "";
                searchDeviceType = "";
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
        System.out.println("searching.......... " + searchDeviceName);
        System.out.println("searching.......... " + searchManufacturerName);
        System.out.println("searching.......... " + searchDeviceType);
        noOfRowsInTable = commandModel.getNoOfRows(searchCommandName, searchDeviceName, searchManufacturerName, searchDeviceType);

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
            searchDeviceName = "";
            searchManufacturerName = "";
            searchDeviceType = "";
        }
        // Logic to show data in the table.
        List<CommandBean> commandTypeList = commandModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName, searchDeviceName, searchManufacturerName, searchDeviceType);
        lowerLimit = lowerLimit + commandTypeList.size();
        noOfRowsTraversed = commandTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("divisionTypeList", commandTypeList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + commandModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        //request.setAttribute("operationName", request.getParameter("operation_name"));
        //request.setAttribute("commandName", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("searchDeviceName", searchDeviceName);
        request.setAttribute("searchManufacturerName", searchManufacturerName);
        request.setAttribute("searchDeviceType", searchDeviceType);
        request.setAttribute("message", commandModel.getMessage());
        request.setAttribute("msgBgColor", commandModel.getMsgBgColor());
        request.getRequestDispatcher("/commandView").forward(request, response);
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
