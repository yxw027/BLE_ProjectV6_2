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
public class ParameterNameBean {
    private String id;
    private String parameter_name;
    private String parameter_type;
    private String active;
    private String created_date;    
    private String revision_no;    
    private String remark;    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the parameter_name
     */
    public String getParameter_name() {
        return parameter_name;
    }

    /**
     * @param parameter_name the parameter_name to set
     */
    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    /**
     * @return the parameter_type
     */
    public String getParameter_type() {
        return parameter_type;
    }

    /**
     * @param parameter_type the parameter_type to set
     */
    public void setParameter_type(String parameter_type) {
        this.parameter_type = parameter_type;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return the created_date
     */
    public String getCreated_date() {
        return created_date;
    }

    /**
     * @param created_date the created_date to set
     */
    public void setCreated_date(String created_date) {
        this.created_date = created_date;
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
    
}
