/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dashboard.regConfig;

import java.util.List;

/**
 *
 * @author saini
 */
public class DeviceConfigBean {
    int device_registration_id;
    int operation_id;
    int command_id;
    int device_type_id;
    String manufacture_name,device_type_name,device_name,device_no,registration_no,manufacture_date,sale_date,remark,imei_no,password,field1,field2,field3,field4;
    String command;
    String operation_name;
    String order_no;
    String delay;
    List<DeviceConfigBean> deviceregBean;
    List<DeviceConfigBean> commandListBean;

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

    public List<DeviceConfigBean> getDeviceregBean() {
        return deviceregBean;
    }

    public void setDeviceregBean(List<DeviceConfigBean> deviceregBean) {
        this.deviceregBean = deviceregBean;
    }

    public List<DeviceConfigBean> getCommandListBean() {
        return commandListBean;
    }

    public void setCommandListBean(List<DeviceConfigBean> commandListBean) {
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

    

}
