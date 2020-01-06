/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.model;

import com.ble.response.bean.VariableResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class VariableResponseModel {
    
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

    public int getNoOfRows(String searchManufacturerName) {

        String query1 = "select count(*) "
                + " from variable_response i "
                + " where i.active='Y' ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows SelectionModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public int insertRecord(VariableResponse variableResponse) {
        int parameter_id = getParameterId(variableResponse.getParameter());
//        byte[] hexaByte = DatatypeConverter.parseHexBinary(input.getCommand_name());
//        String jaya = Arrays.toString(hexaByte);
        int response_id = getResponseId(variableResponse.getResponse());

        String query = " insert into variable_response(response_id,parameter_id,start_pos,no_of_byte,remark) "
                + " values(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1, variableResponse.getVariable_response_id());
            pstmt.setInt(1, response_id);
            pstmt.setInt(2, parameter_id);
            pstmt.setInt(3,variableResponse.getStart_pos());
            pstmt.setInt(4,variableResponse.getNo_of_byte());
            pstmt.setString(5, variableResponse.getRemark());
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

    public boolean reviseRecords(VariableResponse bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int parameter_id = getParameterId(bean.getParameter());
//        byte[] hexaByte = DatatypeConverter.parseHexBinary(bean.getCommand_name());
//        String jaya = Arrays.toString(hexaByte);
        int response_id = getResponseId(bean.getResponse());
        String query1 = " SELECT max(revision_no) revision_no FROM variable_response c WHERE c.variable_response_id = " + bean.getVariable_response_id()+ " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE variable_response SET active=? WHERE variable_response_id = ? && revision_no = ? ";
        String query3 = " INSERT INTO variable_response(variable_response_id,response_id,parameter_id,start_pos,no_of_byte,remark,revision_no,active) VALUES (?,?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, bean.getVariable_response_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, bean.getVariable_response_id());
                    psmt.setInt(2, response_id);
                    psmt.setInt(3, parameter_id);
                    psmt.setInt(4,bean.getStart_pos());
                   psmt.setInt(5,bean.getNo_of_byte());
                   psmt.setString(6, bean.getRemark());
                    psmt.setInt(7, rev);
                    psmt.setString(8, "Y");

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

    public int getParameterId(String parameter_name) {
        String query = "select parameter_id from parameter where parameter_name = '" + parameter_name + "';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("parameter_id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public int getResponseId(String response) {
        String query = "select response_id from response where response = '" + response + "';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("response_id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<VariableResponse> showData(int lowerLimit, int noOfRowsToDisplay, String searchManufacturerName) {
        List<VariableResponse> list = new ArrayList<VariableResponse>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String responseName = "";
        if (searchManufacturerName != null) {
//              byte[] hexaByte = DatatypeConverter.parseHexBinary(searchManufacturerName);
            responseName = searchManufacturerName;//Arrays.toString(hexaByte);
        }

        String query2 = "select s.variable_response_id,c.command,s.remark, p.parameter_name"
                + " from variable_response s, parameter p, response c "
                + " where s.response_id=c.id and s.parameter_id = p.parameter_id and s.active='Y' "
                + " and IF('" + responseName + "' = '', c.response LIKE '%%',c.response ='" + responseName + "') " + addQuery;
//                     +" and ('" + searchManufacturerName + "' = '', c.command LIKE '%%',c.command =?)";

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                VariableResponse variableResponse = new VariableResponse();
                variableResponse.setVariable_response_id(rset.getInt("variable_response_id"));
                String response= rset.getString("response");
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                variableResponse.setResponse(response);
                variableResponse.setParameter(rset.getString("parameter_name"));
                 variableResponse.setStart_pos(rset.getInt("start_pos"));
                 variableResponse.setNo_of_byte(rset.getInt("no_of_byte"));
                variableResponse.setRemark(rset.getString("remark"));
                list.add(variableResponse);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<VariableResponse> showDataByResponseId(int lowerLimit, int noOfRowsToDisplay, String variable_response, String response_id) {
        List<VariableResponse> list = new ArrayList<VariableResponse>();

        int i = 1;
        String query2 = "select vr.variable_response_id,r.response,vr.start_pos,vr.no_of_byte,vr.remark, p.parameter_name"
                + " from variable_response vr, parameter p, response r "
                + " where r.response_id=vr.response_id and vr.parameter_id = p.parameter_id and r.active='Y' and vr.response_id = " + Integer.parseInt(response_id);

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (i <= Integer.parseInt(variable_response)) {
                    VariableResponse variableResponse = new VariableResponse();
                    variableResponse.setVariable_response_id(rset.getInt("variable_response_id"));
                    String response = rset.getString("response");
                    variableResponse.setResponse(response);
                    variableResponse.setParameter(rset.getString("parameter_name"));
                    variableResponse.setStart_pos(rset.getInt("start_pos"));
                    variableResponse.setNo_of_byte(rset.getInt("no_of_byte"));
                    variableResponse.setRemark(rset.getString("remark"));
                    list.add(variableResponse);
                } else {
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getResponseName() {
        List<String> list = new ArrayList<String>();
        String query = "select response from response order by response_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("response");
//                String commandReq = type.substring(1, type.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Response Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getResponseNameByResponse_id(int response_id) {
        String type = "";
        String query = "select response from response where response_id = " + response_id + " and active = 'Y' order by response_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("response");
//                

        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<String> getParameter() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from parameter order by parameter_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("parameter_name");
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Parameter Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getParameterType() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_type from parameter order by parameter_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("parameter_type");
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Parameter Type exists.......");
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
            System.out.println("Error inside closeConnection ruleModel:" + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
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
