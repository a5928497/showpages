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
    $submitBtn = $("#submitBtn");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/details_pg/background.jpg");
    $submitBtn.css("background-image","url("+ localhostPaht +"/themeImg/" + $("#businessName").val() + "/details_pg/submit_btn.jpg");

    //生成各个选项
    $fields = $(".fields");
    if ($fields.length > 0) {
        $fields.each(function () {
            addSwitch($(this).attr("field_type"),$(this));
        });
        // $("body").css("height",resizeBackground());
    }

    //提交按钮事件监听
    $submitBtn.click(function () {
        $.ajax({
            type: "POST",   //提交的方法
            url:"/result", //提交的地址
            data:$("form").serialize(),// 序列化表单值
            async: false,
            error: function (request) {
                alert("连接超时！");
            },
            success: function (data) {
                if (data == true) {
                    alert("提交成功！");
                }else {
                    alert("提交失败，请重试！");
                }
            }
        });
        return false;
    });

    function resizeBackground() {
        return $(".conteng_wrapper").position().top + $(".conteng_wrapper").height();
    }

    //根据类型执行相应生成函数
    function addSwitch(type,selector) {
        // $selector = $(selector);
        switch (type) {
            case "1":
                $(".btnWrapper").before(addShortText(selector.attr("title")));
                break;
            case "2":
                $(".btnWrapper").before(addLongText(selector.attr("title")));
                break;
            case "3":
                addRaios(selector,".btnWrapper");
                break;
            case "4":
                addCheckBox(selector,".btnWrapper");
                break;
            case "5":
                $(".btnWrapper").before(addDateInput(selector.attr("title")));
                break;
            case "6":
                addTimeSelect(selector,".btnWrapper");
                break;
            default:
                break;
        }

    }

    function addShortText(title) {
        var result = "<div class=\"shortText\">\n" +
            "            <input type=\"hidden\" name=\"key"+ key_count +"\" value=\""+ title+"\">\n" +
            "            <label>"+ title + "</label>\n" +
            "            <input type=\"text\" name=\"value"+ key_count + "\">\n" +
            "        </div>";
        key_count++;
        return result
    }
    
    function addLongText(title) {
        var result = "<div class=\"longText\">\n" +
            "            <input type=\"hidden\" name=\"key"+ key_count +"\" value=\""+ title+"\">\n" +
            "                    <p>"+ title +"</p>\n" +
            "                    <textarea name=\"value"+ key_count +"\"></textarea>\n" +
            "                </div>";
        key_count++;
        return result;
    }

    function addRaios(selector,subSelector) {
        var options = selector.attr("condition").split(",");
        var result = "<div class=\"radioContent\">\n" +
            "            <input type=\"hidden\" name=\"key"+ key_count +"\" value=\""+ selector.attr("title")+"\">\n" +
                        "<p>" +selector.attr("title") +"</p>";
        for (var i = 0;i<options.length  ;i++) {
            result = result + addSingleRadio(options[i]);
        }
        key_count++;
        result = result + "</div>";
        $(subSelector).before(result);
    }

    function addSingleRadio(value) {
        var result = "<label><input name=\"value"+ key_count +"\" type=\"radio\" value=\""+ value+"\">" + value +"</label>\n";
        return result;
    }

    function addCheckBox(selector,subSelector) {
        var options = selector.attr("condition").split(",");
        var result = "<div class=\"checkboxContent\">\n" +
            "            <input type=\"hidden\" name=\"key"+ key_count +"\" value=\""+ selector.attr("title")+"\">\n" +
            "<p>" +selector.attr("title") +"</p>";
        for (var i = 0;i<options.length  ;i++) {
            result = result + addSingleCheckBox(options[i]);
        }
        key_count++;
        result = result + "</div>";
        $(subSelector).before(result);
    }

    function addSingleCheckBox(value) {
        var result = "<label><input type=\"checkbox\" name=\"value" + key_count +"\" value=\""+ value+"\">" + value +"</label>\n";
        return result;
    }

    function addTimeSelect(selector,subSelector) {
        var title = selector.attr("title");
        var options = selector.attr("condition").split(",");
        var result = "<div class=\"timeContent\">\n" +
            "                    <input type=\"hidden\" name=\"key"+ key_count +"\" value=\""+ title+"\">\n" +
            "                    <label>"+ title +"</label>\n" +
            "                    <select name=\"value"+ key_count+"\">\n";
        for (var i = 0;i<options.length;i++) {
            result = result + addSingleTime(options[i]);
        }
        key_count++;
        result = result + "</select>\n" +
            "                </div>\n";
        $(subSelector).before(result);
    }

    function addSingleTime(value) {
        var result = "<option value=\""+ value +"\">" + value +"</option>";
        return result;
    }

    function addDateInput(title) {
        var result = "<div class=\"dateContent\">\n" +
            "                    <input type=\"hidden\" name=\"key"+ key_count+"\" value=\""+ title+"\">\n" +
            "                    <label>"+ title+"</label>\n" +
            "                    <input type=\"date\" class=\"date_input\" name=\"value"+ key_count+"\">\n" +
            "                </div>";
        key_count++;
        return result;
    }

})