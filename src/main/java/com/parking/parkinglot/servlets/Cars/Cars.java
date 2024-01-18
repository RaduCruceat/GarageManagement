package com.parking.parkinglot.servlets.Cars;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.ejb.CarsBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DeclareRoles({"READ_CARS", "WRITE_CARS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"WRITE_CARS"})})
@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {
    @Inject
    CarsBean carsBean;
    private static final int TOTAL_PARKING_SPOTS = 10; // Total parking spots available

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<CarDto> cars= carsBean.findAllCars();
        request.setAttribute("cars",cars);

        int numberOfOccupiedSpots = cars.size();
        int numberOfFreeParkingSpots = TOTAL_PARKING_SPOTS - numberOfOccupiedSpots;

        request.setAttribute("numberOfFreeParkingSpots", numberOfFreeParkingSpots);
        request.getRequestDispatcher("/WEB-INF/pages/cars/Cars.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String[] carIdsAsString = request.getParameterValues("car_ids");
        if( carIdsAsString != null)
        {
            List<Long> carIds = new ArrayList<>();
            for ( String carIdAsString : carIdsAsString)
            {
                carIds.add(Long.parseLong(carIdAsString));
            }
            carsBean.deleteCarByIds(carIds);
        }
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}