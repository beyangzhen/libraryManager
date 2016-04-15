<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.ManagerDAO"%>
<%@ page import="com.actionForm.ManagerForm"%>
<%@ page import="com.action.Manager"%>
<%
ManagerDAO managerDAO=new ManagerDAO();
ManagerForm form1=(ManagerForm)managerDAO.query_p((String)session.getAttribute("manager")); //将查询的managerForm1对象赋给form1对象
int sysset1=0;
int readerset1=0;
int bookset1=0;
int borrowback1=0;
int sysquery1=0;
if(form1!=null){
	sysset1=form1.getSysset();
	readerset1=form1.getReaderset();
	bookset1=form1.getBookset();
	borrowback1=form1.getBorrowback();
	sysquery1=form1.getSysquery();
}
%>
<style>
nav ul{
	list-style:none;
	margin: 0px;
	padding: 0px;
}
nav li{
	height:24px;
	float:left;
	padding-top:6px;
}
nav{
	width:778px; 
	background-color:#FFF;
	height:39px;padding: 0px;
	margin: 0px;
	background-image:url(Images/navigation_bg_bottom.gif);
	background-position: bottom;
	background-repeat: repeat-x; 
}
</style>
<script src="JS/onclock.JS"></script>
<script src="JS/menu.JS"></script>
		<script type="text/javascript">
			function quit(){
				if(confirm("真的要退出系统吗?")){
					window.location.href="logout.jsp";
				}
			}
		</script>
<div class="menuskin" id="popmenu" onmouseover="clearhidemenu();highlightmenu(event,'on')" onmouseout="highlightmenu(event,'off');dynamichide(event)" style="Z-index:100;position:absolute;"></div>
<nav>
	<ul>
    	<li style="width:230px;background-color:#c69a0a;text-align:center"><span id="bgclock" class="word_white"></span></li>
        <li style="width:538px;background-color:#ad7c28;text-align: right;padding-right:10px;" class="word_white">
	        <a href="main.jsp" class="word_white">首页</a> |
		    <%if(sysset1==1){%><a  onmouseover=showmenu(event,sysmenu) onmouseout=delayhidemenu() class="word_white" style="CURSOR:pointer" >系统设置</a> | <%}%>
		    <%if(readerset1==1){%><a  onmouseover=showmenu(event,readermenu) onmouseout=delayhidemenu() style="CURSOR:pointer"  class="word_white">读者管理</a> | <%}%>
		    <%if(bookset1==1){%><a  onmouseover=showmenu(event,bookmenu) onmouseout=delayhidemenu() class="word_white" style="CURSOR:pointer" >图书管理</a> | <%}%>
		    <%if(borrowback1==1){%><a  onmouseover=showmenu(event,borrowmenu) onmouseout=delayhidemenu() class="word_white" style="CURSOR:pointer">图书借还</a> | <%}%>
		    <%if(sysquery1==1){%><a  onmouseover=showmenu(event,querymenu) onmouseout=delayhidemenu()  class="word_white" style="CURSOR:pointer" >系统查询</a> | <%}%>
	        <a  href="manager?action=querypwd" class="word_white">更改口令</a> | <a href="#" onClick="quit()" class="word_white">退出系统</a>
        </li>
    </ul>，
</nav>
