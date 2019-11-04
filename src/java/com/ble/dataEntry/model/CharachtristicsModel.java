/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.model;

import com.ble.dataEntry.bean.CharachtristicsBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class CharachtristicsModel {
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
    
    public int getServiceId(String service_name,String service_uuid) {
    
    
    int device_id = 0;

        String query = " select s.id\n" +
                       "from servicies s\n" +
                       "where service_name='"+service_name+"'\n"+
                       "and service_uuid='"+service_uuid+"'\n"+
                       "and s.active='Y'";
        
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                device_id = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (device_id > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return device_id;

    }


public int insertRecord(CharachtristicsBean charachtristicsBean) {
    
    String service_name = charachtristicsBean.getService_name();
    String service_uuid = charachtristicsBean.getService_uuid();
    
    int service_id = getServiceId(service_name,service_uuid);
    

        String query = " insert into charachtristics(name,service_id,uuid,remark)\n" +
                       "values(?,?,?,?)";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1,charachtristicsBean.getCharachtristics_name() );
            pstmt.setInt(2,service_id);
            pstmt.setString(3, charachtristicsBean.getUuid());
            pstmt.setString(4, charachtristicsBean.getRemark());

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

public boolean reviseRecords(CharachtristicsBean charachtristicsBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;
    String service_name = charachtristicsBean.getService_name();
    String service_uuid = charachtristicsBean.getService_uuid();
    
    int service_id = getServiceId(service_name,service_uuid);
    
    

      String query1 = " SELECT max(revision_no) revision_no FROM charachtristics c WHERE c.id = "+charachtristicsBean.getCharachtristics_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE charachtristics SET active=? WHERE id = ? && revision_no = ? ";
      String query3 = " INSERT INTO charachtristics (id,name,service_id,uuid,remark,revision_no,active) VALUES (?,?,?,?,?,?,?) ";

      int updateRowsAffected=0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,charachtristicsBean.getCharachtristics_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,charachtristicsBean.getCharachtristics_id());
             psmt.setString(2,charachtristicsBean.getCharachtristics_name());
              psmt.setInt(3,service_id);
             psmt.setString(4,charachtristicsBean.getUuid());
             
             psmt.setString(5,charachtristicsBean.getRemark());
             psmt.setInt(6,rev);
             psmt.setString(7,"Y");
             

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
  public int getNoOfRows(String searchDeviceType) {
      String query1="select count(*)\n" +
                    "from charachtristics c,servicies s\n" +
                    "where c.service_id = s.id\n" +
                    "and c.active='Y'\n" +
                    "and s.active='Y' "+
                    " AND IF('" + searchDeviceType + "' = '', name LIKE '%%',name =?) ";
                    

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchDeviceType);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<CharachtristicsBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchDeviceType) {
        List<CharachtristicsBean> list = new ArrayList<CharachtristicsBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
       String query2=" select c.id,c.name,s.service_name,s.service_uuid,c.uuid,c.remark\n" +
                     "from charachtristics c,servicies s\n" +
                     "where c.service_id = s.id\n" +
                     "and c.active='Y'\n" +
                     "and s.active='Y'"+
                     "AND IF('" + searchDeviceType + "' = '', name LIKE '%%',name =?) "+
                     
                     addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchDeviceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CharachtristicsBean charachtristicsBean = new CharachtristicsBean();

                charachtristicsBean.setCharachtristics_id(rset.getInt("id"));
                charachtristicsBean.setCharachtristics_name(rset.getString("name"));
                charachtristicsBean.setService_name(rset.getString("service_name"));
                charachtristicsBean.setService_uuid(rset.getString("service_uuid"));
                charachtristicsBean.setUuid(rset.getString("uuid"));
                charachtristicsBean.setRemark(rset.getString("remark"));
                list.add(charachtristicsBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

  public int deleteRecord(int charachtristics_id) {

      String query = "update charachtristics set active='N' where id=" + charachtristics_id;
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
  
  public List<String> getSearchCharachtersticName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name\n" +
                       "from charachtristics c\n" +
                       "where c.active='Y'\n" +
                       "group by name order by name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getServiceUuid(String q,String service_name) {
        List<String> list = new ArrayList<String>();
        String query = "select service_uuid\n" +
                       "from servicies s\n" +
                       "where s.service_name='"+service_name+"'\n"+
                       "and s.active='Y'\n" +
                       "group by service_uuid order by service_uuid";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("service_uuid");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getServiceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select service_name\n" +
                       "from servicies s\n" +
                       "where s.active='Y'\n" +
                       "group by service_name order by service_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("service_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getModelType(String q,String manufacturer,String device_type) {
        List<String> list = new ArrayList<String>();
        String query = " select m.device_name\n" +
                      "from device d,manufacturer mf,model m,device_type dt\n" +
                      "where d.manufacture_id = mf.id\n" +
                      "and d.device_type_id = dt.id\n" +
                      "and m.id = d.model_id \n"+
                      "and mf.name='"+manufacturer+"'\n"+
                      "and dt.type='"+device_type+"'\n"+
                      "and d.active='Y'\n" +
                      "and mf.active='Y'\n" +
                      "and m.active='Y'\n" +
                      "and dt.active='Y'\n" +
                      "group by m.device_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("device_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
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
