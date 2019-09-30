/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.model;

import com.ble.device.bean.DeviceMapBean;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apogee
 */
public class DeviceMapModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
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
    
    public int getFinishedDeviceId(String manufactureName, String deviceType, String modelName) {
        
        //
        String query1 = "Select d.id as finished_device_id " 
                + "from manufacturer m, device_type dt, model mo, device d " 
                + "where d.manufacture_id = m.id and d.device_type_id = dt.id " 
                + "and d.model_id = mo.id " 
                + "and d.active = 'Y'" 
                + "and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y' " 
                + "and m.name = ? and dt.type = ? and mo.device_name = ?;";
        int finished_device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, manufactureName);
            stmt.setString(2, deviceType);
            stmt.setString(3, modelName);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Jaya");
            rs.next();
            finished_device_id = rs.getInt("finished_device_id");
            
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
         return finished_device_id;
    }
    
    public int getModuleDeviceId(String manufactureName, String deviceType, String modelName) {
        
        //
        String query1 = "Select d.id as module_device_id from manufacturer m, device_type dt, model mo, device d " 
                +"where d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id " 
                +"and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y'" 
                +"and m.name = ? and dt.type = ? and mo.device_name = ?;";
        int module_device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, manufactureName);
            stmt.setString(2, deviceType);
            stmt.setString(3, modelName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            module_device_id = rs.getInt("module_device_id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return module_device_id;
    }
    
    public int getBleDeviceId(String manufactureName, String deviceType, String modelName) {
        
        //
        String query1 = "Select d.id as ble_device_id from manufacturer m, device_type dt, model mo, device d " 
                        + "where d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id " 
                        + "and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y'" 
                        + "and m.name = ? and dt.type = ? and mo.device_name = ?;";
        int ble_device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, manufactureName);
            stmt.setString(2, deviceType);
            stmt.setString(3, modelName);
            ResultSet rs = stmt.executeQuery();
            System.out.print("Jaya");
            rs.next();
            ble_device_id = rs.getInt("ble_device_id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return ble_device_id;
    }

    public int insertRecord(DeviceMapBean deviceMapBean) {

        int finished_device_id = getFinishedDeviceId(deviceMapBean.getFinished_manufacture_name(),deviceMapBean.getFinished_device_type(), deviceMapBean.getFinished_model_name());
        int module_device_id = getModuleDeviceId(deviceMapBean.getModule_manufacture_name(),deviceMapBean.getModule_device_type(), deviceMapBean.getModule_model_name());
        int ble_device_id = getBleDeviceId(deviceMapBean.getBle_manufacture_name(),deviceMapBean.getBle_device_type(), deviceMapBean.getBle_model_name());
        
        String query1 = " select count(*) from device_map "
                        + " where finished_device_";
        
        String query = " insert into device_map(finished_device_id,module_device_id,ble_device_id,remark) "
                + " values(?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, finished_device_id);
            pstmt.setInt(2, module_device_id);
            pstmt.setInt(3, ble_device_id);
            pstmt.setString(4, deviceMapBean.getRemark());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

    public boolean reviseRecords(DeviceMapBean deviceMapBean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        int finished_device_id = getFinishedDeviceId(deviceMapBean.getFinished_manufacture_name(),deviceMapBean.getFinished_device_type(), deviceMapBean.getFinished_model_name());
        int module_device_id = getModuleDeviceId(deviceMapBean.getModule_manufacture_name(),deviceMapBean.getModule_device_type(), deviceMapBean.getModule_model_name());
        int ble_device_id = getBleDeviceId(deviceMapBean.getBle_manufacture_name(),deviceMapBean.getBle_device_type(), deviceMapBean.getBle_model_name());
        
        String query1 = " SELECT max(revision_no) revision_no FROM device_map c WHERE c.device_map_id = " + deviceMapBean.getDevice_map_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE device_map SET active=? WHERE device_map_id = ? && revision_no = ? ";
        String query3 = " insert into device_map(device_map_id,finished_device_id,module_device_id,ble_device_id,remark,revision_no,active) "
                        + " values(?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, deviceMapBean.getDevice_map_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, deviceMapBean.getDevice_map_id());
                    psmt.setInt(2, finished_device_id);
                    psmt.setInt(3, module_device_id);
                    psmt.setInt(4, ble_device_id);
                    psmt.setString(5, deviceMapBean.getRemark());
                    psmt.setInt(6, rev);
                    psmt.setString(7, "Y");

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CommandModel reviseRecord() Error: " + e);
        }
        if (status) {
            message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
            message = "Record Not updated Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not updated");
        }

        return status;
    }

    public int getNoOfRows(String searchManufacturerName, String searchDeviceTypeName) {
        String query1 = "SELECT count(*)" 
                +" FROM device_map dm, device d, device d1, device d2, manufacturer m, model mo, device_type dt," 
                +" manufacturer m1, model mo1, device_type dt1, manufacturer m2, model mo2, device_type dt2" 
                +" where dm.finished_device_id = d.id " 
                +" and dm.module_device_id = d1.id and dm.ble_device_id = d2.id" 
                +" and d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id" 
                +" and d1.manufacture_id = m1.id and d1.device_type_id = dt1.id and d1.model_id = mo1.id" 
                +" and d2.manufacture_id = m2.id and d2.device_type_id = dt2.id and d2.model_id = mo2.id and dm.active = 'Y'" 
                +" and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y'" 
                +" and d1.active = 'Y' and m1.active = 'Y' and dt1.active = 'Y' and mo1.active = 'Y'" 
                +" and d2.active = 'Y' and m2.active = 'Y' and dt2.active = 'Y' and mo2.active = 'Y'"
                +" AND IF('" + searchManufacturerName + "' = '', m.name LIKE '%%',m.name =?) "
                +" AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) ";
      

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchManufacturerName);
            stmt.setString(2, searchDeviceTypeName);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<DeviceMapBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchManufacturerName, String searchDeviceTypeName) {
        List<DeviceMapBean> list = new ArrayList<DeviceMapBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

//       String query2="select d.id as device_id,mt.name as manufacturer_name,dt.type as device_type,m.device_name,m.device_no,d.remark "
//                     +" from device d,manufacturer mt,device_type dt,model m "
//                     +" where d.manufacture_id = mt.id "
//                     +" and d.device_type_id = dt.id "
//                     +" and d.model_id = m.id "
//                     +" AND IF('" + searchManufacturerName + "' = '', mt.name LIKE '%%',mt.name =?) "
//                     +" AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
//                     +" and d.active='Y' and mt.active='Y' and dt.active='Y'  and m.active='Y' "
//                     +addQuery;
        String query2 = "SELECT dm.device_map_id as device_map_id," 
                +" dm.finished_device_id as finished_device_id,m.name as finished_manufacturer_name," 
                +" mo.device_name as finished_model_name, mo.device_no as finished_model_number, dt.type as finished_device_type," 
                +" dm.module_device_id as module_device_id,m1.name as module_manufacturer_name," 
                +" mo1.device_name as module_model_name, mo1.device_no as module_model_number, dt1.type as module_device_type," 
                +" dm.ble_device_id as ble_device_id,m2.name as ble_manufacturer_name, mo2.device_no as ble_model_number," 
                +" mo2.device_name as ble_model_name, dt2.type as ble_device_type, dm.remark" 
                +" FROM device_map dm, device d, device d1, device d2, manufacturer m, model mo, device_type dt," 
                +" manufacturer m1, model mo1, device_type dt1, manufacturer m2, model mo2, device_type dt2" 
                +" where dm.finished_device_id = d.id " 
                +" and dm.module_device_id = d1.id and dm.ble_device_id = d2.id" 
                +" and d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id" 
                +" and d1.manufacture_id = m1.id and d1.device_type_id = dt1.id and d1.model_id = mo1.id" 
                +" and d2.manufacture_id = m2.id and d2.device_type_id = dt2.id and d2.model_id = mo2.id and dm.active = 'Y'" 
                +" and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y'" 
                +" and d1.active = 'Y' and m1.active = 'Y' and dt1.active = 'Y' and mo1.active = 'Y'" 
                +" and d2.active = 'Y' and m2.active = 'Y' and dt2.active = 'Y' and mo2.active = 'Y'"
                +" AND IF('" + searchManufacturerName + "' = '', m.name LIKE '%%',m.name =?) "
                +" AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
                + addQuery;
                
//                "select dr.id as device_reg_id,name as manufacture_name,type as device_type,device_name, "
//                + " device_no,reg_no,manufacture_date,sale_date,dr.remark "
//                + " from device f, device b, device f,device_type dt,manufacturer mt,model m "
//                + " where dr.device_id = d.id "
//                + " and d.manufacture_id = mt.id "
//                + " and d.device_type_id = dt.id "
//                + " and d.model_id = m.id "
//                + " AND IF('" + searchManufacturerName + "' = '', mt.name LIKE '%%',mt.name =?) "
//                + " AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
//                + " and dr.active='Y' and d.active='Y' "
//                + " and mt.active='Y' and dt.active='Y' and m.active='Y' "
                
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchManufacturerName);
            pstmt.setString(2, searchDeviceTypeName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceMapBean deviceBean = new DeviceMapBean();
                deviceBean.setDevice_map_id(rset.getInt("device_map_id"));
                deviceBean.setBle_device_id(rset.getInt("ble_device_id"));
                deviceBean.setBle_manufacture_name(rset.getString("ble_manufacturer_name"));
                deviceBean.setBle_device_type(rset.getString("ble_device_type"));
                deviceBean.setBle_model_name(rset.getString("ble_model_name"));
                deviceBean.setBle_model_no(rset.getString("ble_model_number"));
                
                deviceBean.setFinished_device_id(rset.getInt("finished_device_id"));
                deviceBean.setFinished_manufacture_name(rset.getString("finished_manufacturer_name"));
                deviceBean.setFinished_device_type(rset.getString("finished_device_type"));
                deviceBean.setFinished_model_name(rset.getString("finished_model_name"));
                deviceBean.setFinished_model_no(rset.getString("finished_model_number"));
                
                deviceBean.setModule_device_id(rset.getInt("module_device_id"));
                deviceBean.setModule_manufacture_name(rset.getString("module_manufacturer_name"));
                deviceBean.setModule_device_type(rset.getString("module_device_type"));
                deviceBean.setModule_model_name(rset.getString("module_model_name"));
                deviceBean.setModule_model_no(rset.getString("module_model_number"));
                
                deviceBean.setRemark(rset.getString("remark"));
                list.add(deviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int deleteRecord(int device_id) {

        String query = "update device_map set active='N' where device_map_id=" + device_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' "
                + " group by name order by name desc ";
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

    public List<String> getDeviceTypeName(String q, String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = "select type "
                + " from device_type dt,device d,manufacturer m "
                + " where d.manufacture_id = (select id from manufacturer m where m.name=? and m.active='Y') "
                + " and d.device_type_id = dt.id "
                + " and dt.active='Y' and d.active='Y' and m.active='Y' "
                + " group by type ";
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
                list.add("No such Manufacturer Name exists.......");
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
                + " group by device_name order by device_name desc ";
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
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getDeviceNo(String q, String device_name) {
        List<String> list = new ArrayList<String>();
        String query = "select device_no from model where device_name=? "
                + " and active='Y' "
                + " group by device_no order by id desc ";
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
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select m.name "
                + " from device_registration dr,device d,manufacturer m "
                + " where dr.device_id = d.id "
                + " and d.manufacture_id = m.id "
                + " and dr.active='Y' and d.active='Y' and m.active='Y' "
                + " group by m.name ";
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

    public List<String> getSearchDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select dt.type "
                + " from device_registration dr,device d,device_type dt "
                + " where dr.device_id = d.id "
                + " and d.device_type_id = dt.id "
                + " and dr.active='Y' and d.active='Y' and dt.active='Y' "
                + " group by dt.type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
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
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

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
