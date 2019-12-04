/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.bean;

/**
 *
 * @author DELL
 */
public class SelectionValueBean {
    

    
    int selection_value_id,selection_value_no,selection_id;
    String display_value,byte_value,parameter,remark;

    public int getSelection_value_id() {
        return selection_value_id;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public String getByte_value() {
        return byte_value;
    }

    public void setSelection_value_id(int selection_value_id) {
        this.selection_value_id = selection_value_id;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public void setByte_value(String byte_value) {
        this.byte_value = byte_value;
    }

    public int getSelection_value_no() {
        return selection_value_no;
    }

    public void setSelection_value_no(int selection_value_no) {
        this.selection_value_no = selection_value_no;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


   

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSelection_id() {
        return selection_id;
    }

    public void setSelection_id(int selection_id) {
        this.selection_id = selection_id;
    }
    
    

  
    
    

}

