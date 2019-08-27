<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>博客网</title>
    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>

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
        body {
            font: 14px/1.5 "PingFang SC","Lantinghei SC","Microsoft YaHei","HanHei SC","Helvetica Neue","Open Sans",Arial,"Hiragino Sans GB","微软雅黑",STHeiti,"WenQuanYi Micro Hei",SimSun,sans-serif;
        }
        .title-content a{
            text-decoration:none;
        }
        .stats-buttons  a{
            text-decoration:none;
        }
        .author-card {
            margin-bottom: 10px;
            margin-left: 50px;
        }

        .designer-card {
            margin: 0;
            display: block;
        }
        .card-media {
            width: 220px;
            height: 280px;
        }
        .designer-card {
            background: #fff;
            border-radius: 4px;
            text-align: center;
            padding: 30px 0 20px;
            overflow: hidden;
            display: inline-block;
            margin: 0 20px 20px 0;
            float: left;
            width: 280px;
            height: 380px;
        }
       /* 梦分类*/
        .dreamland-diff{
            display: block;
            width: 280px;
           min-height: 60px;
            margin-top: 399px;
            position: absolute;
            background-color: #EBEBEB;
        }
        /*关注*/
        .dreamland-see{
            display: block;
            width: 280px;
            min-height: 550px;
            margin-top: 750px;
            position: absolute;
            background-color: white;
        }

        /*被关注*/
        .dreamland-bysee{
            display: block;
            width: 280px;
            min-height: 550px;
            margin-top: 1320px;
            position: absolute;
            background-color: white;
        }
        .avatar-container-80.center {
            margin: 0 auto;
            position: relative;
            left: inherit;
            transform: inherit;
        }
        .avatar-container-80 {
            width: 80px;
        }
        a {
            color: inherit;
            outline: 0;
        }

        .designer-card img {
            border-radius: 50%;
            vertical-align: middle;
        }
        img {
            border: 0;
        }
        a {
            color: inherit;
            outline: 0;
        }
        a:-webkit-any-link {
            cursor: pointer;

        }
        .designer-card .position-info {
            margin-bottom: 20px;
        }
        .designer-card .btn-area {
            padding: 0 20px;
        }
        .designer-card .btn-area .js-project-focus-btn {
            height: 32px;
        }
        .designer-card .js-project-focus-btn {
            float: left;
        }
        .js-project-focus-btn {
            display: inline-block;
            position: relative;
        }
        .btn-default-main {
            color: #444;
            background: #ffd100;
            border: 1px solid #ffd100;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }

        .designer-card .btn-area .btn-current {
            width: 83px;
            height: 36px;
        }
        .designer-card .btn-area .private-letter {
            margin-right: 0;
        }
        .card-media .private-letter {
            float: right;
        }
        .btn-default-secondary {
            color: #666;
            background: 0 0;
            border: 1px solid #bbb;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }
        .dreamland-content{
            float: left;
            width: 800px;
            min-height:100%;
            position: relative;
        }
        .foot-nav-col li{
            list-style: none;
            margin-left: 70px;
        }
        .foot-nav-col h3{
            margin-left:90px;
        }
        .foot-nav-col a{
            text-decoration:none;
            color:grey;

        }
        .foot-nav-col a:link,a:visited { color:grey;}
        .foot-nav-col a:hover,a:active { color: #6318ff;}

        .foot-nav-col{
            margin-top: 10px;
            float: left;
        }

        .author img {
            width: 35px;
            height: 35px;
            border-radius: 35px;
            padding: 0;
            margin-right: 10px;
        }
        fieldset, img {
            border: 0;
        }
        .author a, .author span {
            float: left;
            font-size: 14px;
            font-weight: 700;
            line-height: 35px;
            color: #9b8878;
            text-decoration: none;
        }

        .author-h2 {
            display: block;
            font-size: 1.5em;
            -webkit-margin-before: 0.83em;
            -webkit-margin-after: 0.83em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
            font-weight: bold;
            font-size: 100%;
            font-weight: 400;
        }

    /* 左侧*/

         .ibx-advice {
             position: fixed;
             top: 140px;
             right: -82px;
             overflow: hidden;
             height: 30px;
             width: 115px;
             background-color: #EBEBEB;
             -moz-transition: right .5s;
             -webkit-transition: right .5s;
             transition: right .5s;
             cursor: pointer;
             z-index: 10;
         }

        .glyphicon glyphicon-edit{
            float: left;
            width: 43px;
            height: 42px;
            border: 1px solid #d6d6d6;
            border-right: none;
            cursor: pointer;

        }
        .tab li{list-style:none}
        .table tr:hover{background-color: #dafdf3;}

        .content-bar{
            padding: 30px;
        }
        .dreamland-fix{
            list-style-type:none;
            margin-left: 10px;
            margin-right: 30px;
        }
        .bar-commend{
            float: right;
            margin-right: 20px;
            color: grey;
        }
        .bar-read{
            float: right;
            margin-right: 20px;
            color: grey;
       }
        .bar-update{
            float: right;
            margin-right: 20px;
            color: grey;
        }

        .bar-delete{
            margin-right: 20px;
            float: right;
            color: grey;
        }
    </style>
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
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
            <a class="navbar-brand" href="javascript:void(0);">个人空间</a>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li><a href="${ctx}/personal/list">我的博客</a></li>
                <li><a href="${ctx}/index.jsp">首页</a></li>

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
                <li><a href="${ctx}/jump/write">写博客</a></li>
            </ul>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">用户信息</a></li>
            </ul>

            <ul class="nav navbar-nav" style="margin-left: 680px">
                <li><a href="${ctx}/personal/list">${user.nickName}

                </a></li>
            </ul>
            <img src="/images/q.png" width="30" style="margin-top: 4px"/>
        </div>
    </div>

</nav>
    <!-- 左侧菜单栏-->
<div class="author-card follow-box">
    <div class="designer-card card-media">

        <input type="hidden" name="creator" value="13149346">
        <div class="avatar-container-80 center">
            <a href="#" title="${otheruser.nickName}" class="avatar" target="_blank">
                <img src="${otheruser.imgUrl}" width="80" height="80" alt="">
            </a>

        </div>

        <br/>
        <div class="author-info">
            <p class="author-info-title">
                <a href="#" title="${otheruser.nickName}"
                   class="title-content" target="_blank">${otheruser.nickName}
                </a>
            </p>
            <div class="position-info">
                <span>上海&nbsp;|&nbsp;UI设计师</span>
            </div>
            <div class="btn-area">
                <div class="js-project-focus-btn" style="margin-left: 20px">

                    <input type="button" title="添加关注" class="btn-default-main btn-current attention notfollow" value="关注"
                           z-st="follow" onclick="addfollow(${otheruser.id})" id="guanzhu">

                </div>
                <div style="margin-left: 30px;float: left">
                <input type="button" title="发私信" class="btn-default-secondary btn-current private-letter" value="私信"
                       onclick="" z-st="privateMsg">
                </div>
            </div>

            <div>
                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 42px">
                    <span style="color: white;font-size: 12px">等级</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">1</span>
                </div>


                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 20px">
                    <span style="color: white;font-size: 12px">博客</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">2</span>
                </div>

                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 20px">
                    <span style="color: white;font-size: 12px">下载</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">2</span>
                </div>
            </div>


        </div>
    </div>

    <div class="dreamland-diff">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">博客分类</font>
        </div>
        <div class="list-group">
            <a onclick="changeToActive('category_x',null,null,${otheruser.id})" class="list-group-item active" id="category_x">全部(${page.total})</a>
            <c:forEach items="${categorys}" var="category" varStatus="sta">
                <a onclick="changeToActive('category_${sta.index}','${category.category}',null,${otheruser.id})" class="list-group-item" id="category_${sta.index}">${category.category}(${category.num})</a>
            </c:forEach>

        </div>
    </div>

    <div class="dreamland-see" id="dreamland-see" style="margin-top: 510px">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">关注列表</font>
        </div>
        <br/>
        <c:forEach var="fl" items="${follow}">
            <div class="author clearfix" style="margin-left: 10px">
                <div>
                    <a href="${ctx}/other/list?oid=${fl.id}" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                         <img src="${fl.imgUrl}">
                    </a>
                </div>
                <a href="${ctx}/other/list?oid=${fl.id}" target="_blank" onclick="" >
                    <h2 class="author-h2">
                       ${fl.nickName}
                    </h2>
                </a>

            </div>
        </c:forEach>

    </div>



    <!--被关注-->
    <div class="dreamland-bysee">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">被关注列表</font>
        </div>
        <br/>
        <c:forEach items="${byfollow}" var="bl">
        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="${ctx}/other/list?oid=${bl.id}" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="${bl.imgUrl}">
                </a>
            </div>
            <a href="${ctx}/other/list?oid=${bl.id}" target="_blank" onclick="" >
                <h2 class="author-h2">
                    ${bl.nickName}
                </h2>
            </a>

        </div>
        </c:forEach>
    </div>

</div>

    <!-- 中间内容展示 -->
    <div class="dreamland-content" style="background-color: white">
        <div class="content-bar">
            <div id="fa-dreamland" style="background-color: #B22222;width: 120px;text-align: center;height: 40px;line-height: 40px;float: left" onclick="release_dreamland();">

                 <span id="fa-span" style="color: white">发布的博客</span>

            </div>


        </div>

        <div id="release-dreamland" style="height: 700px;margin-top: 50px;width: 100%">
            <ul style="font-size: 12px" id="release-dreamland-ul">
                <c:forEach var="cont" items="${page.list}" varStatus="i">
                <li class="dreamland-fix">
                    <a href="${ctx}/jump/watch?cid=${cont.id}">${cont.title}</a>
                    <span class="bar-read">评论 (${cont.commentNum})</span>
                    <span class="bar-commend">${cont.upvote}人阅读</span>


                    <hr/>
                </li>
                </c:forEach>

            </ul>
        </div>
            <div id="release-dreamland-div" style="float: left;margin-left: 20px">

                <ul class="pager pager-loose" id="release-dreamland-fy">
                    <c:if test="${page.pageNum <= 1}">
                        <li class="previous"><a href="javascript:void(0);">« 上一页</a></li>
                    </c:if>
                    <c:if test="${page.pageNum > 1}">
                        <li class="previous" id=""><a onclick="changeToActive('category_x',null,${page.pageNum - 1},${otheruser.id})">« 上一页</a></li>
                    </c:if>
                    <c:forEach begin="1" end="${page.pages}" var="pn">
                        <c:if test="${page.pageNum==pn}">
                            <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                        </c:if>
                        <c:if test="${page.pageNum!=pn}">
                            <li ><a onclick="changeToActive('category_x',null,${pn},${otheruser.id})">${pn}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${page.pageNum>=page.pages}">
                        <li><a href="javascript:void(0);">下一页 »</a></li>
                    </c:if>
                    <c:if test="${page.pageNum<page.pages}">
                        <li><a onclick="changeToActive('category_x',null,${page.pageNum + 1},${otheruser.id})">下一页 »</a></li>
                    </c:if>

                </ul>
            </div>

        </div>


<!--右侧-->

<div class="ibx-advice" onmouseover="changeBackColor();" onmouseout="back2color();">
    <a href="${ctx}/jump/write"><span class="glyphicon glyphicon-edit" aria-hidden="true" style="color:#1b1b1b;font-size:30px;" title="写梦"></span></a>
</div>

<!--底部-->
<div class="foot" style="position: absolute;left: 0px;float: left;margin-top: 1920px;width: 100% ;height: 280px;background-color: #5e5e5e">

    <div style="margin-left: 400px;color: white">
        <div class="foot-nav clearfix">
            <div class="foot-nav-col">
                <h3>
                    关于
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            关于博客网
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            加入我们
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            联系方式
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    帮助
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            在线反馈
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            用户协议
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            隐私政策
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    下载
                </h3>
                <ul style="color: white">
                    <li>
                        <a href=""
                           target="_blank" rel="external nofollow" style="color: white">
                            Android 客户端
                        </a>
                    </li>
                    <li>
                        <a href="" rel="external nofollow" style="color: white">
                            iPhone 客户端
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    关注
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="http://www.dreamland.wang" onMouseOut="hideImg()"  onmouseover="showImg()" style="color: white">
                            微信
                            <div id="wxImg" style="display:none;height:50px;back-ground:#f00;position:absolute;color: white">
                                <img src="/images/dreamland.png"/><br/>
                                手机扫描二维码关注
                            </div>

                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="external nofollow" style="color: white">
                            新浪微博
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="external nofollow" style="color: white">
                            QQ空间
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- rgba(60,63,65,0.31)-->
        <hr style="position: absolute;background-color: rgba(161,171,175,0.31);width: 100%;height: 1px;left: 0px;margin-top: 10px"/>

        <div class="foot-nav clearfix" style="position: absolute;left: 0px;margin-top: 20px;margin-left: 400px;text-align: center">
            <div class="foot-copyrights" style="margin-left: 100px">
                <p style="color: white;font-size: 12px">
                    互联网ICP备案：京ICP备xxxxxx号-1
                </p>
                <p>
                    <span style="color: white;font-size: 12px">违法和不良信息举报电话：010-xxxxxxx</span>
                    <span style="color: white">邮箱：784503325@qq.com</span>
                </p>
                <p style="margin-top: 8px;color: white;font-size: 12px">&copy;Caayb博客网版权所有</p>
            </div>
        </div>
    </div>
</div>


</body>
<script>
    $(function () {
        var oid=${otheruser.id}
        var num = "${categorys.size()}";
        var tomVal = document.getElementById("dreamland-see").style.marginTop;
        var hgt = parseInt(num)*40+parseInt(tomVal.split("px")[0]);
        document.getElementById("dreamland-see").style.marginTop = hgt + "px";

        var val = "${manage}";
        if(val=="manage"){
            manage_dreamland();
        }

        $.ajax({
            type: 'post',
            url: '${ctx}/other/checkfollow',
            data: {"oid":oid},
            dataType: 'json',
            success:function (data) {
              if (data.msg=='success'){
                  $("#guanzhu").val("取消关注")
              }else {
                  $("#guanzhu").val("关注")
              }
            }
        })
    });
    //梦分类点击事件
   function changeToActive(id,category,pageNum,oid) {
       var ulist_id = "";
       if(typeof (id)=="object"){
           ulist_id = id.id;
       }else{
           ulist_id = id;
       }
       $("ul").remove("#release-dreamland-ul");
       $("ul").remove("#release-dreamland-fy");
       $(".list-group-item").attr("class","list-group-item");
       $("#"+ulist_id).attr("class","list-group-item active");
       $.ajax({
           type: 'post',
           url: '${ctx}/other/findByCategory',
           data: {"category": category,"pageNum":pageNum,"oid":oid},
           dataType: 'json',
           success: function (data) {
               var pageCate = data["pageCate"];
               if(pageCate=="fail"){
                   window.location.href = "${ctx}/login.jsp";
               }else{

               var ucList = pageCate.list;
               var startHtml = "<ul style='font-size: 12px' id='release-dreamland-ul'>";
               var endHtml = "</ul>";
               if(ucList!=null){
                  $(ucList).each(function () {
                      var contHtml = "<li class='dreamland-fix'><a href='${ctx}/jump/watch?cid="+this.id+"'>"+this.title+"</a> <span class='bar-read'>评论 ("+this.commentNum+")</span>"
                          +"<span class='bar-commend'>"+this.upvote+"人阅读</span><hr/></li>";
                      startHtml = startHtml + contHtml;
                  });
                var okHtml = startHtml + endHtml;

                //分页
                var stPageHtml = " <ul id='release-dreamland-fy' class='pager pager-loose'>";
                if(pageCate.pageNum<=1){
                    stPageHtml = stPageHtml + " <li class='previous'><a href='javascript:void(0);'>« 上一页</a></li>";
                }else if(pageCate.pageNum>1){
                    var num = parseInt(pageCate.pageNum) -1;
                   stPageHtml = stPageHtml + "<li class='previous'><a onclick='changeToActive("+ulist_id+",\""+category+"\","+num+","+oid+")'>« 上一页</a></li>";
                }

                var foHtml = "";
                for(var i = 1 ;i<= pageCate.pages;i++){
                    if(pageCate.pageNum==i){
                        foHtml = foHtml+ "<li class='active'><a href='javascript:void(0);'>"+i+"</a></li>";
                    }else{
                        foHtml = foHtml+ "<li ><a onclick='changeToActive("+ulist_id+",\""+category+"\","+i+","+oid+")'>"+i+"</a></li>";
                    }
                }

                 var teHtml = "";
                 if(pageCate.pageNum>=pageCate.pages){
                     teHtml = " <li><a href='javascript:void(0);'>下一页 »</a></li>";
                 }else if(pageCate.pageNum<pageCate.pages){
                     var num = parseInt(pageCate.pageNum) + 1;
                        teHtml = "<li><a onclick='changeToActive("+ulist_id+",\""+category+"\","+num+","+oid+")'>下一页 »</a></li>";
                 }
                var endPageHtml = "</ul>";

               var pageOkHtml = stPageHtml + foHtml + teHtml +endPageHtml;
               }

               $("#release-dreamland").append(okHtml);
               $("#release-dreamland-div").append(pageOkHtml);
           }
           }

       });
   }
    //发布梦点击事件
    function release_dreamland() {
        document.getElementById("fa-dreamland").style.backgroundColor = "#B22222";
        document.getElementById("fa-span").style.color = "white";

        document.getElementById("manage-dreamland").style.backgroundColor = "#F0F0F0";
        document.getElementById("manage-span").style.color = "black";

        document.getElementById("personal-div").style.backgroundColor = "#F0F0F0";
        document.getElementById("personal-span").style.color = "black";

        document.getElementById("release-dreamland").style.display = "";
        document.getElementById("personal-dreamland").style.display = "none";
        document.getElementById("update-dreamland").style.display = "none";

    }

   //关注事件
    function addfollow(oid) {
        var uid = "${user.id}";
        if(uid==''||uid==null){
            alert("请登录");
            window.location.href = "/login.jsp";
            return
        }
       var gz=$("#guanzhu").val()
        var flag="";
       if (gz=='关注') {
           $("#guanzhu").val("取消关注")
           flag="add";
       }else {
           $("#guanzhu").val("关注")
           flag="remove";
       }
       $.ajax({
           type: 'post',
           url: '${ctx}/other/updatefollow',
           data: {"oid":oid,"flag":flag},
           dataType: 'json',
           success:function (data) {
               if (data.msg=='no'){
                   alert("请不要关注自己")
                   $("#guanzhu").val("关注")
               }
           }

       })

    }


    function changeBackColor() {
        $(".glyphicon-edit").css("color","blue");
    }
    function back2color() {
        $(".glyphicon-edit").css("color", "black");
    }

</script>
</html>