/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.model;

import com.ble.command.bean.SubByteDivisionBean;
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
public class SubByteDivisionModel {
    
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
    
    public int getNoOfRows() {

      String query1="select count(*) "
                  +" from sub_byte_division s "
                  +" where s.active='Y' ";
                  

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
            System.out.println("Error inside getNoOfRows SelectionModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
    
    public int getSubByteDataId() {
        int sub_byte_division_id = 0;
         String query1 = "select Max(sub_byte_division_id) as sub_byte_division_id "
                + " from sub_byte_division s "
                + " where s.active='Y' ";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
          
            ResultSet rs = stmt.executeQuery();
            rs.next();
            sub_byte_division_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getSelectionValueId SelectionModel" + e);
        }
        return sub_byte_division_id;
    }
    
    public int getByteDataId(String byte_data_parameter) {
        String query = "select byte_data_id from byte_data where parameter_name = '" + byte_data_parameter + "';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("byte_data_id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }
    
    public int insertRecord(SubByteDivisionBean subByteDivisionBean) {
        
        int sub_byte_division_id = getSubByteDataId() + 1;
        int byte_data_id = getByteDataId(subByteDivisionBean.getParameter_name_byte());
        String query = " insert into sub_byte_division(sub_byte_division_id,byte_id,parameter_name,start_pos,no_of_bit,sub_division_no,remark) "
                       +" values(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,sub_byte_division_id);
            pstmt.setInt(2, byte_data_id);
            pstmt.setString(3, subByteDivisionBean.getParameter_name());
            pstmt.setInt(4, subByteDivisionBean.getStart_pos());
            pstmt.setInt(5, subByteDivisionBean.getNo_of_bit());
            pstmt.setInt(6, subByteDivisionBean.getSub_division_no());
            pstmt.setString(7, subByteDivisionBean.getRemark());
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
//    
//    public boolean reviseRecords(ByteDataBean bean){
//        boolean status=false;
//        String query="";
//        int rowsAffected=0;
////        int parameter_id = getParameterId(bean.getParameter());
////
////        int command_id = getCommandId(bean.getCommandId());
//        String query1 = " SELECT max(revision_no) revision_no FROM selection c WHERE c.selection_id = "+bean.getByte_data_id()+" && active='Y' ORDER BY revision_no DESC";
//        String query2 = " UPDATE selection SET active=? WHERE selection_id = ? && revision_no = ? ";
//        String query3 = " INSERT INTO selection (selection_id,command_id,parameter_id,remark,revision_no,active,selection_value_no) VALUES (?,?,?,?,?,?,?) ";
//
//      int updateRowsAffected = 0;
//      try {
//           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
//           ResultSet rs = ps.executeQuery();
//           if(rs.next()){
//           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//           pst.setString(1,  "N");
//           pst.setInt(2,bean.getByte_data_id());
//           pst.setInt(3, rs.getInt("revision_no"));
//           updateRowsAffected = pst.executeUpdate();
//             if(updateRowsAffected >= 1){
//             int rev = rs.getInt("revision_no")+1;
//             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//             psmt.setInt(1,bean.getByte_data_id());
//             psmt.setInt(2,command_id);
//             psmt.setInt(3,parameter_id);
//             psmt.setString(4,bean.getRemark());
//             psmt.setInt(5,rev);
//             psmt.setString(6,"Y");
//             psmt.setInt(7,bean.getByte_data_id());
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
//
//    }
        public boolean updateRecords(SubByteDivisionBean bean){
        boolean status=false;
        String query="";
        int rowsAffected=0;
      
        int sub_byte_division_id = bean.getSub_byte_division_id();
        int sub_division_no = bean.getSub_division_no();

       
 
//        String query2 = "UPDATE selection as s,command as c,parameter as p SET c.command = '"+command_name+"', p.parameter_name ='"+parameter_name+"',p.parameter_type='"+parameter_type+"',s.selection_value_no='"+selection_value_no+"' WHERE s.selection_id='"+selection_id+" ;" ;
      
 String query2 = "UPDATE sub_byte_division SET sub_division_no="+sub_division_no+" where sub_byte_division_id="+sub_byte_division_id+"";
      int updateRowsAffected = 0;
      try  {
      
        
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
        
           updateRowsAffected = pst.executeUpdate();
            
           
          }catch (Exception e)
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
    
   
    public String getParameterNameByByte_id(int byte_data_id) {
        String type = "";
        String query = "select parameter_name from byte_data where byte_data_id = "+byte_data_id+" and active = 'Y' order by byte_data_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("parameter_name");
//                
            
        } catch (Exception e) {
            System.out.println(" ERROR inside SubByteDivisionModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }
    
    public List<String> getParameterName() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from byte_data order by id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    
                String type = rset.getString("parameter_name");
//                String commandReq = type.substring(1, type.length()-1);
//                String[] commandByte = commandReq.split(", ");
//                Byte[] b = new Byte[commandByte.length];
//                for (int i = 0; i < commandByte.length; i++) {
//                    b[i] = Byte.parseByte(commandByte[i]);                   
//                }
//                String hex = bytesToHex(b);
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Parameter Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside ByteDataModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
    
//    public List<SelectionBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
//        List<SelectionBean> list = new ArrayList<SelectionBean>();
//         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//          if(lowerLimit == -1){
//              addQuery = "";
//          }
//          String commandName = "";
//            
//          if(searchManufacturerName != null) {
////              byte[] hexaByte = DatatypeConverter.parseHexBinary(searchManufacturerName);
////              commandName = Arrays.toString(hexaByte);
//                commandName = searchManufacturerName;
//          }
//          
//
//       String query2="select s.selection_id,c.command,s.remark,s.selection_value_no, p.parameter_name, p.parameter_type"
//                     +" from selection s, parameter p, command c "
//                     +" where s.command_id=c.id and s.parameter_id = p.parameter_id and s.active='Y' and c.active='Y' and p.active='Y' " 
//                     +" and IF('" + commandName + "' = '', c.command LIKE '%%',c.command ='"+commandName+"') "+ addQuery;
//
//
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                SelectionBean selectionBean = new SelectionBean();
//                
//                String command = rset.getString("command");
////                String commandReq = command.substring(1, command.length()-1);
////                String[] commandByte = commandReq.split(", ");
////                Byte[] b = new Byte[commandByte.length];
////                for (int i = 0; i < commandByte.length; i++) {
////                    b[i] = Byte.parseByte(commandByte[i]);                   
////                }
////                String hex = bytesToHex(b);
//                selectionBean.setSelection_id(rset.getInt("selection_id"));
//                selectionBean.setCommand_name(command);
//                selectionBean.setParameter(rset.getString("parameter_name"));
//                selectionBean.setSelection_value_no(Integer.parseInt(rset.getString("selection_value_no")));
//                selectionBean.setParameter_type(rset.getString("parameter_type"));
//                selectionBean.setRemark(rset.getString("remark"));
//                list.add(selectionBean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        return list;
//    }
    
    public List<SubByteDivisionBean> showDataByByteId(int lowerLimit, int noOfRowsToDisplay,String sub_byte_division,String  byte_data_id) {
        List<SubByteDivisionBean> list = new ArrayList<SubByteDivisionBean>();
        
          String commandName = "";
          int i = 1;
            
         
          

       String query2="select s.sub_byte_division_id,b.parameter_name as parameter_name_byte,s.remark,s.start_pos,s.no_of_bit,s.parameter_name as parameter_name_sub ,b.sub_byte_division,s.sub_division_no"
                     +" from sub_byte_division s, byte_data b "
                     +" where s.byte_id=b. byte_data_id and b.active='Y' and s.active='Y' and b.byte_data_id ="+ byte_data_id;
                    


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if(i <= Integer.parseInt(sub_byte_division)) {
                    SubByteDivisionBean subbyteDivisionBean = new SubByteDivisionBean();
                
                        String parameter_name_byte = rset.getString("parameter_name_byte");
                        subbyteDivisionBean.setSub_byte_division_id(rset.getInt("sub_byte_division_id"));
                        subbyteDivisionBean.setParameter_name_byte(parameter_name_byte);
                        subbyteDivisionBean.setParameter_name(rset.getString("parameter_name_sub"));
                          subbyteDivisionBean.setNo_of_bit(Integer.parseInt(rset.getString("no_of_bit")));
                        subbyteDivisionBean.setStart_pos(Integer.parseInt(rset.getString("start_pos")));
                         subbyteDivisionBean.setSub_division_no(Integer.parseInt(rset.getString("sub_division_no")));
                        subbyteDivisionBean.setRemark(rset.getString("remark"));
                        list.add(subbyteDivisionBean);
                } else {
                    break;
                }
                i++;
                
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
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

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
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


