/**
 * Created by xujunfei on 2017/5/11.
 */
!(function ($) {
    $.ajaxSetup({cache: true});
    $.fn.Address = function (settings) {
        var box = this;
        var level = settings.level; //2-省市 3-省市区 4-省市区街道
        var a = settings.prov, b = settings.city, c = settings.area, d = settings.street;
        var db;

        var sel = "<select class='form-control' name='prov' id='prov'></select>" + "<select class='form-control' name='city' id='city'></select>";
        if (level == 3 || level == 4) {
            sel += "<select class='form-control' name='area' id='area'></select>";
        }
        if (level == 4) {
            sel += "<select class='form-control' name='street' id='street'></select>";
        }
        box.append(sel);

        var prov = "<option value=''>--省--</option>";
        $.getJSON("static/library/plugins/jf-city-picker/address.json", function (data) {
            db = data.RECORDS;
            $.each(db, function (k, v) {
                if (v.parent == 0) {
                    if (a != null && v.id == a) {
                        prov += "<option value='" + v.id + "' selected>" + v.name + "</option>";
                    } else {
                        prov += "<option value='" + v.id + "'>" + v.name + "</option>";
                    }
                }
            });
            $("#prov").html(prov);
            $("#prov").change();
        });

        box.on("change", "#prov", function () {
            var id = $("#prov option:selected").val();
            var city = "<option value=''>--市--</option>";
            if (id != "") {
                $.each(db, function (k, v) {
                    if (v.parent == id) {
                        if (b != null && v.id == b) {
                            city += "<option value='" + v.id + "' selected>" + v.name + "</option>";
                        } else {
                            city += "<option value='" + v.id + "'>" + v.name + "</option>";
                        }
                    }
                });
            }
            $("#area,#street").html('');
            $("#city").html(city);
            $("#city").change();
        });


        if (level == 3 || level == 4) {
            box.on("change", "#city", function () {
                var id = $("#city option:selected").val();
                var area = "<option value=''>--县区--</option>";
                if (id != "") {
                    $.each(db, function (k, v) {
                        if (v.parent == id) {
                            if (c != null && v.id == c) {
                                area += "<option value='" + v.id + "' selected>" + v.name + "</option>";
                            } else {
                                area += "<option value='" + v.id + "'>" + v.name + "</option>";
                            }
                        }
                    });
                }
                $("#street").html('');
                $("#area").html(area);
                $("#area").change();
            });
        }

        if (level == 4) {
            box.on("change", "#area", function () {
                var id = $("#area option:selected").val();
                var street = "<option value=''>--街道--</option>";
                if (id != "") {
                    $.each(db, function (k, v) {
                        if (v.parent == id) {
                            if (d != null && v.id == d) {
                                street += "<option value='" + v.id + "' selected>" + v.name + "</option>";
                            } else {
                                street += "<option value='" + v.id + "'>" + v.name + "</option>";
                            }
                        }
                    });
                }
                $("#street").html(street);
            });
        }

    }
})(jQuery);
