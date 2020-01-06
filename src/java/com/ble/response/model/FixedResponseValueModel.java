/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.model;

import com.ble.response.bean.FixedResponseValue;
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
public class FixedResponseValueModel {
    
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

    public int getNoOfRows(String searchFixedResponseName) {

        String query1 = "select count(*) "
                + " from fixed_response_value s "
                + " where s.active='Y' ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            //stmt.setString(1, searchCommandName);
            //stmt.setString(2, searchDeviceName);
            //stmt.setString(3, searchManufacturerName);
            //stmt.setString(4, searchDeviceType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows SelectionModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
    
    public int getFixedResponseValueId() {
        int fixed_response_value_id = 0;
         String query1 = "select Max(fixed_response_value_id) as fixed_response_value_id "
                + " from fixed_response_value frv "
                + " where frv.active='Y' ";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
          
            ResultSet rs = stmt.executeQuery();
            rs.next();
            fixed_response_value_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getSelectionValueId SelectionModel" + e);
        }
        return fixed_response_value_id;
    }

    public int insertRecord(FixedResponseValue fixedResponseValue) {

       int fixed_response_value_id = getFixedResponseValueId() + 1;

        String query = " insert into fixed_response_value(fixed_response_value_id,display_value,select_value,remark,fixed_response_id) "
                + " values(?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, fixed_response_value_id);
            pstmt.setString(2, fixedResponseValue.getDisplay_value());
            pstmt.setString(3, fixedResponseValue.getSelect_value());
            pstmt.setString(4, fixedResponseValue.getRemark());
            pstmt.setInt(5, fixedResponseValue.getFixed_response_id());
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

    public boolean reviseRecords(FixedResponseValue fixedResponseValue) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        int fixed_response_id = getFixedResponseID(fixedResponseValue.getParameter());
        String query1 = " SELECT max(revision_no) revision_no FROM fixed_response_value c WHERE c.fixed_response_value_id = " + fixedResponseValue.getFixed_response_value_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE fixed_response_value SET active=? WHERE fixed_response_value_id = ? && revision_no = ? ";
        String query3 = " INSERT INTO fixed_response_value(fixed_response_value_id,fixed_response_id,display_value,select_value,remark,revision_no,active) VALUES (?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, fixedResponseValue.getFixed_response_value_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, fixedResponseValue.getFixed_response_value_id());
                    psmt.setInt(2, fixed_response_id);
                    psmt.setString(3, fixedResponseValue.getDisplay_value());
                    psmt.setString(4, fixedResponseValue.getSelect_value());
                    psmt.setString(5, fixedResponseValue.getRemark());
                    psmt.setInt(6, rev);
                    psmt.setString(7, "Y");

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CommandValueModel reviseRecord() Error: " + e);
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

    public int getFixedResponseID(String fixed_response_value_no) {
        String query = "select id from fixed_response where fixed_response_value_no = '" + fixed_response_value_no + "';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside SelectionModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<Integer> getFixedResponseName() {
        List<Integer> list = new ArrayList<Integer>();
        String query = "select fixed_response_value_no from fixed_response order by fixed_response_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                int type = rset.getInt("fixed_response_value_no");
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
                list.add(0);
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getFixedResponseNameByFixed_ResponseId(int fixed_response_id) {
        String type = "";
        String query = "select parameter_name from parameter p ,fixed_response fr, fixed_reponse_value frv where frv.fixed_response_id=fr.fixed_response_id and p.parameter_id=s.parameter_id";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("parameter_name");
//                

        } catch (Exception e) {
            System.out.println(" ERROR inside SelectionValueModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<FixedResponseValue> showData(int lowerLimit, int noOfRowsToDisplay, String searchFixedResponseName, String parameter, int fixed_response_id) {
        List<FixedResponseValue> list = new ArrayList<FixedResponseValue>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String parameter1 = parameter;
        int fixed_response_id1 = fixed_response_id;
        if (searchFixedResponseName != null) {
//              byte[] hexaByte = DatatypeConverter.parseHexBinary(searchManufacturerName);
//              commandName = Arrays.toString(hexaByte);
            parameter = searchFixedResponseName;
        }

        //String query2="select sv.selection_value_id,sv.byte_value,sv.display_value,sv.remark,s.selection_value_no, p.parameter_name "
//        + " from selection_value sv, parameter p, selection s "
//        + " where sv.selection_value_id=s.selection_id and s.parameter_id = p.parameter_id and s.active='Y' and sv.active='Y' and p.active='Y' "
//        + addQuery;
        String query2;
        query2 = " SELECT fixed_response_value_id,display_value, select_value,fixed_response_value.remark, parameter.parameter_name, fixed_response_value.fixed_response_id  FROM fixed_response_value inner join fixed_response on fixed_response_value.fixed_response_id =fixed_response.fixed_response_id  inner join parameter on fixed_response.parameter_id = parameter.parameter_id   where fixed_response.active = 'Y' and fixed_response_value.active = 'Y' and parameter.active='Y' and parameter.parameter_name ='" + parameter1 + "' and fixed_response_value.fixed_response_id =" + fixed_response_id1 + ";";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                FixedResponseValue fixedResponseValue = new FixedResponseValue();

                parameter = rset.getString("parameter_name");
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                fixedResponseValue.setFixed_response_value_id(rset.getInt("fixed_response_value_id"));

                fixedResponseValue.setParameter(rset.getString("parameter_name"));
                fixedResponseValue.setDisplay_value(rset.getString("display_value"));
                fixedResponseValue.setSelect_value(rset.getString("select_value"));
                fixedResponseValue.setRemark(rset.getString("remark"));
                list.add(fixedResponseValue);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getParameter() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from parameter where active='Y' order by parameter_id desc;";
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
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<FixedResponseValue> showDataByFixedResponseId(int lowerLimit, int noOfRowsToDisplay, int fixed_response_id, String parameter) {
        List<FixedResponseValue> list = new ArrayList<FixedResponseValue>();

        String searchName = "";
        int i = 1;

        String query2 = "select frv.fixed_response_value_id,s.remark,s.selection_value_no"
                + " from fixed_response_value frv, fixed_response fr "
                + " where frv.fixed_response_id=fr.fixed_response_id and frv.active='Y' and fr.active='Y' and fr.fixed_response_id = " + fixed_response_id;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (i <= (Integer.parseInt(parameter))) {
                    FixedResponseValue fixedResponseValue = new FixedResponseValue();

                    parameter = rset.getString("parameter");
                    //                String commandReq = command.substring(1, command.length()-1);
                    //                String[] commandByte = commandReq.split(", ");
                    //                Byte[] b = new Byte[commandByte.length];
                    //                for (int i = 0; i < commandByte.length; i++) {
                    //                    b[i] = Byte.parseByte(commandByte[i]);                   
                    //                }
                    //                String hex = bytesToHex(b);
                    fixedResponseValue.setFixed_response_value_id(rset.getInt("fixed_response_value_id"));
                    fixedResponseValue.setDisplay_value(rset.getString("display_value"));
                    fixedResponseValue.setSelect_value(rset.getString("select_value"));

                    fixedResponseValue.setRemark(rset.getString("remark"));
                    list.add(fixedResponseValue);
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
