<%--
  Created by IntelliJ IDEA.
  User: ceotungbeo
  Date: 29/7/24
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</head>
<body>

<div class="container" style="height: 500px">
    <div class="row">
        <a href="/book">Book Manager</a>
    </div>
    <div class="row">
        <div class="col-6">
            <a href="${pageContext.request.contextPath}/category/create" class="btn btn-primary">Add Category</a>
        </div>

        <div class="col-6">
            <form method="get" action="/category/search">
                <input type="text" class="search" name="input"/>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
    </div>


    <div class="container col-12">
        <div class="row">
            <table class="table  caption-top table-hover table-bordered" to>
                <thead>
                <caption><h1>List of Categories</h1></caption>
                <tr>
                    <td>ID</td>
                    <td>Name</td>
                    <td>Action</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${categoryList}">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td><a href="/category/edit?id=${item.id}" class="btn btn-success">Edit</a> |
                            <a href="/category/delete?id=${item.id}" class="btn btn-danger">Delete</a>
                                <%--                    <button class="btn btn-danger" data-toggle="modal" data-target="#myModal">Delete</button>--%>
                        </td>
                        <div class="modal fade" id="myModal">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <!-- Modal Header -->
                                    <div class="modal-header">
                                        <h4 class="modal-title">Do you want to delete? ID = ${item.id}</h4>
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>

                                    <!-- Modal Body -->
                                    <div class="modal-body">
                                        <a href="category/delete?id=${item.id}" class="btn btn-danger">OK</a>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
    <div class="container row text-center col-12" style="position: fixed; bottom: 5px;">
        <nav aria-label="Page navigation example">
            <ul class="pagination" style="justify-content: center">
                <c:if test="${!firstTab}">
                    <li class="page-item"><a class="page-link"
                                             href="/category/search?page=${currentPage-1}&size=10">
                        Previous</a></li>
                </c:if>

                <c:forEach begin="${beginPage}" end="${endPage}" var="page">
                    <li class="page-item ${currentPage == page ? 'active' : ''}">
                        <a class="page-link"
                           href="/category/search?page=${page}&size=10">${page}</a>
                    </li>
                </c:forEach>
                <c:if test="${!lastTab}">
                    <li class="page-item">
                        <a class="page-link"
                           href="/category/search?page=${currentPage+1}&size=10">
                            Next</a></li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
