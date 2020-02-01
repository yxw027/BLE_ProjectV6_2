/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ble.command.bean;

import java.util.List;

/**
 *
 * @author DELL
 */

public class ListBean {
      private String name;
      private List<ListBean> device;
       private List<ListBean> manufacturer;
       private List<ListBean> model;
        private List<ListBean> operation;
        private List<ListBean> command;
         private List<ListBean> list1;
        

    public List<ListBean> getDevice() {
        return device;
    }

    public void setDevice(List<ListBean> device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(List<ListBean> manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<ListBean> getModel() {
        return model;
    }

    public void setModel(List<ListBean> model) {
        this.model = model;
    }

    public List<ListBean> getOperation() {
        return operation;
    }

    public void setOperation(List<ListBean> operation) {
        this.operation = operation;
    }

    public List<ListBean> getCommand() {
        return command;
    }

    public void setCommand(List<ListBean> command) {
        this.command = command;
    }

    public List<ListBean> getList1() {
        return list1;
    }

    public void setList1(List<ListBean> list1) {
        this.list1 = list1;
    }
      
    
}
