/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.command.model;


import com.ble.command.bean.DeviceOperationCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class DeviceOperationCommandModel {

     private static Connection connection;
    private String driverClass;
    private static String connectionString;
    private String db_username;
    private String db_password;
    DeviceOperationCommand commandBean = new DeviceOperationCommand();
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
    
    
     public int getDevice_op_comId(  int device_commamnd_id) {
       String query="SELECT * FROM device_command_map ORDER BY device_command_id DESC LIMIT 0, 1"; 

        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         device_commamnd_id=rs.getInt("device_command_id")+1;
         }
        } catch (Exception e) {
            System.out.println("Error inside getDeviceId CommandModel " + e);
        }

        return device_commamnd_id;
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

    public int getCommandTypeId(String command) {
        String query = " select ct.id from command ct where ct.command='"+command+"'";
        int command_name_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         command_name_id=rs.getInt("id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return command_name_id;
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


    public static List<DeviceOperationCommand> showReport(int device_command_id, DeviceOperationCommand userEntryByImageBean1,String searchCommandName,String searchDeviceName,String searchOperationName  ){
        List<DeviceOperationCommand> list = new ArrayList<DeviceOperationCommand>();
        List<DeviceOperationCommand> list1 = new ArrayList<DeviceOperationCommand>();
        DeviceOperationCommand tpBean = new DeviceOperationCommand();
      

        Map<String, String> map = new HashMap<String, String>();
        String query="select md.device_name,dcm.device_command_id,m.name as manu_name,dt.type ,md.device_no as mno,opn.operation_name,c.command,c.id,dcm.remark"
                    + " from device_command_map dcm,device d,model md ,manufacturer m ,device_type dt,operation_name opn,command c"
                    + " where dcm.device_id=d.id and d.active='Y' and d.model_id = md.id and d.manufacture_id = m.id and d.device_type_id = dt.id  and dcm.operation_id=opn.id and dcm.command_id=c.id"
                    + " and dcm.active='Y' and md.active='Y' and m.active='Y' and dt.active='Y' and opn.active='Y' and c.active='Y'"
                    +" and device_command_id="+ device_command_id; 
                      
        try{
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
           
             
             ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
               // userEntryByImageBean tpBean = new userEntryByImageBean();
                //String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name"));

                 device_command_id = rs.getInt("device_command_id");
                String device_name = rs.getString("device_name");
                String operation_name = rs.getString("operation_name");
                String command = rs.getString("command");
                String manufacturer = rs.getString("manu_name");
                String device_type = rs.getString("type");
                String device_no = rs.getString("id");
                tpBean.setDevice_command_id(device_command_id);
                tpBean.setDevice_name(device_name);
                tpBean.setCommand(command);
                tpBean.setOperation_name(operation_name);
                tpBean.setManufacturer(manufacturer);
                tpBean.setDevice_type(device_type);
                tpBean.setDevice_no(device_no);
               
                //list.add(tpBean);
                
            }                      
                 //list.add(list1);
           // showMISData2(tpBean,device_command_id);
            list.add(tpBean);
            //}
        }catch(Exception ex){
            System.out.println("ERROR : in showData() in Model : " + query);
        }
        return list;
    }
    
    
      public static List<DeviceOperationCommand> showPDF(DeviceOperationCommand dcm,String searchCommandName,String searchDeviceName,String searchOperationName){
        List<DeviceOperationCommand> list = new ArrayList<DeviceOperationCommand>();
        Map<String, String> map = new HashMap<String, String>();
        String query=" select dcm.device_command_id, " +
                    " dcm.device_id,dcm.remark,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type  " +
                    " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt " +
                    " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id " +
                    "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'";
//                      + " and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
//                     + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
//                     + " and iF('" + searchOperationName +"'='',opn.operation_name LIKE '%%',opn.operation_name=?)"     
                  
        try{
               PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//                 pstmt.setString(1, searchCommandName);
//            pstmt.setString(2, searchDeviceName);
//            pstmt.setString(3, searchOperationName);
            ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
              // List<device_operation_command> list1 = new ArrayList<device_operation_command>();
                DeviceOperationCommand dcm1 = new DeviceOperationCommand();
                dcm1.setDevice_command_id(rs.getInt("device_command_id"));
              //  commandBean.setCommand_id(rset.getInt("id"));
              
                dcm1.setManufacturer(rs.getString("name"));
                dcm1.setDevice_type(rs.getString("type"));
                dcm1.setDevice_name(rs.getString("device_name"));
                dcm1.setDevice_no(rs.getString("device_no"));
                String command = rs.getString("command");
                
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                
//                String hex = bytesToHex(b);
                dcm1.setCommand(command);
              
                dcm1.setOperation_name(rs.getString("operation_name"));
              
                dcm1.setRemark(rs.getString("remark"));
                

                list.add(dcm1);
                //list.add(list1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    
    
    
    
     public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (JRException e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
    
    
    
    public int getMaxCommandId(String manufacturere_type) {
        String query = " select Max(device_command_id) from device_command_map dcm where dcm.active='Y'";
        int manufacturerId = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
            manufacturerId=rs.getInt("device_command_id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getOperationId CommandModel" + e);
        }

        return manufacturerId;
    }
    
     public int insertRecord1(DeviceOperationCommand commandBean) {
       
        int deviceType_id = getDeviceTypeId(commandBean.getDevice_type());
        int manufdacture_id = getManufacturerId(commandBean.getManufacturer());
        int model_id = getModelId(commandBean.getDevice_name(), commandBean.getDevice_no());
        int device_id = getDeviceId(manufdacture_id, deviceType_id, model_id);
      int operation_id = getOperationId(commandBean.getOperation_name());
        int command_type_id = getCommandTypeId(commandBean.getCommand());
        int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());

       
        String query = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,remark) "
                       +" values(?,?,?,?,?) ";
      
        
        
      int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,device_command_id+1 );
            pstmt.setInt(2,device_id);
            pstmt.setInt(3,command_type_id);
            pstmt.setInt(4,operation_id);
            pstmt.setString(5,commandBean.getRemark());

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
   
public boolean reviseRecords(DeviceOperationCommand  commandBean){
    boolean status=false;
    String query="";
    int rowsAffected=0;
       
        int deviceType_id = getDeviceTypeId(commandBean.getDevice_type());
      int operation_id = getOperationId(commandBean.getOperation_name());
        int command_type_id = getCommandTypeId(commandBean.getCommand_type());
        int device_command_id = getDevice_op_comId(commandBean.getDevice_command_id());


      String query1 = " SELECT max(revision_no) revision_no FROM device_command_map c WHERE c.device_command_id = "+device_command_id+" && active='Y' ORDER BY revision_no DESC";
      String query2 = " UPDATE device_command_map SET active=? WHERE device_command_id = ? && revision_no = ? ";
      String query3 = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,remark) VALUES (?,?,?,?,?) ";

      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,device_command_id);
           pst.setInt(3, rs.getInt("revision_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("revision_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,device_command_id);
             psmt.setInt(2,deviceType_id);
             psmt.setInt(3,command_type_id);
             psmt.setInt(4,operation_id);
           
             psmt.setString(5,commandBean.getRemark());
            
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


  public static  int getNoOfRows(String searchCommandName,String searchDeviceName,String searchOperationName) {
        String query1 = " select count(*)" 
                      + " from device_command_map dcm,device d,command c,operation_name opn"
                      + " where dcm.device_id = d.id and dcm.command_id = c.id and dcm.operation_id = opn.id and dcm.active='Y' and d.active='Y' and c.active='Y' and opn.active='Y'";
               
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

  public static List<DeviceOperationCommand> showData(int lowerLimit, int noOfRowsToDisplay, String searchCommandName,String searchDeviceName,String searchOperationName) {
        List<DeviceOperationCommand> list = new ArrayList<DeviceOperationCommand>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
          
      String query3=" select dcm.device_command_id, " +
                    " dcm.device_id,dcm.remark,opn.operation_name,c.command,mf.name,m.device_name,m.device_no,dt.type  " +
                    " from device_command_map dcm,device d,operation_name opn,command c ,manufacturer mf,model m,device_type dt " +
                    " where dcm.device_id=d.id  and dcm.operation_id=opn.id and c.id=dcm.command_id  and mf.id=d.manufacture_id and d.model_id=m.id and d.device_type_id=dt.id " +
                    "  and dcm.active='Y' and d.active='Y' and opn.active='Y' and c.active='Y' and mf.active='Y' and m.active='Y' and dt.active='Y'"
                  + " and IF('" + searchCommandName + "' = '', c.command LIKE '%%',c.command =?) "
                  + " and IF('" + searchDeviceName + "' = '', m.device_name LIKE '%%',m.device_name =?) "
                  + " and iF('" + searchOperationName +"'='',opn.operation_name LIKE '%%',opn.operation_name=?)"  
                  + addQuery;
                         
        try {
            
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query3);
            pstmt.setString(1, searchCommandName);
            pstmt.setString(2, searchDeviceName);
            pstmt.setString(3, searchOperationName);
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DeviceOperationCommand commandBean = new DeviceOperationCommand();

                commandBean.setDevice_command_id(rset.getInt("device_command_id"));
              //  commandBean.setCommand_id(rset.getInt("id"));
              
                commandBean.setManufacturer(rset.getString("name"));
                commandBean.setDevice_type(rset.getString("type"));
                commandBean.setDevice_name(rset.getString("device_name"));
                commandBean.setDevice_no(rset.getString("device_no"));
                String command = rset.getString("command");
//                String commandReq = command.substring(1, command.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                commandBean.setCommand(command);
              
                commandBean.setOperation_name(rset.getString("operation_name"));
              
                commandBean.setRemark(rset.getString("remark"));
                

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


  public int deleteRecord(int device_command_id) {

      String query = "update device_command_map set active='N' where id=" + device_command_id;
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

    public List<String> getDeviceTypeName(String q, String manufacturer_name) {
        List<String> list = new ArrayList<String>();
        String query = " select dt.type " 
                      +" from device d inner join manufacturer m on d.manufacture_id = m.id"
		     +" inner join model ml on d.model_id = ml.id"
                     +" inner join device_type dt on dt.id = d.device_type_id"
                     +" inner join modal_type mt on ml.model_type_id = mt.id"
                     +" where d.manufacture_id = (select id from manufacturer m where m.name=? and m.active='Y')"
                     +" and mt.type = 'module'"
                     +" and d.device_type_id = dt.id  and dt.active='Y' and d.active='Y' and m.active='Y' and mt.active='Y' and ml.active = 'Y'";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, manufacturer_name);
            ResultSet rset = pstmt.executeQuery();

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
   public List<String> getDeviceName(String q,String manufacturer,String devicetype) {
         List<String> list = new ArrayList<String>();
        String query = " select ml.device_name " +
                       " from device d inner join manufacturer m on d.manufacture_id = m.id " +
                       " inner join model ml on d.model_id = ml.id " +
                       " inner join device_type dt on dt.id = d.device_type_id " +
                       " inner join modal_type mt on ml.model_type_id = mt.id " +
                       " where d.manufacture_id = (select id from manufacturer m where m.name='UBlox' and m.active='Y') " +
                       " and d.device_type_id = (select id from device_type dt where dt.type='DGPS' and dt.active='Y') " +
                       " and mt.type = 'module'" +
                       " and d.device_type_id = dt.id  and dt.active='Y' and d.active='Y' and m.active='Y' and mt.active='Y' "; 
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

    public List<String> getDeviceNo(String q, String device_name,String manufacturer,String devicetype) {
        List<String> list = new ArrayList<String>();
        String query = " select ml.device_no from device d " +
                       " inner join manufacturer m on d.manufacture_id = m.id " +
                       " inner join model ml on d.model_id = ml.id inner join device_type dt on dt.id = d.device_type_id " +
                       " inner join modal_type mt on ml.model_type_id = mt.id where d.manufacture_id = (select id from manufacturer m where m.name=? and m.active='Y')" +
                       " and d.device_type_id = (select id from device_type dt where dt.type=? and dt.active='Y')" +
                       " and  ml.id= (select id from model mo where mo.device_name=? and mo.active='Y') " +
                       " and mt.type = 'module' and d.device_type_id = dt.id  and dt.active='Y' and d.active='Y' and m.active='Y' and mt.active='Y' and ml.active = 'Y'"; 
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, manufacturer);
            pstmt.setString(2, devicetype);
            pstmt.setString(3, device_name);
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
        String query = " select command from command c,device_command_map dcm"
                       +" where dcm.command_id=c.id group by command order by command desc ";
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

    public List<String> getSearchOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select operation_name from operation_name "
                       +" where active='Y' group by operation_name order by operation_name desc ";
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
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("ERROR inside Command Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
   
    
    
      public List<String> getCommand(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select command from command "
                       +" where active='Y' ";
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
                       +" from device d,device_command_map c,device_type dt "
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
                       +" from device d,device_command_map c,model m "
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
