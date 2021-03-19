/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

/**
 *
 * @author saini
 */
public class ManufacturerBean {
    private String manufacturer_id;
    private String manufacturer_name;
    private String remark;
    private String is_active;
    private String revision_no;
    private String created_by;
    private String created_at;

    /**
     * @return the manufacturer_id
     */
    public String getManufacturer_id() {
        return manufacturer_id;
    }

    /**
     * @param manufacturer_id the manufacturer_id to set
     */
    public void setManufacturer_id(String manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    /**
     * @return the manufacturer_name
     */
    public String getManufacturer_name() {
        return manufacturer_name;
    }

    /**
     * @param manufacturer_name the manufacturer_name to set
     */
    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the is_active
     */
    public String getIs_active() {
        return is_active;
    }

    /**
     * @param is_active the is_active to set
     */
    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    /**
     * @return the revision_no
     */
    public String getRevision_no() {
        return revision_no;
    }

    /**
     * @param revision_no the revision_no to set
     */
    public void setRevision_no(String revision_no) {
        this.revision_no = revision_no;
    }

    /**
     * @return the created_by
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * @param created_by the created_by to set
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * @return the created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
}
