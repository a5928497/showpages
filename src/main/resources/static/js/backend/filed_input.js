$(function () {
    $orderGrp = $(".order-group");
    //全局选项数计算变量
    var count = 1;

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
        //条件字段清零
        $("#condition").removeAttr("value");
        //清理添加项
        cleanContainers();
        //根据类型不同生成不同交互表单
        optionSwitch(type);
    });

    function optionSwitch(type) {
        switch (type) {
            //单选项
            case "3" :
                showRadio();
                break;
            case "4" :
                showCheckbox();
            default:
                break;
        }
    }
    
    //类型为多选事件函数
    function showCheckbox() {
        $orderGrp.after(addInput(count,4,"选项","checkbox","checkboxCon"));
        addResultOnListener("form","blur",".checkbox");
        $(".backBTN").before("<button class=\"btn btn-success addBTN\" type=\"button\">增加选项</button> ");
        addAddBTNOnListener(".btns","click",".addBTN","选项","checkbox","checkboxCon",".checkboxCon:last");
    }
    
    //类型为单选事件函数
    function showRadio() {
        $orderGrp.after(addInput(count,4,"选项","radio","radioCon"));
        //使用事件委派获取各单选项内容
        addResultOnListener("form","blur",".radio");
    }

    //绑定处理输入结果事件委派
    function addResultOnListener(parentSelector,event,selector) {
        $(parentSelector).on(event,selector,function () {
            var condition = "";
            $(selector).each(function () {
                var val = $(this).val();
                if (val != "") {
                    condition = condition + $(this).val() + ",";
                }
            });
            condition = condition.substring(0,condition.lastIndexOf(","));
            $("#condition").val(condition);
        });
    }

    //绑定多选项类型添加按钮事件委派
    function addAddBTNOnListener(parentSelector,event,selector,prefix,clazz,clazzContainer,callSelector) {
        $(parentSelector).on(event,selector,function () {
                $(callSelector).after(addInput(count,count,prefix,clazz,clazzContainer));
        });
    }

    /**
     * 根据数量生成输入框函数
     * @param number 生成输入框数量
     * @param prefix 标签名前缀
     * @param clazz 输入框class标记属性
     * @param clazzContainer 包裹区域标记属性
     */
    function addInput(beginNum,overNum,prefix,clazz,clazzContainer) {
        var result = "";
        for (var i =beginNum;i<=overNum;i++ ) {
            result = result + "<div class=\"form-group "+ clazzContainer +"\"><label class=\"col-sm-2 control-label\">"+ prefix + i +"</label>\n" +
                "                                    <div class=\"col-sm-10\"><input type=\"text\" class=\"form-control "+ clazz +"\"></div>\n" +
                "                                </div>\n";
            count++;
        }
        return result;
    }

    function cleanContainers() {
        $(".radioCon").remove();
        $(".checkboxCon").remove();
        $(".addBTN").remove();
        count = 1;
    }
});