<%-- 
    Document   : dev_ope_com_map
    Created on : Feb 4, 2021, 2:33:53 PM
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
        <script src="JS/command.js"></script>       

        <script type="text/javascript">
            function zoom() {
                document.body.style.zoom = "90%"
            }

            $(function () {

            })

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

                $("#my_image")
                        .attr(
                                "src",
                                "http://120.138.10.146:8080/ContactLess_Management/VisitTempDetailsController?getImage="
                                + image_path + "");

            }

            $(document).ready(
                    function () {
                        $('#dataTables_DevOPComMap tbody').on(
                                'click',
                                'tr',
                                function () {
                                    if ($(this).hasClass('selected_row')) {
                                        $(this).removeClass('selected_row');
                                    } else {
                                        $("#dataTables_DevOPComMap").DataTable().$(
                                                'tr.selected_row').removeClass(
                                                'selected_row');
                                        $(this).addClass('selected_row');
                                    }
                                });
                    });


            $(function () {
                $("#parameter").autocomplete({
                    source: function (request, response) {
                        var random = document.getElementById("parameter").value;
                        var type="Common";
                        $.ajax({
                            url: "CommandController",
                            dataType: "json",
                            data: {
                                action1: "getParameter",
                                str: random,
                                type: type
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
                        $('#parameter').val(ui.item.label); // display the selected text
                        return false;
                    }
                });

                $("#command_type").autocomplete({
                    source: function (request, response) {
                        var random = document.getElementById("command_type").value;
                        $.ajax({
                            url: "CommandController",
                            dataType: "json",
                            data: {
                                action1: "getCommandType",
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
                        $('#command_type').val(ui.item.label); // display the selected text
                        return false;
                    }
                });

            });

            function fillColumn(dev_id, count) {
                $('#manufacturer_name').val($("#" + count + '2').html());
                $('#device_type').val($("#" + count + '3').html());
                $('#model_name').val($("#" + count + '4').html());
                $('#model_no').val($("#" + count + '5').html());
                $('#remark').val($("#" + count + '6').html());
                $('#edit').attr('disabled', false);
            }

            function makeEditable(id) {
                document.getElementById("command_format").disabled = false;
                document.getElementById("command_format1").disabled = false;
                document.getElementById("command").disabled = false;
                //document.getElementById("parameter").disabled = false;
                document.getElementById("insert_param_btn").disabled = false;

                document.getElementById("command_type").disabled = false;
                document.getElementById("starting_del").disabled = false;
                document.getElementById("ending_del").disabled = false;
//                document.getElementById("selection_no").disabled = false;
//                document.getElementById("input_no").disabled = false;
//                document.getElementById("bitwise").disabled = false;
                document.getElementById("short_name").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("save").disabled = false;
                if (id === 'new') {

                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("manufacturer_name").focus();
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

            function mergeParameter(value)
            {
                var para = document.getElementById('parameter').value;
                var para1 = document.getElementById("command").value;
                var para2 = para1 + para;
                para1.value = para2;

                $("#command").val(para2);
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
                            <b>Command Detail</b>
                        </h2>
                    </div>
                </div>
            </center>								


            <div class="row" style="margin-top: 10px;margin-left: 10%;margin-right: 10%;font-size:14px" id="table_for_module_type">
                <div class="col-md-12">
                    <!--                                 Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">Command Detail Table</div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover"
                                       id="dataTables_DevOPComMap" style="margin-bottom: 5px;">
                                    <thead>
                                        <tr>
                                            <th class="heading">S.No.</th>
                                            <th class="heading">Command</th>
                                            <th class="heading">Command Type</th>
                                            <th class="heading">Starting Delay</th>
                                            <th class="heading">End Delay</th>
                                            <th class="heading">Selection Number</th>
                                            <th class="heading">Input Number</th>
                                            <th class="heading">Bitwise</th>
                                            <th class="heading">Format</th>
                                            <th class="heading">Short Name</th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>                                        
                                        <c:forEach var="model" items="${requestScope['list']}"
                                                   varStatus="loopCounter">
                                            <tr
                                                onclick="fillColumn('${model.command_id}', '${loopCounter.count }');">
                                                <td>${loopCounter.count }</td>                                                
                                                <td id="${loopCounter.count }2">${model.command}</td>
                                                <td id="${loopCounter.count }3">${model.command_type}</td>
                                                <td id="${loopCounter.count }4">${model.starting_del}</td>
                                                <td id="${loopCounter.count }5" >${model.end_del}</td>
                                                <td id="${loopCounter.count }6" >${model.selection_no}</td>
                                                <td id="${loopCounter.count }7" >${model.input_no}</td>
                                                <td id="${loopCounter.count }8" >${model.bitwise}</td>
                                                <td id="${loopCounter.count }9" >${model.format}</td>
                                                <td id="${loopCounter.count }10" >${model.short_name}</td>                                                
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
                <form method="post" action="CommandController" name="form1" style="background-color: white;">

                    <c:if test="${not empty message}">
                        <div style="font-size: 14px;width:30%;margin-left:2%;margin-top:2%;background-color: ${msgBgColor}"><b>Result: ${message}</b></div>
                    </c:if>


                    <div class="row">
                        <div class="col-lg-5 col_label"  style="margin-top:35px">
                            <label class="required" style="font-size:20px;">Command Format:</label>
                        </div>
                        <div class="col-lg-5"  style="margin-top:35px">
                            <input type='radio' style="margin:10px 0px 0px;" id="command_format" class='free atype' value='hex' name='command_format' autocomplete="off" disabled/> Hexadecimal
                            <input type='radio' class='paid atype' value='String' id="command_format1" name='command_format' autocomplete="off" disabled/> String
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Command:</label>
                        </div>
                        <div class="col-lg-4">                            
                            <input type="text" disabled name="command" id="command" class="form-control m-input" onchange="mergeParameter(value)" placeholder="Enter Command" required>
                        </div>                                               
                        <div class="col-lg-1">
                            <button id="insert_param_btn" disabled type="button" onclick="enable_param();" class="btn btn-info" style="font-size:20px;"><b>Insert Parameter</b></button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Parameter:</label>
                        </div>
                        <div class="col-lg-4">                            
                            <input type="text" disabled name="parameter" id="parameter" class="form-control m-input" placeholder="Select Parameter">
                        </div>
                        <div class="col-lg-1">
                            <button id="insert_param_data" onclick="addParamValueToCommand();" type="button" class="btn btn-info" style="font-size:20px;"><b>Insert</b></button>
                        </div>
                        <div class="col-lg-1">
                            <button id="" type="button" class="btn btn-info" style="font-size:20px;"><b><a href="ParameterNameController" target="_blank">Add</a></b></button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="required" style="font-size:20px;">Command Type:</label>
                        </div>
                        <div class="col-lg-4">                            
                            <input type="text" disabled name="command_type" id="command_type" class="form-control m-input" placeholder="Select Command Type" autocomplete="off" required>  
                        </div>                        
                        <div class="col-lg-1">
                            <button id="goToModelNamePage" type="button" class="btn btn-info" style="font-size:20px;"><b><a href="CommandTypeController" target="_blank">Add</a></b></button>
                        </div>  
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Starting Del:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" disabled name="starting_del" id="starting_del" class="form-control m-input" placeholder="Enter Starting Delimeters" autocomplete="off"> 
                        </div>                       
                    </div>



                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Ending Del:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="text" disabled name="ending_del" id="ending_del" class="form-control m-input" placeholder="Enter Ending Delimeters" autocomplete="off"> 
                        </div>                       
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Selection No:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="number" disabled name="selection_no" id="selection_no" class="form-control m-input" onchange="showSelectionValueFields(value);" autocomplete="off"> 
                        </div>  
                        <div class="col-lg-1">
                            <button id="show_hide_btn" type="button" class="btn btn-info" onclick="showDivHide();" style="font-size:20px;display:none"><b>Hide</b></button>
                        </div>                        
                    </div>

                    <div class="row" id="selection_div_start" style="display:none;margin-left: 0px;margin-right: 0px;">
                        <div class="col-lg-12">
                            <div class="row" style="padding-bottom:10px;margin-left: 0px;margin-right: 0px;" id="selection_field_div">
                            </div>
                        </div>                                
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Input No:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="number" disabled name="input_no" id="input_no" class="form-control m-input" onchange="showInputValueFields(value);" autocomplete="off"> 
                        </div>    
                        <div class="col-lg-1">
                            <button id="input_show_hide_btn" type="button" class="btn btn-info" onclick="inputShowDivHide();" style="font-size:20px;display:none"><b>Hide</b></button>
                        </div>  
                    </div>

                    <div class="row" id="input_div_start" style="display:none;margin-left: 0px;margin-right: 0px;">
                        <div class="col-lg-12">
                            <div class="row" style="border:2px solid rgb(56, 165, 238, 0.5);padding:0px 0px 4px 0px;margin-left: 0px;margin-right: 0px;" id="input_field_div">
                            </div>
                        </div>                                
                    </div>

                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label style="font-size:20px;">Bitwise:</label>
                        </div>
                        <div class="col-lg-5">                            
                            <input type="number" disabled name="bitwise" id="bitwise" class="form-control" onchange="showBitwiseValueFields(value);" autocomplete="off"> 
                        </div>   
                        <div class="col-lg-1">
                            <button id="show_hide_btn_bitwise" type="button" class="btn btn-info" onclick="showDivHideBitwise();" style="font-size:20px;display:none"><b>Hide</b></button>
                        </div>
                    </div>

                    <div class="row" id="bitwise_div_start" style="display:none;margin-left: 0px;margin-right: 0px;">
                        <div class="col-lg-12">
                            <div class="row" style="padding-bottom:10px;margin-left: 0px;margin-right: 0px;" id="bitwise_field_div">
                            </div>
                        </div>                                
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col_label">
                            <label class="" style="font-size:20px;">Short Name:</label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" disabled name="short_name" id="short_name" class="form-control m-input" placeholder="Enter Short Name" autocomplete="off">
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
                                $('#dataTables_DevOPComMap').dataTable({
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


