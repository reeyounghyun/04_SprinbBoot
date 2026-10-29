<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>FrontController</title>
</head>
<body>
    <h1>FrontController</h1>
    <p>모든 요청은 FrontControllerServlet을 거쳐 처리됩니다.</p>
    <div>
        <%--링크 주소 문제: index.jsp의 href="/todo/menu"는 context path가 빠져 있습니다. 톰캣에서 앱 경로가 /가 아니면(예: /front-controller) 이 링크를 눌렀을 때 404가 날 수 있습니다.--%>
        <a href="${pageContext.request.contextPath}/todo/create">새 Todo</a>
        <a href="/todo/menu">새 menu</a>

    </div>
</body>
</html>
