package com.parking.parkinglot;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UserBean;
import com.parking.parkinglot.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditCar", value = "/EditCar")
public class EditCar extends HttpServlet {

    @Inject
    UserBean usersBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<UserDto> users = null;
        try {
            users = usersBean.findAllUsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("users", users);

        Long carId = Long.parseLong(request.getParameter("id"));
        CarDto car = carsBean.findById(carId);
        request.setAttribute("car", car);

        request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        Long carId = Long.parseLong(request.getParameter("car_id"));

        carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}