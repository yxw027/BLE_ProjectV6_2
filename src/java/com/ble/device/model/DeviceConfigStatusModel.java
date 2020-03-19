/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.model;

import com.ble.command.model.DeviceOperationCommandModel;
import com.ble.device.bean.DeviceConfigStatusBean;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DeviceConfigStatusModel {
    
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


//public int insertRecord(NtripBaseBean ntripBaseBean) {
//
//        String query = " insert into ntrip_base(base_name,base_password,remark) values(?,?,?); ";
//        int rowsAffected = 0;
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//
//            pstmt.setString(1, ntripBaseBean.getBase_name());
//            pstmt.setString(2, ntripBaseBean.getBase_password());
//            pstmt.setString(3, ntripBaseBean.getRemark());
//
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//
//    }

//public boolean reviseRecords(NtripBaseBean ntripBaseBean){
//    boolean status=false;
//    String query="";
//    int rowsAffected=0;
//
//      String query1 = " SELECT max(revision_no) revision_no FROM ntrip_base c WHERE c.ntrip_base_id = "+ntripBaseBean.getNtrip_base_id()+" && active='Y' ORDER BY revision_no DESC";
//      String query2 = " UPDATE ntrip_base SET active=? WHERE ntrip_base_id = ? && revision_no = ? ";
//      String query3 = " INSERT INTO ntrip_base (ntrip_base_id,base_name,base_password,remark,revision_no,active) VALUES (?,?,?,?,?,?) ";
//
//      int updateRowsAffected = 0;
//      try {
//           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
//           ResultSet rs = ps.executeQuery();
//           if(rs.next()){
//           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//           pst.setString(1,  "N");
//           pst.setInt(2,ntripBaseBean.getNtrip_base_id());
//           pst.setInt(3, rs.getInt("revision_no"));
//           updateRowsAffected = pst.executeUpdate();
//             if(updateRowsAffected >= 1){
//             int rev = rs.getInt("revision_no")+1;
//             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//             psmt.setInt(1,ntripBaseBean.getNtrip_base_id());
//             psmt.setString(2,ntripBaseBean.getBase_name());
//              psmt.setString(3,ntripBaseBean.getBase_password());
//             psmt.setString(4,ntripBaseBean.getRemark());
//             psmt.setInt(5,rev);
//             psmt.setString(6,"Y");
//
//             int a = psmt.executeUpdate();
//              if(a > 0)
//              status=true;
//             }
//           }
//          } catch (Exception e)
//             {
//              System.out.println("CommandModel reviseRecord() Error: " + e);
//             }
//      if (status) {
//             message = "Record updated successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//             message = "Record Not updated Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not updated");
//        }
//
//       return status;
//    }
  public int getNoOfRows(String searchdeviceconfigStatusName) {
      String query1="select count(*) "
                    +" from deviceconfigstatus mt "
                    +" where IF('" + searchdeviceconfigStatusName + "' = '', reg_no LIKE '%%',reg_no =?) "
                    +" and mt.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchdeviceconfigStatusName);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<DeviceConfigStatusBean> showData(int lowerLimit, int noOfRowsToDisplay,String reg_no) {
        List<DeviceConfigStatusBean> list = new ArrayList<DeviceConfigStatusBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
       String query2="select dc.deviceConfigStatus_id,dt.type as device_type,op.operation_name,m.device_name,device_no,dc.command,dc.order_no,dc.remark,dc.reg_no " 
               + " from device_type dt,operation_name op,model m,deviceconfigstatus dc,device d " 
                + " where dc.finished_device_id=d.id and d.device_type_id=dt.type and dc.model_id=m.id and dc.operation_id=op.id "
                     + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceConfigStatusBean ntripBaseBean = new DeviceConfigStatusBean();

                ntripBaseBean.setDeviceConfigStatus_id(rset.getInt("deviceConfigStatus_id"));
                ntripBaseBean.setFinished_device_name(rset.getString("device_type"));
                 ntripBaseBean.setModel_name(rset.getString("device_name"));
                ntripBaseBean.setOperation_name(rset.getString("operation_name"));
                 ntripBaseBean.setCommand(rset.getString("command"));
                 ntripBaseBean.setReg_no(rset.getString("reg_no"));
                   ntripBaseBean.setOrder_no(rset.getInt("order_no"));
                     ntripBaseBean.setModel_no(rset.getString("device_no"));
                list.add(ntripBaseBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

//  public int deleteRecord(int ntrip_base_id) {
//
//      String query = "update ntrip_base set active='N' where ntrip_base_id=" + ntrip_base_id;
//        int rowsAffected = 0;
//        try {
//            rowsAffected = connection.prepareStatement(query).executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record deleted successfully......";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Error Record cannot be deleted.....";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }

  public List<String> getsearchRegistrationNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select reg_no from deviceconfigstatus where active='Y' group by reg_no order by deviceConfigStatus_id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("reg_no");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such reg_no name exists.......");
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


