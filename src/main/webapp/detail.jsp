<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Notes</title>
</head>
<body>
<%
    Note note = (Note) request.getAttribute("note");
    if (note != null) {
        Integer id = note.getId();
        String title = note.getTitle();
        String label = note.getLabel();
        String text = "";
        Byte[] image = null;
        byte[] imgbytes = null;
        String imgStr = "";

        LocalDateTime createdAt = note.getCreatedAt();
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
            }
        }
        String formatCreatedAt = "";
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formatCreatedAt = createdAt.format(formatter);
        }

%>
<p>id: <%=id%>
</p>

<p>title: <%=title%>
</p>
<p>label: <%=label%>
</p>
<% if (text != "") {%>
<p>text: <%=text%>
</p>
<%}%>
<%if (image != null) {%>
<%--<p>image: <%=imgStr%>--%>
<img alt="img" src="data:image/jpeg;base64, <%=imgStr%>"/>
</p>
<%}%>
<p>created: <%=formatCreatedAt%>
</p>
<%}%>
</body>
</html>
