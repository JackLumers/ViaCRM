<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Bootstrap --%>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <title>Назначенные уроки</title>
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
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/students">
                        Список учеников
                    </a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/students/add">
                        Добавить нового ученика
                    </a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="teachersDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Преподаватели
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/teachers">
                        Список преподавателей
                    </a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/teachers/add">
                        Добавить нового преподавателя
                    </a>
                </div>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="datedLessonsDropdown" role="button"
                   data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Занятия
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/datedLessons">
                        Список занятий
                    </a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/datedLessons/add">
                        Назначить занятие
                    </a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<%-- Таблица занятий --%>
<div class="container-fluid" style="margin-top: 75px">
    <table class="table table-hover">
        <%-- Поисковые формы --%>
        <thead class="thead-dark">
        <tr>
            <th>#</th>
            <th>
                <form>
                    <div class="form-group">
                        <label for="searchByStudentFirstName">Имя ученика</label>
                        <input class="form-control" form="searchForm" type="search" id="searchByStudentFirstName"
                               name="student_first_name" value="${student_first_name}" autocomplete="off">
                    </div>
                </form>
            </th>
            <th>
                <form>
                    <div class="form-group">
                        <label for="searchByStudentLastName">Фамилия ученика</label>
                        <input class="form-control" form="searchForm" type="search" id="searchByStudentLastName"
                               name="student_last_name" value="${student_last_name}" autocomplete="off">
                    </div>
                </form>
            </th>
            <th>
                <form>
                    <div class="form-group">
                        <label for="searchByTeacherFirstName">Имя преподавателя</label>
                        <input class="form-control" form="searchForm" type="search" id="searchByTeacherFirstName"
                               name="student_phone" value="${teacher_first_name}" autocomplete="off">
                    </div>
                </form>
            </th>
            <th>
                <form>
                    <div class="form-group">
                        <label for="searchByTeacherLastName">Фамилия преподавателя</label>
                        <input class="form-control" form="searchForm" type="search" id="searchByTeacherLastName"
                               name="student_city" value="${teacher_last_name}" autocomplete="off">
                    </div>
                </form>
            </th>
            <th>
                <form>
                    <div class="form-group">
                        <label for="searchByLessonDate">Дата занятия</label>
                        <input class="form-control" form="searchForm" type="search" id="searchByLessonDate"
                               name="student_street" value="${dated_lesson_date}" autocomplete="off">
                    </div>
                </form>
            </th>
            <th>
                <%-- Форма поиска --%>
                <div class="container-fluid">
                    <div class="raw">
                        <form method="get" action="${pageContext.request.contextPath}/datedLessons" id="searchForm">
                            <button type="submit" class="btn btn-info">Поиск</button>
                        </form>
                    </div>
                </div>
            </th>
        </tr>
        </thead>
        <%-- Переменная-счетчик num для обозначения номера строки в таблице --%>
        <c:set var="num" value="0"/>
        <%-- items - массив/список, по которому проходится forEach. Он берется из аттрибутов запроса --%>
        <c:forEach items="${datedLessonsFromServer}" var="datedLesson">
            <tr>
                <td>${num = num + 1}</td>
                <td>${datedLesson.student.firstName}</td>
                <td>${datedLesson.student.lastName}</td>
                <td>${datedLesson.teacher.firstName}</td>
                <td>${datedLesson.teacher.lastName}</td>
                <td>${datedLesson.date}</td>
            </tr>
        </c:forEach>
    </table>
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
