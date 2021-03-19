/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author saini
 */
public class NewDeviceModel {

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
        String query1 = "select id from manufacturer m "
                + " where m.name=? "
                + " and m.active='Y' ";
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
        String query1 = "select id from device_type d "
                + " where d.type=? "
                + " and d.active='Y' ";
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

    public int getModelId(String device_name, String device_no) {
        String query1 = "select id from model m "
                + " where m.device_name=? and m.device_no=? "
                + " and m.active='Y' ";
        int model_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setString(1, device_name);
            stmt.setString(2, device_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            model_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return model_id;
    }

    public int getDeviceId(int manufacture_id, int device_type_id, int model_id) {
        String query1 = "select d.id from device d "
                + " where d.manufacture_id=? "
                + " and d.device_type_id=? "
                + " and d.model_id=? "
                + " and d.active='Y' ";
        int device_id = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            stmt.setInt(1, manufacture_id);
            stmt.setInt(2, device_type_id);
            stmt.setInt(3, model_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            device_id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        return device_id;
    }

    public int getNoOfRows(String searchManufacturerName, String searchDeviceTypeName) {
        String query1 = "select distinct mr.name as manufacturer_name, dt.type as device_type, mt.type as model_type,"
                + " m.device_name,m.device_no,m.warranty_period,m.device_address,m.no_of_module "
                + " from device d, device_type dt, device_map dm, manufacturer mr, model m, modal_type mt "
                + " where d.active='Y' and dt.active='Y' and dm.active='Y' and mr.active='Y' and m.active='Y' and mt.active='Y' "
                + " and d.manufacture_id=mr.id and d.device_type_id=dt.id and d.model_id=m.id and m.model_type_id=mt.id ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noOfRows++;
            }
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CommandModel" + e);
        }
        //System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<DashboardBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchManufacturerName, String searchDeviceTypeName) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String sub_module_device_id = "";

        String query2 = " select distinct d.id,mr.name as manufacturer_name, dt.type as device_type, mt.type as model_type,"
                + " m.device_name,m.device_no,m.warranty_period,m.device_address,m.no_of_module "
                + " from device d, device_type dt, device_map dm, manufacturer mr, model m, modal_type mt "
                + " where d.active='Y' and dt.active='Y' and dm.active='Y' and mr.active='Y' and m.active='Y' and mt.active='Y' "
                + " and d.manufacture_id=mr.id and d.device_type_id=dt.id and d.model_id=m.id and m.model_type_id=mt.id "
                + addQuery;
        try {
            //System.out.println("query  -" + query2);
            int count = 0;
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                count++;
                DashboardBean deviceBean = new DashboardBean();

                deviceBean.setDevice_id(rset.getString("id"));
                deviceBean.setManufacture_name(rset.getString("manufacturer_name"));
                deviceBean.setDevice_type_name(rset.getString("device_type"));
                deviceBean.setModel_type(rset.getString("model_type"));
                deviceBean.setDevice_name(rset.getString("device_name"));
                deviceBean.setDevice_no(rset.getString("device_no"));
                deviceBean.setWarranty_period(rset.getString("warranty_period"));
                deviceBean.setDevice_address(rset.getString("device_address"));
                deviceBean.setNo_of_module(rset.getString("no_of_module"));
                deviceBean.setSerial_no(count);

                if (!deviceBean.getDevice_id().equals(null)) {
                    int c_out = 0;
                    String query3 = " select module_device_id from device_map where "
                            + " finished_device_id='" + deviceBean.getDevice_id() + "' ";
                    PreparedStatement pstt = connection.prepareStatement(query3);
                    ResultSet rstt = pstt.executeQuery();
                    while (rstt.next()) {
                        sub_module_device_id = rstt.getString(1);
                        c_out++;
                    }
                    if (c_out > 0) {
                        deviceBean.setFinished_product("Yes");
                    } else {
                        deviceBean.setFinished_product("No");
                    }
                }
                list.add(deviceBean);
                //list.add(deviceBean2);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    // get SubModule list	
    public List<DashboardBean> getSubModule(String device_id) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        String query = "";
        int count = 0;
        String module_device_id = "";
        try {
            query = " select module_device_id from device_map where  "
                    + "finished_device_id='" + device_id + "' order by module_device_id  ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                module_device_id = rst.getString(1);
                count++;

                String qry = " select distinct  mr.name as manufacturer_name, dt.type as device_type, mt.type as model_type, "
                        + " m.device_name,m.device_no,m.warranty_period,m.device_address,m.no_of_module,d.id "
                        + " from device d, device_type dt, device_map dm, manufacturer mr, model m, modal_type mt "
                        + " where d.active='Y' and dt.active='Y' and dm.active='Y' and mr.active='Y' and m.active='Y' and mt.active='Y' "
                        + " and d.manufacture_id=mr.id and d.device_type_id=dt.id and d.model_id=m.id and m.model_type_id=mt.id "
                        + " and d.id='" + module_device_id + "' ";
                PreparedStatement pst = connection.prepareStatement(qry);
                ResultSet rset = pst.executeQuery();
                while (rset.next()) {
                    DashboardBean deviceBean = new DashboardBean();

                    deviceBean.setDevice_id(rset.getString("id"));
                    deviceBean.setManufacture_name(rset.getString("manufacturer_name"));
                    deviceBean.setDevice_type_name(rset.getString("device_type"));
                    deviceBean.setModel_type(rset.getString("model_type"));
                    deviceBean.setDevice_name(rset.getString("device_name"));
                    deviceBean.setDevice_no(rset.getString("device_no"));
                    deviceBean.setWarranty_period(rset.getString("warranty_period"));
                    deviceBean.setDevice_address(rset.getString("device_address"));
                    deviceBean.setNo_of_module(rset.getString("no_of_module"));

                    list.add(deviceBean);

                    System.out.println("cccount --" + list.size());

                }
            }
        } catch (Exception e) {
            System.out.println("getSubModule ERROR inside DashboardModel - " + e);
        }
        System.out.println("final size --" + list.size());
        return list;
    }

