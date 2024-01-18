package com.parking.parkinglot.servlets.Users;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EditUser.class.getName());

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        UserDto user = userBean.findUserById(userId);
        request.setAttribute("user", user);

        List<String> userGroups = userBean.getAllUserGroups();
        request.setAttribute("allGroups", userGroups);



        List<String> userGroupsForUser = userBean.getUserGroupsByUsername(user.getUsername());
        LOG.info("username gasit:"+user.getUsername());
        request.setAttribute("userGroups", userGroupsForUser);

        LOG.info("leare deja:"+userGroupsForUser.toString());

        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String[] userGroups = request.getParameterValues("user_groups");

        userBean.updateUser(userId, username, email, password, userGroups);

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}