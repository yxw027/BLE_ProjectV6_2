/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.NtripRoverBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NtripRoverModel {
    
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


public int insertRecord(NtripRoverBean ntripRoverBean) {

        String query = " insert into ntrip_rover(rover_name,rover_password,remark) values(?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, ntripRoverBean.getRover_name());
            pstmt.setString(2, ntripRoverBean.getRover_password());
            pstmt.setString(3, ntripRoverBean.getRemark());

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

public boolean reviseRecords(NtripRoverBean ntripRoverBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;

      String query1 = " SELECT max(revision_no) revision_no FROM ntrip_rover c WHERE c.ntrip_rover_id = "+ntripRoverBean.getNtrip_rover_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE ntrip_rover SET active=? WHERE ntrip_rover_id = ? && revision_no = ? ";
      String query3 = " INSERT INTO ntrip_rover (ntrip_rover_id,rover_name,rover_password,remark,revision_no,active) VALUES (?,?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,ntripRoverBean.getNtrip_rover_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,ntripRoverBean.getNtrip_rover_id());
             psmt.setString(2,ntripRoverBean.getRover_name());
              psmt.setString(3,ntripRoverBean.getRover_password());
             psmt.setString(4,ntripRoverBean.getRemark());
             psmt.setInt(5,rev);
             psmt.setString(6,"Y");

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
  public int getNoOfRows(String searchroverName) {
      String query1="select count(*) "
                    +" from ntrip_rover mt "
                    +" where IF('" + searchroverName + "' = '', rover_name LIKE '%%',rover_name =?) "
                    +" and mt.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchroverName);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<NtripRoverBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchroverName) {
        List<NtripRoverBean> list = new ArrayList<NtripRoverBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
       String query2="select ntrip_rover_id,rover_name,rover_password,remark "
                     +" from ntrip_rover m "
                     +" where IF('" + searchroverName + "' = '', rover_name LIKE '%%',rover_name =?) "
                     +" and m.active='Y'"
                     +addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchroverName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                NtripRoverBean ntripRoverBean = new NtripRoverBean();

                ntripRoverBean.setNtrip_rover_id(rset.getInt("ntrip_rover_id"));
                ntripRoverBean.setRover_name(rset.getString("rover_name"));
                 ntripRoverBean.setRover_password(rset.getString("rover_password"));
                ntripRoverBean.setRemark(rset.getString("remark"));
                list.add(ntripRoverBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

  public int deleteRecord(int ntrip_rover_id) {

      String query = "update ntrip_rover set active='N' where ntrip_rover_id=" + ntrip_rover_id;
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

  public List<String> getRoverName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select rover_name from ntrip_rover where active='Y' group by rover_name order by ntrip_rover_id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("rover_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Rover name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside Model - " + e);
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


