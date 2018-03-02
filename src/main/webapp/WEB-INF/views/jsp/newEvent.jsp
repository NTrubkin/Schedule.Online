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
    <title>Schedule.Online - Новое событие</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/event.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<div id="mainPanel">
    <div id="recordsWithDividers">
        <p class="recordName">Новое событие</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Название</p>
                </td>
                <td class="settingsCell">
                    <input id="nameFld" class="textbox textboxOnWhite settingsInput" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Место</p>
                </td>
                <td class="settingsCell">
                    <input id="placeFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell ">
                    <p>Начало</p>
                </td>
                <td class="settingsCell">
                    <input id="startDTPiker" type="datetime-local">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Описание</p>
                </td>
                <td class="settingsCell">
                    <input id="descriptionFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Теги</p>
                </td>
                <td class="settingsCell">
                    <input id="tagsFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
        </table>
        <button onclick="createNewEvent()">Создать</button>
    </div>
</div>
<script>initNewEventPage()</script>
</body>

</html>
