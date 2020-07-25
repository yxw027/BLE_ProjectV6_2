<%-- 
    Document   : selection_command
    Created on : 19 Sep, 2019, 4:17:59 PM
>    Author     : apogee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/jquery-ui.min.js"></script>

    </head>
    <c:choose>        
        <c:when test="${fn:length(fixedResponseListById) > 0}">            
            <body>
        </c:when>        
        <c:otherwise>
        <body onload="addRow('dataTable',${fixed_response},'${response}')">
        </c:otherwise>
    </c:choose>

        <table cellspacing="0" border="0" id="table0"  align="center" width="60%">
              <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td>
                    "${response}" Fixed Response Details
                </td>
            </tr>
            
            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td style="padding-top: 13px;">
                    <form name="form1" id ="form1" action="FixedResponseCont" method="post" >
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading">Response</th>
                                    <th class="heading">Parameter</th>                                    
                                    <th class="heading" >Fixed Response Value No</th>
                                    <th class="heading">Start Pos</th>
                                    <th class="heading">No of Byte</th>
                                    <th class="heading">Remark</th>
                                     
                                    
                                </tr>
                                <c:forEach var="list" items="${requestScope['fixedResponseListById']}" varStatus="loopCounter">
                                    <tr>
                                        <td><input type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="5" value="${loopCounter.count}" readonly>
                                        <input type="hidden" name="fixed_response_id${loopCounter.count}" maxlength="8" size="5" id="fixed_response_id${loopCounter.count}" value="${list.fixed_response_id}">
                                        <input type="hidden" name="count" maxlength="8" size="5" id="count" value="${loopCounter.count}"></td>
                                        <td><input type="text" name="response${loopCounter.count}" maxlength="50" size="80" id="response${loopCounter.count}" value="${list.response}" readonly></td>
                                        <td><input type="text" name="parameter${loopCounter.count}" maxlength="8" size="15" id="parameter${loopCounter.count}" value="${list.parameter}" onkeyup="autocompleteMethod('parameter',${loopCounter.count}+1)"></td>
                                       
                                        <td><input type="text" name="fixed_response_value_no${loopCounter.count}" maxlength="50" size="5" id=" fixed_response_value_no${loopCounter.count}" value="${list. fixed_response_value_no}" onkeyup="check(value)">                                         
                                        <a href="#" onclick="inputPopup('FixedResponseValueCont','${list.parameter}',${list.fixed_response_value_no},${list.fixed_response_id});return false" id="input_button">View Fixed Response Value</a></td>
                                        <td><input type="text" name="start_pos${loopCounter.count}" maxlength="50" size="80" id="start_pos${loopCounter.count}" value="${list.start_pos}"></td>
                                        <td><input type="text" name="no_of_byte${loopCounter.count}" maxlength="50" size="80" id="no_of_byte${loopCounter.count}" value="${list.no_of_byte}"></td>
                                        <td><input type="text" name="remark${loopCounter.count}" maxlength="8" size="20" id="remark{loopCounter.count}" value="${list.remark}" ></td>
<!--                                           <td><input type="submit" class="button" name="task"  value="update"></td>-->
<!--                                      
                                        <td><input type="submit" class="button" name="task"  value="update" ></td>
