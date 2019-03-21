<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <%-- Bootstrap --%>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>Sing In!</title>
</head>

<body>
<p></p>
<p></p>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}/signIn">
        <div class="form-group" align="center">
            <div class="col-5">
                <label for="userName">User name</label>
                <input type="text" id="userName" name="userName" class="form-control" align="center">
            </div>
        </div>
        <div class="form-group" align="center">
            <div class="col-5">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" align="center">
            </div>
        </div>
        <div class="form-group" align="center">
            <button type="submit" class="btn btn-primary">Sign In!</button>
        </div>
    </form>
</div>


<%-- Bootstrap --%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
