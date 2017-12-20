<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    application.setAttribute("basePath",basePath);
%>
<title>千里码软件，全方位软件开发服务</title>
<base href="${basePath}">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no,maximum-scale=1,minimum-scale=1.0"><%--bootstrap适配屏幕--%>
<link rel="icon" href="${basePath}images/index/icon.png" type="image/x-icon" /><%--公司网址图标--%>

<script type="text/javascript" src="${basePath}jquery/jquery-3.2.1.min.js"></script>

<script type="text/javascript" src="${basePath}jquery/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}jquery/validate/additional-methods.min.js"></script><%--jquery的一个主文件和四个验证包--%>
<script type="text/javascript" src="${basePath}jquery/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${basePath}jquery/validate/Message_zh_CN.js"></script>

<script type="text/javascript" src="${basePath}bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}bootstrap/css/bootstrap.min.css" /><%--bootstrap的两个包--%>

<script type="text/javascript" src="${basePath}js/mapping.js"></script> <%--自己的两个包--%>
<link rel="stylesheet" type="text/css" href="${basePath}css/mapping.css" />

<body>
        <div class="panel panel-danger">
            <div class="panel-heading">面板标题</div>
            <div class="panel-body">面板内容</div>
            <div class="panel-footer">面板脚部</div>
        </div>


</body>


