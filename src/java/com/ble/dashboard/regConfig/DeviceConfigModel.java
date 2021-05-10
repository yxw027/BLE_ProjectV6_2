/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.regConfig;

import com.ble.device.bean.DeviceBean;
//import com.ble.device.bean.DeviceConfigBean;
import com.ble.webService.controller.BLEWebServicesController;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author saini
 */
public class DeviceConfigModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static String isOperationArr[];
    public static String isModuleOperation;
    public static List<String> commandAll;
    public static int gB;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
        }
    }

    public String sendConnectionReq(String device_type) {
        String status = "";
        //String reg_no_pass = saveDeviceReg(device_type);    
        SocketAddress socketaddress = new InetSocketAddress("192.168.1.38", 8090);
//    SocketAddress socketaddress = new InetSocketAddress("120.138.10.197", 8090);
        Socket socket = new Socket();
        int timeout = 4000;
        try {
            socket.connect(socketaddress, timeout);
            String result = "for to get lat lon";
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeChars(result);
            //outputStream.close();
//    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//    byte[] bytes = new byte[30];   
//    inputStream.read(bytes);
//    getlatlon = new String(bytes); 

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            String decodeString = reader.readLine();
            status = decodeString;
            System.out.println("total data ::  " + decodeString);
            // inputStream.close();
        } catch (SocketTimeoutException exception) {
            System.out.println("Socket Timeout Exception ::: " + exception);
        } catch (IOException exception) {
            System.out.println("Unable to connect Exception ::: " + exception);
        }
        return status;
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

    public String getLatestRegNo() {
        String query1 = "select reg_no from device_registration m "
                + " where m.active='Y'"
                + " order by reg_no desc limit 1";

        String reg_no = "D_1";
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
            System.err.println("device id -" + stmt);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_id;
    }

    public String saveDeviceReg(String device_type, String manu_name, String device_name, String device_no) {
        String status = "";
        int manufacture_id = getManufactureId(manu_name);
        int device_type_id = getDeviceTypeId(device_type);
        int model_id = getModelId(device_name, device_no);
        int device_id = getDeviceId(manufacture_id, device_type_id, model_id);
        String lat_re_no = getLatestRegNo();
        int update_reg_no = Integer.parseInt(lat_re_no.split("_")[1]) + 1;
        String reg_no1 = "D" + update_reg_no;
        Random random = new Random();
        int rand_int1 = random.nextInt(100000);
        String pass = "P_" + rand_int1;
        String query = " insert into device_registration(device_id,reg_no,password,remark)"
                + " values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, device_id);
            pstmt.setString(2, reg_no1);
            pstmt.setString(3, pass);
            pstmt.setString(4, "");
//            pstmt.setString(5,deviceRegistrationBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            saveDeviceAllocationStratus(rowsAffected);
            String status1 = "$$$$,04,0,6," + device_id + "," + pass + ",120.138.10.146,8060,45.114.142.35,8060,12,####";
            sendConnectionReq(status1);
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return status;
    }

    public void saveDeviceAllocationStratus(int device_registration_id) {
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

    public List<String> getManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' "
                + " group by name order by id desc ";
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

    public List<DeviceConfigBean> showData(int device_id) {
        List<DeviceConfigBean> list = new ArrayList<DeviceConfigBean>();
        List<DeviceConfigBean> list1 = null;
        //  String query2= " select id,device_name from model where active='Y'";
        String query2 = " select m.id,d.id as device_id,m.device_name,dt.id as device_type_id,dt.type as device_type from device_map as map,device as d , model as m , device_type as dt"
                + "  where map.finished_device_id='" + device_id + "' and map.module_device_id = d.id and d.model_id = m.id and d.device_type_id = dt.id and dt.active='Y' and d.active='Y' and map.active='Y' and m.active='Y'";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            System.err.println("show dat a-" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceConfigBean deviceBean = new DeviceConfigBean();
                int id = rset.getInt("device_id");
                deviceBean.setDevice_registration_id(rset.getInt("id"));
                deviceBean.setDevice_type_id(rset.getInt("device_type_id"));
                deviceBean.setDevice_name(rset.getString("device_name"));
                // added after change in workbench Finished Device Operation Map table added
                int device_map_id = getDeviceMapId(device_id, id);
                List<DeviceConfigBean> operationList = showOffenceData(id,device_map_id);
                // added after change in workbench Finished Device Operation Map table added
                //List<DeviceConfigBean> operationList = showOffenceData(id);
                deviceBean.setDeviceregBean(operationList);

                list.add(deviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return list;
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
            System.out.println("com.ble.dashboard.regConfig.DeviceConfigModel.getDeviceMapId()-"+e);
        }
        return id;
    }

    public List<DeviceConfigBean> showOffenceData(int model_id, int device_map_id) {
        List<DeviceConfigBean> list = new ArrayList<DeviceConfigBean>();
        String query;
        try {
//            query = "select op.id,op.operation_name from device_command_map as dcm , device as d , model as m , operation_name as op " +
//                    " where m.id = d.model_id and d.id = dcm.device_id and dcm.operation_id = op.id and dcm.active='Y' and d.active='Y' "
//                    + " and m.active='Y' and op.active='Y' and m.id='" + model_id + "'";
            query = "  select dcm.device_command_id,opn.id,"
                    + " dcm.device_id,dcm.remark,dcm.delay,opn.operation_name,mf.name,m.device_name,m.device_no,dt.type"
                    + " from device_command_map dcm,device d,operation_name opn ,manufacturer mf,model m,device_type dt, "
                    // added after change in workbench Finished Device Operation Map table added
                    + " fin_device_op_map fdom "
                    // added after change in workbench Finished Device Operation Map table added
                    + " where dcm.device_id=d.id  and dcm.operation_id=opn.id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + model_id + "'"
                    + " and dcm.active='Y' and d.active='Y' and opn.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y' "
                    // added after change in workbench Finished Device Operation Map table added
                    + " and opn.id=fdom.operation_id and fdom.device_map_id="+device_map_id+" "
                    // added after change in workbench Finished Device Operation Map table added
                    + " group by (opn.operation_name) "
                    + " order by (opn.id) asc";
            PreparedStatement pstmt = connection.prepareStatement(query);
            System.err.println("showOffenceData -" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceConfigBean drb = new DeviceConfigBean();
                int id = rset.getInt("id");
                drb.setOperation_id(rset.getInt("id"));
                drb.setOperation_name(rset.getString("operation_name"));
                List<DeviceConfigBean> commandList = showOffenceData1(id, model_id);
                drb.setCommandListBean(commandList);
                list.add(drb);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        return list;
    }

    public List<DeviceConfigBean> showOffenceData1(int operation_id, int device_id) {
        List<DeviceConfigBean> list = new ArrayList<DeviceConfigBean>();
        String query;
        try {
//            query = "select c.id,c.command from device_command_map as map, operation_name as op , command as c" +
//                    " where map.operation_id = op.id and map.command_id = c.id and op.id ='" + operation_id + "'";
            query = " select dcm.device_command_id,c.id,"
                    + " dcm.device_id,dcm.remark,dcm.order_no,dcm.delay,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type"
                    + " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt "
                    + " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id and d.id='" + device_id + "' and opn.id='" + operation_id + "'"
                    + " and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'";
            PreparedStatement pstmt = connection.prepareStatement(query);
            System.err.println("show offence data 1 -" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceConfigBean drb = new DeviceConfigBean();
                int id = rset.getInt("id");
                drb.setCommand_id(rset.getInt("id"));
                drb.setCommand(rset.getString("command"));
                drb.setOrder_no(rset.getString("order_no"));
                drb.setDelay(rset.getString("delay"));
                list.add(drb);
            }
        } catch (Exception e) {
            System.out.println("Error:TrafficPoliceModel--showOffenceData " + e);
        }
        System.out.println("------------- command list size -----------------" + list.size());
        return list;
    }

    public String sendToShweta(String arr[], String arrOperation[], String arrCommand[]) throws InterruptedException {
        String status = "check";
        int j = 0;
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            int model_id = Integer.parseInt(arr[i]);
            String[] idsToOperation = arrOperation;
            for (j = 0; j < idsToOperation.length; j++) {

                int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
                if (model_id1 == model_id) {
                    for (k = 0; k < arrCommand.length; k++) {
                        int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
                        int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);
                        if (command_id1 == op_id) {

                            isModuleOperation = "$$$$,05,23,5," + model_id + "," + idsToOperation[j].split(",")[0] + ",1,2,123," + arrCommand[k].split(",")[0] + ",23,####";
                            System.out.println(isModuleOperation + "<br>");
                            Thread.sleep(3 * 1000);
                        }

                    }
                    k = 0;
//                 isModuleOperation = "$$$$,05,23,5,"+model_id+","+idsToOperation[j].split(",")[0]+",1,2,123,robinsingh,23,####";
//                 System.out.println(isModuleOperation + "<br>");
                }
                //  Thread.sleep(10 * 1000);
            }
            j = 0;
        }
        isModuleOperation = "$$$$,end of file,####";
        //isOperationArr = arr;
        return status;
    }

    public String sendCommandForControllerTesting(String arr[], String arrOperation[], String arrCommand[], int finished_id, String reg_no) throws InterruptedException {
        String status = "check";
        BLEWebServicesController ble = new BLEWebServicesController();
        String startPacket = "$$$$,07," + reg_no + ",01,startpacket,12,####";
        String endPacket = "$$$$,06," + reg_no + ",01,endpacket,12,####";
        int j = 0, k = 0, a = 0;
        commandAll = new ArrayList<String>();
        try {
            commandAll.add(startPacket);

            System.err.println("---- arra size ---" + arr.length);
            for (int i = 0; i < arr.length; i++) {
                System.err.println("- under arr val of i -----------" + i);
                isModuleOperation = "end of file";
                int model_id = Integer.parseInt(arr[i].split(",")[0]);
                int device_type_id = Integer.parseInt(arr[i].split(",")[1]);
                String[] idsToOperation = arrOperation;
                System.err.println("---- idsToOperation size ---" + idsToOperation.length);
                for (j = 0; j < idsToOperation.length; j++) {
                    int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
                    if (model_id1 == model_id) {
                        System.err.println("---- arrCommand size ---" + arrCommand.length);
                        for (k = 0; k < arrCommand.length; k++) {
                            int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
                            int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);
                            if (command_id1 == op_id) {
                                long startTime = System.currentTimeMillis();
                                while (true && (((System.currentTimeMillis() - startTime) / 1000) < 10)) {          //300 in sec
                                    String deviceResp = ble.deviceResponse;
                                    String abc1 = "start";
                                    if (deviceResp == null) {
                                        deviceResp = "";
                                    } else {
                                        int x = deviceResp.indexOf('$');
                                        if (x >= 0) {
                                            String cmd = deviceResp.split(",")[1];
                                            if (!cmd.equalsIgnoreCase("07")) {
                                                abc1 = deviceResp.split(",")[9];
                                            }
                                        }
                                    }
                                    String abc = "";
                                    String preSavedValue = "";
                                    if (isModuleOperation == null || isModuleOperation.equalsIgnoreCase("end of file")) {
                                        isModuleOperation = "end of file";
                                    } else {

                                        preSavedValue = isModuleOperation;
                                        abc = isModuleOperation.split(",")[9];
                                    }
                                    if (abc1.equalsIgnoreCase(abc)) {
                                        int re = insertDeviceConfigurationStatus(reg_no, finished_id, model_id, Integer.parseInt(idsToOperation[j].split(",")[0]), Integer.parseInt(arrCommand[k].split(",")[2]), arrCommand[k].split(",")[0]);
                                        isModuleOperation = null;
                                        break;
                                    } else {
                                        if (k == (arrCommand.length) - 1) {
                                            isModuleOperation = isModuleOperation;
                                        } else {
                                            //isModuleOperation = "$$$$,05," + reg_no + ",06," + device_type_id + "," + idsToOperation[j].split(",")[0] + "," + Integer.parseInt(arrCommand[k].split(",")[2]) + "," + Integer.parseInt(arrCommand[k].split(",")[3]) + ",123,{" + arrCommand[k].split(",")[0] + "},00,####";
                                            isModuleOperation = "$$$$,05," + reg_no + ",06," + device_type_id + "," + idsToOperation[j].split(",")[0] + "," + Integer.parseInt(arrCommand[k].split(",")[2]) + "," + Integer.parseInt(arrCommand[k].split(",")[3]) + ",123,{" + arrCommand[k].split(",")[0] + "},00,####";
                                            commandAll.add(isModuleOperation);
                                            a = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("com.ble.dashboard.regConfig.DeviceConfigModel.sendCommandForControllerTesting()-" + e);
        }
        System.err.println("----- command all sizeeee ----------" + commandAll.size());
        System.err.println("----- command all ----------" + commandAll.toString());
        return status;
    }

    public String sendCommandForController(String arr[], String arrOperation[], String arrCommand[], int finished_id, String reg_no) throws InterruptedException {
        String status = "check";
        boolean user = true;
        boolean user1 = true;
        BLEWebServicesController ble = new BLEWebServicesController();
        int j = 0;
        int k = 0;
        int a = 0;

        try {
            while (user1 == true) {
                isModuleOperation = "$$$$,07," + reg_no + ",01,startpacket,12,####";
                //isModuleOperation = "$$$$,07,D_2,01,startpacket,12,####";
                String startpacket = ble.deviceResponse;
                //System.err.println("-- start packet before condition ---"+startpacket);
                if (startpacket == null) {
                    startpacket = "";
                } else {
                    //System.out.println("mayank packet data .. " + startpacket);
                    int x1 = startpacket.indexOf('$');
                    if (x1 >= 0) {
                        startpacket = startpacket.split(",")[4];
                        //System.err.println("-- start packet after condition ---"+startpacket);
                    }
                }
                String startpacket1 = isModuleOperation.split(",")[4];
                //System.err.println("-- start packet 111111 before condition ---"+startpacket1);
                if (startpacket.equalsIgnoreCase(startpacket1)) {
                    user = false;
                    isModuleOperation = "end of file";
                    ble.deviceResponse = null;
                }
                if (user == false) {
                    System.err.println("-- array of command length ---"+arr.length);
                    System.err.println("-- array of arrayyyyycommand length ---"+arrCommand.length);
                    System.err.println("-- array of arrayyyyycommand elementsss ---"+arrCommand.toString());
                    for (int i = 0; i < arr.length; i++) {
                        isModuleOperation = "end of file";
                        int model_id = Integer.parseInt(arr[i].split(",")[0]);
                        System.err.println("--- model id -----"+model_id);
                        int device_type_id = Integer.parseInt(arr[i].split(",")[1]);
                        String[] idsToOperation = arrOperation;
                        System.err.println("-- array of operation length ---"+idsToOperation.length);
                        for (j = 0; j < idsToOperation.length; j++) {
                            int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
                            System.err.println("--- model id 1111 ----"+model_id1);
                            if (model_id1 == model_id) {
                                for (k = 0; k < arrCommand.length; k++) {
                                    int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
                                    int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);
                                    if (command_id1 == op_id) {
                                        long startTime = System.currentTimeMillis();
                                        System.err.println("----- start time /1000 < 10 ------"+(((System.currentTimeMillis() - startTime) / 1000)));
                                        while (true && (((System.currentTimeMillis() - startTime) / 1000) < 10)) {          //300 in sec
                                            String deviceResp = ble.deviceResponse;
                                            String abc1 = "start";
                                            if (deviceResp == null) {
                                                deviceResp = "";
                                            } else {
                                                int x = deviceResp.indexOf('$');
                                                if (x >= 0) {
                                                    String cmd = deviceResp.split(",")[1];
                                                    if (!cmd.equalsIgnoreCase("07")) {
                                                        abc1 = deviceResp.split(",")[9];
                                                    }
                                                }
                                            }
                                            String abc = "";
                                            String preSavedValue = "";
                                            if (isModuleOperation == null || isModuleOperation.equalsIgnoreCase("end of file")) {
                                                isModuleOperation = "end of file";
                                            } else {

                                                preSavedValue = isModuleOperation;
                                                abc = isModuleOperation.split(",")[9];
                                            }
                                            System.err.println("----- abc   ----"+abc);
                                            System.err.println("----- abc 11111  ----"+abc1);
                                            if (abc1.equalsIgnoreCase(abc)) {
                                                int re = insertDeviceConfigurationStatus(reg_no, finished_id, model_id, Integer.parseInt(idsToOperation[j].split(",")[0]), Integer.parseInt(arrCommand[k].split(",")[2]), arrCommand[k].split(",")[0]);
                                                isModuleOperation = null;
                                                break;
                                            } else {
                                                //if (k == (arrCommand.length) - 1) {
                                                if (k == (arrCommand.length)) {
                                                    isModuleOperation = isModuleOperation;
                                                } else {
                                                    isModuleOperation = "$$$$,05," + reg_no + ",06," + device_type_id + "," + idsToOperation[j].split(",")[0] + "," + Integer.parseInt(arrCommand[k].split(",")[2]) + "," + Integer.parseInt(arrCommand[k].split(",")[3]) + ",123,{" + arrCommand[k].split(",")[0] + "},00,####";                                                    
                                                    a = 0;
                                                }
                                            }
                                            System.out.println("----- isModuleOperation value  -----" + isModuleOperation);
                                        }
                                    }
                                }
                                k = 0;
                            }
                        }
                        j = 0;
                    }
                    user1 = false;
                }
            }
        } catch (Exception e) {
            System.out.println("some error .... " + e);
        }
        isModuleOperation = "end of file";
        //isOperationArr = arr;
        return status;
    }

    public String sendToShwetaTesting(String arr[], String arrOperation[], String arrCommand[], int finished_id, String reg_no) throws InterruptedException {
        String status = "check";
        boolean user = true;
        boolean user1 = true;
        BLEWebServicesController ble = new BLEWebServicesController();
        int j = 0;
        int k = 0;
        int a = 0;

        try {
            while (user1 == true) {
//                isModuleOperation = "$$$$,07,D_2,01,startpacket,12,####";
                isModuleOperation = "$$$$,07," + reg_no + ",01,startpacket,12,####";
                String startpacket = ble.deviceResponse;
                if (startpacket == null) {
                    startpacket = "";
                } else {
                    System.out.println("mayank packet data .. " + startpacket);
                    int x1 = startpacket.indexOf('$');
                    if (x1 >= 0) {
                        startpacket = startpacket.split(",")[4];
                    }
                }
                String startpacket1 = isModuleOperation.split(",")[4];
                if (startpacket.equalsIgnoreCase(startpacket1)) {
                    user = false;
                    isModuleOperation = "end of file";
                    ble.deviceResponse = null;
                }
                //            if(user == false){

                for (int i = 0; i < arr.length; i++) {
                    isModuleOperation = "end of file";
                    int model_id = Integer.parseInt(arr[i].split(",")[0]);
                    int device_type_id = Integer.parseInt(arr[i].split(",")[1]);
                    String[] idsToOperation = arrOperation;
                    for (j = 0; j < idsToOperation.length; j++) {
                        int model_id1 = Integer.parseInt(idsToOperation[j].split(",")[1]);
                        if (model_id1 == model_id) {
                            for (k = 0; k < arrCommand.length; k++) {
                                int op_id = Integer.parseInt(idsToOperation[j].split(",")[0]);
                                int command_id1 = Integer.parseInt(arrCommand[k].split(",")[1]);
                                System.err.println("------------ model command id -----------" + command_id1);
                                System.err.println("------------ model op id -----------" + op_id);
                                if (command_id1 == op_id) {
                                    long startTime = System.currentTimeMillis();
                                    //                        while(true && (System.currentTimeMillis()-startTime)<20000){
                                    while (true && (((System.currentTimeMillis() - startTime) / 1000) < 100)) {      // 300 in seconds
                                        String deviceResp = ble.deviceResponse;
                                        String abc1 = "start";
                                        if (deviceResp == null) {
                                            deviceResp = "";
                                        } else {

                                            int x = deviceResp.indexOf('$');
                                            if (x >= 0) {
                                                String cmd = deviceResp.split(",")[1];
                                                if (!cmd.equalsIgnoreCase("07")) {
                                                    abc1 = deviceResp.split(",")[9];
                                                }
                                            }
                                        }
                                        //  System.out.println("device resp ... "+deviceResp + "<br>");
                                        String abc = "";
                                        String preSavedValue = "";
                                        if (isModuleOperation == null || isModuleOperation.equalsIgnoreCase("end of file")) {
                                            isModuleOperation = "end of file";
                                        } else {
                                            preSavedValue = isModuleOperation;
                                            abc = isModuleOperation.split(",")[9];
                                        }

                                        if (abc1.equalsIgnoreCase(abc)) {
                                            // isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+arrCommand[k].split(",")[2]+","+arrCommand[k].split(",")[3]+",123,{"+arrCommand[k].split(",")[0]+"},00,####";

//                            int re = insertDeviceConfigurationStatus(finished_id,model_id,Integer.parseInt(idsToOperation[j].split(",")[0]),Integer.parseInt(arrCommand[k].split(",")[2]),arrCommand[k].split(",")[0]);
                                            //change in configuration table replace finished_device_id by reg_no
                                            int re = insertDeviceConfigurationStatus(reg_no, finished_id, model_id, Integer.parseInt(idsToOperation[j].split(",")[0]), Integer.parseInt(arrCommand[k].split(",")[2]), arrCommand[k].split(",")[0]);
                                            ////

                                            System.out.println(" matched  and  curent value is .. " + isModuleOperation + " and record updated status is ..." + re);
                                            isModuleOperation = null;
                                            //Thread.sleep(2 * 1000);
                                            //a = 0;
                                            break;
                                        } else {
                                            if (k == (arrCommand.length) - 1) {
                                                isModuleOperation = isModuleOperation;
                                            } else {

//                            isModuleOperation = "$$$$,05,D_2,06,"+device_type_id+","+idsToOperation[j].split(",")[0]+","+Integer.parseInt(arrCommand[k].split(",")[2])+","+Integer.parseInt(arrCommand[k].split(",")[3])+",123,{"+arrCommand[k].split(",")[0]+"},00,####";
                                                isModuleOperation = "$$$$,05," + reg_no + ",06," + device_type_id + "," + idsToOperation[j].split(",")[0] + "," + Integer.parseInt(arrCommand[k].split(",")[2]) + "," + Integer.parseInt(arrCommand[k].split(",")[3]) + ",123,{" + arrCommand[k].split(",")[0] + "},00,####";

                                                //isModuleOperation = isModuleOperation;
                                                //System.out.println("previous value .. " + isModuleOperation);
                                                //Thread.sleep(2 * 1000);
                                                a = 0;
                                                //System.err.println("----- device config model ---- is Module Operation -------"+isModuleOperation);
                                            }
                                            //a = 0;
                                            // break;
                                        }

                                    }
                                    //Thread.sleep(4 * 1000);

                                }

                            }
                            k = 0;
//                 isModuleOperation = "$$$$,05,23,5,"+model_id+","+idsToOperation[j].split(",")[0]+",1,2,123,robinsingh,23,####";
//                 System.out.println(isModuleOperation + "<br>");
                        }
                        //  Thread.sleep(10 * 1000);
                    }
                    j = 0;
                }
                user1 = false;
                //           }
                //user1 = false;
            }
            // user1 = false;
        } catch (Exception e) {
            System.out.println("some error .... " + e);
        }
        isModuleOperation = "end of file";
        //isOperationArr = arr;
        return status;
    }

    public int insertDeviceConfigurationStatus(String reg_no, int finished_id, int model_id, int op_id, int order_no, String command) {

//        String query = " insert into deviceconfigstatus(finished_device_id,model_id,operation_id,order_no,command,status) "
//                       +" values(?,?,?,?,?,?) ";
        String query = " insert into deviceconfigstatus( finished_device_id,reg_no,model_id,operation_id,order_no,command,status) "
                + " values(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, finished_id);
            pstmt.setString(2, reg_no);
            pstmt.setInt(3, model_id);
            pstmt.setInt(4, op_id);
            pstmt.setInt(5, order_no);
            pstmt.setString(6, command);
            pstmt.setString(7, "yes");
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

    public String getDeviceRegNo(int device_id) {
        String reg_no = "";
        String query = "select id,reg_no from device_registration "
                + " where device_id=" + device_id + " and created_at=(select max(created_at) from device_registration where device_id=" + device_id + ")";
        int rowsAffected = 0;

        try {
            // PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rest = stmt.executeQuery();
            rest.next();
            reg_no = rest.getString("reg_no");

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

        return reg_no;
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
