<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Note" %>
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
          for (Note note : notes)
          {
            String title = note.getTitle();
        %>
        <li>
        <%=title%>
        </li>
        <% } %>
      </ul>
</body>
</html>