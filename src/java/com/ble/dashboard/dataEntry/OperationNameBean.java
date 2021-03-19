/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dashboard.dataEntry;

/**
 *
 * @author saini
 */
public class OperationNameBean {
    String operation_name,remark,parent_operation,is_super_child;
    int operation_name_id;
    private String parent_id;
    private String parent_name;
    private String child_type;
    private String parent_type;
    private String generation;

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public int getOperation_name_id() {
        return operation_name_id;
    }

    public void setOperation_name_id(int operation_name_id) {
        this.operation_name_id = operation_name_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParent_operation() {
        return parent_operation;
    }

    public void setParent_operation(String parent_operation) {
        this.parent_operation = parent_operation;
    }

    public String getIs_super_child() {
        return is_super_child;
    }

    public void setIs_super_child(String is_super_child) {
        this.is_super_child = is_super_child;
    }

    /**
     * @return the parent_id
     */
    public String getParent_id() {
        return parent_id;
    }

    /**
     * @param parent_id the parent_id to set
     */
    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * @return the parent_name
     */
    public String getParent_name() {
        return parent_name;
    }

    /**
     * @param parent_name the parent_name to set
     */
    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    /**
     * @return the child_type
     */
    public String getChild_type() {
        return child_type;
    }

    /**
     * @param child_type the child_type to set
     */
    public void setChild_type(String child_type) {
        this.child_type = child_type;
    }

    /**
     * @return the parent_type
     */
    public String getParent_type() {
        return parent_type;
    }

    /**
     * @param parent_type the parent_type to set
     */
    public void setParent_type(String parent_type) {
        this.parent_type = parent_type;
    }

    /**
     * @return the generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * @param generation the generation to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    



}
