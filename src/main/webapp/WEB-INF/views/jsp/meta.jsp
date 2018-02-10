<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<c:set var="urlPrefix" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}"/>

<link href="${urlPrefix}/resources/css/style.css" rel="stylesheet" type="text/css">
<script>const urlPrefix = "${urlPrefix}";</script>
<script src="${urlPrefix}/resources/js/jquery-3.3.1.min.js"></script>
<script src="${urlPrefix}/resources/js/util.js"></script>