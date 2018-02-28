<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 29.01.2018
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>

<html>
<head>
    <title>Schedule.Online</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/index.js"></script>
</head>

<body onload="initIndexPage()">
<%@include file="header.jsp" %>

<div id="mainPanel">
    <div id="recordsWithDividers">
        <p id="withoutGroupPanel">
            Вы не состоите в группе. Ожидайте приглашения либо <button class="buttonOnWhite" onclick="createGroup()">создайте</button> новую группу
        </p>
    </div>
</div>
</body>
</html>
