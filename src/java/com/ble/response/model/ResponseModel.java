/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.model;

import com.ble.command.bean.CommandBean;
import com.ble.response.bean.Response;
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
public class ResponseModel {
    
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

    public void setConnection(Connection connection) {
        this.connection = connection;
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
    
    
    public List<String> getSearchCommand(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select command from command "
                       +" where active='Y' group by command order by command desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
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
    
    public List<String> getSearchResponse(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select response from response "
                       +" where active='Y' group by response order by response desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("response");
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

    public boolean reviseRecords(Response bean){
        boolean status=false;
        String query="";
        int rowsAffected=0;
            
            int command_id = getCommandId(bean.getCommand());


          String query1 = " SELECT max(revision_no) revision_no FROM response r WHERE r.response_id = "+bean.getResponse_id()+" && active='Y' ORDER BY revision_no DESC";
          String query2 = " UPDATE response SET active=? WHERE response_id = ? AND revision_no = ? ";
          String query3 = " INSERT INTO response (response_id,command_id,response,remark,revision_no,format,fixed_response,variable_response,bitwise_response,data_extract_type) VALUES (?,?,?,?,?,?,?,?,?,?) ";

          int updateRowsAffected = 0;
          try {
               PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
               PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
               pst.setString(1,  "N");
               pst.setInt(2,bean.getResponse_id());
               pst.setInt(3, rs.getInt("revision_no"));
               updateRowsAffected = pst.executeUpdate();
                 if(updateRowsAffected >= 1){
                 int rev = rs.getInt("revision_no")+1;
                 PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                 psmt.setInt(1,bean.getResponse_id());
                 psmt.setInt(2,command_id);
                 psmt.setString(3,bean.getResponse());
                 psmt.setString(4,bean.getRemark());
                 psmt.setInt(5,rev);
                 psmt.setString(6,bean.getFormat());
                 psmt.setInt(7,bean.getFixed_response());
                 psmt.setInt(8,bean.getVariable_response());
                 psmt.setInt(9,bean.getBitwise_response());
                 psmt.setString(10,bean.getData_extract_type());

                 int a = psmt.executeUpdate();
                  if(a > 0)
                  status=true;
                 }
               }
              } catch (Exception e)
                 {
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

    public int insertRecord(Response responseBean) {
       
        int command_id = getCommandId(responseBean.getCommand());

        String query = " insert into response(command_id,response,fixed_response,remark,variable_response,"
                        + "format,bitwise_response,data_extract_type) "
                       +" values(?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, command_id);
            pstmt.setString(2, responseBean.getResponse());
            pstmt.setInt(3, responseBean.getFixed_response());
            pstmt.setString(4, responseBean.getRemark());
            pstmt.setInt(5, responseBean.getVariable_response());
            pstmt.setString(6, responseBean.getFormat());
            pstmt.setInt(7, responseBean.getBitwise_response());
            pstmt.setString(8, responseBean.getData_extract_type());
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

    public int deleteRecord(int response_id) {

      String query = "update response set active='N' where response_id=" + response_id;
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
    
    public int getCommandId(String command_type) {
        String query = " select ct.id from command ct where ct.command='"+command_type+"'";
        int operation_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         operation_name_id=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return operation_name_id;
    }
    
    public int getNoOfRows(String searchResponse) {
//        String query = " select count(*) "
//                      +" from command c,device d "
//                      +" where c.device_id = d.id "
//                      + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
//                      //+ " and IF('" + searchCommandName + "' = '', device_name LIKE '%%',device_name =?) "
//                      +" and c.active='Y' ";
    String query1 = "select count(*)  from response c  where IF('" + searchResponse + "' = '', c.response LIKE '%%',c.response ='')  and c.active='Y' ;";
        

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
           
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ResponseModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<Response> showData(int lowerLimit, int noOfRowsToDisplay, String searchCommandName, String searchResponse) {
        
        List<Response> responseList = new ArrayList<>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";


       String query2="select c.id as command_id,c.command, "
        +" r.response,r.response_id,r.remark,r.fixed_response,r.format, r.variable_response, r.bitwise_response, r.data_extract_type "
        +" from command c,response r "
        +" where c.id = r.command_id "
        +" and IF('" + searchResponse + "' = '', response LIKE '%%',response =?) "
        +" and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
        +" and c.active='Y' and r.active='Y' order by c.created_at desc "
        +addQuery;


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchResponse);
           pstmt.setString(2, searchCommandName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Response response = new Response();

                response.setCommand_id(rset.getInt("command_id"));
                String command = rset.getString("command");
                response.setCommand(command);
                response.setResponse(rset.getString("response"));
                response.setResponse_id(rset.getInt("response_id"));
                response.setRemark(rset.getString("remark"));
                response.setFixed_response(rset.getInt("fixed_response"));
                response.setFormat(rset.getString("format"));
                response.setVariable_response(rset.getInt("variable_response"));
                response.setBitwise_response(rset.getInt("bitwise_response"));
                response.setData_extract_type(rset.getString("data_extract_type"));

                responseList.add(response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return responseList;
    
    }
    
    
}
