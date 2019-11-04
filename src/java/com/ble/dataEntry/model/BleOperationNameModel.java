/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.model;
import com.ble.dataEntry.bean.BleOperationNameBean;
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
public class BleOperationNameModel {

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


public int insertRecord(BleOperationNameBean ruleBean) {

        String query = " insert into ble_operation_name(ble_operation_name,remark) values(?, ?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, ruleBean.getBle_operation_name());
            pstmt.setString(2, ruleBean.getRemark());

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

public boolean reviseRecords(BleOperationNameBean ruleBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;

      String query1 = " SELECT max(revision_no) revision_no FROM ble_operation_name c WHERE c.ble_operation_name_id = "+ruleBean.getBle_operation_name_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE ble_operation_name SET active=? WHERE ble_operation_name_id = ? && revision_no = ? ";
      String query3 = " INSERT INTO ble_operation_name (ble_operation_name_id,ble_operation_name,remark,revision_no,active) VALUES (?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,ruleBean.getBle_operation_name_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,ruleBean.getBle_operation_name_id());
             psmt.setString(2,ruleBean.getBle_operation_name());
             psmt.setString(3,ruleBean.getRemark());
             psmt.setInt(4,rev);
             psmt.setString(5,"Y");

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
                  +" from ble_operation_name m "
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

  public List<BleOperationNameBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
        List<BleOperationNameBean> list = new ArrayList<BleOperationNameBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

       String query2="select r.ble_operation_name_id,r.remark,r.ble_operation_name "
                     +" from ble_operation_name r"
                     +" where  r.active='Y'";
//                     +" and ('" + searchManufacturerName + "' = '', c.command LIKE '%%',c.command =?)";


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            //pstmt.setString(1, searchCommandName);
            //pstmt.setString(2, searchDeviceName);
            //pstmt.setString(3, searchManufacturerName);
            //pstmt.setString(4, searchDeviceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                BleOperationNameBean manufacturerBean = new BleOperationNameBean();
                manufacturerBean.setBle_operation_name_id(rset.getInt("ble_operation_name_id"));
                manufacturerBean.setBle_operation_name(rset.getString("ble_operation_name"));
                manufacturerBean.setRemark(rset.getString("remark"));
                list.add(manufacturerBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }


  public int deleteRecord(int manufacturer_id) {

      String query = "update ble_operation_name set active='N' where ble_operation_name_id=" + manufacturer_id;
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
        String query = "select command from manufacturer where active='y' group by name order by id desc";
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
        String query = "select ble_operation_name from ble_operation_name where active='Y' group by ble_operation_name order by ble_operation_name_id desc";
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

