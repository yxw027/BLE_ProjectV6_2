/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.controller;
import com.ble.dataEntry.bean.DeviceOprtnChartstcMapBean;
import com.ble.dataEntry.model.DeviceOprtnChartstcMapModel;
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
public class DeviceOprtnChartstcMapController extends HttpServlet {

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
        DeviceOprtnChartstcMapModel ruleModel = new DeviceOprtnChartstcMapModel();
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

                if(JQstring.equals("getServices")) {
                    int manufacturer_id = ruleModel.getManufacturerId(request.getParameter("action2"));
                    int device_type_id = ruleModel.getDeviceTypeId(request.getParameter("action3"));
                    int model_id = ruleModel.getModelId(request.getParameter("action4"));
                    int device_id = ruleModel.getDeviceId(manufacturer_id,device_type_id,model_id);
                    list = ruleModel.getServices(q,device_id);
                }
                else if(JQstring.equals("getCharacteristics")) {
                    int services_id = ruleModel.getServicesId(request.getParameter("action2"));
                    list = ruleModel.getCharacteristicsName(q,services_id);
                }
                else if(JQstring.equals("getBleOperationName")) {
                    list = ruleModel.getBLEOperationName(q);
                }
                else if(JQstring.equals("getDeviceType")) {
                    list = ruleModel.getDeviceTypeName(q);
                }
                else if(JQstring.equals("getManufacturer")) {
                    list = ruleModel.geManufacturerName(q);
                }
                else if(JQstring.equals("getModel")) {
                    list = ruleModel.getModelName(q);
                }
                else if(JQstring.equals("getModelName")) {
                    list = ruleModel.getModelName(q);
                }
                else if(JQstring.equals("getDeviceType")) {
                    list = ruleModel.getDeviceTypeName(q);
                }
                else if(JQstring.equals("getCommandName")) {
                    list = ruleModel.geManufacturerName(q);
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
            ruleModel.deleteRecord(Integer.parseInt(request.getParameter("device_characteristic_ble_map_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int device_characteristic_ble_map_id;
            try {
                device_characteristic_ble_map_id = Integer.parseInt(request.getParameter("device_characteristic_ble_map_id"));
            } catch (Exception e) {
                device_characteristic_ble_map_id = 0;
            }
            if (task.equals("Save AS New")) {
                device_characteristic_ble_map_id = 0;
            }
            DeviceOprtnChartstcMapBean ruleBean = new DeviceOprtnChartstcMapBean();
            ruleBean.setDevice_characteristic_ble_map_id(device_characteristic_ble_map_id);
            //int command_id = ruleModel.getCommandId(request.getParameter("command"));
            int manufacturer_id = ruleModel.getManufacturerId(request.getParameter("manufacturer"));
            int device_type_id = ruleModel.getDeviceTypeId(request.getParameter("device_type"));
            int model_id = ruleModel.getModelId(request.getParameter("model"));
            int device_id = ruleModel.getDeviceId(manufacturer_id,device_type_id,model_id);
            ruleBean.setDevice_id(device_id);
            int read_characteristic_id = ruleModel.getCharacteristicId(request.getParameter("read_characteristics"));
            int write_characteristic_id = ruleModel.getCharacteristicId(request.getParameter("write_characteristics"));
            ruleBean.setRead_characteristics_id(read_characteristic_id);
            ruleBean.setWrite_characteristics_id(write_characteristic_id);
            int ble_operation_name_id = ruleModel.getBLEOperationNameId(request.getParameter("ble_operation_name"));
            ruleBean.setBle_operation_name_id(ble_operation_name_id);
            ruleBean.setOrder_no(Integer.parseInt(request.getParameter("order_no")));
            ruleBean.setRemark(request.getParameter("remark"));

            if (device_characteristic_ble_map_id == 0) {
                System.out.println("Inserting values by model......");
                ruleModel.insertRecord(ruleBean);
            } else {
                System.out.println("Update values by model........");
                ruleModel.reviseRecords(ruleBean);
            }
        }

        String searchCommandName = "";
        String searchDeviceType = "";
        String searchModelName = "";

        searchCommandName = request.getParameter("searchCommandName");
        searchDeviceType = request.getParameter("searchDeviceType");
        searchModelName = request.getParameter("searchModelName");

         try {
            if (searchCommandName == null) {
                searchCommandName="";
            }
            if (searchDeviceType == null) {
                searchDeviceType="";
            }
            if (searchModelName == null) {
                searchModelName="";
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
        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchCommandName="";
            searchDeviceType = "";
            searchModelName = "";

        }
           // Logic to show data in the table.
        List<DeviceOprtnChartstcMapBean> commandTypeList = ruleModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
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
        request.setAttribute("searchDeviceType",searchDeviceType );
        request.setAttribute("searchModelName",searchModelName );
        request.setAttribute("message", ruleModel.getMessage());
        request.setAttribute("msgBgColor", ruleModel.getMsgBgColor());
        request.getRequestDispatcher("/device_oprtn_chartstc_map").forward(request, response);
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

