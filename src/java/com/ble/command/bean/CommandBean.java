/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.command.bean;

/**
 *
 * @author Shobha
 */
public class CommandBean {

    int command_id;
    String device_name,command,order_no,delay,operation_name,starting_del,end_del,remark,command_type;
    String manufacturer,device_type,model_type,device_no,format;
            int input_no,selection_no,bitwise;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    

    public String getCommand_type() {
        return command_type;
    }

    public void setCommand_type(String command_type) {
        this.command_type = command_type;
    }

    

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getEnd_del() {
        return end_del;
    }

    public void setEnd_del(String end_del) {
        this.end_del = end_del;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStarting_del() {
        return starting_del;
    }

    public void setStarting_del(String starting_del) {
        this.starting_del = starting_del;
    }

    public int getInput_no() {
        return input_no;
    }

    public void setInput_no(int input_no) {
        this.input_no = input_no;
    }

    public int getSelection_no() {
        return selection_no;
    }

    public void setSelection_no(int selection_no) {
        this.selection_no = selection_no;
    }

    public int getBitwise() {
        return bitwise;
    }

    public void setBitwise(int bitwise) {
        this.bitwise = bitwise;
    }

    




}
