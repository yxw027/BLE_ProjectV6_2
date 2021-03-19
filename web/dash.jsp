<%-- 
    Document   : dash
    Created on : Dec 5, 2020, 10:57:22 AM
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

        <script src="JS/dashJS.js"></script>

        <script type="text/javascript">
            function zoom() {
                document.body.style.zoom = "90%"

                //			setTimeout(function(){
                //				   window.location.reload(1);
                //				}, 60000)
            }
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

            .textColor2 {
                color: white;
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


        </script>


    </head>
    <body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
        <!-- <div id="wrapper"> -->

        <%@include file="layout/menu2.jsp"%>
        <div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
            <!--            <center>
                            <div class="row" style="margin-top: -20px;">
                                <div class="col-sm-12">
                                    <h2 style="color: white">
                                        <b>BLE Dashboard</b>
                                    </h2>
                                </div>
                            </div>
                        </center>								-->


            <div class="row" style="margin-left: 10%;margin-right: 10%;">                  
                <div class="col-md-4 col-sm-12 col-xs-12"
                     onclick="showRespectiveDiv('Devices');">
                    <div class="panel panel-default"
                         style="background-color: #60b4df; height: 243px;">
                        <div class="panel-body easypiechart-panel" style="margin-top: 10%;">
                            <h2 class="textColor2">Click me to view</h2>                                                        
                        </div>
                        <div data-percent="100">
                            <center>
                                <h1 class="textColor2">Devices</h1>
                            </center>
                        </div>
                    </div>
                </div>


                <div class="col-md-4 col-sm-12 col-xs-12"
                     onclick="showRespectiveDiv('operation_command');">
                    <div class="panel panel-default"
                         style="background-color: #60b4df; height: 243px;">
                        <div class="panel-body easypiechart-panel" style="margin-top: 10%;">
                            <h2 class="textColor2">Click me to view</h2>                                                        
                        </div>
                        <div data-percent="100">
                            <center>
                                <h1 class="textColor2">Operation-Command</h1>
                            </center>
                        </div>
                    </div>
                </div>


                <div class="col-md-4 col-sm-12 col-xs-12"
                     onclick="showRespectiveDiv('Registration');">
                    <div class="panel panel-default"
                         style="background-color: #60b4df; height: 243px;">
                        <div class="panel-body easypiechart-panel" style="margin-top: 10%;">
                            <h2 class="textColor2">Click me to view</h2>                                                        
                        </div>
                        <div data-percent="100">
                            <center>
                                <h1 class="textColor2">Registration & Configuration</h1>
                            </center>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row" id="device_modules_div" style="display:none;margin-left: 10%;margin-right: 10%;">

                <center style="margin-bottom: 3%;">
                    <h2><u>Devices</u></h2>
                </center>

                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="showModulesImage('modules_name');">
                    <div class="panel panel-default"
                         style="background-color: #EAC2E2; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Modules</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle" id="no_of_modules">20</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/ble_modules.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="showFinishedProductImage('finished_product');">
                    <div class="panel panel-default"
                         style="background-color: #49EE72CF; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Finished Product</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle" id="no_of_finished_product">5</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/finished.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <div class="row" id="registration_div" style="display:none;margin-left: 10%;margin-right: 10%;">

                <center style="margin-bottom: 3%;">
                    <h2><u>Registration & Configuration</u></h2>
                </center>

                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="filterTable('Slight');">
                    <div class="panel panel-default"
                         style="background-color: #EAD421; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Registration</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle">15</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/registration.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="goToConfigurationPage();">
                    <div class="panel panel-default"
                         style="background-color: greenyellow; height: 243px;">
                        <div class="panel-body easypiechart-panel" style="margin-top: 10%;">
                            <h2 class="textColor2">Click me to Go on</h2>                                                        
                        </div>
                        <div data-percent="100">
                            <center>
                                <h1 class="textColor2">Configuration</h1>
                            </center>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row" id="operation_command_div" style="display:none;margin-left: 10%;margin-right: 10%;">

                <center style="margin-bottom: 3%;">
                    <h2><u>Operation-Command</u></h2>
                </center>

                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="showOperationName();">
                    <div class="panel panel-default"
                         style="background-color: red; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Operation Name</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle" id="operation_name">15</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/operation.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>                       

                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="showCommandDetail();">
                    <div class="panel panel-default"
                         style="background-color:  #1ABC9C; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Command Detail</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle" id="command_detail">15</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/command.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-12 col-xs-12"
                     onclick="showDevOpCommandList();">
                    <div class="panel panel-default"
                         style="background-color:  violet; height: 243px;">
                        <div class="panel-body easypiechart-panel">
                            <h4 class="textColor">Dev-Op Command Map</h4>
                            <div data-percent="100">
                                <center>
                                    <div class="circle" id="device_op_command">15</div>
                                </center>
                            </div>
                            <div style="margin-top: 10px">
                                <img src="images/device-op-comm.jpg" width=100px; height=100px; />
                            </div>
                        </div>
                    </div>
                </div>                                             


                <!--                <div class="col-md-2 col-sm-12 col-xs-12"
                                     onclick="showParameter();"  style="display:none">
                                    <div class="panel panel-default"
                                         style="background-color:  #76ec15; height: 243px;">
                                        <div class="panel-body easypiechart-panel">
                                            <h4 class="textColor">Parameter</h4>
                                            <div data-percent="100">
                                                <center>
                                                    <div class="circle" id="parameter">15</div>
                                                </center>
                                            </div>
                                            <div style="margin-top: 10px">
                                                <img src="images/parameter.jpg" width=100px; height=100px; />
                                            </div>
                                        </div>
                                    </div>
                                </div>-->

            </div>        


            <div class="row" style="margin-top: 10px;margin-left:5%;display:none" id="finished_type_details">
                <center>
                    <h2><u>Finished Product</u></h2>
                </center>
                <div id="elementid1">
                </div>
            </div>

            <div class="row" style="margin-top: 10px;margin-left:5%;display:none" id="modules_type_details">
                <center>
                    <h2><u>Modules</u></h2>
                </center>
                <div id="elementid2">
                </div>
            </div>

            <div class="row" style="margin-top: 10px;margin-left:5%;display:none" id="registration_type_details">
                <center>
                    <h2><u>Registration</u></h2>
                </center>
                <div id="elementid3">
                </div>
            </div>



            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%; display: none" id="table_for_operation_name">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Operation Name Table</div>
                        <div class="panel-body" style="padding: 0px;">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_operation" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Parent Operation Name</th>                                                                                   
                                            <th>Operation Name</th>                                                                                   
                                            <th>Is Super Child</th>                                                                                                                                                                     
                                            <th>Generation</th>                                                                                                                                                                     
                                            <th>Remark</th>                                                                                                                                                                             
                                        </tr>
                                    </thead>
                                    <tbody>									
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>				
            </div>

            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%; display: none" id="table_for_device_name">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Device Name</div>
                        <div class="panel-body" style="padding: 0px;">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_DeviceName" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Device Id</th>
                                            <th>Device Name</th>                                            
                                            <th>Device No.</th>                                            
                                            <th>Warranty Period</th>                                            
                                            <th>Device Address</th>                                            
                                            <th>No. of Module</th>                                            
                                            <th>Model Type</th>                                                                                                                                    
                                        </tr>
                                    </thead>
                                    <tbody>									
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>				
            </div>


            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%; display: none" id="table_for_finished_product">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Finished Product</div>
                        <div class="panel-body" style="padding: 0px;">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_FinishedProduct" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Device Id</th>
                                            <th>Device Name</th>                                            
                                            <th>Device No.</th>                                            
                                            <th>Warranty Period</th>                                            
                                            <th>Device Address</th>                                            
                                            <th>No. of Module</th>                                            
                                            <th>Model Type</th>                                                                                                                                    
                                        </tr>
                                    </thead>
                                    <tbody>									
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>				
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

