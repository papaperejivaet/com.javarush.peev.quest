<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>${quest.name}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</head>
<body>
<!-- Новая кнопка "К выбору квестов" -->


<div class="container mt-5 position-relative">

  <div class="position-absolute top-0 end-1 mt-3 me-3">
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">К выбору квестов</a>
  </div>

  <!-- Кнопка перезапуска квеста -->
  <form action="${pageContext.request.contextPath}/quest" method="get" class="position-absolute top-0 end-0 mt-3 me-3">
    <input type="hidden" name="questId" value="${quest.id}"/>
    <button type="submit" class="btn btn-warning">
      Пройти заново
    </button>
  </form>

  <!-- Отображение очков сверху по центру -->
  <div class="text-center mb-4">
    <h4>Очки: <span class="badge bg-success">${score}</span></h4>
  </div>

  <!-- Основной блок вопроса -->
  <div class="mb-4">
    <h1>${question.title}</h1>
    <p class="lead">${question.content}</p>
  </div>

  <!-- Ответы -->
  <form action="${pageContext.request.contextPath}/quest" method="post">
    <input type="hidden" name="questId" value="${quest.id}"/>
    <input type="hidden" name="currentQuestionId" value="${question.id}"/>
    <div class="d-grid gap-3">
      <c:forEach var="answer" items="${question.answers}" varStatus="status">
        <button type="submit" name="answerIndex" value="${status.index}" class="btn btn-primary btn-lg">
            ${answer.content}
        </button>
      </c:forEach>
    </div>
  </form>
</div>
</body>
</html>
