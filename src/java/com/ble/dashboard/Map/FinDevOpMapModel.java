/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.Map;

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
public class FinDevOpMapModel {

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
    public int model_count = 0;
    public int operation_count = 0;

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
        String query = " select name from manufacturer where active='Y' group by name order by id desc ";
        try {
            System.err.println("---- before connection ---");
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            System.err.println("---- after connection ---");
            int count = 0;
            q = q.trim();
            while (rset.next()) { // move cursor from BOR to valid record.
                String str = rset.getString(1);
                System.err.println("---- str ---" + str);
                if (str.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(str);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getManufacturerName()-" + e);
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
                list.add("No such Device Type exists.......");
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceTypeName()-" + e);
        }
        return list;
    }

    public List<String> getDeviceName(String q, String mf_name, String dev_type) {
        List<String> list = new ArrayList<String>();
        int man_id = getManufactureId(mf_name);
        int dev_typ_id = getDeviceTypeId(dev_type);
        String query = "select m.device_name from device d, model m  where d.manufacture_id=" + man_id + " and d.device_type_id=" + dev_typ_id + " "
                + " and d.active='Y' and m.active='Y' and m.id=d.model_id ";
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
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceName()-" + e);
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
                list.add("No such Device No exists.......");
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceNo()-" + e);
        }
        return list;
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
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getManufactureId()-" + e);
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
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceTypeId()-" + e);
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
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getModelId()-" + e);
        }
        return model_id;
    }

    public int getDeviceId(int manufacture_id, int device_type_id, int model_id) {
        String query1 = "select d.id from device d where d.manufacture_id=? and d.device_type_id=? "
                + " and d.model_id=? and d.active='Y' ";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setInt(1, manufacture_id);
            stmt.setInt(2, device_type_id);
            stmt.setInt(3, model_id);
            System.err.println("device id -" + stmt);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceId()-" + e);
        }
        return device_id;
    }

    public List<FinDevOpMapBean> showData(int device_id) {
        List<FinDevOpMapBean> list = new ArrayList<FinDevOpMapBean>();
        List<FinDevOpMapBean> list1 = null;
        int count = 0;
        //  String query2= " select id,device_name from model where active='Y'";
        String query2 = " select m.id,d.id as device_id,m.device_name,dt.id as device_type_id,dt.type as device_type "
                + " from device_map as map,device as d , model as m , device_type as dt"
                + " where map.finished_device_id='" + device_id + "' and map.module_device_id = d.id and d.model_id = m.id and "
                + " d.device_type_id = dt.id and dt.active='Y' and d.active='Y' and map.active='Y' and m.active='Y' ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            System.err.println("show dat a-" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                FinDevOpMapBean deviceBean = new FinDevOpMapBean();
                int id = rset.getInt("device_id");
                deviceBean.setDevice_id(rset.getInt("id"));
                deviceBean.setDevice_registration_id(rset.getInt("id"));
                deviceBean.setDevice_type_id(rset.getInt("device_type_id"));
                deviceBean.setDevice_name(rset.getString("device_name"));
                List<FinDevOpMapBean> operationList = showOffenceData(id);
                deviceBean.setDeviceregBean(operationList);

                model_count++;
                deviceBean.setModel_count(count);
                deviceBean.setOperation_count(operation_count);
                list.add(deviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return list;
    }

    public List<FinDevOpMapBean> showOffenceData(int model_id) {
        List<FinDevOpMapBean> list = new ArrayList<FinDevOpMapBean>();
        String query;
        operation_count = 0;
        try {
            query = "  select dcm.device_command_id,opn.id,"
                    + " dcm.device_id,dcm.remark,dcm.delay,opn.operation_name,mf.name,m.device_name,m.device_no,dt.type"
                    + " from device_command_map dcm,device d,operation_name opn ,manufacturer mf,model m,device_type dt "
                    + " where dcm.device_id=d.id  and dcm.operation_id=opn.id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + model_id + "'"
                    + " and dcm.active='Y' and d.active='Y' and opn.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y' "
                    + " group by (opn.operation_name) "
                    + " order by (opn.id) asc";
            PreparedStatement pstmt = connection.prepareStatement(query);
            System.err.println("showOffenceData -" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                FinDevOpMapBean drb = new FinDevOpMapBean();
                int id = rset.getInt("id");
                drb.setOperation_id(rset.getInt("id"));
                drb.setOperation_name(rset.getString("operation_name"));
                List<FinDevOpMapBean> commandList = showOffenceData1(id, model_id);
                //drb.setCommandListBean(commandList);
                list.add(drb);
                operation_count++;
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.showOffenceData() -" + e);
        }
        return list;
    }

    public List<FinDevOpMapBean> showOffenceData1(int operation_id, int device_id) {
        List<FinDevOpMapBean> list = new ArrayList<FinDevOpMapBean>();
        String query;
        try {
            query = " select dcm.device_command_id,c.id,"
                    + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type"
                    + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
                    + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + device_id + "' and opn.id='" + operation_id + "'"
                    + " and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            System.err.println("show offence data 1 -" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                FinDevOpMapBean drb = new FinDevOpMapBean();
                int id = rset.getInt("id");
                drb.setCommand_id(rset.getInt("id"));
                drb.setCommand(rset.getString("command"));
                drb.setOrder_no(rset.getString("order_no"));
                drb.setDelay(rset.getString("delay"));
                list.add(drb);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.showOffenceData1() --" + e);
        }
        return list;
    }

    public void saveRecord(String model_name, int model_count, List<Integer> operationCountList, List<Integer> modelCheckList, List<Integer> operationCheckList) {
        PreparedStatement psmt = null;
        ResultSet rst = null;
        String query = "";
        // parent_model_id as finished_device_id
        int c = 0, parent_model_id = 0;
        try {

            parent_model_id = getParentModelId(model_name);

            if (parent_model_id != 0) {
                for (int i = 0; i < model_count; i++) {
                    int model_id = 0;
                    int device_map_id = 0;
                    if (modelCheckList.get(i) != 0) {
                        model_id = getModelId(modelCheckList.get(i), model_name);
                        device_map_id = getDeviceMapId(parent_model_id, model_id);

                        for (int j = i; j < operationCountList.size(); j++) {
                            int operation_count = operationCountList.get(j);
                            for (int k = 0; k < operationCheckList.size(); k++) {
                                int operation_id = operationCheckList.get(k);
                                if (operation_id != 0) {
                                    c = insertIntoTable(operation_id, device_map_id, model_id);
                                    if (c > 0) {
                                        message = "Record Saved Successfully!";
                                        msgBgColor = COLOR_OK;
                                    } else {
                                        message = "Cannot save the record, some error!";
                                        msgBgColor = COLOR_ERROR;
                                    }
                                }
                            }
                        }

                    } else {

                    }
                }
            } else {

            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.saveRecord()-" + e);
        }
    }

    public int getParentModelId(String model_name) {
        int id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        try {
            String query = " select d.id from model m, device d where m.active='Y' and d.active='Y' and m.id=d.model_id and "
                    + " m.device_name='" + model_name + "' ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getParentModelId()-" + e);
        }
        return id;
    }

    public int getModelId(int model_id, String model_name) {
        int id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        try {
            String query = " select d.id from model m, device d where m.active='Y' and d.active='Y' and d.model_id='" + model_id + "' and "
                    + " m.device_name='" + model_name + "' ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getParentModelId()-" + e);
        }
        return id;
    }

    public int getDeviceMapId(int parent_model_id, int model_id) {
        int id = 0;
        PreparedStatement psmt;
        ResultSet rst;
        try {
            String query = " select * from device_map where active='Y' and finished_device_id='" + parent_model_id + "' "
                    + " and module_device_id='" + model_id + "' ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.getDeviceMapId() -" + e);
        }
        return id;
    }

    public int insertIntoTable(int operation_id, int device_map_id, int model_id) {
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            String query = " select * from device_command_map where active='Y' and operation_id=" + operation_id + " "
                    + " and device_id=" + model_id + " ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                int device_command_id = rst.getInt(1);

                String query2 = " insert into fin_device_op_map(device_map_id,operation_id,device_command_id) "
                        + " values(?,?,?) ";
                PreparedStatement psmt2 = connection.prepareStatement(query2);
                psmt2.setInt(1, device_map_id);
                psmt2.setInt(2, operation_id);
                psmt2.setInt(3, device_command_id);
                count = psmt2.executeUpdate();
                if(count>0){
                    connection.commit();
                }else{
                    connection.rollback();
                    count=0;
                }

            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.Map.FinDevOpMapModel.insertIntoTable() -" + e);
            count = 0;
        }
        return count;
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
