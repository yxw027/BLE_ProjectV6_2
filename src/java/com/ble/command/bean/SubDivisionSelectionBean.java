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
public class SubDivisionSelectionBean {
    int sub_division_selection_id,sub_byte_division_id;
    String display_value,bit_value;

    public int getSub_division_selection_id() {
        return sub_division_selection_id;
    }

    public int getSub_byte_division_id() {
        return sub_byte_division_id;
    }

    public String getDisplay_value() {
        return display_value;
    }

    public String getBit_value() {
        return bit_value;
    }

    public void setSub_division_selection_id(int sub_division_selection_id) {
        this.sub_division_selection_id = sub_division_selection_id;
    }

    public void setSub_byte_division_id(int sub_byte_division_id) {
        this.sub_byte_division_id = sub_byte_division_id;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public void setBit_value(String bit_value) {
        this.bit_value = bit_value;
    }
    
}
