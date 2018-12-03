$(function () {
    $details = $(".details_code");
    $introcude = $(".introduce_code");
    $details_url = $(".details_url");
    $introcude_url = $(".introduce_url");
    var details_url = $details.attr("src");
    var introduce_url = $introcude.attr("src");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $details_url.text(localhostPaht+details_url);
    $introcude_url.text(localhostPaht+introduce_url);
    details_url = "http://b.bshare.cn/barCode?site=weixin&url=" + localhostPaht + details_url;
    introduce_url = "http://b.bshare.cn/barCode?site=weixin&url=" + localhostPaht + introduce_url;
    $details.attr("src",details_url);
    $introcude.attr("src",introduce_url);

});