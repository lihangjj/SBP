var gunL;//滚动距离
window.onscroll = function () {
    console.log('滚了')
    gunL = document.documentElement.scrollTop || document.body.scrollTop;
    var jiazhiOffset = $("#jiazhi").offset().top;//图片距离顶部距离
    if (gunL + windowH > jiazhiOffset) {
        $("#jiazhi").css({
            animation: "guolai 4s",
            "-webkit-animation": "guolai 4s"
        })
    }
    console.log(jiazhiOffset + 'di');
    console.log(gunL)
};
$(function () {
    $("#titleDiv").css({
        display: "block",
        animation: "show 4s",
        "-webkit-animation": "show 4s"
    })
});