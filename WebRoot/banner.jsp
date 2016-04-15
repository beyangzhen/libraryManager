<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String manager=(String)session.getAttribute("manager");
//验证用户是否登录
if (manager==null || "".equals(manager)){
	response.sendRedirect("index.jsp");
}
%>
<script type="text/javascript">
<!--
window.onload=function(){
	clockon("bgclock");
}
//-->
</script>
<header style="width:328px;height:34px;background-image:url(Images/top_bg.gif);padding-top:84px;padding-left:450px;">
当前登录用户：<%=manager%>
</header>
