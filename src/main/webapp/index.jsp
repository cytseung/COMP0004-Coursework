<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.net.URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Notes</title>
</head>
<body>
<% Boolean full = (Boolean) request.getAttribute("full");
    Boolean summary = (Boolean) request.getAttribute("summary");%>
<ul>
    <%
        List<Note> notes = (List<Note>) request.getAttribute("notes");
        for (Note note : notes) {
            String id = note.getId();
            String title = note.getTitle();
            String label = note.getLabel();
            LocalDateTime createdAt = note.getCreatedAt();
            String formatCreatedAt = "";
            if (createdAt != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                formatCreatedAt = createdAt.format(formatter);

            }

            String href = String.format("/note/%s", id);
            String hrefEdit = String.format("/edit/%s", id);


    %>
    <li>
        <a href="<%=href%>">
            <%=title%>
            <%=label%>
            <%=formatCreatedAt%>
        </a>
        <div>
            <% if (full != null && full == true) {
                Map<String, Object> content = note.getContent();
                for (Map.Entry entry : content.entrySet()) {
                    if (entry.getKey() == "text") {
                        String text = (String) entry.getValue();%>
            <p><%=text%>
            </p>
            <%
            } else if (entry.getKey() == "image") {
                Byte[] image = (Byte[]) entry.getValue();
                byte[] imgbytes = new byte[image.length];
                int i = 0;
                for (Byte b : image) {
                    imgbytes[i++] = b.byteValue();
                }
                String imgStr = Base64.getEncoder().encodeToString(imgbytes); %>
            <img alt="img" src="data:image/jpeg;base64, <%=imgStr%>"/>
            <%
            } else if (entry.getKey() == "url") {
                URL url = (URL) entry.getValue();
            %>
            <p><a href="<%=url%>"><%=url%>
            </a></p>
            <%
                    }
                }
            } else if (summary != null && summary) {
                Map<String, Object> content = note.getContent();
                for (Map.Entry entry : content.entrySet()) {
                    if (entry.getKey() == "text") {
                        String text = (String) entry.getValue();
                        if (text.length() > 20)
                            text = text.substring(20) + "...";
            %>
            <span><%=text%></span>
            <% } else if (entry.getKey() == "url") {
                URL url = (URL) entry.getValue();%>
            <span><a href="<%=url%>"><%=url%></a></span>
            <%
                        }
                    }
                }
            %>
        </div>
        <a href="<%=hrefEdit%>">Edit</a>
        <form action="delete/" method="POST">
            <input type="hidden" id="id" name="id" value="<%=id%>">
            <Input type="submit" value="Delete">
        </form>
    </li>
    <% } %>
</ul>
<c:choose>
    <c:when test="${pageContext.request.queryString.contains('mode')}">
        <c:if test="${!pageContext.request.queryString.contains('sort')}">
            <a href="?${pageContext.request.queryString}&sort=created">Sort by Created</a>
            <a href="?${pageContext.request.queryString}&sort=title">Sort by Title</a>
        </c:if>
        <c:if test="${pageContext.request.queryString.contains('sort')}">
            <a href="?${pageContext.request.queryString.replace("title", "created")}">Sort by Created</a>
            <a href="?${pageContext.request.queryString.replace("created", "title")}">Sort by Title</a>
        </c:if>
    </c:when>
    <c:otherwise>
        <a href="?sort=created">Sort by Created</a>
        <a href="?sort=title">Sort by Title</a>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${pageContext.request.queryString.contains('sort')}">
        <c:if test="${!pageContext.request.queryString.contains('mode')}">
            <a href="?${pageContext.request.queryString}&mode=summary">Summary</a>
            <a href="?${pageContext.request.queryString}&mode=full">Full Note</a>
        </c:if>
        <c:if test="${pageContext.request.queryString.contains('mode')}">
            <a href="?${pageContext.request.queryString.replace("full", "summary")}">Summary</a>
            <a href="?${pageContext.request.queryString.replace("summary","full")}">Full Note</a>
        </c:if>
    </c:when>
    <c:otherwise>
        <a href="?mode=summary">Summary</a>
        <a href="?mode=full">Full Note</a>
    </c:otherwise>
</c:choose>
<a href="${pageContext.request.requestURL.substring(0,pageContext.request.requestURL.length()-9)}">Back to default</a>

<div><span><a href="create">Create</a></span>
    <span><a href="search">Search</a></span></div>

</body>

</html>