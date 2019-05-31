<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入bootstrap -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<!-- 引入JQuery  bootstrap.js-->
	<script src="/js/jquery-3.2.1.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<style type="text/css">
	body{
	   background: url(images/a.jpg)repeat;
	}
	#login-box {
		/*border:1px solid #F00;*/
		padding: 35px;
		border-radius:15px;
		background: #56666B;
		color: #fff;
	}

	</style>
</head>
<body>
	<div class="container" id="top">
		<div class="row" style="margin-top: 280px; ">	
			<div class="col-md-4"></div>
			
			<button class="col-md-4" id="login-box" onclick="location.href='http://localhost/'">
				<h2>${message}</h2>
			</button>
			
			<div class="col-md-4"></div>
		
		</div>		
	</div>
</body>

</html>