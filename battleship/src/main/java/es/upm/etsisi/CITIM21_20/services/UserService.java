package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import servidor.ExternalLDAP;

import java.io.IOException;

public class UserService implements IUserService {

    private IUserRepository userList;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, ISessonRepository sessionList) {
        this.userList = userList;
        this.sessionList = sessionList;
    }

    @Override
    public User userRegister(String id, String username) throws IOException {
        User createdUser;

        if (id == null) {
            throw new RuntimeException("ERROR IN LDAP LOGIN");
        }
        if (userList.getUser(id) != null) {
            throw new RuntimeException("USER ALREADY REGISTERED");
        }

        if (userList.getUserByUsername(username) != null) {
            throw new RuntimeException("USERNAME ALREADY EXISTS, TRY ANOTHER ONE :) ");
        }

        createdUser = userList.createUser(username, id);
        if (createdUser == null) {
            throw new RuntimeException("USER NOT VALID");
        }

        sessionList.createSession(createdUser, id);
        createdUser.setAdmin(false);
        return createdUser;
    }

    @Override
    public User login(String id) {
        if (id == null) {
            throw new RuntimeException("ERROR IN LDAP LOGIN");
        }

        User user = userList.getUser(id);
        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }

        Session session = sessionList.getSession(id);
        session.login();
        return user;
    }

    @Override
    public void logout(String id) {
        Session userSession = this.sessionList.getSession(id);
        if (userSession == null) {
            throw new RuntimeException("USER NOT FOUND");
        } else {
            if (!userSession.logout()) {
                throw new RuntimeException("LOGOUT FAILED");
            }
        }
        userSession.logout();
        sessionList.saveSesions();
    }

    @Override
    public void deleteAccount(String id_user) {
        String id = ExternalLDAP.LoginLDAP();
        if (id_user.equals(id)) {
            this.userList.deleteUser(id_user);
            this.sessionList.deleteSession(id_user);
        } else {
            throw new RuntimeException("ERROR LOGIN LDAP");
        }
        sessionList.saveSesions();
    }

    @Override
    public void changeUserName(User user, String actualUsername, String newUsername) throws IOException {

        if (!isSure(user, actualUsername)) {
            throw new RuntimeException("IS NOT YOUR ACTUAL USERNAME");
        }

        if (userList.getUserByUsername(newUsername) != null) {
            System.out.println("CHANGE NAME NOT POSIBLE");
        }

        user.setUsername(newUsername);
        if (!user.isValidUserName()) {
            user.setUsername(actualUsername);
            throw new RuntimeException("INVALID USERNAME");
        }
        userList.saveUsers();
    }

    private boolean isSure(User user, String actualUsername) {
        return user.getNombre().equals(actualUsername);
    }
}
