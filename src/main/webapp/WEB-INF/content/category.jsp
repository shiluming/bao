<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加图书类别</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fileinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/zh.js"></script>

</head>
<style type="text/css">
    body {
        padding-top: 60px;
        padding-bottom: 40px;
    }
    .form-signin {
        max-width: 330px;
        padding: 5px;
        margin: 0 auto;
    }
</style>
<script type="text/javascript">
    function addBook() {
        var bookName = $("#Username").val();
        if(bookName == '') {
            alert("请输入类别名称");
            return ;
        }
        
        $.ajax({
            url : "${pageContext.request.contextPath}/book!addCategory.action",
            type : "post",
            data : {
                "bookCategory.categoryName" : bookName,
            },
            sync : false,
            success : function (data) {
                if(data.success) {
                    clear();
                    alert(data.msg);
                }
            },
            error : function () {
                alert("网络发生错误，请检查");
            }

        });
    }

    function goBack() {
        window.location.href = "${pageContext.request.contextPath}/book!list.action";
    }


    function clear() {
        $("#Username").val("");
    }

    $(function(){
        
    });
</script>
<body>

<div class="container">

    <div class="form-signin">
        <h2 class="form-signin-heading">添加图书类别</h2>
        <div class="form-group">
            <label for="Username">类别名称：</label>
            <div>
                <input type="text" id="Username" name="book.bookName" class="form-control" placeholder="类别名称">
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" onclick="addBook()">添加</button>
        <button class="btn btn-lg btn-primary btn-block" onclick="goBack()">返回</button>
    </div>

</div> <!-- /container -->


</body>
</html>