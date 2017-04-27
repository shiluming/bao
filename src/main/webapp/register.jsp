<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>register</title>
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

    function register() {
        var username = $.trim($("#Username").val());
        var loginName = $.trim($("#loginName").val());
        var password = $.trim($("#password").val());
        var repassword = $.trim($("#repassword").val());
        if(username == '') {
            alert("用户名必填");
            return;
        }
        if(loginName == '') {
            alert("登录名必填");
            return ;
        }
        if(password != repassword || password == '') {
            alert("输入密码不一致，请检查");
            return ;
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/book!register.action",
            data : {
            	'user.name' : username,
            	'user.password':password,
            	'user.loginCode':loginName
            },
            sync : false,
            type : "post",
            success : function(data) {
                clear();
                alert(data.msg);
            },
            error : function() {
                alert("网络发生错误，请检查！");
            }
        });
    }

    function clear() {
        $("#Username").val("");
        $("#loginName").val("");
        $("#password").val("");
        $("#repassword").val("");
    }

    function goBack() {
		window.location.href="${pageContext.request.contextPath}/login.jsp";
    }

</script>
<body>

<div class="container">

    <div class="form-signin">
        <h2 class="form-signin-heading">学生注册</h2>
        <div class="form-group">
            <label for="Username">用户名：</label>
            <div>
                <input type="text" id="Username" class="form-control" placeholder="">
            </div>
        </div>

        <div class="form-group">
            <label for="loginName">登录账号：</label>
            <div>
                <input type="text" id="loginName" class="form-control" placeholder="">
            </div>
        </div>

        <div class="form-group">
            <label for="password">密  码：</label>
            <div>
                <input type="password" id="password" class="form-control" placeholder="">
            </div>
        </div>

        <div class="form-group">
            <label for="repassword">确认密码：</label>
            <div>
                <input type="password" id="repassword" class="form-control" placeholder="">
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" onclick="register()">注册</button>
        <button class="btn btn-lg btn-primary btn-block" onclick="goBack()">返回</button>
    </div>

</div> <!-- /container -->

</body>
</html>