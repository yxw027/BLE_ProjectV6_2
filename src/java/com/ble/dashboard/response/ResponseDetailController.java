/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.response;

import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONObject;

/**
 *
 * @author saini
 */
public class ResponseDetailController extends HttpServlet {

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
        ServletContext ctx = getServletContext();
        ResponseDetailModel commandModel = new ResponseDetailModel();
        commandModel.setDriverClass(ctx.getInitParameter("driverClass"));
        commandModel.setConnectionString(ctx.getInitParameter("connectionString"));
        commandModel.setDb_username(ctx.getInitParameter("db_username"));
        commandModel.setDb_password(ctx.getInitParameter("db_password"));
        commandModel.setConnection();

        HttpSession session = request.getSession();

        String task = request.getParameter("task");
        try {

            String JQstring = request.getParameter("action1");
            System.err.println("jq string -" + JQstring);
            String q = request.getParameter("str");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                List<ResponseDetailBean> list2 = null;
                if (JQstring.equals("getParameter")) {
                    String type = request.getParameter("type");
                    list = commandModel.getParameter(q, type);
                } else if (JQstring.equals("getCommand")) {
                    list = commandModel.getCommand(q);
                } else if (JQstring.equals("getSelectionValue")) {
                    String param = request.getParameter("param");
                    list = commandModel.getSelectionValue(q, param);
                } else if (JQstring.equals("getDisplayByteVal")) {
                    String sel_val = request.getParameter("sel_val");
                    String param = request.getParameter("param");
                    list2 = commandModel.getDisplayByteVal(q, sel_val, param);
                } else if (JQstring.equals("getBitwiseValue")) {
                    String param = request.getParameter("param");
                    list = commandModel.getBitwiseValue(q, param);
                } else if (JQstring.equals("getSubByteDivVal")) {
                    String sub_byte_div_val = request.getParameter("sub_byte_div_val");
                    String param = request.getParameter("param");
                    list2 = commandModel.getSubByteDivVal(q, sub_byte_div_val, param);
                } else if (JQstring.equals("getBitwiseDisByteVal")) {
                    String id = request.getParameter("id");
                    list2 = commandModel.getBitwiseDisByteVal(q, id);
                }

                JSONObject gson = new JSONObject();
                if (JQstring.equals("getDisplayByteVal") || JQstring.equals("getSubByteDivVal") || JQstring.equals("getBitwiseDisByteVal")) {
                    gson.put("data", list2);
                } else {
                    gson.put("list", list);
                }
                System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --CommandController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }

        int fix_number = 3;
        int var_number = 0;
        int bitwise_number = 0;

