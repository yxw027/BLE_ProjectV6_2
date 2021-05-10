/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.webService.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public static String reg_no25;
    public static String pass25;
    public static int device_id25;

    public JSONArray getManufacturerRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,remark "
                + " from manufacturer m "
                + " where m.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("name", rset.getString("name"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDeviceTypeRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,type,remark "
                + " from device_type dt "
                + " where dt.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("type", rset.getString("type"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getModelTypeRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,type,remark "
                + " from modal_type mt "
                + " where mt.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("type", rset.getString("type"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getModelRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_name,device_no,warranty_period,remark,model_type_id,device_address,no_of_module "
                + " from model m "
                + " where m.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("device_name", rset.getString("device_name"));
                obj.put("device_no", rset.getString("device_no"));
                obj.put("warranty_period", rset.getString("warranty_period"));
                obj.put("remark", rset.getString("remark"));
                obj.put("model_type_id", rset.getInt("model_type_id"));
                obj.put("no_of_module", rset.getInt("no_of_module"));
                obj.put("device_address", rset.getString("device_address"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCommandTypeRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,remark "
                + " from command_type ct "
                + " where ct.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("name", rset.getString("name"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getOperationNameRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,operation_name,remark, parent_id,is_super_child "
                + " from operation_name op_n "
                + " where op_n.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("operation_name", rset.getString("operation_name"));
                obj.put("remark", rset.getString("remark"));
                obj.put("parent_id", rset.getString("parent_id"));
                obj.put("is_super_child", rset.getString("is_super_child"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDeviceRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,manufacture_id,device_type_id,model_id,remark "
                + " from device d "
                + " where d.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("manufacture_id", rset.getInt("manufacture_id"));
                obj.put("device_type_id", rset.getInt("device_type_id"));
                obj.put("model_id", rset.getInt("model_id"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDeviceRegistrationRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_id,reg_no,manufacture_date,sale_date,remark "
                + " from device_registration dr "
                + " where dr.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("device_id", rset.getInt("device_id"));
                obj.put("reg_no", rset.getInt("reg_no"));
                obj.put("manufacture_date", rset.getString("manufacture_date"));
                obj.put("date2", rset.getString("date2"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getServiceRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,service_name,service_uuid,device_id,remark "
                + " from servicies s "
                + " where s.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("service_name", rset.getString("service_name"));
                obj.put("service_uuid", rset.getString("service_uuid"));
                obj.put("device_id", rset.getString("device_id"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCharachtersticRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,service_id,uuid,remark "
                + " from charachtristics c "
                + " where c.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("name", rset.getString("name"));
                obj.put("service_id", rset.getInt("service_id"));
                obj.put("uuid", rset.getString("uuid"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCommandRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,command,starting_del,end_del,remark,command_type_id,selection,input "
                + " from command c "
                + " where c.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                String command = rset.getString("command");
                obj.put("command", command);
                obj.put("starting_del", rset.getString("starting_del"));
                obj.put("end_del", rset.getString("end_del"));
                obj.put("remark", rset.getString("remark"));
                obj.put("command_type_id", rset.getInt("command_type_id"));
                obj.put("selection", rset.getInt("selection"));
                obj.put("input", rset.getInt("input"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCommandDeviceMapRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = "select device_command_id,order_no,delay,device_id,command_id,operation_id,remark"
//                + " from device_command_map c "
//                + " where c.active='Y' ORDER BY c.order_no ASC ";
        query = " select * from device_command_map where active='Y' order by device_command_id desc ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("device_command_id"));
                obj.put("device_id", rset.getInt("device_id"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("operation_id", rset.getInt("operation_id"));
                obj.put("order_no", rset.getInt("order_no"));
                obj.put("delay", rset.getInt("delay"));
                obj.put("remark", rset.getString("remark"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getRuleRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,command_id,description,remark "
                + " from rule r "
                + " where r.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("description", rset.getString("description"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getBleOperationNameRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select ble_operation_name_id,ble_operation_name,remark "
                + " from ble_operation_name bop "
                + " where bop.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("ble_operation_name_id", rset.getInt("ble_operation_name_id"));
                obj.put("ble_operation_name", rset.getString("ble_operation_name"));
                obj.put("remark", rset.getString("remark"));

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
    public JSONArray getDeviceCharacteristicBleMapRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select device_characteristic_ble_map_id,device_id,read_characteristic_id,ble_operation_name_id,write_characteristic_id, "
                + " order_no,remark "
                + " from device_characteristic_ble_map d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("device_characteristic_ble_map_id", rset.getInt("device_characteristic_ble_map_id"));
                obj.put("device_id", rset.getInt("device_id"));
                obj.put("read_characteristic_id", rset.getInt("read_characteristic_id"));
                obj.put("write_characteristic_id", rset.getInt("write_characteristic_id"));
                obj.put("ble_operation_name_id", rset.getInt("ble_operation_name_id"));
                obj.put("order_no", rset.getString("order_no"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getSelectionRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
//        query = " select selection_id, command_id, parameter_id, remark , selection_value_no"
//                + " from selection d "
//                + " where d.active='Y' ";
        query = " select s.selection_id, cpm.command_id, cpm.parameter_id, cpm.remark , s.selection_value_no "
                + " from command_param_map cpm, selection s, selection_value sv "
                + " where cpm.active='Y' and s.active='Y' and sv.active='Y' and cpm.selection_value_id=sv.selection_value_id "
                + " and sv.selection_id=s.selection_id ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("selection_id", rset.getInt("selection_id"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("parameter_id", rset.getInt("parameter_id"));
                obj.put("remark", rset.getString("remark"));
                obj.put("selection_value_no", rset.getInt("selection_value_no"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getSelectionValueRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select selection_value_id, display_value, byte_value, remark, revision_no, selection_id"
                + " from selection_value d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("selection_value_id", rset.getInt("selection_value_id"));
                obj.put("display_value", rset.getString("display_value"));
                obj.put("byte_value", rset.getString("byte_value"));
                obj.put("remark", rset.getString("remark"));
                obj.put("revision_no", rset.getInt("revision_no"));
                obj.put("selection_id", rset.getInt("selection_id"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getByteDataRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select byte_data_id, sub_byte_division, command_id, remark, revision_no, parameter_name"
                + " from byte_data d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("byte_data_id", rset.getInt("byte_data_id"));
                obj.put("sub_byte_division", rset.getInt("sub_byte_division"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("remark", rset.getString("remark"));
                obj.put("revision_no", rset.getInt("revision_no"));
                obj.put("parameter_name", rset.getString("parameter_name"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getSubByteDivisionRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select sub_byte_division_id, byte_id, parameter_name, start_pos, no_of_bit,  remark, revision_no, sub_division_no"
                + " from sub_byte_division d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("sub_byte_division_id", rset.getInt("sub_byte_division_id"));
                obj.put("byte_id", rset.getInt("byte_id"));
                obj.put("parameter_name", rset.getString("parameter_name"));
                obj.put("remark", rset.getString("remark"));
                obj.put("start_pos", rset.getInt("start_pos"));
                obj.put("no_of_bit", rset.getInt("no_of_bit"));
                obj.put("sub_division_no", rset.getInt("sub_division_no"));
                obj.put("revision_no", rset.getInt("revision_no"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getSubDivisionSelectionRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select sub_division_selection_id, display_value, bit_value, sub_byte_division_id"
                + " from sub_division_selection d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("sub_division_selection_id", rset.getInt("sub_division_selection_id"));
                obj.put("display_value", rset.getString("display_value"));
                obj.put("bit_value", rset.getString("bit_value"));
                obj.put("sub_byte_division_id", rset.getInt("sub_byte_division_id"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getInputRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select input_id, command_id, parameter_id,remark "
                + " from input d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("input_id", rset.getInt("input_id"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("parameter_id", rset.getInt("parameter_id"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getParameterRecord() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select parameter_id, parameter_name, parameter_type, remark "
                + " from parameter d "
                + " where d.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("parameter_id", rset.getInt("parameter_id"));
                obj.put("parameter_name", rset.getString("parameter_name"));
                obj.put("parameter_type", rset.getString("parameter_type"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDeviceMapRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select device_map_id,finished_device_id,module_device_id,remark "
                + " from device_map dm "
                + " where dm.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("device_map_id", rset.getInt("device_map_id"));
                obj.put("finished_device_id", rset.getInt("finished_device_id"));
                obj.put("module_device_id", rset.getInt("module_device_id"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

//    public String saveDeviceReg(String device_type, String manu_name, String device_name, String device_no) {
//        String status = "";
//        int device_registration_id = 0;
//        int manufacture_id = getManufactureId(manu_name);
//        int device_type_id = getDeviceTypeId(device_type);
//        int model_id = getModelId(device_name, device_no);
//        int device_id = getDeviceId(manufacture_id, device_type_id, model_id);
//        String lat_re_no = getLatestRegNo();
//        int update_reg_no = Integer.parseInt(lat_re_no.split("_")[1]) + 1;
//        String reg_no1 = "D_" + update_reg_no;
//        Random random = new Random();
//        int rand_int1 = random.nextInt(100000);
//        String pass = "P_" + rand_int1;
//        String query = " insert into device_registration(device_id,reg_no,password,remark)"
//                + " values(?,?,?,?) ";
//        int rowsAffected = 0;
//        try {
//            // java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//            pstmt.setInt(1, device_id);
//            pstmt.setString(2, reg_no1);
//            pstmt.setString(3, pass);
//            pstmt.setString(4, "");
////            pstmt.setString(5,deviceRegistrationBean.getRemark());
//            rowsAffected = pstmt.executeUpdate();
////            ResultSet rs = pstmt.getGeneratedKeys();
////           if(rs.next()){
////               device_registration_id=rs.getInt(1);
////           }
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//        if (rowsAffected > 0) {
//            saveDeviceAllocationStratus(device_registration_id);
//            status = device_id + "," + reg_no1 + "," + pass;
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return status;
//    }
    public String saveDeviceReg(String device_type, String manu_name, String device_name, String device_no) {
        String status = "";
        int device_registration_id = 0;
        int manufacture_id = getManufactureId(manu_name);
        int device_type_id = getDeviceTypeId(device_type);
        int model_id = getModelId(device_name, device_no);
        device_id25 = getDeviceId(manufacture_id, device_type_id, model_id);
        String lat_re_no = getLatestRegNo();
        int update_reg_no = Integer.parseInt(lat_re_no.split("_")[1]) + 1;
        reg_no25 = "D_" + update_reg_no;
        Random random = new Random();
        int rand_int1 = random.nextInt(100000);
        pass25 = "P_" + rand_int1;

        status = device_id25 + "," + reg_no25 + "," + pass25;

        return status;
    }

    public String saveDeviceRegConformation(String device_id, String reg_no, String password) {
        String status = "";
        int device_registration_id = 0;
        int device_id1 = 0;
        device_id1 = Integer.parseInt((device_id).trim());

        String query = " insert into device_registration(device_id,reg_no,password,remark)"
                + " values(?,?,?,?) ";
        int rowsAffected = 0;
        try {
            // java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, device_id1);
            pstmt.setString(2, reg_no);
            pstmt.setString(3, password);
            pstmt.setString(4, "");
//            pstmt.setString(5,deviceRegistrationBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
//            ResultSet rs = pstmt.getGeneratedKeys();
//           if(rs.next()){
//               device_registration_id=rs.getInt(1);
//           }
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            saveDeviceAllocationStratus(device_registration_id);
            status = device_id + "," + reg_no + "," + password;
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return status;
    }

    public void saveDeviceAllocationStratus(int device_registration_id) {
        device_registration_id = getLastdeviceRegistrationId();
        String query = " insert into device_allocation_status(device_registration_id,is_allocate,remark)"
                + " values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, device_registration_id);
            pstmt.setString(2, "Yes");
            pstmt.setString(3, "");
//            pstmt.setString(4,deviceRegistrationBean.getSale_date());
//            pstmt.setString(5,deviceRegistrationBean.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
        }
    }

    public int getManufactureId(String manufactureName) {
        String query1 = "select id from manufacturer m "
                + " where m.name=? "
                + " and m.active='Y' ";
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

    public int getLastdeviceRegistrationId() {
        String query1 = "select id from device_registration m "
                + " order by id desc limit 1";
        int manufacturer_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            manufacturer_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return manufacturer_id;
    }

    public String getLatestRegNo() {
//        String query1 = "select reg_no from device_registration m "
//                + " where m.active='Y'"
//                + " order by reg_no desc limit 1";
        String query1 = "select reg_no,created_at from device_registration m  where "
                + "  created_at=(select max(created_at) from device_registration where active='Y')";

        String reg_no = "D_0";
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
        String query1 = "select id from device_type d "
                + " where d.type=? "
                + " and d.active='Y' ";
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

    public int getModelId(String device_name, String device_no) {
        String query1 = "select id from model m "
                + " where m.device_name=? and m.device_no=? "
                + " and m.active='Y' ";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, device_name);
            stmt.setString(2, device_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            model_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return model_id;
    }

    public int getDeviceId(int manufacture_id, int device_type_id, int model_id) {
        String query1 = "select d.id from device d "
                + " where d.manufacture_id=? "
                + " and d.device_type_id=? "
                + " and d.model_id=? "
                + " and d.active='Y' ";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setInt(1, manufacture_id);
            stmt.setInt(2, device_type_id);
            stmt.setInt(3, model_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_id;
    }

    public JSONArray getCrcTypeRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select crc_type_id,crc_type,remark "
                + " from crc_type ct "
                + " where ct.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("crc_type_id", rset.getInt("crc_type_id"));
                obj.put("crc_type", rset.getString("crc_type"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of crc type table: " + e);
        }
        return rowData;
    }

    public JSONArray getCommandCrcMappingRecords() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select command_crc_mapping_id,command_id,crc_type_id,remark "
                + " from command_crc_mapping ccm "
                + " where ccm.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("command_crc_mapping_id", rset.getInt("command_crc_mapping_id"));
                obj.put("command_id", rset.getInt("command_id"));
                obj.put("crc_type_id", rset.getInt("crc_type_id"));
                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of crc Command Mapping: " + e);
        }
        return rowData;
    }

    public int getDeviceId(String reg_no) {
        int device_id = 0;
        String query = null;
        // query="select module_device_id from device_map where finished_device_id="+reg_no+" and active='Y'";
        query = "select device_id from device_registration where reg_no='" + reg_no + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            device_id = rset.getInt("device_id");
        } catch (Exception e) {
            System.out.println("Exception device_id" + e);
        }
        return device_id;
    }
//public int getModuleDeviceId(int reg_no)
//{
//    int device_id=0;
//    String query=null;
//   // query="select module_device_id from device_map where finished_device_id="+reg_no+" and active='Y'";
//    query = "select device_id from device_registration where reg_no="+reg_no+" and active='Y' ";
//    try{
//   PreparedStatement pstmt = connection.prepareStatement(query);
//   ResultSet rset = pstmt.executeQuery();
//   rset.next();
//   device_id=rset.getInt("device_id");
//    }catch(Exception e)
//    {
//     System.out.println("Exception device_id"+e);
//    }
//    return device_id;
//}

    public List<Integer> getModuleDeviceId(int device_id) {
        List<Integer> list = new ArrayList<Integer>();
//String query="select distinct module_device_id from device_map where finished_device_id="+device_id+" and active='Y' "; 
        String query = "select distinct module_device_id from device_map where finished_device_id=" + device_id + " and active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                int module_device_id = rset.getInt("module_device_id");

                list.add(module_device_id);
                count++;

            }
            if (count == 0) {
                list.add(0);
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public JSONArray getregisteredDeviceDetailRecords(String regiNo) {
        JSONArray rowData = new JSONArray();
        String query = null;
        List<Integer> moduleDeviceId = null;
//           JSONObject obj1 = new JSONObject();
        int device_id = 0;
        String moduleDeviceId1 = "";
        device_id = getDeviceId(regiNo);
        moduleDeviceId = getModuleDeviceId(device_id);
        moduleDeviceId1 = moduleDeviceId.toString();
        moduleDeviceId1 = moduleDeviceId1.toString().substring(1, moduleDeviceId1.length() - 1);
        query = "select distinct d.id,dt.type "
                + "  from device d,device_type dt,device_map dm where d.id=dm.module_device_id and d.device_type_id=dt.id "
                + " and d.id in(" + moduleDeviceId1 + ") and d.active='Y' and dm.active='Y' and dt.active='Y'";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                JSONObject obj = new JSONObject();
//                obj.put( rset.getInt("id"),"module_device_id");
//
//                obj.put(rset.getString("type"),"module_device_type");
                obj.put(rset.getString("type"), rset.getInt("id"));

                rowData.add(obj);
            }
//            obj1.put(regiNo, "regiNo");
//            rowData.add(obj1);
        } catch (Exception e) {
            System.out.println("Error inside show data of crc Command Mapping: " + e);
        }

        return rowData;
    }

    ////////////
    public JSONArray getModelTypedata() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,type,active,remark,Created_at,revision_no "
                + " from modal_type mt "
                + " where mt.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("type", rset.getString("type"));
                obj.put("active", rset.getString("active"));
                obj.put("remark", rset.getString("remark"));
                obj.put("Created_at", rset.getString("Created_at"));

                obj.put("revision_no", rset.getInt("revision_no"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getModeldata() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_name,device_no,warranty_period,remark,model_type_id,device_address,no_of_module,active"
                + " ,created_at "
                + " from model m "
                + " where m.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("device_name", rset.getString("device_name"));
                obj.put("device_no", rset.getString("device_no"));
                obj.put("warranty_period", rset.getString("warranty_period"));
                obj.put("remark", rset.getString("remark"));

                obj.put("model_type_id", rset.getInt("model_type_id"));
                obj.put("device_address", rset.getString("device_address"));
                obj.put("no_of_module", rset.getInt("no_of_module"));

                obj.put("active", rset.getString("active"));
                obj.put("created_at", rset.getString("created_at"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDevicedata() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,device_type_id,model_id,remark,active,revision_no,created_at,created_by "
                + " from device d "
                + " where d.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));

                obj.put("device_type_id", rset.getInt("device_type_id"));
                obj.put("model_id", rset.getInt("model_id"));
                obj.put("remark", rset.getString("remark"));
                obj.put("revision_no", rset.getInt("revision_no"));
                obj.put("active", rset.getString("active"));
                obj.put("created_at", rset.getString("created_at"));
                obj.put("created_by", rset.getString("created_by"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getService() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,service_name,service_uuid,device_id,remark,active,created_at,revision_no "
                + " from servicies s "
                + " where s.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("service_name", rset.getString("service_name"));
                obj.put("service_uuid", rset.getString("service_uuid"));
                obj.put("device_id", rset.getString("device_id"));
                obj.put("remark", rset.getString("remark"));
                obj.put("active", rset.getString("active"));
                obj.put("created_at", rset.getString("created_at"));
                obj.put("revision_no", rset.getInt("revision_no"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getCharachtersticdata() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select id,name,active,remark,created_at,revision_no,service_id,uuid "
                + " from charachtristics c "
                + " where c.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rset.getInt("id"));
                obj.put("name", rset.getString("name"));
                obj.put("active", rset.getString("active"));
                obj.put("remark", rset.getString("remark"));
                obj.put("revision_no", rset.getInt("revision_no"));
                obj.put("created_at", rset.getString("created_at"));
                obj.put("service_id", rset.getInt("service_id"));
                obj.put("uuid", rset.getString("uuid"));
                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getBleOperationNamedata() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = " select device_characteristic_ble_map_id,device_id,read_characteristic_id,write_characteristic_id,ble_operation_name_id,"
                + " order_no,remark "
                + " from device_characteristic_ble_map bop "
                + " where bop.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("device_characteristic_ble_map_id", rset.getInt("device_characteristic_ble_map_id"));
                obj.put("device_id", rset.getInt("device_id"));
                obj.put("read_characteristic_id", rset.getInt("read_characteristic_id"));
                obj.put("write_characteristic_id", rset.getInt("write_characteristic_id"));
                obj.put("ble_operation_name_id", rset.getInt("ble_operation_name_id"));
                obj.put("order_no", rset.getInt("order_no"));

                obj.put("remark", rset.getString("remark"));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    //datum data
    public JSONArray getDatumData() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select datum_id,name,command from datum where active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("datum_id", rset.getString(1));
                obj.put("datum_name", rset.getString(2));
                obj.put("datum_command", rset.getString(3));
                //obj.put("datum_remark", rset.getString(4));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of getDatumData: " + e);
        }
        return rowData;
    }
    // end datum data
    
    //command_param_map data
    public JSONArray getCommandParam() {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select * from command_param_map where active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("command_param_map_id", rset.getString(1));
                obj.put("command_id", rset.getString(2));
                obj.put("parameter_id", rset.getString(3));
                obj.put("selection_value_id", rset.getString(4));
                obj.put("sub_division_selection_id", rset.getString(5));
                //obj.put("datum_remark", rset.getString(4));

                rowData.add(obj);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of getCommandParam: " + e);
        }
        return rowData;
    }
    // command_param_map data

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            System.out.println("hii inside setConnection() method");
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble2", "root", "CXKyE2ZpT%HjbP!4c$");
            // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble_database6", "jpss_2", "jpss_1277");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble_database7", "root", "root");

            // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble_database6", "root", "CXKyE2ZpT%HjbP!4c$");
            // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ble_database6", "jpss_2", "jpss_1277");
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
