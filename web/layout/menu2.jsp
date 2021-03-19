<%--
    Document   : menu2
    Created on : Dec 5, 2020, 11:10:58 AM
    Author     : saini
--%>


<script src="https://kit.fontawesome.com/64d58efce2.js"
crossorigin="anonymous"></script>
<link rel="stylesheet" href="assets/css/style.css">
<header>
    <div class="container">
        <input type="checkbox" name="" id="check">

        <div class="logo-container">
            <div class="logo">
                <a href="http://apogeeprecision.com" target="_blank"><img
                        src="images/logo.jpg" /></a>
            </div>
        </div>

        <%-- <c:forEach var="entry" items="${classsectionMap}">
                <option>${entry.key}</option>
                <c:forEach var="val" items="${entry.value}">
                        <option>${val }</option>
                </c:forEach>
        </c:forEach> --%>

        <div class="nav-btn">
            <div class="nav-links" style="margin-top: 10px;">
                <ul>
                    <li class="nav-link" style="-i: .6s"><a href="/BLE_ProjectV6_2">Home</a>
                    </li>
                    <li class="nav-link" style="-i: 1.1s"><a href="#">Modules<i
                                class="fas fa-caret-down"></i></a>
                        <div class="dropdown">
                            <ul>
                                <li class="dropdown-link"><a href="AddModulesController">Add
                                        Modules</a></li>
                                <!--								<li class="dropdown-link"><a
                                                                                                        href="">Shift Designation
                                                                                                                Map</a></li>								-->
                            </ul>
                        </div></li>


                    <li class="nav-link" style="-i: 1.1s"><a href="#">Devices<i
                                class="fas fa-caret-down"></i></a>
                        <div class="dropdown">
                            <ul>
                                <li class="dropdown-link"><a href="DeviceController">Add
                                        Devices</a></li>
                                <!--								<li class="dropdown-link"><a
                                                                                                        href="">Shift Designation
                                                                                                                Map</a></li>								-->
                            </ul>
                        </div></li>

                    <li class="nav-link" style="-i: 1.1s"><a href="#">Data Entry<i
                                class="fas fa-caret-down"></i></a>
                        <div class="dropdown">
                            <ul>
                                <li class="dropdown-link"><a href="OperationNameController">Operation Name
                                    </a></li>
                                <li class="dropdown-link"><a href="CommandTypeController">Command
                                        Type</a></li>
                                <li class="dropdown-link"><a href="CommandController">Command
                                        Detail</a></li>
                                <li class="dropdown-link"><a href="ManufacturerController">Manufacturer
                                        Detail</a></li>
                                <li class="dropdown-link"><a href="DeviceTypeController">Device
                                        Type</a></li>
                                <li class="dropdown-link"><a href="ModelController">Add
                                        Model</a></li>
                                <li class="dropdown-link"><a href="ModelTypeController">Model
                                        Type</a></li>
                                <li class="dropdown-link"><a href="DeviceMapController">Device
                                        Map</a></li>
                                <li class="dropdown-link"><a href="DevOpComMapController">Device Operation Command
                                        Map</a></li>
                                <li class="dropdown-link"><a href="ParameterNameController">Parameter
                                    </a></li>
                            </ul>
                        </div></li>

                    <!--					<li class="nav-link" style="-i: .6s"><a href="dashboard.jsp">Dashboard</a>
                                                            </li>
                                                            <li class="nav-link" style="-i: .85s"><a
                                                                    href="AttendanceController">Attendance<i
                                                                            class="fas fa-caret-down"></i></a></li>
                                                            <li class="nav-link" style="-i: 1.1s"><a href="#">Device
                                                                            Registrations<i class="fas fa-caret-down"></i>
                                                            </a>
                                                                    <div class="dropdown">
                                                                            <ul>
                                                                                    <li class="dropdown-link"><a href="DeviceInfoRegController">Device
                                                                                                    Info</a></li>
                                                                                    <li class="dropdown-link"><a href="DeviceRegController">RFID
                                                                                                    Allocation</a></li>
                                                                                    <div class="arrow"></div>
                                                                            </ul>
                                                                    </div></li>
                    
                                                            <li class="nav-link" style="-i: 1.1s"><a href="#">Shift<i
                                                                            class="fas fa-caret-down"></i></a>
                                                                    <div class="dropdown">
                                                                            <ul>
                                                                                    <li class="dropdown-link"><a href="ShiftController">Shift
                                                                                                    Type</a></li>
                                                                                    <li class="dropdown-link"><a
                                                                                            href="ShiftDesinationLocationController">Shift Designation
                                                                                                    Map</a></li>
                                                                                    <li class="dropdown-link"><a
                                                                                            href="ShiftKeyPersonMapController">Shift Key Person Map</a></li>
                                                                                    <li class="dropdown-link"><a href="ShiftTimeController">Shift
                                                                                                    Time</a></li>
                                                                                    <li class="dropdown-link"><a href="AttendenceController">Attendance</a></li>
                                                                                    <li class="dropdown-link"><a
                                                                                            href="AttendenceViewController">Shift Attendance detail</a></li>
                                                                                    <li class="dropdown-link"><a href="shiftReasonCont.do">Shift
                                                                                                    Reason</a></li>
                                                                            </ul>
                                                                    </div></li>
                    
                                                            <li class="nav-link" style="-i: 1.35s"><a
                                                                    href="ims_home">Time Table</a></li>
                                                            <li class="nav-link" style="-i: 1.35s"><a
                                                                    href="TempDashController">Status</a></li>
                                                            <li class="nav-link" style="-i: 1.35s"><a href="#">Policies</a>
                                                            </li>
                                                            <li class="nav-link" style="-i: 1.35s"><a href="#">About</a></li>-->
                </ul>
            </div>

            <div class="log-sign" style="-i: 1.8s">
                <a href="#" class="btn transparent">Log in</a> <a href="#"
                                                                  class="btn solid">Sign up</a>
            </div>
        </div>

        <div class="hamburger-menu-container">
            <div class="hamburger-menu">
                <div></div>
            </div>
        </div>
    </div>
</header>
