/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ble.webService.controller;

import com.ble.device.model.DeviceRegConModel;
import static com.ble.device.model.DeviceRegConModel.isModuleOperation;
import static com.ble.device.model.DeviceRegConModel.isOperationArr;
import com.ble.webService.model.BLEWebServicesModel;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Shobha
 */


@Path("/")
public class BLEWebServicesController {
     public static String deviceResponse;
    @Resource
    WebServiceContext wsContext;  
    
    @POST
    @Path("/getAllTableRecords")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/BLE_Project/resources/getAllTableRecords
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject surveyLast5Records(String dataString) {
        JSONObject obj = new JSONObject();
        BLEWebServicesModel bLEWebServicesModel = new BLEWebServicesModel();
        try{
       
        bLEWebServicesModel.setConnection();
        //JSONObject obj = new JSONObject();
        JSONArray json = null;

         json = bLEWebServicesModel.getManufacturerRecords();
         obj.put("manufacturer", json);
         json = bLEWebServicesModel.getDeviceTypeRecords();
         obj.put("device_type", json);
         json = bLEWebServicesModel.getModelTypeRecords();
         obj.put("modal_type", json);
         json = bLEWebServicesModel.getModelRecords();
         obj.put("model", json);
         json = bLEWebServicesModel.getCommandTypeRecords();
         obj.put("command_type", json);
         json = bLEWebServicesModel.getOperationNameRecords();
         obj.put("operation_name", json);
         json = bLEWebServicesModel.getDeviceRecords();
         obj.put("device", json);
         json = bLEWebServicesModel.getDeviceRegistrationRecords();
         obj.put("device_registration", json);
         json = bLEWebServicesModel.getServiceRecords();
         obj.put("servicies", json);
         json = bLEWebServicesModel.getCharachtersticRecords();
         obj.put("charachtristics", json);
         json = bLEWebServicesModel.getCommandRecords();
         obj.put("command", json);
         json = bLEWebServicesModel.getCommandDeviceMapRecords();
         obj.put("command_device_map", json);
         json = bLEWebServicesModel.getRuleRecords();
         obj.put("rule", json);

         json = bLEWebServicesModel.getBleOperationNameRecords();
         obj.put("ble_operation_name", json);
         json = bLEWebServicesModel.getDeviceCharacteristicBleMapRecord();
         obj.put("device_characteristic_ble_map", json);
         json = bLEWebServicesModel.getSelectionRecord();
         obj.put("selection", json);
         json = bLEWebServicesModel.getSelectionValueRecord();
         obj.put("selection_value", json);
         json = bLEWebServicesModel.getInputRecord();
         obj.put("input", json);
         json = bLEWebServicesModel.getParameterRecord();
         obj.put("parameter", json);
         json = bLEWebServicesModel.getByteDataRecord();
         obj.put("byte_data", json);
         json = bLEWebServicesModel.getSubByteDivisionRecord();
         obj.put("sub_byte_division", json);
         json = bLEWebServicesModel.getSubDivisionSelectionRecord();
         obj.put("sub_division_selection", json);
         json = bLEWebServicesModel.getDeviceMapRecords();
         obj.put("device_map", json);
        }catch(Exception e){
            System.out.println("Error in BLEWebServices 'requestData' url calling getWardData()..."+e);
        }
        return obj;
    }    

    @POST
    @Path("/deviceRegRecords")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/BLE_Project/resources/getAllTableRecords
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveDeviceRegRecord(String dataString) {
        String status = "some error";
        String dataString1[] = dataString.split(",");
        String manu_name = dataString1[0];
        String device_type = dataString1[1];
        String model_name = dataString1[2];
        String model_no = dataString1[3];
         BLEWebServicesModel bLEWebServicesModel = new BLEWebServicesModel();
        try{       
       bLEWebServicesModel.setConnection();
       String devID_regNo_pass =  bLEWebServicesModel.saveDeviceReg(device_type,manu_name,model_name,model_no);
       String devId= devID_regNo_pass.split(",")[0];
       String regNo= devID_regNo_pass.split(",")[1];
       String pass= devID_regNo_pass.split(",")[2];
       status = "$$$$,04,0,6,"+regNo+","+pass+",120.138.10.146,8060,45.114.142.35,8060,12,####";
        
        }catch(Exception e){
            System.out.println("Error in BLEWebServices 'requestData' url calling getWardData()..."+e);
        }
        return status;
    }
    
    @POST
    @Path("/checkDeviceResponse")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/BLE_Project/resources/getAllTableRecords
    @Consumes(MediaType.APPLICATION_JSON)
    public void checkDeviceResponse(String dataString) {
        String status = dataString.split(",")[0];
        if(status.equalsIgnoreCase("$$$$")){
            System.out.println("success");
        }       
    }
    
    
     @POST
    @Path("/sendRequest")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/BLE_Project/resources/getAllTableRecords
    @Consumes(MediaType.APPLICATION_JSON)
    public String  sendConfigurationReq(String dataString) {
        String status = "not ready";
        deviceResponse = dataString;
        System.out.println("mayank req data...   "+dataString);
        DeviceRegConModel drc = new DeviceRegConModel();
        String arr = drc.isModuleOperation;
        if(arr != null){
        status = arr;
        }
//        for (int i = 0; i < arr.length; i++) {
//            status = arr[i]; 
//            System.out.println("in status ..... "+status);
//    }
         return status;
    }


}
