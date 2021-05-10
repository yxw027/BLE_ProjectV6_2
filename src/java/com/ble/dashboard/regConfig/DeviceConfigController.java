/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.regConfig;

import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author saini
 */
public class DeviceConfigController extends HttpServlet {

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
            throws ServletException, IOException, InterruptedException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        DeviceConfigModel deviceModel = new DeviceConfigModel();
        deviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceModel.setConnection();
        int device_id = 0;
        String manu_name = "";
        String device_type = "";
        String device_name = "";
        String device_no = "";
        String reg_no = "";

        String task = request.getParameter("task");
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getManufactureName")) {
                    list = deviceModel.getManufactureName(q);
                } else if (JQstring.equals("getDeviceTypeName")) {
                    list = deviceModel.getDeviceTypeName(q, request.getParameter("mf_name"));
                } else if (JQstring.equals("getDeviceName")) {
                    list = deviceModel.getDeviceName(q, request.getParameter("mf_name"), request.getParameter("device_type"));
                } else if (JQstring.equals("getDeviceNo")) {
                    list = deviceModel.getDeviceNo(q, request.getParameter("model_name"));
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
        if (task.equalsIgnoreCase("connection")) {
            manu_name = request.getParameter("manufacturer_name");
            device_type = request.getParameter("device_type");
            device_name = request.getParameter("model_name");
            device_no = request.getParameter("model_no");
            int manufacture_id = deviceModel.getManufactureId(manu_name);
            int device_type_id = deviceModel.getDeviceTypeId(device_type);
            int model_id = deviceModel.getModelId(device_name, device_no);
            device_id = deviceModel.getDeviceId(manufacture_id, device_type_id, model_id);
            reg_no = deviceModel.getDeviceRegNo(device_id);
            //String status = deviceModel.saveDeviceReg(device_type,manu_name,device_name,device_no);
        }

        if (task.equalsIgnoreCase("send")) {
            int j = 0;
            String[] idsToModel = request.getParameterValues("isCheck");
            String[] idsToOperation = request.getParameterValues("isCheckOperation");
            String[] idsToCommand = request.getParameterValues("isCheckCommand");
//            
//            for(int l=0;l<idsToModel.length;l++){
//                System.err.println("----------- model element ------------"+idsToModel[l]);
//            }
//            for(int l=0;l<idsToOperation.length;l++){
//                System.err.println("----------- idsToOperation element ------------"+idsToOperation[l]);
//            }
//            for(int l=0;l<idsToCommand.length;l++){
//                System.err.println("----------- command element ------------"+idsToCommand[l]);
//            }

            device_id = Integer.parseInt(request.getParameter("d_id"));
            System.err.println("devie iddddd -------------" + device_id);
            reg_no = request.getParameter("reg_no1");
            System.err.println("--- reg no ---------" + reg_no);
            //String check = deviceModel.sendToShwetaTesting(idsToModel, idsToOperation, idsToCommand, device_id, reg_no);
            // for Start Packet 
            String check = deviceModel.sendCommandForController(idsToModel, idsToOperation, idsToCommand, device_id, reg_no);
            // end for start packet
            //String check = deviceModel.sendCommandForControllerTesting(idsToModel, idsToOperation, idsToCommand, device_id, reg_no);
            //String check = deviceModel.sendCommandForController(idsToModel, idsToOperation, idsToCommand, device_id, reg_no);
        }

        List<DeviceConfigBean> commandTypeList = deviceModel.showData(device_id);

        System.out.println("color is :" + deviceModel.getMsgBgColor());
        request.setAttribute("manufacturer", manu_name);
        request.setAttribute("device_type", device_type);
        request.setAttribute("deviceName", device_name);
        request.setAttribute("device_no", device_no);
        request.setAttribute("device_id", device_id);
        request.setAttribute("reg_no1", reg_no);
        request.setAttribute("divisionTypeList", commandTypeList);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", deviceModel.getMessage());
        request.setAttribute("msgBgColor", deviceModel.getMsgBgColor());
        //request.getRequestDispatcher("/device_configuration").forward(request, response);
        request.getRequestDispatcher("/collapse_expand").forward(request, response);

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
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(DeviceConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(DeviceConfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
