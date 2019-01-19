<%--
    Document   : existingEstimate
    Created on : Dec 16, 2013, 3:29:53 PM
    Author     : Ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript" src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
      
    </head>
    <body onload="checkClickedColumnId();">
        <table align="center"  class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td nowrap>
                    <DIV id="div_viewQtList" class="maindiv"  style="height: 650px; width: 900px" >
                        <table width="100%" >
                            <tr>
                                <td align="center">
                                    <h3>  Welcome</h3>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
