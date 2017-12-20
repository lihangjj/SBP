$(function () {
    tinymce.init({selector: '#content'});
    $("#myform").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            form.submit(); // 提交表单
        },
        errorPlacement: function (error, element) {
            $("#" + $(element).attr("id").replace(".", "\\.") + "Msg").append(error);
        },
        highlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $("#" + $(element).attr("id").replace(".", "\\.") + "Div").attr("class", "form-group has-error");
                });

            })
        },
        messages: {
            "title": {
                remote: "该新闻标题已存在，无法使用，请更换！"
            }
        },
        unhighlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $("#" + $(element).attr("id").replace(".", "\\.") + "Div").attr("class", "form-group has-success");
                });
            })
        },
        errorClass: "text-danger",
        rules: {
            "title": {
                required: true,
                remote: {
                    url: "admin/news/checkTitle.action", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "html", // 接受数据格式
                    data: { // 要传递的数据
                        title: function () {
                            return $("#title").val();
                        }
                    },
                    dataFilter: function (data, type) {
                        if (data.trim() == "true") {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            },
            "abs": {
                required: true
            },
            "dtid": {
                required: true
            },
            "pic": {
                extension: "png|jpg|gif|bmp"
            },
            "content": {
                required: true
            },
            "flag": {
                required: true
            }
        }
    });
})