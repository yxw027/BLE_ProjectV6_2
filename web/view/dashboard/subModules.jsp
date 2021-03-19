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

        <%@include file="/layout/menu2.jsp"%>
        <div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
            
            <div class="row" style="margin-top: 10px;margin-left:5%;" id="model_type_details">
                <div id="elementid3">
                    <c:forEach var="subModulesList" items="${requestScope['subModulesList']}"  varStatus="loopCounter">
                        <div class="col-md-3">
                            <img src="images/sub_modules2.jfif" onclick="showSubModulesData();" style="margin-top: 30px;" width="250" height="200">
                            <h4 style="margin-top:0px"><b>${subModulesList.sub_modules_name}</b></h4>
                        </div>
                    </c:forEach>
                </div>
            </div>



            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%; display: none" id="table_for_model_type">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Model Table</div>
                        <div class="panel-body" style="padding: 0px;">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_ModelType" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Model Id</th>
                                            <th>Model Type</th>                                            
                                            <th>Active</th>
                                            <th>Date</th>                                            
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

