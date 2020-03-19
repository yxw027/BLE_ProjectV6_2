/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.bean;

/**
 *
 * @author Administrator
 */
public class NtripRoverBean {
      String rover_name,rover_password,remark;
    int ntrip_rover_id;

    public String getRover_name() {
        return rover_name;
    }

    public void setRover_name(String rover_name) {
        this.rover_name = rover_name;
    }

    public String getRover_password() {
        return rover_password;
    }

    public void setRover_password(String rover_password) {
        this.rover_password = rover_password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNtrip_rover_id() {
        return ntrip_rover_id;
    }

    public void setNtrip_rover_id(int ntrip_rover_id) {
        this.ntrip_rover_id = ntrip_rover_id;
    }
    
}
