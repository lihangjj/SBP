//
function loadData() {

    $.ajax({
        url: "/pages/back/client/listAjax",
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
            $("[name=clientRow]").each(function () {
                $(this).remove();
            });
            $("#shouji").empty();
            var allVo = data.allVo;
            for (var x = 0; x < allVo.length; x++) {
                var dFlag = allVo[x].dealFlag;
                var clientid = allVo[x].clientid;
                var textColor = dFlag == 0 ? "red" : "green";
                var textDeal = dFlag == 0 ? "未处理" : "已处理";
                var butCls = dFlag == 0 ? "btn-success" : "btn-danger";
                var butText = dFlag == 0 ? "已处理" : "未处理";

                $("#row1").after($(" <tr name=\"clientRow\">\n" +
                    "                        <td ><textarea id='claim-" + clientid + "'  class='form-control'>" + allVo[x].claim + "</textarea></td>\n" +
                    "                        <td><input type='text' id='name-" + clientid + "' class='form-control' value='" + allVo[x].name + "'/></td>\n" +
                    "                        <td><input type='text' id='phone-" + clientid + "' class='form-control' value='" + allVo[x].phone + "'/></td>\n" +
                    "                        <td><input type='text' id='budget-" + clientid + "'  class='form-control' value='" + allVo[x].budget + "'/></td>\n" +
                    "                        <td><input type='text'  disabled class='form-control' value='" + new Date(allVo[x].pubdate).format("yyyy-MM-dd ") + "'/></td>\n" +
                    "                        <td ><textarea  id='note-" + clientid + "'  class='form-control'>" + allVo[x].note + "</textarea></td>\n" +
                    "                        <td><input type='text' id='address-" + clientid + "'  class='form-control' value='" + allVo[x].address + "'/></td>\n" +
                    "                        <td><input type='text' id='company-" + clientid + "'  class='form-control' value='" + allVo[x].company + "'/></td>\n" +
                    "                        <td style='color: " + textColor + "' id='stat-" + clientid + "'>" + textDeal + "</td>\n" +
                    "                        <td>\n" +
                    "                            <button class=\"btn btn-xs " + butCls + "\" style=\"margin: 0.2rem\" id='deal-" + clientid + "'>" + butText + "</button>\n" +
                    "                            <button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + clientid + "'>修改</button>\n" +
                    "                        </td>\n" +
                    "                    </tr>"));


                $("#shouji").append($("<table style='margin-bottom: 10rem;' class=\"table  table-hover table-responsive visible-xs\">\n" +
                    "                        <tr>" +
                    "<td >需求编号:" + clientid +" </td>\n" +
                    "<td  >要求<textarea class='form-control' id='claim-" + clientid + "' >" + allVo[x].claim + "</textarea></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                        <td>客户:<input type='text' id='name-" + clientid + "' name='name' class='form-control' value='" + allVo[x].name + "'/></td>\n" +
                    "                        <td>电话:<input type='text' id='phone-" + clientid + "' class='form-control' value='" + allVo[x].phone + "'/></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                        <td>预算:<input type='text'  id='budget-" + clientid + "'  class='form-control' value='" + allVo[x].budget + "'/></td>\n" +
                    "                        <td>单位:<input type='text' id='company-" + clientid + "'  class='form-control' value='" + allVo[x].company + "'/></td>\n" +
                    "                        </tr>\n" +

                    "                        <tr>\n" +
                    "                        <td>发布时间:<input type='text'    disabled class='form-control' value='" + new Date(allVo[x].pubdate).format("yyyy-MM-dd ") + "'/></td>\n" +
                    "                        <td>地址:<input type='text' id='address-" + clientid + "'  class='form-control' value='" + allVo[x].address + "'/></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                        <td >备注:<textarea class='form-control' id='note-" + clientid + "'>" + allVo[x].note + "</textarea></td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td >处理与否:<span style='color: " + textColor + "' id='stat-" + clientid + "'>" + textDeal + "</span></td>\n" +
                    "                            <td>\n" +
                    "                            <button class=\"btn btn-xs " + butCls + "\" style=\"margin: 0.2rem\" id='deal-" + clientid + "'>" + butText + "</button>\n" +
                    "                                <button class=\"btn btn-xs btn-primary\" style=\"margin: 0.2rem\" id='edit-" + clientid + "'>修改</button>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +

                    "                    </table>"
                ))
                ;

            }
            $("[id^='edit']").each(function () {//为编辑按钮添加点击事件
                $(this).click(function () {
                    var clientid = this.id.split("-")[1];
                    var type = windowNW < 768 ? 1 : 0;
                    $.post(" /pages/back/client/edit", {
                        clientid: clientid,
                        claim: $("[id=claim-" + clientid + "]").get(type).value,
                        phone: $("[id=phone-" + clientid + "]").get(type).value,
                        budget: $("[id=budget-" + clientid + "]").get(type).value,
                        name: $("[id=name-" + clientid + "]").get(type).value,
                        address: $("[id=address-" + clientid + "]").get(type).value,
                        company: $("[id=company-" + clientid + "]").get(type).value,
                        note: $("[id=note-" + clientid + "]").get(type).value
                    }, function (data) {
                        data == true ? showAlert($("#successMsg"), "客户信息修改成功") : showAlert($("#failureMsg"), "客户信息修改失败")
                    }, "json");
                })
            });

            //为处理按钮添加点击事件
            $("[id^='deal-']").each(function () {
                $(this).click(function () {
                    var clientid = this.id.split("-")[1];
                    var bt = $(this);
                    var text = bt.text();
                    if (text == "已处理") {
                        $.post("/pages/back/client/dealOrNoDeal", {
                            clientid: clientid,
                            dealFlag: 1
                        }, function (data) {
                            bt.html("未处理");
                            bt.removeClass("btn-success");
                            bt.addClass("btn-danger");
                            var stat = $("[id=stat-" + clientid + "]");
                            stat.each(function () {
                                $(this).html("已处理");
                            });
                            stat.css({
                                color: "green"
                            });
                        }, "json");
                    } else {
                        $.post("/pages/back/client/dealOrNoDeal", {
                            clientid: clientid,
                            dealFlag: 0
                        }, function (data) {
                            bt.html("已处理");
                            bt.removeClass("btn-danger");
                            bt.addClass("btn-success");
                            var stat = $("[id=stat-" + clientid + "]");
                            stat.each(function () {
                                $(this).html("未处理");
                            });
                            stat.css({
                                color: "red"
                            });
                        }, "json");
                    }
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


// /*]]>*/
$(function () {
    //这里需要复写parameterName;
    parameterName = 'dealFlag';
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

});



