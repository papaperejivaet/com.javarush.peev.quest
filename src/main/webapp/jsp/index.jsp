<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Quests App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <!-- Приветствие -->
    <div class="text-center mb-4">
        <h1>Добро пожаловать в Quests App!</h1>
        <p class="lead">Это приложение позволяет выбирать интересные квесты и проходить их онлайн.</p>
    </div>

    <!-- Заголовок выбора квеста -->
    <div class="mb-3">
        <h3>Выберите свой квест:</h3>
    </div>

    <!-- Список квестов -->
    <div class="d-flex flex-wrap gap-3">
        <!-- Кликабельные квесты -->
        <c:forEach var="entry" items="${quests}">
            <c:if test="${entry.key != null}">
                <a href="${pageContext.request.contextPath}/quest?questId=${entry.key}"
                   class="btn btn-success btn-lg">
                        ${entry.value}
                </a>
            </c:if>
        </c:forEach>

        <!-- Некликабельная кнопка "В разработке..." -->
        <button class="btn btn-secondary btn-lg" disabled>
            В разработке...
        </button>
    </div>
</div>
</body>
</html>
