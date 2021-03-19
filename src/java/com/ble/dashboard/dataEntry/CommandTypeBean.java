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
public class CommandTypeBean {
    
    private String id;
    private String command_type;
    private String short_hand;
    private String remark;
    private String created_date;
    private String revision_no;
    private String active;

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
     * @return the command_type
     */
    public String getCommand_type() {
        return command_type;
    }

    /**
     * @param command_type the command_type to set
     */
    public void setCommand_type(String command_type) {
        this.command_type = command_type;
    }

    /**
     * @return the short_hand
     */
    public String getShort_hand() {
        return short_hand;
    }

    /**
     * @param short_hand the short_hand to set
     */
    public void setShort_hand(String short_hand) {
        this.short_hand = short_hand;
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
    
    
}
