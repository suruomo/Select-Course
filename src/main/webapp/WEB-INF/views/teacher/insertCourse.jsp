<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>添加新课程</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <form class="layui-form" id="changeform" method="post" action="<%=basePath%>teacher/changeTeaPass" style="margin:80px 400px">
        <div class="layui-form-item">
            <label class="layui-form-label">开课学年</label>
            <div class="layui-input-block">
            <select id="year" lay-verify="">
                <option value="2017-2018">2017-2018</option>
                <option value="2018-2019">2018-2019</option>
                <option value="2019-2020">2019-2020</option>
           </select>
           </div>
        </div>
         <div class="layui-form-item">
            <label class="layui-form-label">开课学期</label>
            <div class="layui-input-block">
            <select id="term" lay-verify="">
                <option value="第一学期">第一学期</option>
                <option value="第二学期">第二学期</option>
           </select>
           </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">课程名称</label>
            <div class="layui-input-block" >
                <input type="text" name="coursename" id="name" placeholder="请输入课程名称" autocomplete="off" class="layui-input">
            </div>
        </div>
         <div class="layui-form-item">
            <label class="layui-form-label">课程性质</label>
            <div class="layui-input-block" id="ty">
                 <input type="radio" name="type" id="type" value="必修" title="必修" checked>
                 <input type="radio" name="type" id="type" value="选修" title="选修" >
            </div>
        </div>
         <div class="layui-form-item">
            <label class="layui-form-label">课程类别</label>
            <div class="layui-input-block" id="le">
                 <input type="radio" name="classify" id="classify" value="公共课" title="公共课" checked>
                 <input type="radio" name="classify" id="classify" value="专业课" title="专业课" >
                 <input type="radio" name="classify" id="classify" value="通识课" title="通识课" >
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">课程学分</label>
            <div class="layui-input-block">
                <input type="text" name="coursecredit" id="credit" placeholder="请输入课程学分" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">人数限制</label>
            <div class="layui-input-block">
                <input type="text" name="coursenum" id="num" placeholder="请输入人数" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">学院限制</label>
            <div class="layui-input-block">    
            <label class="layui-form-label">计算机学院</label>   
                   <div class="layui-form-item">  
                       <c:forEach items="${jisuanjiList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                    </div>  
            <label class="layui-form-label">医学院</label>  
                   <div class="layui-form-item">  
                        <c:forEach items="${yiList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                   </div>   
            <label class="layui-form-label">文学院</label>  
                   <div class="layui-form-item">  
                        <c:forEach items="${wenList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                   </div>  
            <label class="layui-form-label">管理学院</label>  
                   <div class="layui-form-item">  
                        <c:forEach items="${guanliList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                   </div>  
             <label class="layui-form-label">经济学院</label>  
                   <div class="layui-form-item">  
                        <c:forEach items="${jingjiList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                   </div>  
              <label class="layui-form-label">理学院</label>  
                   <div class="layui-form-item">  
                        <c:forEach items="${liList}" var="pro">
                         <input type="checkbox" name="pro" value="${pro.proId}" title="${pro.proName}">
                        </c:forEach>   
                   </div>        
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">课程简介</label>
            <div class="layui-input-block">
                <textarea id="introduction" name="introduction"  lay-verify="required" placeholder="请输入课程简介" class="layui-textarea"></textarea>
            </div>
        </div>
        <button type="button" id="success" class="layui-btn layui-btn-danger layui-btn-lg" style="margin:0 550px;">
        确认提交
    </button>
    </form>
    
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script>
      layui.use(['layer','form'],  function(){
    	var layer = layui.layer;
    	var form = layui.form; 
    	form.render();  
      });
    </script>
    <script>
        $(function () {
            $("#success").click(function () {
            	var year=$("#year option:selected").val();     //下拉框选学年
            	var term=$("#term option:selected").val();   //学期
            	var type=$('#ty input[name="type"]:checked ').val();    //单选框选课程性质
            	var credit=$("#credit").val();    //学分
                var name = $("#name").val();     //课程名称
                var introduction = $("#introduction").val();    //简介
                var num = $("#num").val();         //课程容量
                var classify = $('#le input[name="classify"]:checked ').val();      //课程类别
                var ins = "";             //学院
                var count=0;
                $("input[name='pro']:checked").each(function () {
                    count++;
                    if (count === 1) {
                        ins = ins + $(this).attr("value");
                    }
                    else {
                        ins = ins + "," + $(this).attr("value");
                    }
                })
                var content=name+"|"+num+"|"+ins+"|"+credit+"|"+introduction+"|"+year+"|"+term+"|"+type+"|"+classify;
          
                var myform=document.createElement("form");
                myform.id = "form1";
                myform.name = "form1";
                document.body.appendChild(myform);
                var input = document.createElement("input");
                input.type = "text";
                input.name = "content";
                input.value = encodeURIComponent(encodeURIComponent(content));
                myform.appendChild(input);
                myform.method = "POST";
                myform.action = "<%=basePath%>teacher/insertCourseSuccess?page="+1;
                myform.submit();
                document.body.removeChild(myform);
            })
        })
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
