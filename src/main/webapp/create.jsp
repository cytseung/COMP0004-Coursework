<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Notes</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="create-edit-page">
    <form action="create" method="post" enctype='multipart/form-data'>
        <p><label for="title">Title:</label>
            <input id="title" name="title" type="text"/>
        </p>
        <p>
            <label for="text">Label:</label>
            <input id="label" name="label" type="label"/>
        </p>
        <p>
            <label for="text">Text:</label>
            <input id="text" name="text" type="text"/>
        </p>
        <p>
            <label for="url">Url:</label>
            <input id="url" name="url" type="url"/>
        </p>
        <p>
            <label for="image">Image:</label>
            <input id="image" name="image" type="file"/>
        </p>
        <p>
            <input class="btn btn-primary btn-small" type="submit" value="submit"/>
        </p>
    </form>
</div>
<div><a class="btn btn-primary btn-small" href="/">Back to Homepage</a></div>
</body>

</html>