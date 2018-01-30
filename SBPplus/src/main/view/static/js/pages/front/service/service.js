
$(window).resize(function () {
    if (windowNW != windowOw) {//如果窗口宽度改变，才执行程序
        windowOw = windowNW;//以前的宽度就应该等于现在的
        shanchangMarginTop();
    }
});

$(function () {
    shanchangMarginTop();
    var guihuaH = $("#gongneng").height();
    $("#gongneng").next().height(guihuaH);
});

function shanchangMarginTop() {
    if (windowNW < 500) {
        $("#backImage").css({
            clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.4 + "px,0)"
        });
        $("#shanchang").css({
            "margin-top": windowH * 0.4
        });
        $("#titleDiv").css(
            {
                top:windowH*0.4*0.5
            }
        );
        $("#shanchang img").height(windowH * 0.1);
    } else {
        $("#backImage").css({
            clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.7 + "px,0)"
        });
        $("#shanchang").css({
            "margin-top": windowH * 0.7
        });
        $("#titleDiv").css(
            {
                top:windowH*0.7*0.5
            }
        );
        $("#shanchang img").height(windowH * 0.2);
    }

}

