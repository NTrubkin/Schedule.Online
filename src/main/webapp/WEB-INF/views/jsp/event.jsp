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
</head>
<body>
<%@include file="header.jsp"%>
<div id="mainPanel">
    <div id="recordsWithDividers">
        <p class="recordName">Событие</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Название</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite settingsInput" type="text" value="Событие 1">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Место</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="пл Минина">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell ">
                    <p>Начало</p>
                </td>
                <td class="settingsCell">
                    <input type="datetime-local">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Описание</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="Описание события 1">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Теги</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="нч лабораторная зачет">
                </td>
            </tr>
        </table>
        <button>Сохранить</button>
        <button>Удалить</button>
    </div>
</div>

</body>

</html>
