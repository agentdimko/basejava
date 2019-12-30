<%@ page import="ru.javawebinar.basejava.model.Institution" %>
<%@ page import="ru.javawebinar.basejava.model.InstitutionSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <c:if test="${sectionEntry.getValue() != null}">
            <h3><%=sectionEntry.getKey().getTitle()%>
            </h3>
            <c:choose>
                <c:when test="${sectionEntry.getValue().getClass().getSimpleName().equals('ListSection')}">
                    <ul>
                        <%
                            List<String> list = ((ListSection) sectionEntry.getValue()).getItems();
                            for (String s : list) {
                        %>
                        <%="<li>" + s + "</li>"%>
                        <%}%>
                    </ul>
                </c:when>
                <c:when test="${sectionEntry.getValue().getClass().getSimpleName().equals('TextSection')}">
                    <%=sectionEntry.getValue()%><br/>
                </c:when>
                <c:when test="${sectionEntry.getKey().name().equals(\"EXPERIENCE\") || sectionEntry.getKey().name().equals('EDUCATION')}">
                    <%
                        List<Institution> institutions = ((InstitutionSection) sectionEntry.getValue()).getItems();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                        for (Institution institution : institutions) {
                    %>
                    <%--Вывод названия организации--%>
                    <%=institution.getHomePage().toString() + "<br>"%>
                    <%
                        List<Institution.Position> positions = institution.getPositions();
                        for (Institution.Position position : positions) {
                    %>
                    <%--Вывод даты--%>
                    <%=formatter.format(position.getStartDate())%>
                    <%if (!position.getEndDate().isEqual(DateUtil.NOW)) {%>
                    <%="&nbsp;&ndash;&nbsp;" + formatter.format(position.getEndDate()) + "&nbsp;&nbsp;"%>
                    <%} else {%>
                    <%="&nbsp;&ndash;&nbsp;Сейчас"%>
                    <%}%>&nbsp;
                    <%--Вывод позиции--%>
                    <%="<strong>" + position.getTitle() + "</strong>" + "<br>"%>
                    <%--Вывод контента--%>
                    <%="&nbsp;&nbsp;&nbsp;&nbsp;" + "<strong>" + position.getDescription() +
                            "</strong><br>"%>
                    <%
                        }
                    %>
                    <%}%>
                </c:when>
            </c:choose>
        </c:if>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>