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
</head>
<body>
<%@include file="header.jsp" %>

<div id="mainPanel">
    <div id="recordsWithDividers">
        <p class="recordName">Настройки группы</p>
        <table class="settingsTable recordDetails">
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Имя</p>
                </td>
                <td class="settingsCell">
                    <input class="textbox textboxOnWhite settingsInput" type="text" value="15-AC">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Лидер</p>
                </td>
                <td class="settingsCell">
                    <p>Никита Трубкин</p>
                </td>
            </tr>
        </table>
        <br>
        <input class="textbox textboxOnWhite" type="text" placeholder="Email или Телефон">
        <button>Пригласить</button>
        <table class="settingsTable recordDetails ">
            <tr>
                <th class="settingsCell">
                    <p>Имя</p>
                </th>
                <th class="bigTableCell">
                    <p>Админ.</p>
                </th>
                <th class="bigTableCell">
                    <p>Ред. занятий</p>
                </th>
                <th class="bigTableCell">
                    <p>Ред. событий</p>
                </th>
                <th class="bigTableCell">
                    <p>Удалить</p>
                </th>
                <th class="bigTableCell">
                    <p>Назначить главным</p>
                </th>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Никита Трубкин</p>
                </td>
                <td class="bigTableCell">
                    <input type="checkbox">
                </td>
                <td class="bigTableCell">
                    <input type="checkbox">
                </td>
                <td class="bigTableCell">
                    <input type="checkbox">
                </td>
                <td class="bigTableCell">
                    <a><img src="${urlPrefix}/resources/icon/deleteBtn24.png"/></a>
                </td>
                <td class="bigTableCell">
                    <a><img src="${urlPrefix}/resources/icon/leader32.png"/></a>
                </td>
            </tr>
        </table>

    </div>
</div>


</body>
</html>
