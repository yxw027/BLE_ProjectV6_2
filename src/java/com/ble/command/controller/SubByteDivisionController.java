/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.bean.SubByteDivisionBean;
import com.ble.command.model.SubByteDivisionModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class SubByteDivisionController extends HttpServlet {

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

        ServletContext ctx = getServletContext();
        SubByteDivisionModel subByteDivisionModel = new  SubByteDivisionModel();
        subByteDivisionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        subByteDivisionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        subByteDivisionModel.setDb_username(ctx.getInitParameter("db_username"));
        subByteDivisionModel.setDb_password(ctx.getInitParameter("db_password"));
        subByteDivisionModel.setConnection();
        int sub_byte_division_id;
        
        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        String sub_byte_division = request.getParameter("sub_byte_division");
        String byte_data_id = request.getParameter("byte_data_id");
        String parameter_name_byte1 = request.getParameter("parameter_name");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getParameter")) {
                list = subByteDivisionModel.getParameterName();
            }
        
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            subByteDivisionModel.closeConnection();
            return;
        }
        noOfRowsInTable =  subByteDivisionModel.getNoOfRows();
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }

//        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
//        else if (buttonAction.equals("Previous")) {
//            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
//            if (temp < 0) {
//                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
//                lowerLimit = 0;
//            } else {
//                lowerLimit = temp;
//            }
//        } else if (buttonAction.equals("First")) {
//            lowerLimit = 0;
//        } else if (buttonAction.equals("Last")) {
//            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
//            if (lowerLimit < 0) {
//                lowerLimit = 0;
//            }
//        }
//        List<SelectionBean> selectionList = selectionModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName);
        if (task.equals("Save") || task.equals("Save AS New")) {

            try {
                sub_byte_division_id = Integer.parseInt(request.getParameter("sub_byte_division_id"));
            } catch (Exception e) {
              sub_byte_division_id = 0;
            }
            if (task.equals("Save AS New")) {
               sub_byte_division_id= 0;
            }

            if (sub_byte_division != null && parameter_name_byte1 != null) {
               int selection =Integer.parseInt(sub_byte_division);
                for (int i = 1; i <= selection; i++) {
                    SubByteDivisionBean bean = new SubByteDivisionBean();
                    bean.setSub_byte_division_id(sub_byte_division_id);
                    bean.setParameter_name_byte(request.getParameter("byte_parameter_name" + i));
                    bean.setParameter_name(request.getParameter("parameter_name" + i));
                    bean.setSub_division_no(Integer.parseInt(request.getParameter("sub_division_no" + i)));
                    bean.setStart_pos(Integer.parseInt(request.getParameter("start_pos" + i)));
                    bean.setNo_of_bit(Integer.parseInt(request.getParameter("no_of_bit" + i)));
                    bean.setRemark(request.getParameter("remark" + i));                  
                    if (sub_byte_division_id == 0) {
                        System.out.println("Inserting values by model......");
                         subByteDivisionModel.insertRecord(bean);
                    }
                }
            } 

        }
        if (task.equals("update")) {
            int count = Integer.parseInt(request.getParameter("count"));
            sub_byte_division_id = Integer.parseInt(request.getParameter("sub_byte_division_id"+count));
            int sub_division_no = Integer.parseInt(request.getParameter("sub_division_no"+count));
          
            byte_data_id = request.getParameter("byte_data_id");
            SubByteDivisionBean bean = new  SubByteDivisionBean();
            bean.setSub_byte_division_id(sub_byte_division_id);
            bean.setNo_of_bit(Integer.parseInt(request.getParameter("no_of_bit"+count)));
            bean.setParameter_name_byte(request.getParameter("byte_parameter_name"+count));
            bean.setParameter_name(request.getParameter("parameter_name"+count));
            bean.setStart_pos(Integer.parseInt(request.getParameter("start_pos"+count)));
            bean.setSub_division_no(Integer.parseInt(request.getParameter("sub_division_no"+count)));
            System.out.println("Update values by model........");
             subByteDivisionModel.updateRecords(bean);

        }

        if (sub_byte_division != null) {
            request.setAttribute("sub_byte_division", sub_byte_division);
            String parameter_name_byte = subByteDivisionModel.getParameterNameByByte_id(Integer.parseInt(byte_data_id));
            int length = parameter_name_byte.length();
            request.setAttribute("parameter_name", parameter_name_byte);
            request.setAttribute("byte_data_id", byte_data_id);
            List<SubByteDivisionBean> sub_byte_divisionListById =  subByteDivisionModel.showDataByByteId(lowerLimit, noOfRowsToDisplay, sub_byte_division, byte_data_id);
            request.setAttribute("sub_byte_divisionListById", sub_byte_divisionListById);
            request.getRequestDispatcher("/sub_byte_division").forward(request, response);
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
