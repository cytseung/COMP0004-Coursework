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
<form action="create" method="post" enctype='multipart/form-data'>
    <label for="title">Title:</label>
    <input id="title" name="title" type="text"/>
    <label for="text">Label:</label>
    <input id="label" name="label" type="label"/>
    <label for="text">Text:</label>
    <input id="text" name="text" type="text"/>
    <label for="url">Url:</label>
    <input id="url" name="url" type="url"/>
    <label for="image">Image:</label>
    <input id="image" name="image" type="file"/>
    <label for="others">Other:</label>
    <input id="others" name="other" type="file"/>
    <input type="submit"/>
</form>
<%
    String error = (String) request.getAttribute("error");

%>
<p>
    <% if (error != null)%>
        <%=error%>
</p>
</body>
<div><a href="/">Back to Homepage</a></div>
</html>