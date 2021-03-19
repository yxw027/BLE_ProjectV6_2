/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard;

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
import org.json.JSONObject;

/**
 *
 * @author saini
 */
public class DashboardController extends HttpServlet {

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
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 8, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        DashboardModel dashModel = new DashboardModel();
        dashModel.setDriverClass(ctx.getInitParameter("driverClass"));
        dashModel.setConnectionString(ctx.getInitParameter("connectionString"));
        dashModel.setDb_username(ctx.getInitParameter("db_username"));
        dashModel.setDb_password(ctx.getInitParameter("db_password"));
        dashModel.setConnection();

        String task = request.getParameter("task");

        System.out.println("before try -" + task);
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<DashboardBean> list = null;

                if (JQstring.equals("getSubModule")) {
                    list = dashModel.getSubModule(q);
                }

                if (JQstring.equals("getCommandDetail")) {
                    list = dashModel.getCommandDetail(q);
                }
                if (JQstring.equals("getOperationName")) {
                    String device_id = request.getParameter("device_id");
                    String command_id = request.getParameter("command_id");
                    list = dashModel.getOperationName(device_id, command_id);
                }

                if (JQstring.equals("getModelType")) {
                    list = dashModel.getModelType(q);
                }

                if (JQstring.equals("getDeviceName")) {
                    list = dashModel.getDeviceName(q);
                }

                if (JQstring.equals("getFinishedProduct")) {
                    list = dashModel.getFinishedProduct(q);
                }

                if (JQstring.equals("getDeviceImage")) {
                    list = dashModel.getDeviceImage(q);
                }

                if (JQstring.equals("getModulesImage")) {
                    list = dashModel.getModulesImage(q);
                }
                if (JQstring.equals("getModulesList")) {
                    list = dashModel.getModulesList(q);
                }
                if (JQstring.equals("getOperationList")) {
                    list = dashModel.getOperationList(q);
                }
                if (JQstring.equals("getCommandList")) {
                    list = dashModel.getCommandList(q);
                } 
                 if (JQstring.equals("getDevOpCommandList")) {
                    list = dashModel.getDevOpCommandList(q);
                }
                  if (JQstring.equals("getParameterList")) {
                    list = dashModel.getParameterList(q);
                }
                
                
                        

                JSONObject gson = new JSONObject();
                //List<String> lst = null;
                //lst.add("hello");
                //gson.put("list", lst);
                gson.put("data", list);
                //System.out.println("gson -" + gson);
                out.println(gson);
                //dashModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --DashboardController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }

        String searchManufacturerName = "";
        String searchDeviceTypeName = "";

        searchManufacturerName = request.getParameter("searchManufactureName");
        searchDeviceTypeName = request.getParameter("searchDeviceType");
        try {
            if (searchManufacturerName == null || searchDeviceTypeName == null) {
                searchManufacturerName = "";
                searchDeviceTypeName = "";
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

        noOfRowsInTable = dashModel.getNoOfRows(searchManufacturerName, searchDeviceTypeName);

        if (buttonAction.equals("Next")) {
            searchManufacturerName = request.getParameter("manname");
            searchDeviceTypeName = request.getParameter("dname");
            noOfRowsInTable = dashModel.getNoOfRows(searchManufacturerName, searchDeviceTypeName);

        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            searchManufacturerName = request.getParameter("manname");
            searchDeviceTypeName = request.getParameter("dname");
            noOfRowsInTable = dashModel.getNoOfRows(searchManufacturerName, searchDeviceTypeName);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            searchManufacturerName = request.getParameter("manname");
            searchDeviceTypeName = request.getParameter("dname");

            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            searchManufacturerName = request.getParameter("manname");
            searchDeviceTypeName = request.getParameter("dname");
            noOfRowsInTable = dashModel.getNoOfRows(searchManufacturerName, searchDeviceTypeName);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        // Logic to show data in the table.
        List<DashboardBean> dashList = dashModel.showData(lowerLimit, noOfRowsToDisplay, searchManufacturerName, searchDeviceTypeName);
        //System.out.println("dash listt --"+dashList.size());
        lowerLimit = lowerLimit + dashList.size();
        noOfRowsTraversed = dashList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("dashList", dashList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", dashModel.getMessage());
        request.setAttribute("msgBgColor", dashModel.getMsgBgColor());
        request.getRequestDispatcher("/dashboardBle").forward(request, response);

        if (task.equals("showSubModulesImage")) {
            String JQstring = request.getParameter("action2");
            List<DashboardBean> subModulesList = dashModel.showSubModulesImage(JQstring);
            request.setAttribute("subModulesList", subModulesList);
            request.getRequestDispatcher("/subModules").forward(request, response);
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
