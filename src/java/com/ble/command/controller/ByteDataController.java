/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.controller;

import com.ble.command.model.ByteDataModel;
import com.ble.command.bean.ByteDataBean;
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
public class ByteDataController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

        ServletContext ctx = getServletContext();
        ByteDataModel byteDataModel = new ByteDataModel();
        byteDataModel.setDriverClass(ctx.getInitParameter("driverClass"));
        byteDataModel.setConnectionString(ctx.getInitParameter("connectionString"));
        byteDataModel.setDb_username(ctx.getInitParameter("db_username"));
        byteDataModel.setDb_password(ctx.getInitParameter("db_password"));
        byteDataModel.setConnection();
        int byte_data_id;

        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        String bitwise = request.getParameter("bitwise2");
        String command_id = request.getParameter("command_id2");
        String command123 = request.getParameter("command_name2");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getCommand")) {
                list = byteDataModel.getCommandName();
            }

            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            byteDataModel.closeConnection();
            return;
        }
        noOfRowsInTable = byteDataModel.getNoOfRows();
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }

//      
        if (task.equals("Save") || task.equals("Save AS New")) {

            try {
                byte_data_id = Integer.parseInt(request.getParameter("byte_data_id"));
            } catch (Exception e) {
                byte_data_id = 0;
            }
            if (task.equals("Save AS New")) {
                byte_data_id = 0;
            }

            if (bitwise != null && command123 != null) {
                int selection = Integer.parseInt(bitwise);
                for (int i = 1; i <= selection; i++) {
                    ByteDataBean bean = new ByteDataBean();
                    bean.setByte_data_id(byte_data_id);
                    bean.setCommand_name(request.getParameter("command_name" + i));
                    bean.setParameter_name(request.getParameter("parameter_name" + i));

                    bean.setSub_byte_division(Integer.parseInt(request.getParameter("sub_byte_division" + i)));
                    bean.setRemark(request.getParameter("remark" + i));
                    if (byte_data_id == 0) {
                        System.out.println("Inserting values by model......");
                        byteDataModel.insertRecord(bean);
                    }

                }
            } 
        }
        if (task.equals("update")) {
            int count = Integer.parseInt(request.getParameter("count"));
            byte_data_id = Integer.parseInt(request.getParameter("byte_data_id"+count));
            
            ByteDataBean bean = new ByteDataBean();
            bean.setByte_data_id(byte_data_id);
            bean.setCommand_name(request.getParameter("command_name" + count));
            bean.setParameter_name(request.getParameter("parameter_name" + count));

            bean.setSub_byte_division(Integer.parseInt(request.getParameter("sub_byte_division" + count)));
            bean.setRemark(request.getParameter("remark" + count));
            System.out.println("Update values by model........");
            byteDataModel.updateRecords(bean);

        }

        if (bitwise != null) {
            request.setAttribute("bitwise", bitwise);
            String command = byteDataModel.getCommandNameByCommand_id(Integer.parseInt(command_id));
            int length = command.length();
            request.setAttribute("command", command);
            request.setAttribute("command_id", command_id);
            List< ByteDataBean> databyteListById = byteDataModel.showDataByCommandId(lowerLimit, noOfRowsToDisplay, bitwise, command_id);
            request.setAttribute("databyteListById", databyteListById);
            request.getRequestDispatcher("/byte_data").forward(request, response);
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
