/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dashboard;

import java.util.List;

/**
 *
 * @author saini
 */
public class DashboardBean {

    
    int device_registration_id;
    int operation_id;
    int command_id;
    int device_type_id;
    String manufacture_name,device_type_name,device_name,device_no,registration_no,manufacture_date,sale_date,remark,imei_no,password,field1,field2,field3,field4;
    String command;
    String operation_name;
    String order_no;
    String delay;
    List<DashboardBean> deviceregBean;
    List<DashboardBean> commandListBean;
    private String model_type;
    private String warranty_period;
    private String device_address;
    private String no_of_module;
    private String device_id;
    private String finished_product;
    private int serial_no;
    private String command_type_name;
    private String shorthand;
    private String starting_del;
    private String end_del;
    private String format;
    private String selection;
    private String input;
    private String bitwise;
    
    private List<DashboardBean> subModuleList;
    
    private String model_id;
    private String active;
    private String revision;
    private String created_date;
    private String modules_name;    
    private String modules_id;
    private String sub_modules_name;
    private String sub_modules_id;
    private String image_id;
    private String image_path;
    
    
    /**
     * @return the command_type_name
     */
    public String getCommand_type_name() {
        return command_type_name;
    }

    /**
     * @param command_type_name the command_type_name to set
     */
    public void setCommand_type_name(String command_type_name) {
        this.command_type_name = command_type_name;
    }

    /**
     * @return the shorthand
     */
    public String getShorthand() {
        return shorthand;
    }

    /**
     * @param shorthand the shorthand to set
     */
    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }

    /**
     * @return the starting_del
     */
    public String getStarting_del() {
        return starting_del;
    }

    /**
     * @param starting_del the starting_del to set
     */
    public void setStarting_del(String starting_del) {
        this.starting_del = starting_del;
    }

    /**
     * @return the end_del
     */
    public String getEnd_del() {
        return end_del;
    }

    /**
     * @param end_del the end_del to set
     */
    public void setEnd_del(String end_del) {
        this.end_del = end_del;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the selection
     */
    public String getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(String selection) {
        this.selection = selection;
    }

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the bitwise
     */
    public String getBitwise() {
        return bitwise;
    }

    /**
     * @param bitwise the bitwise to set
     */
    public void setBitwise(String bitwise) {
        this.bitwise = bitwise;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public int getDevice_registration_id() {
        return device_registration_id;
    }

    public void setDevice_registration_id(int device_registration_id) {
        this.device_registration_id = device_registration_id;
    }

    public String getDevice_type_name() {
        return device_type_name;
    }

    public void setDevice_type_name(String device_type_name) {
        this.device_type_name = device_type_name;
    }

    public String getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(String manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public String getManufacture_name() {
        return manufacture_name;
    }

    public void setManufacture_name(String manufacture_name) {
        this.manufacture_name = manufacture_name;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

    public String getImei_no() {
        return imei_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public int getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(int operation_id) {
        this.operation_id = operation_id;
    }

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public List<DashboardBean> getDeviceregBean() {
        return deviceregBean;
    }

    public void setDeviceregBean(List<DashboardBean> deviceregBean) {
        this.deviceregBean = deviceregBean;
    }

    public List<DashboardBean> getCommandListBean() {
        return commandListBean;
    }

    public void setCommandListBean(List<DashboardBean> commandListBean) {
        this.commandListBean = commandListBean;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public int getDevice_type_id() {
        return device_type_id;
    }

    public void setDevice_type_id(int device_type_id) {
        this.device_type_id = device_type_id;
    }

    /**
     * @return the model_type
     */
    public String getModel_type() {
        return model_type;
    }

    /**
     * @param model_type the model_type to set
     */
    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    /**
     * @return the warranty_period
     */
    public String getWarranty_period() {
        return warranty_period;
    }

    /**
     * @param warranty_period the warranty_period to set
     */
    public void setWarranty_period(String warranty_period) {
        this.warranty_period = warranty_period;
    }

    /**
     * @return the device_address
     */
    public String getDevice_address() {
        return device_address;
    }

    /**
     * @param device_address the device_address to set
     */
    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    /**
     * @return the no_of_module
     */
    public String getNo_of_module() {
        return no_of_module;
    }

    /**
     * @param no_of_module the no_of_module to set
     */
    public void setNo_of_module(String no_of_module) {
        this.no_of_module = no_of_module;
    }

    /**
     * @return the device_id
     */
    public String getDevice_id() {
        return device_id;
    }

    /**
     * @param device_id the device_id to set
     */
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    /**
     * @return the finished_product
     */
    public String getFinished_product() {
        return finished_product;
    }

    /**
     * @param finished_product the finished_product to set
     */
    public void setFinished_product(String finished_product) {
        this.finished_product = finished_product;
    }

    /**
     * @return the serial_no
     */
    public int getSerial_no() {
        return serial_no;
    }

    /**
     * @param serial_no the serial_no to set
     */
    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    /**
     * @return the subModuleList
     */
    public List<DashboardBean> getSubModuleList() {
        return subModuleList;
    }

    /**
     * @param subModuleList the subModuleList to set
     */
    public void setSubModuleList(List<DashboardBean> subModuleList) {
        this.subModuleList = subModuleList;
    }

    /**
     * @return the model_id
     */
    public String getModel_id() {
        return model_id;
    }

    /**
     * @param model_id the model_id to set
     */
    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return the revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * @param revision the revision to set
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return the created_date
     */
    public String getCreated_date() {
        return created_date;
    }

    /**
     * @param created_date the created_date to set
     */
    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    /**
     * @return the modules_name
     */
    public String getModules_name() {
        return modules_name;
    }

    /**
     * @param modules_name the modules_name to set
     */
    public void setModules_name(String modules_name) {
        this.modules_name = modules_name;
    }

    /**
     * @return the modules_id
     */
    public String getModules_id() {
        return modules_id;
    }

    /**
     * @param modules_id the modules_id to set
     */
    public void setModules_id(String modules_id) {
        this.modules_id = modules_id;
    }

    /**
     * @return the sub_modules_name
     */
    public String getSub_modules_name() {
        return sub_modules_name;
    }

    /**
     * @param sub_modules_name the sub_modules_name to set
     */
    public void setSub_modules_name(String sub_modules_name) {
        this.sub_modules_name = sub_modules_name;
    }

    /**
     * @return the sub_modules_id
     */
    public String getSub_modules_id() {
        return sub_modules_id;
    }

    /**
     * @param sub_modules_id the sub_modules_id to set
     */
    public void setSub_modules_id(String sub_modules_id) {
        this.sub_modules_id = sub_modules_id;
    }

    /**
     * @return the image_id
     */
    public String getImage_id() {
        return image_id;
    }

    /**
     * @param image_id the image_id to set
     */
    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    /**
     * @return the image_path
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * @param image_path the image_path to set
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    

}
