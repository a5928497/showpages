$(function () {
    //全局计数变量
    var key_count =1;
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $wrapper = $("#main_wrapper");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/details_pg/background.jpg");
    // $joinBTN.css("background-image","url("+ localhostPaht +"/welcomeImg/" + $("#businessName").val() + "/welcome_pg/join_btn.jpg");
    
    function addShortText(title) {
        var result = "<div class=\"shortText\">\n" +
            "            <label>"+ title + "</label>\n" +
            "            <input type=\"text\" name=\"key"+ key_count + "\">\n" +
            "        </div>";
        key_count++;
        return result
    }
    
    function addLongText(title) {
        var result = "<div class=\"longText\">\n" +
            "                    <p></p>\n" +
            "                    <textarea name=\"key"+ key_count +"\"></textarea>\n" +
            "                </div>";
        key_count++;
        return result;
    }

    function addRaios(selector,parentSelector) {
        $selector = $(selector);
        var options = $selector.attr("condition").split(",");
        var result = "<div class=\"radioContent\">\n";
        for (var i = 0;i<options.length  ;i++) {
            result = result + addSingleRadio()
        }
    }

    function addSingleRadio(title,value) {
        var result = "<label><input name=\"key"+ key_count +"\" type=\"radio\" value=\""+ value+"\">" + title +"</label>\n";
        key_count++;
        return result;
    }
})