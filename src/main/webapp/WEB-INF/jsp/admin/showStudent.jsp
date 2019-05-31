<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<title>学生信息显示</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入bootstrap -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<!-- 引入JQuery  bootstrap.js-->
	<script src="/js/jquery-3.2.1.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>

	<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>

</head>
<body>

	<!-- 顶栏 -->
	<jsp:include page="top.jsp"></jsp:include>
	<!-- 中间主体 -->
	<div class="container" id="content">
		<div class="row">
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="col-md-10">	
				<div class="panel panel-default">
				    <div class="panel-heading">
						<div class="row">
					    	<h1 class="col-md-5">学生名单管理</h1>
							<form class="bs-example bs-example-form col-md-5" role="form" style="margin: 20px 0 10px 0;" action="/admin/selectStudent" id="form1" method="post">
								<div class="input-group">
									<input type="text" class="form-control" placeholder="请输入姓名" name="findByName">
									<span class="input-group-addon btn" id="sub">搜索</span>
								</div>
							</form>
							<button class="btn btn-default col-md-2" style="margin-top: 20px" onClick="location.href='/admin/goAddStudentPage'">
								添加学生信息
								<sapn class="glyphicon glyphicon-plus"/>
							</button>

						</div>
				    </div>
				    <table class="table table-bordered">
					        <thead>
					            <tr>
					                <th>学号</th>
				  					<th>姓名</th>
				  					<th>性别</th>
				  					<th>出生年份</th>
				  					<th>入学时间</th>
				  					<th>学院</th>
				  					<th>操作</th>
					            </tr>
					        </thead>
					        <tbody>
					        
							<c:forEach items="${ext.list}" var="l">
								<tr>
									<td>${l.userid}</td>
									<td>${l.username}</td>
									<td>${l.sex}</td>
									<td><fmt:formatDate value="${l.birthyear}" dateStyle="medium" /></td>
									<td><fmt:formatDate value="${l.grade}" dateStyle="medium" /></td>
									<%-- <td>${l.collegeid}</td> --%>
									<td>${l.collegeName}</td>
									<td>
										<button class="btn btn-default btn-xs btn-info" onClick="location.href='/admin/goEditStudentPage?id=${l.userid}'">修改</button>
										<button class="btn btn-default btn-xs btn-danger btn-primary" id="remove" onClick="location.href='/admin/removeStudent?id=${l.userid}'">删除</button>
																			
									</td>
								</tr>	
							</c:forEach>
						
					        </tbody>
				    </table>
				    <div class="panel-footer">
				    
					
							<nav style="text-align: center">
								<ul class="pagination">
									<li><a href="/admin/showStudent?page=${ext.currentPage==1? 1: ext.currentPage-1}">&laquo;上一页</a></li>
									<li><a href="/admin/showStudent?page=${ext.currentPage}">${ext.currentPage}</a></li>
									<li><a href="/admin/showStudent?page=${ext.currentPage==ext.pageCount? ext.pageCount:ext.currentPage+1}">下一页&raquo;</a></li>
									<li><a href="#">总页数&nbsp;${ext.pageCount}</a></li>
									<%-- <li><a href="#">总页数&nbsp;${ext.total}</a></li> --%>
								</ul>
							</nav>
					
				    </div>
				</div>

			</div>
		</div>
	</div>
	<div class="container" id="footer">
		<div class="row">
			<div class="col-md-12"></div>
			
		</div>
	</div>
</body>
	<script type="text/javascript">
	
	/* 添加这行会使选择的标题第二行变亮 */
	$("#nav li:nth-child(2)").addClass("active");
	
	
    function confirmd() {
        var msg = "您真的确定要删除吗？！";
        if (confirm(msg)==true){
            return true;
        }else{
            return false;
        }
    };

	//提交搜索表单
	$("#sub").click(function () {
        $("#form1").submit();
    });
	</script>
</html>