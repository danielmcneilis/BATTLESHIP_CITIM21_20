package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import servidor.ExternalLDAP;

import java.io.IOException;

public class UserService {

    private IUserRepository userList;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, ISessonRepository sessionList) {
        this.userList = userList;
        this.sessionList = sessionList;
    }

    public void userRegister(String username, String email) throws IOException {
        String id = ExternalLDAP.LoginLDAP();
        if (id == null) {
            throw new RuntimeException("ERROR LDAP");
        }

        if (userList.getUser(id) != null) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (userList.getUserByUsername(username)) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (username.length() < 3 || username.length() > 10) {
            throw new RuntimeException("INVALID USERNAME");
        }

        User user = userList.createUser(username, id);
        sessionList.createSession(user, email);
    }

    public boolean login() throws IOException {
        String id = ExternalLDAP.LoginLDAP();
        if (id == null) {
            throw new RuntimeException("INVALID USERNAME");
        }

        User user = userList.getUser(id);
        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }

        Session session = sessionList.getSession(id);
        session.login();
        //Ir a la pantalla principal
        return true;
    }

    public boolean logout(User user) {
        Session userSession = this.sessionList.getSession(user.getId());
        userSession.logout();
        //Ir a la pantalla principal
        return true;
    }

    public boolean DeleteAccount(User user) {
        this.userList.deleteUser(user.getId());
        this.sessionList.deleteSession(user.getId());
        //Ir a la pantalla principal
        return true;
    }

    public boolean changeUserName(User user, String newUserName) throws IOException {
        if (!user.isValidUserName()) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (userList.getUserByUsername(newUserName)) {
            throw new RuntimeException("INVALID USERNAME");
        }

        user.setUsername(newUserName);
        return true;
    }


}
