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
public class ModelTypeModel {

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
       

    public List<ModelTypeBean> showData() {
        List<ModelTypeBean> list = new ArrayList<ModelTypeBean>();
        PreparedStatement psmt;
        ResultSet rst;
        String query = "";
        try {
            query = " select * from modal_type order by id desc ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                ModelTypeBean bean = new ModelTypeBean();
                bean.setModel_type_id(rst.getString(1));
                bean.setModel_type(rst.getString(2));
                bean.setIs_active(rst.getString(3));
                bean.setRevision_no(rst.getString(4));
                bean.setCreated_by(rst.getString(5));
                bean.setCreated_at(rst.getString(6));                                
                bean.setRemark(rst.getString(7));                                
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.dataEntry.ModelTypeModel.showdata() -" + e);
        }
        return list;
    }
        

    public int insertRecord(ModelTypeBean bean) throws SQLException {            
        String query="";
        PreparedStatement psmt;
        int count=0;
        try {            
            connection.setAutoCommit(false);
            query=" insert into modal_type(type,remark) values(?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getModel_type());
            psmt.setString(2, bean.getRemark());            
            count=psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        }
        if (count > 0) {
            connection.commit();
            message = "Record Saved Successfully!";
            msgBgColor = COLOR_OK;
        } else {
            connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return count;
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
