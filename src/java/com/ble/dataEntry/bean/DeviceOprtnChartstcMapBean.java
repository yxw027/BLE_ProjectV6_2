/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.dataEntry.bean;

/**
 *
 * @author jpss
 */
public class DeviceOprtnChartstcMapBean {

    String manufacturer_name,device_type,model_name,service_name,read_characteristics_name, write_characteristics_name,ble_operation_name,remark;
    int device_type_id,manufacturer_id,model_id,device_id,servicies_id,read_characteristics_id, write_characteristics_id,ble_operation_name_id;
    int device_characteristic_ble_map_id,order_no;

    public String getBle_operation_name() {
        return ble_operation_name;
    }

    public void setBle_operation_name(String ble_operation_name) {
        this.ble_operation_name = ble_operation_name;
    }

    public int getBle_operation_name_id() {
        return ble_operation_name_id;
    }

    public void setBle_operation_name_id(int ble_operation_name_id) {
        this.ble_operation_name_id = ble_operation_name_id;
    }

  

    public String getRead_characteristics_name() {
        return read_characteristics_name;
    }

    public void setRead_characteristics_name(String read_characteristics_name) {
        this.read_characteristics_name = read_characteristics_name;
    }

    public String getWrite_characteristics_name() {
        return write_characteristics_name;
    }

    public void setWrite_characteristics_name(String write_characteristics_name) {
        this.write_characteristics_name = write_characteristics_name;
    }

    public int getRead_characteristics_id() {
        return read_characteristics_id;
    }

    public void setRead_characteristics_id(int read_characteristics_id) {
        this.read_characteristics_id = read_characteristics_id;
    }

    public int getWrite_characteristics_id() {
        return write_characteristics_id;
    }

    public void setWrite_characteristics_id(int write_characteristics_id) {
        this.write_characteristics_id = write_characteristics_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public int getDevice_type_id() {
        return device_type_id;
    }

    public void setDevice_type_id(int device_type_id) {
        this.device_type_id = device_type_id;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public int getServicies_id() {
        return servicies_id;
    }

    public void setServicies_id(int servicies_id) {
        this.servicies_id = servicies_id;
    }
   
    public int getDevice_characteristic_ble_map_id() {
        return device_characteristic_ble_map_id;
    }

    public void setDevice_characteristic_ble_map_id(int device_characteristic_ble_map_id) {
        this.device_characteristic_ble_map_id = device_characteristic_ble_map_id;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    
}
