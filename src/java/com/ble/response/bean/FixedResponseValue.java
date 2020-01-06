/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.bean;

/**
 *
 * @author DELL
 */
public class FixedResponseValue {
    int fixed_response_value_id,fixed_response_id;
    String display_value,select_value,remark,parameter;

    public int getFixed_response_value_id() {
        return fixed_response_value_id;
    }

    public void setFixed_response_value_id(int fixed_response_value_id) {
        this.fixed_response_value_id = fixed_response_value_id;
    }

    public int getFixed_response_id() {
        return fixed_response_id;
    }

    public void setFixed_response_id(int fixed_response_id) {
        this.fixed_response_id = fixed_response_id;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public String getSelect_value() {
        return select_value;
    }

    public void setSelect_value(String select_value) {
        this.select_value = select_value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    
}
