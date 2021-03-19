/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.device;

import com.ble.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class DeviceController extends HttpServlet {

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
        DeviceModel model = new DeviceModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        Map<String, String> map = new HashMap<String, String>();
        String task0="";
        task0 = request.getParameter("task");
        System.err.println("taskkk -"+task0);
        String device_type="",idd="";
        if(task0==null){
            task0="";
        }
        if (task0.equals("Finished")) {
            device_type = task0;
        }else if(task0.equals("OpenSpecific")){
            idd=request.getParameter("idd");        
        }

        List items = null;
        Iterator itr = null;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        List<String> modulesSetMF = new ArrayList<String>();
        List<String> modulesSetDT = new ArrayList<String>();
        List<String> modulesSetMN = new ArrayList<String>();

        try {
            //if (!map.isEmpty()) {
            items = uploadHandler.parseRequest(request);
            itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                    map.put(item.getFieldName(), item.getString("UTF-8"));

                    if (item.getFieldName().equals("manufacturer_name")) {
                        modulesSetMF.add(item.getString());
                    }
                    if (item.getFieldName().equals("device_type")) {
                        modulesSetDT.add(item.getString());
                    }
                    if (item.getFieldName().equals("model_name")) {
                        modulesSetMN.add(item.getString());
                    }
                    
                    
                    
                } else {
                    //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {
                        String image_name = item.getName();
                        image_name = image_name.substring(0, image_name.length());
                        System.out.println(image_name);
                        map.put(item.getFieldName(), item.getName());
                    }
                }
            }
            itr = null;
            itr = items.iterator();
            
            //}
        } catch (Exception ex) {
            System.out.println("Error encountered while uploading file" + ex);
        }     
//        System.err.println("map size -"+map.size());
//        System.err.println("map string -"+map.toString());
//        for(int a=0;a<map.size();a++){
//            System.err.println("map mf name -"+map.get("manufacturer_name"));
//        }

        try {
            if (!map.isEmpty()) {
                System.out.println("map string  -" + map.toString());
                if (map.get("save").equals("Save")) {
                    model.insertData(map, itr, modulesSetMF,modulesSetDT,modulesSetMN);
                } else if (map.get("delete").equals("Delete")) {
                    //model.insertData(map, itr, modulesSet);
                }
            }
        } catch (Exception e) {

        }

        String task = request.getParameter("task");
        String getImage = request.getParameter("getImage");

        try {
            if (getImage == null) {
                getImage = "";
            } else {

                String destinationPath = getImage;
                File f = new File(destinationPath);
                FileInputStream fis = null;
                if (!f.exists()) {
                    destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    f = new File(destinationPath);
                }
                fis = new FileInputStream(f);
                if (destinationPath.contains("pdf")) {
                    response.setContentType("pdf");
                } else {
                    response.setContentType("image/jpeg");
                }

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                response.setContentLength(fis.available());
                ServletOutputStream os = response.getOutputStream();
                BufferedOutputStream out = new BufferedOutputStream(os);
                int ch = 0;
                while ((ch = bis.read()) != -1) {
                    out.write(ch);
                }
                bis.close();
                fis.close();
                out.close();
                os.close();
                response.flushBuffer();
                return;
            }

        } catch (Exception e) {
            System.out.println("errorr -" + e);
            return;
        }

        //System.out.println("before try");
        try {
            String JQstring = request.getParameter("action1");
            System.out.println("JQstring action --" + JQstring);
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                List<DeviceBean> list2 = null;

                if (JQstring.equals("getManufacturerName")) {
                    list = model.getManufacturerName(q);
                }
                if (JQstring.equals("getDeviceType")) {
                    String type = "all";
                    list = model.getDeviceType(q, type);
                }
                if (JQstring.equals("getModelName")) {
                    String type = "all";
                    list = model.getModelName(q, type);
                }
                if (JQstring.equals("getModelNo")) {
                    String model_name = request.getParameter("model_name");
                    list = model.getModelNo(q, model_name);
                }
                if (JQstring.equals("getManufacturerNameModule")) {
                    list = model.getManufacturerName(q);
                }
                if (JQstring.equals("getDeviceTypeModule")) {
                    String type = "non-finished";
                    list = model.getDeviceType(q, type);
                }
                if (JQstring.equals("getModelNameModule")) {
                    String type = "non-finished";
                    list = model.getModelName(q, type);
                }

                JSONObject gson = new JSONObject();
                gson.put("list", list);
                //System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --NewDeviceController get JQuery Parameters Part-" + e);
        }

        try {
            String taskNew = request.getParameter("map_device");
            if (taskNew.equals("Map Device")) {
                String module_table_id = request.getParameter("module_table_id");
                String device_table_id[] = request.getParameterValues("device_names");
                model.insertMappingData(module_table_id, device_table_id);

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

        List<DeviceBean> list2 = model.getDevice(idd);

        request.setAttribute("list2", list2);
        request.setAttribute("device_type", device_type);
        request.setAttribute("module_table_id", model.getModule_table_id());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMsgBgColor());
        request.getRequestDispatcher("/devicesAdd").forward(request, response);

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
