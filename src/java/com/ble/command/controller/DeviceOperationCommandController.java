/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.DeviceOperationCommand;
import com.ble.command.model.DeviceOperationCommandModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class DeviceOperationCommandController extends HttpServlet {

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
        DeviceOperationCommandModel commandModel = new DeviceOperationCommandModel();
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
                  if (JQstring.equals("getManufacturer")) {
                    list = commandModel.getManufacturer(q);
                  } 
            else  if (JQstring.equals("getDeviceType")) {
                    list = commandModel.getDeviceTypeName(q,request.getParameter("action2"));
                }
                 //String DeviceName = request.getParameter("DeviceName");
                else if (JQstring.equals("getDeviceName")) {
                    list = commandModel.getDeviceName(q,request.getParameter("action2"),request.getParameter("action3"));
                } else if (JQstring.equals("getDeviceNo")) {
                  list = commandModel.getDeviceNo(q,request.getParameter("action2"),request.getParameter("action3"),request.getParameter("action4"));
                } else if (JQstring.equals("getOperationName")) {
                    list = commandModel.getOperationName(q);
                } else if (JQstring.equals("getCommandType")) {
                    list = commandModel.getCommandType(q);
                } else if (JQstring.equals("getSearchCommandName")) {
                    list = commandModel.getSearchCommandName(q);
                } 
                     else if (JQstring.equals("getSearchOperationName")) {
                    list = commandModel.getSearchOperationName(q);
                     } 
                     else if (JQstring.equals("getCommand")) {
                    list = commandModel.getCommand(q);
                          
                    
                    
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
          if(task.equals("generateMapReport")){
               
                List listAll = null;
                String jrxmlFilePath;
                 DeviceOperationCommand dcm = new DeviceOperationCommand();
                 int device_command_id =Integer.parseInt(request.getParameter("device_command_id"));
                   String searchOperationName=request.getParameter("searchoperationname");
               String searchCommandName=request.getParameter("searchCommandname");
               String searchDeviceName=request.getParameter("searchDevicename");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=DeviceOperationCommandModel.showReport(device_command_id,dcm,searchCommandName,searchDeviceName,searchOperationName);
                jrxmlFilePath = ctx.getRealPath("/view/device_commandReport.jrxml");
                byte[] reportInbytes = DeviceOperationCommandModel.generateRecordList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
          
            if(task.equals("generatePDF")){
               
                List listAll = null;
                String jrxmlFilePath;
                 DeviceOperationCommand dcm = new DeviceOperationCommand();
//                 int device_command_id =Integer.parseInt(request.getParameter("device_command_id"));
              String searchOperationName=request.getParameter("searchoperationname");
               String searchCommandName=request.getParameter("searchCommandname");
               String searchDeviceName=request.getParameter("searchDevicename");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                    listAll=DeviceOperationCommandModel.showPDF(dcm,searchCommandName,searchDeviceName,searchOperationName);
             //  listAll=DeviceOperationCommandModel.showPDF(dcm,searchOperationName,searchCommandName,searchDeviceName);
                jrxmlFilePath = ctx.getRealPath("/view/device_commandReport.jrxml");
                //jrxmlFilePath = ctx.getRealPath("/view/TestReport.jrxml");
                byte[] reportInbytes = DeviceOperationCommandModel.generateRecordList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
           
          
          
          
          
        if (task.equals("Cancel")) {
            commandModel.deleteRecord(Integer.parseInt(request.getParameter("device_command_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int device_command_id;
            try {
               device_command_id =Integer.parseInt(request.getParameter("device_command_id"));
            } catch (Exception e) {
                device_command_id = 0;
            }
            if (task.equals("Save AS New")) {
                device_command_id = 0;
            }
            DeviceOperationCommand commandBean = new DeviceOperationCommand();
             commandBean.setDevice_command_id(device_command_id);
             commandBean.setManufacturer(request.getParameter("manufacturer"));
             commandBean.setDevice_type(request.getParameter("device_type"));
             commandBean.setDevice_name(request.getParameter("device_name"));
             commandBean.setDevice_no(request.getParameter("device_no"));
          
//             commandBean.setCommand_id(Integer.parseInt(request.getParameter("command_id")));
//             commandBean.setOperation_id(Integer.parseInt(request.getParameter("operation_id")));
//             commandBean.setDevice_id(Integer.parseInt(request.getParameter("device_id")));
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
            commandBean.setCommand(request.getParameter("command"));
//             commandBean.setCommand(request.getParameter("command"));
           // commandBean.setCommand_type(request.getParameter("command_type"));
//            commandBean.setOrder_no(request.getParameter("order_no"));
//            commandBean.setDelay(request.getParameter("delay"));
           commandBean.setOperation_name(request.getParameter("operation_name"));
//            commandBean.setStarting_del(request.getParameter("starting_del"));
//            commandBean.setEnd_del(request.getParameter("end_del"));
//            commandBean.setInput_no(Integer.parseInt(request.getParameter("input_no")));
//            commandBean.setSelection_no(Integer.parseInt(request.getParameter("selection_no")));
            commandBean.setRemark(request.getParameter("remark"));

            //session.setAttribute("deviceName", request.getParameter("device_name"));
            //session.setAttribute("operationName", request.getParameter("operation_name"));
            if (device_command_id == 0) {
                System.out.println("Inserting values by model......");
                commandModel.insertRecord1(commandBean);
            } else {
                System.out.println("Update values by model........");
                commandModel.reviseRecords(commandBean);
            }
        }

        String searchCommandName = "";
        String searchDeviceName = "";
        String searchManufacturerName = "";
        String searchDeviceType = "";
        String searchOperationName="";

        searchCommandName = request.getParameter("searchCommandName");
        searchDeviceName = request.getParameter("searchDeviceName");
        searchManufacturerName = request.getParameter("searchManufacturerName");
        searchDeviceType = request.getParameter("searchDeviceType");
        searchOperationName = request.getParameter("searchOperationName");
        try {
            if (searchCommandName == null)
            {
            searchCommandName = "";
            }
             if (searchDeviceName == null)
             {
              searchDeviceName = "";
             }
              if( searchManufacturerName == null) 
              {
                searchManufacturerName = "";
              }    
              if(searchDeviceType == null)
              {
               searchDeviceType = "";
              }
              if (searchOperationName== null ) {
                 searchOperationName="";
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
        System.out.println("searching.......... " + searchOperationName);
        noOfRowsInTable = DeviceOperationCommandModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName);

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
            searchOperationName="";
        }
        // Logic to show data in the table.
        List<DeviceOperationCommand> commandTypeList = DeviceOperationCommandModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName, searchDeviceName, searchOperationName);
     
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
        request.setAttribute("operationName", request.getParameter("operation_name"));
        request.setAttribute("command", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("searchOperationName", searchOperationName);
         
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("searchCommandName", searchOperationName);
        request.setAttribute("searchDeviceName", searchDeviceName);
        request.setAttribute("searchManufacturerName", searchManufacturerName);
        request.setAttribute("searchDeviceType", searchDeviceType);
        request.setAttribute("message", commandModel.getMessage());
        request.setAttribute("msgBgColor", commandModel.getMsgBgColor());
        request.getRequestDispatcher("/device_operation_commandView").forward(request, response);
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
