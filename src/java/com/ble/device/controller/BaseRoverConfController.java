/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.controller;

import com.ble.command.bean.CommandBean;
import com.ble.command.bean.SelectionValueBean;
import com.ble.dataEntry.bean.OperationNameBean;
import com.ble.device.bean.DeviceRegistrationBean;
import com.ble.device.model.DeviceRegModel;
import com.ble.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

 

/**
 *
 * @author Shobha
 */
public class BaseRoverConfController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, InterruptedException {
        
        PrintWriter out = response.getWriter();
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        System.out.println("this is Base Rover Conf Controller....");
        ServletContext ctx = getServletContext();
        DeviceRegModel deviceModel = new DeviceRegModel();
        deviceModel.setDriverClass(ctx.getInitParameter("driverClass"));
        deviceModel.setConnectionString(ctx.getInitParameter("connectionString"));
        deviceModel.setDb_username(ctx.getInitParameter("db_username"));
        deviceModel.setDb_password(ctx.getInitParameter("db_password"));
        deviceModel.setConnection();
        int device_id=0;
        String manu_name="";
        String device_type="";
        String device_name="";
        String DGPS_operation="";
        String device_no="";
        List<String> list = null;
        List<String> commandlist=null;
        List<String> finalcmdlist=null;
        String task = request.getParameter("task");
           
       
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
              
                if(JQstring.equals("getManufactureName")) {
                    
                    list = deviceModel.getManufactureName(q);
                }else if(JQstring.equals("getDeviceTypeName")) {
                    list = deviceModel.getDeviceTypeName(q,request.getParameter("action2"));
                }else if(JQstring.equals("getDeviceName")) {
                    list = deviceModel.getDeviceName(q);
                }else if(JQstring.equals("getDeviceNo")) {
                    list = deviceModel.getDeviceNo(q,request.getParameter("action2"));
                }
                else if(JQstring.equals("getDGPSoperation")) {
                    list = deviceModel.getDGPSoperation(q);
                }else if(JQstring.equals("getSubOperation")) {
                    
                    list = deviceModel.getSubOperation(q,request.getParameter("action2"),request.getParameter("action3"));
                    System.out.println("hello");
              //  }
                   // JSONObject gson = new JSONObject();
                   //  gson.put("list",list);
                  //  gson.put("size", list.size());
                //  out.println(gson);
                //  return;
                    
                    
               }
         }
            
            
//             if (JQstring != null) {
//                    PrintWriter out111 = response.getWriter();
//                    List<String> listyes = null;
//                    if (JQstring.equals("getSubOperation")) {
//                        listyes = deviceModel.getSubOperation12(request.getParameter("action2"));
//                    }
//                   
//                     JSONObject gson = new JSONObject();
//                     gson.put("list",listyes);
//                      out111.println(gson);
//                      return;
//                }
            
            
            
            
               
                else if(JQstring.equals("getBaudrate")) {
                String  manu_name1 = request.getParameter("action3");
                String device_type1 = request.getParameter("action4");
                String   device_name1 = request.getParameter("action5");
                String  device_no1 = request.getParameter("action6");   
               int manufacture_id=deviceModel.getManufactureId(manu_name1);
               int device_type_id = deviceModel.getDeviceTypeId(device_type1);
                int model_id = deviceModel.getModelId(device_name1,device_no1);
               int  device_id1 = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id); 
                int op_id = deviceModel.getOperationId(request.getParameter("action2"));
                //commandlist= deviceModel.getCommand(device_id1,op_id);
                   list= deviceModel.getBaudrate(q,device_id1,op_id);
