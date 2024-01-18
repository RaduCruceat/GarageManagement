package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;
    @Inject
    PasswordBean passwordBean;

    public List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        users.forEach(user ->
        {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
            usersDto.add(userDto);
        });
        return usersDto;
    }

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT C FROM User c", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);

        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }
    public Collection<String> findUsernameByUserIds(Collection<Long> userIds) {
        List<String> usernames =
                entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class)
                        .setParameter("userIds", userIds)
                        .getResultList();
        return usernames;
    }
    public UserDto findUserById(Long userId) {
        LOG.info("findUserById");
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(),user.getEmail(),user.getPassword());
    }
    public void updateUser(Long userId, String username, String email, String password, String[] userGroups) {
        LOG.info("updateUser");
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            if (password != null && !password.isEmpty()) {
                user.setPassword(passwordBean.convertToSha256(password));
            }
            entityManager.merge(user);

            updateGroupsForUser(username, userGroups);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    private void updateGroupsForUser(String username, String[] groups) {
        // Remove existing groups
        entityManager.createQuery("DELETE FROM UserGroup g WHERE g.username = :username")
                .setParameter("username", username)
                .executeUpdate();

        // Add new groups
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }
    public List<String> getUserGroupsByUsername(String username) {
        LOG.info("getUserGroupsByUsername");
        TypedQuery<String> query = entityManager.createQuery(
                        "SELECT ug.userGroup FROM UserGroup ug WHERE ug.username = :username", String.class)
                .setParameter("username", username);
        return query.getResultList();
    }


    public List<String> getAllUserGroups() {
        LOG.info("getAllUserGroups");
        TypedQuery<String> query = entityManager.createQuery("SELECT DISTINCT ug.userGroup FROM UserGroup ug", String.class);
        return query.getResultList();
    }
    public void updateUserGroups(String username, List<String> groups) {
        LOG.info("updateUserGroups for user: " + username);
        // Remove existing groups
        entityManager.createQuery("DELETE FROM UserGroup ug WHERE ug.username = :username")
                .setParameter("username", username)
                .executeUpdate();

        // Assign new groups
        for (String group : groups) {
            UserGroup newUserGroup = new UserGroup();
            newUserGroup.setUsername(username);
            newUserGroup.setUserGroup(group);
            entityManager.persist(newUserGroup);
        }
    }




}
