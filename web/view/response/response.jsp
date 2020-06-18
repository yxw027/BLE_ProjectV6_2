<%-- 
    Document   : response
    Created on : 17 Dec, 2019, 12:13:53 PM
    Author     : DELL
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




<script type="text/javascript" language="javascript">
    jQuery(function () {

        $("#searchResponse").autocomplete("Response", {
            extraParams: {
                action1: function () {
                    return "getSearchResponse"
                }
            }
        });

        $("#searchCommandName").autocomplete("Response", {
            extraParams: {
                action1: function () {
                    return "getSearchCommand"
                }
            }
        });
        
        $("#command").autocomplete("Response", {
            extraParams: {
                action1: function () {
                    return "getSearchCommand"
                }
            }
        });
    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {

        
        document.getElementById("command").disabled = false;
        document.getElementById("response").disabled = false;
         document.getElementById("response_id").disabled = false;
        document.getElementById("fixed_response").disabled = false;
        document.getElementById("variable_response").disabled = false;
        document.getElementById("bitwise_response").disabled = false;
        document.getElementById("data_extract_type").disabled = false;
        document.getElementById("format").disabled = false;
        document.getElementById("remark").disabled = false;
        
        document.getElementById("save").disabled = false;
//        document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("save_As").disabled = false;
        //document.getElementById("save").disabled = true;
        if (id === 'new') {
            //    document.getElementById("created_date").disabled = true;
            // document.getElementById("active").value ='';
            document.getElementById("message").innerHTML = "";      // Remove message
            
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
//    function verify() {
//        var result;
//        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise' || document.getElementById("clickedButton").value == 'Delete') {
//            var division_name_m = document.getElementById("division_name_m").value;
//            var a = document.getElementById("active").value;
//            //    alert(a);
//            if (myLeftTrim(division_name_m).length == 0) {
//
//                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
//                $("#message").html("<td colspan='5' bgcolor='coral'><b>Ward No is required...</b></td>");
//                document.getElementById("division_name_m").focus();
//                return false; // code to stop from submitting the form2.
//            }
//
//            if (document.getElementById("active").value == 'Revised' || document.getElementById("active").value == 'Cancelled')
//            {
//                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on revised and cancelled record...</b></td>");
//                // document.getElementById("source_wattage"+i).focus();
//                return false; // code to stop from submitting the form2.
//            }
//            if (result == false)    // if result has value false do nothing, so result will remain contain value false.
//            {
//
//            } else {
//                result = true;
//            }
//
//            if (document.getElementById("clickedButton").value == 'Save AS New') {
//                result = confirm("Are you sure you want to save it as New record?")
//                return result;
//            }
//        } else
//            result = confirm("Are you sure you want to cancel this record?")
//        return result;
//    }
    function fillColumns(id) {
        debugger;
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 11;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        
      
        document.getElementById("command").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
        document.getElementById("response").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("response_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;        
        document.getElementById("fixed_response").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML.split(" ")[0];
        document.getElementById("variable_response").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML.split(" ")[0];
        document.getElementById("bitwise_response").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML.split(" ")[0];
        document.getElementById("data_extract_type").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("format").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
      
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;

        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
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
            if (str.charAt(i) === ' ')
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

    function pack(bytes) {
        var str = "";
// You could make it faster by reading bytes.length once.
        for (var i = 0; i < bytes.length; i += 2) {
// If you're using signed bytes, you probably need to mask here.
            var char = bytes[i] << 8;
// (undefined | 0) === 0 so you can save a test here by doing
//     var char = (bytes[i] << 8) | (bytes[i + 1] & 0xff);
            if (bytes[i + 1])
                char |= bytes[i + 1];
// Instead of using string += you could push char onto an array
// and take advantage of the fact that String.fromCharCode can
// take any number of arguments to do
//     String.fromCharCode.apply(null, chars);
            str += String.fromCharCode(char);
        }
        return str;
    }

    function selectionPopup(url,selection_no,command_id) {
        debugger;
        var popup_height = 580;
        var popup_width = 900;
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        url = url + "?selection_no="+selection_no+"&command_id="+command_id;
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        popupWindow = window.open(url, 'Selection Window', window_features);
    }
    
    function inputPopup(url,variable_response,response_id) {
        debugger;
        var popup_height = 580;
        var popup_width = 900;
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        url = url + "?variable_response="+variable_response+"&response_id="+response_id;
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        popupWindow = window.open(url, 'response Window', window_features);
    }
    
     function fixed(fixed_response,response,response_id) {
        debugger;
        document.getElementById("fixed_response1").value = fixed_response;
        document.getElementById("response1").value = response;
               document.getElementById("response_id1").value = response_id;
        document.forms['redirectFixedResponse'].submit();

    }
       function variable(bitwise_response,response_id) {
           debugger;
        document.getElementById("bitwise2").value = bitwise_response;
        document.getElementById("response_id2").value = response_id;
        document.forms['redirectByte'].submit();

    }
  function bits(bitwise,response_id) {
           debugger;
        document.getElementById("bitwise2").value = bitwise;
        document.getElementById("command_id2").value = response_id;
        document.forms['redirectByte'].submit();

    }
</script>
<style>
a:hover{
  background-color: yellow;
}</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Command Page</title>
        <meta charset="utf-8">
        <!--  <meta name="viewport" content="width=device-width, initial-scale=1">
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>-->

    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">Response DETAIL</td>

                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="Response">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <td>Command Name<input class="input" type="text" id="searchCommandName" name="searchCommandName" value="${searchCommandName}" size="20" ></td>
                                                <td>Response Name<input class="input" type="text" id="searchResponse" name="searchResponse" value="${searchResponse}" size="20" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form>
                                  </div>
                            </td>
                        </tr>
                        <tr>
                            <td align='center'>
                                <form name="form1" method="POST" action="Response">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                
                                                <th class="heading">Command</th>
                                                <th class="heading">Response</th>

                                                <th class="heading">Fixed Response Number</th>
                                                <th class="heading">Variable Response Number</th>
                                                <th class="heading">Bitwise Response Number</th>
                                                <th class="heading">Data Extract Type</th>
                                                <th class="heading">Format</th>
                                                <th class="heading">Remark</th>
                                                <th class="heading">ShortHand</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="response" items="${requestScope['responseList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${response.response_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.command}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.response}</td>
                               
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.fixed_response} <a href="#" onclick="fixed('${response.fixed_response}','${response.response}', '${response.response_id}');">(View Fixed Response)</a></td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.variable_response} <a href="#" onclick="inputPopup('VariableResponseCont',${response.variable_response},${response.response_id});return false" id="input_button">View Input</a></td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.bitwise_response} <a href="#" onclick="variable('${response.bitwise_response}','${response.response_id}');">View Bitwise</a></td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.data_extract_type} </td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.format}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.remark}</td>
                                                     <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${response.shorthand}</td>
                                                   

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="16">
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
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchCityType" name="searchCityType" value="${searchCityType}" >
                                            <input  type="hidden" id="searchDivisionType" name="searchDivisionType" value="${searchDivisionType}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>
                        <!--<div  id="divContentToPopup" style="display:none;">
                            <b>...content here...</b>
                        </div>-->
                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="Response" >
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            
                                            <tr>
                                                <th class="heading1">Command</th>
                                                <td><input class="input" type="text" id="command" name="command" value="" size="40" disabled>
                                                <input class="input" type="hidden" id="command_id" name="command_id" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Response</th>
                                                <td><input class="input" type="text" id="response" name="response" value="" size="40" disabled>
                                                    <input class="input" type="hidden" id="response_id" name="response_id" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Fixed Response Number</th>
                                                <td><input class="input" type="number" id="fixed_response" name="fixed_response" value="" style="width: 69%;" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Variable Response Number</th>
                                                <td><input class="input" type="number" id="variable_response" name="variable_response" value="" style="width: 69%;" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Bitwise Response Number</th>
                                                <td><input class="input" type="number" id="bitwise_response" name="bitwise_response" value="" style="width: 69%;" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Data Extract Type</th>
                                                <td><input class="input" type="text" id="data_extract_type" name="data_extract_type" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Format</th>
                                                <td><input class="input" type="text" id="format" name="format" value="" size="40" disabled >
                                                     </td>
                                            </tr>
                                          
                                            <tr>
                                                <th class="heading1">Remark</th>
                                                <td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                            </tr>


                                            <tr>
                                                <td align='center' colspan="2">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <%--       <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>  --%>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>

                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="active" id="active" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchCityType" value="${searchCityType}" >
                                            <input type="hidden"  name="searchDivisionType" value="${searchDivisionType}" >
                                        </table>
                                    </form>
                                </div>




                            </td>




                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>    

<form name="redirectFixedResponse" method="post" action="FixedResponseCont" target="_blank">
            <input type="hidden" id="fixed_response1" name="fixed_response1" value="">
            <input type="hidden" id="response1" name="response1" value="">
            <input type="hidden" id="response_id1" name="response_id1" value="">
</form>
<form name="redirectByte" method="post" action="ResponseByteDataController" target="_blank">
            <input type="hidden" id="bitwise2" name="bitwise2" value="">
            <input type="hidden" id="response_id2" name="response_id2" value="">
</form>

    </body>
</html>

