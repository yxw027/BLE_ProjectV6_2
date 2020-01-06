/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.model;
import com.ble.response.bean.FixedResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author DELL
 */
public class FixedResponseModel {
    
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
            System.out.println("ResponseModel setConnection() Error: " + e);
        }
    }
     public int getNoOfRows() {

        String query1 = "select count(*) "
                + " from fixed_response s "
                + " where s.active='Y' ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fixedResponseModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public int insertRecord(FixedResponse fixedBean,int response_id) {
        int parameter_id = getParameterId(fixedBean.getParameter());
        String query = " insert into fixed_response(fixed_response_id,response_id,parameter_id,fixed_response_value_no,start_pos,no_of_byte,remark) "
                + " values(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, fixedBean.getFixed_response_id());
            pstmt.setInt(2, response_id);
            pstmt.setInt(3, parameter_id);
            pstmt.setInt(4, fixedBean.getFixed_response_value_no());
            pstmt.setInt(5, fixedBean.getStart_pos());
            pstmt.setInt(6, fixedBean.getNo_of_byte());
            pstmt.setString(7, fixedBean.getRemark());
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

    public boolean reviseRecords(FixedResponse bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int parameter_id = getParameterId(bean.getParameter());

        int response_id = getResponseId(bean.getResponse());
        String query1 = " SELECT max(revision_no) revision_no FROM fixed_response fr WHERE fr.fixed_response_id = " + bean.getFixed_response_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE selection SET active=? WHERE fixed_response_id = ? && revision_no = ? ";
        String query3 = " INSERT INTO fixed_response(fixed_response_id,response_id,parameter_id,remark,revision_no,active,fixed_response_value_no,start_pos,no_of_byte) VALUES (?,?,?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, bean.getFixed_response_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, bean.getFixed_response_id());
                    psmt.setInt(2, response_id);
                    psmt.setInt(3, parameter_id);
                    psmt.setString(4, bean.getRemark());
                    psmt.setInt(5, rev);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, bean.getFixed_response_value_no());
                     psmt.setInt(8, bean.getStart_pos());
                      psmt.setInt(9, bean.getNo_of_byte());
                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("responseModel reviseRecord() Error: " + e);
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

    public boolean updateRecords(FixedResponse bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int parameter_id = getParameterId(bean.getParameter());
        int response_id = getResponseId(bean.getResponse());
        int revision_no = getRevisionNo(bean.getResponse_id());

        String query2 = "UPDATE fixed_response SET active='N' where fixed_response_id=" + bean.getResponse_id() + "";
        int updateRowsAffected = 0;
        try {

            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);

            updateRowsAffected = pst.executeUpdate();
            if(updateRowsAffected > 0) {
                try {
                    pst.close();
                    String queryInsert = "INSERT INTO fixed_response (fixed_response_id,response_id,parameter_id,remark,revision_no,active,fixed_response_value_no,start_pos,no_of_byte) VALUES (?,?,?,?,?,?,?,?,?) ";
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(queryInsert);
                    psmt.setInt(1, bean.getFixed_response_id());
                    psmt.setInt(2, response_id);
                    psmt.setInt(3, parameter_id);
                    psmt.setString(4, bean.getRemark());
                    psmt.setInt(5, revision_no+1);
                    psmt.setString(6, "Y");
                    psmt.setInt(7, bean.getFixed_response_value_no());
                    psmt.setInt(8, bean.getStart_pos());
                    psmt.setInt(9, bean.getNo_of_byte());
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    }
                }catch (Exception e) {
                    System.out.println("FixedResponseModel updateRecord() Error: " + e);
                }
            }

        } catch (SQLException e) {
            System.out.println("FixedResponseModel updateRecord() Error: " + e);
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

    public int getParameterId(String parameter_name) {
        String query = "select parameter_id from parameter where parameter_name = '" + parameter_name + "' and active = 'Y';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("parameter_id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }
    
    public int getRevisionNo(int fixed_response_id) {
        String query = "select revision_no from fixed_response where fixed_response_id = '" + fixed_response_id + "' and active = 'Y';";
        int revison_no = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                revison_no = rset.getInt("revision_no");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside ResponseModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return revison_no;
    }

    public int getResponseId(String response) {
        String query = "select id from response where response = '" + response + "' and active='Y';";
        int type = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                type = rset.getInt("id");
            }

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<String> getResponseName() {
        List<String> list = new ArrayList<String>();
        String query = "select response from response order by id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("response");
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
                list.add("No such Command Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public String getResponseNameByResponse_id(int response_id) {
        String type = "";
        String query = "select response from response where response_id = " + response_id + " and active = 'Y' order by response_id desc;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            rset.next();
            type = rset.getString("response");
//                

        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return type;
    }

    public List<String> getParameter() {
        List<String> list = new ArrayList<String>();
        String query = "select parameter_name from parameter where active='Y' order by parameter_id desc;";
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

    public List<String> getParameterType() {
        List<String> list = new ArrayList<String>();
        String query = "select distinct parameter_type from parameter;";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String type = rset.getString("parameter_type");
                list.add(type);
                count++;
            }
            if (count == 0) {
                list.add("No such Parameter Type exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<FixedResponse> showDataByResponseId(int lowerLimit, int noOfRowsToDisplay, String fixed_response, String response_id) {
        List<FixedResponse> list = new ArrayList<FixedResponse>();

        String responseName = "";
        int i = 1;
        

        String query2 = "select fr.fixed_response_id,r.response,fr.remark,fr.fixed_response_value_no,fr.start_pos,fr.no_of_byte,p.parameter_name"
                + " from fixed_response fr, parameter p, response r "
                + " where fr.response_id=r.response_id and fr.parameter_id = p.parameter_id and fr.active='Y' and r.active='Y' and p.active='Y' and fr.response_id ="+response_id;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if (i <= Integer.parseInt(fixed_response)) {
                    FixedResponse  fixedResponseBean = new  FixedResponse();

                    String response = rset.getString("response");
                 
                    fixedResponseBean.setFixed_response_id(rset.getInt("fixed_response_id"));
                    fixedResponseBean.setResponse(response);
                    fixedResponseBean.setParameter(rset.getString("parameter_name"));
                    fixedResponseBean.setFixed_response_value_no(Integer.parseInt(rset.getString("fixed_response_value_no")));
                    fixedResponseBean.setStart_pos(Integer.parseInt(rset.getString("start_pos")));
                    fixedResponseBean.setNo_of_byte(Integer.parseInt(rset.getString("no_of_byte")));
                         
                    fixedResponseBean.setRemark(rset.getString("remark"));
                    list.add(fixedResponseBean);
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
            this.connection.close();
        } catch (Exception ex) {
            System.out.println("ERROR: on closeConnection() in ClientResponderModel : " + ex);
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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
