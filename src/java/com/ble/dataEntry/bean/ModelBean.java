/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.bean;

/**
 *
 * @author Shobha
 */
public class ModelBean {

    String device_name,device_no,warrenty_period,remark,model_type,device_address;
    int model_type_id,model_id,no_of_module;

    public String getDevice_address() {
        return device_address;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

    

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getModel_type_id() {
        return model_type_id;
    }

    public void setModel_type_id(int model_type_id) {
        this.model_type_id = model_type_id;
    }

    public String getWarrenty_period() {
        return warrenty_period;
    }

    public void setWarrenty_period(String warrenty_period) {
        this.warrenty_period = warrenty_period;
    }

    public int getNo_of_module() {
        return no_of_module;
    }

    public void setNo_of_module(int no_of_module) {
        this.no_of_module = no_of_module;
    }

}
