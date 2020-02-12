/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.bean;

/**
 *
 * @author Shobha
 */
public class OperationNameBean {
    String operation_name,remark,parent_operation,is_super_child;
    int operation_name_id;

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

    



}
