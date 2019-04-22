<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<html>
<head>
    <rapid:block name="head"></rapid:block>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta charset="UTF-8">
	<title>网上选课后台管理</title>
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/weadmin.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layui/layui.js" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layui.css">
</head>
<body class="layui-layout-body"  >
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!-- 顶部开始 -->
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">网上选课系统后台管理</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">控制台</a></li>
      <li class="layui-nav-item"><a href="">商品管理</a></li>
      <li class="layui-nav-item"><a href="">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item" style="float:right; "><a href="<%=basePath%>exit" style="font-size: 20px;">退出登录</a></li>
      <li class="layui-nav-item" style="float:right; font-size:20px;"><i class="layui-icon" style="font-size:18px;" >&#xe612;</i>&nbsp;${sessionScope.username}&nbsp;${sessionScope.stuid}
        <dl class="layui-nav-child">
          <dd><a href="<%=basePath%>admin/adminInfo">基本资料</a></dd>
          <dd><a href="<%=basePath%>admin/editPass">修改密码</a></dd>
        </dl>
      </li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
       <li class="layui-nav-item layui-nav-itemd">
        <a href="javascript:;">学生管理</a>
        <dl class="layui-nav-child">
          <dd><a href="<%=basePath%>admin/studentList">查看学生信息</a></dd>
        </dl>
       </li>
        <li class="layui-nav-item layui-nav-itemd">
          <a href="javascript:;">教师管理</a>
          <dl class="layui-nav-child">
           <dd><a href="<%=basePath%>admin/teacherList">查看教师信息</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item layui-nav-itemd">
          <a href="javascript:;">课程管理</a>
          <dl class="layui-nav-child">
           <dd><a href="<%=basePath%>admin/courseList">查看课程信息</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    <div class="copyright">Copyright ©2019 网上选课系统 by suruomo</div>
  </div>
</div>
<rapid:block name="content"></rapid:block>
<script src="${pageContext.request.contextPath}/static/layui.all.js"></script>
</body>

</html>
