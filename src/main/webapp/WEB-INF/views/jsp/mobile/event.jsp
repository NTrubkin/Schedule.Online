<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 11.02.2018
  Time: 4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule.Online - Событие</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/event.js"></script>
    <script>
        if(!window.mobilecheck()) {
            var eventDTO = JSON.parse('${eventDTO}');
            window.location.href = urlPrefix + '/event?id=' + eventDTO.id;
        }
    </script>
</head>
<body>
<%@include file="header.jsp"%>
<div id="mainPanel">
    <span style="font-size:30px;cursor:pointer;" onclick="openNav()">&#9776;</span>
    <div id="recordsWithDividers">
        <p class="recordName">Событие</p>
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
                    <p>Место</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <input id="placeFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell ">
                    <p>Начало</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <input id="startDTPiker" type="datetime-local">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Описание</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <input id="descriptionFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Теги</p>
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <input id="tagsFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
        </table>
        <button onclick="updateEvent()">Сохранить</button>
        <button onclick="deleteEvent()">Удалить</button>
    </div>
</div>
<script>initEventPage('${eventDTO}')</script>
</body>

</html>
