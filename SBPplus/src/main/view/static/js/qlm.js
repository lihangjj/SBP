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
    var min = Math.min(windowH, windowNW);
    if (min<500){
        $("html").css({
            "font-size": min * 0.013
        });
    }else {
        $("html").css({
            "font-size": min * 0.01
        });
    }

}


