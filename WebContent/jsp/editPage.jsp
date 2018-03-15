<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="data.*, utility.*,java.util.Vector" %>
<%User user = (User)request.getSession().getAttribute(StringConstants.CURRENTUSER); 
 Website website = (Website)request.getSession().getAttribute(StringConstants.WEBSITE);
 String content = (String)request.getSession().getAttribute(StringConstants.CONTENTS);
 content.replaceAll("\'","\\\'");
 Vector<String> fileNames = (Vector<String>)request.getSession().getAttribute("fileNames");
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
	
	
	function sendMessage(data) {
		websocket.send(data);
		return false;
	}
	
	function sendWebContents() {
		var f = document.getElementById("myPage");
		var content = f.contentWindow.document.documentElement.innerHTML;
		sendMessage(content);
	}
	
	<% String result = request.getLocalAddr();
	if(result.equals("0:0:0:0:0:0:0:1")) {
		result = "localhost";
	}%>
	
	var websocket = new WebSocket("ws://<%=result%>:8080/MyPage/editGUI");

	websocket.onopen = function (event) {
		var webpageName = document.getElementById("file-name").textContent.replace(".html");
		console.log(webpageName)
		sendMessage("<%=website.getName()%>:<%=user.getUsername()%>:"+webpageName);
	}

	websocket.onmessage = function (event) {
		var f = document.getElementById("myPage");
		f.contentWindow.document.open();
		f.contentWindow.document.write(event.data);
		f.contentWindow.document.close();
		return false;
	}
	
	websocket.onclose = function(event) {
		console.log(event);
		console.log("Connection Closed!");
	}
	
	function addWebpage() {
		req  = new XMLHttpRequest();
		req.open("POST", "${pageContext.request.contextPath}/AddWebpage");
		req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		var name = prompt("Please give your new webpage a name", "");
		req.send("website="+"<%=website.getName()%>"+"&newWebpage="+name);
		addFileDir(name);
	}
	window.onload=function(){
		load();
		//setting up newPage button
		var newPageCell = document.getElementById("add-file-cell");
		newPageCell.onmouseover=function(){
			newPageCell.style.backgroundColor = "#A0A0A0";	
		}
		newPageCell.onmouseout=function(){
			newPageCell.style.backgroundColor = "transparent";	
		}
		newPageCell.onclick = addWebpage;
		
		//setting up save button
		var saveCell = document.getElementById("save-cell");
		saveCell.onmouseover = function(){
			saveCell.style.backgroundColor = "#A0A0A0";
		}
		saveCell.onmouseout = function(){
			saveCell.style.backgroundColor = "transparent";
		}
		//saveCell.onclick = sendWebContents;
		
		
		<%for(int i = 0; i < fileNames.size();i++){%>
			addFileDir("<%=fileNames.get(i)%>");
			if("<%=fileNames.get(i)%>"==="index"){
				var indexDir = document.getElementById("my-pages-directory").lastElementChild;
				indexDir.style.backgroundColor = "#F5F5F5";
				indexDir.onmouseout = function(){return false};
			}
		<%}%>
		
	}
	
	function addFileDir(fileName){
		var dir = document.getElementById("my-pages-directory");
		var fileLink = document.createElement("div");
		fileLink.textContent = fileName;
		fileLink.className = "mypage-dir";
		fileLink.onmouseover = function(){
			this.style.backgroundColor = "#F5F5F5";
		}
		fileLink.onmouseout = function(){
			this.style.backgroundColor = "transparent";
		}
		fileLink.onclick = function(){

			document.getElementById('file-name').textContent = fileName+'.html';
			resetDir();
			this.onmouseout = function(){return false;}
			this.style.backgroundColor = "#F5F5F5";
			//send switch request
			var url = "LoadPageServlet?webpage="+fileName;
			var req = new XMLHttpRequest();
			req.open("GET",url);
			req.onreadystatechange = function(){
				if(req.readyState == 4 && req.status == 200)
				{
					var f = document.getElementById("myPage");
					f.contentWindow.document.open();
					f.contentWindow.document.write(req.responseText);
					f.contentWindow.document.close();
				}
			}
			req.send(null);
		}
		fileLink.ondblclick = function(event){
			event.preventDefault();
			//console.log("delete "+fileName+" request received");
			if(fileName==="index"){
				alert("You can't delete index page!");
				return;
			}
			
			var confirmation = confirm("Are you sure you want to delete?");
			if (!confirmation) {
				return;
			}
			var url = "DeleteWebpageServlet?webpage="+fileName;
			var req = new XMLHttpRequest();
			req.open("GET",url);
			req.onreadystatechange = function(){
				if(req.readyState == 4 && req.status == 200)
				{
					var dir = document.getElementById("my-pages-directory");
					if(fileLink.nextSibling!=null){
						var fileName = fileLink.nextSibling.textContent;
						document.getElementById('file-name').textContent = fileName+'.html';
						//console.log(fileName);
						var url = "LoadPageServlet?webpage="+fileName;
						var dreq = new XMLHttpRequest();
						dreq.open("GET",url);
						dreq.onreadystatechange = function(){
							if(dreq.readyState == 4 && dreq.status == 200)
							{
								var f = document.getElementById("myPage");
								f.contentWindow.document.open();
								f.contentWindow.document.write(dreq.responseText);
								f.contentWindow.document.close();
							}
						}
						dreq.send(null);
						fileLink.nextSibling.style.backgroundColor = "#F5F5F5";
						fileLink.nextSibling.onmouseout = function(){return false;};
					}
					else if(fileLink.previousSibling!=null){
						var fileName = fileLink.previousSibling.textContent;
						document.getElementById('file-name').textContent = fileName+'.html';
						console.log("previous sibling: "+fileName);
						var url = "LoadPageServlet?webpage="+fileName;
						var dreq = new XMLHttpRequest();
						dreq.open("GET",url);
						dreq.onreadystatechange = function(){
							if(dreq.readyState == 4 && dreq.status == 200)
							{
								var f = document.getElementById("myPage");
								f.contentWindow.document.open();
								f.contentWindow.document.write(dreq.responseText);
								f.contentWindow.document.close();
							}
						}
						dreq.send(null);
						fileLink.previousSibling.style.backgroundColor = "#F5F5F5";
						fileLink.previousSibling.onmouseout = function(){return false;};
					}
					
					dir.removeChild(fileLink);
				}
				
			}
			req.send(null);
		}
		dir.appendChild(fileLink);
	}

	function resetDir(){
		var dirs = document.getElementById("my-pages-directory").getElementsByClassName("mypage-dir");
		for(var i = 0; i < dirs.length; i++){
			dirs[i].style.backgroundColor = "transparent";
			dirs[i].onmouseover = function(){
				this.style.backgroundColor = "#F5F5F5";
			}
			dirs[i].onmouseout = function(){
				this.style.backgroundColor = "transparent";
			}
		}
	}
