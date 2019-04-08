<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Bootstrap --%>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>Registered Users</title>

    <title>Добавление занятия</title>
</head>
<body>
<%-- Навигационная панель --%>
<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">ViaCRM</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="studentsDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Ученики
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/students">Список учеников</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/students/add">Добавить нового
                        ученика</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="teachersDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Преподаватели
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/teachers">Список
                        преподавателей</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/teachers/add">Добавить нового
                        преподавателя</a>
                </div>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="datedLessonsDropdown" role="button"
                   data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Занятия
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/datedLessons">
                        Список занятий
                    </a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/adminsPart/datedLessons/add">
                        Назначить занятие
                    </a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top: 75px">
    <%--
    *   form используется для передачи параметров запроса через клиентскую форму
    *   method - какой метод запроса будет использован
    *   action - на какой URL будет послан запрос
    --%>
    <form method="post" autocomplete="off" action="${pageContext.request.contextPath}/adminsPart/datedLessons/add">
        <div class="form-group">
            <div class="col-5">
                <label for="studentFullName">Ученик</label>
                <input class="form-control" type="text" id="studentFullName" name="studentFullName">
            </div>
        </div>
        <div class="form-group">
            <div class="col-5">
                <label for="teacherFullName">Преподаватель</label>
                <input class="form-control" type="text" id="teacherFullName" name="teacherFullName">
            </div>
        </div>
        <div class="form-group">
            <div class="col-5">
                <label for="datedLessonDate">Дата занятия</label>
                <input class="form-control" type="datetime-local" id="datedLessonDate" name="datedLessonDate">
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
</div>

<%-- Autocomplete --%>
<script type="text/javascript">
    <%@include file="/javascript/autocompleteForInput.js"%>
    var studentFullNamesArray = [];
    var teacherFullNamesArray = [];
    <jsp:useBean id="teacherFullNames" scope="request" type="java.util.List"/>
    <c:forEach items="${teacherFullNames}" var="fullName">
    teacherFullNamesArray.push('${fullName}');
    </c:forEach>
    <jsp:useBean id="studentFullNames" scope="request" type="java.util.List"/>
    <c:forEach items="${studentFullNames}" var="fullName">
    studentFullNamesArray.push('${fullName}');
    </c:forEach>
    autocomplete(document.getElementById("teacherFullName"), teacherFullNamesArray);
    autocomplete(document.getElementById("studentFullName"), studentFullNamesArray);
</script>


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
