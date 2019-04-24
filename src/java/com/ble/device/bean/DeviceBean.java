/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.device.bean;

/**
 *
 * @author Shobha
 */
public class DeviceBean {
    int device_id, device_no;
    String manufacture_name,device_type_name,device_name,remark;

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getDevice_no() {
        return device_no;
    }

    public void setDevice_no(int device_no) {
        this.device_no = device_no;
    }

    public String getDevice_type_name() {
        return device_type_name;
    }

    public void setDevice_type_name(String device_type_name) {
        this.device_type_name = device_type_name;
    }

    public String getManufacture_name() {
        return manufacture_name;
    }

    public void setManufacture_name(String manufacture_name) {
        this.manufacture_name = manufacture_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    

}
