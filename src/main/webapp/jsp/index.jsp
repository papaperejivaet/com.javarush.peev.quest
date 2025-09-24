<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <title>Quests App</title>
</head>
<body>
<c:forEach var="quest" items="${quests}">
    ${quest}
</c:forEach>
</body>
</html>
