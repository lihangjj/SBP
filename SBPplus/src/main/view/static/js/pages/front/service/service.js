var backImgH;
window.onresize = function () {
    windowNW = window.innerWidth;
    windowH = window.innerHeight;
    initPublic();//公共初始化
    if (windowNW != windowOw) {//如果窗口宽度改变，才执行程序
        windowOw = windowNW;//以前的宽度就应该等于现在的
        if (windowNW<500){
            $("#backImage").css({
                clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.4 + "px,0)"
            });
            $("#service").css({
                "margin-top":  windowH * 0.4
            })
        }else {
            $("#backImage").css({
                clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.7 + "px,0)"
            });
            $("#service").css({
                "margin-top":  windowH * 0.7
            })
        }
    }
};
$(function () {
    initPublic();//公共初始化
    if (windowNW<500){
        $("#backImage").css({
            clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.4 + "px,0)"
        });
        $("#service").css({
            "margin-top":  windowH * 0.4
        })
    }else {
        $("#backImage").css({
            clip: "rect(0 ," + windowNW + 'px,' + windowH * 0.7 + "px,0)"
        });
        $("#service").css({
            "margin-top":  windowH * 0.7
        })
    }

});



