<%-- 
    Document   : dash
    Created on : Dec 26, 2020, 10:00:22 AM
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
        <title>BLE Dashboard</title>
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
        <!-- <script src='https://kit.fontawesome.com/a076d05399.js'></script> -->
        <script
        src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <!-- <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script> -->

        <!-- <script
                src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> -->
        <script src="assets/js/jquery-1.10.2.js"></script>

        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <!--        upload image-->
        <!--        <script src="JS/jquery.smartuploader.min.js"></script>-->
        <link href="css/common.css" rel="stylesheet" />
        <script src="JS/jquery.smartuploader.js"></script>

        <script src="JS/dashJS.js"></script>

        <script type="text/javascript">
            // add row
            $(document).on('click', '#addCategory', function () {
                //debugger;
                var html = '';
                html += '<div id="inputFormCategory">';
                html += '<div class="input-group mb-3">';
                html += '<input type="text" name="category" id="category" class="form-control m-input" placeholder="Enter Category" autocomplete="off" required>';
                //html += '<input type="text" name="title[]" class="form-control m-input" placeholder="Enter title" autocomplete="off">';
                html += '<div class="input-group-append">';
                html += '<button id="removeCategory" type="button" class="btn btn-danger"><b>-</b></button>';
                html += '</div>';
                html += '</div>';

                $('#newCategory').append(html);
            });

            // remove row
            $(document).on('click', '#removeCategory', function () {
                $(this).closest('#inputFormCategory').remove();
            });

            $(document).on('click', '#addBrand', function () {
                //debugger;
                var html = '';
                html += '<div id="inputFormBrand">';
                html += '<div class="input-group mb-3">';
                html += '<input type="text" name="brand" id="brand" class="form-control m-input" placeholder="Enter Brand" autocomplete="off" required>';
                //html += '<input type="text" name="title[]" class="form-control m-input" placeholder="Enter title" autocomplete="off">';
                html += '<div class="input-group-append">';
                html += '<button id="removeBrand" type="button" class="btn btn-danger"><b>-</b></button>';
                html += '</div>';
                html += '</div>';

                $('#newBrand').append(html);
            });

            // remove row
            $(document).on('click', '#removeBrand', function () {
                $(this).closest('#inputFormBrand').remove();
            });

            $(document).on('click', '#addAttribute', function () {
                //debugger;
                var html = '';
                html += '<div id="inputFormAttribute">';
                html += '<div class="input-group mb-3">';
                html += '<input type="text" name="attribute" id="attribute" class="form-control m-input" placeholder="Enter Attribute" autocomplete="off">';
                html += '<input type="text" name="attribute_value" id="attribute_value" class="form-control m-input" placeholder="Enter Value" autocomplete="off">';
                html += '<div class="input-group-append">';
                html += '<button id="removeAttribute" type="button" class="btn btn-danger"><b>-</b></button>';
                html += '</div>';
                html += '</div>';

                $('#newAttribute').append(html);
            });

            // remove row
            $(document).on('click', '#removeAttribute', function () {
                $(this).closest('#inputFormAttribute').remove();
            });

            $(function () {
                $("#file_input").withDropZone("#drop_zone", {
                    action: {
                        name: "image",
                        params: {
                            preview: true,
                        }
                    },
                });
            })

        </script>

        <style>
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
                margin-right: 0px;
                margin-top: 10px;
            }

            .container{
                height: 50px;
                margin-left: 20px;
            }

            .log-sign{
                margin-right: -460px;
            }
            .nav-btn{
                margin-top: -5px;
            }

            .required:after {
                content:" *";
                color: red;
            }
            /*            .required{
                            font-size: 20px;
                            padding-right: 20%;
                        }*/

            .form-control{
                font-size: 15px; 
            }  

            .col_label{
                padding-left: 17%;
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
                        //var table = $('#dataTables-example').DataTable();

                        $('#dataTables-example tbody').on(
                                'click',
                                'tr',
                                function () {
                                    if ($(this).hasClass('selected_row')) {
                                        $(this).removeClass('selected_row');
                                    } else {
                                        $("#dataTables-example").DataTable().$(
                                                'tr.selected_row').removeClass(
                                                'selected_row');
                                        $(this).addClass('selected_row');
                                    }
                                });
                    });


            $(function () {

//                $("#elementid").empty();
//                $('#modules_type_details').show();
//
//                var size = 6;
//                var ar_image = [];
//                var ar_name = [];
//
//                for (var l = 0; l < size; l++) {
//                    ar_image.push("images/ble_modules.jpg");
//                    $("#elementid2").append('<div class="col-md-3"><input type="checkbox" style="width:20px;height:20px;" value="modules' + (l + 1) + '" name="modules"><img src="' + ar_image[l] + '" style="margin-top: 30px;margin-left:10px;" width="75" height="75"><h4 style="margin-left:30px"><b>' + "Module Name" + (l + 1) + '</b></h4></div>');
//                }

                $.ajax({
                    url: "AddDeviceController",
                    contentType: "application/json",
                    dataType: 'json',
                    data: {
                        action1: "getModulesList",
                        q: "getModulesList"
                    },
                    success: function (result) {
                        var size = result.data.length;
                        var ar_image = [];
                        var ar_name = [];
                        for (var l = 0; l < size; l++) {
                            //ar_image.push("images/ble_model.jpg");
                            //ar_image.push(result.data[l].module_image_path);
                            $("#elementid2").append('<div class="col-md-3"><input type="checkbox" style="width:20px;height:20px;" value="' + result.data[l].module_table_id + '" name="modules"><img src="" name="' + result.data[l].module_id + '" id="' + result.data[l].module_id + '" style="margin-top: 30px;margin-left:10px;" width="75" height="75"><h4 style="margin-left:30px"><b>' + result.data[l].module_name + '</b></h4></div>');

                            $("#" + result.data[l].module_id)
                                    .attr(
                                            "src",
                                            "http://localhost:8084/BLE_ProjectV6_2/AddDeviceController?getImage="
                                            + result.data[l].module_image_path + "");
                        }
                        
                    }
                });
            })



            $(document).on('click', '#generateDeviceId', function () {

                var now = new Date();
                var randomNum = '';
                randomNum += Math.round(Math.random() * 9);
                randomNum += Math.round(Math.random() * 9);
                randomNum += now.getTime();
                $("#device_id").val("dev_" + randomNum.substr(0, 5));
            });
        </script>


    </head>
    <body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
        <!-- <div id="wrapper"> -->

        <%@include file="/layout/menu2.jsp"%>
        <div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
            <center>
                <div class="row" style="margin-top: -20px;">
                    <div class="col-sm-12">
                        <h2 style="color: white;padding-top: 15px;font-size: 30px;">
                            <b>Add Device</b>
                        </h2>
                    </div>
                </div>
            </center>	
            
            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%;font-size:14px" id="table_for_device_type">
                <div class="col-md-12">
                    <!--                                 Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Device Table</div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_DeviceType" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Device Name</th>
                                            <th>Device Id</th>                                            
                                            <th>Levels</th>                                            
                                            <th>Active</th>                                            
                                            <th>Date & Time</th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>	                                        
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--                                End Advanced Tables -->
                </div>				
            </div>


            <!--            <div class="row" style="margin-top: 10px;background-color: white;width:80%;margin-left:10%;" id="icons_pc_view">-->
            <div class="row" style="margin-top: 10px;padding-left:12%;padding-right:11%" id="icons_pc_view">


                <!--                <center>-->
                <!--                <form method="post" action="" style="width: 85%;margin-top:20px;">-->
                <form method="post" action="AddDeviceController" name="form1" enctype="multipart/form-data" style="width: 250%;background-color: white;">
                    <div class="row" style="margin-top:35px">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Device Name:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="device_name" id="device_name" class="form-control" placeholder="Enter Device Name " autocomplete="off" required>                                                                        
                        </div>
                    </div>               

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Devcie Id:</label>
                        </div>
                        <div class="col-lg-5">
                            <!--                            <input type="text" name="module_id" id="module_id" class="form-control m-input" placeholder="Enter Module Id" autocomplete="off" required>                                                                        -->
                            <div class="input-group">
                                <input type="text" name="device_id" id="device_id" class="form-control m-input" placeholder="Enter Device Id" autocomplete="off" required>                                   
                                <div class="input-group-append">                                                        
                                    <button id="generateDeviceId" type="button" class="btn btn-info"><b>Generate Id</b></button>
                                </div>
                            </div>


                        </div>
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Levels</label>
                        </div>                            
                        <div class="col-lg-5">                                
                            <input type="text" name="levels" id="levels" class="form-control m-input" placeholder="Enter Level" autocomplete="off">                                                                        
                        </div>                            
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Upload Image:</label>
                        </div>
                        <div class="col-lg-5">
                            <div id="drop_zone" class="drop-zone">
                                <p class="title">Drop file here</p>
                                <div class="preview-container"></div>
                            </div>
                            <input id="file_input" accept="image/*" type="file" multiple="" name="file">
                        </div>
                    </div>


                    <div class="row" style="margin-top: 10px;padding-left:17%;" id="modules_type_details">
                        <div id="elementid2" style="width:100%">
                        </div>
                    </div>

                    <div class="row" style="padding-left:17%;" id="add_new_device">
                        <a href="AddModulesController" target="_blank" style="margin:20px 70px 20px 0px;" class="btn btn-info">Add New Module</a>
                    </div>


                    <div class="row" style="float: right">
                        <center>
                            <input type="submit"  name="submit" value="Submit" style="margin:20px 70px 0px 0px;" class="btn btn-success" />
                        </center>
                    </div>


                        <div class="row" style="margin-top:5%">
                            <div class="col-lg-5 col_label">
                                <b><h2 class="" style="font-size:22px;">Mapping:</h2></b>
                            </div>

                        </div>

                        <center>
                            <div class="row" style="margin-bottom:35px">

                                <div class="col-lg-5 col_label">
                                    <input type="checkbox" name="motherboard">
                                    <label class="" style="font-size:15px;">  MotherBoard/Master</label>
                                </div>

                                <div class="col-lg-7">
                                    <input type="checkbox" name="daughterboard">
                                    <label class="" style="font-size:15px;">  DaughterBoard</label>
                                </div>

                            </div>
                        </center>
                </form>
                <!--                </center>-->

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
            $('#dataTables_ModelTypeE').dataTable({
                //"autoWidth": false,
                //"lengthChange": false,
                "pageLength": 5,
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

