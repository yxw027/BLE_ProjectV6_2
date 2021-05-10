/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.device;

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
public class DeviceModel {

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

    public List<String> getManufacturerName(String q) {
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
            System.out.println("getManufacturerName ERROR inside DeviceModel - " + e);
        }
        return list;
    }

    public List<String> getDeviceType(String q, String type) {
        List<String> list = new ArrayList<String>();
        String query = " select * from device_type where active='Y' ";
        if (type.equals("non-finished")) {
            query += " and type !='finished' ";
        }
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

    public List<String> getModelName(String q, String type) {
        List<String> list = new ArrayList<String>();
        String query = " select * from model where active='Y' ";
//        if(type.equals("non-finished")){
//            query+=" and type='finished' ";
//        }
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
                list.add("No such Model Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getModelName ERROR inside DeviceModel - " + e);
        }
        return list;
    }

    public List<String> getModelNo(String q, String model_name) {
        List<String> list = new ArrayList<String>();
        String query = " select * from model where active='Y' and device_name='" + model_name + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) { // move cursor from BOR to valid record.
                String str = rset.getString(3);
                if (str.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(str);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Model No exists.......");
            }
        } catch (Exception e) {
            System.out.println("getModelNo ERROR inside DeviceModel - " + e);
        }
        return list;
    }

    public void insertData(Map<String, String> map, Iterator itr, List<String> modulesSetMF, List<String> modulesSetDT, List<String> modulesSetMN) throws SQLException {
        String query1 = "", query2 = "", query3 = "", max_id = "", query4 = "", query5 = "", query6 = "";
        PreparedStatement psmt;
        ResultSet rst;
        try {

            connection.setAutoCommit(false);
            String image_id = "", manufacture_id = "", device_type_id = "", model_id = "";
            String image_query = " select coalesce(max(image_id)+1,1) as max from image ";
            psmt = connection.prepareStatement(image_query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                image_id = rst.getString(1);
            }

            WriteImage(image_id, itr);

            //if (!map.get("device_type").equals("Finished")) {
            if (!modulesSetDT.get(0).equals("Finished")) {

                psmt = null;
                rst = null;

                //query4 = " select * from manufacturer where name='" + map.get("manufacturer_name") + "' ";
                query4 = " select * from manufacturer where name='" + modulesSetMF.get(0) + "' ";

                System.out.println("manufacture..." + query4);

                psmt = connection.prepareStatement(query4);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    manufacture_id = rst.getString(1);
                }

                psmt = null;
                rst = null;
                //query5 = " select * from device_type where type='" + map.get("device_type") + "' ";
                query5 = " select * from device_type where type='" + modulesSetDT.get(0) + "' ";
                psmt = connection.prepareStatement(query5);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    device_type_id = rst.getString(1);
                }

                psmt = null;
                rst = null;
                //query6 = " select * from model where device_name='" + map.get("model_name") + "' ";
                query6 = " select * from model where device_name='" + modulesSetMN.get(0) + "' ";
                psmt = connection.prepareStatement(query6);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    model_id = rst.getString(1);
                }

                psmt = null;
                rst = null;
                query3 = "insert into device(manufacture_id,device_type_id,model_id,image_id,remark) "
                        + " values(?,?,?,?,?) ";
                psmt = connection.prepareStatement(query3);

                psmt.setString(1, manufacture_id);
                psmt.setString(2, device_type_id);
                psmt.setString(3, model_id);
                psmt.setString(4, image_id);
                psmt.setString(5, map.get("remark"));
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

            } else {

                int finished_device_id = 0;
                int c = 0;
                int count = 0;
                c = Integer.parseInt(map.get("no_of_module")) + 1;
                for (int a = 0; a < c; a++) {

                    
                        System.err.println("mf name index -" + modulesSetMF.get(a));
                        psmt = null;
                        rst = null;

                        //query4 = " select * from manufacturer where name='" + map.get("manufacturer_name") + "' ";
                        query4 = " select * from manufacturer where name='" + modulesSetMF.get(a) + "' ";

                        System.out.println("manufacture..." + query4);

                        psmt = connection.prepareStatement(query4);
                        rst = psmt.executeQuery();
                        while (rst.next()) {
                            manufacture_id = rst.getString(1);
                        }

                        psmt = null;
                        rst = null;
                        //query5 = " select * from device_type where type='" + map.get("device_type") + "' ";
                        query5 = " select * from device_type where type='" + modulesSetDT.get(a) + "' ";
                        psmt = connection.prepareStatement(query5);
                        rst = psmt.executeQuery();
                        while (rst.next()) {
                            device_type_id = rst.getString(1);
                        }

                        psmt = null;
                        rst = null;
                        //query6 = " select * from model where device_name='" + map.get("model_name") + "' ";
                        query6 = " select * from model where device_name='" + modulesSetMN.get(a) + "' ";
                        psmt = connection.prepareStatement(query6);
                        rst = psmt.executeQuery();
                        while (rst.next()) {
                            model_id = rst.getString(1);
                        }
                        
                        if (a == 0) {
                        psmt = null;
                        rst = null;
                        query3 = "insert into device(manufacture_id,device_type_id,model_id,image_id,remark) "
                                + " values(?,?,?,?,?) ";
                        psmt = connection.prepareStatement(query3);

                        psmt.setString(1, manufacture_id);
                        psmt.setString(2, device_type_id);
                        psmt.setString(3, model_id);
                        psmt.setString(4, image_id);
                        psmt.setString(5, map.get("remark"));
                        count = psmt.executeUpdate();

                        //}
                        //if (count > 0) {
                        //count = 0;
                        //if (a == 0) {
                        if (count > 0) {
                            psmt = null;
                            rst = null;
                            query4 = " select * from device where active='Y' and manufacture_id='" + manufacture_id + "' "
                                    + " and device_type_id='" + device_type_id + "' and model_id='" + model_id + "'"
                                    + " order by id desc limit 1 ";
                            psmt = connection.prepareStatement(query4);
                            rst = psmt.executeQuery();
                            while (rst.next()) {
                                finished_device_id = rst.getInt(1);
                            }
                        } else {
                            connection.rollback();
                            message = "Record Not updated Some Error!";
                            msgBgColor = COLOR_ERROR;
                        }

                    } else {
                        int module_device_id = 0;
                        psmt = null;
                        rst = null;
                        query4 = " select * from device where active='Y' and manufacture_id='" + manufacture_id + "' "
                                + " and device_type_id='" + device_type_id + "' and model_id='" + model_id + "'"
                                + " order by id desc limit 1 ";
                        psmt = connection.prepareStatement(query4);
                        rst = psmt.executeQuery();
                        while (rst.next()) {
                            module_device_id = rst.getInt(1);
                        }

                        psmt = null;
                        rst = null;
                        query5 = " insert into device_map(finished_device_id,module_device_id) "
                                + " values(?,?) ";
                        psmt = connection.prepareStatement(query5);
                        psmt.setInt(1, finished_device_id);
                        psmt.setInt(2, module_device_id);
                        count = psmt.executeUpdate();
                    }

//                    } else {
//                        connection.rollback();
//                        message = "Record Not updated Some Error!";
//                        msgBgColor = COLOR_ERROR;
//                    }
                }

                if (count > 0) {
                    connection.commit();
                    message = "Record Inserted successfully.";
                    msgBgColor = COLOR_OK;
                } else {
                    connection.rollback();
                    message = "Record Not updated Some Error!";
                    msgBgColor = COLOR_ERROR;
                }

            }

        } catch (Exception e) {
            System.out.println("Exception com.ble.dashboard.device.DeviceModel.insertData() -" + e);
            connection.rollback();
            message = "Record Not updated Some Error!";
            msgBgColor = COLOR_ERROR;
        } finally {

        }
        //WriteImage(mod_name, itr);
    }

    public void WriteImage(String image_id, Iterator itr) {
        try {
            String uploadPath = "C:\\Images\\Device\\";
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

    public List<DeviceBean> getDevice(String idd) {
        List<DeviceBean> list = new ArrayList<DeviceBean>();
        System.err.println("idddd model -" + idd);
        try {
            String query = " select mr.name,dt.type,m.device_name,m.device_no,d.remark,d.active,d.created_at,d.id "
                    + "from device d,manufacturer mr,device_type dt,model m "
                    + "where d.manufacture_id=mr.id and d.device_type_id=dt.id and d.model_id=m.id and d.active='Y' ";
            if (!idd.equals("")) {
                query += " and d.id='" + idd + "' ";
            }

            System.out.println("query--" + query);
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {

                DeviceBean bean = new DeviceBean();
                bean.setManufacturer_name(rst.getString(1));
                bean.setDevice_type(rst.getString(2));
                bean.setModel_name(rst.getString(3));
                bean.setModel_no(rst.getString(4));
                bean.setRemark(rst.getString(5));
                bean.setDevice_active(rst.getString(6));
                bean.setDate(rst.getString(7));
                bean.setDevice_id(rst.getString(8));

                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.device.DeviceModel.getDevice() -" + e);
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
