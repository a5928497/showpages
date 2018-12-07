$(function () {

    $backBTN = $(".backBTN");
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    });

    //调整背景颜色
    $switch = $("#bgc_switch");
    var flag = true;
    $switch.click(function () {
        flag = !flag;
        if (flag) {
            $("#img_container").css("background","red");
            $switch.text("关闭背景颜色");
        }else {
            $("#img_container").removeAttr("style");
            $switch.text("开启背景颜色");
        }
    });
});