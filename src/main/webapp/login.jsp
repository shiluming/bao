<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login</title>
<link rel="stylesheet" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</head>
<style type="text/css">
    body {
        padding-top: 60px;
        padding-bottom: 40px;
    }
    .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
    }
</style>
<script type="text/javascript">
	function login() {
		var name = $.trim($("#inputEmail").val());
		var password = $.trim($("#inputPassword").val());
		if(name=='') {
			alert("请输入用户名");
			return ;
		}
		$.ajax({
			url : "${pageContext.request.contextPath}/book!login.action",
			data : {
				'userVO.name':name,
				'userVO.password':password
			},
			success : function(data) {
				alert(data.msg);
				if(data.success) {
					window.location.href="${pageContext.request.contextPath}/book!list.action";
				}
			},
			error :function() {
				alert("网路发生错误，请检查");
			}
			
		});
	}
	function goRegister() {
		window.location.href="${pageContext.request.contextPath}/register.jsp";
	}
</script>
<body>

<div class="container">

    <div class="form-signin">
        <h2 class="form-signin-heading">学生登录</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="text" id="inputEmail" class="form-control" placeholder="学号" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="checkbox">

        </div>
        <button class="btn btn-lg btn-primary " onclick="login()">登录</button>
        <button class="btn btn-lg btn-primary " onclick="goRegister()">注册</button>
    </div>

</div> <!-- /container -->


</body>
</html>