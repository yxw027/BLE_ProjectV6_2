/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.dashboard.dataEntry;

/**
 *
 * @author saini
 */
public class CommandBean {

    int command_id;
    String device_name, command, order_no, delay, operation_name, starting_del, end_del, remark, command_type;
    String manufacturer, device_type, model_type, device_no, format, short_name;
    int input_no, selection_no, bitwise;

    private String display_val;
    private String byte_val;
    private String selection_val_id;
    private String sub_parameter_name;
    private String start_pos;
    private String no_of_bit;
    private String sub_div_no;
    private String sub_byte_div_id;
    private String sub_div_sel_id;

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

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    /**
     * @return the display_val
     */
    public String getDisplay_val() {
        return display_val;
    }

    /**
     * @param display_val the display_val to set
     */
    public void setDisplay_val(String display_val) {
        this.display_val = display_val;
    }

    /**
     * @return the byte_val
     */
    public String getByte_val() {
        return byte_val;
    }

    /**
     * @param byte_val the byte_val to set
     */
    public void setByte_val(String byte_val) {
        this.byte_val = byte_val;
    }

    /**
     * @return the sub_parameter_name
     */
    public String getSub_parameter_name() {
        return sub_parameter_name;
    }

    /**
     * @param sub_parameter_name the sub_parameter_name to set
     */
    public void setSub_parameter_name(String sub_parameter_name) {
        this.sub_parameter_name = sub_parameter_name;
    }

    /**
     * @return the start_pos
     */
    public String getStart_pos() {
        return start_pos;
    }

    /**
     * @param start_pos the start_pos to set
     */
    public void setStart_pos(String start_pos) {
        this.start_pos = start_pos;
    }

    /**
     * @return the no_of_bit
     */
    public String getNo_of_bit() {
        return no_of_bit;
    }

    /**
     * @param no_of_bit the no_of_bit to set
     */
    public void setNo_of_bit(String no_of_bit) {
        this.no_of_bit = no_of_bit;
    }

    /**
     * @return the sub_div_no
     */
    public String getSub_div_no() {
        return sub_div_no;
    }

    /**
     * @param sub_div_no the sub_div_no to set
     */
    public void setSub_div_no(String sub_div_no) {
        this.sub_div_no = sub_div_no;
    }

    /**
     * @return the sub_byte_div_id
     */
    public String getSub_byte_div_id() {
        return sub_byte_div_id;
    }

    /**
     * @param sub_byte_div_id the sub_byte_div_id to set
     */
    public void setSub_byte_div_id(String sub_byte_div_id) {
        this.sub_byte_div_id = sub_byte_div_id;
    }

    /**
     * @return the sub_div_sel_id
     */
    public String getSub_div_sel_id() {
        return sub_div_sel_id;
    }

    /**
     * @param sub_div_sel_id the sub_div_sel_id to set
     */
    public void setSub_div_sel_id(String sub_div_sel_id) {
        this.sub_div_sel_id = sub_div_sel_id;
    }

    /**
     * @return the selection_val_id
     */
    public String getSelection_val_id() {
        return selection_val_id;
    }

    /**
     * @param selection_val_id the selection_val_id to set
     */
    public void setSelection_val_id(String selection_val_id) {
        this.selection_val_id = selection_val_id;
    }

}
