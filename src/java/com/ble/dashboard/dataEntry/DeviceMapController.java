/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import com.ble.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

/**
 *
 * @author saini
 */
public class DeviceMapController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private File tmpDir;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 8, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        DeviceMapModel model = new DeviceMapModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        Map<String, String> map = new HashMap<String, String>();                                        
        
        String task = request.getParameter("task");                          
        try {
            String JQstring = request.getParameter("action1");                               
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                List<DeviceMapBean> list2 = null;
                
                if (JQstring.equals("getFinishedManufacturer")) {
                    list = model.getManufacturer(q);
                }
                if (JQstring.equals("getModuleManufacturer")) {
                    list = model.getManufacturer(q);
                }
                if (JQstring.equals("getFinishedDeviceType")) {
                    String r = request.getParameter("str2");
                    list = model.getDeviceType(q,r);
                }
                if (JQstring.equals("getModuleDeviceType")) {
                    String r = request.getParameter("str2");
                    list = model.getDeviceType(q,r);
                }
                if (JQstring.equals("getModuleModelName")) {
                    String r = request.getParameter("str2");
                    list = model.getModelName(q,r);
                }
                if (JQstring.equals("getFinishedModelName")) {
                    String r = request.getParameter("str2");
                    list = model.getModelName(q,r);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --NewDeviceController get JQuery Parameters Part-" + e);
        }               
        
        if (task == null) {
            task = "";
        }
        System.err.println("taskkk --"+task);
        
        if(task.equals("Save")){
            DeviceMapBean deviceMapBean = new DeviceMapBean();            
            deviceMapBean.setFinished_manufacture_name(request.getParameter("finished_manufacturer"));
            deviceMapBean.setFinished_device_type(request.getParameter("finished_device_type"));
            deviceMapBean.setFinished_model_name(request.getParameter("finished_model"));
            deviceMapBean.setModule_manufacture_name(request.getParameter("module_manufacturer"));
            deviceMapBean.setModule_device_type(request.getParameter("module_device_type"));
            deviceMapBean.setModule_model_name(request.getParameter("module_model"));
            deviceMapBean.setRemark(request.getParameter("module_remark"));                                                    
            try {
                model.insertRecord(deviceMapBean);
            } catch (SQLException ex) {
                Logger.getLogger(DeviceTypeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
        
        List<DeviceMapBean> list2 = model.showData();
        System.err.println("list 2 -"+list2.size());
        
        request.setAttribute("list2", list2);
        request.setAttribute("module_table_id", model.getModule_table_id());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMsgBgColor());
        request.getRequestDispatcher("/deviceMap").forward(request, response);
        
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
