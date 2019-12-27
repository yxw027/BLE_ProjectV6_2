/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.response.bean;

/**
 *
 * @author DELL
 */
public class Response {
    
    int response_id,command_id,fixed_response,variable_response,bitwise_response;
    String command,response,data_extract_type,format, remark;

    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public int getFixed_response() {
        return fixed_response;
    }

    public void setFixed_response(int fixed_response) {
        this.fixed_response = fixed_response;
    }

    public int getVariable_response() {
        return variable_response;
    }

    public void setVariable_response(int variable_response) {
        this.variable_response = variable_response;
    }

    public int getBitwise_response() {
        return bitwise_response;
    }

    public void setBitwise_response(int bitwise_response) {
        this.bitwise_response = bitwise_response;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getData_extract_type() {
        return data_extract_type;
    }

    public void setData_extract_type(String data_extract_type) {
        this.data_extract_type = data_extract_type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
