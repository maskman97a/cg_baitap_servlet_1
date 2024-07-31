<%--
  Created by IntelliJ IDEA.
  User: ceotungbeo
  Date: 29/7/24
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form class="form-horizontal" action="${pageContext.request.contextPath}/book/${currentFunction}" method="POST">
        <h1><c:out value="${labelAction}"></c:out></h1>
        <div class="form-group" hidden="true">
            <label>
                <input type="text" class="form-control" placeholder="id" name="id" value="${book.id}"/>
            </label>
        </div>
        <div class="form-group">
            <label>
                <input type="text" class="form-control" placeholder="Name" name="name" value="${book.name}"/>
            </label>
        </div>
        <div class="form-group">
            <label>
                <input type="text" class="form-control" placeholder="Description" name="description"
                       value="${book.description}"/>
            </label>
        </div>
        <div class="form-group">
            <label>
                <input type="number" class="form-control" placeholder="Price" name="price" value="${book.price}"/>
            </label>
        </div>
        <div class="form-group">
            <select class="form-control" name="categoryId">
                <c:forEach var="item" items="${listCategories}">
                    <option <c:out value="${book.categoryId == item.id ? 'selected': ''}"/>
                            value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary" value="${btnActionValue}"/>
            <a href="<c:url value="${pageContext.request.contextPath}/book"/>" class="btn btn-default">Back</a>
        </div>
    </form>
</div>
</body>
</html>
