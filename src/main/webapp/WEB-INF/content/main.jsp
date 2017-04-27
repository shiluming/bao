<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>main</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/bootstrap-pagination.css">
<link rel="stylesheet" href="css/bootstrap-modal-bs3patch.css">
<link rel="stylesheet" href="css/bootstrap-modal.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-pagination.js"></script>
<script type="text/javascript" src="js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="js/bootstrap-modal.js"></script>
</head>
<style type="text/css">
body {
    padding-top: 60px;
    padding-bottom: 40px;
}

</style>
<script type="text/javascript">

    function addBook() {
        window.location.href = "";
    }
    function goSearch() {
        var name = $.trim($("#bookname").val());
        search(name,1);
    }
    function deleteBook(id) {
    	$("#delete").attr("objId",id);
    	$('#delete').modal('show')
    }
    function edit(id) {
    	$("#update").attr("objId",id);
    	$('#update').modal('show')
    }
    function dodelete() {
    	var objId = $("#delete").attr("objId");
    	$.post("${pageContext.request.contextPath}/book!delete.action",{
    		'book.id':objId
    	},function(){
    		var name = $.trim($("#bookname").val());
    		search(name, 0);
    	});
    }
    function doupdate() {
    	var objId = $("#update").attr("objId");
		
    }
    function search(name,index) {
        $.ajax({
            url : "${pageContext.request.contextPath}/book!search.action",
            data : {
                "searchVO.bookName":name,
                "page.pageNo":index
            },
            type : 'post',
            async : false,
            success : function (data) {
                var td = '<div class="col-sm-6 col-md-3">'
                            +'<div href="#" class="thumbnail">'
                            +'<img src="images/github.png" alt="myself">'
                                +'<div class="caption">'
                                +'<h4>图书名称</h4>'
                                +'<p>价格：</p>'
                                +'<p>作者：</p>'
                                +'<p>出版社：</p>'
                                +'<p>类别编号：</p>'
                                    +'<button class="btn btn-success btn-sm">修改</button>'
                                    +'<button class="btn btn-danger btn-sm">删除</button>'
                                +'</div>'
                                +'</div>'
                            +'</div>';

                $(".container").empty();
                for(var i=0;i<data.rows.row;i++) {
                    var tr = '<div class="row" rowobj="'+i+'">'
                        +'</div><!-- row end -->';
                    $(".container").append(tr);
                    for(var j=i*4;j<data.rows.data.length;j++) {
                        if(i==0 && j==4) {
                            break;
                        }
                        var td = '<div class="col-sm-6 col-md-3">'
                            +'<div href="#" class="thumbnail">'
                            +'<img src="images/'+data.rows.data[j].imagePath+'" alt="myself">'
                            +'<div class="caption">'
                            +'<h4>图书名称: '+data.rows.data[j].bookName+'</h4>'
                            +'<p>价格：'+data.rows.data[j].price+'</p>'
                            +'<p>作者：'+data.rows.data[j].author+'</p>'
                            +'<p>出版社：'+data.rows.data[j].publishingCompany+'</p>'
                            +'<p>类别编号：</p>'
                            +'<button class="btn btn-success btn-sm" onclick="edit('+data.rows.data[j].id+')">修改</button>'
                            +'<button class="btn btn-danger btn-sm" onclick="deleteBook('+data.rows.data[j].id+')">删除</button>'
                            +'</div>'
                            +'</div>'
                            +'</div>';
                        $('div[rowobj="'+i+'"]').append(td);
                    }

                }
                doPagination(data.totals,index);
            },
            error : function() {
                alert("网络发生问题，请检查");
            }
        })
    }

    function doPagination(total,index) {
        var demo1 = BootstrapPagination($("#demo1"), {
            //记录总数。
            total: total,
            //当前页索引编号。从其开始（从0开始）的整数。
            pageIndex: index,
            pageSizeList: "",
            pageSize:8,
            nextGroupPageText: "",
            prevGroupPageText: "",
            leftFormateString:"",
            pageSizeListFormateString:"",
            //当分页更改后引发此事件。
            pageChanged: function (pageIndex, pageSize) {
                alert("page changed. pageIndex:" + pageIndex + ",pageSize:" + pageSize)
                var name = $.trim($("#bookname").val());
                search(name,pageIndex+1);
            },
        });
    }
    $(function(){
        var rows = $(".row").length;
        if(rows > 0) {
            alert("rows > 0");
        }else {
            search();
        }
    });

