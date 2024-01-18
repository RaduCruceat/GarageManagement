<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit User">
    <h1>Edit User</h1>
    <form class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/EditUser">
        <input type="hidden" name="user_id" value="${user.id}"/>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="" value="${user.username}" required>
                <div class="invalid-feedback">
                    Username is required.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="" value="${user.email}" required>
                <div class="invalid-feedback">
                    Email is required.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="password">New Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter new password">
                <div class="invalid-feedback">
                    Password is required for change.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="user_groups">Groups</label>
                <select class="custom-select d-block w-100" multiple id="user_groups" name="user_groups">
                    <c:forEach var="group" items="${allGroups}">
                        <option value="${group}" ${userGroups.contains(group) ? 'selected' : ''}>${group}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <button class="btn btn-primary btn-lg btn-block" type="submit">Update</button>
    </form>
</t:pageTemplate>