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
public class DeviceTypeModel {

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

    public List<DeviceTypeBean> showData() {
        List<DeviceTypeBean> list = new ArrayList<DeviceTypeBean>();
        PreparedStatement psmt;
        ResultSet rst;
        String query = "";
        try {
            query = " select id,type as child,remark,is_super_child,IFNULL(parent_id,'')as parent_id,generation,active "
                    + " from device_type where active='Y' order by generation ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                DeviceTypeBean bean = new DeviceTypeBean();
                bean.setDevice_type_id(rst.getString(1));
                bean.setChild_type(rst.getString(2));
                bean.setRemark(rst.getString(3));
                bean.setIs_super_child(rst.getString(4));
                bean.setParent_id(rst.getString(5));
                bean.setGeneration(rst.getString(6));
                bean.setIs_active(rst.getString(7));

                String parent_type = getParentTypeName(bean.getParent_id());
                bean.setParent_type(parent_type);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.dataEntry.DeviceTypeModel.showdata() -" + e);
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

    public int insertRecord(DeviceTypeBean bean) throws SQLException {
        System.err.println("yahan aa gaya");
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;
        is_child = bean.getIs_super_child();
        //active = bean.getIs_active();

        //int orgid = getOrganisation_id(bean.getOrganisation());
        String child_type = bean.getChild_type();
        String parent_type = bean.getParent_type();
        int parent_id = getParentId(parent_type);
        int generation = 0;
        if (parent_type.equals("")) {
            generation = 1;
        } else {
            generation = getParentGeneration(parent_type) + 1;
        }
        if (child_type.equals(parent_type)) {
            message = "Sorry! Parent-Child cannot be same!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        // to check if parent exist or not
        String qry2 = "select count(*) from device_type where "
                + " type='" + child_type + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            //System.out.println("query 1 -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        //
        String query1 = "select count(*) "
                + " from device_type where "
                + " parent_id='" + parent_id + "' and "
                + " type='" + child_type + "' and active='Y' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            //System.out.println("query 2 -" + pst);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        String query = "insert into device_type(type, "
                + " parent_id,is_super_child,remark,created_by,generation) "
                + " values (?,?,?,?,?,?) ";

        //int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, child_type);
            if (parent_id == 0) {
                pstmt.setString(2, null);
            } else {
                pstmt.setInt(2, parent_id);
            }
            pstmt.setString(3, is_child);
            pstmt.setString(4, bean.getRemark());
            pstmt.setString(5, "Test");
            pstmt.setInt(6, generation);

            System.out.println("insert query 3 -" + pstmt);

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        }
        if (rowsAffected > 0) {
            connection.commit();
            message = "Record Saved Successfully!";
            msgBgColor = COLOR_OK;
        } else {
            connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
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
