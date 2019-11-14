/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.model;

import com.ble.command.bean.SelectionBean;
import static com.ble.command.model.CommandModel.bytesToHex;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author apogee
 */
public class SelectionModel {

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

    public int getNoOfRows() {

        String query1 = "select count(*) "
                + " from selection s "
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

    public int insertRecord(SelectionBean selectionBean,int command_id) {
        int parameter_id = getParameterId(selectionBean.getParameter());
//        byte[] hexaByte = DatatypeConverter.parseHexBinary(selectionBean.getCommand_name());
//        String jaya = Arrays.toString(hexaByte);
        //int command_id = getCommandId(selectionBean.getCommand_name());

        String query = " insert into selection(selection_id,command_id,parameter_id,selection_value_no,remark) "
                + " values(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, selectionBean.getSelection_id());
            pstmt.setInt(2, command_id);
            pstmt.setInt(3, parameter_id);
            pstmt.setInt(4, selectionBean.getSelection_value_no());
            pstmt.setString(5, selectionBean.getRemark());
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

    public boolean reviseRecords(SelectionBean bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int parameter_id = getParameterId(bean.getParameter());
//        byte[] hexaByte = DatatypeConverter.parseHexBinary(bean.getCommand_name());
//        String jaya = Arrays.toString(hexaByte);
        int command_id = getCommandId(bean.getCommand_name());
        String query1 = " SELECT max(revision_no) revision_no FROM selection c WHERE c.selection_id = " + bean.getSelection_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE selection SET active=? WHERE selection_id = ? && revision_no = ? ";
        String query3 = " INSERT INTO selection (selection_id,command_id,parameter_id,remark,revision_no,active,selection_value_no) VALUES (?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, bean.getSelection_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, bean.getSelection_id());
                    psmt.setInt(2, command_id);
                    psmt.setInt(3, parameter_id);
                    psmt.setString(4, bean.getRemark());
                    psmt.setInt(5, rev);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, bean.getSelection_value_no());
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

    public boolean updateRecords(SelectionBean bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int parameter_id = getParameterId(bean.getParameter());
        int command_id = getCommandId(bean.getCommand_name());
        int revision_no = getRevisionNo(bean.getSelection_id());

        String query2 = "UPDATE selection SET active='N' where selection_id=" + bean.getSelection_id() + "";
        int updateRowsAffected = 0;
        try {

            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);

            updateRowsAffected = pst.executeUpdate();
            if(updateRowsAffected > 0) {
                try {
                    pst.close();
                    String queryInsert = "INSERT INTO selection (selection_id,command_id,parameter_id,remark,revision_no,active,selection_value_no) VALUES (?,?,?,?,?,?,?) ";
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(queryInsert);
                    psmt.setInt(1, bean.getSelection_id());
                    psmt.setInt(2, command_id);
                    psmt.setInt(3, parameter_id);
                    psmt.setString(4, bean.getRemark());
                    psmt.setInt(5, revision_no+1);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, bean.getSelection_value_no());
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    }
                }catch (Exception e) {
                    System.out.println("SelectionModel updateRecord() Error: " + e);
                }
            }

        } catch (SQLException e) {
            System.out.println("SelectionModel updateRecord() Error: " + e);
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
        String query = "select parameter_id from parameter where parameter_name = '" + parameter_name + "' and active = 'Y';";
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
    
    public int getRevisionNo(int selection_id) {
        String query = "select revision_no from selection where selection_id = '" + selection_id + "' and active = 'Y';";
        int revison_no = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                revison_no = rset.getInt("revision_no");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return revison_no;
    }

    public int getCommandId(String command) {
        String query = "select id from command where command = '" + command + "' and active='Y';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<String> getCommandName() {
        List<String> list = new ArrayList<String>();
        String query = "select command from command order by id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("command");
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
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getCommandNameByCommand_id(int command_id) {
        String type = "";
        String query = "select command from command where id = " + command_id + " and active = 'Y' order by id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("command");
//                

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
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

    public List<String> getParameterType() {
        List<String> list = new ArrayList<String>();
        String query = "select distinct parameter_type from parameter;";
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

//    public List<SelectionBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
//        List<SelectionBean> list = new ArrayList<SelectionBean>();
//         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//          if(lowerLimit == -1){
//              addQuery = "";
//          }
//          String commandName = "";
//            
//          if(searchManufacturerName != null) {
////              byte[] hexaByte = DatatypeConverter.parseHexBinary(searchManufacturerName);
////              commandName = Arrays.toString(hexaByte);
//                commandName = searchManufacturerName;
//          }
//          
//
//       String query2="select s.selection_id,c.command,s.remark,s.selection_value_no, p.parameter_name, p.parameter_type"
//                     +" from selection s, parameter p, command c "
//                     +" where s.command_id=c.id and s.parameter_id = p.parameter_id and s.active='Y' and c.active='Y' and p.active='Y' " 
//                     +" and IF('" + commandName + "' = '', c.command LIKE '%%',c.command ='"+commandName+"') "+ addQuery;
//
//
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                SelectionBean selectionBean = new SelectionBean();
//                
//                String command = rset.getString("command");
////                String commandReq = command.substring(1, command.length()-1);
////                String[] commandByte = commandReq.split(", ");
////                Byte[] b = new Byte[commandByte.length];
////                for (int i = 0; i < commandByte.length; i++) {
////                    b[i] = Byte.parseByte(commandByte[i]);                   
////                }
////                String hex = bytesToHex(b);
//                selectionBean.setSelection_id(rset.getInt("selection_id"));
//                selectionBean.setCommand_name(command);
//                selectionBean.setParameter(rset.getString("parameter_name"));
//                selectionBean.setSelection_value_no(Integer.parseInt(rset.getString("selection_value_no")));
//                selectionBean.setParameter_type(rset.getString("parameter_type"));
//                selectionBean.setRemark(rset.getString("remark"));
//                list.add(selectionBean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        return list;
//    }
    public List<SelectionBean> showDataByCommandId(int lowerLimit, int noOfRowsToDisplay, String selection_no, String command_id) {
        List<SelectionBean> list = new ArrayList<SelectionBean>();

        String commandName = "";
        int i = 1;
        

        String query2 = "select s.selection_id,c.command,s.remark,s.selection_value_no, p.parameter_name, p.parameter_type"
                + " from selection s, parameter p, command c "
                + " where s.command_id=c.id and s.parameter_id = p.parameter_id and s.active='Y' and c.active='Y' and p.active='Y' and s.command_id ="+command_id;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (i <= Integer.parseInt(selection_no)) {
                    SelectionBean selectionBean = new SelectionBean();

                    String command = rset.getString("command");
                    //                String commandReq = command.substring(1, command.length()-1);
                    //                String[] commandByte = commandReq.split(", ");
                    //                Byte[] b = new Byte[commandByte.length];
                    //                for (int i = 0; i < commandByte.length; i++) {
                    //                    b[i] = Byte.parseByte(commandByte[i]);                   
                    //                }
                    //                String hex = bytesToHex(b);
                    selectionBean.setSelection_id(rset.getInt("selection_id"));
                    selectionBean.setCommand_name(command);
                    selectionBean.setParameter(rset.getString("parameter_name"));
                    selectionBean.setSelection_value_no(Integer.parseInt(rset.getString("selection_value_no")));
                    selectionBean.setParameter_type(rset.getString("parameter_type"));
                    selectionBean.setRemark(rset.getString("remark"));
                    list.add(selectionBean);
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
