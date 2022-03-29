<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.net.URL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Notes</title>
</head>
<body>
<%
    Note note = (Note) request.getAttribute("note");
    if (note != null) {
        String id = note.getId();
        String title = note.getTitle();
        String label = note.getLabel();
        String text = "";
        Byte[] image = null;
        byte[] imgbytes = null;
        String imgStr = "";
        URL url = null;

        LocalDateTime createdAt = note.getCreatedAt();
        LocalDateTime updatedAt = note.getUpdatedAt();
//        System.out.println(note);
//        System.out.println(note.getContent());
        note.getContent();
        Map<String, Object> content = (Map<String, Object>) note.getContent();
        for (Map.Entry entry : content.entrySet()) {
            if (entry.getKey() == "text") {
                text = (String) entry.getValue();
            } else if (entry.getKey() == "image") {
                System.out.println("here");
                image = (Byte[]) entry.getValue();
                imgbytes = new byte[image.length];
                int i = 0;
                for (Byte b : image) {
                    imgbytes[i++] = b.byteValue();
                }
                imgStr = Base64.getEncoder().encodeToString(imgbytes);
            }else if (entry.getKey() == "url"){
                url = (URL) entry.getValue();
            }
        }
        String formatCreatedAt = "";
        String formatUpdatedAt = "";
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formatCreatedAt = createdAt.format(formatter);
        }
        if (updatedAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formatUpdatedAt = updatedAt.format(formatter);
        }

%>
<p>Id: <%=id%>
</p>

<p>Title: <%=title%>
</p>
<p>Label: <%=label%>
</p>
<% if (text != "") {%>
<p>Text: <%=text%>
</p>
<%}%>
<%if (image != null) {%>
<%--<p>image: <%=imgStr%>--%>
<img alt="img" src="data:image/jpeg;base64, <%=imgStr%>"/>
</p>
<%}%>
<% if (url != null) {%>
<p>Url: <%=url%>
</p>
<%}%>
<p>Created: <%=formatCreatedAt%>
</p>
<% if (formatUpdatedAt != ""){ %>
<p>Updated: <%=formatUpdatedAt%>
</p>
<%}%>
<%}%>
</body>
</html>
