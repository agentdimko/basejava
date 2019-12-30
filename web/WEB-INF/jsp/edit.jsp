<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.InstitutionSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>
        Резюме ${isNew?"Новое резюме":resume.fullName}"
    </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${isNew?"":resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd>
                <input type="text" name="fullName" size=50 required value="${isNew?"":resume.fullName}">
            </dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
        <c:when test="${type.name().equals('OBJECTIVE') || type.name().equals('PERSONAL')}">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=100 value="${resume.getSection(type)}">
                </dd>
            </dl>
        </c:when>
        <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <textarea name="${type.name()}" cols="76" rows="5">${resume.getSection(type)}</textarea>
                        </dd>
                    </dl>
                </c:when>
        <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
        <c:forEach var="institution" items="<%=((InstitutionSection)resume.getSection(type)).getItems())%>">
        <dl>
            <dt>Название организации:</dt>
            <dd><input type="text" name='${type}' size=100 value="${institution.homePage.name}"></dd>
        </dl>
        <dl>
            </c:forEach>
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <textarea name="${type.name()}" cols="76" rows="5">${resume.getSection(type)}</textarea>
                        </dd>
                    </dl>
            </c:when>
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