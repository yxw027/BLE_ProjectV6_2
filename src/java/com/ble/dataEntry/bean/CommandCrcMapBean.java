/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.bean;

/**
 *
 * @author DELL
 */
public class CommandCrcMapBean {
     String crc_type,remark,active,command,short_hand;
    int crc_type_id,command_id,command_crc_map_id;

    public String getCrc_type() {
        return crc_type;
    }

    public void setCrc_type(String crc_type) {
        this.crc_type = crc_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getShort_hand() {
        return short_hand;
    }

    public void setShort_hand(String short_hand) {
        this.short_hand = short_hand;
    }

    public int getCrc_type_id() {
        return crc_type_id;
    }

    public void setCrc_type_id(int crc_type_id) {
        this.crc_type_id = crc_type_id;
    }

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public int getCommand_crc_map_id() {
        return command_crc_map_id;
    }

    public void setCommand_crc_map_id(int command_crc_map_id) {
        this.command_crc_map_id = command_crc_map_id;
    }
    
    
}
