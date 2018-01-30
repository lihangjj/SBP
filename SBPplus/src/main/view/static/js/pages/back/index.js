var x = true;
var contentW;
var contentH;
var windowW;
var navBarW;
var zhedieBut;
var menuDiv;
var menuDivH;
var contentDiv;
var myButSize;
var role;
var upBut;
var spanTop;//span居于角色的正中位置
var menuBarStatus = true;

var contentDivColor;//内容颜色
var hdColor;
var menuColor;
var bodyColor;
var styleValue;
var sysColor;
var sysFont;
var menuSelectedColor;
var menuFontColor;
//增加验证日期时间的函数
function addValidateDateTime() {
    $.validator.addMethod("datetime", function (value, element) {
        if ("" == value || null == value) {
            return true;
        } else {//时间格式，要打括号！！
            var regex = /[1-2][0-9][0-9][0-9]-(([0][1-9])|([1][0-2]))-(([0][1-9])|([1-2][0-9])|([3][0-1])) (([0-1][0-9])|([2][0-3])):([0-5][0-9]):([0-5][0-9])/;
            return (regex.test(value));
        }
    }, "错误");
}

//恢复菜单栏状态
function restoreBar() {
    var jiaH = 168;
    $("[id^=role-]").each(function () {
        var rid = this.id.split("-")[1];
        var actions = $("[id=action-" + rid + "]");
        var status = cookie.get(rid);
        if (status == "show") {
            actions.css("display", "block");
            actions.show();
            var actionsH = parseInt(actions.css("height").replace("px", "")) + 10;
            var l = actions.length;
            jiaH += l * actionsH;
        } else {
            actions.css("display", "none");
            actions.hide();
        }
    });
    var menuH = jiaH + 500;
    $("#menuDiv").css({
        height: menuH
    });
    var actid = cookie.get("selectedActId");//选中颜色
    $("[name=" + actid + "]").css({
        "background": menuSelectedColor
    });
}
var webSocket;
var memberid;
var sessionid;

function myWebSocket() {
    var url="ws://192.168.1.108/fushMessage/"+sessionid;
    if ('WebSocket' in window) {
        webSocket = new WebSocket(url);
    } else {
        if ('MozWebSocket' in window) {
            webSocket = new MozWebSocket(url);
        } else {
            alert("不支持webSocket")
        }
    }

    webSocket.onopen = function (event) {
        alert("服务器链接成功")
    };
    $("#send").click(function () {
        webSocket.send("测试一下" + $("#data").val());
    });
    webSocket.onmessage = function (p1) {
        alert(p1.data)
    };
    webSocket.onclose = function (p1) {
        alert("链接关闭前端")
    };
    webSocket.onerror = function (p1) {
        alert(p1);
    };
}

$(function () {
    // myWebSocket();
    contentDiv = $("#contentDiv");
    role = $("[id^=title-]");
    upBut = $("[id^=role-] span");
    menuDivH = $("#menuDiv").height();
    zhedieBut = $("#zhedie");
    menuDiv = $("#menuDiv");
    initW();//根据设备初始化控件
    zhedieBut.click(function () {
        zhedie();
    });
    $("[id^=action-]").each(function () {
        $(this).click(function () {
            var actid = $(this).attr("name");
            cookie.set("selectedActId", actid);
            window.location = $(this).attr("href");
            event.stopPropagation();
        })
    });

    $("[id^=role-]").each(function () {
        $(this).click(function () {
            var id = this.id.split("-")[1];
            var actions = $(this).find("[id^=action-]");
            var status = actions.css("display");
            if (status == "none") {
                cookie.set(id, "show", 30);
                menuBarStatus = false;
            } else {
                cookie.set(id, "hide", 30);
                menuBarStatus = true;
            }

            actions.slideToggle(function () {
                restoreBar();
                sameHeight();
            });

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

    });
    //为后台所有图片添加大图查看
    showBigImg();
    //恢复菜单栏
});

$(window).resize(function () {
    initW();
});

function initW() {//重新初始化控件的宽度
    contentH = $("#contentDiv").height();
    var loadingData = $("#loadingData");
    contentDiv.append(loadingData);
    windowW = window.innerWidth;
    var ct = contentDivColor.split(":")[1];
    var hd = hdColor.split(":")[1];
    var menu = menuColor.split(":")[1];
    var value = styleValue.split(":")[1];

    $("body").css({//设置系统字体和颜色
        "font-family": sysFont,
        color: sysColor
    });
    $("#menuDiv").css({//菜单字体颜色
        color: menuFontColor
    });
    $("#menuDiv > div > div").hover(function () {
        $(this).css({//鼠标选中的颜色
            background: menuSelectedColor
        })

    }, function () {
        //鼠标移开的颜色，如果是选中的Item，就保留原来的颜色
        var actid = cookie.get("selectedActId");
        var hid = $(this).attr("name");
        if ((hid != actid) || hid == undefined || actid == undefined) {
            $(this).css({//鼠标选中的颜色
                background: ""
            })
        } else {
            $(this).css({//鼠标选中的颜色
                background: menuSelectedColor
            })
        }

    });
    if (value != 'null') {//如果是选择了推荐款式,这里是字符串
        $("body").css({
            width: "100%",
            height: windowH,
            "background-image": "url('" + value + "')",
            "background-size": "cover",
            "background-repeat": "no-repeat",
            "background-attachment": "fixed" /*背景图片固定不动，不跟内容滚动而滚动*/
        });
    } else {
        var style = bodyColor.split(":")[0];
        var bodyC = bodyColor.split(":")[1];
        if (style == 'color') {//如果是颜色
            $("body").css({
                background: bodyC
            });
        } else {
            $("body").css({//如果是图片
                width: "100%",
                height: windowH,
                "background-image": "url('" + bodyC + "')",
                "background-size": "cover",
                "background-repeat": "no-repeat",
                "background-attachment": "fixed" /*背景图片固定不动，不跟内容滚动而滚动*/
            });

        }
    }


    if (ct.indexOf("/") > -1) {//内容是图片
        contentDiv.css({
            // background: contentDivColor.split(":")[1]
            "background-image": "url('" + ct + "')",
            "background-size": "cover"
        });
    } else {//是颜色
        contentDiv.css({
            background: ct
        });
    }

    if (hd.indexOf("/") > -1) {//头部是图片
        $("#validateDiv").css({
            // background: contentDivColor.split(":")[1]
            "background-image": "url('" + hd + "')",
            "background-size": "cover"
        });
    } else {//是颜色
        $("#validateDiv").css({
            background: hd
        });
    }
    if (menu.indexOf("/") > -1) {//是图片
        $("#menuDiv").css({
            // background: contentDivColor.split(":")[1]
            "background-image": "url('" + menu + "')",
            "background-size": "cover"
        });
    } else {//是颜色
        $("#menuDiv").css({
            background: menu
        });
    }


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
    $("#loadingData").css({
        height: contentH
    });
    $("#alert").css({
        top: windowH * 0.4,
        transform: "translateY(-50%)"
    });
    $("#alertM-d").css({
        top: windowH * 0.4,
        transform: "translateY(-50%)"
    });


    menuDiv.css({
        width: navBarW,
        height: contentH
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
    restoreBar();
    setTimeout(function () {
        sameHeight();
    },100)
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

//内容和左边导航高度保持相同
function sameHeight() {
    var cH = $("#contentDiv").height();
    var menuH = $("#menuDiv").height();
    var maxH = Math.max(cH, menuH);
    $("#menuDiv").css({height: maxH});
    $("#contentDiv").css({height: maxH})

}
