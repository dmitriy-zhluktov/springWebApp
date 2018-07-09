<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" />
    <title>Admin part</title>
</head>
<body>
<div id="container">
    <div id="header">
        <div id="message-box">
            <c:if test="${not empty sessionScope.message}">
                <span class="info-message"><c:out value="${sessionScope.message}"/></span>
                <c:remove var="message" scope="session" />
            </c:if>

            <c:if test="${not empty sessionScope.error}">
                <span class="error-message"><c:out value="${sessionScope.error}"/></span>
                <c:remove var="error" scope="session" />
            </c:if>
        </div>
    </div>

    <div id="content">
        <div class="login-box">
            <span class="login-title">Какой ты чувствительный мальчик, Томми</span>

            <a href="<spring:url value="singout"/>" class="register-link">Выход</a>
        </div>
    </div>

    <div id="footer">
        <p>Попячтесь!</p>
    </div>
</div>
</body>
</html>