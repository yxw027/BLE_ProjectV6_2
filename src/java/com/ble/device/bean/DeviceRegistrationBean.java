/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.device.bean;

import java.util.List;

/**
 *
 * @author Shobha
 */
public class DeviceRegistrationBean {
    int device_registration_id;
    //int device_registration_id;
    int operation_id;
    int command_id;
    String manufacture_name,device_type_name,device_name,device_no,registration_no,manufacture_date,sale_date,remark;
    List<DeviceRegistrationBean> deviceregBean;
    List<DeviceRegistrationBean> commandListBean;
    String operation_name;
    String command;

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

    public List<DeviceRegistrationBean> getDeviceregBean() {
        return deviceregBean;
    }

    public void setDeviceregBean(List<DeviceRegistrationBean> deviceregBean) {
        this.deviceregBean = deviceregBean;
    }

    public List<DeviceRegistrationBean> getCommandListBean() {
        return commandListBean;
    }

    public void setCommandListBean(List<DeviceRegistrationBean> commandListBean) {
        this.commandListBean = commandListBean;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    

}
