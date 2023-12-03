package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;
    public List<UserDto> copyUsersToDto(List<User> users)
    {
        List<UserDto> usersDto=new ArrayList<>();
        users.forEach(user ->
        {
            UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getPassword());
            usersDto.add(userDto);
        });
        return usersDto;
    }
    public List<UserDto> findAllUsers()
    {
        LOG.info("findAllUsers");
        try
        {
            TypedQuery<User> typedQuery=entityManager.createQuery("SELECT C FROM User c", User.class);
            List<User> users=typedQuery.getResultList();
            return copyUsersToDto(users);

        }
        catch(Exception ex)
        {
            throw new EJBException(ex);
        }
    }

}
