package com.parking.parkinglot;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<CarDto> cars= carsBean.findAllCars();
        request.setAttribute("cars",cars);

        request.getRequestDispatcher("/WEB-INF/pages/Cars.jsp").forward(request,response);
        request.setAttribute("numberOfFreeParkingSpots",10);
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