/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.model;


import com.ble.response.bean.ResponseCrcMappingBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ResponseCrcMappingModel {
    
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
            System.out.println("ResponsecrcModel setConnection() Error: " + e);
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

    public int getResponseId(String response) {

        //
        String query1 = "SELECT response_id FROM response where response=? and active='Y' ";
        int id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, response);
            
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            id = rs.getInt("response_id");

        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ResponseCrcModel" + e);
        }
        return id;
    }

    public int getCrcId(String crctype) {

        //
      String query1 = "SELECT crc_type_id FROM crc_type where crc_type=? and active='Y' ";
        int id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, crctype);
            
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            id = rs.getInt("crc_type_id");

        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ResponsecrcModel" + e);
        }
        return id;
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

    public int insertRecord(ResponseCrcMappingBean cmdcrcmapbean ) {

        int response_id = getResponseId(cmdcrcmapbean.getResponse());
        int crc_type_id = getCrcId(cmdcrcmapbean.getCrc_type());
 
        String query = "insert into response_crc_mapping(response_id,crc_type_id,remark)values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, response_id);
            pstmt.setInt(2, crc_type_id);
             pstmt.setString(3, cmdcrcmapbean.getRemark());

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

    public boolean reviseRecords(ResponseCrcMappingBean cmdcrcmapbean ) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
         PreparedStatement ps=null;

        int response_id = getResponseId(cmdcrcmapbean.getResponse());
        int crc_type_id = getCrcId(cmdcrcmapbean.getCrc_type());
//        int ble_device_id = getBleDeviceId(deviceMapBean.getBle_manufacture_name(),deviceMapBean.getBle_device_type(), deviceMapBean.getBle_model_name());

        String query1 = " SELECT max(revision) revision FROM response_crc_mapping c WHERE c.response_crc_mapping_id = "+cmdcrcmapbean.getResponse_crc_map_id()+" && active='Y' ORDER BY revision DESC";
        String query2 = " UPDATE response_crc_mapping SET active=? WHERE response_crc_mapping_id = ? && revision = ? ";
 
        String query3 = "insert into response_crc_mapping(response_id,crc_type_id,remark)values(?,?,?)";
        int updateRowsAffected = 0;
        try {
            connection.setAutoCommit(false);
           ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
          ps = (PreparedStatement) connection.prepareStatement(query2);
                ps.setString(1, "N");
                ps.setInt(2, cmdcrcmapbean.getResponse_crc_map_id());
                ps.setInt(3, rs.getInt("revision"));
                updateRowsAffected = ps.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision") + 1;
                   ps = (PreparedStatement) connection.prepareStatement(query3);
                    ps.setInt(1, response_id);
                    ps.setInt(2, crc_type_id);
                   ps.setString(3, cmdcrcmapbean.getRemark());
                  

                    int a = ps.executeUpdate();
                    if (a > 0) {
                         connection.commit();
                        status = true;
                    }
                    else {
                    connection.rollback();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ResponsecrcModel reviseRecord() Error: " + e);
        }
         finally{
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println("Exception:"+ ex);
            }
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

    public int getNoOfRows(String searchResponse, String searchCrctype) {
         
        
        String query1 = "select count(*) from response_crc_mapping ccm,response c,crc_type ct "
                + "where ccm.response_id=c.response_id and  ccm.crc_type_id=ct.crc_type_id and ccm.active='Y'and c.active='Y' and ct.active='Y' "
                + "AND IF('" + searchResponse + "' = '', c.response LIKE '%%',c.response =?) "
                + "AND IF('" + searchCrctype + "' = '', ct.crc_type LIKE '%%',ct.crc_type =?)";
              

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchResponse);
            stmt.setString(2, searchCrctype);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ResponsecrcModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<ResponseCrcMappingBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchResponse, String searchCrctype) {
        List<ResponseCrcMappingBean> list = new ArrayList<ResponseCrcMappingBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

 
       String query2 = "select ccm.response_crc_mapping_id,c.response,ct.crc_type,ccm.remark from response_crc_mapping ccm,response c,crc_type ct "
                + "where ccm.response_id=c.response_id and  ccm.crc_type_id=ct.crc_type_id and ccm.active='Y'and c.active='Y' and ct.active='Y' "
                + "AND IF('" + searchResponse + "' = '', c.response LIKE '%%',c.response =?) "
                + "AND IF('" + searchCrctype + "' = '', ct.crc_type LIKE '%%',ct.crc_type =?)" 
              
                + addQuery;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchResponse);
            pstmt.setString(2, searchCrctype);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ResponseCrcMappingBean cmdcrmapBean = new ResponseCrcMappingBean();
                cmdcrmapBean.setResponse_crc_map_id(rset.getInt("response_crc_mapping_id"));
                cmdcrmapBean.setResponse(rset.getString("c.response"));
                cmdcrmapBean.setCrc_type(rset.getString("crc_type"));

                cmdcrmapBean.setRemark(rset.getString("ccm.remark"));
                list.add(cmdcrmapBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int deleteRecord(int response_crc_mapping_id) {

        String query = "update response_crc_mapping set active='N' where response_crc_mapping_id=" + response_crc_mapping_id;
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

    public List<String> getResponse(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select response from response where active='Y' group by response order by response desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String response = rset.getString("response");
                if (response.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(response);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Response Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseCrcModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    
    public List<String> getCrcType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select crc_type from crc_type where active='Y'group by crc_type order by crc_type_id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("crc_type");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
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
                list.add("No such Device No exists.......");
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
                list.add("No such Device Type exists.......");
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

