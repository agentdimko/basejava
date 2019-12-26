<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <%--<title>Резюме ${resume.fullName}</title>--%>
    <title>Резюме ${resume.fullName.equals("dummy")?"Новое резюме":resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="dummy" value="${resume.fullName}">
        <dt>Имя:</dt>
        <%--<dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>--%>
        <dd><input type="text" name="fullName" size=50
                   value="${resume.fullName.equals("dummy")?"":resume.fullName}"></dd>

        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type.name().equals(\"EXPERIENCE\") || type.name().equals(\"EDUCATION\")}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" readonly name="${type.name()}" size=100
                                   value="${resume.getSection(type)}"></dd>
                    </dl>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=100 value="${resume.getSection(type)}"></dd>
                    </dl>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>