$(function () {
    $joinBTN = $(".joinBTN");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $wrapper = $("#main_wrapper");
    $footer = $(".footer_wrapper");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/welcome_pg/background.jpg");
    $joinBTN.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/welcome_pg/join_btn.jpg");
    $footer.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/welcome_pg/footer.jpg");

    $joinBTN.click(function () {
        window.location.href= localhostPaht + "/details/" + $("#businessName").val();
        return false;
    });
})