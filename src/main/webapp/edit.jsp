<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notes</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="create-edit-page">
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
            note.getContent();
            Map<String, Object> content = (Map<String, Object>) note.getContent();
            for (Map.Entry entry : content.entrySet()) {
                if (entry.getKey() == "text") {
                    text = (String) entry.getValue();
                } else if (entry.getKey() == "image") {
                    image = (Byte[]) entry.getValue();
                    imgbytes = new byte[image.length];
                    int i = 0;
                    for (Byte b : image) {
                        imgbytes[i++] = b.byteValue();
                    }
                    imgStr = Base64.getEncoder().encodeToString(imgbytes);
                } else if (entry.getKey() == "url") {
                    url = (URL) entry.getValue();
                }
            }
            String formatCreatedAt = "";
            if (createdAt != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                formatCreatedAt = createdAt.format(formatter);
            }

    %>
    <form action="<%=id%>" method="post" enctype='multipart/form-data'>
        <input type="hidden" id="id" name="id" value="<%=id%>">
        <p>
            <label for="title">Title:</label>
            <input id="title" name="title" type="text" value="<%=title%>"/>
        </p>
        <p>
            <label for="label">Label:</label>
            <input id="label" name="label" type="label" value="<%=label%>"/>
        </p>
        <p>
            <label for="text">Text:</label>
            <input id="text" name="text" type="text" value="<%=text%>"/>
        </p>
        <p>
            <label for="image">Image:</label>
            <%if (image != null) {%>
            <%--<p>image: <%=imgStr%>--%>
            <img alt="img" src="data:image/jpeg;base64, <%=imgStr%>"/>
            <input id="hasOriginal" name="hasOriginal" type="hidden" value="true"/>
            <%} else {%>
            <input id="hasOriginal" name="hasOriginal" type="hidden" value="false"/>
            <%}%>
            <input id="image" name="image" type="file"/>

        </p>
        <p>
            <label for="url">Url:</label>
            <% if (url != null) {%>

            <input id="url" name="url" type="url" value="<%=url%>"/>

            <%} else {%>
            <input id="url" name="url" type="url" value=""/>
            <%}%>
        </p>
        <input class="btn btn-primary btn-small" type="submit" value="submit"/>
    </form>
    <%}%>
</div>
<div><a class="btn btn-primary btn-small" href="/">Back to Homepage</a></div>
</body>

</html>
