// /*<![CDATA[*/
//
// var ls = [[${lineSize}]];
// var kw = [[${keyWord}]] == null ? "" : [[${keyWord}]];
// var cl = [[${column}]] == null ? "" : [[${column}]];
// console.log(kw);
var windowH = window.innerHeight;
var windowNW = window.innerWidth;
var windowOw = windowNW;
var minL;
var rem;

$(window).resize(function () {
    windowNW = window.innerWidth;
    windowH = window.innerHeight;
    initPublic();//初始化字体
});

$(function () {//公共操作函数这样写就必须要写window.onload
    initPublic();//初始化字体
    //加载完成之后显示body
    $("body").css({
        visibility: "visible"
    });
    //验证码点击换图
    $("[name=rand]").each(function () {
        $(this).click(function () {
            $(this).attr("src", "/defaultKaptcha")
        })
    });//点击换验证码

});

//确定对话框
function areYouSure(msg, execute) {
    var sureModal = $("<div id=\"sure\" class=\"modal fade\" aria-labelledby=\"title\" aria-hidden=\"true\" tabindex=\"-1\">\n" +
        "            <div class=\"modal-dialog\" style=\"top: 30%;\">\n" +
        "                <div class=\"modal-content\" style=\"z-index: 9999\">\n" +
        "                    <div class=\"modal-header\">\n" +
        "                        <button class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
        "                        <span id=\"title\">利作温馨提醒您：</span>\n" +
        "                    </div>\n" +
        "                    <div class=\"modal-body\" style=\"text-align: center\">\n" +
        "                        <label>" + msg + "</label>\n" +
        "                    </div>\n" +
        "                    <div class=\"modal-footer\">\n" +
        " <small style=\"text-align: center;font-size: 1rem;color: gray\">Esc键、或单机灰色区域关闭窗口</small>" +
        "                        <button class=\"btn btn-danger\" id=\"yes\">确定</button>\n" +
        "                        <button class=\"btn btn-primary\" data-dismiss=\"modal\">取消</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>");
    $("body").prepend(sureModal);
    var sureM = $("#sure");
    var yes = $("#yes");
    sureM.modal("show");
    yes.click(function () {
        sureM.modal("hide");
        execute();
    });
    yes.next().click(function () {
        sureM.modal("hide");
        return false;
    })

}

//初始化字体大小
function initPublic() {
    minL = Math.min(windowH, windowNW);//最小一边长度
    rem = minL < 500 ? minL * 0.014 : minL * 0.01;//手机还是其他
    $("html").css({
        "font-size": rem
    });

}

//日期格式化函数
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
// var time1 = new Date(longTime).format("yyyy-MM-dd hh:mm:ss");
// console.log(time1);
//2s隐藏alert
var hideAlert;
var setT;

function showAlert(type, msg) {
    type.html(msg);
    type.parent().siblings().css("display", "none");
    type.parent().css("display", "block");
    $("#alertM").modal("show");
    //取消之前的到时设置
    clearInterval(hideAlert);
    hideAlert = setTimeout(function () {
        //这里还要将对应的提示类型再次变为none
        $("#alertM").modal("hide");
    }, 2000);

}

function alertMsg(obj, msg) {
    var span = obj.next();
    clearInterval(setT);
    span.popover({
        title: "温馨提示：",
        content: "<span style='color:#d93fff'>" + msg + "</span>",
        placement: "auto",
        html: true
    });
    span.popover("show");
    setT = setTimeout(function () {
        span.popover("hide");
    }, 2000)
}

function noSelectMsg() {
    var x = Math.random();
    var msg = "您还未选择任何数据";
    x < 0.5 ? showAlert($("#warningMsg"), msg) : alertMsg($("[id=selectAll]"), msg);
}

//展现数据加载
function showLoadingData() {
    $("#loadingData").css({
        display: "block"
    })
}
//隐藏数据加载
function hideLoadingData() {
    $("#loadingData").css({
        display: "none"
    })
}

//为所有图片增加展示大图的函数
function showBigImg() {
    $("img").each(function () {
        $(this).click(function () {
            var bigImgUrl = $(this).attr("name");
            if (bigImgUrl != undefined) {//如果有大图片路径，就显示大图片路径，否则显示缩略图
                $("#bigImg").attr("src", bigImgUrl);
            } else {
                $("#bigImg").attr("src", $(this).attr("src"));
            }
            $("#showBigImg").modal("show");
        });
    });
}

