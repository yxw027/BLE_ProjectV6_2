/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.command.controller;
import com.ble.command.bean.RuleBean;
import com.ble.command.model.RuleModel;
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
/**
 *
 * @author jpss
 */
public class RuleController extends HttpServlet {

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
        System.out.println("this is rule Controller....");
        ServletContext ctx = getServletContext();
        RuleModel ruleModel = new RuleModel();
        ruleModel.setDriverClass(ctx.getInitParameter("driverClass"));
        ruleModel.setConnectionString(ctx.getInitParameter("connectionString"));
        ruleModel.setDb_username(ctx.getInitParameter("db_username"));
        ruleModel.setDb_password(ctx.getInitParameter("db_password"));
        ruleModel.setConnection();

        //HttpSession session = request.getSession();



        String task = request.getParameter("task");
         try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getCommandName")) {
                    list = ruleModel.getCommand(q);
                }
                else if(JQstring.equals("getCommand")) {
                    list = ruleModel.getCommand(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                ruleModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --RuleController get JQuery Parameters Part-" + e);
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
            ruleModel.deleteRecord(Integer.parseInt(request.getParameter("rule_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int rule_id;
            try {
                rule_id = Integer.parseInt(request.getParameter("rule_id"));
            } catch (Exception e) {
                rule_id = 0;
            }
            if (task.equals("Save AS New")) {
                rule_id = 0;
            }
            RuleBean ruleBean = new RuleBean();
            ruleBean.setRule_id(rule_id);
            int command_id = ruleModel.getCommandId(request.getParameter("command"));
            ruleBean.setCommand_id(command_id);
            ruleBean.setDescription(request.getParameter("rule"));
            ruleBean.setRemark(request.getParameter("remark"));

            if (rule_id == 0) {
                System.out.println("Inserting values by model......");
                ruleModel.insertRecord(ruleBean);
            } else {
                System.out.println("Update values by model........");
                ruleModel.reviseRecords(ruleBean);
            }
        }

        String searchCommandName = "";

        searchCommandName = request.getParameter("searchCommandName");

         try {
            if (searchCommandName == null) {
                searchCommandName="";
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
        System.out.println("searching.......... " + searchCommandName);

         noOfRowsInTable = ruleModel.getNoOfRows(searchCommandName);

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
            searchCommandName="";

        }
           // Logic to show data in the table.
        List<RuleBean> commandTypeList = ruleModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
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

        System.out.println("color is :" + ruleModel.getMsgBgColor());
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        //request.setAttribute("operationName", request.getParameter("operation_name"));
        //request.setAttribute("commandName", request.getParameter("command"));

        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandName",searchCommandName );
        request.setAttribute("message", ruleModel.getMessage());
        request.setAttribute("msgBgColor", ruleModel.getMsgBgColor());
        request.getRequestDispatcher("/rule").forward(request, response);
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

