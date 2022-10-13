<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>FstFud | Order</title>
  <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
      <jsp:useBean id="orders" scope="session" type="java.util.List"/>
  <div class="c">
    <div class="card">
      <h1 class="tc">Orders</h1>
      <strong class="large">Active: <c:out value="${orders.size()}"/></strong>
      <hr>
      <c:forEach items="${orders}" var="order">
        <div><c:out value="${order.id}"/></div>
        <hr>
      </c:forEach>
    </div>
  </div>
</body>
</html>
