$(function () {
    $("img[id*='but']").each(function () {
        $(this).on("mouseover", function () {
            $(this).fadeOut(50, function () {
                $(this).fadeIn(500);
            });
        })
    });
})