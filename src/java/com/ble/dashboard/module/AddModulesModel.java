/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.module;

import com.ble.dashboard.*;
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
public class AddModulesModel {

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
            System.out.println("CommandModel setConnection() Error: " + e);
        }
    }

    public List<AddModulesBean> getModulesType() {
        List<AddModulesBean> list = new ArrayList<AddModulesBean>();

        try {
            String query = " select * from modules";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                AddModulesBean bean = new AddModulesBean();
                bean.setModule_table_id(rst.getString(1));
                bean.setModule_name(rst.getString(2));
                bean.setModule_id(rst.getString(3));
                bean.setModule_category(rst.getString(4));
                bean.setModule_brand(rst.getString(5));
                bean.setDatasheet_link(rst.getString(6));
                bean.setDevelopers_note(rst.getString(7));
                bean.setModule_active(rst.getString(10));
                bean.setDate(rst.getString(13));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.module.AddModulesModel.getModulesType() -" + e);
        }
        return list;

    }

    public void insertData(Map<String, String> map, Iterator itr, List<String> categorySet, List<String> brandSet, List<String> attributeSet, List<String> valueSet) throws SQLException {
        String query1 = "", query2 = "", query3 = "", max_id = "";
        PreparedStatement psmt;
        ResultSet rst;
        try {
            connection.setAutoCommit(false);
            query1 = " select coalesce(max(attribute_value_id)+1,1) as max from attribute_value ";
            psmt = connection.prepareStatement(query1);
            rst = psmt.executeQuery();
            while (rst.next()) {
                max_id = rst.getString(1);
            }
            psmt = null;
            rst = null;
            for (int i = 0; i < attributeSet.size(); i++) {
                query2 = " insert into attribute_value(attribute_value_id,attribute,att_value) "
                        + "values(?,?,?)";
                psmt = connection.prepareStatement(query2);
                psmt.setString(1, max_id);
                psmt.setString(2, attributeSet.get(i));
                psmt.setString(3, valueSet.get(i));
                System.err.println("query attribute --" + psmt);
                psmt.executeUpdate();
            }
            psmt = null;
            rst = null;
            String image_id = "";
            String image_query = " select coalesce(max(image_id)+1,1) as max from image ";
            psmt = connection.prepareStatement(image_query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                image_id = rst.getString(1);
            }

            WriteImage(image_id, itr);

            psmt = null;
            rst = null;
            query3 = "insert into modules(module_name,module_id,category,brand,datasheet_link,developers_note,"
                    + " image_id,attribute_value_id) values(?,?,?,?,?,?,?,?)";
            psmt = connection.prepareStatement(query3);
            psmt.setString(1, map.get("module_name"));
            psmt.setString(2, map.get("module_id"));
            psmt.setString(3, map.get("category"));
            psmt.setString(4, map.get("brand"));
            psmt.setString(5, map.get("datasheet_link"));
            psmt.setString(6, map.get("developers_note"));
            psmt.setString(7, image_id);
            if (attributeSet.size() != 0) {
                psmt.setString(8, max_id);
            } else {
                psmt.setString(8, null);
            }
            int count = psmt.executeUpdate();
            if (count > 0) {
                String qry = " select * from modules";
                ResultSet rstt = connection.prepareStatement(qry).executeQuery();
                while (rstt.next()) {
                    setModule_table_id(rstt.getString(1));
                }

                connection.commit();
                message = "Record Inserted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Record Not updated Some Error!";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Exception com.ble.dashboard.module.AddModulesModel.insertData() -" + e);
        } finally {

        }
        //WriteImage(mod_name, itr);
    }

    public void WriteImage(String image_id, Iterator itr) {
        try {
            String uploadPath = "C:\\Images\\";
            //bean.setUid_image_path(uploadPath + bean.getUid_image_name());
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(uploadPath);
                try {
                    if (!item.isFormField()) {
                        System.err.println("image size --" + item.getSize());
                        System.err.println("image field name --" + item.getFieldName());
                        if (item.getSize() > 0) {
                            String image = item.getName();
                            String image_path = uploadPath + image;
                            File savedFile = new File(uploadPath + image);
                            item.write(savedFile);
                            //}
                            String query = " insert into image(image_id,image_name,image_path) values(?,?,?)";
                            PreparedStatement psmt = connection.prepareStatement(query);
                            psmt.setString(1, image_id);
                            psmt.setString(2, image);
                            psmt.setString(3, image_path);
                            psmt.executeUpdate();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Image upload error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }

    public List<AddModulesBean> showDeviceList() {
        List<AddModulesBean> list = new ArrayList<AddModulesBean>();

        try {
            String query = " select d.device_name,d.device_id,d.device_table_id,i.image_id "
                    + " from devices d "
                    + " left join image i on d.image_id = i.image_id ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                AddModulesBean bean = new AddModulesBean();
                bean.setDevice_table_id(rst.getString(3));
                bean.setDevice_name(rst.getString(1));
                bean.setDevice_id(rst.getString(2));
                bean.setDevice_image_path(rst.getString(4));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.module.AddModulesModel.showDeviceList() -" + e);
        }
        return list;

    }

    public void insertMappingData(String module_table_id, String[] device_table_id) {
        String query = "";
        PreparedStatement psmt;
        int count = 0;
        //String dev_table_id=device_table_id.toString();
        try {
            connection.setAutoCommit(false);
            for (int i = 0; i < device_table_id.length; i++) {
                query = " insert into moduleDeviceMapping(module_table_id,device_table_id) values(?,?)";
                psmt = connection.prepareStatement(query);
                psmt.setString(1, module_table_id);
                psmt.setString(2, device_table_id[i]);
                count = psmt.executeUpdate();
            }
            if (count > 0) {
                connection.commit();
                message = "Record Mapped Successfully.";
                msgBgColor = COLOR_OK;
            } else {
                connection.commit();
                message = "Some error occured.";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.module.AddModulesModel.insertMappingData() " + e);
        }
    }
    
    
    public String getImagePath(String image_id) {
        String image_path="";

        try {
            String query = " select * from image where image_id='"+image_id+"' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                AddModulesBean bean = new AddModulesBean();
                image_path=rst.getString(3);                                
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.module.AddModulesModel.showDeviceList() -" + e);
        }
        return image_path;

    }
    

    // end Sub Modules image data
    // Dashboard Enf
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
