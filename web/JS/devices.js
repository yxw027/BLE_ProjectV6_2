/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $(document).on('keydown', '.myAutocompleteClass', function () {
        var id = this.id;
        //alert("iddd -"+id);
        var type;
        if (id.match("^manufacturer_name_module")) {
            type = "manufacturer_name";
        } else if (id.match("^device_type_module")) {
            type = "device_type";
        } else if (id.match("^model_name_module")) {
            type = "model_name";
        }
        //alert("type --"+type);


        var random = this.value;
        $('#' + id).autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "DeviceController",
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
                $(this).val(ui.item.label); // display the selected text
                return false;
            }
        });
    });

});