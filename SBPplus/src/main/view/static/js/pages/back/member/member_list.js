//
function loadData() {
    $.ajax({
        url: "/pages/back/member/listAjax",
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
            $("[name=members]").each(function () {
                $(this).remove();
            });
            $("#shouji").empty();
            var allVo = data.allVo;
            for (var x = 0; x < allVo.length; x++) {
                var status;
                var mid = allVo[x].memberid;
                var color;
                var lock;
                var cls;
                var dele;
                var deleCls;

                if (allVo[x].sflag == 1 || allVo[x].sflag == 0) {
                    status = "正常";
                    color = "green";
                    lock = "锁定";
                    cls = "btn-warning";
                    dele = "删除";
                    deleCls = "btn-danger";
                } else if (allVo[x].sflag == 2) {
                    status = "已删除";
                    color = "red";
                    dele = "恢复";
                    deleCls = "btn-success";
                    cls = "btn-success";
                    lock = "解锁";
                } else if (allVo[x].sflag == 999) {
                    status = "已锁定";
                    color = "#F0AD4E";
                    lock = "解锁";
                    dele = "恢复";
                    cls = "btn-success";
                    deleCls = "btn-success";
                }

                $("#row1").after($(" <tr name=\"members\">\n" +
                    "                        <td><input type=\"checkbox\" class='form-control' id='selectItem' name='selectItem'  value='" + mid + "'/></td>\n" +
                    "                        <td><img style=\"height: 5rem;cursor: pointer\" name='" + allVo[x].bigphoto + "' src=\"" + allVo[x].photo + "\"/></td>\n" +
                    "                        <td>" + mid + "</td>\n" +
                    "                        <td >" + allVo[x].name + "</td>\n" +
                    "                        <td>" + allVo[x].phone + "</td>\n" +
                    "                        <td >" + allVo[x].age + "</td>\n" +
                    "                        <td >" + allVo[x].sex + "</td>\n" +
                    "                        <td id='stat-" + mid + "' style='color: " + color + "'>" + status + "</td>\n" +
                    "                        <td >" + new Date(allVo[x].regdate).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                    "                        <td>\n" +
                    "<button class=\"btn btn-xs btn-info\" style=\"margin: 0.2rem\" id='hisRole-" + mid + "'>他的角色</button>\n" +
                    "<button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + mid + "'>修改</button>\n" +
                    "<button class=\"btn btn-xs " + cls + "\" style=\"margin: 0.2rem\" id='lock-" + mid + "'>" + lock + "</button>\n" +
                    "<button class=\"btn btn-xs " + deleCls + "\" style=\"margin: 0.2rem\" id='dele-" + mid + "'>" + dele + "</button>\n" +
                    "                        </td>\n" +
                    "                    </tr>"));
                $("#shouji").append($("<table style='margin-bottom: 10rem' class=\"table  table-hover table-responsive visible-xs\">\n" +
                    "                        <tr>\n" +
                    "<td><input type=\"checkbox\" class=' form-control' id='selectItem-xs' name='selectItem' value=\"" + mid + "\"/></td>\n" +
                    "<td>照片:<img style=\"height: 5rem;cursor: pointer\" name='" + allVo[x].bigphoto + "' src=\"" + allVo[x].photo + "\"/></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "<td>用户名:" + mid + "</td>\n" +
                    "<td>姓名:" + allVo[x].name + "</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "<td >电话:" + allVo[x].phone + "</td>\n" +
                    "<td>年龄:" + allVo[x].age + "</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "<td>性别:" + allVo[x].sex + "</td>\n" +
                    "<td style='color: " + color + "' id='stat-" + mid + "' >状态:" + status + "</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "<td >注册日期:" + new Date(allVo[x].regdate).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                    "<td>\n" +
                    "    <button class=\"btn btn-xs btn-info\" style=\"margin: 0.2rem\" id='hisRole-" + mid + "'>他的角色</button>\n" +
                    "    <button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + mid + "'>修改</button>\n" +
                    "    <button class=\"btn btn-xs " + cls + "\" style=\"margin: 0.2rem\"  id='lock-" + mid + "'>" + lock + "</button>\n" +
                    "    <button class=\"btn btn-xs " + deleCls + "\" style=\"margin: 0.2rem\" id='dele-" + mid + "'>" + dele + "</button>\n" +
                    "</td>\n" +
                    "                        </tr>\n" +
                    "                    </table>"))
            }
            $("[id^='edit']").each(function () {//为编辑按钮添加点击事件
                $(this).click(function () {
                    var memberid = this.id.split("-")[1];
                    var url = "/pages/back/member/editPre?memberid=" + memberid;
                    window.location = (url);
                })
            });
            $("[id^='lock']").each(function () {//为锁定按钮添加点击事件
                $(this).click(function () {
                    var memberid = this.id.split("-")[1];
                    var bt = $(this);
                    var text = bt.text();
                    if (window.confirm("您确定要" + text + "此用户？")) {
                        if (text == "锁定") {
                            $.post("/pages/back/member/lockOrUnLock", {
                                memberid: memberid,
                                sflag: 999
                            }, function (data) {
                                bt.html("解锁");
                                bt.removeClass("btn-warning");
                                bt.addClass("btn-success");
                                var stat = $("[id=stat-" + memberid + "]");
                                stat.each(function () {
                                    $(this).html("已锁定");
                                });
                                stat.css({
                                    color: "#F0AD4E"
                                });
                            }, "json");
                        } else {
                            $.post("/pages/back/member/lockOrUnLock", {memberid: memberid, sflag: 1}, function (data) {
                                bt.html("锁定");
                                bt.removeClass("btn-success");
                                bt.addClass("btn-warning");
                                var stat = $("[id=stat-" + memberid + "]");
                                stat.each(function () {
                                    $(this).html("正常");
                                });
                                stat.css({
                                    color: "green"
                                });
                            }, "json");
                        }

                    }
                })
            });
            $("[id^='dele']").each(function () {//为删除按钮添加点击事件
                $(this).click(function () {
                    var memberid = this.id.split("-")[1];
                    var bt = $(this);
                    var text = bt.text();
                    $.post("/pages/back/member/delete", {memberid: memberid}, function (data) {

                        areYouSure("您确定要 " + text + " 此用户？", function () {
                            if (text == "删除") {
                                $.post("/pages/back/member/lockOrUnLock", {
                                    memberid: memberid,
                                    sflag: 2
                                }, function (data) {
                                    bt.html("恢复");
                                    bt.removeClass("btn-danger");
                                    bt.addClass("btn-success");
                                    var stat = $("[id=stat-" + memberid + "]");
                                    stat.each(function () {
                                        $(this).html("已删除");
                                    });
                                    stat.css({
                                        color: "red"
                                    });
                                }, "json");
                            } else {
                                $.post("/pages/back/member/lockOrUnLock", {
                                    memberid: memberid,
                                    sflag: 1
                                }, function (data) {
                                    bt.html("删除");
                                    bt.removeClass("btn-success");
                                    bt.addClass("btn-danger");

                                    var stat = $("[id=stat-" + memberid + "]");
                                    stat.each(function () {
                                        $(this).html("正常");
                                    });
                                    stat.css({
                                        color: "green"
                                    });
                                }, "json");
                            }
                        });

                    }, "json");
                })
            });
            $("[id^='hisRole-']").each(function () {
                $(this).click(function () {
                    var mid = this.id.split("-")[1];
                    $.get("/pages/back/member/getRolesByMemberid", {memberid: mid}, function (allRole) {
                        var row1 = $("#hisRole-row");
                        row1.nextAll().remove();
                        for (var x = 0; x < allRole.length; x++) {
                            var role = allRole[x];
                            var row = $("<tr >\n" +
                                "<td>" + role.roleid + "</td>\n" +
                                "<td>" + role.title + "</td>\n" +
                                "<td>" + role.flag + "</td>\n" +
                                " </tr>");
                            row1.after(row);
                        }
                    }, "json");
                    $("#hisRoleWindow").modal("show");

                })
            });
            selectAll();
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


// /*]]>*/
$(function () {
    $("[id=checkAllRole]").each(function () {
        $(this).click(function () {
            var s = this.checked;
            $("[id^=check-]").each(function () {
                this.checked = s;
            })

        });
    });

    loadData();
    $("[id=status]").each(function () {
        $(this).change(function () {
            parameterValue = this.value;
            currentPage = 1;//重新加载数据当前页变为1
            loadData();
        });
    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    //批量锁
    $("[id=plSuo]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/member/plSuoOrJie", {data: str, sflag: 999}, function (data) {

                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=stat-" + ids[x] + "]");
                        var lockBut = $("[id=lock-" + ids[x] + "]");
                        lockBut.each(function () {
                            $(this).html("解锁");
                            $(this).removeClass("btn-warning");
                            $(this).addClass("btn-success");
                        });
                        stat.each(function () {
                            $(this).html("已锁定");
                        });
                        stat.css({
                            color: "#F0AD4E"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
            }
        })
    });
    //批量解锁
    $("[id=plJie]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/member/plSuoOrJie", {data: str, sflag: 1}, function (data) {
                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=stat-" + ids[x] + "]");
                        var lockBut = $("[id=lock-" + ids[x] + "]");
                        lockBut.each(function () {
                            $(this).html("锁定");
                            $(this).removeClass("btn-success");
                            $(this).addClass("btn-warning");
                        });
                        stat.each(function () {
                            $(this).html("正常");
                        });
                        stat.css({
                            color: "green"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
            }
        })
    });
    //批量恢复
    $("[id=plHui]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/member/plSuoOrJie", {data: str, sflag: 1}, function (data) {
                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=stat-" + ids[x] + "]");
                        var lockBut = $("[id=dele-" + ids[x] + "]");
                        lockBut.each(function () {
                            $(this).html("删除");
                            $(this).removeClass("btn-success");
                            $(this).addClass("btn-danger");
                        });
                        stat.each(function () {
                            $(this).html("正常");
                        });
                        stat.css({
                            color: "green"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
            }
        })
    });
    //批量删除
    $("[id=plShan]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/member/plSuoOrJie", {data: str, sflag: 2}, function (data) {
                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=stat-" + ids[x] + "]");
                        var lockBut = $("[id=dele-" + ids[x] + "]");
                        lockBut.each(function () {
                            $(this).html("恢复");
                            $(this).removeClass("btn-danger");
                            $(this).addClass("btn-success");
                        });
                        stat.each(function () {
                            $(this).html("已删除");
                        });
                        stat.css({
                            color: "red"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
            }
        })
    });
    $("[id=plAddRole]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                loadRoleData();
                $("#addRoleWindow").modal("show")
            } else {
                noSelectMsg();
            }
        });
    });

    $("[id^=plRole]").each(function () {
        $(this).click(function () {
            var type;
            if (this.id == 'plRoleAddToMember') {
                type = "add";
            } else {
                type = "remove";
            }

            var str = "";
            $("[name^=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "-";
                }
            });
            var roleids = "";
            $("[id^=check-" + "]").each(function () {
                if (this.checked) {
                    roleids += this.id.split("-")[1] + "-"
                }
            });
            if (roleids != "") {
                $.post("/pages/back/role/plAddRoleToMembersOrRemove", {
                    type: type,
                    roleids: roleids,
                    memberids: str
                }, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "角色分配成功！");
                    } else {
                        showAlert($("#failureMsg"), "角色分配失败！");

                    }
                }, "json");
            } else {
                showAlert($("#warningMsg"), "您还未选择任何数据");
            }

        });
    });

});

function loadRoleData() {
    $.ajax({
        url: "/pages/back/role/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": null,
            "currentPage": currentPage,
            "parameterName": parameterName,
            "parameterValue": parameterValue
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var allroles = data.allVo;
            var actb = $("#roletb");
            $("#role-row").nextAll().remove();
            for (var x = 0; x < allroles.length; x++) {
                var role = allroles[x];
                var roleid = role.roleid;
                actb.append($("<tr id='row-" + roleid + "'>\n" +
                    " <td><input type='checkbox' class='checkbox-inline form-control'  id='check-" + roleid + "' value='" + roleid + "'/></td>\n" +
                    " <td id='actionid-" + roleid + "'>" + roleid + "</td>\n" +
                    " <td  >" + role.title + "</td>\n" +
                    " <td>" + role.flag + "</td>\n" +
                    "  </tr>"));
            }
        }
        ,
        error: function (e) {
        }
    });
}

