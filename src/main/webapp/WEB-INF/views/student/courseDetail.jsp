<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>确认选课</title>
</rapid:override>
<rapid:override name="content">
    <%
    
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        int stuId=(int)request.getSession().getAttribute("id");
    %>
    <div>
    <input type="hidden" id="flag" value='${flag}'>
        <table class="layui-table" style="width:280px; margin:25px 480px 25px;">
            <colgroup>
                <col width="100">
                <col width="180">
            </colgroup>
            <tbody>
            <tr>
                <td>课程编号</td>
                <td>${course.classId}</td>
            </tr>
            <tr>
                <td>课程名称</td>
                <td>${course.className}</td>
            </tr>
            <tr>
                <td>教师名称</td>
                <td>${course.teaName}</td>
            </tr>
            <tr>
                <td>人数限制</td>
                <td>${course.classNum}</td>
            </tr>
            <tr>
                <td>已选人数</td>
                <td>${course.classChooseNum}</td>
            </tr>
            <tr>
                <td>课程性质</td>
                <td>${course.type}</td>
            </tr>
             <tr>
                <td>课程类型</td>
                <td>${course.classify}</td>
            </tr>
            <tr>
                <td>学分</td>
                <td>${course.credit}</td>
            </tr>
            <tr>
                <td>专业限制</td>
                <td>
                    <c:forEach items="${course.classLimitProName}" var="pro">
                        ${pro}&nbsp;
                    </c:forEach>
                </td>
            </tr>
             <tr>
                <td>课程简介</td>
                <td>${course.introduction}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="layui-input-block" style="margin-left:500px;">
        <button type="button" onclick="javascript:history.back(-1);" class="layui-btn layui-btn-lg">
            返回上一页
        </button>
        <button type="button" id="success" onclick="choose_fun(${course.classId})" class="layui-btn layui-btn-danger layui-btn-lg">
            选择
        </button>
    </div>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script>
        //删除选课学生
        //后面有时间或者下一个版本考虑加入学分 甚至是同学院学分绩点的排名等。。。
        function choose_fun(classId) {
            //判断是否有权限选
            var r=confirm("确认选择这门课程？");
            var f=$("#flag").val();
            console.log(f);
            if (r==true)
            {
                window.location.href="<%=basePath%>student/chooseSuccess?flag=<%=request.getAttribute("flag")%>&stuid=<%=stuId%>&courseid="+classId;
                alert("选择成功");
            }
            else
            {
                return;
            }
        }
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
