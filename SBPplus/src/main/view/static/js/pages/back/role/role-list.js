//
function loadData() {
    $.ajax({
        url: "/pages/back/role/listAjax",
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
            var allroles = data.allVo;
            var actb = $("#roletb");
            var shouji = $("#shouji");
            $("#row1").nextAll().remove();
            shouji.empty();
            addCancel();
            for (var x = 0; x < allroles.length; x++) {
                var role = allroles[x];
                var roleid = role.roleid;
                actb.append($("<tr id='row-" + roleid + "'>\n" +
                    " <td><input class='form-control' type='checkbox'  id='check-" + roleid + "' value='" + roleid + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='actionid-" + roleid + "' value='" + roleid + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='ti-" + roleid + "' value='" + role.title + "' /></td>\n" +
                    " <td><input class='form-control' type='text'  id='flag-" + roleid + "' value='" + role.flag + "' /></td>\n" +
                    " <td><button type='button' class='btn btn-info' id='hisAction-" + roleid + "'>它的权限</button></td>\n" +
                    " <td><button type='button' class='btn btn-danger' id='edit-" + roleid + "'>修改</button></td>\n" +
                    "  </tr>"));
                shouji.append($("<table class=\"table  table-hover table-bordered visible-xs \" style=\"text-align: center\" id=\"actb\">\n" +
                    " <tr>\n" +
                    "     <td>角色ID</td>\n" +
                    "     <td><input  class='form-control' type='text'  id='actionid-" + roleid + "' value='" + roleid + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>角色名称</td>\n" +
                    "     <td><input class='form-control' type='text'  id='ti-" + roleid + "' value='" + role.title + "' /></td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "     <td>角色标记</td>\n" +
                    "     <td><input class='form-control' type='text'  id='flag-" + roleid + "' value='" + role.flag + "' /></td>\n" +
                    " </tr>\n" +

                    " <tr>\n" +
                    "     <td>选中<input class='form-control' type='checkbox' style='margin-right: 3rem' id='checkxs-" + roleid + "' value='" + roleid + "' /></td>\n" +
                    "     <td>" +
                    "<button type='button' class='btn btn-info' id='hisAction-" + roleid + "'>它的权限</button>\n" +
                    "<button type='button' class='btn btn-danger' id='edit-" + roleid + "'>修改</button></td>\n" +
                    " </tr>\n" +
                    "   </table>"));
            }

            $("#shouji input").css({
                background: "none", border: "none", color: "black"

            });

            $("[id^=edit-]").each(function () {
                $(this).click(function () {
                    var roleid = this.id.split("-")[1];
                    areYouSure("危险操作！可能导致程序无法正常运行！您确定继续修改？", function () {
                        var type = windowNW < 500 ? 1 : 0;//宽度小于500是手机
                        $.post("/pages/back/role/edit", {
                            roleid: roleid,
                            flag: $("[id=flag-" + roleid + "]").get(type).value,
                            title: $("[id=ti-" + roleid + "]").get(type).value
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
            $("[id^=hisAction-]").each(function () {
                $(this).click(function () {
                    var roleid = this.id.split("-")[1];
                    $.get("/pages/back/role/getActionsByRoleId", {roleid: roleid}, function (allAction) {
                        var row1 = $("#hisAction-row");
                        row1.nextAll().remove();
                        for (var x = 0; x < allAction.length; x++) {
                            var action = allAction[x];
                            var row = $("<tr>\n" +
                                " <td>" + action.actionid + "</td>\n" +
                                "  <td>" + action.title + "</td>\n" +
                                "      <td>" + action.flag + "</td>\n" +
                                "   <td>" + action.url + "</td>\n" +
                                "      </tr>");
                            row1.after(row);

                        }
                        $("#hisActionWindow").modal("show");


                    }, "json")
                });

            });
            selectAllRole();
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}


function addCancel() {
    $("#addRole").get(0).disabled = false;
    $("#addSure").get(0).disabled = true;
    $("#addSure").hide(2000, function () {
        $("#addSure").get(0).disabled = false;
    });
    $("#addRole").text("增加角色");
    $("#addCancel").hide(2000);
}

// /*]]>*/
$(function () {

    //这里需要复写parameterName;
    parameterName = 'dflag';
    loadData();
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    var tr;
    $("#addRole").click(function () {
        tr = $("<tr id='form'>\n" +
            " <td><input class='form-control' type='checkbox'   id='roleidNew' disabled  /></td>\n" +
            " <td><input class='form-control' type='text'  id='roleidNew' disabled  /></td>\n" +
            " <td><input class='form-control' type='text'  id='titleNew' name='title'/><span id='titleNewMsg'></span></td>\n" +
            " <td><input class='form-control' type='text'  id='flagNew' name='flag' /><span id='flagNewMsg'></span></td>\n" +
            " </tr>");
        $("#roletb").append(tr);
        this.disabled = true;
        $(this).text("一次只能增加一个角色");
        $("#addSure").show(2000);
        $("#addCancel").show(2000);
    });
    $("#addCancel").click(function () {
        tr.remove();
        addCancel();
    });
    $("#addOrRemoveRoles").click(function () {
        var flag = false;
        $("[id^=check]").each(function () {
            if (this.checked) {
                flag = true;
            }
        });
        if (flag) {
            loadMemberData();
            $("#roleWindow").modal("show");
        } else {
            showAlert($("#warningMsg"), "您还未选择任何角色")
        }


    });
    plAddRoleToMember();
    $("#formDiv").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            showLoadingData();
            $.ajax({
                url: "/pages/back/role/add",
                data: {
                    "title": $("#titleNew").val(),
                    "flag": $("#flagNew").val(),
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    hideLoadingData();
                    if (data) {
                        showAlert($("#successMsg"), "权限角色成功");
                        tr.find("input").val("");
                    } else {
                        showAlert($("#failureMsg"), "权限角色失败");
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
            flag: {
                required: true
            }
        }
    });

});

function loadMemberData() {
    $.ajax({
        url: "/pages/back/member/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": null,
            "currentPage": null,
            "parameterName": parameterName,
            "parameterValue": parameterValue
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            $("[name=members]").each(function () {
                $(this).remove();
            });
            var allVo = data.allVo;
            for (var x = 0; x < allVo.length; x++) {
                var mid = allVo[x].memberid;
                $("#member-row").after($(" <tr name=\"members\">\n" +
                    "                        <td><input type=\"checkbox\" class='form-control' id='selectItem' name='selectItem'  value='" + mid + "'/></td>\n" +
                    "                        <td><img style=\"height: 5rem;cursor: pointer\" name='" + allVo[x].bigphoto + "' src=\"" + allVo[x].photo + "\"/></td>\n" +
                    "                        <td >" + allVo[x].name + "</td>\n" +
                    "                        <td>" + mid + "</td>\n" +
                    "                    </tr>"));
            }
            selectAllMember();
            //为图片添加点击show大图事件
            showBigImg();
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();

        },
        error: function (e) {
        }
    });
}

function selectAllMember() {
    $("[id=selectAll]").each(function () {
        $(this).click(function () {
            var s = this.checked;
            $("[id=selectItem]").each(function () {
                this.checked = s;
            })
        })
    });
}

function selectAllRole() {
    $("[id=selectAllRole]").each(function () {
        $(this).click(function () {
            var s = this.checked;
            windowNW < 500 ? $("[id^=checkxs-]").each(function () {
                this.checked = s;
            }) : $("[id^=check-]").each(function () {
                this.checked = s;
            })
        })
    });
}

function plAddRoleToMember() {
    $("[id^=plRole]").each(function () {
        $(this).click(function () {
            var roleids = "";
            $("[id^=check]").each(function () {
                if (this.checked) {
                    roleids += this.value + "-";
                }
            });
            var memberids = "";
            $("[id=selectItem]").each(function () {
                if (this.checked) {
                    memberids += this.value + "-";
                }
            });
            var type = this.id == "plRoleAddToMember" ? "add" : "remove";
            if (memberids != "") {

                $.post("/pages/back/role/plAddRoleToMembersOrRemove", {
                    type: type,
                    roleids: roleids,
                    memberids: memberids
                }, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "角色分配成功")

                    } else {
                        showAlert($("#failureMsg"), "角色分配失败")

                    }
                }, "json");

            } else {
                showAlert($("#warningMsg"), "您还未选择任何用户")
            }
        })
    })

}