    public List<DashboardBean> getCommandDetail(String device_id) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        try {
            String qry = " select distinct dcm.command_id,dcm.device_id, "
                    + " ct.name as command_type_name, COALESCE(ct.shorthand, 'null')as shorthand, "
                    + " c.command,c.starting_del,c.end_del,c.format,c.selection,c.input,c.bitwise "
                    + " from device d, device_type dt, device_map dm, manufacturer mr, model m, modal_type mt, "
                    + " command_type ct, command c,"
                    + " device_command_map dcm, operation_name op "
                    + " where d.active='Y' and dt.active='Y' and dm.active='Y' and mr.active='Y' and m.active='Y' and mt.active='Y' "
                    + " and d.manufacture_id=mr.id and d.device_type_id=dt.id and d.model_id=m.id and m.model_type_id=mt.id "
                    + " and dcm.device_id='" + device_id + "' "
                    + " and dcm.device_id=d.id and dcm.command_id=c.id and c.command_type_id=ct.id "
                    + " and dcm.operation_id=op.id "
                    + " and c.active='Y' and ct.active='Y' and dcm.active='Y' and op.active='Y' ";

            System.out.println("command query --" + qry);

            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean deviceBean = new DashboardBean();
                deviceBean.setCommand_type_name(rset.getString("command_type_name"));
                deviceBean.setShorthand(rset.getString("shorthand"));
                deviceBean.setCommand(rset.getString("command"));
                deviceBean.setStarting_del(rset.getString("starting_del"));
                deviceBean.setEnd_del(rset.getString("end_del"));
                deviceBean.setFormat(rset.getString("format"));
                deviceBean.setSelection(rset.getString("selection"));
                deviceBean.setInput(rset.getString("input"));
                deviceBean.setBitwise(rset.getString("bitwise"));
                deviceBean.setCommand_id(rset.getInt("command_id"));
                deviceBean.setDevice_id(rset.getString("device_id"));

                list.add(deviceBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getSubModule ERROR inside DashboardModel - " + e);
        }
        //System.out.println("final size --"+list.size());
        return list;
    }

