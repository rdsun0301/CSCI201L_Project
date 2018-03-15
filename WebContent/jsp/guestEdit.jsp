<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="data.*, utility.*" %>
<% 

 String content = (String)request.getSession().getAttribute(StringConstants.CONTENTS);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit GUI</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pageEditGUI.css" />
<style>
#testdiv {
	position: relative;
	top: 30px;
	left: 40%;
	width: 200px;
	height: 150px;
	border: 1px solid black;
	margin: 10px;
}
</style>
<script>
	function loadPage(){
		var f = document.getElementById("myPage");
		f.contentWindow.document.open();
		f.contentWindow.document.write('<%=content%>');
		f.contentWindow.document.close();
	}
</script>
<script src="${pageContext.request.contextPath}/js/iframeV2.js" type="text/javascript"></script>
<script>
	window.onload=function(){
		load();
	}	
</script>
</head>
<body>
	<div id="header">

		<h1 id="logo">MyPage</h1>
		
		<a href="${pageContext.request.contextPath}/jsp/index.jsp"><img src="${pageContext.request.contextPath}/img/icons/exit-icon.png" class="header-icon" id="exit-icon"/></a>
		<div style="text-align:right; font-size:9pt; padding-right:1.5%; margin-top:-1.5%;"> Logged in as Guest 
		</div>
	</div>
	<div id="toolBar">
		<div id="undo-cell" class="toolButton">
			<img src="${pageContext.request.contextPath}/img/icons/undo-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div id="redo-cell" class="toolButton">
			<img src="${pageContext.request.contextPath}/img/icons/redo-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" style="padding: 5px 8px;">
			<select id="font-family-menu">
				<option value="Times-New-Roman">Times New Roman</option>
				<option value="Arial">Arial</option>
				<option value="Comic-Sans-MS">Comic Sans MS</option>
				<option value="Impact">Impact</option>
			</select>
		</div>
		<div class="toolButton" style="padding: 5px 8px;">
			<select id="font-size-menu">
				<option value="12">12</option>
				<option value="16">16</option>
				<option value="20" selected="selected">20</option>
				<option value="24">24</option>
				<option value="28">28</option>
				<option value="32">32</option>
				<option value="36">36</option>
			</select>
		</div>
		<div id="bold-cell" class="toolButton">
			<img src="${pageContext.request.contextPath}/img/icons/bold-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div id="italic-cell" class="toolButton" id="icon-cell">
			<img src="${pageContext.request.contextPath}/img/icons/italic-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div id="underline-cell" class="toolButton" id="icon-cell">
			<img src="${pageContext.request.contextPath}/img/icons/underline-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div id="font-color-cell" class="toolButton"
			style="padding: 5px 8px; padding-top: 2px;">
			A
			<div id="font-color-indicator"
				style="background-color: black; width: 15px; height: 3px;"></div>
			<div id="font-color-menu">
				<div class="menu-span">
					<div class="color-cell">
						<div class="color-block" style="background-color: black"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: grey"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: lightgrey"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: white"></div>
					</div>
				</div>
				<div class="menu-span">
					<div class="color-cell">
						<div class="color-block" style="background-color: purple"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: brown"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: red"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: pink"></div>
					</div>
				</div>
				<div class="menu-span">
					<div class="color-cell">
						<div class="color-block" style="background-color: olive"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: green"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: greenyellow"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: yellow"></div>
					</div>
				</div>
				<div class="menu-span">
					<div class="color-cell">
						<div class="color-block" style="background-color: navy"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: blue"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: cyan"></div>
					</div>
					<div class="color-cell">
						<div class="color-block" style="background-color: lightblue"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="toolButton" id="align-left-cell">
			<img src="${pageContext.request.contextPath}/img/icons/align-left-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="align-center-cell">
			<img src="${pageContext.request.contextPath}/img/icons/align-center-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="align-right-cell">
			<img src="${pageContext.request.contextPath}/img/icons/align-right-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="unordered-list-cell">
			<img src="${pageContext.request.contextPath}/img/icons/unordered-list-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="ordered-list-cell">
			<img src="${pageContext.request.contextPath}/img/icons/ordered-list-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="add-link">
			<img src="${pageContext.request.contextPath}/img/icons/link-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="add-image">
			<img src="${pageContext.request.contextPath}/img/icons/img-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div class="toolButton" id="add-table">
			<img src="${pageContext.request.contextPath}/img/icons/table-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
		<div></div>
	</div>
	<div id="my-pages-directory"
		style="position: absolute; top: 130px; width: 160px; height: 630px; text-align: center;">
		<div>Progress will not be saved</div>
	</div>
	<div id="iframe-container">
		<iframe id="myPage" ></iframe>
	</div>


	<div id="popup">
		<form id="popup-form">
			<h5 id="popup-title"
				style="margin-bottom: 10px; margin-top: 0px; float: left"></h5>
			<div id="popup-quit" style="cursor: pointer">X</div>
			<input id="popup-input" type="text" placeholder=""
				style="padding: 2px;"> <input type="submit" value="Add"
				style="position: relative; left: 70px; width: 80px; background-color: #F5F5F5; cursor: pointer;">
		</form>
	</div>
	<div id="testdiv"
		style="position: relative; top: 30px; left: 40%; width: 200px; height: 150px; border: 1px solid black; margin: 10px; display: none;">
		<p id="demo"></p>
	</div>
</body>
</html>