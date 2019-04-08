<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>课程信息</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <p style="color:red; margin-top:10px; font-size:15px;">${msg}</p>
    <table class="layui-table" style="margin-top:15px;">
        <colgroup>
            <col width="100">
            <col width="120">
            <col width="80">
            <col width="50">
            <col width="50">
            <col width="50">
            <col width="50">
            <col width="60">
            <col width="60">
        </colgroup>
        <thead>
        <tr>
            <th>开课学年</th>
            <th>开课学期</th>
            <th>课程编号</th>
            <th>课程名称</th>
            <th>教师名称</th>
            <th>学分</th>
            <th>绩点</th>
            <th>分数</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courseList}" var="course">
            <tr>
                <td>${course.year}</td>
                <td>${course.term}</td>
                <td>${course.classId}</td>
                <td>${course.className}</td>
                <td>${course.teaName}</td>
                <td>${course.credit}</td>
                <td>绩点未完待续</td>
                <td>${course.score}</td>
                <td>
                    <button class="layui-btn layui-btn-radius layui-btn-normal" onclick="delete_fun(${course.classId})">退课
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <script>
        function delete_fun(classId) {
            var r = confirm("确认退课吗？")
            if (r == true) {
                window.location.href = "<%=basePath%>student/deleteCourse?courseid=" + classId;
            }
            else {
                return;
            }
        }
    </script>
</rapid:override>
<%@ include file="base.jsp" %>