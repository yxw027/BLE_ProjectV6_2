<%-- 
    Document   : deviceRegConnPage
    Created on : Dec 5, 2019, 11:45:28 AM
    Author     : rituk
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
<script type="text/javascript" language="javascript">
//   $(document).ready(function () {
//     $(function () {
//         document.getElementById("td3").style.display = "none";
//     });
// });

    jQuery(function() {
$("#manufacturer_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function() { return "getManufactureName"}
            }
        });
        $("#device_type_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function() { return "getDeviceTypeName"},
                action2: function() { return  $("#manufacturer_name").val();}
            }
        });
        $("#device_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function() { return "getDeviceName"}
            }
        });
        $("#device_no").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function () {
                    return "getDeviceNo"
                },
                action2: function () {
                    return  $("#device_name").val();
                }
            }
        });
                             
    });

    
    function setStatus(id) {
        if (id == 'connection') {
            document.getElementById("clickedButton").value = "connection";
        }else if(id == 'send'){
            document.getElementById("clickedButton").value = "connection";
        }
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

    function checkmethod(name,no){
       //alert(name+" and "+no);
     //   document.getElementById("td"+no).style.display = "block";   
    if(document.getElementById("isCheck").checked){    
        document.getElementById("td"+no).style.display = "block";
    }
    else{      
          //document.getElementById("td"+no).style.display = "none";
          document.getElementById("td"+no).style.display = "block";
    }

    }

    function checkmethod1(name,no){       
    if(document.getElementById("isCheckOperation").checked){
        document.getElementById("tdC"+no).style.display = "block";
    }
    else{
        // document.getElementById("tdC"+no).style.display = "none";
         document.getElementById("td"+no).style.display = "block";
    }
        
    }


</script>
<style>
a:hover{
  background-color: yellow;
}</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Device Reg Conn Page</title>
        <meta charset="utf-8">

    </head>
    <body>

        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <td>
        <DIV id="body" class="maindiv" align="center" >
            <table width="100%" align="center">
                <tr><td>
                        <table align="center">
                            <tr>
                                <td align="center" class="header_table" width="100%">Device Registration/Configuration Connection</td>

                            </tr>
                        </table>
                    </td></tr>                               

                <tr>
                    <td align="center">
                        <div>
                            <form name="form2" method="POST" action="deviceRegistrationCont.do">
                                <table id="table2"  class="content" border="0"  align="center" width="600">

                                    <tr>
                                        <th class="heading1">Manufacturer Name </th>
                                        <td><input class="input" type="text" id="manufacturer_name" name="manufacturer_name" value="${manufacturer}" size="40" >
                                    </tr>

                                    <tr>
                                        <th class="heading1">Device Type</th>
                                        <td><input class="input" type="text" id="device_type_name" name="device_type_name" value="${device_type}" size="40" ></td>
                                    </tr>
                                    <tr>
                                        <th class="heading1">Model Name</th>
                                        <td><input class="input" type="text" id="device_name" name="device_name" value="${deviceName}" size="40"></td>
                                    </tr>
                                    <tr>
                                        <th class="heading1">Model No.</th>
                                        <td><input class="input" type="text" id="device_no" name="device_no" value="${device_no}" size="40" ></td>
                                    </tr>

                                    <tr>
                                        <td align='center' colspan="2">                                       
                                            <input class="button" type="submit" name="task" id="connection" value="connection" onclick="setStatus(id)">
                                        </td>
                                    </tr>

                                    <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                    <input type="hidden" name="active" id="active" value="">                                    
                                    <input type="hidden" id="clickedButton" value="">                                    
                                </table>
                            </form>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td align="center">
                        <form name="form1" method="POST" action="deviceRegistrationCont.do">
                            <DIV class="content_div">
                                <table id="table1" width="600"  border="1"  align="center" class="content">
                                    <tr>
                                        <th class="heading">Model Name</th>
                                    </tr>
                                    <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                    <c:forEach var="divisionTypeBean" items="${requestScope['divisionTypeList']}"  varStatus="loopCounter">
                                        <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">                                  
                                            <td><input type="checkbox" id="isCheck" name="isCheck" value ="${divisionTypeBean.device_registration_id},${divisionTypeBean.device_type_id}" onclick="checkmethod('${divisionTypeBean.device_name}','${loopCounter.index}')"/>${divisionTypeBean.device_name}</td>
                                        <tr>
                                            <td  id="td${loopCounter.index}" align="center" style="display:none">
                                                <DIV class="content_div">
                                                    <table id="table1" width="600"  border="1"  align="center" class="content">
                                                        <tr>                                                   
                                                            <th class="heading">Operation Name</th>
                                                        </tr>                                            
                                                        <c:forEach var="typebean" items="${divisionTypeBean.deviceregBean}"  varStatus="loopCounter1">
                                                            <tr>
<!--                                                                    <td  style="display:none"><input type="checkbox" name="name1" />${typebean.operation_id}</td>-->
                                                                <td><input type="checkbox" id="isCheckOperation" name="isCheckOperation" value ="${typebean.operation_id},${divisionTypeBean.device_registration_id}"  onclick="checkmethod1('${divisionTypeBean.device_name}','${loopCounter1.index}')"/>${typebean.operation_name}</td>

                                                                <!--          test for command    -->

                                                            <tr>
                                                                <td  id="tdC${loopCounter1.index}" align="center" style="display:none">
                                                                    <DIV class="content_div">
                                                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                                                            <tr>
                                                                                <th class="heading">Command</th>
                                                                            </tr>
                                                                            <c:forEach var="typebean1" items="${typebean.commandListBean}"  varStatus="loopCounter">
                                                                                <tr>
                                                                                    <td><input type="checkbox" id="isCheckCommand" name="isCheckCommand" value ="${typebean1.command},${typebean.operation_id},${typebean1.order_no},${typebean1.delay}" checked="checked"/>${typebean1.command}</td>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </table>   </DIV>
                                                                </td>
                                                            </tr>

                                                                <!--         end  test for command    -->


                                                            </tr>
                                                            </c:forEach>
                                                </table>   </DIV>
                                            </td>
                                        </tr>
                                    </tr>
                                    </c:forEach>

                                    <tr>
                                        <td align='center' colspan="2">
                                            <input class="button" type="submit" name="task" id="send" value="send" onclick="setStatus(id)">
                                             <input type="hidden" value="${device_id}" name="d_id">
                                              <input type="hidden" value="${reg_no1}" name="reg_no1">
                                        </td>
                                    </tr>

                                </table>
                            </DIV>
                        </form>
                    </td>
                </tr>

            </table>

        </DIV>
    </td>
    <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>  
        </table>
    </body>
</html>