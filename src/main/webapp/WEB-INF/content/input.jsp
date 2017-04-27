<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加图书</title>
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
        var price = $("#loginName").val();
        var author = $("#password").val();
        var pulisment = $("#repassword").val();
        var bookDesc = $("#desc").val();
        var imagePath = $("#filename").val();
        if(bookName == '') {
            alert("请输入图书名称");
            return ;
        }
        if(price == '') {
            alert("请输入图书价格");
            return ;
        }
        if(author == '') {
            alert("请输入图书作者");
            return ;
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/book!input.action",
            type : "post",
            data : {
                "book.bookName" : bookName,
                "book.price" : price,
                "book.publishingCompany" : pulisment,
                "book.author" : author,
                "book.bookDesc" : bookDesc,
                "book.imagePath" : imagePath
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
        $("#loginName").val("");
        $("#password").val("");
        $("#repassword").val("");
    }

    $(function(){
        $("#file").fileinput({
            language : 'zh',
            uploadUrl : '${pageContext.request.contextPath}/book!fileUpload.action',
            allowedFileExtensions: ['jpg', 'png', 'gif'],
            showUpload: true, //是否显示上传按钮
            showCaption: true,//是否显示标题
            overwriteInitial: false,
            browseClass: "btn btn-primary", //按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
        });
        $("#file").on("fileuploaded",function(event, data, previewId, index) {
            console.log('File uploaded triggered');
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            console.log(files[0]);
            $("#filename").val(files[0].name);
        });
    });
</script>
<body>

<div class="container">

    <div class="form-signin">
        <h2 class="form-signin-heading">添加图书</h2>
        <div class="form-group">
            <label for="Username">图书名称：</label>
            <div>
                <input type="text" id="Username" name="book.bookName" class="form-control" placeholder="图书名称">
            </div>
        </div>

        <div class="form-group">
            <label for="loginName">价 格：</label>
            <div class="input-group">

                <input type="text" class="form-control" id="loginName" name="book.price" placeholder="价格" aria-describedby="sizing-addon2">
                <span class="input-group-addon" id="sizing-addon2">￥</span>
            </div>
        </div>

        <div class="form-group">
            <label for="password">作 者：</label>
            <div>
                <input type="text" id="password" name="author" class="form-control" placeholder="作者">
            </div>
        </div>

        <div class="form-group">
            <label for="repassword">出版社：</label>
            <div>
                <input type="text" id="repassword" name="book.publishingCompany" class="form-control" placeholder="出版社">
            </div>
        </div>
        <div class="form-group">
            <label for="file">图 片：</label>
            <div>
                <input type="file" id="file" name="file" placeholder="图片">
                <input type="hidden" id="filename" name="book.imagePath" />
            </div>
        </div>
        <div class="form-group">
            <label for="desc">图书描述：</label>
            <div>
                <textarea rows="3" cols="" id="desc" name="book.bookDesc" class="form-control" placeholder="描述"></textarea>
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" onclick="addBook()">添加</button>
        <button class="btn btn-lg btn-primary btn-block" onclick="goBack()">返回</button>
    </div>

</div> <!-- /container -->


</body>
</html>