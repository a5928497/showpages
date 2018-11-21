$(function () {
    $orderGrp = $(".order-group");

    //返回按钮事件
    $backBTN = $(".backBTN");
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    })
    //类型变化时改变列表
    $("select").change(function () {
        var type = $(":selected").val();
        optionSwitch(type);
    });

    function optionSwitch(type) {
        switch (type) {
            //单选项
            case "3" :
                showRadio();
                break;
            default:
                break;
        }
    }
    //类型为单选事件函数
    function showRadio() {
        $orderGrp.after("<div class=\"form-group\"><label class=\"col-sm-2 control-label\">选项一</label>\n" +
            "                                    <div class=\"col-sm-10\"><input type=\"text\" class=\"form-control radio\"></div>\n" +
            "                                </div>\n" +
            "                                <div class=\"form-group\"><label class=\"col-sm-2 control-label\">选项二</label>\n" +
            "                                    <div class=\"col-sm-10\"><input type=\"text\" class=\"form-control radio\"></div>\n" +
            "                                </div>\n" +
            "                                <div class=\"form-group\"><label class=\"col-sm-2 control-label\">选项三</label>\n" +
            "                                    <div class=\"col-sm-10\"><input type=\"text\" class=\"form-control radio\"></div>\n" +
            "                                </div>\n" +
            "                                <div class=\"form-group\"><label class=\"col-sm-2 control-label\">选项四</label>\n" +
            "                                    <div class=\"col-sm-10\"><input type=\"text\" class=\"form-control radio\"></div>\n" +
            "                                </div>")
    }
    //使用时间委派获取各单选项内容
    $("form").on("blur",".radio",function () {
        var condition = "";
        $(".radio").each(function () {
            var val = $(this).val();
            if (val != "") {
                condition = condition + $(this).val() + ",";
            }
        });
        condition = condition.substring(0,condition.lastIndexOf(","));
        $("#condition").val(condition);
    });
});