    public List<DashboardBean> getOperationName(String device_id, String command_id) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select op.operation_name "
                    + " from operation_name op, device_command_map dcm where dcm.device_id='" + device_id + "' "
                    + " and dcm.command_id='" + command_id + "' and dcm.active='Y' and dcm.operation_id=op.id and op.active='Y' ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean deviceBean = new DashboardBean();
                deviceBean.setOperation_name(rset.getString(1));
                list.add(deviceBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getOperationName ERROR inside DashboardModel - " + e);
        }
        return list;
    }

    // Dashboard Start
    public List<DashboardBean> getModelType(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " Select * from modal_type where active='Y' ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setModel_id(rset.getString(1));
                dashBean.setModel_type(rset.getString(2));
                dashBean.setActive(rset.getString(3));
                dashBean.setRevision(rset.getString(4));
                dashBean.setCreated_date(rset.getString(6));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getModelType ERROR inside DashboardModel - " + e);
        }
        return list;
    }

    public List<DashboardBean> getDeviceName(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select * from model m, modal_type mt where m.active='Y' and mt.active='Y' and mt.id=m.model_type_id ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setDevice_id(rset.getString(1));
                dashBean.setDevice_name(rset.getString(2));
                dashBean.setDevice_no(rset.getString(3));
                dashBean.setWarranty_period(rset.getString(4));
                dashBean.setDevice_address(rset.getString(11));
                dashBean.setNo_of_module(rset.getString(12));
                dashBean.setModel_type(rset.getString(14));
                dashBean.setCreated_date(rset.getString(8));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getDeviceName ERROR inside DashboardModel - " + e);
        }
        return list;
    }

    public List<DashboardBean> getFinishedProduct(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select distinct dm.finished_device_id, m.device_name,m.device_no,m.warranty_period, "
                    + " m.device_address,mt.type,m.no_of_module from device_map dm, device d, model m, modal_type mt "
                    + " where dm.active='Y' and d.id=dm.finished_device_id and m.id=d.model_id and m.active='Y' and d.active='Y' "
                    + " and mt.active='Y' and m.model_type_id=mt.id order by dm.finished_device_id ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setDevice_id(rset.getString(1));
                dashBean.setDevice_name(rset.getString(2));
                dashBean.setDevice_no(rset.getString(3));
                dashBean.setWarranty_period(rset.getString(4));
                dashBean.setDevice_address(rset.getString(5));
                dashBean.setNo_of_module(rset.getString(7));
                dashBean.setModel_type(rset.getString(6));
                //dashBean.setCreated_date(rset.getString(8));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getFinishedProduct ERROR inside DashboardModel - " + e);
        }
        return list;
    }

    // Device Image data
    public List<DashboardBean> getDeviceImage(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select * from model where active='Y' and model_type_id=3 ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setDevice_name(rset.getString(2));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getDeviceImage ERROR inside DashboardModel - " + e);
        }
        return list;
    }
    // end device image data 

    // Modules Image data
    public List<DashboardBean> getModulesImage(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select * from device_type where active='Y' ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setModules_id(rset.getString(1));
                dashBean.setModules_name(rset.getString(2));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getModulesImage ERROR inside DashboardModel - " + e);
        }
        return list;
    }
    // end Modules image data

    // Sub Modules Image data
    public List<DashboardBean> showSubModulesImage(String q) {
        List<DashboardBean> list = new ArrayList<DashboardBean>();
        //String[] ids = device_id.split("\\s*,\\s*");        
        try {
            String qry = " select m.id,m.device_name, m.device_no,m.device_address,dt.type "
                    + " from device d, device_type dt, model m "
                    + " where dt.id=d.device_type_id and d.model_id=m.id and m.active='Y' "
                    + " and d.active='Y' and dt.active='Y' and dt.id="+q+" ";

            //System.out.println("query operation -"+qry);
            PreparedStatement pst = connection.prepareStatement(qry);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                DashboardBean dashBean = new DashboardBean();
                dashBean.setSub_modules_id(rset.getString(1));
                dashBean.setSub_modules_name(rset.getString(2));
                list.add(dashBean);

                //System.out.println("cccount --"+list.size());
            }
        } catch (Exception e) {
            System.out.println("getModulesImage ERROR inside DashboardModel - " + e);
        }
        return list;
    }
    // end Sub Modules image data

    // Dashboard Enf
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
