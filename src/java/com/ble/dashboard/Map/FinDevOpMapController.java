/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.Map;

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
public class FinDevOpMapController extends HttpServlet {

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
        ServletContext ctx = getServletContext();
        FinDevOpMapModel model = new FinDevOpMapModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        Map<String, String> map = new HashMap<String, String>();
        String manu_name = "", device_type = "", device_name = "", device_no = "", reg_no = "";
        int device_id = 0;

        String task = request.getParameter("task");
        try {
            String JQstring = request.getParameter("action1");
            System.err.println("---- jq sring ---" + JQstring);
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getManufactureName")) {
                    list = model.getManufacturerName(q);
                } else if (JQstring.equals("getDeviceTypeName")) {
                    list = model.getDeviceTypeName(q, request.getParameter("mf_name"));
                } else if (JQstring.equals("getDeviceName")) {
                    list = model.getDeviceName(q, request.getParameter("mf_name"), request.getParameter("device_type"));
                } else if (JQstring.equals("getDeviceNo")) {
                    list = model.getDeviceNo(q, request.getParameter("model_name"));
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

        if (task.equalsIgnoreCase("submit")) {
            manu_name = request.getParameter("manufacturer_name");
            device_type = request.getParameter("device_type");
            device_name = request.getParameter("model_name");
            device_no = request.getParameter("model_no");
            int manufacture_id = model.getManufactureId(manu_name);
            int device_type_id = model.getDeviceTypeId(device_type);
            int model_id = model.getModelId(device_name, device_no);
            device_id = model.getDeviceId(manufacture_id, device_type_id, model_id);
            //reg_no = model.getDeviceRegNo(device_id);
            //String status = deviceModel.saveDeviceReg(device_type,manu_name,device_name,device_no);
        }

        if (task.equals("Save")) {
            FinDevOpMapBean bean = new FinDevOpMapBean();
            String[] idsToModel = null;
            String[] idsToOperation = null;
            List<Integer> modelCheckList=new ArrayList<Integer>();
            List<Integer> operationCheckList=new ArrayList<Integer>();
            List<Integer> operationCountList=new ArrayList<Integer>();
            
            String model_name=request.getParameter("model_name");
            int model_count=Integer.parseInt(request.getParameter("model_count"));
            System.err.println("---- models checkbox idd ----"+model_count);            
                System.err.println("--- model id not null ---"+idsToModel);
                for(int i=0;i<model_count;i++){
                    idsToModel=request.getParameterValues("model_"+i);
                    if(idsToModel !=null){
                        modelCheckList.add(Integer.parseInt(idsToModel[0]));
                        int operation_count=Integer.parseInt(request.getParameter("operation_count_"+i));
                        operationCountList.add(operation_count);
                        System.err.println("--- operation count ----"+operation_count);
                        for(int j=0;j<operation_count;j++){
                            idsToOperation=request.getParameterValues("operation_"+ i + j);
                            if(idsToOperation !=null){
                                operationCheckList.add(Integer.parseInt(idsToOperation[0]));
                            }else{
                                //operationCheckList.add(0);
                            }
                        }
                    }else{
                        //modelCheckList.add(0);
                    }
                }
                
                System.err.println("---- model check list size  ----"+modelCheckList.size());
                System.err.println("---- model check list element  ----"+modelCheckList.toString());
                System.err.println("---- operation check list size  ----"+operationCheckList.size());
                System.err.println("---- operation check list element  ----"+operationCheckList.toString());
                System.err.println("---- operation count list size  ----"+operationCountList.size());
                System.err.println("---- operation count list element  ----"+operationCountList.toString());
                System.err.println("---- total model count ----"+model_count);
                
                model.saveRecord(model_name,model_count,operationCountList,modelCheckList,operationCheckList);
            
            
            //String[] idsToCommand = request.getParameterValues("isCheckCommand");
//            bean.setChild_type(request.getParameter("device_type"));
//            bean.setParent_type(request.getParameter("parent_device_type"));
//            bean.setIs_super_child(request.getParameter("is_super_child"));
//            bean.setRemark(request.getParameter("remark"));

//            try {
//                model.insertRecord(bean);
//            } catch (SQLException ex) {
//                Logger.getLogger(FinDevOpMapController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        List<FinDevOpMapBean> commandTypeList = model.showData(device_id);
        request.setAttribute("manufacturer", manu_name);
        request.setAttribute("device_type", device_type);
        request.setAttribute("deviceName", device_name);
        request.setAttribute("device_no", device_no);
        request.setAttribute("device_id", device_id);
        request.setAttribute("reg_no1", reg_no);
        request.setAttribute("model_count", model.model_count);
        request.setAttribute("divisionTypeList", commandTypeList);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMsgBgColor());
        request.getRequestDispatcher("/finished_device_operation_map").forward(request, response);

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
