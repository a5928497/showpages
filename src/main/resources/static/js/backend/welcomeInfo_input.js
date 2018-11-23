$(function () {
    //全局计数变量
    var count = 2;

    //返回按钮事件
    $backBTN = $(".backBTN");
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    });

    //处理回显
    $paragraph = $("input[name='paragraph']");
    if ($paragraph.val() != "") {
        count = 1;
        var paragraphs = $paragraph.val().split(",");
        for (var i = 0;i<paragraphs.length;i++) {
            $(".btns").before(addInput(count,count,"段落","paragraph","paragraphCon",true,null,paragraphs[i]));
        }
    }

    //添加按钮监听
    $(".addBTN").click(function () {
        $(".paragraphCon:last").after(addInput(count,count,"段落","paragraph","paragraphCon",true,null,null));
    });

    //输入变化监听
    $("form").on("change",".paragraph",function () {
        var paragraphs = "";
        $(".paragraph").each(function () {
            var paragraph = $(this).val();
            if ( paragraph != "") {
                paragraphs = paragraphs + paragraph +",";
            }
        });
        paragraphs = paragraphs.substring(0,paragraphs.lastIndexOf(","));
        $("input[name='paragraph']").val(paragraphs);
    });

    function addInput(beginNum,overNum,prefix,clazz,clazzContainer,needSerial,placeholder,value) {
        var result = "";
        //placeholder置空
        if (null == placeholder) {
            placeholder = "";
        }
        if (null == value) {
            value = "";
        }
        //判断是否需要序号
        if (true == needSerial) {
            for (var i =beginNum;i<=overNum;i++ ) {
                result = result + "<div class=\"form-group "+ clazzContainer +"\"><label class=\"col-sm-2 control-label\">"+ prefix + i +"</label>\n" +
                    "                                    <div class=\"col-sm-10\"><input type=\"text\" placeholder=\""+ placeholder
                    +"\" class=\"form-control "+ clazz +"\" value=\""+ value + "\"></div>\n" +
                    "                                </div>\n";
                count++;
            }
        }else {
            for (var i =beginNum;i<=overNum;i++ ) {
                result = result + "<div class=\"form-group "+ clazzContainer +"\"><label class=\"col-sm-2 control-label\">"+ prefix +"</label>\n" +
                    "                                    <div class=\"col-sm-10\"><input type=\"text\" placeholder=\""+ placeholder
                    +"\" class=\"form-control "+ clazz +"\" value=\""+ value +"\"></div>\n" +
                    "                                </div>\n";
                count++;
            }
        }

        return result;
    }
});