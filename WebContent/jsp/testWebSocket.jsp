<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Darkness, Your Old Friend</title>
<script>
var websocket = new WebSocket("ws://localhost:8080/MyPage/editGUI");

websocket.onopen = function (event) {
	websocket.send("test:yad");
}

websocket.onmessage = function (event) {
	console.log(event.data);
	websocket.send("test:yad");
}

</script>
</head>
<body>

</body>
</html>