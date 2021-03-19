<%-- 
    Document   : dash
    Created on : Jan 21, 2021, 01:46:09 PM
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
        <title>Device Mapping</title>



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
        <script src="JS/jquery.smartuploader.js"></script>

        <!--                <script src="JS/dashJS.js"></script>-->

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
                //alert("image [athhh -" + image_path);
                //$("#my_image").attr("src", "images/person_logo.jpg");
                //http://localhost:8081/ContactLess_Management/VisitTempDetailsController?getImage='NewImage111'
                $("#my_image")
                        .attr(
                                "src",
                                "http://120.138.10.146:8080/ContactLess_Management/VisitTempDetailsController?getImage="
                                + image_path + "");

            }

            $(document).ready(
                    function () {
                        $('#dataTables_DeviceType tbody').on(
                                'click',
                                'tr',
                                function () {
                                    if ($(this).hasClass('selected_row')) {
                                        $(this).removeClass('selected_row');
                                    } else {
                                        $("#dataTables_DeviceType").DataTable().$(
                                                'tr.selected_row').removeClass(
                                                'selected_row');
                                        $(this).addClass('selected_row');
                                    }
                                });
                    });


            $(function () {
                $("#finished_manufacturer").autocomplete({
                    source: function (request, response) {//alert(1312);
                        var random = document.getElementById("finished_manufacturer").value;
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getFinishedManufacturer",
                                str: random
                            },
                            success: function (data) {
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
                        $('#finished_manufacturer').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
                
                $("#module_manufacturer").autocomplete({
                    source: function (request, response) {
                        var random = document.getElementById("module_manufacturer").value;
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getModuleManufacturer",
                                str: random
                            },
                            success: function (data) {
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
                        $('#module_manufacturer').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
                
                $("#module_device_type").autocomplete({
                    source: function (request, response) {
                        var random = document.getElementById("module_device_type").value;
                        var random2 = document.getElementById("module_manufacturer").value;
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getModuleDeviceType",
                                str: random,
                                str2: random2                                
                            },
                            success: function (data) {
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
                        $('#module_device_type').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
                
                $("#finished_device_type").autocomplete({
                    source: function (request, response) {
                        var random = document.getElementById("finished_device_type").value;
                        var random2 = document.getElementById("finished_manufacturer").value;
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getFinishedDeviceType",
                                str: random,
                                str2: random2
                            },
                            success: function (data) {
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
                        $('#finished_device_type').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
                
                $("#finished_model").autocomplete({
                    source: function (request, response) {                        
                        var random = document.getElementById("finished_model").value;                        
                        var random2 = document.getElementById("finished_device_type").value;                        
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getFinishedModelName",
                                str: random,                                
                                str2: random2                                
                            },
                            success: function (data) {                                
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
                        $('#finished_model').val(ui.item.label); // display the selected text
                        return false;
                    }
                });

                $("#module_model").autocomplete({
                    source: function (request, response) {                        
                        var random = document.getElementById("module_model").value;                        
                        var random2 = document.getElementById("module_device_type").value;                                                
                        $.ajax({
                            url: "DeviceMapController",
                            dataType: "json",
                            data: {
                                action1: "getModuleModelName",
                                str: random,
                                str2: random2
                            },
                            success: function (data) {                                
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
                        $('#module_model').val(ui.item.label); // display the selected text
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
                document.getElementById("device_type").disabled = false;
                document.getElementById("parent_device_type").disabled = false;
                document.getElementById("is_super_child").disabled = false;
                document.getElementById("remark").disabled = false;

                document.getElementById("save").disabled = false;
                if (id === 'new') {

                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("device_type").focus();
                    $("#message").html("");
                    document.getElementById("save").disabled = false;
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
                            <b>Device Mapping</b>
                        </h2>
                    </div>
                </div>
            </center>								


            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%;font-size:14px" id="table_for_module_type">
                <div class="col-md-12">
                    <!--                                 Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Device Map Table</div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_DeviceMap" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th colspan="3" style="text-align:center;background-color:rgb(56, 165, 238, 0.5);">Finished Device</th>
                                            <th colspan="3" style="text-align:center;background-color:rgb(56, 165, 238, 0.5);">Module Device</th>                                                                                   
                                        </tr>
                                        <tr style="text-align:center;background-color:rgb(56, 165, 238, 0.5);">
                                            <th>S.No</th>
                                            <th>Manufacturer Name</th>                                                                                   
                                            <th>Device Type</th>                                                                                   
                                            <th>Model</th>                                                                                   
                                            <th>Manufacturer Name</th>                                                                                   
                                            <th>Device Type</th>                                                                                   
                                            <th>Model</th>
                                            <th>Remark</th>                                                                                        
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="model" items="${requestScope['list2']}"
                                                   varStatus="loopCounter">
                                            <tr
                                                onclick="fillColumn('${model.device_map_id}','${loopCounter.count }');">
                                                <td>${loopCounter.count }</td>
                                                <td id="${loopCounter.count }2">${model.finished_manufacture_name}</td>
                                                <td id="${loopCounter.count }3">${model.finished_device_type}</td>
                                                <td id="${loopCounter.count }4">${model.finished_model_name}</td>                                                                                               
                                                <td id="${loopCounter.count }5">${model.module_manufacture_name}</td>                                                                                               
                                                <td id="${loopCounter.count }6">${model.module_device_type}</td>                                                                                               
                                                <td id="${loopCounter.count }7">${model.module_model_name}</td>                                                                                               
                                                <td id="${loopCounter.count }8">${model.remark}</td>                                                                                               
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--                                End Advanced Tables -->
                </div>				
            </div>	




            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%;font-size:14px ;padding:0px 15px 0px 15px" id="">
                <form method="post" action="DeviceMapController" name="form1" style="background-color: white;">                    

                    <c:if test="${not empty message}">
                        <div style="font-size: 14px;width:30%;margin-left:2%;margin-top:2%;background-color: ${msgBgColor}"><b>Result: ${message}</b></div>
                    </c:if>


                    <center>
                        <div style="margin-top: -10px;">
                            <div class="col-sm-12">
                                <h2 style="color: red;font-size: 30px;margin-bottom: 13px;">
                                    <b>Finished Device</b>
                                </h2>
                            </div>
                        </div>
                    </center>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <input type="button"  name="finished_device" id="finished_select_device" value="Select Finished Device" onclick="" />
                        </div>
                        <div class="col-lg-5">                            
                            <input type="button"  name="finished_create_device" id="finished_create_device" value="Create Finished Device" onclick="" />
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Manufacturer:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="finished_manufacturer" id="finished_manufacturer" class="form-control m-input" placeholder="Enter Manufacturer">
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Device Type:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="finished_device_type" id="finished_device_type" class="form-control m-input" placeholder="Enter Device Type" required>
                        </div>                        
                    </div>                    

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Model:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="finished_model" id="finished_model" class="form-control m-input" placeholder="Enter Model" autocomplete="off">
                        </div>
                    </div>
                        
                        <center>
                        <div style="margin-top: 10px;">
                            <div class="col-sm-12">
                                <h2 style="color: red;font-size: 30px;margin-bottom: 13px;">
                                    <b>Module Device</b>
                                </h2>
                            </div>
                        </div>
                    </center>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <input type="button"  name="module_device" id="module_select_device" value="Select Module Device" onclick="" />
                        </div>
                        <div class="col-lg-5">                            
                            <input type="button"  name="module_create_device" id="module_create_device" value="Create Module Device" onclick="" />
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Manufacturer:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="module_manufacturer" id="module_manufacturer" class="form-control m-input" placeholder="Enter Manufacturer">
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Device Type:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" name="module_device_type" id="module_device_type" class="form-control m-input" placeholder="Enter Device Type" required>
                        </div>                        
                    </div>                    

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Model:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="module_model" id="module_model" class="form-control m-input" placeholder="Enter Model" autocomplete="off">
                        </div>
                    </div>
                        

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Remark:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="module_remark" id="module_remark" class="form-control m-input" placeholder="Enter Remark" autocomplete="off">
                        </div>
                    </div>


                    <div class="row" style="margin-bottom:35px">
                        <input type="submit"  name="task" id="delete" value="Delete" onclick="showNewDevice();" style="margin:35px 35px 35px 0px;float:right" class="btn btn-danger" disabled/>
                        <input type="button"  name="task" id="edit" value="Edit" onclick="makeEditable(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-info" disabled/>
                        <input type="submit"  name="task" id="save" value="Save" onclick="setStatus(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-success"/>
                        <input type="button"  name="task" id="new" value="New" onclick="makeEditable(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-warning" />
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


        <!-- Bootstrap Js -->
        <!-- <script src="assets/js/bootstrap.min.js"></script> -->
        <!-- Metis Menu Js -->
        <script src="assets/js/jquery.metisMenu.js"></script>
        <!-- DATA TABLE SCRIPTS -->
        <script src="assets/js/dataTables/jquery.dataTables.js"></script>
        <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>

        <script src="assets/js/easypiechart.js"></script>
        <script src="assets/js/easypiechart-data.js"></script>

        <script>
                            $(document).ready(function () {
                                /* $('#dataTables-example').dataTable();
                                 "pageLength": 25 */
                                $('#dataTables_DeviceMap').dataTable({
                                    //"autoWidth": false,
                                    //"lengthChange": false,
                                    "pageLength": 10,

                                });
                                $('#dataTables_mob_view').dataTable({
                                    //"autoWidth": false,
                                    //"lengthChange": false,
                                    "pageLength": 1
                                });
                            });
        </script>
        <!-- Custom Js -->
        <script src="assets/js/custom-scripts.js"></script>
    </body>
</html>

