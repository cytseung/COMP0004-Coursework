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
<jsp:include page="/header.jsp"/>
<body>
<% Boolean full = (Boolean) request.getAttribute("full");
    Boolean summary = (Boolean) request.getAttribute("summary");%>
<div class="main">

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
    <div class="list-item-container">
        <div class="list-item-title">
            <a href="<%=href%>">
                <h3>
                    <%=title%>
                </h3>
            </a>

        </div>
        <div class="list-item-datetime-label">
            <%=label%>
            <%=formatCreatedAt%>
        </div>

        <div class="list-item-content">
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
            <div class="list-item-image">
                <img alt="img" src="data:image/jpeg;base64, <%=imgStr%>"/>
            </div>
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
        <div class="list-item-actions">
            <a class="btn btn-primary btn-sm" href="<%=hrefEdit%>">Edit</a>
            <form action="delete/" method="POST">
                <input type="hidden" id="id" name="id" value="<%=id%>">
                <Input class="btn btn-danger btn-sm" type="submit" value="Delete">
            </form>
        </div>
    </div>
    <% } %>
</div>
<div class="index-actions">
    <c:choose>
        <c:when test="${pageContext.request.queryString.contains('mode')}">
            <c:if test="${!pageContext.request.queryString.contains('sort')}">
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString}&sort=created">Sort by Created</a>
                </div>
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString}&sort=title">Sort by Title</a>
                </div>
            </c:if>
            <c:if test="${pageContext.request.queryString.contains('sort')}">
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString.replace("title", "created")}">Sort by Created</a>
                </div>
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString.replace("created", "title")}">Sort by Title</a>
                </div>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary action-item">
                <a href="?sort=created">Sort by Created</a>
            </div>
            <div class="btn btn-primary action-item">
                <a href="?sort=title">Sort by Title</a>
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${pageContext.request.queryString.contains('sort')}">
            <c:if test="${!pageContext.request.queryString.contains('mode')}">
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString}&mode=summary">Summary</a>
                </div>
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString}&mode=full">Full Note</a>
                </div>
            </c:if>
            <c:if test="${pageContext.request.queryString.contains('mode')}">
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString.replace("full", "summary")}">Summary</a>
                </div>
                <div class="btn btn-primary action-item">
                    <a href="?${pageContext.request.queryString.replace("summary","full")}">Full Note</a>
                </div>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary action-item">
                <a href="?mode=summary">Summary</a>
            </div>
            <div class="btn btn-primary action-item">
                <a href="?mode=full">Full Note</a>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="btn btn-primary action-item">
        <a href="${pageContext.request.requestURL.substring(0,pageContext.request.requestURL.length()-9)}">Back to
            default</a>
    </div>
</div>
<div class="index-actions">
    <div class="btn btn-primary action-item"><a href="create">Create</a></div>
    <div class="btn btn-primary action-item"><a href="search">Search</a></div>
</div>

</body>

</html>