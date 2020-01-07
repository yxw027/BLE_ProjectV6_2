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
public class ByteDataBean {
    int byte_data_id,sub_byte_division;
    String command_name,parameter_name,remark;

    public int getByte_data_id() {
        return byte_data_id;
    }

    public int getSub_byte_division() {
        return sub_byte_division;
    }

    public void setSub_byte_division(int sub_byte_division) {
        this.sub_byte_division = sub_byte_division;
    }

   

    public String getCommand_name() {
        return command_name;
    }


    public void setByte_data_id(int byte_data_id) {
        this.byte_data_id = byte_data_id;
    }

   

    public void setCommand_name(String command_name) {
        this.command_name = command_name;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }
    
    
}
