/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.model;

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
public class ModelModel {

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

    public List<String> getModelType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select * from modal_type where active='Y' ";
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
                list.add("No such Model Type exists.......");
            }
        } catch (Exception e) {
            System.out.println("getModelType ERROR inside ModelModel - " + e);
        }
        return list;
    }

    
    public void insertData(Map<String, String> map, Iterator itr, List<String> modulesSet) throws SQLException {
        String query1 = "", query2 = "", query3 = "", max_id = "", query4 = "", query5 = "", query6 = "";
        PreparedStatement psmt;
        ResultSet rst;
        try {

            connection.setAutoCommit(false);
            String image_id = "", model_type_id = "", model_id = "";
            String image_query = " select coalesce(max(image_id)+1,1) as max from image ";
            psmt = connection.prepareStatement(image_query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                image_id = rst.getString(1);
            }

            WriteImage(image_id, itr);

            psmt = null;
            rst = null;

            query5 = " select * from modal_type where type='" + map.get("model_type") + "' and active='Y' ";
            psmt = connection.prepareStatement(query5);
            rst = psmt.executeQuery();
            while (rst.next()) {
                model_type_id = rst.getString(1);
            }

            psmt = null;
            rst = null;
            query3 = " insert into model(device_name,device_no,warranty_period,model_type_id,device_address,no_of_module) "
                    + " values(?,?,?,?,?,?) ";
            psmt = connection.prepareStatement(query3);
            psmt.setString(1, map.get("device_name"));
            psmt.setString(2, map.get("device_no"));
            psmt.setString(3, map.get("warranty_period"));
            psmt.setString(4, model_type_id);
            psmt.setString(5, map.get("device_address"));
            psmt.setString(6, map.get("no_of_module"));
            
            int count = psmt.executeUpdate();

            if (count > 0) {
                connection.commit();
                message = "Record Inserted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Record Not updated Some Error!";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Exception com.ble.dashboard.device.DeviceModel.insertData() -" + e);
        } finally {

        }        
    }

    public void WriteImage(String image_id, Iterator itr) {
        try {
            String uploadPath = "C:\\Images\\Model\\";
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

    public List<ModelBean> showModelData() {
        List<ModelBean> list = new ArrayList<ModelBean>();

        try {
            String query = " select * from model order by id desc ";

            //  System.out.println("query--"+query);
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {

                ModelBean bean = new ModelBean();
                bean.setModel_id(rst.getString(1));
                bean.setDevice_name(rst.getString(2));
                bean.setDevice_no(rst.getString(3));
                bean.setWarranty_period(rst.getString(4));
                bean.setIs_active(rst.getString(5));
                bean.setCreated_by(rst.getString(6));
                bean.setRevision_no(rst.getString(7));
                bean.setCreated_at(rst.getString(8));
                bean.setRemark(rst.getString(9));
                bean.setModel_type_id(rst.getString(10));
                bean.setDevice_address(rst.getString(11));
                bean.setNo_of_module(rst.getString(12));

                String type = getDeviceTypeName(bean.getModel_type_id());
                bean.setModel_type(type);
                
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.model.ModelModel.showModelData() -" + e);
        }
        return list;
    }
    
    public String getDeviceTypeName(String parentId) {
        String query = "select type from modal_type where id=? and active='Y' ";
        String parent_type = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, parentId);
            //System.out.println("queryyy type name -"+pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                parent_type = rset.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error: ModelModel[getDeviceTypeName]--" + e);
        }
        return parent_type;
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