//图片上传预览功能
function imgPreview(fileDom) {
    var img = document.getElementById("preview");

    //判断是否支持FileReader
    if (window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }
    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if (!imageType.test(file.type)) {

        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function (e) {
        //获取图片dom
        //图片路径设置为读取的图片
        img.src = e.target.result;
        //加上大图路径
        img.name = e.target.result;
    };
    reader.readAsDataURL(file);
}




//设置cookie
var cookie = {
    set: function (key, val, time) {//设置cookie方法
        var date = new Date(); //获取当前时间
        var expiresDays = time;  //将date设置为n天以后的时间
        date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000); //格式化为cookie识别的时间
        document.cookie = key + "=" + val + ";expires=" + date.toGMTString() + ";path=/";  //设置cookie
    },
    get: function (key) {//获取cookie方法
        /*获取cookie参数*/
        var getCookie = document.cookie.replace(/[ ]/g, "");  //获取cookie，并且将获得的cookie格式化，去掉空格字符
        var arrCookie = getCookie.split(";"); //将获得的cookie以"分号"为标识 将cookie保存到arrCookie的数组中
        var tips;  //声明变量tips
        for (var i = 0; i < arrCookie.length; i++) {   //使用for循环查找cookie中的tips变量
            var arr = arrCookie[i].split("=");   //将单条cookie用"等号"为标识，将单条cookie保存为arr数组
            if (key == arr[0]) {  //匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
                tips = arr[1];   //将cookie的值赋给变量tips
                break;   //终止for循环遍历
            }
        }
        return tips;
    }
    ,
    delete: function (key) { //删除cookie方法
        var date = new Date(); //获取当前时间
        date.setTime(date.getTime() - 10000); //将date设置为过去的时间
        document.cookie = key + "=v; expires =" + date.toGMTString() + ";path=/";//设置cookie,要加路径为/
    }
};


//全选，手机和电脑端

function selectAll() {
    $("[id=selectAll]").each(function () {
        $(this).click(function () {
            var s = this.checked;
            if (windowNW<500){
                $("[id=selectItem-xs]").each(function () {
                    this.checked = s;
                })
            }else {
                $("[id=selectItem]").each(function () {
                    this.checked = s;
                })
            }

        })
    });
}


/**
 * 分页组件
 */

var pageSize = 1;
var allRecorders;
var column = "";
var keyWord = "";
var lineSize = 8;
var currentPage = 1;
var parameterName = "sflag";
var parameterValue;

//检索按钮，需要传入加载数据的loadData函数
function jiansuo(functionName) {
    $("#jiansuo").click(function () {
        column = $("#column").val();
        keyWord = $("#keyWord").val();
        currentPage = 1;
        functionName();
    });
}

//回车键提交操作搜索
function enterKeySubmit(loadData) {
    $("#keyWord").keypress(function (e) {
        if (e.keyCode == 13) {//回车键
            e.preventDefault();//阻止回车键表单提交
            column = $("#column").val();
            keyWord = $("#keyWord").val();
            currentPage = 1;
            loadData();
        }
    });
}

//得到总页数
function getPageSize(allRecorders) {//计算总页数
    if (allRecorders == 0) {
        pageSize = 1;
    } else {
        if("null"==lineSize||lineSize==null||""==lineSize){
            pageSize=1;
        }else {
            pageSize = Math.ceil(allRecorders / lineSize);

        }
    }
}

//创建单个分页按钮
function addBar(index) {
    var liObj = $("<li></li>");
    var aObj = $("<a style='cursor:pointer'>" + index + "</a>");
    if (index == currentPage) {
        liObj.addClass("active");
    } else {
        aObj.on({
            "click": function () {
                currentPage = index;
                loadData();
            }
        });
    }
    liObj.append(aObj);
    $("#splitBar").append(liObj);
}

