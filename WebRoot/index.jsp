<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
<head>
<title>图书馆管理系统</title>
<meta charset="UTF-8">
<link href="CSS/style.css" rel="stylesheet">
<style type="text/css">
ul{
	list-style: none;
	padding-top:100px;
	padding-left:345px;
}
li{
	padding:10px;
}
</style>
<script type="text/javascript">
function check(form){
	if (form.name.value==""){
		alert("请输入管理员名称!");form.name.focus();return false;
	}
	if (form.pwd.value==""){
		alert("请输入密码!");form.pwd.focus();return false;
	}	
}
</script>
</head>
<body>
<div style="background-color:#c3a339;height:523px;" class="tableBorder">
	<div style="background-image:url(Images/login.jpg);height:255px;margin-top:142px;">
         <form name="form1" method="post" action="manager?action=login">
              <ul>
              	<li>管理员名称：<input name="name" type="text" class="logininput" id="name" size="27"></li>
                <li>管理员密码：<input name="pwd" type="password" class="logininput" id="pwd" size="27"></li>
                <li><input name="Submit" type="submit" value="确定" onClick="return check(form1)">
                        &nbsp;
                        <input name="Submit3" type="reset"  value="重置">&nbsp;
                        <input name="Submit2" type="button" value="关闭" onClick="window.close();"></li>
              </ul>
         </form>    
    </div>
    <div class="word_login" style="padding-top:10px;text-align:center;line-height:140%;">
    CopyRight &copy; 2012 www.jxust-nc.cn 江西理工大学南昌校区<br>
             本站请使用IE9.0或以上版本 1024*768为最佳显示效果</div>
</div>
</body>
</html>
