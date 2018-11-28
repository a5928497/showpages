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

    //生成各个选项
    $fields = $(".fields");
    if ($fields.length > 0) {
        var count = 0;
        do {
            var i = 1;
            var selector = ".fields[order=\""+ i +"\"]";
            $field = $(selector);
            if (0 != $field.length) {
                addSwitch($(selector).attr("field_type"),selector);
                console.log(count);
                count++;
            }
            i++;
        }while (count == $fields.length)
    }

    //根据类型执行相应生成函数
    function addSwitch(type,selector) {
        $selector = $(selector);
        switch (type) {
            case "1":
                $("form").append(addShortText($selector.attr("title")));
                break;
            case "2":
                $("form").append(addLongText($selector.attr("title")));
                break;
            case "3":
                addRaios(selector,"form");
                break;
            case "4":
                addCheckBox(selector,"form");
                break;
            case "5":
                $("form").append(addDateInput($selector.attr("title")));
                break;
            case "6":
                addTimeSelect(selector,"form");
                break;
            default:
                break;
        }

    }

    // addTimeSelect("input[title='预约时间']","form");
    // addRaios("input[title='性别']","form");
    // addCheckBox("input[title='你喜欢吃什么水果']","form");
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

    function addRaios(selector,parentSelector) {
        $selector = $(selector);
        var options = $selector.attr("condition").split(",");
        var result = "<div class=\"radioContent\">\n" +
                        "<p>" +$selector.attr("title") +"</p>";
        for (var i = 0;i<options.length  ;i++) {
            result = result + addSingleRadio(options[i]);
        }
        key_count++;
        result = result + "</div>";
        $(parentSelector).append(result);
    }

    function addSingleRadio(value) {
        var result = "<label><input name=\"value"+ key_count +"\" type=\"radio\" value=\""+ value+"\">" + value +"</label>\n";
        return result;
    }

    function addCheckBox(selector,parentSelector) {
        $selector = $(selector);
        var options = $selector.attr("condition").split(",");
        var result = "<div class=\"checkboxContent\">\n" +
            "<p>" +$selector.attr("title") +"</p>";
        for (var i = 0;i<options.length  ;i++) {
            result = result + addSingleCheckBox(options[i]);
        }
        key_count++;
        result = result + "</div>";
        $(parentSelector).append(result);
    }

    function addSingleCheckBox(value) {
        var result = "<label><input type=\"checkbox\" name=\"value" + key_count +"\" value=\""+ value+"\">" + value +"</label>\n";
        return result;
    }

    function addTimeSelect(selector,parentSelector) {
        $selector = $(selector);
        var title = $selector.attr("title");
        var options = $selector.attr("condition").split(",");
        var result = "<div class=\"timeContent\">\n" +
            "                    <input type=\"hidden\" name=\"key"+ key_count +"\" value=\"key"+ key_count+"\">\n" +
            "                    <label>"+ title +"</label>\n" +
            "                    <select name=\"value"+ key_count+"\">\n";
        for (var i = 0;i<options.length;i++) {
            result = result + addSingleTime(options[i]);
        }
        key_count++;
        result = result + "</select>\n" +
            "                </div>\n";
        $(parentSelector).append(result);
    }

    function addSingleTime(value) {
        var result = "<option value=\""+ value +"\">" + value +"</option>";
        return result;
    }

    function addDateInput(title) {
        var result = "<div class=\"dateContent\">\n" +
            "                    <input type=\"hidden\" name=\"key"+ key_count+"\" value=\"\">\n" +
            "                    <label>"+ title+"</label>\n" +
            "                    <input type=\"date\" class=\"date_input\" name=\"value"+ key_count+"\">\n" +
            "                </div>";
        key_count++;
        return result;
    }
})