//
function loadData() {

    $.ajax({
        url: "/pages/back/cost/myCostListAjax",
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
            $("[name=costRow]").each(function () {
                $(this).remove();
            });
            $("#shouji").empty();
            var allVo = data.allVo;
            $("#currentAllCost").text(data.allCostMoney);
            for (var x = 0; x < allVo.length; x++) {
                var status;
                var costid = allVo[x].costid;
                var color;
                var dele;
                var deleCls;
                var nobx = "未报销";
                var areadybx = "已报销";
                var bxstatus = (allVo[x].bxflag) == 0 ? nobx : areadybx;
                var bxtext = (allVo[x].bxflag) == 0 ? areadybx : nobx;
                var bxcolor = (allVo[x].bxflag) == 0 ? "red" : "green";
                var bxCls = (allVo[x].bxflag) == 0 ? "btn-success" : "btn-danger";

                if (allVo[x].dflag != 1) {
                    status = "已记录";
                    color = "green";
                    dele = "删除";
                    deleCls = "btn-danger";
                } else {
                    status = "已删除";
                    color = "red";
                    dele = "恢复";
                    deleCls = "btn-success";
                }
                $("#row1").after($(" <tr name=\"costRow\">\n" +
                    "                        <td><input type=\"checkbox\" id='selectItem' name='selectItem' class='form-control'  value='" + costid + "'/></td>\n" +
                    "                        <td>" + allVo[x].title + "</td>\n" +
                    "                        <td>" + allVo[x].member.name + "</td>\n" +
                    "                        <td>" + allVo[x].amount + "</td>\n" +
                    "                        <td >" + allVo[x].cost + "</td>\n" +
                    "                        <td ><img style=\"height: 5rem;cursor: pointer\" name='" + allVo[x].bigphoto + "' src=\" " + allVo[x].photo + "\"/></td>\n" +
                    "                        <td >" + allVo[x].note + "</td>\n" +
                    "                        <td >" + new Date(allVo[x].time).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                    "                        <td style='color: " + color + "' id='stat-" + costid + "'>" + status + "</td>\n" +
                    "                        <td style='color: " + bxcolor + "' id='bxstat-" + costid + "'>" + bxstatus + "</td>\n" +
                    "                        <td>\n" +
                    "                            <button class=\"btn btn-xs " + bxCls + "\" style=\"margin: 0.2rem\" id='bx-" + costid + "'>" + bxtext + "</button>\n" +
                    "                            <button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + costid + "'>修改</button>\n" +
                    "                            <button class=\"btn btn-xs " + deleCls + "\" style=\"margin: 0.2rem\" id='dele-" + costid + "'>" + dele + "</button>\n" +
                    "                        </td>\n" +
                    "                    </tr>"));
                $("#shouji").append($("<table style='margin-bottom: 10rem' class=\"table table-hover table-responsive visible-xs\">\n" +
                    "                        <tr>\n" +
                    "                            <td><input type=\"checkbox\" id='selectItem-xs' class='form-control' name='selectItem' value=\"" + costid + "\"/><td>" + allVo[x].title + "</td></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td>属于:" + allVo[x].member.name + "</td>\n" +
                    "                            <td>数量:" + allVo[x].amount + "</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td >金额:" + allVo[x].cost + "</td>\n" +
                    "                            <td>凭证:<img style=\"height: 5rem;cursor: pointer\" name='" + allVo[x].bigphoto + "' src=\" " + allVo[x].photo + "\"/></td>\n" +
                    "                        </tr>\n" +

                    "                        <tr>\n" +
                    "                            <td>时间:" + new Date(allVo[x].time).format("yyyy-MM-dd") + "</td>\n" +
                    "                            <td style='color: " + color + "' id='stat-" + costid + "'>" + status + "</td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td>说明:" + allVo[x].note + "</td>\n" +
                    "                        </tr>\n" + "                        " +
                    "                           <tr>\n" +
                    "                            <td >报销与否:<span style='color: " + bxcolor + "' id='bxstat-" + costid + "'>" + bxstatus + "</span></td>\n" +
                    "                        </tr>\n" +

                    "                        <tr>\n" +
                    "                            <td>\n" +
                    "                            <button class=\"btn btn-xs " + bxCls + "\" style=\"margin: 0.2rem\" id='bx-" + costid + "'>" + bxtext + "</button>\n" +
                    "                                <button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + costid + "'>修改</button>\n" +
                    "                                <button class=\"btn btn-xs " + deleCls + "\" style=\"margin: 0.2rem\" id='dele-" + costid + "'>" + dele + "</button>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>"));

            }
            $("[id^='edit']").each(function () {//为编辑按钮添加点击事件
                $(this).click(function () {
                    var costid = this.id.split("-")[1];
                    $.post(" /pages/back/cost/editPre", {costid: costid}, function (data) {
                        $("[name=title]").val(data.title);
                        $("#amount").val(data.amount);
                        $("#cost").val(data.cost);
                        $("#costid").val(data.costid);
                        $("#bxflag").val(data.bxflag);
                        $("#preview").attr("src", data.photo);
                        $("#preview").attr("name", data.bigphoto);
                        $("#note").text(data.note);
                        $("#photo").val(data.photo);
                        $("#bigphoto").val(data.bigphoto);
                        $("#time").val(new Date(data.time).format("yyyy-MM-dd"));
                        $("#editCostPre").modal("show");

                    }, "json");
                })
            });
            //为删除按钮添加点击事件
            $("[id^='dele']").each(function () {
                $(this).click(function () {
                    var costid = this.id.split("-")[1];
                    var bt = $(this);
                    var text = bt.text();
                    if (window.confirm("您确定要" + text + "此记录？")) {

                        if (text == "删除") {
                            $.post("/pages/back/cost/deleteOrRestore", {
                                costid: costid,
                                dflag: 1
                            }, function (data) {
                                bt.html("恢复");
                                bt.removeClass("btn-danger");
                                bt.addClass("btn-success");
                                var stat = $("[id=stat-" + costid + "]");
                                stat.each(function () {
                                    $(this).html("已删除");
                                });
                                stat.css({
                                    color: "red"
                                });
                            }, "json");
                        } else {
                            $.post("/pages/back/cost/deleteOrRestore", {
                                costid: costid,
                                dflag: 0
                            }, function (data) {
                                bt.html("删除");
                                bt.removeClass("btn-success");
                                bt.addClass("btn-danger");

                                var stat = $("[id=stat-" + costid + "]");
                                stat.each(function () {
                                    $(this).html("已记录");
                                });
                                stat.css({
                                    color: "green"
                                });
                            }, "json");
                        }
                    }
                })
            });


            //为报销按钮添加点击事件
            $("[id^='bx-']").each(function () {
                $(this).click(function () {
                    var costid = this.id.split("-")[1];
                    var bt = $(this);
                    var text = bt.text();
                    if (window.confirm("您确定要" + text + "此记录？")) {

                        if (text == "已报销") {
                            $.post("/pages/back/cost/bxOrNobx", {
                                costid: costid,
                                bxflag: 1
                            }, function (data) {
                                bt.html("未报销");
                                bt.removeClass("btn-success");
                                bt.addClass("btn-danger");
                                var stat = $("[id=bxstat-" + costid + "]");
                                stat.each(function () {
                                    $(this).html("已报销");
                                });
                                stat.css({
                                    color: "green"
                                });
                            }, "json");
                        } else {
                            $.post("/pages/back/cost/bxOrNobx", {
                                costid: costid,
                                bxflag: 0
                            }, function (data) {
                                bt.html("已报销");
                                bt.removeClass("btn-danger");
                                bt.addClass("btn-success");

                                var stat = $("[id=bxstat-" + costid + "]");
                                stat.each(function () {
                                    $(this).html("未报销");
                                });
                                stat.css({
                                    color: "red"
                                });
                            }, "json");
                        }
                    }
                })
            });

            selectAll();
            //为图片添加点击show大图事件
            showBigImg();
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}



// /*]]>*/
$(function () {

    //这里需要复写parameterName;
    parameterName = 'dflag';
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
                $.post("/pages/back/cost/pldeleteOrRestore", {data: str, dflag: 0}, function (data) {
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
                $.post("/pages/back/cost/pldeleteOrRestore", {data: str, dflag: 1}, function (data) {
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
    });//批量恢复
    //批量报销
    $("[id=plBx]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/cost/plbxOrNobx", {data: str, bxflag: 1}, function (data) {
                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=bxstat-" + ids[x] + "]");
                        var bxBut = $("[id=bx-" + ids[x] + "]");
                        bxBut.each(function () {
                            $(this).html("未报销");
                            $(this).removeClass("btn-success");
                            $(this).addClass("btn-danger");
                        });
                        stat.each(function () {
                            $(this).html("已报销");
                        });
                        stat.css({
                            color: "green"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
                // showAlert($("#warningMsg"), '您还没有选择任何数据');
            }
        })
    });
    //批量不报
    $("[id=plBB]").each(function () {
        $(this).click(function () {
            var str = "";
            $("[name=selectItem]").each(function () {
                if (this.checked) {
                    str += this.value + "|";
                }
            });
            if (str != "") {
                $.post("/pages/back/cost/plbxOrNobx", {data: str, bxflag: 0}, function (data) {
                    var ids = str.split("|");
                    for (var x = 0; x < ids.length; x++) {
                        var stat = $("[id=bxstat-" + ids[x] + "]");
                        var bxBut = $("[id=bx-" + ids[x] + "]");
                        bxBut.each(function () {
                            $(this).html("已报销");
                            $(this).removeClass("btn-danger");
                            $(this).addClass("btn-success");
                        });
                        stat.each(function () {
                            $(this).html("未报销");
                        });
                        stat.css({
                            color: "red"
                        });
                    }
                }, "json")
            } else {
                noSelectMsg();
                // showAlert($("#warningMsg"), '您还没有选择任何数据');
            }
        })
    });

});



