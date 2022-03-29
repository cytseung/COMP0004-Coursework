<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Notes</title>
</head>
<body>
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
            <%=id%>
            <%=title%>
            <%=label%>
            <%=formatCreatedAt%>
        </a>
        <a href="<%=hrefEdit%>">Edit</a>
        <a>Delete</a>
    </li>
    <% } %>
</ul>
</body>
</html>