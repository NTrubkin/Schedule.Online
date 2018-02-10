<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 10.02.2018
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="meta.jsp"/>
</head>
<body>
<h1 id="txt">hello, dude</h1>
<input id="in" type="text"/>
<button onclick="Cookies.set('usr', $('#in').val());">save</button>
<button onclick="$('#txt').text(Cookies.get('usr'))">load</button>
</body>
</html>
