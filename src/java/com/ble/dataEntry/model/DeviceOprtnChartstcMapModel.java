/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.model;
import com.ble.dataEntry.bean.DeviceOprtnChartstcMapBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jpss
 */
public class DeviceOprtnChartstcMapModel {

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


public int insertRecord(DeviceOprtnChartstcMapBean ruleBean) {

        String query = " insert into device_characteristic_ble_map(device_id,read_characteristic_id,write_characteristic_id,ble_operation_name_id,order_no,remark) values(?, ?, ?, ?, ?, ?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, ruleBean.getDevice_id());
            pstmt.setInt(2, ruleBean.getRead_characteristics_id());
            pstmt.setInt(3, ruleBean.getWrite_characteristics_id());
            pstmt.setInt(4, ruleBean.getBle_operation_name_id());
            pstmt.setInt(5, ruleBean.getOrder_no());
            pstmt.setString(6, ruleBean.getRemark());

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

public boolean reviseRecords(DeviceOprtnChartstcMapBean ruleBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;

      String query1 = " SELECT max(revision_no) revision_no FROM device_characteristic_ble_map c WHERE c.device_characteristic_ble_map_id = "+ruleBean.getDevice_characteristic_ble_map_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE device_characteristic_ble_map SET active=? WHERE device_characteristic_ble_map_id = ? && revision_no = ? ";
      String query3 = " insert into device_characteristic_ble_map(device_characteristic_ble_map_id,device_id,read_characteristic_id,write_characteristic_id,ble_operation_name_id,order_no,remark,revision_no,active) values(?, ?, ?, ?, ?, ?, ?, ?, ?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,ruleBean.getDevice_characteristic_ble_map_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3); 
             psmt.setInt(1,ruleBean.getDevice_characteristic_ble_map_id());
             psmt.setInt(2,ruleBean.getDevice_id());
             psmt.setInt(3,ruleBean.getRead_characteristics_id());
             psmt.setInt(4,ruleBean.getWrite_characteristics_id());
             psmt.setInt(5,ruleBean.getBle_operation_name_id());
             psmt.setInt(6,ruleBean.getOrder_no());
             psmt.setString(7,ruleBean.getRemark());
             psmt.setInt(8,rev);
             psmt.setString(9,"Y");

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


  public int getNoOfRows(String searchManufacturerName) {
//        String query = " select count(*) "
//                      +" from command c,device d "
//                      +" where c.device_id = d.id "
//                      + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
//                      //+ " and IF('" + searchCommandName + "' = '', device_name LIKE '%%',device_name =?) "
//                      +" and c.active='Y' ";
//        String query1="select count(*) "
//      +" from manufacturer m,device_type dt,model md,operation_name op_n,command c,command_type ct,device d "
//      +" where c.device_id=d.id and d.manufacture_id = m.id "
//      +" and d.device_type_id = dt.id and d.model_id = md.id "
//      +" and c.operation_id = op_n.id and c.command_type_id = ct.id "
//      +" and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
//      +" and IF('" + searchDeviceName + "' = '', md.device_name LIKE '%%',md.device_name =?) "
//      +" and IF('" + searchManufacturerName + "' = '', m.name LIKE '%%',m.name =?) "
//      +" and IF('" + searchDeviceType + "' = '', dt.type LIKE '%%',dt.type =?) "
//      +" and c.active='Y' ";

      String query1="select count(*) "
                  +" from device_characteristic_ble_map m "
                  +" where m.active='Y' ";


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
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<DeviceOprtnChartstcMapBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
        List<DeviceOprtnChartstcMapBean> list = new ArrayList<DeviceOprtnChartstcMapBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";


        String query2="SELECT device_characteristic_ble_map.device_characteristic_ble_map_id, manufacturer.name, device_type.type, model.device_name,c1.uuid as read_characteristics," 
                      +" c2.uuid as write_characteristics, servicies.service_uuid," 
                      +" ble_operation_name.ble_operation_name, device_characteristic_ble_map.order_no, device_characteristic_ble_map.remark" 
                      +" FROM device_characteristic_ble_map " 
                      +" inner join device on device.id = device_characteristic_ble_map.device_id " 
                      +" inner join manufacturer on device.manufacture_id= manufacturer.id " 
                      +" inner join device_type on device.device_type_id= device_type.id "
                      +" inner join model on device.model_id = model.id " 
                      +" inner join charachtristics c1 on device_characteristic_ble_map.read_characteristic_id = c1.id " 
                      +" inner join charachtristics c2 on device_characteristic_ble_map.write_characteristic_id = c2.id " 
                      +" inner join servicies on c1.service_id = servicies.id" 
                      +" inner join ble_operation_name on ble_operation_name.ble_operation_name_id = device_characteristic_ble_map.ble_operation_name_id" 
                      +" where device_characteristic_ble_map.active = 'Y' and device.active = 'Y' and " 
                      +" manufacturer.active = 'Y' and device_type.active = 'Y' and  "
                      +" model.active = 'Y' and c1.active = 'Y' and c2.active = 'Y' and "
                      +" servicies.active = 'Y' and ble_operation_name.active = 'Y' ";

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            //pstmt.setString(1, searchCommandName);
            //pstmt.setString(2, searchDeviceName);
            //pstmt.setString(3, searchManufacturerName);
            //pstmt.setString(4, searchDeviceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceOprtnChartstcMapBean manufacturerBean = new DeviceOprtnChartstcMapBean();

                manufacturerBean.setDevice_characteristic_ble_map_id(rset.getInt("device_characteristic_ble_map_id"));
                manufacturerBean.setManufacturer_name(rset.getString("name"));
                manufacturerBean.setModel_name(rset.getString("device_name"));
                manufacturerBean.setDevice_type(rset.getString("type"));
                manufacturerBean.setService_name(rset.getString("service_uuid"));
                manufacturerBean.setRead_characteristics_name(rset.getString("read_characteristics"));
                manufacturerBean.setWrite_characteristics_name(rset.getString("write_characteristics"));
                manufacturerBean.setBle_operation_name(rset.getString("ble_operation_name"));
                manufacturerBean.setOrder_no(rset.getInt("order_no"));
                manufacturerBean.setRemark(rset.getString("remark"));
                list.add(manufacturerBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }


  public int deleteRecord(int device_characteristic_ble_map) {

      String query = "update device_characteristic_ble_map set active='N' where device_characteristic_ble_map_id=" + device_characteristic_ble_map;
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

  public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='y' group by name order by name desc";
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

  public List<String> getCommand(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select command from command where active='Y' group by command order by id desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("command");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such command exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> geManufacturerName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='Y' group by name order by name desc";
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
                list.add("No such name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getDeviceTypeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from device_type where active='Y' group by type order by type desc";
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
                list.add("No such device_type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getModelName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_name from model where active='Y' group by device_name order by device_name desc";
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
                list.add("No such device name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getBLEOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select ble_operation_name from ble_operation_name where active='Y' group by ble_operation_name order by ble_operation_name desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("ble_operation_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such ble_operation_name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
  public List<String> getCharacteristicsName(String q,int services_id) {
        List<String> list = new ArrayList<String>();
        String query = "select name from charachtristics where active='Y' and service_id = "+services_id+""
                      + " group by name order by name desc";
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
                list.add("No such name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public List<String> getServices(String q,int device_id) {
        List<String> list = new ArrayList<String>();
        String query = "select service_name from servicies where active='Y' and device_id = "+device_id+""
                      + " group by service_name order by service_name desc";
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
                list.add("No such service_name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ruleModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

  public int getCommandId(String command) {
        String query = "SELECT id FROM command WHERE command = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getCharacteristicId(String command) {
        String query = "SELECT id FROM charachtristics WHERE name = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getDeviceTypeId(String command) {
        String query = "SELECT id FROM device_type WHERE type = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getBLEOperationNameId(String command) {
        String query = "SELECT ble_operation_name_id FROM ble_operation_name WHERE ble_operation_name = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("ble_operation_name_id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getModelId(String command) {
        String query = "SELECT id FROM model WHERE device_name = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getManufacturerId(String command) {
        String query = "SELECT id FROM manufacturer WHERE name = ? and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getDeviceId(int manufacturer_id,int device_type_id,int model_id) {
        String query = "SELECT id FROM device WHERE manufacture_id = "+manufacturer_id+" and device_type_id = "+device_type_id+" and model_id = "+model_id+" and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
    }

  public int getServicesId(String service_name) {
        String query = "SELECT id FROM servicies WHERE service_name = '"+service_name+"'  and active='Y'";
        int designation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, command);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_id = rset.getInt("id");
        } catch (Exception e) {
            System.out.println("Error:client EquipmentModel--getCommandid-- " + e);
        }
        return designation_id;
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




