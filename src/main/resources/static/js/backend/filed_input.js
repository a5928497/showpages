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
                break;
            case "6" :
                showTimes();
                break;
            default:
                break;
        }
    }
    
    //类型为时间事件函数
    function showTimes() {
        $orderGrp.after(addInput(count,count,"除去时段","except-time","timeCon",false,"格式示例：15:00-16:30"));
        $orderGrp.after(addInput(count,count,"时间间隔","interval","timeCon",false,"直接输入数字"));
        $(".backBTN").before("<button class=\"btn btn-success addBTN\" type=\"button\">增加去除时段</button> ");
        addAddBTNOnListener(".btns","click",".addBTN","除去时段","except-time","timeCon",".timeCon:last",false,"格式示例：15:00-16:30");
    }
    
    //类型为多选事件函数
    function showCheckbox() {
        $orderGrp.after(addInput(count,4,"选项","checkbox","checkboxCon",true));
        addResultOnListener("form","blur",".checkbox");
        $(".backBTN").before("<button class=\"btn btn-success addBTN\" type=\"button\">增加选项</button> ");
        addAddBTNOnListener(".btns","click",".addBTN","选项","checkbox","checkboxCon",".checkboxCon:last",true);
    }
    
    //类型为单选事件函数
    function showRadio() {
        $orderGrp.after(addInput(count,4,"选项","radio","radioCon",true));
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
    function addAddBTNOnListener(parentSelector,event,selector,prefix,clazz,clazzContainer,callSelector,needSerial,placeholder) {
        $(parentSelector).on(event,selector,function () {
                $(callSelector).after(addInput(count,count,prefix,clazz,clazzContainer,needSerial,placeholder));
                console.log(111)
        });
    }

    /**
     * 根据数量生成输入框函数
     * @param number 生成输入框数量
     * @param prefix 标签名前缀
     * @param clazz 输入框class标记属性
     * @param clazzContainer 包裹区域标记属性
     */
    function addInput(beginNum,overNum,prefix,clazz,clazzContainer,needSerial,placeholder) {
        var result = "";
        //placeholder置空
        if (null == placeholder) {
            placeholder = "";
        }
        //判断是否需要序号
        if (true == needSerial) {
            for (var i =beginNum;i<=overNum;i++ ) {
                result = result + "<div class=\"form-group "+ clazzContainer +"\"><label class=\"col-sm-2 control-label\">"+ prefix + i +"</label>\n" +
                    "                                    <div class=\"col-sm-10\"><input type=\"text\" placeholder=\""+ placeholder
                    +"\" class=\"form-control "+ clazz +"\"></div>\n" +
                    "                                </div>\n";
                count++;
            }
        }else {
            for (var i =beginNum;i<=overNum;i++ ) {
                result = result + "<div class=\"form-group "+ clazzContainer +"\"><label class=\"col-sm-2 control-label\">"+ prefix +"</label>\n" +
                    "                                    <div class=\"col-sm-10\"><input type=\"text\" placeholder=\""+ placeholder
                    +"\" class=\"form-control "+ clazz +"\"></div>\n" +
                    "                                </div>\n";
                count++;
            }
        }

        return result;
    }
    //重置函数
    function cleanContainers() {
        $(".radioCon").remove();
        $(".checkboxCon").remove();
        $(".timeCon").remove();
        $(".addBTN").remove()
        $(".btns").off();
        $("form").off();
        count = 1;
    }
    
    //日期控件函数
    function datePick() {
        $("#tips").css("margin-left","23%").css("color","red");
        $("#data_3").css("margin-left","1.8%");
        $('#data_3 .input-group.date').datepicker({
            startView: 2,
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
    }
});