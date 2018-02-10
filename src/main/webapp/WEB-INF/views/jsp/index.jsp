<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 29.01.2018
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            <a id="accountLogout"><img src="${urlPrefix}resources/icon/logout32-4.png"></a>
            <a id="accountName">FIRSTNAME<br>SECONDNAME</a>
        </div>
        <div id="groupBlock" class="menuBlock">
            <a id="groupName">GROUP</a>
        </div>
        <div class="menuBlock menuRegularBlock">
            <form>
                <p class="menuHeader">Фильтры</p>
                <label>Занятия</label>
                <input type="checkbox">
                <br>
                <label>События</label>
                <input type="checkbox">
                <br>
                <label>Поиск</label>
                <input type="text">
                <br>
                <label>Теги</label>
                <input type="text">
                <br>
                <label>Скрыть прошедшее</label>
                <input type="checkbox">
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
        <!--
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">18:30</p>
                    <div class="headerItem">
                        <p class="recordName eventName">
                            Событие 1
                        </p>
                        <p class="recordDetails">
                            пл. Минина и Пожарского
                        </p>
                    </div>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
-->

    </div>
</div>

</body>

</html>
