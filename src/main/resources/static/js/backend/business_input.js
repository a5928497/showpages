$(function () {
    //检查两次输入是否一致
    $password = $(".password");
    $pswConfirm = $(".pswConfirm");
    $pswConfirm.blur(function () {
       if ($password.val() != $pswConfirm.val()) {
            $(".pswTips").text("两次密码输入不一致，请重新输入").show();
            $(".submitBTN").attr("disabled","disabled");
       }else {
           $(".pswTips").hide();
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