function logoResize() {
    if (windowNW < 500) {
        $("#logo").css({
            height: "3rem"
        })
    } else {
        $("#logo").css({
            height: "6rem"
        })
    }
}

$(function () {
    logoResize();


});

$(window).resize(function () {
    logoResize();
});