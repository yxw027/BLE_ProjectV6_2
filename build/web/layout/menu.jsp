<%--
    Document   : index
    Created on : Jan 7, 2013, 3:26:07 PM
    Author     : Neha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>

<html>
    <head>
        <title>Menu</title>
        <script type="text/javascript">
            $(function() {
                if ($.browser.msie && $.browser.version.substr(0,1)<7)
                {
                    $('li').has('ul').mouseover(function(){
                        $(this).children('ul').show();
                    }).mouseout(function(){
                        $(this).children('ul').hide();
                    })
                }
            });
        </script>

        <link type="text/css" href="style/menu.css" rel="stylesheet"/>



    </head>
    <body>
<!--        <div>
            <ul id="menu">
                <li><a href="#">Data Entry</a>
                    <ul>
                        <li><a href="ManufacturerCont.do">Manufacturer</a></li>
                        <li><a href="DeviceTypeCont.do">Device Type</a></li>
                        <li><a href="ModelTypeCont.do">Model Type</a></li>
                        <li><a href="ModelCont.do">Model</a></li>
                        <li><a href="CommandTypeCont.do">Command Type</a></li>
                        <li><a href="OperationNameCont.do">Operation Name</a></li>
                        <li><a href="BleOperationNameCont.do">Ble Operation Nmae</a></li>
                        <li><a href="DeviceOprtnChartstcMapCont.do">Device Character Operation map</a></li>
                    </ul>

                </li>
                
                <li><a href="#">Operation</a>
                    <ul>
                        <li><a href="CommandCont.do">Command</a></li>
                        <li><a href="ruleCont.do">Rules</a></li>
                    </ul>

                </li>
                <li><a href="#">Device</a>
                    <ul>
                        <li><a href="DeviceCont.do">Device</a></li>
                        <li><a href="DeviceRegistrationCont.do">Device Registration</a></li>
                    </ul>

                </li>
            </ul>
        </div>-->

<div>
            <ul id="menu">
                <li><a href="#">Device</a>
                    <ul>
                        <li><a href="#">Data Entry</a>
                        <ul>
                        <li><a href="ManufacturerCont.do">Manufacturer</a></li>
                        <li><a href="DeviceTypeCont.do">Device Type</a></li>
                        <li><a href="ModelTypeCont.do">Model Type</a></li>
                        <li><a href="ModelCont.do">Model</a></li>
                        <li><a href="ServiceCont.do">Services</a></li>
                        <li><a href="CharachtristicsCont.do">Characteristic</a></li>
                        </ul>
                        </li>
                        <li><a href="DeviceCont.do">Device</a></li>
                        <li><a href="DeviceRegistrationCont.do">Device Registration</a></li>
                    </ul>
                </li>

                <li><a href="#">Module</a>
                    <ul>
                        <li><a href="#">Data Entry</a>
                        <ul>
                        <li><a href="CommandTypeCont.do">Command Type</a></li>
                        <li><a href="OperationNameCont.do">Operation Name</a></li>
                        <li><a href="ruleCont.do">Rules</a></li>
                        </ul>
                        </li>
                        <li><a href="CommandCont.do">Command</a></li>
                    </ul>
                </li>

                <li><a href="#">BLE</a>
                    <ul>
                        <li><a href="#">Data Entry</a>
                            <ul>
                        <li><a href="BleOperationNameCont.do">Ble Operation Nmae</a></li>
                        </ul>
                       </li>
                <li><a href="DeviceOprtnChartstcMapCont.do">Device Character Operation map</a></li>
            </ul>
            </ul>
        </div>

    </body>
</html>