<%-- 
    Document   : index
    Created on : Dec 31, 2018, 3:32:58 PM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function goToDeviceMap() {
                window.location = "ModelTypeCont.do";
            }
             function goToDeviceMap1() {
                window.location = "CommandCont.do";
            }
             function goToDeviceMap2() {
                window.location = "BleOperationNameCont.do";
            }
        </script>
        <style>           .button {
  display: inline-block;
  border-radius: 4px;
  background-color:#802900;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 15px;
  padding: 5px;
  width: 200px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 15px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}
   
a:hover{
  background-color: yellow;
}
        </style>
    </head>
    <body>
        <table align="center" class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
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
                            <tr>
                                <td align="center">
                                    <button  class="button" style="vertical-align:middle" type="button" value="Create Finished Device" name="create_finished_device" onclick="goToDeviceMap()" ><span> Finished</span></button>
                                   
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <button  class="button" style="vertical-align:middle" type="button" value="Create Module Device" name="create_module_device" onclick="goToDeviceMap1()" ><span>Module</span></button>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <button class="button" style="vertical-align:middle" type="button" value="Create Ble Device" name="create_commands_device" onclick="goToDeviceMap2()" ><span> Ble </span></button>
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

