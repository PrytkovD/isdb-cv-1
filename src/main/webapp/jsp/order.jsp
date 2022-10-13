<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FstFud | Order</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="c">
        <div class="row">
            <div class="col 6">
                <div class="card w-100">
                    <h1 class="tc">Menu</h1>
                    <hr>
                    <jsp:useBean id="dishes" scope="session" type="java.util.List"/>
                    <c:forEach items="${dishes}" var="dish">
                        <form action="/order?dish=${dish.name}" method="post">
                            <div class="row">
                                <div class="col 8">
                                    <strong class="large"><c:out value="${dish.name}"/></strong>
                                </div>
                                <div class="col 2">
                                    <span class="large"><c:out value="${dish.price}"/></span>
                                </div>
                                <div class="col 2">
                                    <button class="btn w-100" type="submit"><strong class="large">+</strong></button>
                                </div>
                            </div>
                        </form>
                        <hr>
                    </c:forEach>
                </div>
            </div>
            <div class="col 6">
                <div class="card">
                    <h1 class="tc">Order</h1>
                    <hr>
                    <jsp:useBean id="dataHolder" scope="session" type="ruitis.isdbcv1.util.SessionDataHolder"/>
                    <c:forEach items="${dataHolder.orderInfo}" var="entry">
                        <div class="row">
                            <div class="col 8">
                                <c:out value="${entry.key.name}"/>
                            </div>
                            <div class="col 3">
                                <c:out value="${entry.key.price}"/>
                            </div>
                            <div class="col 1">
                                <c:out value="x${entry.value}"/>
                            </div>
                        </div>
                    </c:forEach>
                    <form action="/order-list" method="post">
                        <button class="btn w-100 primary" type="submit"><strong class="large">Confirm</strong></button>
                    </form>
                </div>
            </div>
            <div class="col 1"></div>
        </div>
    </div>
</body>
</html>
