/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.device.model;

import com.ble.command.bean.ByteDataBean;
import com.ble.command.bean.CommBean;
import com.ble.command.bean.CommandBean;
import com.ble.command.bean.SelectionValueBean;
import com.ble.dataEntry.bean.OperationNameBean;
import com.ble.device.bean.DeviceBean;
import com.ble.device.bean.DeviceRegistrationBean;
import com.ble.webService.controller.BLEWebServicesController;
//import static com.ble.webService.controller.BLEWebServicesController.deviceResponse;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Shobha
 */
public class DeviceRegModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static String isModuleOperation="";

    public void setConnection() {
        try {
            Class.forName(driverClass);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
        }
    }
   
    public String sendConnectionReq(String device_type){
    String status="";    
    //String reg_no_pass = saveDeviceReg(device_type);    
    SocketAddress socketaddress = new InetSocketAddress("192.168.1.38", 8090);
//    SocketAddress socketaddress = new InetSocketAddress("120.138.10.197", 8090);
    Socket socket = new Socket();
    int timeout = 4000;
    try{
    socket.connect(socketaddress, timeout);
    String result = "for to get lat lon";
    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
    outputStream.writeChars(result);
    //outputStream.close();
//    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//    byte[] bytes = new byte[30];   
//    inputStream.read(bytes);
//    getlatlon = new String(bytes); 
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
    String decodeString = reader.readLine();
    status = decodeString;
    System.out.println("total data ::  " + decodeString);       
   // inputStream.close();
    }
    catch(SocketTimeoutException exception){
    System.out.println("Socket Timeout Exception ::: " + exception);   
    }
    catch(IOException exception){
    System.out.println("Unable to connect Exception ::: " + exception);  
    }         
    return status;
    }
    
    public int getManufactureId(String manufactureName) {
      String query1="select id from manufacturer m "
                    +" where m.name=? "
                    +" and m.active='Y' ";
        int manufacturer_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, manufactureName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            manufacturer_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return manufacturer_id;
    }
    
    public String getLatestRegNo() {
      String query1="select reg_no from device_registration m "
                    +" where m.active='Y'"
                   + " order by reg_no desc limit 1";
                    
        String reg_no = "D_1";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);          
            ResultSet rs = stmt.executeQuery();
            rs.next();
            reg_no = rs.getString("reg_no");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return reg_no;
    }
    
    public int getDeviceTypeId(String deviceTypeName) {
      String query1="select id from device_type d "
                    +" where d.type=? "
                    +" and d.active='Y' ";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, deviceTypeName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_type_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_type_id;
    }
    
     public int getOperationId(String deviceTypeName) {
      String query1="select id from operation_name d "
                    +" where d.operation_name=? "
                    +" and d.active='Y' ";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, deviceTypeName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_type_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_type_id;
    }

     
       public int getSubOperationId(String deviceTypeName) {
      String query1="select id from operation_name d "
                    +" where d.operation_name=? "
                    +" and d.active='Y' ";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, deviceTypeName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_type_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_type_id;
    }

    public int getModelId(String device_name,String device_no) {
      String query1="select id from model m "
                    +" where m.device_name=? and m.device_no=? "
                    +" and m.active='Y' ";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1,device_name);
            stmt.setString(2,device_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            model_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return model_id;
    }
    public int getDeviceId(int manufacture_id,int device_type_id,int model_id) {
      String query1="select d.id from device d "
                    +" where d.manufacture_id=? "
                    +" and d.device_type_id=? "
                    +" and d.model_id=? "
                    +" and d.active='Y' ";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setInt(1,manufacture_id);
            stmt.setInt(2,device_type_id);
            stmt.setInt(3,model_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_id;
    }
    
    public String saveDeviceReg(String device_type,String manu_name,String device_name,String device_no){
    String status = "";
    int manufacture_id=getManufactureId(manu_name);
    int device_type_id = getDeviceTypeId(device_type);
    int model_id = getModelId(device_name,device_no);
    int device_id = getDeviceId(manufacture_id,device_type_id,model_id);
    String lat_re_no = getLatestRegNo();
    int update_reg_no = Integer.parseInt(lat_re_no.split("_")[1]) + 1;
    String reg_no1 = "D"+update_reg_no;
    Random random = new Random();        
    int rand_int1 = random.nextInt(100000);
    String pass = "P_"+rand_int1;
    String query = " insert into device_registration(device_id,reg_no,password,remark)"
                       +" values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,device_id);
            pstmt.setString(2,reg_no1);
            pstmt.setString(3,pass);
            pstmt.setString(4,"");
//            pstmt.setString(5,deviceRegistrationBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            saveDeviceAllocationStratus(rowsAffected);
            String status1 = "$$$$,04,0,6,"+device_id+","+pass+",120.138.10.146,8060,45.114.142.35,8060,12,####";
            sendConnectionReq(status1);
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }    
    return status;
    }
    
    public void saveDeviceAllocationStratus(int device_registration_id){
    String query = " insert into device_allocation_status(device_registration_id,is_allocate,remark)"
                       +" values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1,device_registration_id);
            pstmt.setString(2,"Yes");
            pstmt.setString(3,"");
//            pstmt.setString(4,deviceRegistrationBean.getSale_date());
//            pstmt.setString(5,deviceRegistrationBean.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {           
        } 
    }
    
    public List<String> getManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' "
                       +" group by name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
    
  public List<String> getDGPSoperation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name where active='Y' "
                       +" group by operation_name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("operation_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such operation_name  exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
    
  public List<String> getDeviceTypeName(String q,String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = "select type "
                       +" from device_type dt,device d,manufacturer m "
                       +" where d.manufacture_id = (select id from manufacturer m where m.name=? and m.active='Y') "
                       +" and d.device_type_id = dt.id "
                       +" and dt.active='Y' and d.active='Y' and m.active='Y' "
                       +" group by type ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, manufacturer_name);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("type");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device Type Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getDeviceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_name from model where active='Y' "
                       +" group by device_name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_name = rset.getString("device_name");
                if (device_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getDeviceNo(String q,String device_name) {
        List<String> list = new ArrayList<String>();
        String query = "select device_no from model where device_name=? "
                       +" and active='Y' "
                       +" group by device_no order by id desc ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, device_name);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_no = rset.getString("device_no");
                if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such DeviceNo  exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  
  public List<String> getSubOperation(String q,String operation_name,String subop) {
      int op_id =0;
      
      if(subop!="" && subop!=null){
           op_id = getOperationId(subop);
      }else
        
      {  
          op_id = getOperationId(operation_name);
      
      }
       
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name where parent_id=? "
                       +" and active='Y' "
                       +" group by operation_name order by id desc ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_no = rset.getString("operation_name");
                if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such SubOperation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  
   public List<String> getSubOperation12(String q) {
        List<String> list = new ArrayList<String>();
        
         int op_id = getOperationId(q);
        String query = "select operation_name from operation_name where parent_id="+op_id+" "
                       +" and active='Y' "
                       +" group by operation_name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            
            int count = 0;
           
             while (rset.next()) {
                String device_no = rset.getString("operation_name");
               
                    list.add(device_no);
                    count++;
                 
            }
            if (count == 0) {
                list.add("No such Sub Org Type Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
  
  
  
  public  List<String> getBaudrate(String q,int device_id, int op_id) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
         HashMap<String ,String> hm=new HashMap<>();
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? ORDER BY order_no ASC";
       // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
      //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT selection_id FROM selection where command_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            q = q.trim();
            while (rset1.next()) {
              String   selection_id = rset1.getString("selection_id");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(selection_id);                  
              //  }
            }
            
            
            joined2 = String. join(",", list1);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id and selection.selection_id IN ("+joined2+") and  parameter_name='Baudrate'");
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            q = q.trim();
            while (rset2.next()) {
              String   display_value = rset2.getString("display_value");     
                String   byte_value = rset2.getString("byte_value");  
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list2.add(display_value);      
                    hm.put(display_value, byte_value);
              //  }
            }
            
            
            
            
            if (count == 0) {
                list2.add("No such Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
         
        
        return list2;
        
    }
  
  public  String getBaudrateByteValues(String q) {
      //int op_id = getOperationId(operation_name);
       
        String bytevalue = null;
       
        String query="SELECT byte_value FROM ble_database6.selection_value where display_value='115200';";
            try {
                
                PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
          
            q = q.trim();
            while (rset2.next()) {
              
               
            }
          
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
         
        
        return bytevalue;
        
    }
  
  public List<String> getComport(String q,int device_id, int op_id) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? ORDER BY order_no ASC";
       // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
      //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT selection_id FROM selection where command_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            q = q.trim();
            while (rset1.next()) {
              String   selection_id = rset1.getString("selection_id");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(selection_id);                  
              //  }
            }
            
            
            joined2 = String. join(",", list1);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id and selection.selection_id IN ("+joined2+")and  parameter_name='COMPORT'");
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            q = q.trim();
            while (rset2.next()) {
              String   display_value = rset2.getString("display_value");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list2.add(display_value);                  
              //  }
            }
            
            
            
            
            if (count == 0) {
                list2.add("No such Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        return list2;
    }
  
   public List<String> getCommand(int device_id, int op_id) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
       
        List<String> list2 = new ArrayList<String>();
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? and device_command_map.active='Y' ORDER BY order_no ASC";
       // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
      //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
          
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT distinct command FROM command where id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
           
            while (rset1.next()) {
                 String   cmd = rset1.getString("command");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
              // CommandBean cb=new CommandBean();
             //  cb.setCommand(cmd);              
               list2.add(cmd);
            }
            
         
            
            
            
            
            
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        return list2;
    }
   
    
   
 public static byte[] intToLittleEndian1(long numero) {
byte[] b = new byte[4];
b[0] = (byte) (numero & 0xFF);
b[1] = (byte) ((numero >> 8) & 0xFF);
b[2] = (byte) ((numero >> 16) & 0xFF);
b[3] = (byte) ((numero >> 24) & 0xFF);
return b;
}

public static String bytesToHex(byte[] in) {
final StringBuilder builder = new StringBuilder();
for (byte b : in) {
builder.append(String.format("%02x", b));
}
return builder.toString();
}
   
  public List<String> editCommand(List<String> commandsfromlist,HashMap<String,String> hs) {
  List<String>  newCommandList=new ArrayList<>();
    int i = 1;
int[] index = new int[3];
for (String command : commandsfromlist) {

int index1 = command.indexOf('/');

while (index1 >= 0) {
System.out.println(index1);
index[i] = index1;
index1 = command.indexOf('/', index1 + 1);


if (i == 2) {
String key = command.substring(index[1] + 1, index[2]);
if (key.equals("CRC")) {
String checksum = checksum(command.substring(4, index[1]));
String actualKey = command.substring(index[1], index[2] + 1);
command = command.replace(actualKey, checksum);
i = 1;
index = new int[3];

break;
}
String actualKey = command.substring(index[1], index[2] + 1);
String value = hs.get(key);
command = command.replace(actualKey, value);

i = 1;
index = new int[3];
index1 = command.indexOf('/');
} else {
i++;
}


}
newCommandList.add(command);

}
return newCommandList;
   }
  
  
  
  /*THIS METHOD IS USED FOR CHECKSUM VALUE OF COMMAND*/
public String checksum(String command) {
String[] commandPair = new String[(command.length() / 2) + 1];
int j = 0;
int size = command.length();
for (int i = 0; i < size; i += 2) {
commandPair[j] = command.substring(i, i + 2);
j++;
}
String ch_A = "0";
String ch_B = "0";
int length = commandPair.length - 1;
for (int i = 0; i < length; i++) {
ch_A = addCheckSum(ch_A, commandPair[i]);
ch_B = addCheckSum(ch_B, ch_A);
}
ch_A = Integer.toHexString(Integer.parseInt(ch_A, 16) & 0xFF).toUpperCase();
ch_B = Integer.toHexString(Integer.parseInt(ch_B, 16) & 0xFF).toUpperCase();
if (ch_A.length() == 1) {
ch_A = "0" + ch_A;
}
if (ch_B.length() == 1) {
ch_B = "0" + ch_B;
}
return ch_A + ch_B;
}





public String addCheckSum(String ch_A, String ch_B) {
int A = Integer.parseInt(ch_A, 16);
int B = Integer.parseInt(ch_B, 16);
int sum = A + B;
return Integer.toHexString(sum);

}
   
   public List<String> getSubOperationCommand(int device_id, String op_name) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
         List<String> list2 = new ArrayList<String>();
        
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "select id from operation_name where operation_name=? and active='Y' order by id desc  ";
        // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
        //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, op_name);
          //  pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
          
            while (rset.next()) {
              String op_id = rset.getString("id");
             
               
                    list1.add(op_id);
                    count++;
              
            }
            joined = String. join(",", list1);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT command_id FROM device_command_map where operation_id in("+joined+") AND device_id=? and device_command_map.active='Y' ORDER BY order_no ASC");
           
            pstmt1.setInt(1, device_id);
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
           
            while (rset1.next()) {
                 String id = rset1.getString("command_id");    
                 
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                           
               list2.add(id);
            }
            
             joined2 = String. join(",", list2);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct command FROM command where id IN("+joined2+")");
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
           
            while (rset2.next()) {
                 String cmd = rset2.getString("command");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                            
               list.add(cmd);
            }
            
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        return list;
    }
  
  
  
  
  
  
//  public List<SelectionValueBean> getMaskangle(int device_id, int op_id) {
//      //int op_id = getOperationId(operation_name);
//        List<String> list = new ArrayList<String>();
//        List<String> list1 = new ArrayList<String>();
//        List<SelectionValueBean> list2 = new ArrayList<SelectionValueBean>();
//        String joined ="";
//        String joined2 ="";
////        String query = "select operation_name from operation_name where parent_id=? "
////                       +" and active='Y' "
////                       +" group by operation_name order by id desc ";
//        
//        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? ORDER BY order_no ASC";
//        // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
//        //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
//        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
//        //                  " and selection.selection_id IN ("+joined2+")";
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//            pstmt.setInt(1, op_id);
//            pstmt.setInt(2, device_id);
//            ResultSet rset = pstmt.executeQuery();
//            //ResultSet rset = connection.prepareStatement(query).executeQuery();
//
//            int count = 0;
//            
//            while (rset.next()) {
//              String command_id = rset.getString("command_id");
//             
//               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(command_id);
//                    count++;
//              //  }
//            }
//            joined = String. join(",", list);
//            
//            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT selection_id FROM selection where command_id IN ("+joined+")");
//            ResultSet rset1 = pstmt1.executeQuery();
//            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
//            
//            while (rset1.next()) {
//              String   selection_id = rset1.getString("selection_id");             
//               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
//                    list1.add(selection_id);                  
//              //  }
//            }
//            
//            
//            joined2 = String. join(",", list1);
//            
//            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id and selection.selection_id IN ("+joined2+")and  parameter_name='mask angle'");
//            ResultSet rset2 = pstmt2.executeQuery();
//            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
//            
//            while (rset2.next()) {
//              SelectionValueBean sb=new SelectionValueBean();
//                String   display_value = rset2.getString("display_value");
//              sb.setDisplay_value(display_value);
//               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
//                    list2.add(sb);                  
//              //  }
//            }
//            
//            
//            
//            
//            if (count == 0) {
//            //    list2.add("No such Name exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println(" ERROR inside CommandModel - " + e);
//            message = "Something going wrong";
//            //messageBGColor = "red";
//        }
//        
//        
//        
//        
//        return list2;
//    }
//  
  
  
  
   public List<SelectionValueBean> getInputVaues(int device_id, int op_id) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        List<SelectionValueBean> list2 = new ArrayList<SelectionValueBean>();
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? ORDER BY order_no ASC";
        // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
        //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT parameter_id FROM input where command_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset1.next()) {
              String   parameter_id = rset1.getString("parameter_id");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(parameter_id);                  
              //  }
            }
            
            
            joined2 = String. join(",", list1);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT parameter_name FROM parameter where parameter.parameter_id IN ("+joined2+") and parameter.active='Y'");
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset2.next()) {
              SelectionValueBean sb=new SelectionValueBean();
                String   pname = rset2.getString("parameter_name");
              sb.setParameter(pname);
              
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list2.add(sb);                  
              //  }
            }
            
            
            
            
            if (count == 0) {
            //    list2.add("No such Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        
        
        
        return list2;
    }
  
  
  
  
  
   public List<SelectionValueBean> getdynamicvalues(int device_id, int op_id ,String pname) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        List<SelectionValueBean> list2 = new ArrayList<SelectionValueBean>();
        String joined ="";
        String joined2 ="";
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? ORDER BY order_no ASC";
        // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
        //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT selection_id FROM selection where command_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset1.next()) {
              String   selection_id = rset1.getString("selection_id");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(selection_id);                  
              //  }
            }
            
            
            joined2 = String. join(",", list1);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id and selection.selection_id IN ("+joined2+")and  parameter_name=?");
           
             pstmt2.setString(1, pname);
            ResultSet rset2 = pstmt2.executeQuery();
            
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset2.next()) {
              SelectionValueBean sb=new SelectionValueBean();
                String   display_value = rset2.getString("display_value");
                String   byte_value = rset2.getString("byte_value");
               sb.setDisplay_value(display_value);
               sb.setByte_value(byte_value);
               
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list2.add(sb);                  
              //  }
            }
            
            
            
            
            if (count == 0) {
            //    list2.add("No such Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        
        
        
        return list2;
    }
  
  
  public List<SelectionValueBean> getParametername(int device_id, int op_id) {
      //int op_id = getOperationId(operation_name);
        List<String> list = new ArrayList<String>();
        List<String> list1 = new ArrayList<String>();
        List<SelectionValueBean> list2 = new ArrayList<SelectionValueBean>();
        String joined ="";
        String joined2 ="";
       
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
        String query = "SELECT command_id FROM device_command_map where operation_id=? AND device_id=? and device_command_map.active='Y' ORDER BY order_no ASC";
       // String query1 = "SELECT selection_id FROM selection where command_id IN ("+joined+")";
      //  String query2 = "SELECT distinct parameter_name,display_value,byte_value FROM selection, parameter, selection_value" +
        //                " where selection.parameter_id = parameter.parameter_id and selection.selection_id = selection_value.selection_id " +
        //                  " and selection.selection_id IN ("+joined2+")";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, op_id);
            pstmt.setInt(2, device_id);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
          
            while (rset.next()) {
              String command_id = rset.getString("command_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command_id);
                    count++;
              //  }
            }
            joined = String. join(",", list);
            
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("SELECT selection_id FROM selection where command_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
           
            while (rset1.next()) {
              String   selection_id = rset1.getString("selection_id");             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(selection_id);                  
              //  }
            }
            
            
            joined2 = String. join(",", list1);
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement("SELECT distinct parameter_name FROM selection, parameter, selection_value where selection.parameter_id = parameter.parameter_id and selection.selection_id IN ("+joined2+")and parameter.active='Y'");
            ResultSet rset2 = pstmt2.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset2.next()) {
                SelectionValueBean sb=new SelectionValueBean();
               String   Parameter = rset2.getString("parameter_name");  
                 
                sb.setParameter(Parameter);
                
            list2.add(sb);                  
              
            }
            
            
            
            
            
            
            
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        
        
        
        return list2;
    }
  
   
  
   public int getopId(String opname, String subname) {
      String query1="select id from operation_name d "
                    +" where d.operation_name=? "
                    +" and d.active='Y' ";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, opname);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_type_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_type_id;
    }
   
  public String getSubChild(String opname, String subname) {
       int op_id = getOperationId(opname);
        
      String  superchild=null;
      
//        String query = "select operation_name from operation_name where parent_id=? "
//                       +" and active='Y' "
//                       +" group by operation_name order by id desc ";
        
       
        try {
             
            
            PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement(" SELECT id,operation_name,is_super_child FROM operation_name where operation_name.operation_name=? and parent_id=?");
            pstmt2.setString(1,subname);
            pstmt2.setInt(2, op_id);
            ResultSet rset2 = pstmt2.executeQuery();
            
            //ResultSet rset = connection.prepareStatement(query).executeQuery();          
            
            while (rset2.next()) {
                
             
                 superchild = rset2.getString("is_super_child");  
                 
                 
                 
                
                           
              
            }
            
           
            
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        
        
        
        
        return superchild;
    }
  
  
  
  
  
  public List<DeviceRegistrationBean> showData(int device_id) {
        List<DeviceRegistrationBean> list = new ArrayList<DeviceRegistrationBean>();                       
        List<DeviceRegistrationBean> list1 = null;                       
     //  String query2= " select id,device_name from model where active='Y'";
       String query2= " select m.id,d.id as device_id,m.device_name,dt.id as device_type_id,dt.type as device_type from device_map as map,device as d , model as m , device_type as dt"
               + "  where map.finished_device_id='"+ device_id +"' and map.module_device_id = d.id and d.model_id = m.id and d.device_type_id = dt.id and dt.active='Y' and d.active='Y' and map.active='Y' and m.active='Y'";  
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);         
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceRegistrationBean deviceBean = new DeviceRegistrationBean(); 
                int id = rset.getInt("device_id");
                deviceBean.setDevice_registration_id(rset.getInt("id"));                
                deviceBean.setDevice_type_id(rset.getInt("device_type_id"));                
                deviceBean.setDevice_name(rset.getString("device_name"));
                List<DeviceRegistrationBean> operationList = showOffenceData(id);                
                deviceBean.setDeviceregBean(operationList);
                
                list.add(deviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }        
         
        
        return list;
    }
  
  public List<DeviceRegistrationBean> showOffenceData(int model_id) {
        List<DeviceRegistrationBean> list = new ArrayList<DeviceRegistrationBean>();
        String query;
        try {
//            query = "select op.id,op.operation_name from device_command_map as dcm , device as d , model as m , operation_name as op " +
//                    " where m.id = d.model_id and d.id = dcm.device_id and dcm.operation_id = op.id and dcm.active='Y' and d.active='Y' "
//                    + " and m.active='Y' and op.active='Y' and m.id='" + model_id + "'";
          query = "  select dcm.device_command_id,opn.id,"
                + " dcm.device_id,dcm.remark,dcm.delay,opn.operation_name,mf.name,m.device_name,m.device_no,dt.type"
                + " from device_command_map dcm,device d,operation_name opn ,manufacturer mf,model m,device_type dt "
                + " where dcm.device_id=d.id  and dcm.operation_id=opn.id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + model_id + "'"
		+ " and dcm.active='Y' and d.active='Y' and opn.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y' group by (opn.operation_name) order by (opn.id) asc";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceRegistrationBean drb = new DeviceRegistrationBean();
                int id = rset.getInt("id");
                drb.setOperation_id(rset.getInt("id"));
                drb.setOperation_name(rset.getString("operation_name"));  
                List<DeviceRegistrationBean> commandList = showOffenceData1(id,model_id);
                drb.setCommandListBean(commandList);
                list.add(drb);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }
  public List<DeviceRegistrationBean> showOffenceData1(int operation_id,int device_id) {
        List<DeviceRegistrationBean> list = new ArrayList<DeviceRegistrationBean>();
        String query;
        try {
//            query = "select c.id,c.command from device_command_map as map, operation_name as op , command as c" +
//                    " where map.operation_id = op.id and map.command_id = c.id and op.id ='" + operation_id + "'";
             query = " select dcm.device_command_id,c.id," +
                     " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type" +
                     " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt " +
                     " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + device_id + "' and opn.id='" + operation_id + "'" +
                     " and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceRegistrationBean drb = new DeviceRegistrationBean();
                int id = rset.getInt("id");
                drb.setCommand_id(rset.getInt("id"));
                drb.setCommand(rset.getString("command"));                  
                drb.setOrder_no(rset.getString("order_no"));                  
                drb.setDelay(rset.getString("delay"));                  
                list.add(drb);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }
  
  public String sendToShwetaTesting(String arr[],String arrOperation[],String arrCommand[],int finished_id) throws InterruptedException{
  String status = "check";
  boolean user = true;
  boolean user1 = true;
  BLEWebServicesController ble = new BLEWebServicesController();
  int j  = 0;
  int k = 0;
  int a = 0;
 
              try {      
                  while(user1 == true){
                isModuleOperation = "$$$$,07,D_2,01,startpacket,12,####";
                 String startpacket=ble.deviceResponse;
                 if(startpacket==null){
                    startpacket = "";
                   }else{
                     System.out.println("mayank packet data .. " +startpacket );    
                int x1 = startpacket.indexOf('$');
                if(x1 >= 0){
                startpacket =  startpacket.split(",")[4];
                }
                 }
                String startpacket1 =  isModuleOperation.split(",")[4];
                if(startpacket.equalsIgnoreCase(startpacket1)){
                user = false;
                isModuleOperation = "end of file";
                ble.deviceResponse = null;
                }
                 if(user == false){
                 
 
                for (int i = 0; i < arr.length; i++) {
                isModuleOperation = "end of file";
                int model_id =  Integer.parseInt(arr[i].split(",")[0]);
                int device_type_id =  Integer.parseInt(arr[i].split(",")[1]);
                String[] idsToOperation = arrOperation;                
                for ( j = 0; j < idsToOperation.length; j++) {                    
                 int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
                 if(model_id1 == model_id){
                    for(k=0; k < arrCommand.length ; k++){
                        int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
                        int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);                        
                          if(command_id1 == op_id){
                            long startTime = System.currentTimeMillis();
                            while(true ||(System.currentTimeMillis()-startTime)<20000){
                            String deviceResp =  ble.deviceResponse;                            
                            String abc1="start";
                            if(deviceResp==null){
                            deviceResp = "";
                            }else{
                             
                                int x = deviceResp.indexOf('$');
                              if(x >= 0){
                                  String  cmd = deviceResp.split(",")[1];
                                  if(!cmd.equalsIgnoreCase("07")){
                                    abc1 = deviceResp.split(",")[9];
                                  }
                              }
                            }
                           //  System.out.println("device resp ... "+deviceResp + "<br>");
                              String abc="";
                              String preSavedValue="";                              
                             if(isModuleOperation==null || isModuleOperation.equalsIgnoreCase("end of file")){
                            isModuleOperation = "end of file";
                            }else{
                                 preSavedValue=isModuleOperation;
                              abc = isModuleOperation.split(",")[9];
                             }
                           
                            if(abc1.equalsIgnoreCase(abc)){
                           // isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+arrCommand[k].split(",")[2]+","+arrCommand[k].split(",")[3]+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
                           
                           // int re = insertDeviceConfigurationStatus(finished_id,model_id,Integer.parseInt(idsToOperation[j].split(",")[0]),Integer.parseInt(arrCommand[k].split(",")[2]),arrCommand[k].split(",")[0]);
                            System.out.println(" matched  and  curent value is .. " +isModuleOperation+" and record updated status is ..." );
                            isModuleOperation = null;
                            //Thread.sleep(2 * 1000);
                            //a = 0;
                            break;
                            }
                            else{
                             if(k == (arrCommand.length) -1){
                             isModuleOperation = isModuleOperation;
                             } else{
                           
                            isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+Integer.parseInt(arrCommand[k].split(",")[2])+","+Integer.parseInt(arrCommand[k].split(",")[3])+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
                            //isModuleOperation = isModuleOperation;
                            System.out.println("previous value .. " +isModuleOperation );      
                            //Thread.sleep(2 * 1000);
                            a = 0;
                             }
                             //a = 0;
                           // break;
                          }
                           
                            }
                           //Thread.sleep(4 * 1000);
                                                   
                        }
                       
                   
                    }
              k=0;
//                 isModuleOperation = "$$$$,05,23,5,"+model_id+","+idsToOperation[j].split(",")[0]+",1,2,123,robinsingh,23,####";
//                 System.out.println(isModuleOperation + "<br>");
                 }
               //  Thread.sleep(10 * 1000);
                }
                 j=0;                        
               }
                user1 = false;
              }
                  //user1 = false;
              }
                 // user1 = false;
              }
              catch (Exception e){
              System.out.println("some error .... "+ e);
              }
                isModuleOperation = "end of file";
               //isOperationArr = arr;
                return status;
  }
  
  
   public void sendToMayankTesting(List<String>  cmdlist) throws InterruptedException{
  String status = "check";
  boolean user = true;
  boolean user1 = true;
  BLEWebServicesController ble = new BLEWebServicesController();
  int j  = 0;
  int k = 0;
  int a = 0;           
                        for(k=0; k < cmdlist.size() ; k++){
                               long startTime = System.currentTimeMillis();
                              while(true && (System.currentTimeMillis()-startTime)<20000){ 
                              String deviceResp =  ble.deviceResponse;
                            if(isModuleOperation.equalsIgnoreCase(deviceResp)){
                           // isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+arrCommand[k].split(",")[2]+","+arrCommand[k].split(",")[3]+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
                           
                           // int re = insertDeviceConfigurationStatus(finished_id,model_id,Integer.parseInt(idsToOperation[j].split(",")[0]),Integer.parseInt(arrCommand[k].split(",")[2]),arrCommand[k].split(",")[0]);
                            System.out.println(" matched  and  curent value is .. " +isModuleOperation+" and record updated status is ..." );
                            isModuleOperation = null;
                            //Thread.sleep(2 * 1000);
                            //a = 0;
                            break;
                            }
                            else{
//                             if(k == (arrCommand.length) -1){
//                             isModuleOperation = isModuleOperation;
//                             } else{
                           
                            isModuleOperation = cmdlist.get(k);
                            //isModuleOperation = isModuleOperation;
                            System.out.println("previous value .. " +isModuleOperation );      
                            
                           
                             //}
                             
                          }
                           
                            }
                              
                           
                        }
                         isModuleOperation = "end of file";
                                                             
   }
   
   
   
   
   
//  public String sendToShweta(String arr[],String arrOperation[],String arrCommand[]) throws InterruptedException{
//  String status = "check";
//  BLEWebServicesController ble = new BLEWebServicesController();
//  int j  = 0;
//  int k = 0;
//               try {
//                                                                                          
//                for (int i = 0; i < arr.length; i++) {
//                isModuleOperation = "end of file";
//                int model_id =  Integer.parseInt(arr[i].split(",")[0]);
//                int device_type_id =  Integer.parseInt(arr[i].split(",")[1]);
//                String[] idsToOperation = arrOperation;                
//                for ( j = 0; j < idsToOperation.length; j++) {                    
//                 int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
//                 if(model_id1 == model_id){
//                    for(k=0; k < arrCommand.length ; k++){
//                        int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
//                        int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);                        
//                          if(command_id1 == op_id){
//                            
//                            for(int a = 0 ; a < 5 ; a++){
//                            String deviceResp =  ble.deviceResponse;                            
//                            String abc1="start";
//                            if(deviceResp==null){
//                            deviceResp = "";
//                            }else{
//                                int x = deviceResp.indexOf('$');
//                              if(x >= 0){
//                              abc1 = deviceResp.split(",")[9];
//                              }
//                            }
//                           //  System.out.println("device resp ... "+deviceResp + "<br>");
//                              String abc="";
//                              String preSavedValue="";                               
//                             if(isModuleOperation==null || isModuleOperation.equalsIgnoreCase("end of file")){
//                            isModuleOperation = "end of file";
//                            }else{
//                                 preSavedValue=isModuleOperation;
//                              abc = isModuleOperation.split(",")[9];
//                             }
//                            
//                            if(abc1.equalsIgnoreCase(abc)){
//                            isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+arrCommand[k].split(",")[2]+","+arrCommand[k].split(",")[3]+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
//                            System.out.println(" matched  and  curent value is .. " +isModuleOperation ); 
//                            Thread.sleep(2 * 1000);
//                            //a = 0;
//                           // break;
//                            }
//                            else{
//                             if(k == (arrCommand.length) -1){
//                             isModuleOperation = isModuleOperation;
//                             } else{ 
//                            
//                            isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+Integer.parseInt(arrCommand[k].split(",")[2])+","+Integer.parseInt(arrCommand[k].split(",")[3])+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
//                            System.out.println("previous value .. " +isModuleOperation );      
//                            Thread.sleep(2 * 1000); 
//                             }
//                             //a = 0;
//                           // break;
//                          }
//                            
//                            }
//                           //Thread.sleep(4 * 1000);
//                                                    
//                        }
//                        
//                    
//                    } 
//    	           k=0;
////                 isModuleOperation = "$$$$,05,23,5,"+model_id+","+idsToOperation[j].split(",")[0]+",1,2,123,robinsingh,23,####";
////                 System.out.println(isModuleOperation + "<br>");
//                 }
//               //  Thread.sleep(10 * 1000);
//                }
//                 j=0;                 	        
//               }
//               } catch (Exception e){
//               System.out.println("some error .... "+ e);
//               }
//                isModuleOperation = "end of file";
//               //isOperationArr = arr;
//                return status;
//  }
//  
//  public String sendToShwetaTesting(String arr[],String arrOperation[],String arrCommand[],int finished_id) throws InterruptedException{
//  String status = "check";
//  boolean user = true;
//  boolean user1 = true;
//  BLEWebServicesController ble = new BLEWebServicesController();
//  int j  = 0;
//  int k = 0;
//  int a = 0;
//  
//              try {       
//                  while(user1 == true){
//                isModuleOperation = "$$$$,07,D_2,01,startpacket,12,####";
//                 String startpacket=ble.deviceResponse;
//                 if(startpacket==null){
//                    startpacket = "";
//                   }else{
//                     System.out.println("mayank packet data .. " +startpacket );    
//                int x1 = startpacket.indexOf('$');
//                if(x1 >= 0){
//                startpacket =  startpacket.split(",")[4]; 
//                } 
//                 }
//                String startpacket1 =  isModuleOperation.split(",")[4]; 
//                if(startpacket.equalsIgnoreCase(startpacket1)){
//                user = false;
//                isModuleOperation = "end of file";
//                ble.deviceResponse = null;
//                }
//                 if(user == false){ 
//                  
//  
//                for (int i = 0; i < arr.length; i++) {
//                isModuleOperation = "end of file";
//                int model_id =  Integer.parseInt(arr[i].split(",")[0]);
//                int device_type_id =  Integer.parseInt(arr[i].split(",")[1]);
//                String[] idsToOperation = arrOperation;                
//                for ( j = 0; j < idsToOperation.length; j++) {                    
//                 int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
//                 if(model_id1 == model_id){
//                    for(k=0; k < arrCommand.length ; k++){
//                        int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
//                        int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);                        
//                          if(command_id1 == op_id){
//                            long startTime = System.currentTimeMillis();
//                            while(true ||(System.currentTimeMillis()-startTime)<20000){
//                            String deviceResp =  ble.deviceResponse;                            
//                            String abc1="start";
//                            if(deviceResp==null){
//                            deviceResp = "";
//                            }else{
//                              
//                                int x = deviceResp.indexOf('$');
//                              if(x >= 0){
//                                  String  cmd = deviceResp.split(",")[1];
//                                  if(!cmd.equalsIgnoreCase("07")){
//                                    abc1 = deviceResp.split(",")[9];
//                                  }
//                              }
//                            }
//                           //  System.out.println("device resp ... "+deviceResp + "<br>");
//                              String abc="";
//                              String preSavedValue="";                               
//                             if(isModuleOperation==null || isModuleOperation.equalsIgnoreCase("end of file")){
//                            isModuleOperation = "end of file";
//                            }else{
//                                 preSavedValue=isModuleOperation;
//                              abc = isModuleOperation.split(",")[9];
//                             }
//                            
//                            if(abc1.equalsIgnoreCase(abc)){
//                           // isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+arrCommand[k].split(",")[2]+","+arrCommand[k].split(",")[3]+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
//                            
//                            int re = insertDeviceConfigurationStatus(finished_id,model_id,Integer.parseInt(idsToOperation[j].split(",")[0]),Integer.parseInt(arrCommand[k].split(",")[2]),arrCommand[k].split(",")[0]);
//                            System.out.println(" matched  and  curent value is .. " +isModuleOperation+" and record updated status is ..."+re ); 
//                            isModuleOperation = null;
//                            //Thread.sleep(2 * 1000);
//                            //a = 0;
//                            break;
//                            }
//                            else{
//                             if(k == (arrCommand.length) -1){
//                             isModuleOperation = isModuleOperation;
//                             } else{ 
//                            
//                            isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+Integer.parseInt(arrCommand[k].split(",")[2])+","+Integer.parseInt(arrCommand[k].split(",")[3])+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
//                            //isModuleOperation = isModuleOperation;
//                            System.out.println("previous value .. " +isModuleOperation );      
//                            //Thread.sleep(2 * 1000); 
//                            a = 0;
//                             }
//                             //a = 0;
//                           // break;
//                          }
//                            
//                            }
//                           //Thread.sleep(4 * 1000);
//                                                    
//                        }
//                        
//                    
//                    } 
//    	           k=0;
////                 isModuleOperation = "$$$$,05,23,5,"+model_id+","+idsToOperation[j].split(",")[0]+",1,2,123,robinsingh,23,####";
////                 System.out.println(isModuleOperation + "<br>");
//                 }
//               //  Thread.sleep(10 * 1000);
//                }
//                 j=0;                 	        
//               }
//                user1 = false;
//              } 
//                  //user1 = false;
//              }
//                 // user1 = false;
//              }
//              catch (Exception e){
//              System.out.println("some error .... "+ e);
//              }
//                isModuleOperation = "end of file";
//               //isOperationArr = arr;
//                return status;
//  }
//  
//  public int insertDeviceConfigurationStatus(int finished_id,int model_id , int op_id, int order_no, String command) {
//                
//        String query = " insert into deviceconfigstatus(finished_device_id,model_id,operation_id,order_no,command,status) "
//                       +" values(?,?,?,?,?,?) ";
//        int rowsAffected = 0;
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, finished_id);
//            pstmt.setInt(2, model_id);
//            pstmt.setInt(3, op_id);            
//            pstmt.setInt(4, order_no);
//            pstmt.setString(5, command);
//            pstmt.setString(6, "yes");
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//
//    }
//    
   public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }


}
