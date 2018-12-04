$(function () {
    $colors = $(".colors");
    $welcomeFont = $("#welcome_font_demo");
    $detailsFont = $("#details_font_demo");
    $detailsBg = $("#welcome_bg_demo");
    demoColor();
    $colors.colorpicker();

    $colors.change(function () {
        demoColor();
    });
    
    function demoColor() {
        $welcomeFont.css("color",$("input[name=\"welcomePageFontColor\"]").val());
        $detailsFont.css("color",$("input[name=\"detailsPageFontColor\"]").val());
        $detailsBg.css("background-color",$("input[name=\"detailsPageBackGroundColor\"]").val());
    }
});