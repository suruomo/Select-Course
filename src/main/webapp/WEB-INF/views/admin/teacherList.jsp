<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<rapid:override name="head">
	<title>学生列表</title>
</rapid:override>
<rapid:override name="content">
	<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
<div class="layui-body">
    <!-- 内容主体区域 -->

     <div style="padding: 70px;border:30px;">
            <form class="layui-form">
        <div class="layui-form-item">
            <div class="layui-input-block"> 
                <input type="text" id="search" class="layui-input" style="float:left; width:200px;"
                       placeholder="请输入教师编号">
                <button type="button" data-type="reload" class="layui-btn layui-btn-radius"style="float:left;" onclick="search()">搜索</button>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block"> 
                <table class="layui-hide" id="demo" lay-filter="test"></table>
            </div>
        </div>
        </form>
   </div>
  </div>
     
<script type="text/html" id="barDemo">
  <a class="layui-btn yutons layui-btn-sm yutons-color-detail" lay-event="modify"><i class="layui-icon">&#xe642;</i>修改</a>
  <a class="layui-btn layui-btn-danger yutons layui-btn-sm" lay-event="del"><i class="layui-icon">&#x1006;</i>删除</a>
</script>
<script type="text/html"  id="toolbarDemo">
  <div class="layui-btn-container" >
    <button type="button"  class="layui-btn layui-btn-danger layui-btn-sm" lay-event="deleteAll"><i class="layui-icon">&#xe640;</i> 批量删除</button>
  </div>
</script>
<script>
    layui.use(['layer','table'],  function(){
    	 var table = layui.table;
    	 var layer = layui.layer;
        //方法渲染
        table.render({
            elem: '#demo'  //绑定table表格 
            ,url: 'teacherList.Action' //后台springmvc接收路径
            ,page:true    //true表示分页
            ,limit: 10
            ,title:'教师信息表'
            ,id:'teacherList'
            ,toolbar: '#toolbarDemo'  //开启表格头部工具栏区域
            ,cols: [[
                 {type: 'checkbox', fixed: 'left'}
                ,{field:'teaId', title:'学号', width:110, fixed: 'left', sort: true}
                ,{field:'teaName', title:'姓名', width:80, edit: 'text'}
                ,{field:'tage', title:'年龄', width:70}
                ,{field:'tsex', title:'性别', width:70}
                ,{field:'tele', title:'电话', width:120}
                ,{field:'address', title:'地址', width:70}
                ,{field:'insName', title:'学院名称', width:120}      
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:180}
            ]]
        }); 

        //监听表格复选框选择
       table.on('checkbox(test)', function(obj){     //待修改
       });
        //监听头工具栏事件
       	table.on('toolbar(test)', function(obj){  //注：toolbar是工具条事件名，demo是table原始容器的属性 lay-filter="对应的值"
       	var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
           case 'deleteAll':
         	if(checkStatus.data.length==0){
         		parent.layer.msg('请先选择要删除的数据行！', {icon: 2});
         		return ;
         	}
         	var ids = "";
         	for(var i=0;i<checkStatus.data.length;i++){
         		ids += checkStatus.data[i].id+",";
         	}
            layer.confirm('真的删除行么', function(index){
            parent.layer.msg('删除中...', {icon: 16,shade: 0.3,time:5000});
		    	$.ajax({
		    		url:'${pageContext.request.contextPath}/admin/deleteMultiStu?id='+ids,
		    		method:'GET',
		    		dataType:'text',
		    		success:function(data){	
		    			layer.msg("删除成功");
		    			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(index); //再执行关闭
		    			obj.del();   //删除对应行（tr）的DOM结构，并更新缓存
		    		}
		    	});
           });
           break;
               };
	});
  	  //监听行工具事件
		table.on('tool(test)', function(obj){  //注：tool是工具条事件名，demo是table原始容器的属性 lay-filter="对应的值"
		    var data = obj.data;   //获得当前行数据  
		    if(obj.event === 'del'){   //删除数据
		    	//执行ajax请求
           layer.confirm('真的删除行么', function(index){
		    	$.ajax({
		    		url:'${pageContext.request.contextPath}/admin/deleteTeacher?id='+data.teaId,
		    		method:'GET',
		    		dataType:'text',
		    		success:function(data){
		    			layer.msg("删除成功");
		    			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(index); //再执行关闭
		    			obj.del();   //删除对应行（tr）的DOM结构，并更新缓存
		    		}
		    	});
           });
		     }else if(obj.event === 'modify'){   //修改数据
		    	layer.open({
					  type: 2, 
					  title:'修改数据'   //标题 
					   ,maxmin: true
					  ,area:['380px','480px']    //宽高
					  ,content:['${pageContext.request.contextPath}/admin/updateTeacher?id='+data.teaId,'no']
		    	      ,end: function () {
		    	    	 location.reload();
		               }
		    	}); 			  
		    } 
		 });
    });
</script>

</rapid:override>
<%@ include file="base.jsp"%>