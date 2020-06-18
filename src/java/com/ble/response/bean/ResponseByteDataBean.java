/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.bean;

/**
 *
 * @author user
 */
public class ResponseByteDataBean {
     int byte_data_response_id,sub_byte_division;
    String response_name,parameter_name,remark;

    public int getByte_data_response_id() {
        return byte_data_response_id;
    }

    public void setByte_data_response_id(int byte_data_response_id) {
        this.byte_data_response_id = byte_data_response_id;
    }

    public int getSub_byte_division() {
        return sub_byte_division;
    }

    public void setSub_byte_division(int sub_byte_division) {
        this.sub_byte_division = sub_byte_division;
    }

    public String getResponse_name() {
        return response_name;
    }

    public void setResponse_name(String response_name) {
        this.response_name = response_name;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
    
    
}
