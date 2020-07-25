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
public class ResponseDivisionSelectionBean {
     int response_sub_division_selection_id,response_sub_byte_division_id;
    String display_value,bit_value;

    public int getResponse_sub_division_selection_id() {
        return response_sub_division_selection_id;
    }

    public void setResponse_sub_division_selection_id(int response_sub_division_selection_id) {
        this.response_sub_division_selection_id = response_sub_division_selection_id;
    }

    public int getResponse_sub_byte_division_id() {
        return response_sub_byte_division_id;
    }

    public void setResponse_sub_byte_division_id(int response_sub_byte_division_id) {
        this.response_sub_byte_division_id = response_sub_byte_division_id;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public String getBit_value() {
        return bit_value;
    }

    public void setBit_value(String bit_value) {
        this.bit_value = bit_value;
    }
    
}
