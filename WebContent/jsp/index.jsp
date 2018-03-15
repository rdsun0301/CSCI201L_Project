<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
<% String error; %>
	<div id="header">
		<div id="logo">
			<h1>MyPage</h1>
		</div>
		<div id="login">
			<form name="login" onsubmit="return login()" method="POST" action="${pageContext.request.contextPath}/LoginServlet">
				<table>
					<thead>
						<tr>
							<td colspan="3">Log In</td>
						</tr>
					</thead>
					<tr>
						<td><input type="text" name="username" id="username" placeholder=" Username" style="border-radius: 7px"/></td>
						<td><input type="password" name="password" id="password" placeholder=" Password" style="border-radius: 7px"/></td>
						<td><input type="submit" value="Log In" id="login-submit" style="border-radius: 7px"/></td>
						<td>
						<% error  = (String) (request.getAttribute("errorlogin")); 
							if (error == null) error = ""; %>
						<%= error %>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="content">
		<table>
			<tr>
				<td id="info">
					<table>
						<thead>
							<tr>
								<th>
									
									<h2>Create personal websites with ease and share them with your friends</h2>
								</th>
							</tr>
						</thead>
						<tr>
							<td>
								<img src="${pageContext.request.contextPath}/img/create.png"/>
								<h2>Create</h2> Personal Websites with Ease
							</td>
						</tr>
						<tr>
							<td>
								<img src="${pageContext.request.contextPath}/img/share.png">
								<h2>Share</h2> your Creation with the World
							</td>
						</tr>
						<tr>
							<td>
								<img src="${pageContext.request.contextPath}/img/collaborate.png"/>
								<h2>Collaborate</h2> to Create Shared Websites
							</td>
						</tr>
						<tr>
							<td>
							<form action = "${pageContext.request.contextPath}/GuestServlet">
								<input type="submit" value="Try It Now" style="border-radius: 7px"/>						
							</form>
								
							</td>
						</tr>
					</table>
				</td>
				<td id="signup">
					<form name="signup" onsubmit="return signup()" method="POST" action="${pageContext.request.contextPath}/SignUpServlet">
						<table>
							<thead>
								<tr>
									<th colspan="2">Sign Up</th>
								</tr>
							</thead>
							<tr>
								<td><input type="text" name="firstname" id="firstname" placeholder=" First Name"/></td>
								<td><input type="text" name="lastname" id="lastname" placeholder=" Last Name"/></td>
							</tr>
							<tr>
								<td colspan="2"><input type="text" name="email" id="email" placeholder=" Email"/></td>
							</tr>
							<tr>
								<td colspan="2"><input type="text" name="username" id="username" placeholder=" Username"/></td>
							</tr>
							<tr>
								<td colspan="2"><input type="password" name="password" id="password" placeholder=" Password"/></td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" value="Sign Up" id="signup-submit"/></td>
							</tr>
						</table>
					<% error  = (String) (request.getAttribute("errorsignup")); 
						if (error == null) error = ""; %>
					<%= error %>	
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>