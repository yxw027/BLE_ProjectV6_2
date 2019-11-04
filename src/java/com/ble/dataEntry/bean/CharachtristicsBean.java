/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dataEntry.bean;

/**
 *
 * @author Shobha
 */
public class CharachtristicsBean {
    int charachtristics_id,service_id;
    String charachtristics_name,service_name,service_uuid,uuid,remark;

    public int getCharachtristics_id() {
        return charachtristics_id;
    }

    public void setCharachtristics_id(int charachtristics_id) {
        this.charachtristics_id = charachtristics_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getCharachtristics_name() {
        return charachtristics_name;
    }

    public void setCharachtristics_name(String charachtristics_name) {
        this.charachtristics_name = charachtristics_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_uuid() {
        return service_uuid;
    }

    public void setService_uuid(String service_uuid) {
        this.service_uuid = service_uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}
