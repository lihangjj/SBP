//
function loadData() {
    $.ajax({
        url: "/pages/back/action/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": parameterName,
            "parameterValue": parameterValue
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allAction = data.allVo;
            var actb = $("#actb");
            var shouji = $("#shouji");
            $("#row1").nextAll().remove();
            shouji.empty();
            cancelAdd.call($("#addCancel").get(0));
            for (var x = 0; x < allAction.length; x++) {
                var action = allAction[x];
                var actionid = action.actionid;
                actb.append($("<tr id='row-" + actionid + "'>\n" +
                    " <td><input class='form-control checkbox-inline' type='checkbox'  id='check-" + actionid + "' value='" + actionid + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='actionid-" + actionid + "' value='" + actionid + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='ti-" + actionid + "' value='" + action.title + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='flag-" + actionid + "' value='" + action.flag + "' /></td>\n" +
                    " <td><input class='form-control' type='text' id='url-" + actionid + "' value='" + action.url + "' /></td>\n" +
                    " <td><input class='form-control' type='text' id='sflag-" + actionid + "' value='" + action.sflag + "' /></td>\n" +
                    " <td><button type='button' class='btn btn-danger' id='edit-" + actionid + "'>修改</button></td>\n" +
                    "                    </tr>"));
                shouji.append($("<table class=\"table  table-hover table-responsive visible-xs \" style=\"text-align: center\" id=\"actb\">\n" +
                    " <tr>\n" +
                    "     <td>权限ID</td>\n" +
                    "     <td><input  class='form-control' type='text'  id='actionid-" + actionid + "' value='" + actionid + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>权限名称</td>\n" +
                    "     <td><input class='form-control' type='text'  id='ti-" + actionid + "' value='" + action.title + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>权限标记</td>\n" +
                    "     <td><input class='form-control' type='text'  id='flag-" + actionid + "' value='" + action.flag + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>权限路径</td>\n" +
                    "     <td><input class='form-control' type='text' id='url-" + actionid + "' value='" + action.url + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>显示标记</td>\n" +
                    "     <td><input class='form-control' type='text' id='sflag-" + actionid + "' value='" + action.sflag + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td><input class='form-control' type='checkbox' id='checkxs-" + actionid + "' value='" + actionid + "' />操作</td>\n" +
                    "     <td>" +
                    "<button type='button' class='btn btn-danger' id='edit-" + actionid + "'>修改</button></td>\n" +
                    " </tr>\n" +
                    "   </table>"));
            }

            $("#shouji input").css({
                background: "none", border: "none", color: "black"

            });

            $("[id^=edit-]").each(function () {
                $(this).click(function () {
                    var actionid = this.id.split("-")[1];
                    areYouSure("危险操作！可能导致程序无法正常运行！您确定继续修改？", function () {
                        var type = windowNW < 500 ? 1 : 0;//宽度小于500是手机
                        $.post("/pages/back/action/edit", {
                            actionid: actionid,
                            flag: $("[id=flag-" + actionid + "]").get(type).value,
                            sflag: $("[id=sflag-" + actionid + "]").get(type).value,
                            url: $("[id=url-" + actionid + "]").get(type).value,
                            title: $("[id=ti-" + actionid + "]").get(type).value
                        }, function (res) {
                            if (res) {
                                showAlert($("#successMsg"), "权限修改成功！")
                            } else {
                                showAlert($("#failureMsg"), "权限修改失败！")
                            }
                        }, "json")
                    });
                })
            });
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}


function cancelAdd() {
    $("#addAction").get(0).disabled = false;
    $("#addSure").get(0).disabled = true;
    $("#addSure").hide(2000, function () {
        $("#addSure").get(0).disabled = false;
    });
    $("#addAction").text("增加权限");
    $(this).hide(2000)
}

// /*]]>*/
$(function () {

    //这里需要复写parameterName;
    parameterName = 'dflag';
    loadData();
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#addActionsTo li").each(function () {
        $(this).click(function () {
            var roleid = this.id;
            var role = $(this).html();
            var ids = "";
            $("[id^=check" + "]").each(function () {
                if (this.checked) {
                    ids += this.id.split("-")[1] + "-";
                }
            });
            if (ids != "") {
                $.post("/pages/back/action/actionsAddTo", {ids: ids, roleid: roleid}, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "权限添加到" + role + "成功！")
                    } else {
                        showAlert($("#failureMsg"), "权限添加到" + role + "失败！")
                    }
                }, "json");
            } else {
                showAlert($("#warningMsg"), "您还未选择任何数据");
            }
        });
    });
    $("[id=selectAll]").each(function () {
        $(this).click(function () {
            var s = this.checked;
            if (windowNW < 500) {
                $("[id^=checkxs-]").each(function () {
                    this.checked = s;
                });
            } else {
                $("[id^=check-]").each(function () {
                    this.checked = s;
                });
            }

        });
    });
    $("#removeActionsFrom li").each(function () {
        $(this).click(function () {
            var roleid = this.id;
            var role = $(this).html();
            var ids = "";
            $("[id^=check" + "]").each(function () {
                if (this.checked) {
                    ids += this.id.split("-")[1] + "-";
                }
            });
            if (ids != "") {
                $.post("/pages/back/action/actionsRemoveFrom", {ids: ids, roleid: roleid}, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "权限从" + role + "移除成功！")
                    } else {
                        showAlert($("#failureMsg"), "权限从" + role + "移除失败！")
                    }
                }, "json");
            } else {
                showAlert($("#warningMsg"), "您还未选择任何数据");
            }
        });
    });
    var tr;
    $("#addAction").click(function () {
        tr = $("<tr id='form'>\n" +
            " <td><input class='form-control' type='checkbox'   disabled  /></td>\n" +
            " <td><input class='form-control' type='text'  id='actionidNew' disabled  /></td>\n" +
            " <td><input class='form-control' type='text'  id='titleNew' name='title'/><span id='titleNewMsg'></span></td>\n" +
            " <td><input class='form-control' type='text'  id='flagNew' name='flag' /><span id='flagNewMsg'></span></td>\n" +
            " <td><input class='form-control' type='text' id='urlNew' name='url'/><span id='urlNewMsg'></span></td>\n" +
            " <td><input class='form-control' type='text' id='sflagNew' name='sflag' /><span id='sflagNewMsg'></span></td>\n" +
            " </tr>");
        $("#actb").append(tr);
        this.disabled = true;
        $(this).text("一次只能增加一个权限");
        $("#addSure").show(2000);
        $("#addCancel").show(2000);
    });

    $("#addCancel").click(function () {
        tr.remove();
        cancelAdd.call(this);
    });
    $("#formDiv").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            showLoadingData();
            $.ajax({
                url: "/pages/back/action/add",
                data: {
                    "title": $("#titleNew").val(),
                    "flag": $("#flagNew").val(),
                    "sflag": $("#sflagNew").val(),
                    "url": $("#urlNew").val()
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    hideLoadingData();
                    if (data) {
                        showAlert($("#successMsg"), "权限增加成功");
                        tr.find("input").val("");
                    } else {
                        showAlert($("#failureMsg"), "权限增加失败");
                    }
                },
                error: function (e) {

                }
            });
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
            title: "请输入权限名称", flag: "请输入权限标记", url: "请输入权限路径", sflag: "只能是0或1"

        },
        rules: {
            title: {
                required: true

            },
            sflag: {
                digits: true,
                range: [0, 1]
            },
            flag: {
                required: true
            }, url: {
                required: true
            }
        }
    });

});



