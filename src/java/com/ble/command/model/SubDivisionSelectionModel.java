/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.model;

import com.ble.command.bean.SubByteDivisionBean;
import com.ble.command.bean.SubDivisionSelectionBean;
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
public class SubDivisionSelectionModel {
    
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
            connection = (Connection)DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("CommandModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchSelectionName) {

        String query1 = "select count(*) "
                + " from sub_division_selection s "
                + " where s.active='Y' ";

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
    
    public int getSubDivisionSelectionId() {
        int sub_division_selection_id = 0;
         String query1 = "select Max(sub_division_selection_id) as sub_division_selection_id "
                + " from sub_division_selection s "
                + " where s.active='Y' ";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
          
            ResultSet rs = stmt.executeQuery();
            rs.next();
            sub_division_selection_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getSelectionValueId SelectionModel" + e);
        }
        return sub_division_selection_id;
    }

    public int insertRecord(SubDivisionSelectionBean subDivisionSelectionBean) {

        int sub_division_selection_id = getSubDivisionSelectionId() + 1;

        String query = " insert into sub_division_selection(sub_division_selection_id,display_value,bit_value,sub_byte_division_id) "
                + " values(?,?,?,?)";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, sub_division_selection_id);
            pstmt.setString(2, subDivisionSelectionBean.getDisplay_value());
            pstmt.setString(3, subDivisionSelectionBean.getBit_value());
            pstmt.setInt(4, subDivisionSelectionBean.getSub_byte_division_id());
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

//    public boolean reviseRecords(SelectionValueBean selectionValuebean) {
//        boolean status = false;
//        String query = "";
//        int rowsAffected = 0;
//
//        int selection_id = getSelectionId(selectionValuebean.getParameter());
//        String query1 = " SELECT max(revision_no) revision_no FROM selection_value c WHERE c.selection_value_id = " + selectionValuebean.getSelection_value_id() + " && active='Y' ORDER BY revision_no DESC";
//        String query2 = " UPDATE selection SET active=? WHERE selection_value_id = ? && revision_no = ? ";
//        String query3 = " INSERT INTO selection_value (selection_value_id,selection_id,display_value,byte_value,remark,revision_no,active) VALUES (?,?,?,?,?,?,?) ";
//
//        int updateRowsAffected = 0;
//        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//                pst.setString(1, "N");
//                pst.setInt(2, selectionValuebean.getSelection_value_id());
//                pst.setInt(3, rs.getInt("revision_no"));
//                updateRowsAffected = pst.executeUpdate();
//                if (updateRowsAffected >= 1) {
//                    int rev = rs.getInt("revision_no") + 1;
//                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//                    psmt.setInt(1, selectionValuebean.getSelection_value_id());
//                    psmt.setInt(2, selection_id);
//                    psmt.setString(3, selectionValuebean.getDisplay_value());
//                    psmt.setString(4, selectionValuebean.getByte_value());
//                    psmt.setString(5, selectionValuebean.getRemark());
//                    psmt.setInt(6, rev);
//                    psmt.setString(7, "Y");
//
//                    int a = psmt.executeUpdate();
//                    if (a > 0) {
//                        status = true;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("CommandValueModel reviseRecord() Error: " + e);
//        }
//        if (status) {
//            message = "Record updated successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//            message = "Record Not updated Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not updated");
//        }
//
//        return status;
//
//    }

    public int getSelectionId(String sub_division_no) {
        String query = "select sub_byte_division_id from sub_byte_division where  = '" + sub_division_no + "';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside SelectionModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<Integer> getSelectionName() {
        List<Integer> list = new ArrayList<Integer>();
        String query = "select sub_division_no from sub_byte_division order by sub_byte_division_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                int type = rset.getInt("sub_division_no  ");
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
                list.add(0);
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getSelectionNameBySelection_id(int selection_id) {
        String type = "";
        String query = "select parameter_name from parameter p ,selection s, selection_value sv where sv.selection_id=s.selection_id and p.parameter_id=s.parameter_id";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("parameter_name");
//                

        } catch (Exception e) {
            System.out.println(" ERROR inside SelectionValueModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<SubDivisionSelectionBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchSelectionName, String parameter_name, int sub_byte_division_id) {
        List<SubDivisionSelectionBean> list = new ArrayList<>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String parameter1 = parameter_name;
        int  sub_byte_division_id1 = sub_byte_division_id;
        if (searchSelectionName != null) {

            parameter_name = searchSelectionName;
        }

        String query2;
        query2 = " SELECT sub_division_selection_id,display_value, bit_value,sub_byte_division.parameter_name, sub_division_selection.sub_byte_division_id "
                + " FROM sub_division_selection inner join sub_byte_division on sub_division_selection.sub_byte_division_id = sub_byte_division.sub_byte_division_id   "
                + "where sub_byte_division.active = 'Y' and sub_division_selection.active = 'Y' and sub_byte_division.parameter_name ='" + parameter1 + "' "
                + "and sub_division_selection.sub_byte_division_id =" + sub_byte_division_id1 + ";";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SubDivisionSelectionBean subDivisionSelectionBean = new  SubDivisionSelectionBean();

               parameter_name = rset.getString("parameter_name");

                subDivisionSelectionBean.setSub_division_selection_id(rset.getInt("sub_division_selection_id"));

              
                subDivisionSelectionBean.setDisplay_value(rset.getString("display_value"));
               subDivisionSelectionBean.setBit_value(rset.getString("bit_value"));
              
                list.add(subDivisionSelectionBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getParameter() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from sub_byte_division where active='Y' order by sub_byte_division_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("parameter_name");
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Parameter Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<SubDivisionSelectionBean> showDataBySelectionId(int lowerLimit, int noOfRowsToDisplay, int sub_byte_division_id, String parameter_name) {
        List<SubDivisionSelectionBean> list = new ArrayList<SubDivisionSelectionBean>();

        String searchName = "";
        int i = 1;

        String query2 = "select s.selection_value_id,s.remark,s.selection_value_no"
                + " from selection_value s, selection c "
                + " where s.selection_id=c.id and s.active='Y' and c.active='Y' and s.sub_byte_division_id = " + sub_byte_division_id;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (i <= (Integer.parseInt(parameter_name))) {
                   SubDivisionSelectionBean subDivisionSelectionBean = new SubDivisionSelectionBean();

                    parameter_name = rset.getString("parameter_name");
                    subDivisionSelectionBean.setSub_division_selection_id(rset.getInt("sub_division_selection_id"));
                    subDivisionSelectionBean.setDisplay_value(rset.getString("display_value"));
                    subDivisionSelectionBean.setBit_value(rset.getString("bit_value"));

                    list.add(subDivisionSelectionBean);
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

