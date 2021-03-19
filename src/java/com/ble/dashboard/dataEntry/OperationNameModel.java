/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saini
 */
public class OperationNameModel {

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

    public String getParentOperationId(String parent_operation_name) {
        String query1 = "select id as operation_id "
                + " from operation_name mt "
                + " where operation_name =? "
                + " and mt.active='Y'";
        System.err.println("queru for parent id -" + query1);
        String operation_parent_name_id = null;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, parent_operation_name);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            operation_parent_name_id = rs.getString(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("operation_parent_name_id in Table for search is" + operation_parent_name_id);
        return operation_parent_name_id;
    }

//    public int insertRecord(OperationNameBean operationNameBean) {
//        String parent_id;
//        String IsSuperChild = operationNameBean.getIs_super_child();
////       String IsSuperParent=operationNameBean.getIs_super_parent();
//        int rowsAffected = 0;
//        String query = " insert into operation_name(operation_name,parent_id,remark,is_super_child) values(?,?,?,?) ";
//
//        parent_id = getParentOperationId(operationNameBean.getParent_operation());
//        System.err.println("parentt idd -" + parent_id);
//
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//
//            pstmt.setString(1, operationNameBean.getOperation_name());
//            pstmt.setString(2, parent_id);
//            pstmt.setString(3, operationNameBean.getRemark());
//            pstmt.setString(4, IsSuperChild);
//            System.err.println("query for insert -" + pstmt);
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//
//    }
    
    
    public int insertRecord(OperationNameBean bean) throws SQLException {        
        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;
        is_child = bean.getIs_super_child();        
        String child_operation = bean.getOperation_name();
        String parent_operation = bean.getParent_operation();
        int parent_id = getParentId(parent_operation);
        int generation = 0;
        if (parent_operation.equals("")) {
            generation = 1;
        } else {
            generation = getParentGeneration(parent_operation) + 1;
        }
        if (child_operation.equals(parent_operation)) {
            message = "Sorry! Parent-Child cannot be same!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        // to check if parent exist or not
        String qry2 = "select count(*) from operation_name where "
                + " operation_name='" + child_operation + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            //System.out.println("query 1 -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        //
        String query1 = "select count(*) "
                + " from operation_name where "
                + " parent_id='" + parent_id + "' and "
                + " operation_name='" + child_operation + "' and active='Y' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            //System.out.println("query 2 -" + pst);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        String query = "insert into operation_name(operation_name, "
                + " parent_id,is_super_child,remark,created_by,generation) "
                + " values (?,?,?,?,?,?) ";

        //int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, child_operation);
            if (parent_id == 0) {
                pstmt.setString(2, null);
            } else {
                pstmt.setInt(2, parent_id);
            }
            pstmt.setString(3, is_child);
            pstmt.setString(4, bean.getRemark());
            pstmt.setString(5, "Test");
            pstmt.setInt(6, generation);

            //System.out.println("insert query 3 -" + pstmt);

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
        }
        if (rowsAffected > 0) {
            connection.commit();
            message = "Record Saved Successfully!";
            msgBgColor = COLOR_OK;
        } else {
            connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    
    
    public int getParentId(String parent_operation) {
        String query = "select id from operation_name where active='Y' and operation_name='" + parent_operation + "' ";
        int parent_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                parent_id = rset.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Error: OperationNameModel[getParentId]--" + e);
        }
        return parent_id;
    }
    
    public int getParentGeneration(String parent_operation) {
        String query = "SELECT generation FROM operation_name WHERE operation_name='" + parent_operation + "' and active='Y' ";
        int generation = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                generation = rset.getInt("generation");
            }
        } catch (Exception e) {
            System.out.println("Error: OperationNameModel--" + e);
        }
        return generation;
    }

    public boolean reviseRecords(OperationNameBean operationNameBean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        String query1 = " SELECT max(revision_no) revision_no FROM operation_name c WHERE c.id = " + operationNameBean.getOperation_name_id() + " && active='Y' ORDER BY revision_no DESC";
        String query2 = " UPDATE operation_name SET active=? WHERE id = ? && revision_no = ? ";
        String query3 = " INSERT INTO operation_name (id,operation_name,parent_id,remark,is_super_child,revision_no,active) VALUES (?,?,?,?,?,?,?) ";
        String parent_id = getParentOperationId(operationNameBean.getParent_operation());
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, operationNameBean.getOperation_name_id());
                pst.setInt(3, rs.getInt("revision_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, operationNameBean.getOperation_name_id());
                    psmt.setString(2, operationNameBean.getOperation_name());
                    psmt.setString(3, parent_id);
                    psmt.setString(4, operationNameBean.getRemark());
                    psmt.setString(5, operationNameBean.getIs_super_child());
                    psmt.setInt(6, rev);
                    psmt.setString(7, "Y");

                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
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

    public int getNoOfRows(String searchOperationname) {
        String query1 = "select count(*) "
                + " from operation_name mt "
                + " where IF('" + searchOperationname + "' = '', operation_name LIKE '%%',operation_name =?) "
                + " and mt.active='Y'";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);

            stmt.setString(1, searchOperationname);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<OperationNameBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchOperationname) {
        List<OperationNameBean> list = new ArrayList<OperationNameBean>();
//        String query2 = "select m.id,m.operation_name,m.remark,m.is_super_child,op.operation_name as parent_operation  "
//                + " from operation_name m , operation_name op "                
//                + " where m.parent_id = op.id and m.active='Y' and op.active = 'Y' ";

        String query2 = " select id,operation_name as child,remark,is_super_child,IFNULL(parent_id,'')as parent_id,active, "
                + " generation from operation_name where active='Y' order by id ";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OperationNameBean operationNameBean = new OperationNameBean();

                operationNameBean.setOperation_name_id(rset.getInt(1));
                operationNameBean.setOperation_name(rset.getString(2));                
                operationNameBean.setRemark(rset.getString(3));
                operationNameBean.setIs_super_child(rset.getString(4));
                operationNameBean.setParent_id(rset.getString(5));
                operationNameBean.setGeneration(rset.getString(7));
                
                String parent_type = getParentTypeName(operationNameBean.getParent_id());
                operationNameBean.setParent_name(parent_type);
                
                list.add(operationNameBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    public String getParentTypeName(String parentId) {
        String query = "select operation_name as parent_type from operation_name where id=? and active='Y' ";
        String parent_type = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, parentId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                parent_type = rset.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error: OperationNameModel[getParentTypeName]--" + e);
        }
        return parent_type;
    }

    public int deleteRecord(int model_type_id) {

        String query = "update operation_name set active='N' where id=" + model_type_id;
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

    public List<String> getOperationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select operation_name from operation_name where active='Y' and is_super_child='N' "
                + " order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("operation_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Operation Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside OperationNameModel - " + e);
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
