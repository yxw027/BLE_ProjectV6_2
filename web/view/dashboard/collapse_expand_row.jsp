<%-- 
    Document   : parameter
    Created on : Mar 3, 2021, 9:58:45 AM
    Author     : saini
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta content="" name="description" />
        <meta content="webthemez" name="author" />
        <title>Device Configuration</title>



        <link rel="stylesheet"
              href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>



        <!-- Bootstrap Styles-->
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FontAwesome Styles-->
        <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- Custom Styles-->
        <link href="assets/css/custom-styles.css" rel="stylesheet" />
        <!-- Google Fonts-->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans'
              rel='stylesheet' type='text/css' />
        <link
            href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
            rel="stylesheet">
        <link rel="stylesheet" href="assets/js/Lightweight-Chart/cssCharts.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">        
        <script
        src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>

        <!--        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

        <!--        <script src="assets/js/jquery-1.10.2.js"></script>-->
        <link href="css/common.css" rel="stylesheet" />
        <!--        <script src="JS/jquery.smartuploader.js"></script>-->

        <script src="JS/deviceConfig.js"></script>



        <script type="text/javascript">
            function zoom() {
                document.body.style.zoom = "90%"
            }

        </script>

        <style>
            p, div, input {
                font: 16px;
            }

            .ui-autocomplete {
                cursor: pointer;
                height: 120px;
                width: 150px;
                overflow-y: scroll;
            }


            .all_data {
                height: 100px;
                width: 200px;
            }

            .list {
                background: #138b85;
            }

            #dataTables_ModelType_filter{
                display:none;
            }

            #dataTables_ModelType_length{
                display:none;
            }            

            .circle {
                width: 80px;
                height: 80px;
                border-radius: 50%;
                font-size: 30px;
                color: white;
                line-height: 80px;
                text-align: center;
                background: #1a3aea
            }

            .textColor {
                color: blue;
                font-weight: bold;
                padding: 0px;
            }

            .selected_row {
                font-weight: bolder;
                color: blue;
                border: 3px solid black;
            }

            .table tbody tr td {
                padding: 4px;
            }
            .row {                
                margin-top: 10px;
            }

            .col_label{
                padding-left: 17%;
            }

            .required:after {
                content:" *";
                color: red;
            }


            table { 
                width: 100%; 
                border-collapse: collapse; 
                margin:10px auto;
            }

            th { 
                background: #60b4dfad; 
                color: white; 
                font-weight: bold; 
            }

            td, th { 
                padding: 5px; 
                border: 1px solid #ccc; 
                text-align: left; 
                font-size: 18px;
            }

            .labels tr td {
                background-color: #2cc16a;
                font-weight: bold;
                color: #fff;
            }

            .label tr td label {
                display: block;
            }
            
            .hide1 {
                display: none;
            }

        </style>

        <script>
            function filterTablee(row_class) {
                if (row_class == 'Low') {
                    $('.slight_row').hide();
                    $('.normal_row').hide();
                    $('.high_row').hide();
                }
            }

            function getImgeIcon(image_path) {
                $("#my_image")
                        .attr(
                                "src",
                                "http://120.138.10.146:8080/ContactLess_Management/VisitTempDetailsController?getImage="
                                + image_path + "");
            }

            $(document).ready(
                    function () {
                        $('#dataTables_Model_Type tbody').on(
                                'click',
                                'tr',
                                function () {
                                    if ($(this).hasClass('selected_row')) {
                                        $(this).removeClass('selected_row');
                                    } else {
                                        $("#dataTables_Model_Type").DataTable().$(
                                                'tr.selected_row').removeClass(
                                                'selected_row');
                                        $(this).addClass('selected_row');
                                    }
                                });
                    });


            $(function () {
                $("#manufacturer_name").autocomplete({
                    source: function (request, response) {
                        //alert(3321);
                        var random = document.getElementById("manufacturer_name").value;
                        $.ajax({
                            url: "DeviceController",
                            dataType: "json",
                            data: {
                                action1: "getManufacturerName",
                                str: random
                            },
                            success: function (data) {
                                //alert("data list -" + data.list);
                                console.log(data);
                                response(data.list);
                            },
                            error: function (error) {
                                console.log(error.responseText);
                                response(error.responseText);
                            }
                        });
                    },
                    select: function (events, ui) {
                        console.log(ui);
                        $('#manufacturer_name').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
            });

            function fillColumn(dev_id, count) {
//                alert("dev idd --"+count);             
//                alert("manf name --"+$("#" + count + '2').html());             
                //var device_name = $("#" + id + '3').html();
                $('#manufacturer_name').val($("#" + count + '2').html());
                $('#device_type').val($("#" + count + '3').html());
                $('#model_name').val($("#" + count + '4').html());
                $('#model_no').val($("#" + count + '5').html());
                $('#remark').val($("#" + count + '6').html());
                $('#edit').attr('disabled', false);
            }

            function makeEditable(id) {

                document.getElementById("command_type").disabled = false;
                document.getElementById("short_hand").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("save").disabled = false;
                if (id === 'new') {

                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("command_type").focus();
                    $("#message").html("");

                    document.getElementById("save").disabled = false;

                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);


                }
                if (id === 'edit') {

                    document.getElementById("save_As").disabled = false;
                    document.getElementById("cancel").disabled = false;
                }
            }


            function setStatus(id) {
                if (id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else
                    document.getElementById("clickedButton").value = "Delete";
            }


            function checkmethod(name, no) {
                //alert(name+" and "+no);
                //   document.getElementById("td"+no).style.display = "block";   
                if (document.getElementById("isCheck").checked) {
                    document.getElementById("td" + no).style.display = "block";
                } else {
                    //document.getElementById("td"+no).style.display = "none";
                    document.getElementById("td" + no).style.display = "block";
                }

            }

            function checkmethod1(name, no) {//alert("row no ---"+no);
                if (document.getElementById("isCheckOperation").checked) {
                    document.getElementById("tdC" + no).style.display = "block";
                } else {
                    document.getElementById("tdC" + no).style.display = "none";
                    //document.getElementById("td" + no).style.display = "block";
                }

            }


            $(document).ready(function () {
                $('[data-toggle="toggle"]').change(function () {//alert("this iddd -"+this.id);
                    //$(this).parents().next('.hide1').toggle();
                    var id=this.id;
                    $('.hide_'+id).toggle();
                });

                $('[data-toggle="toggle"]').change(function () {
                    $(this).parents().next('.hide2').toggle();
                });

            });

        </script>


    </head>
    <body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
        <!-- <div id="wrapper"> -->

        <%@include file="/layout/menu2.jsp"%>
        <div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
            <center>
                <div style="margin-top: -10px;">
                    <div class="col-sm-12">
                        <h2 style="color: white;font-size: 30px;margin-bottom: 13px;">
                            <b>Device Configuration</b>
                        </h2>
                    </div>
                </div>
            </center>							


            <div class="row" style="margin-top: 2.55%;margin-left: 10%;margin-right: 10%;font-size:14px ;padding:0px 15px 0px 15px" id="">
                <form method="post" action="DeviceConfigController" name="form2" style="background-color: white;">

                    <c:if test="${not empty message}">
                        <div style="font-size: 14px;width:30%;margin-left:2%;margin-top:2%;background-color: ${msgBgColor}"><b>Result: ${message}</b></div>
                    </c:if>

                    <div class="row">
                        <div class="col-lg-5 col_label" style="margin-top:35px">
                            <label class="required" style="font-size:20px;">Manufacturer Name:</label>
                        </div>
                        <div class="col-lg-5" style="margin-top:35px">                            
                            <input type="text" name="manufacturer_name" id="manufacturer_name" value="${manufacturer}" class="form-control myAutocompleteClass" placeholder="Enter Manufacturer Name" required>
                        </div>                        
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Device Type:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="device_type" id="device_type" value="${device_type}" class="form-control myAutocompleteClass" placeholder="Enter Device Type" required>
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Model Name:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="model_name" id="model_name" value="${deviceName}" class="form-control myAutocompleteClass" placeholder="Enter Model Name" required>
                        </div>                        
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Model No:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="model_no" id="model_no" value="${device_no}" class="form-control myAutocompleteClass" placeholder="Enter Model No" autocomplete="off">
                        </div>
                    </div>


                    <div class="row" style="margin-bottom:35px">
                        <!--                        <input type="submit"  name="task" id="delete" value="Delete" onclick="showNewDevice();" style="margin:35px 35px 35px 0px;float:right" class="btn btn-danger" disabled/>
                                                <input type="button"  name="task" id="edit" value="Edit" onclick="makeEditable(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-info" disabled/>
                                                <input type="submit"  name="task" id="save" value="Save" onclick="setStatus(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-success" disabled/>-->
                        <input type="submit" name="task" id="connection" value="Connection" style="margin:35px 65px 35px 0px;float:right" class="btn btn-success" />
                    </div>

                </form>                

            </div>


            <div class="row" style="margin-top: 2.55%;margin-left: 10%;margin-right: 10%;font-size:14px ;padding:0px 15px 0px 15px;display:" id="connection_div">
                <form name="form1" method="POST" action="DeviceConfigController" style="background-color: white;padding:3%">
                    <table>                        
                        <thead>
                            <tr>
                                <th style="text-align:center;background-color: #60b4df;">Model Name</th>                                
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="divisionTypeBean" items="${requestScope['divisionTypeList']}"  varStatus="loopCounter">
                            <tbody class="labels">

                                <tr>
                                    <td colspan="5">
                                        <!--                                        <label for="accounting">Accounting</label>-->
                                        <label id="isCheck" name="isCheck" value ="${divisionTypeBean.device_registration_id},${divisionTypeBean.device_type_id}">${divisionTypeBean.device_name}</label>
                                        <input type="checkbox" name="isCheck" id="model_${loopCounter.index}" value ="${divisionTypeBean.device_registration_id},${divisionTypeBean.device_type_id}" data-toggle="toggle">
                                    </td>
                                </tr>

                            <div>
                            <tbody>
                            <thead class="hide_model_${loopCounter.index}" style="display:none;">
                                <tr>
                                    <th style="text-align:center;">Operation Name</th>                                
                                </tr>
                            </thead>
                            <c:forEach var="typebean" items="${divisionTypeBean.deviceregBean}"  varStatus="loopCounter1">
                                <tr class="hide_model_${loopCounter.index}" style="background-color:#d2d21e8f;display:none;">
                                    <td>
                                        <label id="isCheckOperation" name="isCheckOperation" value ="${typebean.operation_id},${divisionTypeBean.device_registration_id}"  onclick="checkmethod1('${divisionTypeBean.device_name}', '${loopCounter1.index}')">${typebean.operation_name}</label>
                                        <input type="checkbox" name="isCheckOperation" value ="${typebean.operation_id},${divisionTypeBean.device_registration_id}" id="operation_${loopCounter1.index}" data-toggle="toggle">
                                    </td>
                                </tr> 


                                <tbody>
                                <thead class="hide_operation_${loopCounter1.index}" style="display:none;">
                                    <tr>
                                        <th style="text-align:center;background-color: #68d7f0c7;">Command Name</th>                                
                                    </tr>
                                </thead>
                                <c:forEach var="typebean1" items="${typebean.commandListBean}"  varStatus="loopCounter2">
                                    <tr class="hide_operation_${loopCounter1.index}" style="background-color:#fff;display:none;">
                                        <td>
                                            <label id="isCheckCommand" name="isCheckCommand" value ="${typebean1.command},${typebean.operation_id},${typebean1.order_no},${typebean1.delay}">${typebean1.command}</label>
                                            <input type="checkbox" id="isCheckCommand" name="isCheckCommand" value ="${typebean1.command},${typebean.operation_id},${typebean1.order_no},${typebean1.delay}" data-toggle="toggle">
                                        </td>
                                    </tr> 
                                </c:forEach>
                                </tbody>



                            </c:forEach>                            


                            </tbody>
                            </div>

                        </c:forEach>
                        </tbody>
                        </tbody>
                    </table>
                    
                    <div class="row" style="margin-bottom:0px">                        
                        <input type="hidden" value="${device_id}" name="d_id" style="margin:35px 10px 35px 0px;float:right" class="btn btn-info"/>
                        <input type="hidden" value="${reg_no1}" name="reg_no1" style="margin:35px 10px 35px 0px;float:right" class="btn btn-success"/>
                        <input type="submit" name="task" id="send" value="Send" onclick="setStatus(id)" style="margin-right:15px;float:right" class="btn btn-success" />
                    </div>
                    
                    
                </form>
            </div>
            <footer>
                <p style="color: black">
                    All right reserved. Template by: <a style="color: white"
                                                        href="http://apogeeprecision.com" target="_blank">Apogee
                        Precision LLP</a>
                </p>


            </footer>

        </div>
        <script src="assets/js/custom-scripts.js"></script>
    </body>
</html>


