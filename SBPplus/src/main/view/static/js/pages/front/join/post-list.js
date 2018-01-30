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
            for (var x = 0; x < allPost.length; x++) {
                var post = allPost[x];
                row1.after($("<tr>\n" +
                    " <td>" + post.name + "</td>\n" +
                    " <td>" + post.duty + "</td>\n" +
                    " <td>" + new Date(post.pubDate).format("yyyy-MM-dd") + "</td>\n" +
                    " <td>" + post.experience + "</td>\n" +
                    " <td>" + post.note + "</td>\n" +
                    " <td>" + post.salRange + "</td>\n" +
                    " <td>" + post.welfare + "</td>\n" +
                    "  <td>" + post.workLoc + "</td>\n" +
                    " <td>" + post.recruits + "</td>\n" +
                    "  <td>" + post.education + "</td>\n" +
                    " <td>" + post.other + "</td>\n" +
                    "   <td>" + post.phone + "</td>\n" +
                    " </tr>"))
            }
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
    parameterValue = 0;
    loadData();
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);

});



