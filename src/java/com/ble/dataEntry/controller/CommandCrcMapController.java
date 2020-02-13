/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.controller;

import com.ble.dataEntry.bean.CommandCrcMapBean;
import com.ble.dataEntry.model.CommandCrcMapModel;
import com.ble.device.bean.DeviceMapBean;
import com.ble.device.model.DeviceMapModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CommandCrcMapController", urlPatterns = {"/CommandCrcMapController"})
public class CommandCrcMapController extends HttpServlet {

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
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        CommandCrcMapModel comcrcModel = new CommandCrcMapModel();
        comcrcModel.setDriverClass(ctx.getInitParameter("driverClass"));
        comcrcModel.setConnectionString(ctx.getInitParameter("connectionString"));
        comcrcModel.setDb_username(ctx.getInitParameter("db_username"));
        comcrcModel.setDb_password(ctx.getInitParameter("db_password"));
        comcrcModel.setConnection();

        String task = request.getParameter("task");
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getCommand")) {
                    list = comcrcModel.getCommand(q);
                    System.out.println("hello");
                } 
                else if(JQstring.equals("getCrcType")) {
                    list = comcrcModel.getCrcType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                comcrcModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
 
         if (task.equals("Cancel")) {
            comcrcModel.deleteRecord(Integer.parseInt(request.getParameter("cmd_crcmap_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int cmd_crcmap_id;
            try {
                cmd_crcmap_id = Integer.parseInt(request.getParameter("cmd_crcmap_id"));
            } catch (Exception e) {
                cmd_crcmap_id = 0;
            }
            if (task.equals("Save AS New")) {
                cmd_crcmap_id = 0;
            }
             CommandCrcMapBean cmdcrcmapbean = new CommandCrcMapBean();
            cmdcrcmapbean.setCommand_crc_map_id(cmd_crcmap_id);
            cmdcrcmapbean.setCommand(request.getParameter("Command"));
            cmdcrcmapbean.setCrc_type(request.getParameter("crctype"));
            cmdcrcmapbean.setRemark(request.getParameter("remark"));

            if (cmd_crcmap_id == 0) {
                System.out.println("Inserting values by model......");
                comcrcModel.insertRecord(cmdcrcmapbean);
            } else {
                System.out.println("Update values by model........");
                comcrcModel.reviseRecords(cmdcrcmapbean);
            }
        }

        String searchCommand = "";
        String searchCrctype = "";

        searchCommand = request.getParameter("searchCommand");
        searchCrctype = request.getParameter("searchCrctype");
         try {
            if (searchCommand == null || searchCrctype == null) {
                searchCommand="";
                searchCrctype="";
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
        System.out.println("searching.......... " + searchCommand);
        System.out.println("searching.......... " + searchCrctype);

         noOfRowsInTable = comcrcModel.getNoOfRows(searchCommand,searchCrctype);

         if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
         else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchCommand="";
            searchCrctype="";

        }
           // Logic to show data in the table.
        List<CommandCrcMapBean> cmdmapList = comcrcModel.showData(lowerLimit, noOfRowsToDisplay,searchCommand,searchCrctype);
        lowerLimit = lowerLimit + cmdmapList.size();
        noOfRowsTraversed = cmdmapList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("cmdmapList", cmdmapList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + comcrcModel.getMsgBgColor());
 

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommand",searchCommand );
        request.setAttribute("searchCrctype",searchCrctype );
        request.setAttribute("message", comcrcModel.getMessage());
        request.setAttribute("msgBgColor", comcrcModel.getMsgBgColor());
        request.getRequestDispatcher("/commandcrcmapping").forward(request, response);
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
