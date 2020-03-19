<%-- 
    Document   : BaseRoverConfgPage
    Created on : Jan 23, 2020, 5:57:56 PM
    Author     : rituk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%> 
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
  
    jQuery(function () {
        $("#manufacturer_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function () {
                    return "getManufactureName"
                }
            }
        });
        $("#device_type_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function () {
                    return "getDeviceTypeName"
                },
                action2: function () {
                    return  $("#manufacturer_name").val();
                }
            }
        });
        $("#device_name").autocomplete("DeviceRegistrationCont.do", {
            extraParams: {
                action1: function () {
                    return "getDeviceName"
                }
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
        
        $("#DGPS_operation").autocomplete("BaseRoverConfCont.do", {
            extraParams: {
                action1: function () {
                    return "getDGPSoperation"
                }
            }
        });
     
       $("#sub_operation").autocomplete("BaseRoverConfCont.do", {
            extraParams: {
                action1: function () {
                    return "getSubOperation"
                },
                action2: function () {
                    return  $("#DGPS_operation").val();
                },
                action3: function () {
                    
                    
                    return  $("#sub_op").val();
                }
               
            }
        });
        $("#baudrate").autocomplete("BaseRoverConfCont.do", {
            extraParams: {

                action1: function () {
                     
                    return "getBaudrate"
                }, action2: function () {
                    return  $("#DGPS_operation").val();
                }, action3: function () {
                    return  $("#manufacturer_name").val();
                }, action4: function () {
                    return  $("#device_type_name").val();
                }, action5: function () {
                    return  $("#device_name").val();
                }
                , action6: function () {
                    return  $("#device_no").val();
                }
                
            }
        });
         $("#CORRECTION_METHOD").autocomplete("BaseRoverConfCont.do", {
           
            extraParams: {

                action1: function () {
                    return "getComport"
                }, action2: function () {
                    return  $("#DGPS_operation").val();
                }, action3: function () {
                    return  $("#manufacturer_name").val();
                }, action4: function () {
                    return  $("#device_type_name").val();
                }, action5: function () {
                    return  $("#device_name").val();
                }
                , action6: function () {
                    return  $("#device_no").val();
                }
                
            }
        });
        
        $("#MASK_ANGLE").autocomplete("BaseRoverConfCont.do", {
            extraParams: {
 
                action1: function () {
                    return "getMaskangle"
                }, action2: function () {
                    return  $("#DGPS_operation").val();
                }, action3: function () {
                    return  $("#manufacturer_name").val();
                }, action4: function () {
                    return  $("#device_type_name").val();
                }, action5: function () {
                    return  $("#device_name").val();
                }
                , action6: function () {
                    return  $("#device_no").val();
                }
                
            }
        });


    });

//        $(function () {  
//              var DGPS_operation=document.getElementById("DGPS_operation").value;
//              
//              $("#sub_operation").autocomplete({
//                   
//            source: function (request, response) {
//                 
//                $.ajax({
//                    url: "BaseRoverConfCont.do?task=sub",
//                    dataType: "json",
//                    data: {action1: "getSubOperation",
//                            action2: DGPS_operation},
//                    success: function (data) {
//                        
//                        console.log(data);
//                     
//                         response(data.list);
//                    
//                    }, error: function (error) {
//                        console.log(error.responseText);
//                        response(error.responseText);
//                    }
//                });
//            },
//            select: function (events, ui) {
//                console.log(ui);
//                $('#sub_operation').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });
//   });       



//$(function () {  
//                
//           var DGPS_operation=document.getElementById("DGPS_operation").value;
//        
//              $("#sub_operation").autocomplete({
//                   
//            source: function (request, response) {
//                debugger;
//                $.ajax({
//                    url: "BaseRoverConfCont.do",
//                    dataType: "json",
//                    data: {action1: "getSubOperation",
//                           action2: DGPS_operation},
//                    success: function (data) {
//
//                        console.log(data);
//                        response(data.list);
//                    }, error: function (error) {
//                        console.log(error.responseText);
//                        response(error.responseText);
//                    }
//                });
//            },
//            select: function (events, ui) {
//                console.log(ui);
//                $('#sub_operation').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });
//    });
//
//
//
//










    function setStatus(id) {
        if (id == 'connection') {
            document.getElementById("clickedButton").value = "connection";
        } else if (id == 'send') {
            document.getElementById("clickedButton").value = "connection";
        }
         else if (id == 'check1') {
            document.getElementById("clickedButton").value = "check1";
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
    
//     $(function(){
//        $('#manufacturer_name').on(change{
//           alert("vallllll --"); 
//        });
//     });
    
    function myFunction(){debugger;
       
        var A=null ;
        
          debugger;
        var maskanglesize=null;
           var manufacturer_name=document.getElementById("manufacturer_name").value;
            var device_type_name=document.getElementById("device_type_name").value;
            var device_name=document.getElementById("device_name").value;
             var device_no=document.getElementById("device_no").value;
               var DGPS_operation=document.getElementById("DGPS_operation").value;
               var sub_operation=document.getElementById("sub_operation").value;
            
               $.ajax({url: "BaseRoverConfCont.do?task=testing",
                    //type: 'POST',
                    dataType: 'json',
                    //contentType: 'application/json',
                    //context: document.body,
                    
                    data: { mname: manufacturer_name, devicetype: device_type_name , device_name: device_name, device_no: device_no, DGPS_operation: DGPS_operation },

                    success: function(response_data)
                    {
                       
                        var data=response_data.data;
                        var common=response_data.common;                       
                        var size1=response_data.size; 
                        var input=response_data.input; 
                          var arr = []; 
                          var pnamearr=[];
                          var arr1=[];
                           var sizearr = [];
                           
                        for(var i=0;i<data.length;i++)
                        {  
                            
                              A = data[i].A;
                             // maskanglesize=data[i].dynamicsize;
                              
                             // alert(maskanglesize);
                             for (j = 0; j < A; j++) {			
                                var p = "parameters"+j;
 
                                var pa=data[i][p];

                                arr.push(pa);
                               
                              }
                              
                              
                              //
                              for (j = 0; j < A; j++) {			
                                var p = "parameters"+j;
 
                                var pa=data[i][p];

                                arr.push(pa);
                               
                              }
                              
                              
                              
                              
                               for (j = 0; j < A; j++) {			
                                var p = "dynamicsize"+j;
 
                                var pa=data[i][p];

                                sizearr.push(pa);
                               
                              }
                              
                               
                               
                              for(var m=0;m<sizearr.length;m++){
                        for (j = 0; j < sizearr[m]; j++) {			
                               var panme=data[i].pname;
                                arr1.push(panme);
                            
                              }
                             
                              }
  }
         
        
			 

                     var cell;
                     var cell1;
                     var cellText;
                        var arpanme=[];
                    // for(var i=0;i<A;i++){
                      for (var j = 0; j < A; j++) {
                        
                     var  row = document.createElement("tr");
                              
                         cell = document.createElement("td");
                         cell1 = document.createElement("th");
                         cell1.style= "bgcolor: #00a3cc";
                         cellText = document.createElement("td");  
                        
                       cellText = document.createTextNode(arr[j]);
                       cellText.style="bgcolor: #00a3cc;";
                       cell1.appendChild(cellText);
                       row.appendChild(cell1);
                       arpanme.push(arr[j]);
               var modeElement = document.createElement("select");
                   modeElement.style="width: 200px;";
                    
						 for(var k=0;k<size1[j].s;k++){
                                                     modeElement.name="v"+j;
                                                 var opt = document.createElement("option");
                                                 var op="check"+k;
                                                 var byte="byte"+k;
						opt.text = common[j][op];
						opt.value = common[j][byte];
						opt.size="70";
						modeElement.options.add(opt);
                                                 cell.appendChild(modeElement);
                                                     row.appendChild(cell);
                                                     
                                                      //document.getElementById("sel_value").value =v;
                                                     
                                                     
                                              var p = document.getElementById("table3");
                                                 p.appendChild(row);
                   } 
                     
                      
                     
                    
           }   document.getElementById("p_value").value =arpanme;
           
           //for inputs
           var inputarr=[];
           var in_valuearr=[];
            var inpsize;
             
            
            for(var i=0;i<input.length;i++)
                        {  
                            
                            inpsize= input[i].inpsize;
                            //alert(inpsize);
                              
           for (j = 0; j < inpsize; j++) {			
                                var p = "input"+j;
 
                                var pa=input[i][p];

                                inputarr.push(pa);
                               
                              }
         
        
        
        
        }
                          
        
        
           // var elementHiddenl = document.createElement("input");
                
               
                    
                 
                    for (var j = 0; j < inpsize; j++) {
                        var row = document.createElement("tr");
                        var cell = document.createElement("td");
                         var cell1 = document.createElement("th");
                          var cellText = document.createElement("td");  
                       
                             
                         cellText = document.createTextNode(inputarr[j]);
                       
                        cell1.appendChild(cellText);
                        row.appendChild(cell1);
                       in_valuearr.push(inputarr[j])
                        var element1 = document.createElement("input");
                        element1.type = "text";
                        element1.id = inputarr[j];
                        element1.name = "va"+j;
                        element1.value = "";
                        element1.size = 40;
                        
                        cell.appendChild(element1);
                            row.appendChild(cell);
                               var p = document.getElementById("table4");
                              p.appendChild(row);
                         }
                    document.getElementById("in_value").value =in_valuearr;
              
              
                    }
                         
                });
       
    } 
    
      function subFunction(){
        
           var DGPS_operation=null;
                DGPS_operation=document.getElementById("DGPS_operation").value;
             var a=DGPS_operation;
             
               var sub_operation=document.getElementById("sub_op").value;
                
                $.ajax({url: "BaseRoverConfCont.do?task=sub",
                   
                    dataType: 'json',
                    
                    data: {DGPS_operation: DGPS_operation,sub_operation:sub_operation },
                        success: function(response_data)
                    {
                          debugger; 
                        var data1=response_data.data1;
                        var arr123=[];
                          var arrid=[];
                    var pa;
                        for(var i=0; i<data1.length;i++){
                              var s=data1[i].ischild;
                              //alert(s);
                             if(s == "on"){
                                // alert("IT IS Superchild No More Selectons");
                                 myFunction();
                            }else
                            { 
                                
                                 alert("!!!Please Select SuperChild If Available ");
                           
                                
                            }
                              
                            }
                             // alert(arr123);
                            
                            debugger;
//                            for(var a=0;a<arr123.length;a++){
//                               var aa=arr123[a];
//                              // alert("aaaaa"+aa);
//                            
//                        }
                            
                            
                            
                      
                    }  
                   
                });
                debugger;
                 var a1=document.getElementById("DGPS_operation").value=sub_operation;
                       a=a1;
              
    }
    
    
    function checkFn(){
        var check=document.getElementById("sub_op").value;
      //alert("iiiiiiiiiiiii"+check);
      //  alert("in sus susb");
    }
    
 
        
     
        
        
        

</script>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="subFunction()">
       
        
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">Base / Rover Configuration </td>

                                    </tr>
                                </table>
                            </td></tr>

                        <tr>
                        
                            <td align="center">
                                <div id="new">
                                    <form name="form2" method="POST" action="BaseRoverConfCont.do">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                          <div id="result"></div>
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
                                                <th class="heading1">DGPS Operation</th>
                                                <td><input class="input" type="text" id="DGPS_operation" name="DGPS_operation" value="${DGPS_operation}" size="40" ></td>
                                                
                                                
                                            </tr>

                                            <tr>
                                                <th class="heading1">Sub Operation</th>
                                                <td>
                                                  <input class="input" type="text" id="sub_operation" name="sub_operation" value=" " size="40" >
 
                                                </td>
                                            </td><td style="border: none"><input class="button" type="submit"  name="task" id="check1" value="check1" onclick="checkFn()" style="border: none;"></td>
                                            </tr>
                           <tr>
                                               
                               <td><input class="input" type="hidden" id="sub_op" name="sub_op" value="${subop}" size="40"> 
                                            </tr>
                                            
                                            <table id="table3"  class="content" border="0"  align="center" width="600">
                                                
                                                
                                                
                                                
                                            </table>
                                           <table id="table4"  class="content" border="0"  align="center" width="600">
                                                
                                                
                                                
                                                
                                            </table>
                                                
                                                
                                                    
                                               
                                                   
                                             
                                          
                                            <tr>
                                                <td align='center' colspan="2">
                                                    <input class="button" type="submit" name="task" id="connection" value="connection" onclick="setStatus(id)">
                                               
                                               
                                                </td><td>${no}</td>
                                            </tr>
                                      
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="active" id="active" value="">
                                             <input type="hidden" name="size" id="size" value="">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" id="p_value" name="p_value" value="">
                                             <input type="hidden" id="in_value" name="in_value" value="">
<!--                                              <input type="hidden" id="sub_op" name="sub_op" value="">-->
                                             <input type="hidden" id="sel_value" name="sel_value" value="">
                                             </table>
                                        </table>
                                            
                                           
                                   
                                </div>
                            </td>
                        </tr>
   
                          <tr>
                   
                </tr>                  
                    </table>
  </form>
                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
