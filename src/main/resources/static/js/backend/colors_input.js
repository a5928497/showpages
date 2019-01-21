$(function () {
    $colors = $(".colors");
    $welcomeFont = $("#welcome_font_demo");
    $welcomeBorder = $("#welcome_border_demo");
    $detailsFont = $("#details_font_demo");
    $detailsBg = $("#welcome_bg_demo");

    //返回按钮事件
    $backBTN = $(".backBTN");
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    })
    demoColor();
    $colors.colorpicker();

    $colors.change(function () {
        demoColor();
    });
    
    function demoColor() {
        $welcomeFont.css("color",$("input[name=\"welcomePageFontColor\"]").val());
        $detailsFont.css("color",$("input[name=\"detailsPageFontColor\"]").val());
        $detailsBg.css("background-color",$("input[name=\"detailsPageBackGroundColor\"]").val());
        $welcomeBorder.css("border","2px " + $("input[name=\"introduceBorderColor\"]").val() + " solid");
    }
});