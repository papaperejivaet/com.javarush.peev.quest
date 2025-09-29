<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>${quest.name}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">${question.title}</h1>
  <p class="lead mb-4">${question.content}</p>

  <!-- Форма для отправки ответа -->
  <form action="${pageContext.request.contextPath}/quest" method="post">
    <input type="hidden" name="questId" value="${quest.id}"/>
    <input type="hidden" name="currentQuestionId" value="${question.id}"/>

    <div class="d-grid gap-3">
      <c:forEach var="answer" items="${question.answers}" varStatus="status">
        <button type="submit" name="answerIndex" value="${status.index}" class="btn btn-primary btn-lg w-100">
            ${answer.content}
        </button>
      </c:forEach>
    </div>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
