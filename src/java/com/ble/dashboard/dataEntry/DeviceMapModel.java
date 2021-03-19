/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author saini
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
    private String module_table_id;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("AddDeviceModel setConnection() Error: " + e);
        }
    }

    public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select * from manufacturer where active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) { // move cursor from BOR to valid record.
                String str = rset.getString(2);
                if (str.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(str);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getManufacturer ERROR inside DeviceMapModel - " + e);
        }
        return list;
    }

    public List<String> getDeviceType(String q, String manufacturer_name) {
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
                list.add("No such Device Type Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside DeviceMapModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getModelName(String q, String r) {
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
                list.add("No such Model Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside DeviceMapModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select * from device_type where active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) { // move cursor from BOR to valid record.
                String str = rset.getString(2);
                if (str.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(str);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device type exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDeviceType ERROR inside DeviceModel - " + e);
        }
        return list;
    }

    public List<String> getParentType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from device_type where active='Y' and is_super_child='N' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String parent_type = (rset.getString("type"));
                if (parent_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(parent_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Parent Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<DeviceMapBean> showData() {
        List<DeviceMapBean> list = new ArrayList<DeviceMapBean>();
        //String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//        if (lowerLimit == -1) {
//            addQuery = "";
//        }
        String query2 = " SELECT dm.device_map_id as device_map_id,dm.finished_device_id as finished_device_id,m.name as finished_manufacturer_name, mo.device_name as finished_model_name, mo.device_no as finished_model_number, "
                + " dt.type as finished_device_type, dm.module_device_id as module_device_id,m1.name as module_manufacturer_name, mo1.device_name as module_model_name, mo1.device_no as module_model_number, dt1.type as module_device_type, dm.remark  "
                + " FROM device_map dm, device d, device d1, manufacturer m, model mo, device_type dt, manufacturer m1, model mo1, device_type dt1 "
                + " where dm.finished_device_id = d.id "
                + " and dm.module_device_id = d1.id and  d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id and d1.manufacture_id = m1.id and d1.device_type_id = dt1.id and d1.model_id = mo1.id "
                + " and dm.active = 'Y' and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y' and d1.active = 'Y' and m1.active = 'Y' and dt1.active = 'Y' and mo1.active = 'Y' ";
//                + " AND IF('" + searchManufacturerName + "' = '', m.name LIKE '%%',m.name =?) "
//                + " AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
//                + " AND IF('" + searchModelName + "' = '', mo1.device_name LIKE '%%',mo1.device_name =?) "
//                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
//            pstmt.setString(1, searchManufacturerName);
//            pstmt.setString(2, searchDeviceTypeName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceMapBean deviceBean = new DeviceMapBean();
                deviceBean.setDevice_map_id(rset.getInt("device_map_id"));
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

    public String getParentTypeName(String parentId) {
        String query = "select type as parent_type from device_type where id=? and active='Y' ";
        String parent_type = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, parentId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                parent_type = rset.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error: DeviceTypeModel[getParentType]--" + e);
        }
        return parent_type;
    }

    public int insertRecord(DeviceMapBean deviceMapBean) throws SQLException {

        int finished_device_id = getFinishedDeviceId(deviceMapBean.getFinished_manufacture_name(), deviceMapBean.getFinished_device_type(), deviceMapBean.getFinished_model_name());
        int module_device_id = getModuleDeviceId(deviceMapBean.getModule_manufacture_name(), deviceMapBean.getModule_device_type(), deviceMapBean.getModule_model_name());

        String query = " insert into device_map(finished_device_id,module_device_id,remark) "
                + " values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, finished_device_id);
            pstmt.setInt(2, module_device_id);
            pstmt.setString(3, deviceMapBean.getRemark());
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

    public int getFinishedDeviceId(String manufactureName, String deviceType, String modelName) {
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
            System.out.println("Error inside getFinishedDeviceId DeviceMapModel" + e);
        }
        return finished_device_id;
    }

    public int getModuleDeviceId(String manufactureName, String deviceType, String modelName) {
        String query1 = "Select d.id as module_device_id from manufacturer m, device_type dt, model mo, device d "
                + "where d.manufacture_id = m.id and d.device_type_id = dt.id and d.model_id = mo.id "
                + "and d.active = 'Y' and m.active = 'Y' and dt.active = 'Y' and mo.active = 'Y'"
                + "and m.name = ? and dt.type = ? and mo.device_name = ?;";
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
            System.out.println("Error inside getModuleDeviceId DeviceMapModel" + e);
        }
        return module_device_id;
    }

    public int getParentId(String parent_type) {
        String query = "select id from device_type where active='Y' and type='" + parent_type + "' ";
        int parent_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //System.out.println("get parent query -" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                parent_id = rset.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error: DeviceTypeModel[getParentId]--" + e);
        }
        return parent_id;
    }

    public int getParentGeneration(String parent_type) {
        String query = "SELECT generation FROM device_type WHERE type='" + parent_type + "' and active='Y' ";
        int generation = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //System.out.println("generation query --" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                generation = rset.getInt("generation");
            }
        } catch (Exception e) {
            System.out.println("Error: DeviceTypeModel--" + e);
        }
        return generation;
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

    /**
     * @return the module_table_id
     */
    public String getModule_table_id() {
        return module_table_id;
    }

    /**
     * @param module_table_id the module_table_id to set
     */
    public void setModule_table_id(String module_table_id) {
        this.module_table_id = module_table_id;
    }

}
