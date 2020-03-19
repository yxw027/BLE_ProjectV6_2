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
public class NtripBaseBean {
     String base_name,base_password,remark;
    int ntrip_base_id;

    public String getBase_name() {
        return base_name;
    }

    public void setBase_name(String base_name) {
        this.base_name = base_name;
    }

    public String getBase_password() {
        return base_password;
    }

    public void setBase_password(String base_password) {
        this.base_password = base_password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNtrip_base_id() {
        return ntrip_base_id;
    }

    public void setNtrip_base_id(int ntrip_base_id) {
        this.ntrip_base_id = ntrip_base_id;
    }

    public void setConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
