var windowH = window.innerHeight;
var windowNW = window.innerWidth;
var windowOw = windowNW;
$(function () {//公共操作函数这样写就必须要写window.onload
    $("[name=rand]").each(function () {
        $(this).click(function () {
            $(this).attr("src", "/defaultKaptcha")
        })
    });//点击换验证码
});


function initPublic() {
    if (windowNW < 500) {//手机屏,设置字体
        $(".font2").css({
            "font-size": "20px"
        })
    } else if (windowNW < 768) {//平板
        $(".font2").css({
            "font-size": "23px"
        })
    } else if (windowNW < 992) {//电脑
        $(".font2").css({
            "font-size": "26px"
        })
    } else if (windowNW < 1200) {//大电脑
        $(".font2").css({
            "font-size": "30px"
        })
    } else {
        $(".font2").css({
            "font-size": "35px"
        })
    }
}


