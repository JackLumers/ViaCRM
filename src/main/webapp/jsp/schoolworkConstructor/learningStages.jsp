<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%-- Bootstrap --%>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <title>Этапы обучения</title>
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
                <a class="nav-link dropdown-toggle" href="#" id="templatesDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Шаблоны
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/templates">
                        Шаблоны
                    </a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="schoolworksDropdown" role="button"
                   data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Уроки
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/schoolworks">
                        Уроки
                    </a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/schoolworkGroups">
                        Группы уроков
                    </a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="topicsDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Темы
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/topics">
                        Темы
                    </a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/subtopics">
                        Подтемы
                    </a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/realizations">
                        Реализации подтем
                    </a>
                </div>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="globalsDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Глобальные данные
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/specializations">
                        Направления
                    </a>
                    <a class="dropdown-item active"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/learningStages">
                        Этапы обучения
                    </a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/schoolworkConstructor/subtopicForms">
                        Виды подтем
                    </a>
                </div>
            </li>
        </ul>
        <%-- Версия конструктора --%>
        <span class="navbar-text">Конструктор уроков v0.1 </span>
    </div>
</nav>

<div class="container-fluid" style="margin-top: 75px">
    <%-- Добавление нового этапа --%>
    <form method="post" action="${pageContext.request.contextPath}/schoolworkConstructor/learningStages">
        <div class="form-group">
            <div class="col-5">
                <label for="nameForm">Наименование этапа</label>
                <input class="form-control" id="nameForm" name="learningStageNameToSave" type="text"
                       autocomplete="off">
            </div>
        </div>
        <div class="form-group">
            <div class="col-5">
                <label for="specForm">Направление</label>
                <input class="form-control" id="specForm" name="learningStageSpecNameToSave" type="text"
                       autocomplete="off">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Создать этап</button>
    </form>

    <%-- Таблица этапов обучения --%>
    <table class="table table-hover">
        <thead class="thead-dark">
        <tr>
            <th>#</th>
            <th>Этап обучения</th>
            <th>Направление</th>
            <th></th>
        </tr>
        </thead>
        <c:set var="nam" value="0"/>
        <c:forEach items="${learningStagesFromServer}" var="learningStage">
            <tr>
                <td>${num = num + 1}</td>
                <td>${learningStage.name}</td>
                <td>${learningStage.specialization.name}</td>
                <td>
                    <form method="post"
                          action="${pageContext.request.contextPath}/schoolworkConstructor/learningStages">
                        <button type="submit"
                                name="learningStageIdToDelete"
                                value="${learningStage.id}"
                                class="btn btn-danger">
                            Удалить этап
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%-- Autocomplete --%>
<script type="text/javascript">
    <%@include file="/javascript/autocompleteForInput.js"%>
    var specNamesArray = [];
    <jsp:useBean id="specializationsFromServer" scope="request" type="java.util.List"/>
    <c:forEach items="${specializationsFromServer}" var="specialization">
    specNamesArray.push('${specialization.name}');
    </c:forEach>
    autocomplete(document.getElementById("specForm"), specNamesArray);
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