</script>
<body>

<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">图书</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/book!input.action">添加图书</a></li>
                <li><a href="${pageContext.request.contextPath}/book!addCategory.action">添加图书类别</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="input-group">
                    <input type="text" class="form-control" id="bookname" placeholder="图书名称...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="goSearch();">查找</button>
                    </span>
                </div><!-- /input-group -->
            </form>
        </div>
    </div>

</div>
<div class="container">
    <%--<div class="row">--%>
        <%--<div class="col-sm-6 col-md-3">--%>
            <%--<div  class="thumbnail">--%>
                <%--<a href="#"><img src="images/github.png" alt="myself"></a>--%>
                <%--<div class="caption">--%>
                    <%--<h4><a href="#">图书名称</a></h4>--%>
                    <%--<p>价格：</p>--%>
                    <%--<p>作者：</p>--%>
                    <%--<p>出版社：</p>--%>
                    <%--<p>类别编号：</p>--%>
                    <%--<button class="btn btn-success btn-sm">修改</button>--%>
                    <%--<button class="btn btn-danger btn-sm">删除</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-sm-6 col-md-3">--%>
            <%--<div  class="thumbnail">--%>
                <%--<a href="#"><img src="images/github.png" alt="myself"></a>--%>
                <%--<div class="caption">--%>
                    <%--<h4><a href="#">图书名称</a></h4>--%>
                    <%--<p>价格：</p>--%>
                    <%--<p>作者：</p>--%>
                    <%--<p>出版社：</p>--%>
                    <%--<p>类别编号：</p>--%>
                    <%--<button class="btn btn-success btn-sm">修改</button>--%>
                    <%--<button class="btn btn-danger btn-sm">删除</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-sm-6 col-md-3">--%>
            <%--<div  class="thumbnail">--%>
                <%--<a href="#"><img src="images/github.png" alt="myself"></a>--%>
                <%--<div class="caption">--%>
                    <%--<h4><a href="#">图书名称</a></h4>--%>
                    <%--<p>价格：</p>--%>
                    <%--<p>作者：</p>--%>
                    <%--<p>出版社：</p>--%>
                    <%--<p>类别编号：</p>--%>
                    <%--<button class="btn btn-success btn-sm">修改</button>--%>
                    <%--<button class="btn btn-danger btn-sm">删除</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-sm-6 col-md-3">--%>
            <%--<div href="#" class="thumbnail">--%>
                <%--<img src="images/github.png" alt="myself">--%>
                <%--<div class="caption">--%>
                    <%--<h4>图书名称</h4>--%>
                    <%--<p>价格：</p>--%>
                    <%--<p>作者：</p>--%>
                    <%--<p>出版社：</p>--%>
                    <%--<p>类别编号：</p>--%>
                    <%--<button class="btn btn-success btn-sm">修改</button>--%>
                    <%--<button class="btn btn-danger btn-sm">删除</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div><!-- row end -->--%>

</div> <!-- /container -->
<div class="text-center">
    <nav>
        <ul id="demo1" class="pagination">
        </ul>
    </nav>
</div>
<!-- 模态框 -->
<div id="delete" objId="" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" style="display: none;">
  <div class="modal-body">
    <p>确认删除吗？</p>
  </div>
  <div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
    <button type="button" data-dismiss="modal" class="btn btn-primary" onclick="dodelete()">确认</button>
  </div>
</div>
<!-- 模态框 -->
<div id="update" objId="" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false" style="display: none;">
  <div class="modal-body">
    <p>确认修改吗？</p>
  </div>
  <div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
    <button type="button" data-dismiss="modal" class="btn btn-primary" onclick="doupdate()">确认</button>
  </div>
</div>
<script type="text/javascript">
	
</script>
</body>
</html>