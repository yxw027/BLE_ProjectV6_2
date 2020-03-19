/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.CommandTypeBean;
import com.ble.dataEntry.model.CommandTypeModel;
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
import org.json.simple.JSONObject;

/**
 *
 * @author Shobha
 */
public class CommandTypeController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        CommandTypeModel commandTypeModel = new CommandTypeModel();
        commandTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        commandTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        commandTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        commandTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        commandTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getCommandType")) {
                    list = commandTypeModel.getCommandType(q);
                }

               Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
               commandTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
//          if(task.equals("generateMapReport")){
//                String searchDivisionType="";
//                List listAll = null;
//                String jrxmlFilePath;
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                listAll=divisionModel.showAllData(searchDivisionType);
//                jrxmlFilePath = ctx.getRealPath("/report/division_m.jrxml");
//                byte[] reportInbytes = divisionModel.generateMapReport(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            }
         if (task.equals("Cancel")) {
            commandTypeModel.deleteRecord(Integer.parseInt(request.getParameter("command_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int command_type_id;
            try {
                command_type_id = Integer.parseInt(request.getParameter("command_type_id"));
            } catch (Exception e) {
                command_type_id = 0;
            }
            if (task.equals("Save AS New")) {
               command_type_id = 0;
            }
            CommandTypeBean commandTypeBean = new CommandTypeBean();
            commandTypeBean.setCommand_type_id(command_type_id);
            commandTypeBean.setName(request.getParameter("name"));
            commandTypeBean.setShorthand(request.getParameter("shorthand"));
            commandTypeBean.setRemark(request.getParameter("remark"));

            if (command_type_id == 0) {
                System.out.println("Inserting values by model......");
                commandTypeModel.insertRecord(commandTypeBean);
            } else {
                System.out.println("Update values by model........");
                commandTypeModel.reviseRecords(commandTypeBean);
            }
        }

        String searchCommandType= "";

        searchCommandType = request.getParameter("searchCommandType");

         try {
            if (searchCommandType == null) {
                searchCommandType="";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
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
        System.out.println("searching.......... " + searchCommandType);

         noOfRowsInTable = commandTypeModel.getNoOfRows(searchCommandType);

         if (buttonAction.equals("Next")){
             searchCommandType=request.getParameter("manname");
           noOfRowsInTable = commandTypeModel.getNoOfRows(searchCommandType);
             
         } // lowerLimit already has value such that it shows forward records, so do nothing here.
         else if (buttonAction.equals("Previous")) {
              searchCommandType=request.getParameter("manname");
           noOfRowsInTable = commandTypeModel.getNoOfRows(searchCommandType);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchCommandType=request.getParameter("manname");
           //noOfRowsInTable = commandTypeModel.getNoOfRows(searchCommandType);
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchCommandType=request.getParameter("manname");
           noOfRowsInTable = commandTypeModel.getNoOfRows(searchCommandType);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchCommandType="";

        }
           // Logic to show data in the table.
        List<CommandTypeBean> commandTypeList = commandTypeModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandType);
        lowerLimit = lowerLimit + commandTypeList.size();
        noOfRowsTraversed = commandTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("divisionTypeList", commandTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + commandTypeModel.getMsgBgColor());
      
 request.setAttribute("manname", searchCommandType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandType",searchCommandType );
        request.setAttribute("message", commandTypeModel.getMessage());
        request.setAttribute("msgBgColor", commandTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/commandtype").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
