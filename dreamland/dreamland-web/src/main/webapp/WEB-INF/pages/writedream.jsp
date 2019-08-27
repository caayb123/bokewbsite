<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>写博客</title>
    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
    <script src="${ctx}/css/zui/lib/kindeditor/kindeditor.min.js"></script>
    <style>
        body,html{
            background-color: #EBEBEB;
            padding: 0;
            margin: 0;
            height:100%;
        }
        .writedream-context{
            background-color: white;
            margin-top: 30px;
            margin-left: 30px;
            margin-right: 30px;
            min-height:800px;
        }

    </style>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid" style="background-color: #2f2f2f">
        <!-- 导航头部 -->
        <div class="navbar-header">
            <!-- 移动设备上的导航切换按钮 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- 品牌名称或logo -->
            <a class="navbar-brand" href="${ctx}/personal/list?id=${user.id}">个人空间</a>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li><a href="${ctx}/personal/list?id=${user.id}">我的博客</a></li>
                <li><a href="${ctx}/index.jsp">首页</a></li>
                ...
                <!-- 导航中的下拉菜单 -->
                <li class="dropdown">
                    <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown">设置 <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="your/nice/url">任务</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="your/nice/url">消息</a></li>
            </ul>

            <ul class="nav navbar-nav">
                <li style="background-color: black"><a href="javascript:void(0);">写博客</a></li>
            </ul>

            <ul class="nav navbar-nav" style="margin-left: 680px">
                <li><a href="${ctx}/personal/list?id=${user.id}">${user.nickName}

                </a></li>
            </ul>
            <img src="/images/q.png" width="30" style="margin-top: 4px"/>
        </div><!-- END .navbar-collapse -->
    </div>

</nav>

<!--中间内容-->
<form id="write_form" name="w_form" role="w_form" class="writedream-form" action="${ctx}/jump/doWriteDream?cid=${cont.id}" method="post">
<div class="writedream-context">
    <div style="margin-top: 20px;margin-left: 20px;position: absolute;">
        <div class="dropdown dropdown-hover">
            <button class="btn" type="button" data-toggle="dropdown" id="dream-diff" style="background-color:#EBEBEB"><span id="fen" >
                <c:if test="${cont.category != null}">
                    ${cont.category}
                </c:if>
                <c:if test="${cont.category == '' || cont.category == null}">
                    博客分类
                </c:if>

            </span> <span class="caret"></span></button>
            <input id="hidden_cat" hidden="hidden" name="category" value="${cont.category}"/>
            <ul class="dropdown-menu" id="dreamland-category">
                <li><a>惊悚博客</a></li>
                <li><a>爱情博客</a></li>
                <li><a>武侠博客</a></li>
                <li><a>美食博客</a></li>
                <li><a>工作博客</a></li>
                <li><a>动物博客</a></li>
                <li><a>其他博客</a></li>
            </ul>
        </div>
    </div>

    <div style="float: left;margin-top: 20px;margin-left: 110px;background-color: #EBEBEB">
        <input type="text" id="txtTitle" name="txtT_itle" value="${cont.title}" class="input-file-title" maxlength="100" placeholder="&nbsp;&nbsp;&nbsp;&nbsp;输入文章标题"  style="height: 33px;width: 1080px;background-color:#EBEBEB;border: 0px" >
    </div>
    <!--富文本编辑器-->
    <div style="margin-top:20px ;float: left;margin-left: 20px">
        <textarea id="content" name="content" class="form-control kindeditor" style="height:600px;width: 1170px">${cont.content}</textarea>

    </div>

    <br/>
    <div class="switch" style="float: left;margin-top: 670px;margin-left: 20px;position: absolute">
        <input type="checkbox" name="private_dream" id="private_dream" value="off">
        <label>私密博客</label>
        <span style="color: red">${error}</span>
    </div>
    <br/>
    <div style="float: left;margin-top: 700px;margin-left:20px;position: absolute">
        <button class="btn btn-primary"  type="button" id="sub_dream">发表博客</button>
        <button class="btn btn-primary" id="go_back" type="button" >返回</button>
    </div>
