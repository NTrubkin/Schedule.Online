<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 30.01.2018
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule.Online - Группа</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/group.js"></script>
    <script>
        if(!window.mobilecheck()) {
            window.location.href = urlPrefix + '/group';
        }
    </script>
</head>
<body>
<%@include file="header.jsp" %>

<div id="mainPanel">
    <span style="font-size:30px;cursor:pointer;" onclick="openNav()">&#9776;</span>
    <div id="recordsWithDividers">
        <p class="recordName">Настройки группы</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Название</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <input id="nameFld" class="textbox textboxOnWhite settingsInput" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Лидер</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p id="leaderFld"></p>
                </td>
            </tr>
        </table>
        <br>
        <p class="recordName">Участники</p>
        <div id="membersTableHolder">
            <table id="membersTable" class="settingsTable recordDetails ">

            </table>
        </div>

        <div id="controlBlock">
            <button onclick="saveGroup()">Сохранить</button>
            <br>
            <br>
            <br>
            <input id="inviteFld" class="textbox textboxOnWhite" type="text" placeholder="Email или Телефон">
            <button onclick="invite()">Пригласить</button>
        </div>
    </div>
</div>

<script>initGroupPage('${membersDTO}', '${permissionsDTO}')</script>
</body>
</html>
