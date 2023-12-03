<%--
  Created by IntelliJ IDEA.
  User: radu
  Date: 11/10/2023
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Parking Lot</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href=${pageContext.request.contextPath}/about.jsp>about</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href=${pageContext.request.contextPath}/Cars>Cars</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href=${pageContext.request.contextPath}/Users>Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled">Disabled</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                    </li>
                </ul>
                <form class="d-flex" role="search">


                </form>
            </div>
        </div>
    </nav>
</header>

</body>
</html>
