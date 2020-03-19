/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.ModelTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ModelTypeModel {
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


public int insertRecord(ModelTypeBean modelTypeBean) {

        String query = " insert into modal_type(type,remark) values(?,?); ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, modelTypeBean.getType());
            pstmt.setString(2, modelTypeBean.getRemark());

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

public boolean reviseRecords(ModelTypeBean modelTypeBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;
        PreparedStatement ps=null;
      String query1 = " SELECT max(revision_no) revision_no FROM modal_type c WHERE c.id = "+modelTypeBean.getModel_type_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE modal_type SET active=? WHERE id = ? && revision_no = ? ";
      String query3 = " INSERT INTO modal_type (id,type,remark,revision_no,active) VALUES (?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
          connection.setAutoCommit(false);
           ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
       ps= (PreparedStatement) connection.prepareStatement(query2);
           ps.setString(1,  "N");
           ps.setInt(2,modelTypeBean.getModel_type_id());
           ps.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = ps.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
            ps = (PreparedStatement) connection.prepareStatement(query3);
             ps.setInt(1,modelTypeBean.getModel_type_id());
             ps.setString(2,modelTypeBean.getType());
             ps.setString(3,modelTypeBean.getRemark());
             ps.setInt(4,rev);
             ps.setString(5,"Y");

             int a = ps.executeUpdate();
             if (a > 0) {
                        connection.commit();
                        status = true;
                    }else {
                    connection.rollback();
                    }
             }
           }
          } catch (Exception e)
             {
              System.out.println("CommandModel reviseRecord() Error: " + e);
             }finally{
        try {
            ps.close();
        } catch (SQLException ex) {
             
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
  public int getNoOfRows(String searchModelType) {
      String query1="select count(*) "
                    +" from modal_type mt "
                    +" where IF('" + searchModelType + "' = '', type LIKE '%%',type =?) "
                    +" and mt.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchModelType);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<ModelTypeBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchModelType) {
        List<ModelTypeBean> list = new ArrayList<ModelTypeBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
       String query2="select id,type,remark "
                     +" from modal_type m "
                     +" where IF('" + searchModelType + "' = '', type LIKE '%%',type =?) "
                     +" and m.active='Y'"
                     +addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchModelType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ModelTypeBean deviceTypeBean = new ModelTypeBean();

                deviceTypeBean.setModel_type_id(rset.getInt("id"));
                deviceTypeBean.setType(rset.getString("type"));
                deviceTypeBean.setRemark(rset.getString("remark"));
                list.add(deviceTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

  public int deleteRecord(int model_type_id) {

      String query = "update modal_type set active='N' where id=" + model_type_id;
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

  public List<String> getModelType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from modal_type where active='Y' group by type order by id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("type");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Model Type exists.......");
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
