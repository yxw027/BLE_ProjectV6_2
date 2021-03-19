/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.Map;

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
import org.json.JSONObject;

/**
 *
 * @author Shobha
 */
public class DevOpComMapController extends HttpServlet {

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
        DevOpComMapModel commandModel = new DevOpComMapModel();
        commandModel.setDriverClass(ctx.getInitParameter("driverClass"));
        commandModel.setConnectionString(ctx.getInitParameter("connectionString"));
        commandModel.setDb_username(ctx.getInitParameter("db_username"));
        commandModel.setDb_password(ctx.getInitParameter("db_password"));
        commandModel.setConnection();

        HttpSession session = request.getSession();

        String task = request.getParameter("task");        
        try {

            String JQstring = request.getParameter("action1");
            //System.err.println("jq string --"+JQstring);
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getManufacturerName")) {
                    list = commandModel.getManufacturerName(q);
                } else if (JQstring.equals("getDeviceType")) {
                    list = commandModel.getDeviceType(q, request.getParameter("str2"));
                } else if (JQstring.equals("getModelName")) {
                    list = commandModel.getModelName(q, request.getParameter("str2"), request.getParameter("str3"));
                } else if (JQstring.equals("getModelNo")) {
                    list = commandModel.getModelNo(q, request.getParameter("str2"), request.getParameter("str3"), request.getParameter("str4"));
                } else if (JQstring.equals("getOperationName")) {
                    list = commandModel.getOperationName(q);
                } else if (JQstring.equals("getShortName")) {
                    list = commandModel.getShortName(q);
                } else if (JQstring.equals("getCommandName")) {
                    list = commandModel.getCommandName(q, request.getParameter("str2"));
                } else if (JQstring.equals("getSearchOperationName")) {
                    list = commandModel.getSearchOperationName(q);
                } else if (JQstring.equals("getCommand")) {
                    list = commandModel.getCommand(q);

                } else if (JQstring.equals("getCommand1")) {
                    list = commandModel.getCommand1(q, request.getParameter("str2"));

                } else if (JQstring.equals("getManufacturerName")) {
                    list = commandModel.getSearchManufacturerName(q);
                } else if (JQstring.equals("getSearchDeviceType")) {
                    list = commandModel.getSearchDeviceType(q);
                } else if (JQstring.equals("getSearchDeviceName")) {
                    list = commandModel.getSearchDeviceName(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                //System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("generateMapReport")) {

            List listAll = null;
            String jrxmlFilePath;
            DevOpComMapBean dcm = new DevOpComMapBean();
            int device_command_id = Integer.parseInt(request.getParameter("device_command_id"));
            String searchOperationName = request.getParameter("searchoperationname");
            String searchCommandName = request.getParameter("searchCommandname");
            String searchDeviceName = request.getParameter("searchDevicename");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = DevOpComMapModel.showReport(device_command_id, dcm, searchCommandName, searchDeviceName, searchOperationName);
            jrxmlFilePath = ctx.getRealPath("/view/device_commandReport.jrxml");
            byte[] reportInbytes = DevOpComMapModel.generateRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }

        if (task.equals("generatePDF")) {

            List listAll = null;
            String jrxmlFilePath;
            DevOpComMapBean dcm = new DevOpComMapBean();
            String searchOperationName = request.getParameter("searchoperationname");
            String searchCommandName = request.getParameter("searchCommandname");
            String searchDeviceName = request.getParameter("searchDevicename");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = DevOpComMapModel.getDevice();
            jrxmlFilePath = ctx.getRealPath("/view/DeviceCommandMapReport.jrxml");
            byte[] reportInbytes = DevOpComMapModel.generateRecordList(jrxmlFilePath, listAll);
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
            DevOpComMapBean commandBean = new DevOpComMapBean();            
            commandBean.setManufacturer(request.getParameter("manufacturer_name"));
            commandBean.setDevice_type(request.getParameter("device_type"));
            commandBean.setDevice_name(request.getParameter("model_name"));
            commandBean.setDevice_no(request.getParameter("model_no"));            
            commandBean.setCommand_type(request.getParameter("command_format"));            
            commandBean.setCommand(request.getParameter("command_name"));
            commandBean.setOperation_name(request.getParameter("operation_name"));
            commandBean.setOrder_no(request.getParameter("order_no"));
            commandBean.setDelay(request.getParameter("delay"));
            commandBean.setRemark(request.getParameter("remark"));
            commandBean.setShort_name(request.getParameter("short_name"));            
            commandModel.insertRecord1(commandBean);
            
        }

        String searchCommandName = "";
        String searchDeviceName = "";
        String searchManufacturerName = "";
        String searchDeviceTypeName = "";
        String searchOperationName = "";

        searchCommandName = request.getParameter("searchCommandName");
        searchDeviceName = request.getParameter("searchDeviceName");
        searchManufacturerName = request.getParameter("searchManufacturerName");
        searchDeviceTypeName = request.getParameter("searchDeviceType");
        searchOperationName = request.getParameter("searchOperationName");
        try {
            if (searchCommandName == null) {
                searchCommandName = "";
            }
            if (searchDeviceName == null) {
                searchDeviceName = "";
            }
            if (searchManufacturerName == null) {
                searchManufacturerName = "";
            }
            if (searchDeviceTypeName == null) {
                searchDeviceTypeName = "";
            }
            if (searchOperationName == null) {
                searchOperationName = "";
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

        noOfRowsInTable = DevOpComMapModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName, searchDeviceTypeName);

        if (buttonAction.equals("Next")) {
            searchCommandName = request.getParameter("cmdname");
            searchDeviceName = request.getParameter("modname");
            searchOperationName = request.getParameter("opname");
            searchDeviceTypeName = request.getParameter("dtypename");

            noOfRowsInTable = DevOpComMapModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName, searchDeviceTypeName);

        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            searchCommandName = request.getParameter("cmdname");
            searchDeviceName = request.getParameter("modname");
            searchOperationName = request.getParameter("opname");
            searchDeviceTypeName = request.getParameter("dtypename");

            noOfRowsInTable = DevOpComMapModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName, searchDeviceTypeName);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            searchCommandName = request.getParameter("cmdname");
            searchDeviceName = request.getParameter("modname");
            searchOperationName = request.getParameter("opname");
            searchDeviceTypeName = request.getParameter("dtypename");

            //  noOfRowsInTable = DevOpComMapModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName,searchDeviceTypeName); 
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            searchCommandName = request.getParameter("cmdname");
            searchDeviceName = request.getParameter("modname");
            searchOperationName = request.getParameter("opname");
            searchDeviceTypeName = request.getParameter("dtypename");

            noOfRowsInTable = DevOpComMapModel.getNoOfRows(searchCommandName, searchDeviceName, searchOperationName, searchDeviceTypeName);
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
            searchDeviceTypeName = "";
            searchOperationName = "";
        }
        // Logic to show data in the table.
        List<DevOpComMapBean> list2 = DevOpComMapModel.showData1(lowerLimit, noOfRowsToDisplay, searchCommandName, searchDeviceName, searchOperationName, searchDeviceTypeName);

        lowerLimit = lowerLimit + list2.size();
        noOfRowsTraversed = list2.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("list2", list2);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        request.setAttribute("operationName", request.getParameter("operation_name"));
        request.setAttribute("command", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchOperationName", searchOperationName);
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("searchOperationName", searchOperationName);
        request.setAttribute("searchDeviceName", searchDeviceName);

        request.setAttribute("opname", searchOperationName);
        request.setAttribute("cmdname", searchCommandName);
        request.setAttribute("dtypename", searchDeviceTypeName);
        request.setAttribute("modname", searchDeviceName);

        request.setAttribute("searchManufacturerName", searchManufacturerName);
        request.setAttribute("searchDeviceType", searchDeviceTypeName);
        request.setAttribute("message", commandModel.getMessage());
        request.setAttribute("msgBgColor", commandModel.getMsgBgColor());
        request.getRequestDispatcher("/devOpComMap").forward(request, response);
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
