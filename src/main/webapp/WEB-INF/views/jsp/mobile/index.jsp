<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 01.03.2018
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule.Online</title>

    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/index.js"></script>
</head>
<body>

<%@include file="header.jsp" %>

<div id="mainPanel">

    <span style="font-size:30px;cursor:pointer;" onclick="openNav()">&#9776;</span>
    <p id="withoutGroupPanel">
        Вы не состоите в группе. Ожидайте приглашения либо
        <button class="buttonOnWhite">создайте</button>
        новую группу
    </p>
    <div id="recordsWithDividers">

    </div>
</div>
<script>
    initIndexPage(${canEditLessons}, ${canEditEvents});
</script>
</body>
</html>