</script>
<script src="${pageContext.request.contextPath}/js/iframeV2.js" type="text/javascript"></script>
</head>
<body>
	<div id="header">
		<a href="${pageContext.request.contextPath}/jsp/dashboard.jsp"><img src="${pageContext.request.contextPath}/img/icons/home-icon.png" class="header-icon" id="home-icon"/></a>
		<h1 id="logo">MyPage</h1>
		<a href="${pageContext.request.contextPath}/jsp/settings.jsp"><img src="${pageContext.request.contextPath}/img/icons/gear-icon.png" class="header-icon" id="gear-icon"/></a>
		<a href="${pageContext.request.contextPath}/jsp/index.jsp"><img src="${pageContext.request.contextPath}/img/icons/exit-icon.png" class="header-icon" id="exit-icon"/></a>
		<div style="text-align:right; font-size:9pt; padding-right:1.5%; margin-top:-1.5%;"> Logged in as <%=user.GetFullName() %>
		</div>
	</div>
	<div id="toolBar">


		<div id="add-file-cell" class="toolButton">
			<img  src="${pageContext.request.contextPath}/img/icons/new-file-icon.png"style="max-height: 20px; max-width: 20px;" /> 

		</div>
		<div id="save-cell"class="toolButton">
			<img onclick="sendWebContents()" src="${pageContext.request.contextPath}/img/icons/save-icon.png"
				style="max-height: 20px; max-width: 20px;" />
		</div>
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
		<div id = "file-name">index.html</div>
	</div>
	<div id="my-pages-directory"
		style="position: absolute; top: 130px; width: 160px; height: 630px; text-align: center;">
		<div style="height:40px;line-height:40px;text-align:center;">My Pages</div>
		<!--  div class="mypage-dir" >Michael Shindler -
			home page.html</div-->
		<!-- div class="mypage-dir" >test.html</div-->
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