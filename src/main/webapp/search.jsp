<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 30/3/2022
  Time: 12:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notes</title>
</head>
<body>
<form action="search" method="post">
    <label for="search">Search:</label>
    <input id="search" name="search" type="text">
</form>
<ul>
    <% List<Note> notes = (List<Note>) request.getAttribute("notes");
        if (notes != null) {
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
            <%--            <%=id%>--%>
            <%=title%>
            <%=label%>
            <%=formatCreatedAt%>
        </a>
        <a href="<%=hrefEdit%>">Edit</a>
        <form action="delete/" method="POST">
            <input type="hidden" id="id" name="id" value="<%=id%>">
            <Input type="submit" value="Delete">
        </form>
    </li>
    <% }
    } %>
</ul>
</body>
<div><a href="/">Back to Homepage</a></div>
</html>
