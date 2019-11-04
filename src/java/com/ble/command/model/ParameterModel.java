/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.model;

import com.ble.command.bean.ParameterBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apogee
 */
public class ParameterModel {
    
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

      String query1="select count(*) "
                  +" from parameter i "
                  +" where i.active='Y' and IF('" + searchManufacturerName + "' = '', i.parameter_name LIKE '%%',i.parameter_name ='"+searchManufacturerName+"')";
                  

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
    
    public int insertRecord(ParameterBean input) {
        

        String query = " insert into parameter(parameter_name,parameter_type,remark) "
                       +" values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, input.getParameter_name());
            pstmt.setString(2, input.getParameter_type());
            pstmt.setString(3, input.getRemark());
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
    
    public boolean reviseRecords(ParameterBean bean){
        boolean status=false;
        String query="";
        int rowsAffected=0;
      String query1 = " SELECT max(revision_no) revision_no FROM parameter c WHERE c.parameter_id = "+bean.getParameter_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE parameter SET active=? WHERE parameter_id = ? && revision_no = ? ";
      String query3 = " INSERT INTO parameter (parameter_id,parameter_name,parameter_type,remark,revision_no,active) VALUES (?,?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,bean.getParameter_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,bean.getParameter_id());
             psmt.setString(2,bean.getParameter_name());
             psmt.setString(3,bean.getParameter_type());
             psmt.setString(4,bean.getRemark());
             psmt.setInt(5,rev);
             psmt.setString(6,"Y");

             int a = psmt.executeUpdate();
              if(a > 0)
              status=true;
             }
           }
          } catch (Exception e)
             {
              System.out.println("reviseRecord() Error: " + e);
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
        String query = "select parameter_id from parameter where parameter_name = '"+parameter_name+"';";
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
    
    public int getCommandId(String command) {
        String query = "select id from command where command = '"+command+"';";
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
    
    public List<ParameterBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
        List<ParameterBean> list = new ArrayList<ParameterBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

       String query2="select s.parameter_id,s.remark, s.parameter_name, s.parameter_type"
                     +" from parameter s"
                     +" where s.active='Y' "
                     +" and IF('" + searchManufacturerName + "' = '', s.parameter_name LIKE '%%',s.parameter_name ='"+searchManufacturerName+"')" + addQuery;


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ParameterBean bean = new ParameterBean();
                bean.setParameter_id(rset.getInt("parameter_id"));
                
                
                bean.setParameter_name(rset.getString("parameter_name"));
                bean.setParameter_type(rset.getString("parameter_type"));
                bean.setRemark(rset.getString("remark"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    public List<String> getParameterName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from parameter where active='y' order by parameter_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    
                String type = rset.getString("parameter_name");      
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
                    count++;
                }
                         
                
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
            System.out.println(" ERROR inside CommandModel - " + e);
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
    
    public int deleteRecord(int parameter_id) {

      String query = "update parameter set active='N' where parameter_id=" + parameter_id;
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
