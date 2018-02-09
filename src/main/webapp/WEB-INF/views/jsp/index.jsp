<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 29.01.2018
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Schedule.Online</title>
    <%@include file="meta.jsp" %>
</head>

<body>
<div id="menuPanel">
    <div id="menuContentPanel">
        <div id="logoBlock" class="menuBlock">
            <p>Schedule<br><span style="color: rgb(82, 94, 116); font-size: 64; line-height: 0;">.</span>Online</p>
        </div>
        <div id="accountBlock" class="menuBlock">
            <c:url var="logoutIconUrl" value="" />
            <a id="accountLogout"><img src="${urlPrefix}resources/icon/logout32-4.png"></a>
            <a id="accountName">Трубкин<br>Никита</a>
        </div>
        <div id="groupBlock" class="menuBlock">
            <a>15-АС</a>
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



        <div class="contentBlock dayBlock">
            <div class="daySubblock">
                <p>Среда</p>
            </div>
            <div class="daySubblock">
                <p>14 февраля</p>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <c:url var="editIconUrl" value="/resources/icon/editBtn24.png" />
                <a class="editBtn"><img src="${logoutIconUrl}"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        10:30<br>12:00
                    </p>
                    <p class="headerItem recordName lessonName">Математическое программирование</p>
                    <p class="headerItem recordDetails">1234</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        12:15<br>15:00
                    </p>
                    <p class="headerItem recordName lessonName">Скриптовые языки программирования</p>
                    <p class="headerItem recordDetails">5678</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock  deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
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



        <div class="contentBlock emptyBlock">
            <p>STUB TEXT IN STUB BLOCK. SHOULD BE INVISIBLE</p>
        </div>
        <div class="contentBlock dayBlock">
            <div class="daySubblock">
                <p>Четверг</p>
            </div>
            <div class="daySubblock">
                <p>15 февраля</p>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        10:30<br>12:00
                    </p>
                    <p class="headerItem recordName lessonName">Математическое программирование</p>
                    <p class="headerItem recordDetails">1234</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        12:15<br>15:00
                    </p>
                    <p class="headerItem recordName lessonName">Скриптовые языки программирования</p>
                    <p class="headerItem recordDetails">5678</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock  deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
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



        <div class="contentBlock emptyBlock">
            <p>STUB TEXT IN STUB BLOCK. SHOULD BE INVISIBLE</p>
        </div>
        <div class="contentBlock dayBlock">
            <div class="daySubblock">
                <p>Пятница</p>
            </div>
            <div class="daySubblock">
                <p>16 февраля</p>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        10:30<br>12:00
                    </p>
                    <p class="headerItem recordName lessonName">Математическое программирование</p>
                    <p class="headerItem recordDetails">1234</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
        <div class="contentBlock recordBlock">
            <div class="recordSubblock editLessonSubblock">
                <a class="editBtn"><img src="editBtn24.png"></a>
            </div>
            <div class="recordSubblock bodyRecordSubblock">
                <div class="recordHeader">
                    <p class="headerItem recordDetails">
                        12:15<br>15:00
                    </p>
                    <p class="headerItem recordName lessonName">Скриптовые языки программирования</p>
                    <p class="headerItem recordDetails">5678</p>
                </div>
                <hr class="recordHr">
            </div>
            <div class="recordSubblock  deleteLessonSubblock">
                <a class="deleteBtn"><img src="deleteBtn24.png"/></a>
            </div>
        </div>
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
    </div>
</div>

</body>

</html>
