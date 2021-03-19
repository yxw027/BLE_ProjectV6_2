/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.module;

import com.ble.dashboard.*;
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
public class AddModulesController extends HttpServlet {

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
        AddModulesModel addModulesModel = new AddModulesModel();
        addModulesModel.setDriverClass(ctx.getInitParameter("driverClass"));
        addModulesModel.setConnectionString(ctx.getInitParameter("connectionString"));
        addModulesModel.setDb_username(ctx.getInitParameter("db_username"));
        addModulesModel.setDb_password(ctx.getInitParameter("db_password"));
        addModulesModel.setConnection();
        Map<String, String> map = new HashMap<String, String>();

        List items = null;
        Iterator itr = null;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        List<String> categorySet = new ArrayList<String>();
        List<String> brandSet = new ArrayList<String>();
        List<String> attributeSet = new ArrayList<String>();
        List<String> valueSet = new ArrayList<String>();

        try {
            //if (!map.isEmpty()) {
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                        map.put(item.getFieldName(), item.getString("UTF-8"));

                        if (item.getFieldName().equals("category")) {
                            categorySet.add(item.getString());
                        }
                        if (item.getFieldName().equals("brand")) {
                            brandSet.add(item.getString());
                        }
                        if (item.getFieldName().equals("attribute")) {
                            attributeSet.add(item.getString());
                        }
                        if (item.getFieldName().equals("attribute_value")) {
                            valueSet.add(item.getString());
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

        try {
            if (!map.isEmpty()) {
                System.out.println("map string  -" + map.toString());
                addModulesModel.insertData(map, itr, categorySet, brandSet, attributeSet, valueSet);
            }
        } catch (Exception e) {

        }

        String task = request.getParameter("task");
        
        String getImage = request.getParameter("getImage");

        try {
            if (getImage == null) {
                getImage = "";
            } else {                
                
                //String destinationPath = getImage;
                String destinationPath = addModulesModel.getImagePath(getImage);
                
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
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<AddModulesBean> list = null;

                if (JQstring.equals("getDeviceList")) {
                    list = addModulesModel.showDeviceList();
                }
                if (JQstring.equals("getModulesType")) {
                    list = addModulesModel.getModulesType();
                }

                JSONObject gson = new JSONObject();                
                gson.put("data", list);
                System.out.println("gson -" + gson);
                out.println(gson);                
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --NewDeviceController get JQuery Parameters Part-" + e);
        }
        
        try {
            String taskNew = request.getParameter("map_device");            
            if (taskNew.equals("Map Device")) {
                String module_table_id=request.getParameter("module_table_id");
                String device_table_id[]=request.getParameterValues("device_names");
                addModulesModel.insertMappingData(module_table_id,device_table_id);
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
                

        //request.setAttribute("listDevice", listDevice);
        request.setAttribute("module_table_id", addModulesModel.getModule_table_id());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", addModulesModel.getMessage());
        request.setAttribute("msgBgColor", addModulesModel.getMsgBgColor());        
        request.getRequestDispatcher("/AddModules").forward(request, response);

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
