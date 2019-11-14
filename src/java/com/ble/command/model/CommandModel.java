 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.command.model;

import com.ble.command.bean.CommandBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class CommandModel {

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
    public int getDeviceId(int manufacturer_id,int deviceType_id,int model_id) {
        String query = " select id from device where manufacture_id="+manufacturer_id+" and device_type_id="+deviceType_id+" and model_id="+model_id+" and active='Y'";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         device_id=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getDeviceId CommandModel " + e);
        }

        return device_id;
    }
    public int getOperationId(String operation_name) {
        String query = " select op.id from operation_name op where operation_name='"+operation_name+"'";
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

    public int getCommandTypeId(String command_type) {
        String query = " select ct.id from command_type ct where ct.name='"+command_type+"'";
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

    public int getManufacturerId(String manufacturere_type) {
        String query = " select id from manufacturer m where m.name='"+manufacturere_type+"' and m.active='Y'";
        int manufacturerId = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         manufacturerId=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return manufacturerId;
    }
    public int getDeviceTypeId(String device_type) {
        String query = " select id from device_type d where d.type='"+device_type+"' and d.active='Y'";
        int device_type_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         device_type_id=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return device_type_id;
    }
    
    

    public int getModelId(String device_name,String device_no) {
        String query = " select id from model m where device_name='"+device_name+"' and device_no='"+device_no+"' and active='Y'";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         model_id=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return model_id;
    }




    public int insertRecord(CommandBean commandBean) {
       
        int command_type_id = getCommandTypeId(commandBean.getCommand_type());

        String query = " insert into command(command,starting_del,end_del,remark,command_type_id,format,input,selection,bitwise) "
                       +" values(?,?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, commandBean.getCommand());
             pstmt.setString(2, commandBean.getStarting_del());
             pstmt.setString(3, commandBean.getEnd_del());
             pstmt.setString(4, commandBean.getRemark());
             pstmt.setInt(5, command_type_id);
             pstmt.setString(6, commandBean.getFormat());
             pstmt.setInt(7, commandBean.getInput_no());
             pstmt.setInt(8, commandBean.getSelection_no());
             pstmt.setInt(9, commandBean.getBitwise());

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

public boolean reviseRecords(CommandBean bean){
    boolean status=false;
    String query="";
    int rowsAffected=0;
        int manufacturer_id = getManufacturerId(bean.getManufacturer());
        int deviceType_id = getDeviceTypeId(bean.getDevice_type());
        int model_id = getModelId(bean.getDevice_name(),bean.getDevice_no());

        int device_id = getDeviceId(manufacturer_id,deviceType_id,model_id);

        int operation_id = getOperationId(bean.getOperation_name());
        int command_type_id = getCommandTypeId(bean.getCommand_type());


      String query1 = " SELECT max(revision_no) revision_no FROM command c WHERE c.id = "+bean.getCommand_id()+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE command SET active=? WHERE id = ? && revision_no = ? ";
      String query3 = " INSERT INTO command (id,device_id,command,operation_id,starting_del,end_del,remark,command_type_id,revision_no,active,format,selection,input) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,bean.getCommand_id());
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,bean.getCommand_id());
             psmt.setInt(2,device_id);
             psmt.setString(3,bean.getCommand());
             psmt.setInt(4,operation_id);
             psmt.setString(5,bean.getStarting_del());
             psmt.setString(6,bean.getEnd_del());
             psmt.setString(7,bean.getRemark());
             psmt.setInt(8,command_type_id);
             psmt.setInt(9,rev);
             psmt.setString(10,"Y");
             psmt.setString(11, bean.getFormat());
             psmt.setInt(12, bean.getSelection_no());
             psmt.setInt(13, bean.getInput_no());

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


  public int getNoOfRows(String searchCommandName,String searchDeviceName,String searchManufacturerName,String searchDeviceType) {
//        String query = " select count(*) "
//                      +" from command c,device d "
//                      +" where c.device_id = d.id "
//                      + " and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
//                      //+ " and IF('" + searchCommandName + "' = '', device_name LIKE '%%',device_name =?) "
//                      +" and c.active='Y' ";
    String query1 = "select count(*)  from command c  where IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command ='')  and c.active='Y' ;";
        

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
           
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

  public List<CommandBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCommandName,String searchDeviceName,String searchManufacturerName,String searchDeviceType) {
        List<CommandBean> list = new ArrayList<CommandBean>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";


       String query2="select c.id as command_id,c.command, "
        +" c.starting_del,c.end_del,c.remark,ct.name as command_type,c.format as format, c.input, c.selection, c.bitwise "
        +" from command c,command_type ct "
        +" where c.command_type_id = ct.id "
        +" and IF('" + searchCommandName + "' = '', command LIKE '%%',command =?) "
        +" and c.active='Y' and ct.active='Y' order by c.created_at desc "
        +addQuery;


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            pstmt.setString(1, searchCommandName);
           
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CommandBean commandBean = new CommandBean();

                commandBean.setCommand_id(rset.getInt("command_id"));
                String command = rset.getString("command");
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                commandBean.setCommand(command);
                commandBean.setStarting_del(rset.getString("starting_del"));
                commandBean.setEnd_del(rset.getString("end_del"));
                commandBean.setRemark(rset.getString("remark"));
                commandBean.setCommand_type(rset.getString("command_type"));
                commandBean.setFormat(rset.getString("format"));
                commandBean.setSelection_no(rset.getInt("selection"));
                commandBean.setInput_no(rset.getInt("input"));
                commandBean.setBitwise(rset.getInt("bitwise"));

                list.add(commandBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
  
  public static String bytesToHex(Byte[] in) {
    final StringBuilder builder = new StringBuilder();
    for(byte b : in) {
        builder.append(String.format("%02x", b));
    }
    return builder.toString();
  } 


  public int deleteRecord(int command_id) {

      String query = "update command set active='N' where id=" + command_id;
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
            System.out.println("Error inside closeConnection CommandModel:" + e);
        }
    }

   public List<String> getDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select type from device_type group by type order by type desc;";
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
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
   public List<String> getManufacturer(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from manufacturer where active='y' order by name desc";
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
   public List<String> getDeviceName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_name from model m where m.active='Y' group by device_name order by device_name desc";
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
                list.add("No such Model Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

   public List<String> getDeviceNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select device_no from model m where m.active='Y' group by device_no order by device_no desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
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
                list.add("No such Model Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

   public List<String> getOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name group by operation_name order by operation_name desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String operation_name = rset.getString("operation_name");
                if (operation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(operation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

   public List<String> getCommandType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select name from command_type ct group by name order by name desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

   public List<String> getSearchCommandName(String q) {
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

   public List<String> getSearchManufacturerName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select m.name "
                       +" from device d,command c,manufacturer m "
                       +" where c.device_id = d.id "
                       +" and d.manufacture_id = m.id "
                       +" and c.active='Y' group by m.name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("name");
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

   public List<String> getSearchDeviceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select dt.type "
                       +" from device d,command c,device_type dt "
                       +" where c.device_id = d.id "
                       +" and d.device_type_id = dt.id "
                       +" and c.active='Y' group by dt.type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String command = rset.getString("type");
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


    public List<String> getSearchDeviceName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = " select device_name "
//                       +" from command c,device d "
//                       +" where c.device_id = d.id "
//                       +" and c.active='Y' group by device_name ";
        String query1 = "select device_name "
                       +" from device d,command c,model m "
                       +" where c.device_id = d.id "
                       +" and d.model_id = m.id "
                       +" and c.active='Y' group by device_name ";
        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
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
                list.add("No such Device Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }








    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

}
