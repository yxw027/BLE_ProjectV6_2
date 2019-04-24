/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.device.model;

import com.ble.device.bean.DeviceBean;
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
public class DeviceModel {

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
    public int getManufactureId(String manufactureName) {
      String query1="select id from manufacturer m "
                    +" where m.name=? "
                    +" and m.active='Y' ";
        int manufacturer_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, manufactureName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            manufacturer_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return manufacturer_id;
    }
    public int getDeviceTypeId(String deviceTypeName) {
      String query1="select id from device_type d "
                    +" where d.type=? "
                    +" and d.active='Y' ";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, deviceTypeName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_type_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_type_id;
    }

    public int getModelId(String device_name,int device_no) {
      String query1="select id from model m "
                    +" where m.device_name=? and m.device_no=? "
                    +" and m.active='Y' ";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1,device_name);
            stmt.setInt(2,device_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();//jieji
            model_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return model_id;
    }


public int insertRecord(DeviceBean deviceBean) {

    int manufacture_id=getManufactureId(deviceBean.getManufacture_name());
    int device_type_id = getDeviceTypeId(deviceBean.getDevice_type_name());
    int model_id = getModelId(deviceBean.getDevice_name(),deviceBean.getDevice_no());

        String query = " insert into device(manufacture_id,device_type_id,model_id,remark) "
                       +" values(?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1,manufacture_id);
            pstmt.setInt(2,device_type_id);
            pstmt.setInt(3,model_id);
            pstmt.setString(4, deviceBean.getRemark());

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

public boolean reviseRecords(DeviceBean deviceBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;

    int manufacture_id=getManufactureId(deviceBean.getManufacture_name());
    int device_type_id = getDeviceTypeId(deviceBean.getDevice_type_name());
    int model_id = getModelId(deviceBean.getDevice_name(),deviceBean.getDevice_no());


      String query1 = " SELECT max(revision_no) revision_no FROM device c WHERE c.id = "+deviceBean.getDevice_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE device SET active=? WHERE id = ? && revision_no = ? ";
      String query3 = " INSERT INTO device(id,manufacture_id,device_type_id,model_id,remark,revision_no,active) VALUES (?,?,?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,deviceBean.getDevice_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,deviceBean.getDevice_id());
             psmt.setInt(2,manufacture_id);
             psmt.setInt(3,device_type_id);
             psmt.setInt(4,model_id);
             psmt.setString(5,deviceBean.getRemark());
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
  public int getNoOfRows(String searchManufacturerName,String searchDeviceTypeName) {
      String query1="select count(*) "
                     +" from device d,manufacturer mt,device_type dt,model m "
                     +" where d.manufacture_id = mt.id "
                     +" and d.device_type_id = dt.id "
                     +" and d.model_id = m.id "
                     +" AND IF('" + searchManufacturerName + "' = '', mt.name LIKE '%%',mt.name =?) "
                     +" AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
                     +" and d.active='Y' and mt.active='Y' and dt.active='Y'  and m.active='Y' ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchManufacturerName);
            stmt.setString(2, searchDeviceTypeName);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<DeviceBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName,String searchDeviceTypeName) {
        List<DeviceBean> list = new ArrayList<DeviceBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
//       String query2="select id,operation_name,remark "
//                     +" from operation_name m "
//                     +" where IF('" + searchOperationname + "' = '', operation_name LIKE '%%',operation_name =?) "
//                     +" and m.active='Y' "
//                     +addQuery;
//
       String query2="select d.id as device_id,mt.name as manufacturer_name,dt.type as device_type,m.device_name,m.device_no,d.remark "
                     +" from device d,manufacturer mt,device_type dt,model m "
                     +" where d.manufacture_id = mt.id "
                     +" and d.device_type_id = dt.id "
                     +" and d.model_id = m.id "
                     +" AND IF('" + searchManufacturerName + "' = '', mt.name LIKE '%%',mt.name =?) "
                     +" AND IF('" + searchDeviceTypeName + "' = '', dt.type LIKE '%%',dt.type =?) "
                     +" and d.active='Y' and mt.active='Y' and dt.active='Y'  and m.active='Y' "
                     +addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchManufacturerName);
            pstmt.setString(2, searchDeviceTypeName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceBean deviceBean = new DeviceBean();

                deviceBean.setDevice_id(rset.getInt("device_id"));
                deviceBean.setManufacture_name(rset.getString("manufacturer_name"));
                deviceBean.setDevice_type_name(rset.getString("device_type"));
                deviceBean.setDevice_name(rset.getString("device_name"));
                deviceBean.setDevice_no(rset.getInt("device_no"));
                deviceBean.setRemark(rset.getString("remark"));
                list.add(deviceBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

  public int deleteRecord(int device_id) {

      String query = "update device set active='N' where id=" + device_id;
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

  public List<String> getManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' "
                       +" group by name order by name desc ";
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
  public List<String> getDeviceTypeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from device_type where active='Y' "
                       +" group by type order by type desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("type");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
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
  public List<String> getDeviceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_name from model where active='Y' "
                       +" group by device_name order by device_name desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_name = rset.getString("device_name");
                if (device_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_name);
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
  public List<String> getDeviceNo(String q,String device_name) {
        List<String> list = new ArrayList<String>();
        String query = "select device_no from model where device_name=? "
                       +" and active='Y' "
                       +" group by device_no order by device_no desc ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, device_name);
            ResultSet rset = pstmt.executeQuery();
            //ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String device_no = rset.getString("device_no");
                if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device_no);
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



  public List<String> getSearchManufactureName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name "
                       +" from manufacturer m,device d "
                       +" where d.manufacture_id = m.id "
                       +" and d.active='Y' group by m.name";
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

  public List<String> getSearchDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select type "
                      +" from device_type dt,device d "
                      +" where d.device_type_id = dt.id "
                      +" and d.active='Y' group by dt.type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("type");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
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
