<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改手机号</title>

    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
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

    </style>


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
                <li class="active"><a href="${ctx}/personal/list">我的博客</a></li>
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
                <li><a href="${ctx}/writedream?id=${user.id}">写博客</a></li>
            </ul>
            <ul class="nav navbar-nav" style="margin-left: 680px">
                <li><a href="${ctx}/list?id=${user.id}">${user.nickName}

                </a></li>
            </ul>
            <img src="${ctx}/images/q.png" width="30" style="margin-top: 4px"/>
        </div><!-- END .navbar-collapse -->
    </div>

</nav>
    <!-- -->
<div style="margin-left: auto;margin-right: auto;width: 460px;height: 550px;background-color: white">

        <div style="text-align: center">
            <div style="margin-top: 50px;float: left;margin-left: 180px">
                    <span style="font-size: 23px"><strong> 修改手机号</strong></span>

            </div>
            <div style="float: left;background-color:#EAEAEA;height: 1px;width: 300px;margin-left: 80px;margin-top: 30px"> </div>

            <div style="float: left;margin-top: 40px;margin-left: 50px">

                <form action="${ctx}/personal/updateCellPhone" method="post" id="update_password">
                    <input onblur="oldPassword();" id="old_password" name="phone" type="text" placeholder="    输入新手机号" style="width: 350px;height: 50px"/><br/><br/>
                    <span id="old_span" style="color:red;"></span><br/>
                    <input onblur="newPassword();" id="password" name="code" type="text" placeholder="    输入验证码" style="width: 350px;height: 50px"/><br/><br/>
                    <div class="col-xs-6">
                        <button class="btn btn-primary btn-block" id="go">获取验证码</button>
                    </div>
                    <br/><br/>
                    <button onclick="surePost();" style="background-color: #0a67fb;height: 50px"  class="btn btn-block " type="button"><span style="color: white">确认</span></button>
                </form>
            </div>
        </div>



</div>
<!--底部-->
<div class="foot" style="position: absolute;left: 0px;float: left;margin-top: 30px;width: 100% ;height: 280px;background-color: #5e5e5e">

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
                                <img src="${ctx}/images/dreamland.png"/><br/>
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
        <hr style="position: absolute;background-color: rgba(161,171,175,0.31);width: 100%;height: 1px;left: 0px;margin-top: 10px"/>

        <div class="foot-nav clearfix" style="position: absolute;left: 0px;margin-top: 20px;margin-left: 400px;text-align: center">
            <div class="foot-copyrights" style="margin-left: 100px">
                <p style="color: white;font-size: 12px">
                    互联网ICP备案：京ICP备xxxxxx号-1
                </p>
                <p>
                    <span style="color: white;font-size: 12px">违法和不良信息举报电话：010-xxxxxxx</span>
                    <span style="color: white">>邮箱：784503325@qq.com</span>
                </p>
                <p style="margin-top: 8px;color: white;font-size: 12px">&copy;Caayb博客网版权所有</p>
            </div>
        </div>
    </div>
</div>


</body>
<script>


 var f1 = false;
function oldPassword() {
   var old =  $("#old_password").val();

   if(old==null || old.trim()==''){
       document.getElementById("old_span").innerHTML = "请输入手机号！";
       f1 = false;
   }else if(old.length < 11){
       document.getElementById("old_span").innerHTML = "手机号非法，请重新输入！";
       f1 = false;
   }
   else {
           $.ajax({
               url:"${ctx}/login/phone",
               dataType:"json",
               type:"post",
               data:{phone:old},
               success:function (data) {
                   if (data.message=='success'){
                       document.getElementById("old_span").innerHTML = "";
                       f1 = true;
                   }else {
                       document.getElementById("old_span").innerHTML = "手机号已被注册，请重新输入！";
                       f1 = false;
                   }
               }
           })

}
}

 var f2 = false;
function newPassword() {
    var p = $("#password").val();
    if(p==null || p.trim()==''){
        document.getElementById("old_span").innerHTML = "请输入验证码！";
        f2 = false;
    }else if(p.length < 6){

            $("#old_span").text("验证码长度少于6位，请重新输入！").css("color","red");
            f2 =  false;

    } else {
        document.getElementById("old_span").innerHTML = "";
        f2 = true
    }
}
 function countDown(s){
     if(s <= 0){
         $("#go").text("重新获取");
         $("#go").removeAttr("disabled");
         return;
     }
     /* $("#go").val(s + "秒后重新获取");*/
     $("#go").text(s + "秒后重新获取");
     setTimeout("countDown("+(s-1)+")",1000);
 }

 //获取验证码
 $(function () {
     var go = document.getElementById('go');

     go.onclick = function (ev){
         if(!f1){
           alert("手机号非法或者已注册")
         }else {
             //  发送短信给用户手机..
             // 1 发送一个HTTP请求，通知服务器 发送短信给目标用户
             var telephone =$("#old_password").val();// 用户输入的手机号
             // 用户输入手机号校验通过
             $("#go").attr("disabled", "disabled");
             countDown(60);

             $.ajax({
                 method: 'POST',
                 url: '${ctx}/personal/sendSms',// 发送验证码给ActiveQM, 同时保存验证码到redis数据库
                 data : {
                     telephone : telephone
                 },
                 success:function(data) {
                     var tt = data.msg;
                     if(tt){
                         alert("发送短信成功!");

                     }else{
                         alert("发送短信出错，请联系管理员");
                     }
                 }
             });
         }


         var oEvent = ev || event;
         //js阻止链接默认行为，没有停止冒泡
         oEvent.preventDefault();
         return false;

     }
 });

function surePost() {
    if(f1 && f2){
        $("#update_password").submit();

    }else {
        $("#old_span").text("请重新输入手机或验证码！").css("color","red");
    }
}
</script>
</html>