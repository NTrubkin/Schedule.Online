<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 31.01.2018
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Schedule.Online - Вход</title>
    <%@include file="meta.jsp" %>
    <script src="${urlPrefix}/resources/js/login.js"></script>
</head>
<body>
<div id="loginMain">
    <div id="logoBlock">
        <p id="logo" style="color: #63b175">Schedule<br><span id="logoDot">.</span>Online</p>
    </div>
    <p style="color: red">${message}</p>
    <div id="authPanel">
        <div class="authBlock">
            <div id="loginRegButtons">
                <a id="loginTabBtn" href="#" onclick="showLogin()" class="recordName authHeader activeAuthButton">Вход</a>
                <a id="regTabBtn" href="#" onclick="showRegistration()" class="recordName authHeader nonActiveAuthButton">Регистрация</a>
            </div>
            <form id="loginBlock" class="login-form" action="${urlPrefix}/login" method="post">
                <input name="login" class="textbox textboxOnWhite loginTextbox" type="text" placeholder="Email или телефон">
                <input name="password" class="textbox textboxOnWhite loginTextbox" type="password" placeholder="Пароль">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="buttonOnWhite authButton">Войти</button>
            </form>
            <div id="regBlock">
                <input id="loginFld" class="textbox textboxOnWhite loginTextbox" type="text" placeholder="Email или телефон(10 цифр)">
                <input id="firstNameFld" class="textbox textboxOnWhite loginTextbox" type="text" placeholder="Имя">
                <input id="secondNameFld" class="textbox textboxOnWhite loginTextbox" type="text" placeholder="Фамилия">
                <input id="passwordFld" class="textbox textboxOnWhite loginTextbox" type="password" placeholder="Пароль">
                <button id="regBtn" class="buttonOnWhite authButton" onclick="createAccount()">Зарегистрироваться</button>
            </div>
        </div>
        <hr id="loginHr">
        <div id="socialBlock" class="authBlock">
            <a href="https://www.facebook.com/v2.12/dialog/oauth?client_id=2081283898770087&redirect_uri=http://localhost:8080/schedule-online-1.0-SNAPSHOT/oauth2/facebook" id="fbButton" class="buttonOnWhite socialButton" style="display: block">
                <p>Войти, используя Facebook</p>
            </a>
            <!--
            <a id="googleButton" class="buttonOnWhite socialButton" style="display: block">
                <p>Войти, используя Google</p>
            </a>
            -->
            <a href="https://oauth.vk.com/authorize?client_id=6384407&display=page&redirect_uri=http://localhost:8080/schedule-online-1.0-SNAPSHOT/oauth2/vk&response_type=code&v=5.73" id="vkButton" class="buttonOnWhite socialButton" style="display: block">
                <p>Войти, используя VK</p>
            </a>
        </div>
    </div>
</div>
</body>
</html>
