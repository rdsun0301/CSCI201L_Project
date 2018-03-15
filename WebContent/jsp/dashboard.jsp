<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.*, utility.*, webHosting.*, java.util.Vector" %>
<%

	//Hard coded user
	//User currentUser = new User("Gary", "Oak", "goak@gmail.com", "goak", "123");
	//session.setAttribute(StringConstants.CURRENTUSER, currentUser);
	User currentUser = (User)session.getAttribute(StringConstants.CURRENTUSER);
	//System.out.println(currentUser);
	String fullName = currentUser.getfName()+" "+currentUser.getlName();
	//Set up some websites for testing
	
	
	//Save the values that will be outputted
	int numSites = currentUser.getWebsites().size();
	//Separate sites owned by current user from sites shared with current user
	Vector<Website> ownedSites = new Vector<Website>();
	Vector<Website> sharedSites = new Vector<Website>();
	for (int i =0; i< numSites; ++i) {
		if (currentUser.getWebsites().get(i).getOwner().equals(currentUser.getUsername())) {
			ownedSites.add(currentUser.getWebsites().get(i));
		} else {
			sharedSites.add(currentUser.getWebsites().get(i));
		}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pageEdit.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dropdown.css"/>
	<script>
		function websiteManager(action, website) {
			req  = new XMLHttpRequest();
			req.open("POST", "${pageContext.request.contextPath}/ManageWebsiteServlet");
			req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			if(action == "edit") {
				req.onreadystatechange = function() {
					if(req.readyState === XMLHttpRequest.DONE && req.status === 200) {
						console.log(req.response);
						document.write(req.response);
					} 
				}
				req.send("action="+action+"&website="+website);
			}
			else if(action == "share") {
				var username = prompt("Please enter the username of the user you want to share this website with" , "");
				req.onreadystatechange = function() { 
					if(req.readyState === XMLHttpRequest.DONE) {
						if(req.status === 404) {
							alert(username + " does not exist in the database!");
						} else if(req.status === 409) {
							alert("You can't share a website with yourself");
						} else if(req.status === 600) {
							alert("Website is already shared with specified user.");
						}
					} else {
						location.reload();
					}
				}
				req.send("action="+action+"&website="+website+"&username="+username);
			} else if(action == "publish") {
				if(confirm("Are you sure you want to publish your website in its current state?")) {
					req.onreadystatechange = function() { 
						if(req.readyState === XMLHttpRequest.DONE && req.status === 200) {
							location.reload();
						}
					}
					req.send("action="+action+"&website="+website);
				}
			} else if(action == "delete") {
				if(confirm("Are you sure you want to delete your website? You will lose all access and information related to it")) {
					req.onreadystatechange = function() { 
						if(req.readyState === XMLHttpRequest.DONE && req.status === 200) {
							location.reload();
						}
					}
					req.send("action="+action+"&website="+website);
				}
			} else {
				req.send("action="+action+"&website="+website);
			}
		}
	
		function showDropDown(i) {
		    document.getElementById("myDropdown"+i).classList.toggle("show");
		}
	
		window.onload = function() {
			// Close the dropdown menu if the user clicks outside of it
			window.onclick = function(event) {
			  if (!event.target.matches('.dropbtn')) {
			    var dropdowns = document.getElementsByClassName("dropdown-content");
			    var i;
			    for (i = 0; i < dropdowns.length; i++) {
			      var openDropdown = dropdowns[i];
			      if (openDropdown.classList.contains('show')) {
			        openDropdown.classList.remove('show');
			      }
			    }
			  }
			}
		};
		
		function addWebsite() {
		    var name = prompt("Please give your new website a name", "");
		    
		    if (name != null) {
		    	//Save the name in session
		       session.setAttribute("newWebsite", name);
		    }
		}
		</script>
	</script>
</head>
<body>
<% String error; %>
	<div id="header">
		<h1 id="logo">MyPage</h1>
		<a href="${pageContext.request.contextPath}/jsp/settings.jsp"><img src="${pageContext.request.contextPath}/img/icons/gear-icon.png" class="header-icon" id="gear-icon"/></a>
		<a href="${pageContext.request.contextPath}/jsp/index.jsp"><img src="${pageContext.request.contextPath}/img/icons/exit-icon.png" class="header-icon" id="exit-icon"/></a>
		<div style="text-align:right; font-size:9pt; padding-right:1.5%; margin-top:-1.5%;"> Logged in as <%=fullName %>
		</div>
	</div>
	<div>
		<div id = "padding" >
		</div>
		<br>
		<table id = "centeredTable">
			<tr>

			<!-- <form name="AddWebsite"  method="POST" action="${pageContext.request.contextPath}/AddWebsiteServlet"> -->

			<% error = (String) (request.getAttribute("error")); 
						if (error == null) error = ""; %>
					<%= error %></tr>
			<tr>
				<form name="login" onsubmit="return login()" method="POST" action="${pageContext.request.contextPath}/AddWebsiteServlet">

					<td><input type="submit" value="Add Website" style="border-radius: 7px" /></td>
					<td><input type="text" name="newWebsite" placeholder="Website name" style="border-radius: 7px"/></td>
						
				</form>
			</tr>
			<tr>
				<th id = "dashTH"> Owned Website(s) </th>
				<th id = "dashTH"> Status </th>
				<th id = "dashTH"> Shared with </th>
				<th id = "dashTH"> Manage </th>
			</tr>
			<% //Display all the user-owned websites
			// i to keep track of dropdown menus
			int i = 0;
			for(Website ws: ownedSites){ %>
			<tr>
				<td id = "dashTD"> <%=ws.getName() %> </td>
				<td id = "dashTD"> 
					<%if (ws.getIsPublished()) { %>
							Online
					<%} else { %>
							Offline <% } %> </td>
				<td id = "dashTD">
					<%if (ws.getSharedUsers().size() == 0) { %>
						- 
					<%} else { 
						for (String user: ws.getSharedUsers()) { %>
							<%= user %> <br/>
						<% } } %></td>
				<td>
					<div class="dropdown">
					<!-- "i" keeps each dropdown independent -->
					  <button onclick="showDropDown(<%=++i %>)" class="dropbtn">Manage</button>
					  <div id=<%="myDropdown"+i%> class="dropdown-content">
					    <a href="${pageContext.request.contextPath}/ManageWebsiteServlet?action=edit&website=<%=ws.getName()%>">Edit</a>
					    <a onclick="websiteManager('share', '<%=ws.getName()%>')">Share</a>
					    <a onclick="websiteManager('publish', '<%=ws.getName()%>')">Publish</a>
					    <a onclick="websiteManager('delete', '<%=ws.getName()%>')">Delete</a>
					    <a href="https://mypagecs201.github.io/<%=currentUser.getUsername() %>/<%=ws.getName() %>/index.html" target="_blank" >Open</a>
					  </div>
					</div>
				</td>
			</tr>
			<% }  %>
		</table>
		<br>
		<table id = "centeredTable">
			<tr>
				<th id = "dashTH"> Shared Website(s) </th>
				<th id = "dashTH"> Status </th>
				<th id = "dashTH"> Owner </th>
				<th id = "dashTH"> Manage </th>
			</tr>
			<% //Display all the websites shared with user
			// i to keep track of dropdown menus
			for(Website ws: sharedSites){ %>
			<tr>
				<td id = "dashTD"> <%=ws.getName() %> </td>
				<td id = "dashTD"> <%if (ws.getIsPublished()) { %>
													Online
													<%} else { %>
													Offline <% } %> </td>
				<td id = "dashTD"> <%=ws.getOwner() %></td>
				<td>
					<div class="dropdown">
						<button onclick="showDropDown(<%=++i %>)" class="dropbtn">Manage</button>
					  <div id=<%="myDropdown"+i%> class="dropdown-content">
					    <a href="${pageContext.request.contextPath}/ManageWebsiteServlet?action=edit&website=<%=ws.getName()%>">Edit</a>
					    <a href="https://mypagecs201.github.io/<%=ws.getOwner() %>/<%=ws.getName() %>/index.html" target="_blank" >Open</a>
					  </div>
					</div>
				</td>
			</tr>
			<%} %>
		</table>
	</div>
</body>
</html>