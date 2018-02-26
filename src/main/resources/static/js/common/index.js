$(document).ready(function () {
    function getRootmenu() {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/mdm/loadMeun",
            success: function (data) {
                var m = "";
                if (data.length > 0) {
                    for (j = 0; j < data.length; j++) {
                        if (data[j].parentMeunId == 1) {
                                m = m + "<li roletype='2' id='" + data[j].meunId + "' class=''>" +
                                    "<a  href='" + data[j].url + "'>" +  "<i class='menu-icon fa "+data[j].meunStyle+"' >" + "</i>" +"<span class='menu-text'>"+ data[j].meunName+"</span> "+ "</a></li>";
                        }
                    }
                }
                $('#ulMenu').html(m);
                $("#ulMenu li").bind('mouseover', function (e) {
                    getSonMenu(this, e)
                });
            }
        });
        }
        getRootmenu();


        function getSonMenu(event) {
        var parentId = $(event).attr("id");
        var roletype = $(event).attr("roletype");
        if (roletype == "2")
            $(event).addClass("hover open");
        if (roletype == "1")
            $(event).addClass("open");
        if ($(event).find("li").length > 0 || roletype == "0") return;
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/common/getSonMenu?parentId=" + parentId,
            success: function (result) {
                var parentId = $(event).attr("id");
                $('#' + parentId).html($('#' + parentId + ' >a'))
                // $('#'+parentId).html("");
                var a = "<ul class='dropDown-menu menu radius box-shadow'>";
                var b = "</ul>";
                var c = "";
                $.each(result, function (i) {
                    if(result[i].isnewblank == 1 && result[i].ishas_son == 1)
                    {
                        c = c + "<li roletype='" + result[i].ishas_son + "' id=" + result[i].node_id + "><a href='" + result[i].node_url + "' target='_blank'>" + result[i].node_showname + "<i class='arrow Hui-iconfont'></i></a></li>";
                    }
                    if(result[i].isnewblank == 1 && result[i].ishas_son == 0){
                        c = c + "<li roletype='" + result[i].ishas_son + "' id=" + result[i].node_id + "><a href='" + result[i].node_url + "' target='_blank'>" + result[i].node_showname + "</a></li>";
                    }
                    if(result[i].isnewblank == 0 && result[i].ishas_son == 1){
                        c = c + "<li roletype='" + result[i].ishas_son + "' id=" + result[i].node_id + "><a href='" + result[i].node_url + "'>" + result[i].node_showname + "<i class='arrow Hui-iconfont'></i></a></li>";
                    }
                    if(result[i].isnewblank == 0 && result[i].ishas_son == 0){
                        c = c + "<li roletype='" + result[i].ishas_son + "' id=" + result[i].node_id + "><a href='" + result[i].node_url + "'>" + result[i].node_showname + "</a></li>";
                    }
                });
                $('#' + parentId).append(a + c + b);
                $("#ulMenu li").unbind('mouseover');
                $("#ulMenu li").bind('mouseover', function (e) {
                    getSonMenu(this, e)
                });
            }, error: function (result) {
                alert(result.responseText);
            }
        });
    }

});