//                  hm=deviceModel.getBaudrateByteValues(q, device_id, op_id);
//                    ArrayList<String> keyList = new ArrayList<String>(hm.keySet());
//                    for(String key: keyList){
//            System.out.println(key);
//          }

                    
                }
                else if(JQstring.equals("getComport")) {
                String  manu_name1 = request.getParameter("action3");
                String device_type1 = request.getParameter("action4");
                String   device_name1 = request.getParameter("action5");
                String  device_no1 = request.getParameter("action6");   
                int manufacture_id=deviceModel.getManufactureId(manu_name1);
                int device_type_id = deviceModel.getDeviceTypeId(device_type1);
                int model_id = deviceModel.getModelId(device_name1,device_no1);
                int  device_id1 = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id); 
                int op_id = deviceModel.getOperationId(request.getParameter("action2"));
                    list = deviceModel.getComport(q,device_id1,op_id);
                }
                
                
                
                
                
                
//                  else if(JQstring.equals("getMaskangle")) {
//                String  manu_name1 = request.getParameter("action3");
//                String device_type1 = request.getParameter("action4");
//                String   device_name1 = request.getParameter("action5");
//                String  device_no1 = request.getParameter("action6");   
//               int manufacture_id=deviceModel.getManufactureId(manu_name1);
//               int device_type_id = deviceModel.getDeviceTypeId(device_type1);
//                int model_id = deviceModel.getModelId(device_name1,device_no1);
//               int  device_id1 = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id); 
//                int op_id = deviceModel.getOperationId(request.getParameter("action2"));
//                    list = deviceModel.getMaskangle(q,device_id1,op_id);
//                }
                        
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                deviceModel.closeConnection();
                return;
            }
         catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        } 
         if(task.equalsIgnoreCase("connection")){
         manu_name = request.getParameter("manufacturer_name");
          device_type = request.getParameter("device_type_name");
           device_name = request.getParameter("device_name");
           device_no = request.getParameter("device_no");   
            DGPS_operation=request.getParameter("DGPS_operation");
            String  p_value = request.getParameter("p_value");
            String  in_value = request.getParameter("in_value");
           
            String in_value_arr[] = in_value.split(",");
           
            String p_value_arr[] = p_value.split(",");
         
            int length1 = in_value_arr.length;
       
          
          for(int len=0; len<length1; len++){
           String  inp_value = request.getParameter("va"+len);
              System.out.println(""+inp_value);
          }
          
          
             System.out.println("l    "+length1);
          int length = p_value_arr.length;
     
          
          for(int i = 0 ; i < length ; i++){
          String  sel_value = request.getParameter("v"+i);
              System.out.println("valuesss     .,.... "+sel_value);
          }
          
          HashMap<String,String> hash=new HashMap<>(); 
         
         for(int len=0; len<length1; len++){
           String  inp_value = request.getParameter("va"+len);
           int value1 = Integer.parseInt(inp_value);
            String Finalvalue = deviceModel.bytesToHex(DeviceRegModel.intToLittleEndian1(value1)).toUpperCase();
           hash.put(in_value_arr[len],Finalvalue);
            
          }

          for(int len=0; len<length; len++){
           String  sel_value = request.getParameter("v"+len);
           
           hash.put(p_value_arr[len],sel_value);
            
          }
             for (Map.Entry<String, String> entry : hash.entrySet()) {
                 String key = entry.getKey();
                 String value = entry.getValue();
                 System.out.println(key+ "        "+value);
             }
             
            
            
          int manufacture_id=deviceModel.getManufactureId(manu_name);
          int device_type_id = deviceModel.getDeviceTypeId(device_type);
         int model_id = deviceModel.getModelId(device_name,device_no);
         device_id = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id);   
           int op_id = deviceModel.getOperationId(DGPS_operation);
           commandlist= deviceModel.getCommand(device_id,op_id);
         //FINAL COMMAND
             finalcmdlist=deviceModel.editCommand(commandlist,hash);
             System.out.println("dddddddddddddd"+finalcmdlist);
           
           
           //sub_operation List
           String sub_operation=request.getParameter("sub_operation");
           
           //SUB OPERATION COMMAND
           
           List<String> opnbean=deviceModel.getSubOperationCommand(device_id,sub_operation);
             System.out.println("hello");
             
             
             
             //NEW FINAL LIST
              
               List<String>  cmdlist= new ArrayList<>(finalcmdlist.size() + opnbean.size());
               cmdlist.addAll(finalcmdlist);
               cmdlist.addAll(opnbean);
             System.out.println("size"+cmdlist.size());
              deviceModel.sendToMayankTesting(cmdlist);
              
            //String status = deviceModel.saveDeviceReg(device_type,manu_name,device_name,device_no);
         }
         
         if(task.equalsIgnoreCase("send")){
             int j = 0;
             String[] idsToModel = request.getParameterValues("isCheck");
             String[] idsToOperation = request.getParameterValues("isCheckOperation");
             String[] idsToCommand = request.getParameterValues("isCheckCommand");
            // String check = deviceModel.sendToShweta(idsToModel,idsToOperation,idsToCommand);
             //String check = deviceModel.sendToShwetaTesting(idsToModel,idsToOperation,idsToCommand,device_id);
             
             
//             String[] idsToModel = request.getParameterValues("isCheck");
//            for (int i = 0; i < idsToModel.length; i++) {
//                int model_id =  Integer.parseInt(idsToModel[i]);
//                String[] idsToOperation = request.getParameterValues("isCheckOperation");
//                
//                for ( j = 0; j < idsToOperation.length; j++) {
//                 int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
//                 if(model_id1 == model_id){
//    	         System.out.println(idsToOperation[j] + "<br>");
//                 }  
//                }
//                                  	        
//               }
         }
        String  op=null;
        
         if(task.equalsIgnoreCase("sub")) {
              
                 op = request.getParameter("DGPS_operation");
                String  subop = request.getParameter("sub_operation");
                
                System.out.println(subop+"                             qqqqqqqqqqqqqqqqqqqqqqq   oplist"+op);
                // String op1 = request.getParameter("sub_op");
               //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+op1);
              
                    String ischild = deviceModel.getSubChild(op,subop);
                     ///System.out.println("oplist"+oplist);
                    
                    JSONObject obj=new JSONObject();
                      JSONObject obj1=new JSONObject();
                      JSONArray js =new JSONArray();
                   //   obj.put("size", oplist.size());
                    // for(int i=0;i<oplist.size();i++){
                    //  obj.put("opname", oplist.get(i).getOperation_name());
                     // obj.put("id"+i, oplist.get(i).getOperation_name_id());
                      
                   // if(oplist.get(i).getRemark()!=null){
                          obj.put("ischild", ischild);
                    //  }else{
                        //  obj.put("ischild"+i, "OFF");
                      
//                    }
//                      }
                       js.add(obj);
                      
                               obj1.put("data1", js);
                               PrintWriter out1 = response.getWriter();
                               out1.print(obj1.toString());
                             return;
                }
         
         String subop="";
         if(task.equalsIgnoreCase("check1")) {
             subop = request.getParameter("sub_operation");
           // String subop1 = request.getParameter("sub_operation");
             manu_name = request.getParameter("manufacturer_name");
             device_type = request.getParameter("device_type_name");
             device_name = request.getParameter("device_name");
            device_no = request.getParameter("device_no");   
            DGPS_operation=request.getParameter("DGPS_operation");
         }
          
          String baudratevalue=request.getParameter("bytevlue");
         
         String baudratevalues=deviceModel.getBaudrateByteValues(baudratevalue);
         
       
            int size=0;
        if (task.equalsIgnoreCase("testing")){
             String mn=request.getParameter("mname");
             String devicetype=request.getParameter("devicetype");
              String devname=request.getParameter("device_name");  
              String dev_no=request.getParameter("device_no");
               DGPS_operation=request.getParameter("DGPS_operation");
             
              int manufacture_id=deviceModel.getManufactureId(mn);
               int device_type_id = deviceModel.getDeviceTypeId(devicetype);
                int model_id = deviceModel.getModelId(devname,dev_no);
               int  device_id123 = deviceModel.getDeviceId(manufacture_id,device_type_id,model_id); 
                int op_id = deviceModel.getOperationId(DGPS_operation);
           
                //get command list on the basis of device id and opn_id
                 List <SelectionValueBean> list1 =null;
                 List <SelectionValueBean> maskangle =null;
                 List <SelectionValueBean> inputvalues =null;
               
                  
                // SelectionValueBean s=new SelectionValueBean();
                // JSONObject obj1 = new JSONObject();    
                JSONObject obj3 = new JSONObject();    
                JSONObject obj2 = new JSONObject();
                 JSONObject objinput = new JSONObject();
                   JSONArray jsoninput = new JSONArray();
                 JSONArray jsonA = new JSONArray();
                 JSONArray jsonAValues = new JSONArray();
                 JSONArray jsonAValues1 = new JSONArray();
                list1 = deviceModel.getParametername(device_id123,op_id);
                
                  inputvalues = deviceModel.getInputVaues(device_id123,op_id);
                  
                  objinput.put("inpsize",inputvalues.size());
                  for(int iv=0;iv<inputvalues.size();iv++){
                  
                   objinput.put("input"+iv,inputvalues.get(iv).getParameter());
                  
                  }
                    
                jsoninput.add(objinput);
                 obj3.put("input", jsoninput);
                
                for(int k=0;k<list1.size();k++){
                    JSONObject objValues = new JSONObject();
                    JSONObject objValues1 = new JSONObject();
                    String pn=list1.get(k).getParameter();

                    maskangle = deviceModel.getdynamicvalues(device_id123,op_id,pn);
                    
                    System.out.println("sizeeeee"+maskangle.size());
                    
                       objValues1.put("s", maskangle.size());
                   for (int m = 0; m < maskangle.size(); m++) {
                       String val=pn+m;
                      
                    objValues.put("check"+m, maskangle.get(m).getDisplay_value());
                    objValues.put("byte"+m, maskangle.get(m).getByte_value());
                
                }
                    jsonAValues.add(objValues);
                    jsonAValues1.add(objValues1);
                }
                
                obj3.put("common", jsonAValues);
                obj3.put("size", jsonAValues1);
                // maskangle = deviceModel.getMaskangle(device_id123,op_id);
                //  JSONObject json =deviceModel.getParameternamebyjson(device_id123,op_id);
                // PrintWriter out123 = response.getWriter();
                //  out123.println(json);
                //  out123.flush();
                //   return;
           size=list1.size();
          
           obj2.put("A", size);
          // obj2.put("masksize", maskangle.size());
           
           obj2.put("B","akash");
        for (int i = 0; i < size; i++) {
                
             obj2.put("parameters"+i, list1.get(i).getParameter());
          }
        
//          for (int i = 0; i < maskangle.size(); i++) {
//                 
//              obj2.put("maskanglevalues"+i, maskangle.get(i).getDisplay_value());
//           }
//          
 
           jsonA.add(obj2);
           obj3.put("data", jsonA);
               PrintWriter out12 = response.getWriter();
                out12.print(obj3.toString());
                 return;
             
        } 
          List<DeviceRegistrationBean> commandTypeList = deviceModel.showData(device_id);
        System.out.println("color is :" + deviceModel.getMsgBgColor());
        request.setAttribute("manufacturer", manu_name);
        request.setAttribute("subop", subop);
        request.setAttribute("device_type", device_type);
        request.setAttribute("deviceName", device_name);
        request.setAttribute("device_no", device_no);
        request.setAttribute("bytevlue", baudratevalues);
        request.setAttribute("divisionTypeList", commandTypeList);
        request.setAttribute("commandlist", commandlist);
           request.setAttribute("DGPS_operation", DGPS_operation);
       request.setAttribute("size", size);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());        
        request.setAttribute("message", deviceModel.getMessage());
        request.setAttribute("msgBgColor", deviceModel.getMsgBgColor());
        
        request.getRequestDispatcher("/BaseRoverConfgPage").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
           // Logger.getLogger(DeviceRegController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
           // Logger.getLogger(DeviceRegController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

