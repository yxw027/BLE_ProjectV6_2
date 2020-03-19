/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.device.bean;

/**
 *
 * @author Administrator
 */
public class DeviceConfigStatusBean {
  private int deviceConfigStatus_id,finished_device_id,model_id,operation_id,order_no;
  private String command,reg_no,remark,finished_device_name,model_name,operation_name,model_no;

    public int getDeviceConfigStatus_id() {
        return deviceConfigStatus_id;
    }

    public void setDeviceConfigStatus_id(int deviceConfigStatus_id) {
        this.deviceConfigStatus_id = deviceConfigStatus_id;
    }

    public int getFinished_device_id() {
        return finished_device_id;
    }

    public void setFinished_device_id(int finished_device_id) {
        this.finished_device_id = finished_device_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(int operation_id) {
        this.operation_id = operation_id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getFinished_device_name() {
        return finished_device_name;
    }

    public void setFinished_device_name(String finished_device_name) {
        this.finished_device_name = finished_device_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public String getModel_no() {
        return model_no;
    }

    public void setModel_no(String model_no) {
        this.model_no = model_no;
    }

  
    
    
}
