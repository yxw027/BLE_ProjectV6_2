/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.controller;

import com.ble.device.bean.DeviceBean;
import com.ble.device.bean.DeviceRegistrationBean;
import com.ble.device.model.DeviceModel;
import com.ble.device.model.DeviceRegConModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class DeviceRegConController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, InterruptedException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        DeviceRegConModel deviceModel = new DeviceRegConModel();
        deviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceModel.setConnection();
        int device_id=0;
        String manu_name="";
        String device_type="";
        String device_name="";
        String device_no="";
         String reg_no="";

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                
                if(JQstring.equals("getManufactureName")) {
                    list = deviceModel.getManufactureName(q);
                }else if(JQstring.equals("getDeviceTypeName")) {
                    list = deviceModel.getDeviceTypeName(q,request.getParameter("action2"));
                }else if(JQstring.equals("getDeviceName")) {
                    list = deviceModel.getDeviceName(q);
                }else if(JQstring.equals("getDeviceNo")) {
                    list = deviceModel.getDeviceNo(q,request.getParameter("action2"));
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                deviceModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        } 
         if(task.equalsIgnoreCase("connection")){
         manu_name = request.getParameter("manufacturer_name");
          device_type = request.getParameter("device_type_name");
           device_name = request.getParameter("device_name");
           device_no = request.getParameter("device_no");   
          int manufacture_id=deviceModel.getManufactureId(manu_name);
          int device_type_id = deviceModel.getDeviceTypeId(device_type);
        int model_id = deviceModel.getModelId(device_name,device_no);
         device_id = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id); 
         reg_no= deviceModel.getDeviceRegNo(device_id);      
         //String status = deviceModel.saveDeviceReg(device_type,manu_name,device_name,device_no);
         }
         
         if(task.equalsIgnoreCase("send")){
             int j = 0;
             String[] idsToModel = request.getParameterValues("isCheck");
             String[] idsToOperation = request.getParameterValues("isCheckOperation");
             String[] idsToCommand = request.getParameterValues("isCheckCommand");
             device_id=Integer.parseInt(request.getParameter("d_id"));
             reg_no= request.getParameter("reg_no1"); 
            // device_id=31;
            // String check = deviceModel.sendToShweta(idsToModel,idsToOperation,idsToCommand);
             String check = deviceModel.sendToShwetaTesting(idsToModel,idsToOperation,idsToCommand,device_id,reg_no);
             
             
//             String[] idsToModel = request.getParameterValues("isCheck");
//            for (int i = 0; i < idsToModel.length; i++) {
//                int model_id =  Integer.parseInt(idsToModel[i]);
//                String[] idsToOperation = request.getParameterValues("isCheckOperation");
//                
//                for ( j = 0; j < idsToOperation.length; j++) {
//                 int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
//                 if(model_id1 == model_id){
//    	         System.out.println(idsToOperation[j] + "<br>");
//                 }  
//                }
//                                  	        
//               }
         }
         
          List<DeviceRegistrationBean> commandTypeList = deviceModel.showData(device_id);
         

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
        request.getRequestDispatcher("/deviceRegConnPage").forward(request, response);
   
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
            Logger.getLogger(DeviceRegConController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
            Logger.getLogger(DeviceRegConController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
