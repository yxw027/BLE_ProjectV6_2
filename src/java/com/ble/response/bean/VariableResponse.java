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
public class VariableResponse {
    
    int variable_response_id,response_id,parameter_id,start_pos,no_of_byte;
    String parameter,response;

    public int getVariable_response_id() {
        return variable_response_id;
    }

    public void setVariable_response_id(int variable_response_id) {
        this.variable_response_id = variable_response_id;
    }

    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public int getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(int parameter_id) {
        this.parameter_id = parameter_id;
    }

    public int getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int start_pos) {
        this.start_pos = start_pos;
    }

    public int getNo_of_byte() {
        return no_of_byte;
    }

    public void setNo_of_byte(int no_of_byte) {
        this.no_of_byte = no_of_byte;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    
}