</div>
</form>
</body>
<script>
    //li标签的点击事件
    $("#dreamland-category li").click(function(){//jquery的click事件
        var val = $(this).text();
        $("#fen").html(val);
        $("#hidden_cat").val(val);
    });


   $(function () {
       editor =  KindEditor.create('#content', {
           basePath: '${ctx}/css/zui/lib/kindeditor/',
           uploadJson : '${ctx}/upload/fileUpload',
           fileManagerJson : '${ctx}/upload/fileManager',
           allowFileManager : true,
           bodyClass : 'article-content',
           items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                                 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                                 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                                 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                                 'anchor', 'link', 'unlink'],
           afterCreate: function () {
               var editerDoc = this.edit.doc;//得到编辑器的文档对象
//监听粘贴事件, 包括右键粘贴和ctrl+v
               $(editerDoc).bind('paste', null, function (e) {
                       /*获得操作系统剪切板里的项目，e即kindeditor,
                       *kindeditor创建了originalEvent(源事件)对象，
                       *并指向了浏览器的事件对象，而chrome浏览器
                       *需要通过clipboardData(剪贴板数据)对象的items
                       *获得复制的图片数据。
                       */
                       var ele = e.originalEvent.clipboardData.items;
                       for (var i = 0; i < ele.length; ++i) {
//判断文件类型
                           if (ele[i].kind == 'file' && ele[i].type.indexOf('image/') !== -1) {
                               var file = ele[i].getAsFile();//得到二进制数据
//创建表单对象，建立name=value的表单数据。
                               var formData = new FormData();
                               formData.append("imgFile", file);//name,value

//用jquery Ajax 上传二进制数据
                               $.ajax({
                                   url: '${ctx}/upload/paste',
                                   type: 'POST',
                                   data: formData,
// 告诉jQuery不要去处理发送的数据
                                   processData: false,
// 告诉jQuery不要去设置Content-Type请求头
                                   contentType: false,
                                   dataType: "json",
                                   beforeSend: function () {
//console.log("正在进行，请稍候");
                                   },
                                   success: function (responseStr) {
//上传完之后，生成图片标签回显图片，假定服务器返回url。
                                       var src = responseStr.url;
                                       var imgTag = "<img src='" + src + "' border='0'/>";

//console.info(imgTag);
//kindeditor提供了一个在焦点位置插入HTML的函数，调用此函数即可。
                                       editor.insertHtml(imgTag);


                                   },
                                   error: function (responseStr) {
                                       console.log("error");
                                   }
                               });

                           }

                       }
                   }
               )
           }

       });
       KindEditor.sync();

   });

//私密博客开关点击事件
$(".switch").click(function () {
    var pd = document.getElementById('private_dream');
    if(pd.checked == true){
        $("#private_dream").val("on");
    }else{
        $("#private_dream").val("off");
    }
});

    //返回
   $("#go_back").click(function () {
       location.href ="javascript:history.go(-1);"
   });

   //发表梦
   $("#sub_dream").click (function(){
      var val =  $("#fen").html();
       if(val.trim()=='博客分类'){
           alert("请选择博客分类！");
           return;
       }

       var tit = $("#txtTitle").val();
       if(tit == null || tit.trim() == ""){
           alert("请输入文章标题！");
           return;
       }
       editor.sync();
       var v1 = $("#content").val();
       if(v1 == null || v1.trim() == ""){
           alert("文章内容为空！");
           return;
       }
           $("#write_form").submit();

   });

   //私密开关回显
   $(function () {
       var v = '${cont.personal}';
       if(v == "1"){
           var pd = document.getElementById('private_dream');
           pd.checked = true;
       }
   });
</script>
</html>