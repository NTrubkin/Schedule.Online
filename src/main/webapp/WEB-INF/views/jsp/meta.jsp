<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<c:set var="urlPrefix" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}"/>
<!-- todo добавить протокол к urlPrefix. Отсутствие протокола приводит к ошибке при обращении через https heroku -->

<style type="text/css">
    @font-face {
        font-family: "Raleway";
        font-weight: 100;
        src: url(${urlPrefix}/resources/font/Raleway-Thin.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 200;
        src: url(${urlPrefix}/resources/font/Raleway-ExtraLight.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 300;
        src: url(${urlPrefix}/resources/font/Raleway-Light.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 400;
        src: url(${urlPrefix}/resources/font/Raleway-Regular.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 500;
        src: url(${urlPrefix}/resources/font/Raleway-Medium.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 600;
        src: url(${urlPrefix}/resources/font/Raleway-SemiBold.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 700;
        src: url(${urlPrefix}/resources/font/Raleway-Bold.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 800;
        src: url(${urlPrefix}/resources/font/Raleway-ExtraBold.ttf) format("truetype");
    }
    @font-face {
        font-family: "Raleway";
        font-weight: 900;
        src: url(${urlPrefix}/resources/font/Raleway-Black.ttf) format("truetype");
    }
</style>

<link href="${urlPrefix}/resources/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${urlPrefix}/resources/css/style.css" rel="stylesheet" type="text/css">

<script>
    const urlPrefix = "${urlPrefix}";
    const IS_MOBILE = false;
</script>

<script src="${urlPrefix}/resources/js/jquery-3.3.1.min.js"></script>
<script src="${urlPrefix}/resources/js/util.js"></script>
<script src="${urlPrefix}/resources/js/js.cookie.js"></script>
<script src="${urlPrefix}/resources/js/date.format.js"></script>
<script src="${urlPrefix}/resources/js/bootstrap.min.js"></script>
<script src="${urlPrefix}/resources/js/bootbox.min.js"></script>