<!--                                                                        -->
                                    </tr>
                                    
                                </c:forEach>
                       
                            <input  type="hidden" name="fixed_response1" value="${fixed_response}" >
                            <input  type="hidden" name="response" id="response" value="${response}" >
                            <input  type="hidden" name="response_id1" id="response_id" value="${response_id}">
                            
                            </tbody>
                        </table>
                            <div style="padding-top: 10px;" align="center">
                                <input class="button" type="submit" id="save" name="task" value="Save">
                            </div>
                    </form>
                </td>
            </tr>
        </table>
                            
        <script type="text/javascript" language="javascript">

       var fixed_response_value_no_update;
            function autocompleteMethod(id, count) {
                debugger;
                if (id === "response") {
                    $("#response" + count).autocomplete("FixedResponseCont", {
                        extraParams: {
                            action1: function () {
                                return "getCommand";
                            }
                        }
                    });
                } else if (id === "parameter") {
                    $("#parameter" + count).autocomplete("FixedResponseCont", {
                        extraParams: {
                            action1: function () {
                                return "getParameter";
                            }
                        }
                    });

                } else if (id === "parameter_type") {
                    $("#parameter_type" + count).autocomplete("FixedResponseCont", {
                        extraParams: {
                            action1: function () {
                                return "getParameterType";
                            }
                        }
                    });
                }

            }

            
            function check(fixed_response_value_no){
                debugger;
              fixed_response_value_no_update = fixed_response_value_no;               
            }
                
          

            function addRow(tableID, selection_no, command) {
                debugger;


                $("#message").html("");
                var table = document.getElementById(tableID);

                for (var i = 1; i <= selection_no; i++) {
                    var row = table.insertRow(i);


                    var cell1 = row.insertCell(0);
                    var element1 = document.createElement("input");
                    element1.type = "text";
                    element1.id = "s_no" + i;
                    element1.name = "s_no" + i;
                    element1.value = i;
                    element1.size = 3;
                    cell1.appendChild(element1);

                    var cell2 = row.insertCell(1);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "response" + i;
                    element2.id = "response" + i;
                    element2.size = 100;
                    element2.maxLength = 2;
                    element2.value = command;
                    element2.setAttribute("onkeyup", 'autocompleteMethod("response",' + i + ')');
                    cell2.appendChild(element2);

                    var cell3 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "parameter" + i;
                    element2.id = "parameter" + i;
                    element2.size = 15;
                    element2.maxLength = 8;
                    element2.value = "";
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter",' + i + ')');
                    cell3.appendChild(element2);

                    var cell4 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "fixed_response_value_no" + i;
                    element2.id = "fixed_response_value_no" + i;
                    element2.size = 15;
                    element2.maxLength = 50;
                    element2.value = "";
                    cell4.appendChild(element2);

                    var cell5 = row.insertCell(4);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "start_pos" + i;
                    element2.id = "start_pos" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter_type",' + i + ')');
                    cell5.appendChild(element2);

                    var cell6 = row.insertCell(5);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "no_of_byte" + i;
                    element2.id = "no_of_byte" + i;
                    element2.size = 15;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell6.appendChild(element2);
                    
                    var cell7 = row.insertCell(6);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "remark" + i;
                    element2.id = "remark" + i;
                    element2.size = 15;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell7.appendChild(element2);

                }


            }
   function inputPopup(url,parameter,fixed_response_value_no,fixed_response_id) {
        debugger;
        var popup_height = 580;
        var popup_width = 900;
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        url = url + "?parameter="+parameter+"&fixed_response_value_no="+fixed_response_value_no+"&fixed_response_id="+fixed_response_id;
        alert(url);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        popupWindow = window.open(url, 'Fixed Response Window', window_features);
    }
            function deleteRow(tableID) {
                try {
                    // alert(tableID);
                    var table = document.getElementById(tableID);
                    var rowCount = table.rows.length;
                    var delete_palan_row = 0;
                    for (var i = 0; i < rowCount; i++) {
                        var row = table.rows[i];
                        //  alert(row);
                        var chkbox = row.cells[0].childNodes[0];
                        //     alert(chkbox);
                        if (null != chkbox && true == chkbox.checked || k < rowCount) {
                            // alert(i);
                            var k = i;
                            for (k = i; k < rowCount; k++) {
                                table.deleteRow(i);
                                delete_palan_row++;
                                //  alert(delete_palan_row);
                                // --rowCount;
                                //setSequential(--rowCount, i);
                                // i--;
                                document.getElementById("no_of_plans").value = document.getElementById("no_of_plans").value - 1;
                            }
                        }
                    }
                    // alert("fdfdfd");
                    //document.getElementById("no_of_plans").value =(parseInt(rowCount)-parseInt(delete_palan_row));
                } catch (e) {
                    // alert(e);
                }
            }

            function verify() {
    //        var data = {
    //            command_name: document.getElementById(command_name),
    //            parameter:document.getElementById(parameter),
    //            parameter_value:document.getElementById(parameter_value),
    //            parametere_type:document.getElementById(parametere_type),
    //            remark:document.getElementById(remark)
    //        };
                debugger;
                var form = document.getElementById("form1");
                var dat = form.data;


            }






        </script>
<style>
a:hover{
  background-color: yellow;
}</style>
    </body>
</html>