$(function () {
    $backBTN = $(".backBTN");
    $fileinput = $(".fileinput");
    $fileinput.css("width",300);
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    });

    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var img_width;
    var img_height;
    //根据路径获取图片
    $sizeTips = $("#size_tips");
    $prevImg = $("#prevImg");
    $themeImg = $("#theme_img");
    var img_url  = localhostPaht + "/welcomeImg/";
    if ($prevImg.length != 0) {
        $("option[value='"+ $prevImg.val()+"']").attr("selected","selected");
    }
    $themeImg.attr("src",img_url+$(":selected").val()+"?"+Math.random())
        .load(function () {
            img_width = $(this)[0].naturalWidth;
            img_height = $(this)[0].naturalHeight;
            $sizeTips.text("原始尺寸（像素）：宽" + img_width + "px，高" + img_height + "px");
            $(this).css("width",resizeImg(img_width));

        });

    //切换选项时切换图片
    $("select").change(function () {
        $("option[selected='selected']").removeAttr("selected");
        $themeImg.removeAttr("style")
            .attr("src",img_url+$(":selected").val()+"?"+Math.random())
            .load(function () {
                img_width = $(this)[0].naturalWidth;
                img_height = $(this)[0].naturalHeight;
                $sizeTips.text("原始尺寸（像素）：宽" + img_width + "px，高" + img_height + "px");
                $(this).css("width",resizeImg(img_width));
            });
    });
    //调整图片函数
    function resizeImg(width) {
        if (width > 320) {
            return 320;
        }else {
            return width;
        }
    }
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