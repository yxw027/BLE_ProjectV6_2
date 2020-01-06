/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.webService.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Shobha
 */
public class BLEWebServicesModel {


    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public JSONArray getManufacturerRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,remark "
                +" from manufacturer m "
               +" where m.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("name",rset.getString("name"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getDeviceTypeRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,type,remark "
                +" from device_type dt "
                +" where dt.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("type",rset.getString("type"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getModelTypeRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,type,remark "
                +" from modal_type mt "
                +" where mt.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("type",rset.getString("type"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getModelRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_name,device_no,warranty_period,remark,model_type_id,device_address "
                +" from model m "
                +" where m.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("device_name",rset.getString("device_name"));
                 obj.put("device_no",rset.getString("device_no"));
                 obj.put("warranty_period",rset.getString("warranty_period"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("model_type_id",rset.getInt("model_type_id"));
                 obj.put("device_address",rset.getString("device_address"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getCommandTypeRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,remark "
               +" from command_type ct "
               +" where ct.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("name",rset.getString("name"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getOperationNameRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,operation_name,remark "
                +" from operation_name op_n "
                +" where op_n.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("operation_name",rset.getString("operation_name"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getDeviceRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,manufacture_id,device_type_id,model_id,remark "
                +" from device d "
                +" where d.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("manufacture_id",rset.getInt("manufacture_id"));
                 obj.put("device_type_id",rset.getInt("device_type_id"));
                 obj.put("model_id",rset.getInt("model_id"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getDeviceRegistrationRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_id,reg_no,manufacture_date,sale_date,remark "
               +" from device_registration dr "
               +" where dr.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("device_id",rset.getInt("device_id"));
                 obj.put("reg_no",rset.getInt("reg_no"));
                 obj.put("manufacture_date",rset.getString("manufacture_date"));
                 obj.put("date2",rset.getString("date2"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getServiceRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,service_name,service_uuid,device_id,remark "
                +" from servicies s "
                +" where s.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("service_name",rset.getString("service_name"));
                 obj.put("service_uuid",rset.getString("service_uuid"));
                 obj.put("device_id",rset.getString("device_id"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getCharachtersticRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,service_id,uuid,remark "
                +" from charachtristics c "
                +" where c.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("name",rset.getString("name"));
                 obj.put("service_id",rset.getInt("service_id"));
                 obj.put("uuid",rset.getString("uuid"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getCommandRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,command,starting_del,end_del,remark,command_type_id,selection,input "
                +" from command c "
                +" where c.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 String command = rset.getString("command");
                 obj.put("command", command);
                 obj.put("starting_del",rset.getString("starting_del"));
                 obj.put("end_del",rset.getString("end_del"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("command_type_id",rset.getInt("command_type_id"));
                 obj.put("selection",rset.getInt("selection"));
                 obj.put("input",rset.getInt("input"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    public JSONArray getCommandDeviceMapRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select device_command_id,order_no,delay,device_id,command_id,operation_id,remark"
                +" from device_command_map c "
                +" where c.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("device_command_id"));                
                 obj.put("device_id",rset.getInt("device_id"));                 
                 obj.put("command_id",rset.getInt("command_id"));
                 obj.put("operation_id",rset.getInt("operation_id"));
                 obj.put("order_no",rset.getInt("order_no"));
                 obj.put("delay",rset.getInt("delay"));
                 obj.put("remark",rset.getString("remark"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    
    public JSONArray getRuleRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,command_id,description,remark "
                +" from rule r "
                +" where r.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("id",rset.getInt("id"));
                 obj.put("command_id",rset.getInt("command_id"));
                 obj.put("description",rset.getString("description"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getBleOperationNameRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select ble_operation_name_id,ble_operation_name,remark "
                +" from ble_operation_name bop "
                +" where bop.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("ble_operation_name_id",rset.getInt("ble_operation_name_id"));
                 obj.put("ble_operation_name",rset.getString("ble_operation_name"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

//    public JSONArray getDeviceCharacteristicBleMapRecord()
//        {
//        JSONArray rowData = new JSONArray();
//        String query = null;
//        query = " select device_characteristic_ble_map_id,device_id,read_characteristic_id,ble_operation_name_id,write_characteristic_id, "
//                +" order_no,remark "
//                +" from device_characteristic_ble_map d "
//                +" where d.active='Y' ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                JSONObject obj = new JSONObject();
//                 obj.put("device_characteristic_ble_map_id",rset.getInt("device_characteristic_ble_map_id"));
//                 obj.put("device_id",rset.getInt("device_id"));
//                 obj.put("read_characteristic_id",rset.getInt("read_characteristic_id"));
//                 obj.put("write_characteristic_id",rset.getInt("write_characteristic_id"));
//                 obj.put("ble_operation_name_id",rset.getInt("ble_operation_name_id"));
//                 obj.put("order_no",rset.getString("order_no"));
//                 obj.put("remark",rset.getString("remark"));
//
//                 rowData.add(obj);
//           }
//        } catch (Exception e) {
//            System.out.println("Error inside show data of survey: " + e);
//        }
//        return rowData;
//    }
    
    public JSONArray getDeviceCharacteristicBleMapRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select device_characteristic_ble_map_id,device_id,read_characteristic_id,ble_operation_name_id,write_characteristic_id, "
                +" order_no,remark "
                +" from device_characteristic_ble_map d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("device_characteristic_ble_map_id",rset.getInt("device_characteristic_ble_map_id"));
                 obj.put("device_id",rset.getInt("device_id"));
                 obj.put("read_characteristic_id",rset.getInt("read_characteristic_id"));
                 obj.put("write_characteristic_id",rset.getInt("write_characteristic_id"));
                 obj.put("ble_operation_name_id",rset.getInt("ble_operation_name_id"));
                 obj.put("order_no",rset.getString("order_no"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    
    public JSONArray getSelectionRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select selection_id, command_id, parameter_id, remark , selection_value_no"
                +" from selection d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("selection_id",rset.getInt("selection_id"));
                 obj.put("command_id",rset.getInt("command_id"));
                 obj.put("parameter_id",rset.getInt("parameter_id"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("selection_value_no",rset.getInt("selection_value_no"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    public JSONArray getSelectionValueRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select selection_value_id, display_value, byte_value, remark, revision_no, selection_id"
                +" from selection_value d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("selection_value_id",rset.getInt("selection_value_id"));
                 obj.put("display_value",rset.getString("display_value"));
                 obj.put("byte_value",rset.getString("byte_value"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("revision_no",rset.getInt("revision_no"));
                 obj.put("selection_id",rset.getInt("selection_id"));
                 
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    public JSONArray getByteDataRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select byte_data_id, sub_byte_division, command_id, remark, revision_no, parameter_name"
                +" from byte_data d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("byte_data_id",rset.getInt("byte_data_id"));
                 obj.put("sub_byte_division",rset.getInt("sub_byte_division"));
                 obj.put("command_id",rset.getInt("command_id"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("revision_no",rset.getInt("revision_no"));
                 obj.put("parameter_name",rset.getString("parameter_name"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getSubByteDivisionRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select sub_byte_division_id, byte_id, parameter_name, start_pos, no_of_bit,  remark, revision_no, sub_division_no"
                +" from sub_byte_division d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("sub_byte_division_id",rset.getInt("sub_byte_division_id"));
                 obj.put("byte_id",rset.getInt("byte_id"));
                 obj.put("parameter_name",rset.getString("parameter_name"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("start_pos",rset.getInt("start_pos"));
                 obj.put("no_of_bit",rset.getInt("no_of_bit"));
                 obj.put("sub_division_no",rset.getInt("sub_division_no"));
                 obj.put("revision_no",rset.getInt("revision_no"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    public JSONArray getSubDivisionSelectionRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select sub_division_selection_id, display_value, bit_value, sub_byte_division_id"
                +" from sub_division_selection d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("sub_division_selection_id",rset.getInt("sub_division_selection_id"));
                 obj.put("display_value",rset.getString("display_value"));
                 obj.put("bit_value",rset.getString("bit_value"));
                 obj.put("sub_byte_division_id",rset.getInt("sub_byte_division_id"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    
     public JSONArray getInputRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select input_id, command_id, parameter_id,remark "
                +" from input d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("input_id",rset.getInt("input_id"));
                 obj.put("command_id",rset.getInt("command_id"));
                 obj.put("parameter_id",rset.getInt("parameter_id"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    public JSONArray getParameterRecord()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select parameter_id, parameter_name, parameter_type, remark "
                +" from parameter d "
                +" where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("parameter_id",rset.getInt("parameter_id"));
                 obj.put("parameter_name",rset.getString("parameter_name"));
                 obj.put("parameter_type",rset.getString("parameter_type"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }


    public JSONArray getDeviceMapRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select device_map_id,finished_device_id,ble_device_id,module_device_id,remark "
                +" from device_map dm "
                +" where dm.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("device_map_id",rset.getInt("device_map_id"));
                 obj.put("finished_device_id",rset.getInt("finished_device_id"));
                 obj.put("ble_device_id",rset.getInt("ble_device_id"));
                 obj.put("module_device_id",rset.getInt("module_device_id"));
                 obj.put("remark",rset.getString("remark"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }






    

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            System.out.println("hii inside setConnection() method");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble_database1", "root", "root");
        } catch (Exception e) {
            System.out.println("BLEWebServicesModel setConnection() Error: " + e);
        }
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
