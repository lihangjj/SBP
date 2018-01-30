//
function loadData() {
    $.ajax({
        url: "/pages/front/post/listAjax",
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
            var allPost = data.allVo;
            var row1 = $("#row1");
            row1.nextAll().remove();
            var shouji = $("#shouji");
            for (var x = 0; x < allPost.length; x++) {
                var post = allPost[x];
                var postid = post.postid;
                var status = post.dflag == 0 ? "招聘中" : "已停招";
                var statusColor = post.dflag == 0 ? "green" : "red";
                var cls = post.dflag == 0 ? "btn-danger" : "btn-success";
                var butText = post.dflag == 0 ? "停招" : "恢复";

                row1.after($("<tr>\n" +
                    " <td id='name-" + postid + "'>" + post.name + "</td>\n" +
                    " <td id='duty-" + postid + "'>" + post.duty + "</td>\n" +
                    " <td id='pubDate-" + postid + "'>" + new Date(post.pubDate).format("yyyy-MM-dd") + "</td>\n" +
                    " <td   id='experience-" + postid + "'>" + post.experience + "</td>\n" +
                    " <td  id='note-" + postid + "'>" + post.note + "</td>\n" +
                    " <td  id='salRange-" + postid + "'>" + post.salRange + "</td>\n" +
                    " <td id='welfare-" + postid + "'> " + post.welfare + "</td>\n" +
                    "  <td  id='workLoc-" + postid + "'>" + post.workLoc + "</td>\n" +
                    " <td  id='recruits-" + postid + "'>" + post.recruits + "</td>\n" +
                    "  <td  id='education-" + postid + "'>" + post.education + "</td>\n" +
                    " <td  id='other-" + postid + "'>" + post.other + "</td>\n" +
                    "   <td  id='phone-" + postid + "'>" + post.phone + "</td>\n" +
                    "   <td >" + post.member.name + "</td>\n" +
                    "   <td  style='color: " + statusColor + "'>" + status + "</td>\n" +
                    "   <td  id='postid-" + postid + "'><button id='" + post.postid + "' name='status' class='btn " + cls + "'>" + butText + "</button>\n" +
                    "   <button style='margin-top: 1rem' id='edit-" + post.postid + "'  class='btn btn-primary'>修改</button></td>\n" +
                    " </tr>"));

                shouji.append($("<table class=\"table table-bordered table-hover visible-xs\" style=\"min-height: 50rem;\">\n" +
                    "<tr>\n" +
                    "    <td><label>现招岗位:</label></td> <td id='name-" + postid + "'>" + post.name + "</td>\n" +
                    "</tr>" +
                    "<tr>\n" +
                    "    <td><label>岗位职责:</label></td> <td id='duty-" + postid + "'>" + post.duty + "</td>\n" +
                    "</tr>" + "<tr>\n" +
                    "    <td><label>发布日期:</label></td> <td id='pubDate-" + postid + "'>" + post.pubDate + "</td>\n" +
                    "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>工作经验:</label></td> <td id='experience-" + postid + "'>" + post.experience + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>任责要求:</label></td> <td id='note-" + postid + "'>" + post.note + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>薪资范围:</label></td> <td id='salRange-" + postid + "'>" + post.salRange + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>相关福利:</label></td> <td id='welfare-" + postid + "'>" + post.welfare + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>工作地点:</label></td> <td id='workLoc-" + postid + "'>" + post.workLoc + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>招聘人数:</label></td> <td id='recruits-" + postid + "'>" + post.recruits + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>学历要求:</label></td> <td id='education-" + postid + "'>" + post.education + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>其他说明:</label></td> <td id='other-" + postid + "'>" + post.other + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>联系电话:</label></td> <td id='phone-" + postid + "'>" + post.phone + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>发布者:</label></td> <td>" + post.member.name + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>状态:</label></td> <td id='stat-"+postid+"' style='color: " + statusColor + "'>" + status + "</td>\n" +
                    "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "</tr>" + "<tr>\n" +
                    "    <td><label>操作:</label></td>" +
                    "<td>" +
                    "<button id='" + post.postid + "' name='status' class='btn " + cls + "'>" + butText + "</button> " +
                    "<button  id='edit-" + post.postid + "'  class='btn btn-primary'>修改</button>" +
                    "</td>\n" +
                    "</tr>" +
                    "</table>"))


            }
            $("[name=status]").each(function () {
                $(this).click(function () {
                    var butText = $(this).text();
                    var id = this.id;
                    var dflag = butText == '停招' ? 1 : 0;
                    var curenntObj = $(this);
                    $.ajax({
                        url: "/pages/back/post/editDflag",
                        data: {
                            postid: id,
                            dflag: dflag
                        },
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data) {
                                var status;
                                if (windowNW < 500) {
                                    status =$("#stat-"+id);

                                } else {
                                    status = curenntObj.parent().prev();


                                }
                                if (dflag == 1) {
                                    status.html("已停招");
                                    status.css({color: "red"});
                                    curenntObj.removeClass("btn-danger");
                                    curenntObj.addClass("btn-success");
                                    curenntObj.text("恢复");
                                } else {
                                    status.html("招聘中");
                                    status.css({color: "green"});
                                    curenntObj.removeClass("btn-success");
                                    curenntObj.addClass("btn-danger");
                                    curenntObj.text("停招");
                                }

                            }
                        },
                        error: function (e) {
                        }
                    });
                })
            });

            $("[id^=edit-]").each(function () {
                $(this).click(function () {
                    $.get("/pages/back/post/editPre", {postid: this.id.split("-")[1]}, function (data) {
                        $("#postid").val(data.postid);
                        $("#name").val(data.name);
                        $("#duty").val(data.duty);
                        $("#note").val(data.note);
                        $("#welfare").val(data.welfare);
                        $("#salRange").val(data.salRange);
                        $("#workLoc").val(data.workLoc);
                        $("#recruits").val(data.recruits);
                        $("#experience").val(data.experience);
                        $("#education").val(data.education);
                        $("#other").val(data.other);
                        $("#phone").val(data.phone);
                        $("#pubDate").val(new Date(data.pubDate).format("yyyy-MM-dd"));
                        $("#modalWindow").modal("show");
                    }, "json");
                })
            });
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
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
    $("#status").change(function () {
        parameterValue = this.value;
        loadData();
    });
    loadData();
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
});



