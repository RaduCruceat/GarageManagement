package com.parking.parkinglot.ejb;
import com.parking.parkinglot.common.CarDto;

import com.parking.parkinglot.Cars;
import com.parking.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;
    public List<CarDto> copyCarsToDto(List<Car> cars)
    {
        List<CarDto> carsDto=new ArrayList<>();
        cars.forEach(car ->
        {
            CarDto carDto=new CarDto(car.getId(),car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
            carsDto.add(carDto);
        });
        return carsDto;
    }
    public List<CarDto> findAllCars()
    {
       LOG.info("findAllCars");
    try
        {
            TypedQuery<Car> typedQuery=entityManager.createQuery("SELECT C FROM Car c", Car.class);
            List<Car> cars=typedQuery.getResultList();
            return copyCarsToDto(cars);

        }
    catch(Exception ex)
    {
        throw new EJBException(ex);
    }
    }
}
