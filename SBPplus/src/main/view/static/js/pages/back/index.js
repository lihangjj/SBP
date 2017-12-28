var x = true;
var contentW;
var windowW;
var navBarW;
var zhedieBut;
var menuDiv;
var contentDiv;
var myButSize;
var role;
var upBut;
var spanTop;//span居于角色的正中位置
window.onload = function () {

    role = $("#role div:nth-child(1)");
    upBut = $("#role span");

    zhedieBut = $("#zhedie");
    menuDiv = $("#menuDiv");
    contentDiv = $("#contentDiv");
    var allRoles = $("[id=role]");
    initW();//根据设备初始化控件
    zhedieBut.click(function () {
        zhedie();
    });
    $("[id=action]").each(function () {
        $(this).click(function () {
            window.location=$(this).attr("href");
            event.stopPropagation();
        })
    });

    allRoles.each(function () {
        $(this).click(function () {
            var actions = $(this).find("[id=action]");
            actions.slideToggle(500);
            var span = $(this).find("span");
            var actionsH = actions.height();
            if (actionsH < 2) {
                span.attr(
                    {
                        class: "glyphicon glyphicon-chevron-up"
                    }
                );
            } else {
                span.attr(
                    {
                        class: "glyphicon glyphicon-chevron-down"
                    }
                );
            }
        })

    })
};
window.onresize = function () {
    initW();
};

function initW() {//重新初始化控件的宽度
    windowW = window.innerWidth;
    if (windowW > 1200) {
        contentW = "90%";
        navBarW = "10%";
        myButSize = "btn-md";
    } else {
        contentW = "80%";
        navBarW = "20%";
        myButSize = "btn-xs";
    }
    $("#validateDiv button").each(
        function () {
            $(this).addClass(myButSize);
        }
    );
    menuDiv.css({
        width: navBarW
    });


    if (x) {
        contentDiv.css({
            width: contentW
        })
    } else {
        contentDiv.css({
            width: windowW
        })
    }
    spanTop = (role.height() * 0.5);
    upBut.css({
        top: spanTop
    });
}

function zhedie() {
    if (x) {
        menuDiv.css({
            transform: "translate(-100%)"
        });
        contentDiv.css({
            width: windowW
        });
        zhedieBut.attr({
            class: "glyphicon glyphicon-menu-right"
        });
        x = false
    } else {
        menuDiv.css({
            transform: "translate(0)"
        });
        contentDiv.css({
            width: contentW
        });
        zhedieBut.attr({
            class: "glyphicon glyphicon-menu-left"
        });
        x = true;
    }
}
