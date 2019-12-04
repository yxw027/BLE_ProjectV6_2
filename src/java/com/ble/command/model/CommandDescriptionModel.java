/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.model;

import com.ble.command.bean.CommandDescriptionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CommandDescriptionModel {

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

    public int getDeviceId(int manufacturer_id, int deviceType_id, int model_id) {
        String query = " select id from device where manufacture_id=" + manufacturer_id + " and device_type_id=" + deviceType_id + " and model_id=" + model_id + " and active='Y'";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                device_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getDeviceId CommandModel " + e);
        }

        return device_id;
    }

    public int getOperationId(String operation_name) {
        String query = " select op.id from operation_name op where operation_name='" + operation_name + "'";
        int operation_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                operation_name_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return operation_name_id;
    }

    public int getCommandId(String command) {
        String query = " select ct.id from command ct where ct.command='" + command + "'";
        int command_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                command_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error inside commandId CommandModel" + e);
        }

        return command_id;
    }

    public int insertRecord(CommandDescriptionBean commandDescriptionBean) {

        int command_id = getCommandId(commandDescriptionBean.getCommand());

        String query = " insert into command_description(position,length,description,command_id,remark) "
                + " values(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, commandDescriptionBean.getPosition());
            pstmt.setInt(2, commandDescriptionBean.getLength());
            pstmt.setString(3, commandDescriptionBean.getDescription());

            pstmt.setInt(4, command_id);
            pstmt.setString(5, commandDescriptionBean.getRemark());

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

    public boolean reviseRecords(CommandDescriptionBean bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        int command_id = getCommandId(bean.getCommand());

        String query1 = " SELECT max(revision_no) revision_no FROM command_description c WHERE c.command_description_id = " + bean.getCommand_description_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE command_description SET active=? WHERE command_description_id = ? && revision_no = ? ";
        String query3 = " INSERT INTO command_description (command_description_id,position,length,description,remark,command_id,revision_no,active) VALUES (?,?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, bean.getCommand_description_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, bean.getCommand_description_id());
                    psmt.setString(2, bean.getPosition());
                    psmt.setInt(3, bean.getLength());
                    psmt.setString(4, bean.getDescription());
                    psmt.setString(5, bean.getRemark());
                    psmt.setInt(6, command_id);
                    psmt.setInt(7, rev);
                    psmt.setString(8, "Y");

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CommandDescriptionModel reviseRecord() Error: " + e);
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

    public int getNoOfRows(String searchCommandName) {
//        String query = " select count(*) "
//                      +" from command c,device d "
//                      +" where c.device_id = d.id "
//                      + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
//                      //+ " and IF('" + searchCommandName + "' = '', device_name LIKE '%%',device_name =?) "
//                      +" and c.active='Y' ";
        String query1 = "select count(*)  from command c  where IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command ='')  and c.active='Y' ;";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<CommandDescriptionBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCommandName) {
        List<CommandDescriptionBean> list = new ArrayList<CommandDescriptionBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query2 = "select c.command_description_id as command_description_id,c.position, "
                + " c.length,c.description,c.remark,ct.command as command "
                + " from command_description c,command ct "
                + " where c.command_id = ct.id "
                + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
                + " and c.active='Y' and ct.active='Y' order by c.created_at desc "
                + addQuery;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchCommandName);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CommandDescriptionBean commandDescriptionBean = new CommandDescriptionBean();

                commandDescriptionBean.setCommand_description_id(rset.getInt("command_description_id"));
                commandDescriptionBean.setCommand(rset.getString("command"));
                commandDescriptionBean.setPosition(rset.getString("position"));
                commandDescriptionBean.setLength(rset.getInt("length"));
                commandDescriptionBean.setDescription(rset.getString("description"));
                commandDescriptionBean.setRemark(rset.getString("remark"));

                list.add(commandDescriptionBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public static String bytesToHex(Byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public int deleteRecord(int command_description_id) {

        String query = "update command_description set active='N' where command_description_id=" + command_description_id;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

    public List<String> getCommand(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select command from command ct group by command order by command desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
//            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("command");
//                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                list.add(command);
                count++;
//                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSearchCommandName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select command from command "
                + " where active='Y' group by command order by command desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            if (q != null) {
                q = q.trim();
            } else {
                q = "";
            }

            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("command");
                if (command.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(command);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }



    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

}
