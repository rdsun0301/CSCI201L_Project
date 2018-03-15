<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="data.User,utility.StringConstants" %>
<%
User currentUser = (User)session.getAttribute(StringConstants.CURRENTUSER);%>
<html>
<head>
	<meta charset="UTF-8">
	<title>User Settings</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pageEdit.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/settings.css"/>
	<script>
		window.onload = function() {
			var req = new XMLHttpRequest();
			req.open("POST", "${pageContext.request.contextPath}/UpdateUserInfoServlet");
			req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			
			req.onreadystatechange = function () {
				if(req.readyState === XMLHttpRequest.DONE && req.status === 200) {
					alert("To complete the action, please confirm it through your email");
				}
			}
			
			document.updateUserInfo.changeFName.onclick = function() {
				// Update User First Name
				var params = "action=fname&";
				params += "fname=" + document.updateUserInfo.fname.value;
				req.send(params);
			}
			
			document.updateUserInfo.changeLName.onclick = function() {
				// Update User Last Name
				var params = "action=lname&";
				params += "lname=" + document.updateUserInfo.lname.value;
				req.send(params);
			}
			
			document.updateUserInfo.changePassword.onclick = function() {
				// Update User Password
				var params = "action=password&";
				params += "password=" + document.updateUserInfo.password.value;
				req.send(params);
			}
			
			document.updateUserInfo.changeEmail.onclick = function() {
				// Update User Email
				var params = "action=email&";
				params += "email=" + document.updateUserInfo.email.value;
				req.send(params);
			}
			
			document.updateUserInfo.deleteUser.onclick = function() {
				// Update User Delete
				var params = "action=delete";
				req.send(params);
			}
		}
	</script>
</head>
<body>
<% String confirmation; %>
	<div id="header">
		<a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"><img src="${pageContext.request.contextPath}/img/icons/home-icon.png" class="header-icon" id="home-icon"/></a>
		<h1 id="logo">MyPage</h1>
		<a href="${pageContext.request.contextPath}/jsp/settings.jsp"><img src="${pageContext.request.contextPath}/img/icons/gear-icon.png" class="header-icon" id="gear-icon"/></a>
		<a href="${pageContext.request.contextPath}/jsp/index.jsp"><img src="${pageContext.request.contextPath}/img/icons/exit-icon.png" class="header-icon" id="exit-icon"/></a>
		<div style="text-align:right; font-size:9pt; padding-right:1.5%; margin-top:-1.5%;"> Logged in as <%=currentUser.GetFullName() %>
		</div>
	</div>
	<div>
		<div id = "padding" >
		</div>
		<br>
		<form name="updateUserInfo">
			<table id = "centeredTable" class="settingsTable">
				<tr>
					<td><input type="text" name="fname"  id="fname" placeholder="<%= currentUser.getfName() %>"></td>
					<td><input type="button" name="changeFName" id="changeFName" value="Change First Name"/></td>
				</tr>
				<tr>
					<td><input type="text" name="lname"  id="lname" placeholder="<%= currentUser.getlName() %>"></td>
					<td><input type="button" name="changeLName" id="changeLName" value="Change Last Name"/></td>
				</tr>
				<tr>
					<td><input type="password" name="password"  id="password"></td>
					<td><input type="button" name="changePassword" id="changePassword" value="Change Password"/></td>
				</tr>
				<tr>
					<td><input type="text" name="email"  id="email" placeholder="<%= currentUser.getEmail() %>"></td>
					<td><input type="button" name="changeEmail" id="changeEmail" value="Change Email"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" name="deleteUser"  id="deleteUser" value="Delete Account"></td>
				</tr>
				<tr>
					<% confirmation  = (String) (request.getAttribute("confirmation")); 
						if (confirmation == null) confirmation = ""; %>
					<%= confirmation %>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>