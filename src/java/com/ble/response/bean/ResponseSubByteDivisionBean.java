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
public class ResponseSubByteDivisionBean {
      int response_sub_byte_division_id,sub_division_no,start_pos,no_of_bit;
    String parameter_name,remark,parameter_name_byte;

    public int getResponse_sub_byte_division_id() {
        return response_sub_byte_division_id;
    }

    public void setResponse_sub_byte_division_id(int response_sub_byte_division_id) {
        this.response_sub_byte_division_id = response_sub_byte_division_id;
    }

    public int getSub_division_no() {
        return sub_division_no;
    }

    public void setSub_division_no(int sub_division_no) {
        this.sub_division_no = sub_division_no;
    }

    public int getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int start_pos) {
        this.start_pos = start_pos;
    }

    public int getNo_of_bit() {
        return no_of_bit;
    }

    public void setNo_of_bit(int no_of_bit) {
        this.no_of_bit = no_of_bit;
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

    public String getParameter_name_byte() {
        return parameter_name_byte;
    }

    public void setParameter_name_byte(String parameter_name_byte) {
        this.parameter_name_byte = parameter_name_byte;
    }
    
    
}
