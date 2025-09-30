<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Квест завершён</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5 text-center">
  <div class="card shadow p-5 mb-4">
    <h1 class="card-title mb-3">${question.title}</h1>
    <p class="card-text lead">${question.content}</p>
    <p class="card-text">Ваши очки: <span class="badge bg-success">${score}</span></p>
  </div>

  <form action="${pageContext.request.contextPath}/quest" method="get">
    <input type="hidden" name="questId" value="${quest.id}"/>
    <button type="submit" class="btn btn-primary btn-lg">
      Пройти квест заново
    </button>
  </form>
</div>
</body>
</html>