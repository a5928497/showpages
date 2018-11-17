$(function () {
    //检查登录账号是否唯一
    $username = $(".username");
    $accTips = $(".accTips");
    $accTipsCon = $(".acc-tips-container");
    $accTipsCon.hide();
    $username.blur(function () {
        var username = $username.val();
        $.get("/acccheck",{username:username},function (data) {
            if (data == false) {
                $accTipsCon.show();
                $(".accTips").text("该账号已被使用，请重新设置");
                $(".submitBTN").attr("disabled","disabled");
            }else {
                $accTipsCon.hide();
                $(".submitBTN").removeAttr("disabled");
            }
        })
    });

    //检查两次输入是否一致
    $password = $(".password");
    $pswConfirm = $(".pswConfirm");
    $pswTipsCon = $(".psw-tips-container");
    $pswTipsCon.hide();
    $pswConfirm.blur(function () {
       if ($password.val() != $pswConfirm.val()) {
            $pswTipsCon.show();
            $(".pswTips").text("两次密码输入不一致，请重新输入");
            $(".submitBTN").attr("disabled","disabled");
       }else {
           $pswTipsCon.hide();
           $(".submitBTN").removeAttr("disabled");
       }
    });
    //返回按钮事件
    $backBTN = $(".backBTN");
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    })
});