//创建分页条
function createSplitBar(allRecorders) {
    getPageSize(allRecorders);
    editAllRecordersAndPageSize();
    $("#splitBar").empty();//清空分页条
    $("#daomei").empty();//清空分页条
    upPage();
    addBar(1);
    if (pageSize < 10) {
        for (var x = 2; x <= pageSize; x++) {
            addBar(x);
        }
        nextPage();
    } else {
        if (currentPage > 5) {
            sandian();
            if (parseInt(currentPage) + 5 > pageSize) {
                for (var x = currentPage - 3; x < pageSize; x++) {
                    addBar(x);
                }
            } else {
                for (var x = currentPage - 3; x < parseInt(currentPage) + 4; x++) {
                    addBar(x);
                }
                sandian();

            }
            addBar(pageSize);
            nextPage()
        } else {

            if (parseInt(currentPage) + 3 > pageSize) {
                for (var x = 2; x <= pageSize; x++) {
                    addBar(x);
                }
            } else {
                for (var x = 2; x <= parseInt(currentPage) + 3; x++) {
                    addBar(x);
                }
                sandian();
                addBar(pageSize);
                nextPage()
            }

        }
    }
    daoAndLineSize();

}

//上一页按钮
function upPage() {
    var liObj = $("<li></li>");
    var aObj = $("<a style='cursor:pointer'>上一页</a>");
    if (currentPage == 1) {
        liObj.addClass("disabled");
    } else {
        aObj.on("click", function () {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        })
    }
    liObj.append(aObj);
    $("#splitBar").append(liObj);
}

//下一页按钮函数
function nextPage() {
    var liObj = $("<li></li>")
    var aObj = $("<a style='cursor:pointer'>下一页</a>");
    if (currentPage == pageSize) {
        liObj.addClass("disabled")
    } else {
        aObj.on("click", function () {
            if (currentPage < pageSize) {
                currentPage++;
                loadData();
            }
        })
    }
    liObj.append(aObj);
    $("#splitBar").append(liObj);
}

//出现。。。省略号函数
function sandian() {
    $("#splitBar").append("<li><span>...</span></li>")
}

//修改总记录数和总页数
function editAllRecordersAndPageSize() {
    $("#allRecorders").html(allRecorders);
    $("#pageSize").html(pageSize)

}

//创建到多少页和每页显示多少行
function daoAndLineSize() {

    var dao = $("<select id=\"dao\" class=\"form-control\"  style=\"width: 13rem;margin: 1rem 1rem 0 0\"></select>");

    for (var x = 1; x <= pageSize; x++) {
        var res = currentPage == x ? "selected" : "";
        var op = $("<option " + res + " id='d" + x + "'  value='" + x + "'>到第" + x + "页</option>");
        dao.append(op);
    }
    var mei = $("<select id=\"mei\" class=\"form-control\" style=\"width: 16rem;margin: 1rem 1rem 0 0\"></select>");
    for (var x = 1; x < 21; x++) {
        var res = lineSize == x ? "selected" : "";
        mei.append($("<option " + res + " id='m" + x + "'  value='" + x + "'>每页显示" + x + "行</option>"))
    }
    var s=lineSize=="500"?"selected":"";
    mei.append($("<option " + s + " id='showAll'  value='500'>最多500行</option>"));
    $("#daomei").append(dao);
    $("#daomei").append(mei);
    dao.on("change", function () {
        currentPage = this.value;
        loadData();
        createSplitBar(allRecorders);

    });
    mei.on("change", function () {
        lineSize = this.value;
        currentPage = 1;
        loadData();
        createSplitBar(allRecorders);
    });

}

function initSimpleDate() {
    $('#datetimepicker1').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',//这里是language,autoclose: 1,
        todayHighlight: 1,//今天高亮，
        todayBtn: 1,//开启今天按钮
        autoclose: 1,//点击完自动关
        startView: 2,//日期页面
        minView: 2,//最小几个页面，选择日期和时间，如果是2就只是日期
        forceParse: 0,
        pickerPosition: 'top-right'//日期插件弹出的位置
    });
}

function initDetailDate() {
    $('#datetimepicker1').datetimepicker({
        format: "yyyy-mm-dd hh:mm:ss",
        language: 'zh-CN',//这里是language,autoclose: 1,
        todayHighlight: 1,//今天高亮，
        todayBtn: 1,//开启今天按钮
        autoclose: 1,//点击完自动关
        startView: 2,//日期页面
        minView: 1,//最小几个页面，选择日期和时间，如果是2就只是日期
        forceParse: 0,
        pickerPosition: 'top-right'//日期插件弹出的位置
    });
}

//图片旋转
function rotateImg() {
    $(function () {
        var currentDeg = 90;
        $("[id=rotate]").each(function () {
            $(this).click(function () {
                $(this).prevAll("img").css({//是img兄弟节点
                    transform: "rotate(" + currentDeg + "deg)"
                });
                currentDeg += 90;
            })
        })
    })
}

