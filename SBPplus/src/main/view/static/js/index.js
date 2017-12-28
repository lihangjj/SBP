var backImgH;
window.onresize = function () {
    windowNW = window.innerWidth;
    windowH = window.innerHeight;
    initPublic();//公共初始化
    if (windowNW != windowOw) {//如果设备改变宽度
        windowOw = windowNW;//以前的宽度就应该等于现在的
        if (windowNW < 500) {//手机屏
            backImgH = windowH * 0.5;

        } else if (windowNW < 768){
            backImgH = windowH;

        }else if (windowNW < 992){
            backImgH = windowH;

        }else {
            backImgH = windowH;
        }
        $("#backImage").height(backImgH);
        $("#youshi").height(backImgH * 0.2)
    }


};

$(function () {

    initPublic();//公共初始化
    if (windowNW < 500) {//手机屏幕
        backImgH =windowH * 0.5;
    } else {
        backImgH = windowH;
    }
    bgScale();
    $("#backImage").height(backImgH);
    $("#youshi").height(backImgH * 0.2);
    $("#body").on("click", hiddenForm);
    $("[name=claim]").each(function () {
        $(this).on("click", function () {
            if (screen.width > 990) {
                $(this).css("height", "292px");
            } else {
                $(this).css("height", "150px");
            }
            $("#model").css("display", "block");
        })
    });
    $("#modelBt").on("click", useModel);

    $("#xiangmuyuyue").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            form.submit(); // 提交表单
        },
        errorPlacement: function (error, element) {
            $("#" + $(element).attr("id") + "Msg").append(error);
        },
        highlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(255,0,0,1)"
                    })
                });

            })
        },
        unhighlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(0,255,0,1)"
                    });
                });
            })
        },
        errorClass: "text-danger",
        messages: {
            claim: "请输入项目需求",
            phone: "请输入正确的手机号",
            code: "验证码错误"

        },
        rules: {
            claim: {
                required: true
            },
            phone: {
                required: true,
                digits: true,
                rangelength: [11, 11],
                remote: {
                    url: "/checkCellPhoneNumber", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#phone").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            },
            code: {
                required: true,
                rangelength: [4, 4],
                remote: {
                    url: "/checkRandAndCode", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        code: function () {
                            return $("#code").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            }
        }
    });
    $("#xiangmuyuyue1").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            form.submit(); // 提交表单
        },
        errorPlacement: function (error, element) {
            $("#" + $(element).attr("id") + "Msg1").append(error);
        },
        highlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(255,0,0,1)"
                    })
                });

            })
        },
        unhighlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(0,255,0,1)"
                    });
                });
            })
        },
        errorClass: "text-danger",
        messages: {
            claim: "请输入项目需求",
            phone: "请输入正确的手机号",
            code: "验证码错误"

        },
        rules: {
            claim: {
                required: true
            },
            phone: {
                required: true,
                digits: true,
                rangelength: [11, 11],
                remote: {
                    url: "/checkCellPhoneNumber", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#phone1").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            },
            code: {
                required: true,
                rangelength: [4, 4],
                remote: {
                    url: "/checkRandAndCode", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        code: function () {
                            return $("#code1").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            }
        }
    });
});

function bgScale() {

    setInterval(setAndCancel, 12000);
    $("#backImage").css({//执行放大
        "transform": "scale(1.2)",
        "transition": "all 12s"
    });
    var b1 = true;//切换图片标记
    function reset() {//将放大后的图片还原
        $("#backImage").css({
            "transform": "scale(1)",
            "transition": "all 0s"
        });
    }

    function setAndCancel() {
        reset();
        if ($("#backImage").css("transition") == "all 0s ease 0s") {//这样的话，他就必须先去设置样式，这样就能取得样式，就是安全线程
            if (b1) {
                $("#backImage").attr({
                    "src": "/images/index/b2.jpg"//切换背景图片
                });
                b1 = false;
            } else {
                $("#backImage").attr({
                    "src": "/images/index/b1.jpg"
                });
                b1 = true;
            }
            $("#backImage").css({//执行放大1
                "transform": "scale(1.2)",
                "transition": "all 12s"
            });
        }

    }
}//背景图片scale轮换方法

function hiddenForm(e) {
    var target = $(e.target);//获得jquery对象
    if (target.is("#formDiv,#formDiv *,#claim")) {
        //如果是点的表单内的元素，就什么也不做
    } else {
        $("#claim").css("height", "50px");
        $("#model").css("display", "none");
    }
}//点击表单之外的地方缩小文本框

function useModel() {
    var modelStr = "1.公司或需求名称（开发/设计对象）\n" +
        "例：重庆洪湖科技有限公司，设计及开发一WEB应用网站\n" +
        "2.设计对象简介（参考网址、文字介绍等）\n" +
        "例：类似淘宝京东电商平台\n" +
        "3.明确具体的要求：\n" +
        "例：自适应网站，活动报名表单收集、开发时间要求，预算范围等";
    $("#claim").val(modelStr);

}//使用模板