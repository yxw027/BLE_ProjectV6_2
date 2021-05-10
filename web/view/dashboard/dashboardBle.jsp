<%-- 
    Document   : dashboardBle
    Created on : Nov 28, 2020, 4:34:00 PM
    Author     : saini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>

<link type="text/css" href="style/menu.css" rel="stylesheet"/>
<!--<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<link href="assets/css/bootstrap.css" rel="stylesheet" />-->

<!--<script src="//code.jquery.com/jquery-1.10.2.js"></script>-->

<!--<link rel="stylesheet"
        href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>-->

<script src=" https://code.jquery.com/jquery-3.5.1.js "></script>
<link href="assets/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" language="javascript">
//    jQuery(function () {
//        $("#manufacturer_name").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getManufactureName"
//                }
//            }
//        });
//        $("#device_type_name").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getDeviceTypeName"
//                }
//            }
//        });
//        $("#device_name").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getDeviceName"
//                }
//            }
//        });
//        $("#device_no").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getDeviceNo"
//                },
//                action2: function () {
//                    return  $("#device_name").val();
//                }
//            }
//        });
//        $("#searchManufactureName").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getSearchManufactureName"
//                }
//            }
//        });
//        $("#searchDeviceType").autocomplete("DeviceCont.do", {
//            extraParams: {
//                action1: function () {
//                    return "getSearchDeviceType"
//                }
//            }
//        });
//
//    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = ""; // set the default color.
            }
        }
    }
    function makeEditable(id) {

        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("device_type_name").disabled = false;
        document.getElementById("device_name").disabled = false;
        document.getElementById("device_no").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
//        document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("save_As").disabled = false;
        //document.getElementById("save").disabled = true;
        if (id === 'new') {
            //    document.getElementById("created_date").disabled = true;
            // document.getElementById("active").value ='';

            document.getElementById("message").innerHTML = ""; // Remove message
            document.getElementById("manufacturer_name").focus();
            $("#message").html("");
            //document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
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
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else if (id == 'revise') {
            document.getElementById("clickedButton").value = "Revise";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise' || document.getElementById("clickedButton").value == 'Delete') {
            var division_name_m = document.getElementById("division_name_m").value;
            var a = document.getElementById("active").value;
            //    alert(a);
            if (myLeftTrim(division_name_m).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Ward No is required...</b></td>");
                document.getElementById("division_name_m").focus();
                return false; // code to stop from submitting the form2.
            }

            if (document.getElementById("active").value == 'Revised' || document.getElementById("active").value == 'Cancelled')
            {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on revised and cancelled record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            } else {
                result = true;
            }

            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else
            result = confirm("Are you sure you want to cancel this record?")
        return result;
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 7;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1; // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns; // e.g. 20 = ((1 + 1) * 10)

            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns); // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c"; // particular column id of table 1 e.g. t1c3.

        document.getElementById("device_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("manufacturer_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("device_type_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("device_name").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("device_no").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd"; // set the background color of clicked row to yellow.
        }
        //   makeEditable('');

        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and cancel button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    var popupwin = null;
    function displayMapList() {
        var queryString = "task=generateMapReport";
        var url = "divisionCont?" + queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function goTo(type) {
        if (type === "manufacturer") {
            window.location.href = "ManufacturerCont.do";
        }
        if (type === "device_type") {
            window.location.href = "DeviceTypeCont.do";
        }
        if (type === "device_name") {
            window.location.href = "ModelCont.do";
        }
    }

    function goToDeviceMap() {
        window.location.href = "DeviceMapCont.do";
    }


    function showSubRow(id) {
        //alert(id);
        //$("#"+id).css('background-color','red');

        if ($('#commandDiv').css('display') != 'none') {
            $('#commandDiv').hide();
        }
        var dataTable = $('#dataTables-example').DataTable();
        dataTable.clear().page.len(5).draw();
        //alert("before ajax");
        $.ajax({
            url: 'DashboardController',
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getSubModule",
                q: id
            },
            success: function (result) {//alert(121);
                console.log(result);
                if ($('#dataTables-example').length > 0) {
                    var k = 0;
                    for (var l = 0; l < result.data.length; l++) {
                        $('#dataTables-example').dataTable().fnAddData([
                            ++k,
                            result.data[l].manufacture_name,
                            result.data[l].device_type_name,
                            result.data[l].model_type,
                            result.data[l].device_name,
                            result.data[l].device_no,
                            result.data[l].warranty_period,
                            result.data[l].device_address,
                            result.data[l].no_of_module,
                            result.data[l].device_id
                        ]
                                );
//                        $('.even').toggleClass('even class' + l + '').attr('onclick', 'viewCommandDetails(' + result.data[l].device_id + ');').attr('id', 'cd' + result.data[l].device_id);
//                        $('.odd').toggleClass('odd class' + l + '').attr('onclick', 'viewCommandDetails(' + result.data[l].device_id + ');').attr('id', 'cd' + result.data[l].device_id);                        
                    }
                }
            }
        });
        // end dashLiveVehicles



        if ($('#subModuleDiv').css('display') == 'none') {
            $('#subModuleDiv').show();
        } else {
            //$('#subModuleDiv').hide();
        }
    }

    $(document).ready(
            function () {

                $("#table1 tr").click(function () {
                    var selected = $(this).hasClass("selected_row");
                    $("#table1 tr").removeClass("selected_row");
                    if (!selected)
                        $(this).addClass("selected_row");
                });
                $('#dataTables-example tbody').on(
                        'click',
                        'tr',
                        function () {
                            var d = $("#dataTables-example").DataTable().row(this).data();
                            //viewCommandDetails(d);
                            viewOperationName(d);
                            if ($(this).hasClass('selected_row2')) {
                                $(this).removeClass('selected_row2');
                            } else {
                                $("#dataTables-example").DataTable().$(
                                        'tr.selected_row2').removeClass(
                                        'selected_row2');
                                $(this).addClass('selected_row2');

//                                var row = table.row(tr);
//                                viewCommandDetails();
                            }
                        });

                $('#dataTables-example3 tbody').on(
                        'click',
                        'tr',
                        function () {
                            var d = $("#dataTables-example3").DataTable().row(this).data();
                            viewCommandDetails(d);
                            if ($(this).hasClass('selected_row2')) {
                                $(this).removeClass('selected_row2');

                                //var tr = $(this).closest('tr');
                                //var row = $(this).row(tr);
                                //viewCommandDetails($(this).data());

                            } else {
                                $("#dataTables-example3").DataTable().$(
                                        'tr.selected_row2').removeClass(
                                        'selected_row2');
                                $(this).addClass('selected_row2');

//                                var row = table.row(tr);
//                                viewCommandDetails();
                            }
                        });

            });

    function viewOperationName(id) {//alert("idd 9 --"+id[9]+"  -- id 8 --"+id[8]+" --- id 10 --"+id[10]);
        var dataTable = $('#dataTables-example3').DataTable();
        dataTable.clear().draw();
        //alert("before ajax");
        $.ajax({
            url: 'DashboardController',
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getOperationName",
                q: id[9]
            },
            success: function (result) {//alert(121);
                console.log(result);
                if ($('#dataTables-example3').length > 0) {
                    var k = 0;
                    for (var l = 0; l < result.data.length; l++) {
                        //alert("field 2 -"+result.data[l].field2+"  field 11 -"+result.data[l].remark);
                        $('#dataTables-example3').dataTable().fnAddData([
                            ++k,
                            result.data[l].operation_id,
                            result.data[l].operation_name,
                            result.data[l].field1,
                            result.data[l].field2,
                            result.data[l].remark,
                            result.data[l].device_id,
                        ]
                                );
//                        $('.even').toggleClass('even class' + l + '').attr('id', result.data[l].device_id + ',' + result.data[l].command_id);
//                        $('.odd').toggleClass('odd class' + l + '').attr('id', result.data[l].device_id + ',' + result.data[l].command_id);
                    }
                }
            }
        });
        if ($('#operationDiv').css('display') == 'none') {
            $('#operationDiv').show();
        }
    }


    function viewCommandDetails(id) {
        var dataTable = $('#dataTables-example2').DataTable();
        dataTable.clear().draw();
        //alert("before ajax");
        $.ajax({
            url: 'DashboardController',
            contentType: "application/json",
            dataType: 'json',
            data: {
                action1: "getCommandDetail",
                q: id[1],
                r: id[6]
            },
            success: function (result) {//alert(121);
                console.log(result);
                if ($('#dataTables-example2').length > 0) {
                    var k = 0;
                    for (var l = 0; l < result.data.length; l++) {
                        //alert("device id -"+result.data[l].device_id+"  c0=ommand id -"+result.data[l].command_id);
                        $('#dataTables-example2').dataTable().fnAddData([
                            ++k,
                            result.data[l].command,
                            result.data[l].shorthand,
                            result.data[l].starting_del,
                            result.data[l].end_del,
                            result.data[l].format,
                            result.data[l].input,
                            result.data[l].bitwise,
                            result.data[l].command_id
                        ]
                                );
//                        $('.even').toggleClass('even class' + l + '').attr('id', result.data[l].device_id + ',' + result.data[l].command_id);
//                        $('.odd').toggleClass('odd class' + l + '').attr('id', result.data[l].device_id + ',' + result.data[l].command_id);
                    }
                }
            }
        });
        if ($('#commandDiv').css('display') == 'none') {
            $('#commandDiv').show();
        }
    }

</script>
<style>
    a:hover{
        background-color: yellow;
    }

    .heading2{
        background-color: yellow;
    }

    td.details-control {
        background: url('images/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('images/details_close.png') no-repeat center center;
    }

/*    #dataTables-example_length{
        display:none;
    }

    #dataTables-example_filter{
        display:none;
    }

    #dataTables-example2_length{
        display:none;
    }

    #dataTables-example2_filter{
        display:none;
    }*/

    .evenn{
        background-color: #ddd;
    }
    .oddd{
        background-color: white;
    }

    .selected_row {
        background-color: yellow;
    }

    .selected_row2 {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }

    .shownNew {
        color: red;
    }


</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dash Page</title>
        <meta charset="utf-8">

    </head>
    <body>

        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <td><%@include file="/layout/menu.jsp" %> </td>            </tr>
        <td>
            <DIV id="body" class="maindiv" align="center" >
                <table width="100%" align="center">
                    <tr><td>
                            <table align="center">
                                <tr>
                                    <td align="center" class="header_table" width="100%">Details</td>

                                </tr>
                            </table>
                        </td></tr>
                    <tr>
                        <td> <div align="center">
                                <form name="form0" method="POST" action="DeviceCont.do">
                                    <table align="center" class="heading1" width="600">
                                        <tr>
                                            <td>Manufacture Name<input class="input" type="text" id="searchManufactureName" name="searchManufactureName" value="${searchManufacturerName}" size="20" ></td>
                                            <td>Device Type<input class="input" type="text" id="searchDeviceType" name="searchDeviceType" value="${searchDeviceTypeName}" size="20" ></td>
                                            <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                            <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                            <td><input class="button" type="button" name="task" id="nextPage" value="Next Page" onclick="goToDeviceMap()"></input></td>
                                            <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                        </tr>
                                    </table>
                                </form></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <form name="form1" method="POST" action="DashboardController">
                                <DIV class="content_div">
                                    <table id="table1" width="600"  border="1"  align="center" class="content">
                                        <tr>
                                            <th class="heading">S.No.</th>
                                            <th class="heading">Manufacturer Name</th>
                                            <th class="heading">Device Type</th>
                                            <th class="heading">Model Type</th>
                                            <th class="heading">Device Name</th>
                                            <th class="heading">Device No.</th>
                                            <th class="heading">Warranty</th>
                                            <th class="heading">Address</th>
                                            <th class="heading">No. Of Module</th>
                                            <th class="heading">Finished Product</th>
                                        </tr>
                                        <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                        <c:forEach var="dashBean" items="${requestScope['dashList']}"  varStatus="loopCounter">
                                            <tr  class="${loopCounter.index % 2 == 0 ? 'evenn': 'oddd'}" id="${dashBean.device_id}" onclick="showSubRow(id)"> 
                                                <td id="t1c${IDGenerator.uniqueID}" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.manufacture_name}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.device_type_name}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.model_type}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.device_name}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.device_no}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.warranty_period}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.device_address}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.no_of_module}</td>
                                                <td id="t1c${IDGenerator.uniqueID}">${dashBean.finished_product}</td>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td align='center' colspan="13">
                                                <c:choose>
                                                    <c:when test="${showFirst eq 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='First'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showPrevious == 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showNext eq 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Next'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showLast == 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Last'>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                        <input type="hidden" name="manname" value="${manname}">
                                        <input type="hidden" name="dname" value="${dname}">
                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                        <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        <input  type="hidden" id="searchCityType" name="searchCityType" value="${searchCityType}" >
                                        <input  type="hidden"  name="searchOperationName" value="${searchOperationName}" >
                                    </table></DIV>

                                <div class="content_div" id="subModuleDiv" style="display:none">
                                    <!-- Advanced Tables -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading" style="">Sub Modules</div>
                                        <div class="panel-body" style="padding: 0px;">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover"
                                                       id="dataTables-example" style="margin-bottom: 5px;">
                                                    <thead>
                                                        <tr>
                                                            <th class="heading">S.No.</th>
                                                            <th class="heading">Manufacturer Name</th>
                                                            <th class="heading">Device Type</th>
                                                            <th class="heading">Model Type</th>
                                                            <th class="heading">Device Name</th>
                                                            <th class="heading">Device No.</th>
                                                            <th class="heading">Warranty</th>
                                                            <th class="heading">Address</th>
                                                            <th class="heading">No. Of Module</th>
                                                            <th class="heading">Device ID</th>                                                            
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


                                <div class="content_div" id="operationDiv" style="display:none">
                                    <!-- Advanced Tables -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Operation Name</div>
                                        <div class="panel-body" style="padding: 0px;">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover"
                                                       id="dataTables-example3" style="margin-bottom: 5px;">
                                                    <thead>
                                                        <tr>
                                                            <th class="heading">S.No.</th>
                                                            <th class="heading">Operation Id</th>
                                                            <th class="heading">Operation Name</th>
                                                            <th class="heading">Is Super Child</th>
                                                            <th class="heading">Generation</th>
                                                            <th class="heading">Remark</th>                                                            
                                                            <th class="heading">Device Id</th>                                                            
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


                                <div class="content_div" id="commandDiv" style="display:none">
                                    <!-- Advanced Tables -->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Commands</div>
                                        <div class="panel-body" style="padding: 0px;">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover"
                                                       id="dataTables-example2" style="margin-bottom: 5px;">
                                                    <thead>
                                                        <tr>
                                                            <th class="heading">S.No.</th>
                                                            <th class="heading">Command</th>
                                                            <th class="heading">Short Name</th>
                                                            <th class="heading">Start Del</th>
                                                            <th class="heading">Ending Del</th>
                                                            <th class="heading">Format</th>
                                                            <th class="heading">Input</th>
                                                            <th class="heading">Bitwise</th>  
                                                            <th class="heading">Command_id</th>  
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


                            </form>
                        </td>
                    </tr>                   
                </table>

            </DIV>
        </td>
        <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        <c:if test="${not empty confirmation}">
            <script>
                alert
                var confirmation = confirm("Do you want to add more devices");
                if (confirmation === true) {
                    window.location.href = "ModelTypeCont.do";
                } else {
                    window.location.href = "DeviceMapCont.do";
                }

            </script>
        </c:if>

        <script src="assets/js/dataTables/jquery.dataTables.js"></script>
        <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
        <script>
                $(document).ready(function () {
                    $('#dataTables-example').DataTable({
//                        "paging": true,
                        "pageLength": 5,
                        "columnDefs": [
                            {
                                "targets": [9],
                                "visible": false,
                                "searchable": false
                            }
                        ]
                    });
                    
//                    $('#dataTables-example3').DataTable({
////                        "paging": true,
//                        "pageLength": 5,
//                        "columnDefs": [
//                            {
//                                "targets": [2],
//                                "visible": false,
//                                "searchable": false
//                            }
//                        ]
//                    });
//                    
//                    $('#dataTables-example2').DataTable({
////                        "paging": true,
//                        "pageLength": 5,
//                        "columnDefs": [
//                            {
//                                "targets": [2],
//                                "visible": false,
//                                "searchable": false
//                            }
//                        ]
//                    });
                    
                });
                /* Formatting function for row details - modify as you need */
                function format(d, id) {
                    //alert("data iddd -" + d + " iddddddd --" + id);
                    //alert("dv id --"+d[9]+" command id -"+d[10]);   
                    var device_id = d[9];
                    var command_id = d[10];
                    var all = new Array();
                    var opName = new Array();

                    var json = JSON.parse(getOpName(device_id, command_id));
                    //alert("jsssoon --"+json);
                    //alert("jsssoon data --"+json.data.length);

                    for (var l = 0; l < json.data.length; l++) {
                        //alert("name  -"+json.data[l].operation_name);
                        opName.push(json.data[l].operation_name);
                    }

                    var a = 1;
                    var all = new Array();
                    for (i = 0; i < opName.length; i++) {
                        all[i] = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
                                '<tr>' +
                                '<th>Operation Name ' + (i + 1) + ':</th>' +
                                '<td>' + opName[i] + '</td>' +
                                '</tr>' +
                                '</table>';
                        a++;
                    }
                    return all;
                }

                function getOpName(device_id, command_id) {
                    //alert(1121);
                    var id = "opName"
                    var jqXHR = $.ajax({
                        url: 'DashboardController',
                        contentType: "application/json",
                        dataType: 'json',
                        async: false,
                        data: {
                            action1: "getOperationName",
                            q: id,
                            device_id: device_id,
                            command_id: command_id
                        },
                        success: function (result) {
                        }
                    });
                    return jqXHR.responseText;
                }

                $(document).ready(function () {

//                    $('#dataTables-example3').DataTable({
//                        "pageLength": 5,
//                        "columnDefs": [
//                            {
//                                className: "details-control", targets: "_all"
//                            },
//                            {
//                                //"targets": [9, 10],
//                                "visible": false,
//                                "searchable": false
//                            }
//
//                        ]
//
//                    });

//                    var table = $('#dataTables-example2').DataTable({
//                        "pageLength": 5,
//                        "columnDefs": [
//                            {
//                                className: "details-control", targets: "_all"
//                            },
//                            {
//                                "targets": [2, 3],
//                                "visible": false,
//                                "searchable": false
//                            }
//
//                        ]
//
//                    });
                    // Add event listener for opening and closing details
                    $('#dataTables-example2 tbody').on('click', 'td.details-control', function () {
                        var tr = $(this).closest('tr');
                        //tr.css("background-color","red");                                               
                        var id = $(this).closest("tr").attr('id');
                        //alert("id before send -"+id);

                        var row = table.row(tr);
                        if (row.child.isShown()) {
                            // This row is already open - close it
                            row.child.hide();
                            tr.removeClass('shown');
                            tr.removeClass('selected_row2');
                        } else {
                            // Open this row                   
                            row.child(format(row.data(), id)).show();
                            tr.addClass('shown');
                            tr.addClass('selected_row2');
                        }
                    });
                });

        </script>

    </table>




</body>
</html>