        if (task.equals("Cancel")) {
            commandModel.deleteRecord(Integer.parseInt(request.getParameter("command_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int response_id = 0;
            
            String fix=request.getParameter("fixed_response_no");
            String var=request.getParameter("variable_response_no");
            String bit=request.getParameter("bitwise_response_no");
            if(fix==null){
                fix="";
            }
            if(var==null){
                var="";
            }
            if(bit==null){
                bit="";
            }
            

            if (!fix.equals("")) {
                fix_number = Integer.parseInt(request.getParameter("fixed_response_no"));
            }
            if (!var.equals("")) {
                var_number = Integer.parseInt(request.getParameter("variable_response_no"));
            }
            if (!bit.equals("")) {
                bitwise_number = Integer.parseInt(request.getParameter("bitwise_response_no"));
            }


            ResponseDetailBean commandBean = new ResponseDetailBean();
            //commandBean.setResponse_id(response_id);            
            commandBean.setCommand(request.getParameter("command"));                       
            commandBean.setResponse(request.getParameter("response"));                       

            
            // START for Variable
            
            int inp_no = 0;
            List<String> inputParameter = new ArrayList<>();
            if (!var.equals("")) {
                commandBean.setInput_no(Integer.parseInt(request.getParameter("variable_response_no")));
                inp_no = Integer.parseInt(request.getParameter("variable_response_no"));
                for (int l = 1; l <= inp_no; l++) {
                    inputParameter.add(request.getParameter("input_parameter" + l));
                }
            }
            
            // END for Input
            

            // START For Fixed Response
            int sel_no = 0;
            List<Integer> SelValList = new ArrayList<>();
            List<String> SelParameter = new ArrayList<>();
//            List<String> displayVal = new ArrayList<>();
//            List<String> byteVal = new ArrayList<>();
            List<Integer> selCheckedVal = new ArrayList<>();
            String[] selectionCheckBox = null;
            if (!fix.equals("")) {
                commandBean.setSelection_no(Integer.parseInt(request.getParameter("fixed_response_no")));

                //Start changes
                sel_no = commandBean.getSelection_no();
                for (int k = 1; k <= sel_no; k++) {
                    SelValList.add(Integer.parseInt(request.getParameter("selection_values" + k)));
                    SelParameter.add(request.getParameter("selection_parameter" + k));

                    for (int j = 1; j <= Integer.parseInt(request.getParameter("selection_values" + k)); j++) {
                        //displayVal.add(request.getParameter("display_value" + k + j));
                        //byteVal.add(request.getParameter("byte_value" + k + j));

                        selectionCheckBox = request.getParameterValues("check_box" + k + j);
                        System.err.println("sel val -" + selectionCheckBox);
                        if (selectionCheckBox != null) {
                            selCheckedVal.add(Integer.parseInt(selectionCheckBox[0]));
                        }else{
                            selCheckedVal.add(0);
                        }

                    }

                }

                //End Changes
            }
//            System.err.println("selection checked listt -"+selCheckedVal.size()+" val -"+selCheckedVal.toString());

            // END For Selection
            
            
            
            
            // START For Bitwise Response
            int bitwise = 0;
            List<String> BitParamList = new ArrayList<>();
            List<Integer> BitValuesList = new ArrayList<>();
            List<Integer> bitCheckedVal = new ArrayList<>();
            String[] bitwiseCheckBox = null;
            if (!bit.equals("")) {
                commandBean.setBitwise(Integer.parseInt(request.getParameter("bitwise_response_no")));
                bitwise = commandBean.getBitwise();
                for (int k = 1; k <= bitwise; k++) {
                    BitParamList.add(request.getParameter("bitwise_parameter" + k));
                    BitValuesList.add(Integer.parseInt(request.getParameter("bitwise_values" + k)));

                    for (int j = 1; j <= Integer.parseInt(request.getParameter("bitwise_values" + k)); j++) {
                        bitwiseCheckBox = request.getParameterValues("bitwise_disp_byte_id" + k + j);                        
                        if (bitwiseCheckBox != null) {
                            bitCheckedVal.add(Integer.parseInt(bitwiseCheckBox[0]));
                        }else{
                            bitCheckedVal.add(0);
                        }
                    }

                }

            }
            //System.err.println("elementt -"+bitCheckedVal.toString()+" length -"+bitCheckedVal.size());

            // END For Bitwise
            commandBean.setData_extract_type(request.getParameter("data_extract_type"));
            commandBean.setFormat(request.getParameter("format"));
            commandBean.setRemark(request.getParameter("remark"));

            if (response_id == 0) {
                System.out.println("Inserting values by model......");
                commandModel.insertRecord(commandBean, sel_no, inp_no, inputParameter, SelValList, SelParameter, selCheckedVal, bitCheckedVal,bitwise,BitParamList,BitValuesList);
            } else {
                System.out.println("Update values by model........");
                commandModel.updateRecords(commandBean);
            }
        }

        String searchCommandName = "";
        String searchDeviceName = "";
        String searchManufacturerName = "";
        String searchDeviceType = "";
        searchCommandName = request.getParameter("searchCommandName");
        searchDeviceName = request.getParameter("searchDeviceName");
        searchManufacturerName = request.getParameter("searchManufacturerName");
        searchDeviceType = request.getParameter("searchDeviceType");

        // Logic to show data in the table.
        List<ResponseDetailBean> list = commandModel.showData();
        int lastInsertedId = 0;
//        int selection_number=Integer.parseInt(request.getParameter("selection_no"));
//        int input_number=Integer.parseInt(request.getParameter("input_no"));
//        int bitwise_number=Integer.parseInt(request.getParameter("bitwise"));
        try {
            lastInsertedId = commandModel.getLastId();
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.dataEntry.CommandController.processRequest()-" + e);
        }

        request.setAttribute("list", list);
        request.setAttribute("is_parameter", request.getParameter("parameter"));
        request.setAttribute("lastInsertedId", lastInsertedId);
        request.setAttribute("fix_number", fix_number);
        request.setAttribute("var_number", var_number);
        request.setAttribute("bitwise_number", bitwise_number);
        request.setAttribute("manufacturer", request.getParameter("manufacturer"));
        request.setAttribute("device_type", request.getParameter("device_type"));
        request.setAttribute("deviceName", request.getParameter("device_name"));
        request.setAttribute("device_no", request.getParameter("device_no"));
        request.setAttribute("manname", searchCommandName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchCommandName", searchCommandName);
        request.setAttribute("searchDeviceName", searchDeviceName);
        request.setAttribute("searchManufacturerName", searchManufacturerName);
        request.setAttribute("searchDeviceType", searchDeviceType);
        request.setAttribute("message", commandModel.getMessage());
        request.setAttribute("msgBgColor", commandModel.getMsgBgColor());
        request.getRequestDispatcher("/response_detail").forward(request, response);
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
