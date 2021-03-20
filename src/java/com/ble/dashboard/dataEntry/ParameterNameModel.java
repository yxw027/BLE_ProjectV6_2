/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author saini
 */
public class ParameterNameModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private String module_table_id;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("AddDeviceModel setConnection() Error: " + e);
        }
    }

    public List<ParameterNameBean> showData() {
        List<ParameterNameBean> list = new ArrayList<ParameterNameBean>();
        PreparedStatement psmt;
        ResultSet rst;
        String query = "";
        try {
            query = " select * from parameter where active='Y' order by parameter_id desc ";
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                ParameterNameBean bean = new ParameterNameBean();
                bean.setId(rst.getString(1));
                bean.setParameter_name(rst.getString(2));
                bean.setParameter_type(rst.getString(3));
                bean.setActive(rst.getString(4));
                bean.setRemark(rst.getString(7));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.ble.dashboard.dataEntry.ParameterNameModel.showdata() -" + e);
        }
        return list;
    }
    
    public int insertRecordForOther(ParameterNameBean bean) throws SQLException {
        String query = "";
        String parameter_id = "", selection_id = "";
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();            

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        }
        return count;
    }

    public int insertRecord(ParameterNameBean bean, int selection_val, List<String> displayVal, List<String> byteVal) throws SQLException {
        String query = "", query1 = "", query2 = "", query3 = "", query4 = "", query5 = "";
        String parameter_id = "", selection_id = "";
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();

            if (count > 0) {
                psmt = null;
                rst = null;
                count = 0;
                query1 = " Select max(parameter_id) from parameter ";
                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    parameter_id = rst.getString(1);
                }
                if (parameter_id == null) {
                    parameter_id = "1";
                }

                psmt = null;
                rst = null;
                query2 = " insert into selection(parameter_id,selection_value_no) "
                        + " values(?,?) ";
                psmt = connection.prepareStatement(query2);
                psmt.setInt(1, Integer.parseInt(parameter_id));
                psmt.setInt(2, selection_val);
                count = psmt.executeUpdate();
                if (count > 0) {
                    psmt = null;
                    rst = null;
                    query3 = " select max(selection_id) from selection ";
                    psmt = connection.prepareStatement(query3);
                    rst = psmt.executeQuery();
                    while (rst.next()) {
                        selection_id = rst.getString(1);
                    }
                    if (selection_id == null) {
                        selection_id = "1";
                    }

                    for (int k = 0; k < displayVal.size(); k++) {
                        count = 0;
                        psmt = null;
                        rst = null;
                        String qry4 = " insert into selection_value(display_value,byte_value,selection_id)"
                                + " values(?,?,?) ";
                        psmt = connection.prepareStatement(qry4);
                        psmt.setString(1, displayVal.get(k));
                        psmt.setString(2, byteVal.get(k));
                        psmt.setString(3, selection_id);
                        count = psmt.executeUpdate();
                    }
                }
            }

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        } finally {
//            if (connection != null) {
//                connection.close();
//            }
        }
        return count;
    }

    public int insertRecordInBitWise(ParameterNameBean bean, int bitiwse_no, List<String> subByteDiv, List<String> subDivSelNo, List<String> startPos, List<String> noOfBit, List<String> bitDispVal, List<String> bitByteVal,List<String> subByteDivName) throws SQLException {
        String query = "", query1 = "", query2 = "", query3 = "", query4 = "", query5 = "",query6="";
        String parameter_id = "", byte_data_id = "",sub_byte_division_id="";
        int a=0,b=0;
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();

            if (count > 0) {
                psmt = null;
                rst = null;
                count = 0;
                query1 = " Select max(parameter_id) from parameter ";
                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    parameter_id = rst.getString(1);
                }
                if (parameter_id == null) {
                    parameter_id = "1";
                }

                for (int i=0;i<1;i++ ) {
                    psmt = null;
                    rst = null;

                    query2 = " insert into byte_data(sub_byte_division,parameter_id,command_id) "
                            + " values(?,?,?) ";
                    psmt = connection.prepareStatement(query2);
                    psmt.setString(1, subByteDiv.get(i));
                    psmt.setString(2, parameter_id);
                    psmt.setString(3, "1");
                    count = psmt.executeUpdate();
                    if(count>0){                        
                        for(int k=0;k<Integer.parseInt(subByteDiv.get(i));k++){
                            count=0;
                            psmt=null;
                            rst=null;
                            query3=" select max(byte_data_id) from byte_data ";
                            psmt=connection.prepareStatement(query3);
                            rst=psmt.executeQuery();
                            while(rst.next()){
                                byte_data_id=rst.getString(1);
                            }
                            
                            psmt=null;
                            rst=null;
                            query4=" insert into sub_byte_division(byte_id,start_pos,no_of_bit,sub_division_no,parameter_name) "
                                    + " values(?,?,?,?,?) ";
                            psmt=connection.prepareStatement(query4);
                            psmt.setString(1, byte_data_id);
                            psmt.setString(2, startPos.get(a));
                            psmt.setString(3, noOfBit.get(a));
                            psmt.setString(4, subDivSelNo.get(a));                            
                            psmt.setString(5, subByteDivName.get(i));                            
                            count=psmt.executeUpdate();
                            a++;
                            
                            if(count>0){
                                psmt=null;
                                rst=null;
                                query5=" select max(sub_byte_division_id) from sub_byte_division ";
                                psmt=connection.prepareStatement(query5);
                                rst=psmt.executeQuery();
                                while(rst.next()){
                                    sub_byte_division_id=rst.getString(1);
                                }
                                
                                for(int j=0;j<Integer.parseInt(subDivSelNo.get(k));j++){                                    
                                    psmt=null;
                                    rst=null;
                                    query6=" insert into sub_division_selection(display_value,bit_value,sub_byte_division_id) "
                                            + " values(?,?,?) ";
                                    psmt=connection.prepareStatement(query6);
                                    psmt.setString(1, bitDispVal.get(b));
                                    psmt.setString(2, bitByteVal.get(b));
                                    psmt.setString(3, sub_byte_division_id);
                                    count=psmt.executeUpdate();
                                    b++;
                                }
                                
                                
                            }
                            
                        }
                    }

                }

            }

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        } finally {
//            if (connection != null) {
//                connection.close();
//            }
        }
        return count;
    }
    
    
    public int insertRecordForFixedResponse(ParameterNameBean bean, int selection_val, List<String> displayVal, List<String> byteVal) throws SQLException {
        String query = "", query1 = "", query2 = "", query3 = "", query4 = "", query5 = "";
        String parameter_id = "", fixed_response_id = "";
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();

            if (count > 0) {
                psmt = null;
                rst = null;
                count = 0;
                query1 = " Select max(parameter_id) from parameter ";
                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    parameter_id = rst.getString(1);
                }
                if (parameter_id == null) {
                    parameter_id = "1";
                }

                psmt = null;
                rst = null;
                query2 = " insert into fixed_response(parameter_id,fixed_response_value_no) "
                        + " values(?,?) ";
                psmt = connection.prepareStatement(query2);
                psmt.setInt(1, Integer.parseInt(parameter_id));
                psmt.setInt(2, selection_val);
                count = psmt.executeUpdate();
                if (count > 0) {
                    psmt = null;
                    rst = null;
                    query3 = " select max(fixed_response_id) from fixed_response ";
                    psmt = connection.prepareStatement(query3);
                    rst = psmt.executeQuery();
                    while (rst.next()) {
                        fixed_response_id = rst.getString(1);
                    }
                    if (fixed_response_id == null) {
                        fixed_response_id = "1";
                    }

                    for (int k = 0; k < displayVal.size(); k++) {
                        count = 0;
                        psmt = null;
                        rst = null;
                        String qry4 = " insert into fixed_response_value(display_value,select_value,fixed_response_id)"
                                + " values(?,?,?) ";
                        psmt = connection.prepareStatement(qry4);
                        psmt.setString(1, displayVal.get(k));
                        psmt.setString(2, byteVal.get(k));
                        psmt.setString(3, fixed_response_id);
                        count = psmt.executeUpdate();
                    }
                }
            }

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecordForFixedResponse...." + e);
        } finally {
        }
        return count;
    }
    
    
    public int insertRecordForBitwiseResponse(ParameterNameBean bean, int bitiwse_no, List<String> subByteDiv, List<String> subDivSelNo, List<String> startPos, List<String> noOfBit, List<String> bitDispVal, List<String> bitByteVal,List<String> subByteDivName) throws SQLException {
        String query = "", query1 = "", query2 = "", query3 = "", query4 = "", query5 = "",query6="";
        String parameter_id = "", byte_data_id = "",sub_byte_division_id="";
        int a=0,b=0;
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();

            if (count > 0) {
                psmt = null;
                rst = null;
                count = 0;
                query1 = " Select max(parameter_id) from parameter ";
                psmt = connection.prepareStatement(query1);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    parameter_id = rst.getString(1);
                }
                if (parameter_id == null) {
                    parameter_id = "1";
                }

                for (int i=0;i<1;i++ ) {
                    psmt = null;
                    rst = null;

                    query2 = " insert into byte_data_response(sub_byte_division,parameter_id) "
                            + " values(?,?) ";
                    psmt = connection.prepareStatement(query2);
                    psmt.setString(1, subByteDiv.get(i));
                    psmt.setString(2, parameter_id);
                    //psmt.setString(3, "1");
                    count = psmt.executeUpdate();
                    if(count>0){                        
                        for(int k=0;k<Integer.parseInt(subByteDiv.get(i));k++){
                            count=0;
                            psmt=null;
                            rst=null;
                            query3=" select max(byte_data_response_id) from byte_data_response ";
                            psmt=connection.prepareStatement(query3);
                            rst=psmt.executeQuery();
                            while(rst.next()){
                                byte_data_id=rst.getString(1);
                            }
                            
                            psmt=null;
                            rst=null;
                            query4=" insert into response_sub_byte_division(res_byte_id,start_pos,no_of_bit,sub_division_no) "
                                    + " values(?,?,?,?) ";
                            psmt=connection.prepareStatement(query4);
                            psmt.setString(1, byte_data_id);
                            psmt.setString(2, startPos.get(a));
                            psmt.setString(3, noOfBit.get(a));
                            psmt.setString(4, subDivSelNo.get(a));                            
                            //psmt.setString(5, subByteDivName.get(i));                            
                            count=psmt.executeUpdate();
                            a++;
                            
                            if(count>0){
                                psmt=null;
                                rst=null;
                                query5=" select max(response_sub_byte_division_id) from response_sub_byte_division ";
                                psmt=connection.prepareStatement(query5);
                                rst=psmt.executeQuery();
                                while(rst.next()){
                                    sub_byte_division_id=rst.getString(1);
                                }
                                
                                for(int j=0;j<Integer.parseInt(subDivSelNo.get(k));j++){                                    
                                    psmt=null;
                                    rst=null;
                                    query6=" insert into response_sub_division_selection(display_value,bit_value,response_sub_byte_division_id) "
                                            + " values(?,?,?) ";
                                    psmt=connection.prepareStatement(query6);
                                    psmt.setString(1, bitDispVal.get(b));
                                    psmt.setString(2, bitByteVal.get(b));
                                    psmt.setString(3, sub_byte_division_id);
                                    count=psmt.executeUpdate();
                                    b++;
                                }
                                
                                
                            }
                            
                        }
                    }

                }

            }

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecordForBitwiseResponse...." + e);
        } finally {
        }
        return count;
    }
    
    
    public int insertRecordForVariableResponse(ParameterNameBean bean) throws SQLException {
        String query = "";
        String parameter_id = "", selection_id = "";
        PreparedStatement psmt;
        ResultSet rst;
        int count = 0;
        try {
            connection.setAutoCommit(false);
            query = " insert into parameter(parameter_name,parameter_type,remark) values(?,?,?)";
            psmt = connection.prepareStatement(query);
            psmt.setString(1, bean.getParameter_name());
            psmt.setString(2, bean.getParameter_type());
            psmt.setString(3, bean.getRemark());
            count = psmt.executeUpdate();            

            if (count > 0) {
                connection.commit();
                message = "Record Saved Successfully!";
                msgBgColor = COLOR_OK;
            } else {
                connection.rollback();
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecordForVariableResponse...." + e);
        }
        return count;
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

    /**
     * @return the module_table_id
     */
    public String getModule_table_id() {
        return module_table_id;
    }

    /**
     * @param module_table_id the module_table_id to set
     */
    public void setModule_table_id(String module_table_id) {
        this.module_table_id = module_table_id;
    }

}
