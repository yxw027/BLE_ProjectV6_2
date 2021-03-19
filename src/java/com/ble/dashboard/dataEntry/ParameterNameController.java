/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import com.ble.util.UniqueIDGenerator;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ParameterNameController extends HttpServlet {

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
        ParameterNameModel model = new ParameterNameModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        Map<String, String> map = new HashMap<String, String>();

        String task = request.getParameter("task");
        System.err.println("taskkk --" + task);
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                List<DeviceTypeBean> list2 = null;

                if (JQstring.equals("getParentType")) {
                    //list = model.getParentType(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                //System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ParameterController get JQuery Parameters Part-" + e);
        }

        if (task == null) {
            task = "";
        }

        if (task.equals("Save")) {
            ParameterNameBean bean = new ParameterNameBean();
            bean.setParameter_name(request.getParameter("parameter_name"));
            bean.setParameter_type(request.getParameter("parameter_type"));
            bean.setRemark(request.getParameter("remark"));

            // START when type equal to Selection
            int selection_value = 0;
            List<String> displayVal = new ArrayList<>();
            List<String> byteVal = new ArrayList<>();
            if (request.getParameter("parameter_type").equals("Selection")) {
                selection_value = Integer.parseInt(request.getParameter("sel_val_no"));
                for (int i = 1; i <= selection_value; i++) {
                    displayVal.add(request.getParameter("diaplay_value_" + i));
                    byteVal.add(request.getParameter("byte_value_" + i));
                }
            }

            // END when type equal to Selection
            // START when type equal to Bitwise
            int bitiwse_no = 0;
            List<String> subByteDiv = new ArrayList<>();
            List<String> subByteDivName = new ArrayList<>();
            List<String> subDivSelNo = new ArrayList<>();
            List<String> startPos = new ArrayList<>();
            List<String> noOfBit = new ArrayList<>();
            List<String> bitDispVal = new ArrayList<>();
            List<String> bitByteVal = new ArrayList<>();
            if (request.getParameter("parameter_type").equals("Bitwise")) {
                bitiwse_no = Integer.parseInt(request.getParameter("bitiwse_no"));
                //for (int i = 0; i < bitiwse_no; i++) {  
                //
                for (int i = 0; i < 1; i++) {                    
                    subByteDiv.add(request.getParameter("sub_byte_division_" + (i + 1)));
                    subByteDivName.add(request.getParameter("bitwise_div_class_parameter_" + (i + 1)));
                    for (int k = 0; k < Integer.parseInt(subByteDiv.get(i)); k++) {
                        subDivSelNo.add(request.getParameter("sub_div_sel_no" + (i + 1) + "" + (k + 1)));
                        startPos.add(request.getParameter("start_position_" + (i + 1) + "" + (k + 1)));
                        noOfBit.add(request.getParameter("no_of_bit_" + (i + 1) + "" + (k + 1)));
                        for (int j = 0; j < Integer.parseInt(subDivSelNo.get(k)); j++) {
                            bitDispVal.add(request.getParameter("display_value_" + (i + 1) + "" + (k + 1) + "" + (j + 1)));
                            bitByteVal.add(request.getParameter("bit_value_" + (i + 1) + "" + (k + 1) + "" + (j + 1)));
                        }
                    }
                }
            }

            // END when type equal to Selection
            try {
                //model.insertRecord(bean);
                if (request.getParameter("parameter_type").equals("Selection")) {
                    model.insertRecord(bean, selection_value, displayVal, byteVal);
                }
                if (request.getParameter("parameter_type").equals("Bitwise")) {
                    model.insertRecordInBitWise(bean,bitiwse_no,subByteDiv,subDivSelNo,startPos,noOfBit,bitDispVal,bitByteVal,subByteDivName);
                }
                if (request.getParameter("parameter_type").equals("Input")) {
                    model.insertRecordForOther(bean);
                }
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

        List<ParameterNameBean> list2 = model.showData();
        //System.err.println("list 2 -"+list2.size());

        request.setAttribute("list2", list2);
        request.setAttribute("module_table_id", model.getModule_table_id());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMsgBgColor());
        request.getRequestDispatcher("/parameter_name").forward(request, response);

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
