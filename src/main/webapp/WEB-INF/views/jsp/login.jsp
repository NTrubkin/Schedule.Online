<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 31.01.2018
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<h1>Spring Security - Sign In</h1>

<div style="color: red">${message}</div>
<c:url var="loginUrl" value="/login" />
<form class="login-form" action="${loginUrl}" method="post">
    <label for="username">Username: </label>
    <input id="username" name="login" size="20" maxlength="50" type="text" />

    <label for="password">Password: </label>
    <input id="password" name="password" size="20" maxlength="50" type="password" />

    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>

    <input type="submit" value="Login" />
</form>

<c:url var="signInFacebook" value="/signin/facebook" />
<form action="${signInFacebook}" method="POST">
    <input type="hidden" name="scope" value="public_profile" />
    <input type="submit" value="Login using Facebook"/>
</form>
</body>
</html>
