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
public class ResponseCrcMappingBean {
      String crc_type,remark,active,response;
    int crc_type_id,response_id,response_crc_map_id;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCrc_type_id() {
        return crc_type_id;
    }

    public void setCrc_type_id(int crc_type_id) {
        this.crc_type_id = crc_type_id;
    }

    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public int getResponse_crc_map_id() {
        return response_crc_map_id;
    }

    public void setResponse_crc_map_id(int response_crc_map_id) {
        this.response_crc_map_id = response_crc_map_id;
    }
    
    
}
