<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="../css/dashboard.css">
	<link rel="stylesheet" href="../css/home.css">
	<title>MyPage</title>
</head>
<body>
	<div id = "dash">
		<a href = "${pageContext.request.contextPath}/jsp/home.jsp">
			<img src = "../img/dash/home.png" id = "home_icon" class = "clickable" alt = "home" title = "Home Button">
		</a>
		<a href = "${pageContext.request.contextPath}/jsp/newpage.jsp">
			<img src = "../img/dash/newpage.png" id = "newpage_icon" class = "clickable" alt = "new" title = "New Page">
		</a>
		<div id = "mypage_title">
			MyPage
		</div>
		<a href = "${pageContext.request.contextPath}/jsp/settings.jsp">
			<img src = "../img/dash/settings.png" id = "settings_icon" class = "clickable" alt = "settings" title = "Settings">
		</a>
		<a href = "${pageContext.request.contextPath}/jsp/login.jsp">
			<img src = "../img/dash/logout.png" id = "logout_icon" class = "clickable" alt = "logout" title = "Log Out">
		</a>
	</div>
	
	<br>
	
	<div id = "website_list_container">
		<table id = "website_list">
			<tbody>
			<!-- for each website... -->
				<br><br>
				<tr>
					<td>
						<div id = "site_name">
							Site 1<br>
						</div>
						<a href = "${pageContext.request.contextPath}/jsp/home.jsp">
							<img src = "../img/default_website.jpg" id = "website_icon" class = "clickable" alt = "site" title = "Website">
						</a>
					</td>
					
					<td>
						<table id = "sub_list">
							<tbody>
								<tr>
									<td>Edit</td>
								</tr>
								<tr>
									<td>Share</td>
								</tr>
								<tr>
									<td>Publish</td>
								</tr>
								<tr>
									<td>Delete</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			<!-- end for loop -->
			</tbody>
		</table>
	</div>
</body>
</html>