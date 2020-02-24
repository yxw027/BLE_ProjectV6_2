/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.CrcTypeBean;
import com.ble.dataEntry.bean.OperationNameBean;
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
public class CrcTypeModel {
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
            System.out.println("CrcModel setConnection() Error: " + e);
        }
    }
 

    public int insertRecord(CrcTypeBean ctypebean) {
        
        int rowsAffected = 0;
        String query = " insert into crc_type(crc_type,remark) values(?,?) ";
 
            try {
                java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

                pstmt.setString(1, ctypebean.getCrc_type());
               
                pstmt.setString(2, ctypebean.getRemark());
                

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

    public boolean reviseRecords(CrcTypeBean ctypebean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        String query1 = " SELECT max(revision) revision FROM crc_type c WHERE c.crc_type_id = "+ctypebean.getCrc_type_id()+" && active='Y' ORDER BY revision DESC";
        String query2 = " UPDATE crc_type SET active=? WHERE crc_type_id = ? && revision = ? ";
        String query3 = " insert into crc_type(crc_type_id,crc_type,remark,revision) values(?,?,?,?)";
      //  int parent_id = getParentOperationId(operationNameBean.getParent_operation());
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, ctypebean.getCrc_type_id() );
                pst.setInt(3, rs.getInt("revision"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, ctypebean.getCrc_type_id());
                    psmt.setString(2, ctypebean.getCrc_type());
                    psmt.setString(3, ctypebean.getRemark());
                    psmt.setInt(4, rev);
                   // psmt.setString(5, "Y");
                   

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CrcModel reviseRecord() Error: " + e);
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

    public int getNoOfRows(String searchcrctype) {
        String query1 = "select count(*)  from crc_type c where IF('" + searchcrctype + "' = '', c.crc_type LIKE '%%',c.crc_type =?)and c.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchcrctype);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<CrcTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchcrctype) {
        List<CrcTypeBean> list = new ArrayList<CrcTypeBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
 
 String query2 = "select c.crc_type_id,c.crc_type,c.remark from crc_type c where IF('" + searchcrctype + "' = '', c.crc_type LIKE '%%',c.crc_type =?)and c.active='Y'"
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchcrctype);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CrcTypeBean ctypebean = new CrcTypeBean();
                ctypebean.setCrc_type_id(rset.getInt("crc_type_id"));
                ctypebean.setCrc_type(rset.getString("crc_type"));
                ctypebean.setRemark(rset.getString("remark"));
              
                list.add(ctypebean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int deleteRecord(int crc_type_id) {

        String query = "update crc_type set active='N' where crc_type_id=" + crc_type_id;
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
