<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<c:set var="urlPrefix" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}"/>
<!-- todo добавить протокол к urlPrefix. Отсутствие протокола приводит к ошибке при обращении через https heroku -->

<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="${urlPrefix}/resources/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${urlPrefix}/resources/css/mobileStyle.css" rel="stylesheet" type="text/css">

<script>
    const urlPrefix = "${urlPrefix}";
    const IS_MOBILE = true;
</script>

<script src="${urlPrefix}/resources/js/jquery-3.3.1.min.js"></script>
<script src="${urlPrefix}/resources/js/util.js"></script>
<script src="${urlPrefix}/resources/js/js.cookie.js"></script>
<script src="${urlPrefix}/resources/js/date.format.js"></script>
<script src="${urlPrefix}/resources/js/bootstrap.min.js"></script>
<script src="${urlPrefix}/resources/js/bootbox.min.js"></script>