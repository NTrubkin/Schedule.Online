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
    <title>Schedule.Online - Аккаунт</title>
    <%@include file="meta.jsp" %>
</head>
<body>
<%@include file="header.jsp"%>

<div id="mainPanel">
    <div id="recordsWithDividers">
        <p class="recordName">Настройки аккаунта</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Имя</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite settingsInput" type="text" value="Никита">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Фамилия</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="Трубкин">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Email</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="trubkin.nikita@yandex.ru">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell ">
                    <p>Телефон</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite" type="text" value="+7 930 70 90 399">
                </td>
            </tr>
        </table>
        <br>
        <p class="recordName">Настройки оповещений</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Изменение настроек группы</p>
                </td>
                <td class="settingsCell">
                    <input type="checkbox">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Изменение расписания</p>
                </td>
                <td class="settingsCell">
                    <input type="checkbox">
                </td>
            </tr>
        </table>

    </div>
</div>

</body>
</html>
