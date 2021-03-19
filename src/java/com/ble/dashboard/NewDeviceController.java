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
public class NewDeviceController extends HttpServlet {

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
        NewDeviceModel newDeviceModel = new NewDeviceModel();
        newDeviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        newDeviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        newDeviceModel.setDb_username(ctx.getInitParameter("db_username"));
        newDeviceModel.setDb_password(ctx.getInitParameter("db_password"));
        newDeviceModel.setConnection();

        System.out.println("device name -" + request.getParameter("device_name"));
        System.out.println("device id -" + request.getParameter("device_id"));
        System.out.println("levels -" + request.getParameter("levels"));

        if (request.getParameter("submit") != null) {            
            String add_modules[] = request.getParameterValues("modules");
            for (int i = 0; i < add_modules.length; i++) {                
                System.out.println("add  modules -" + add_modules[i]);
            }
        }

        String task = request.getParameter("task");

        //System.out.println("before try");
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<DashboardBean> list = null;

                if (JQstring.equals("getModulesImage")) {
                    list = newDeviceModel.getModulesImage(q);
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
            System.out.println("\n Error --NewDeviceController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
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

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", newDeviceModel.getMessage());
        request.setAttribute("msgBgColor", newDeviceModel.getMsgBgColor());
        request.getRequestDispatcher("/AddDevices").forward(request, response);

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
