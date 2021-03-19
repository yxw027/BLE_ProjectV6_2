/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author saini
 */
public class OperationNameController extends HttpServlet {

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
        OperationNameModel operationNameModel = new OperationNameModel();
        operationNameModel.setDriverClass(ctx.getInitParameter("driverClass"));
        operationNameModel.setConnectionString(ctx.getInitParameter("connectionString"));
        operationNameModel.setDb_username(ctx.getInitParameter("db_username"));
        operationNameModel.setDb_password(ctx.getInitParameter("db_password"));
        operationNameModel.setConnection();
        String IsSuperChild = "";
        String task = request.getParameter("task");
        IsSuperChild = request.getParameter("IsSuperChild");

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getParentOperationName")) {
                    list = operationNameModel.getOperationName(q);
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

        if (task.equals("Cancel")) {
            operationNameModel.deleteRecord(Integer.parseInt(request.getParameter("operation_name_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int operation_name_id = 0;
//            try {
//                operation_name_id = Integer.parseInt(request.getParameter("operation_name_id"));
//            } catch (Exception e) {
//                operation_name_id = 0;
//            }
            if (task.equals("Save AS New")) {
                operation_name_id = 0;
            }
            OperationNameBean operationNameBean = new OperationNameBean();
            //operationNameBean.setOperation_name_id(operation_name_id);
            operationNameBean.setOperation_name(request.getParameter("operation_name"));
            operationNameBean.setParent_operation(request.getParameter("parent_operation_name"));
            operationNameBean.setRemark(request.getParameter("remark"));
            operationNameBean.setIs_super_child(request.getParameter("is_super_child"));

            if (operation_name_id == 0) {
                System.out.println("Inserting values by model......");
                //operationNameModel.insertRecord(operationNameBean);
                try {
                    operationNameModel.insertRecord(operationNameBean);
                } catch (SQLException ex) {
                    Logger.getLogger(OperationNameController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("Update values by model........");
                operationNameModel.reviseRecords(operationNameBean);
            }
        }

        String searchOperationName = "";

        searchOperationName = request.getParameter("searchOperationName");

        try {
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


        // Logic to show data in the table.
        List<OperationNameBean> list = operationNameModel.showData(lowerLimit, noOfRowsToDisplay, searchOperationName);
        //System.err.println("listtt -" + list.size());
//        lowerLimit = lowerLimit + commandTypeList.size();
//        noOfRowsTraversed = commandTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("list", list);
//        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
//            request.setAttribute("showFirst", "false");
//            request.setAttribute("showPrevious", "false");
//        }
//        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
//            request.setAttribute("showNext", "false");
//            request.setAttribute("showLast", "false");
//        }

        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        request.setAttribute("manname", searchOperationName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchOperationName", searchOperationName);
        request.setAttribute("message", operationNameModel.getMessage());
        request.setAttribute("msgBgColor", operationNameModel.getMsgBgColor());
        request.getRequestDispatcher("/operation_name").forward(request, response);
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
