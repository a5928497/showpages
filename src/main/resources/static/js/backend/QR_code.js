$(function () {
    $details = $(".details_code");
    $introcude = $(".introduce_code");
    var details_url = $details.attr("src");
    var introduce_url = $introcude.attr("src");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    details_url = "http://b.bshare.cn/barCode?site=weixin&url=" + localhostPaht + details_url;
    introduce_url = "http://b.bshare.cn/barCode?site=weixin&url=" + localhostPaht + introduce_url;
    $details.attr("src",details_url);
    $introcude.attr("src",details_url);
});