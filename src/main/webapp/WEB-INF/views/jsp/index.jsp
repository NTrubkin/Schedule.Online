<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 29.01.2018
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Schedule.Online</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/index.js"></script>
</head>

<body onload="initIndexPage()">
<div id="menuPanel">
    <div id="menuContentPanel">
        <div id="logoBlock" class="menuBlock">
            <p>Schedule<br><span style="color: rgb(82, 94, 116); font-size: 64; line-height: 0;">.</span>Online</p>
        </div>
        <div id="accountBlock" class="menuBlock">
            <a id="accountLogout" href="${urlPrefix}/login?logout"><img src="${urlPrefix}/resources/icon/logout32-4.png"></a>
            <a id="accountName">FIRSTNAME<br>SECONDNAME</a>
        </div>
        <div id="groupBlock" class="menuBlock">
            <a id="groupName">GROUP</a>
        </div>
        <div class="menuBlock menuRegularBlock">
            <form>
                <p class="menuHeader">Фильтры</p>
                <label for="lessonFilter">Занятия</label>
                <input id="lessonFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();" checked>
                <br>
                <label for="eventFilter">События</label>
                <input id="eventFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();" checked>
                <br>
                <label for="searchFilter">Поиск</label>
                <input id="searchFilter" type="text" onchange="saveFilter(); loadData(); showRecords();">
                <br>
                <label for="tagsFilter">Теги</label>
                <input id="tagsFilter" type="text" onchange="saveFilter(); loadData(); showRecords();">
                <br>
                <label for="hideLastFilter">Скрыть прошедшее</label>
                <input id="hideLastFilter" type="checkbox" onchange="saveFilter(); loadData(); showRecords();">
                <br>
            </form>
        </div>

        <div class="menuBlock menuRegularBlock">
            <p class="menuHeader">Действия</p>
            <a>Добавить занятие</a>
            <br>
            <a>Добавить событие</a>
            <br>
            <a>Перейти к текущему</a>
            <br>
        </div>
    </div>
</div>

<div id="mainPanel">
    <div id="recordsWithDividers">

    </div>
</div>

</body>

</html>
