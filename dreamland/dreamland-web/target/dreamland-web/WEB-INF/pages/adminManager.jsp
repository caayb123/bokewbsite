<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<html>
<head>
    <title>管理员个人页面</title>
    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
</head>
<body>
<h3>尊敬的管理员:${user.nickName}您好!</h3> &nbsp;&nbsp;&nbsp;<h3><a href="${ctx}/login/out">点此退出</a></h3><br><a href="${ctx}/personal/list">点此返回个人主页</a>
<input type="text" id="mima"><input type="button" name="bt2" value="查询账号" onclick="findall()"><input type="button" name="bt3" value="查询文章" onclick="findallcont()">
<table name="t1" border="1px" id="t1" class="table">
</table>

</body>
<script type="text/javascript">
    function addmo(id) {
        var ps=$("#mima").val()
        $.ajax({
            type:'post',
            data: {"mima":ps,"id":id},
            url: '${ctx}/admin/addmo' ,
            dataType:'json',
            success:function (data) {
                if (data.msg=='success'){
                    alert("解封成功")
                    window.location="${ctx}/admin/jumpAdmin"
                }else {
                    alert("解封失败")
                }
            }
        })
    }
    function remo(id){
        var ps=$("#mima").val()
        $.ajax({
            type:'post',
            data: {"mima":ps,"id":id},
            url: '${ctx}/admin/remo' ,
            dataType:'json',
            success:function (data) {
                if (data.msg=='success'){
                    alert("封号成功")
                    window.location="${ctx}/admin/jumpAdmin"
                }else {
                    alert("封号失败")
                }
            }
        })
    }
   function findall() {
       var ps=$("#mima").val()
       $.ajax({
           type:'post',
           data: {"mima":ps},
           url: '${ctx}/admin/admin' ,
           dataType:'json',
           success:function (data) {
               if (data.msg=='success'){
                   $("#t1 tr").remove()   //移除表格所有信息
                   $("#t1").append("<tr class='success'><td>用户id</td><td>用户邮箱</td><td>用户手机号</td><td>用户昵称</td><td>操作</td></tr>")
                   $(data.list).each(function () {
                       $("#t1").append("<tr><td>"+this.id+"</td><td>"+this.email+"</td><td>"+this.phone+"</td><td>"+this.nickName+"</td><td><button onclick='remo("+this.id+")' class='btn btn-danger'>封号</button><button onclick='addmo("+this.id+")' class='btn btn-success'>解封</td></tr>")
                   })
               }else {
                   alert("管理员密码错误");
               }
           }
       })
   }

   function findallcont() {
       var ps=$("#mima").val()
       $.ajax({
           type:'post',
           data: {"mima":ps},
           url: '${ctx}/admin/adminCont' ,
           dataType:'json',
           success:function (data) {
               if (data.msg=='success'){
                   $("#t1 tr").remove()   //移除表格所有信息
                   $("#t1").append("<tr class='success'><td>文章id</td><td>文章标题</td><td>作者id</td><td>操作</td></tr>")
                   $(data.list).each(function () {
                       $("#t1").append("<tr><td>"+this.id+"</td><td>"+this.title+"</td><td>"+this.uId+"</td><td><button onclick='remoCont("+this.id+")' class='btn btn-danger'>删除文章</button></td></tr>")
                   })
               }else {
                   alert("管理员密码错误")
               }
           }
   })
   }
   function remoCont(id) {
       var ps=$("#mima").val()
       $.ajax({
           type:'post',
           data: {"mima":ps,"id":id},
           url: '${ctx}/admin/remoCont' ,
           dataType:'json',
           success:function (data) {
               if (data.msg=='success'){
                   alert("删除成功")
                   window.location="${ctx}/admin/jumpAdmin"
               }else {
                   alert("删除失败")
               }
           }
       })
   }
</script>
</html>
