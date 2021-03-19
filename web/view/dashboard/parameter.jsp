<%-- 
    Document   : parameter
    Created on : Feb 6, 2021, 9:18:45 AM
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
        <title>Parameter</title>



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

        <script src="JS/parameter.js"></script>

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

                document.getElementById("parameter_name").disabled = false;
                document.getElementById("parameter_type").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("save").disabled = false;
                if (id === 'new') {

                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("parameter_name").focus();
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
                            <b>Parameter</b>
                        </h2>
                    </div>
                </div>
            </center>								


            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%;font-size:14px" id="table_for_module_type">
                <div class="col-md-12">
                    <!--                                 Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Parameter Table</div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_Model_Type" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th>S.No</th>
                                            <th>Parameter Name</th>                                                                                   
                                            <th>Parameter Type</th>                                            
                                            <th>Remark</th>                                                                                        
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="model" items="${requestScope['list2']}"
                                                   varStatus="loopCounter">
                                            <tr
                                                onclick="fillColumn('${model.id}', '${loopCounter.count }');">
                                                <td>${loopCounter.count }</td>
                                                <td id="${loopCounter.count }2">${model.parameter_name}</td>
                                                <td id="${loopCounter.count }3">${model.parameter_type}</td>
                                                <td id="${loopCounter.count }4">${model.remark}</td>                                                                                               
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
                <form method="post" action="ParameterNameController" name="form1" style="background-color: white;">

                    <c:if test="${not empty message}">
                        <div style="font-size: 14px;width:30%;margin-left:2%;margin-top:2%;background-color: ${msgBgColor}"><b>Result: ${message}</b></div>
                    </c:if>



                    <div class="row">
                        <div class="col-lg-5 col_label" style="margin-top:35px">
                            <label class="required" style="font-size:20px;">Parameter Name:</label>
                        </div>
                        <div class="col-lg-5" style="margin-top:35px">                            
                            <input type="text" disabled name="parameter_name" id="parameter_name" class="form-control m-input" placeholder="Enter Parameter Name" required>
                        </div>                        
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Parameter Type:</label>
                        </div>
                        <div class="col-lg-5">  
                            <select disabled id="parameter_type" name="parameter_type" class="form-control" onchange="getParameterType(this.value);">
                                <option value="0">Select Parameter Type</option>
                                <option value="Selection">Selection</option>
                                <option value="Input">Input</option>
                                <option value="Bitwise">Bitwise</option>
                                <option value="Hex">Hex</option>
                                <option value="String">String</option>
                                <option value="Boolean">Boolean</option>
                            </select>
                            <!--                            <input type="text" disabled name="parameter_type" id="parameter_type" class="form-control m-input" onchange="getParameterType(value);" placeholder="Enter Parameter Type" required>-->
                        </div>                        
                    </div>

                    <div class="row" id="sel_val_div" style="display:none;">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Selection Value No:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="number" name="sel_val_no" id="sel_val_no" class="form-control" onchange="getSelectionValueNo(this.value);" placeholder="Enter Selection Value No">
                        </div>                        
                    </div>


                    <div class="row" id="selection_div_start" style="display:none;margin-left: 15%;margin-right: 16%;">
                        <div class="col-lg-12">
                            <div class="row" style="padding-bottom:10px;margin-left: 0px;margin-right: 0px;" id="selection_field_div">
                                <!--                                <div class="col-lg-6" style="border:2px solid black">
                                                                    <div class="row">
                                                                        <div class="col-lg-6">
                                                                            <label>Display Value</label>
                                                                        </div>
                                                                        <div class="col-lg-6">
                                                                            <label>Byte Value</label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row" style="margin-bottom: 10px;">
                                                                        <div class="col-lg-6">
                                                                            <input type="number" name="sel_val_no" id="sel_val_no" class="form-control" onchange="getSelectionValueNo(this.value);" placeholder="Enter Selection Value No">
                                                                        </div>
                                                                        <div class="col-lg-6">
                                                                            <input type="number" name="sel_val_no" id="sel_val_no" class="form-control" onchange="getSelectionValueNo(this.value);" placeholder="Enter Selection Value No">
                                                                        </div>
                                                                    </div>
                                                                </div>-->
                            </div>
                        </div>                                
                    </div>

                    <div class="row" id="bitiwse_no_div" style="display:none;">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Bitwise:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="number" name="bitiwse_no" id="bitiwse_no" class="form-control" onchange="getBitwiseNo(this.value);" placeholder="Enter Bitwise No">
                        </div>                        
                    </div>

                    <div class="row" id="bitwise_div_start" style="display:none;margin-left: 15%;margin-right: 16%;">
                        <div class="col-lg-12">
                            <div class="row" style="padding-bottom:10px;margin-left: 0px;margin-right: 0px;" id="bitwise_field_div">
                            </div>
                        </div>                                
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Remark:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" disabled name="remark" id="remark" class="form-control m-input" placeholder="Enter Remark" autocomplete="off">
                        </div>
                    </div>


                    <div class="row" style="margin-bottom:35px">
                        <input type="submit"  name="task" id="delete" value="Delete" onclick="showNewDevice();" style="margin:35px 35px 35px 0px;float:right" class="btn btn-danger" disabled/>
                        <input type="button"  name="task" id="edit" value="Edit" onclick="makeEditable(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-info" disabled/>
                        <input type="submit"  name="task" id="save" value="Save" onclick="setStatus(id)" style="margin:35px 10px 35px 0px;float:right" class="btn btn-success" disabled/>
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
                                $('#dataTables_Model_Type').dataTable({
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


