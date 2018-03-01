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
    <title>Schedule.Online - Занятие</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/lesson.js"></script>
    <script>
        if(window.mobilecheck()) {
            var lessonDTO = JSON.parse('${lessonDTO}');
            window.location.href = urlPrefix + '/lesson?id=' + lessonDTO.id + '&m=true';
        }
    </script>
</head>
<body>
<%@include file="header.jsp"%>

<div id="mainPanel">
    <div id="recordsWithDividers">
        <p class="recordName">Занятие</p>
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
                    <p>Аудитория</p>
                </td>
                <td class="settingsCell">
                    <input id="roomFld" class="textbox textboxOnWhite" type="text">
                </td>
            </tr>
            <tr class="settingsRow">
                <td class="settingsCell">
                    <p>Преподаватель</p>
                </td>
                <td class="settingsCell">
                    <input id="teacherFld" class="textbox textboxOnWhite" type="text">
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
                <td class="settingsCell ">
                    <p>Конец</p>
                </td>
                <td class="settingsCell">
                    <input id="endDTPiker" type="datetime-local">
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
        <button onclick="updateLesson()">Сохранить</button>
        <button onclick="deleteLesson()">Удалить</button>
    </div>
</div>
<script>initLessonPage('${lessonDTO}')</script>
</body>

</html>