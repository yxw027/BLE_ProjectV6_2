
package com.ble.response.controller;

import com.ble.response.bean.FixedResponse;
import com.ble.response.model.FixedResponseModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FixedResponseController extends HttpServlet {

     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

        ServletContext ctx = getServletContext();
        FixedResponseModel fixedResponseModel = new FixedResponseModel();
        fixedResponseModel.setDriverClass(ctx.getInitParameter("driverClass"));
        fixedResponseModel.setConnectionString(ctx.getInitParameter("connectionString"));
        fixedResponseModel.setDb_username(ctx.getInitParameter("db_username"));
        fixedResponseModel.setDb_password(ctx.getInitParameter("db_password"));
        fixedResponseModel.setConnection();
        int fixed_response_id;
        String task = request.getParameter("task");
        String action1 = request.getParameter("action1");
        String q = request.getParameter("q");
        if (task == null) {
            task = "";
        }
        
        String fixed_response = request.getParameter("fixed_response1");
        String response_id = request.getParameter("response_id1");
        String response123 = request.getParameter("response");

        if (action1 == null) {
            action1 = "";
        } else if (action1 != null) {
            PrintWriter out = response.getWriter();
            List<String> list = null;

            if (action1.equals("getResponse")) {
                list = fixedResponseModel.getResponseName();
            }
            if (action1.equals("getParameter")) {
                list = fixedResponseModel.getParameter();
            }
            if (action1.equals("getParameterType")) {
                list = fixedResponseModel.getParameterType();
            }
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
            }

            fixedResponseModel.closeConnection();
            return;

        }

//        String searchCommandName = "";
//
//        searchCommandName = request.getParameter("searchCommandName");
//
//        System.out.println("searching.......... " + searchCommandName);
        noOfRowsInTable = fixedResponseModel.getNoOfRows();
//        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
//        if (buttonAction == null) {
//            buttonAction = "none";
//        }

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
                fixed_response_id = Integer.parseInt(request.getParameter("fixed_response_id"));
            } catch (Exception e) {
               fixed_response_id = 0;
            }
            if (task.equals("Save AS New")) {
                fixed_response_id = 0;
            }

            if (fixed_response != null && response123 != null) {
                int fixed_response1 = Integer.parseInt(fixed_response);
                for (int i = 1; i <= fixed_response1; i++) {
                    FixedResponse bean = new FixedResponse();
                    bean.setFixed_response_id(fixed_response_id);
                    bean.setResponse(request.getParameter("response" + i));
                    bean.setParameter(request.getParameter("parameter" + i));
                   
                    bean.setFixed_response_value_no(Integer.parseInt(request.getParameter("fixed_response_value_no" + i)));
                     bean.setStart_pos(Integer.parseInt(request.getParameter("start_pos" + i)));
                      bean.setNo_of_byte(Integer.parseInt(request.getParameter("no_of_byte" + i)));
                    bean.setRemark(request.getParameter("remark" + i));
                    if (fixed_response_id == 0) {
                        System.out.println("Inserting values by model......");
                        fixedResponseModel.insertRecord(bean,Integer.parseInt(response_id));
                    } else {
                        System.out.println("Update values by model........");
                        fixedResponseModel.reviseRecords(bean);
                    }

                }
            } 

        }
        if (task.equals("update")) {
            int count = Integer.parseInt(request.getParameter("count"));
            fixed_response_id = Integer.parseInt(request.getParameter("fixed_response_id" + count));
            int fixed_response_value_no = Integer.parseInt(request.getParameter("fixed_response_value_no" + count));
            fixed_response = request.getParameter("fixed_response");
           response_id = request.getParameter("response_id");
            String response1 = request.getParameter("response" + count);
            String parameter = request.getParameter("parameter" + count);
           
            
            FixedResponse bean = new FixedResponse();
            bean.setFixed_response_id(fixed_response_id);
            bean.setFixed_response_value_no(fixed_response_value_no);
            bean.setParameter(parameter);
           
            bean.setResponse(response1);
            bean.setRemark(request.getParameter("remark" + count));

            System.out.println("Update values by model........");
            fixedResponseModel.updateRecords(bean);

        }
            request.setAttribute("fixed_response", fixed_response);
            String response1 = fixedResponseModel.getResponseNameByResponse_id(Integer.parseInt(response_id));
            int length = response1.length();
            request.setAttribute("response", response1);
            request.setAttribute("response_id", response_id);
            List<FixedResponse> fixedResponseListById = fixedResponseModel.showDataByResponseId(lowerLimit, noOfRowsToDisplay, fixed_response, response_id);
            request.setAttribute("fixedResponseListById", fixedResponseListById);
            request.getRequestDispatcher("/fixed_response").forward(request, response);

    }
    


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

