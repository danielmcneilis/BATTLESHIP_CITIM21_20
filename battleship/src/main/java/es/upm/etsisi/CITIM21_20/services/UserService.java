package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.fis.model.IPuntuacion;
import servidor.ExternalLDAP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UserService implements  IUserService{

    private IUserRepository userList;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, ISessonRepository sessionList) {
        this.userList = userList;
        this.sessionList = sessionList;
    }

    @Override
    public User userRegister() throws IOException {
        String id = ExternalLDAP.LoginLDAP(); // unico para cada usuario
        Scanner scanner = new Scanner(System.in);
        String username;
        User createdUser;

        if (id == null) {
            throw new RuntimeException("ERROR IN LDAP LOGIN");
        }

        if (userList.getUser(id) != null) {
            throw new RuntimeException("USER ALREADY REGISTERED");
        }

        do {
            System.out.println("Introduce un nombre de usuario");
            username = scanner.nextLine();
            if (userList.getUserByUsername(username) != null) {
                System.out.println("USERNAME ALREADY EXISTS, TRY ANOTHER ONE :) ");
            }
            createdUser = userList.createUser(username, id);
        } while (createdUser == null);

        sessionList.createSession(createdUser, id);
        createdUser.setAdmin(false);
        return createdUser;
    }

    @Override
    public User login() {
        String id = ExternalLDAP.LoginLDAP();

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
    public void logout(User user) {
        Session userSession = this.sessionList.getSession(user.getId());
        if (userSession == null) {
            System.out.println("USER NOT FOUND");
        }
        if (userSession.logout()) {
            System.out.println("LOGOUT SUCCESSFUL");
        } else {
            System.out.println("LOGOUT FAILED");
        }
    }

    @Override
    public void deleteAccount(User user) {
        String id = ExternalLDAP.LoginLDAP();
        if (user.getId().equals(id)) {
            this.userList.deleteUser(user.getId());
            this.sessionList.deleteSession(user.getId());
        } else {
            throw new RuntimeException("ERROR LOGIN LDAP");
        }
    }

    @Override
    public void changeUserName(User user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String username;
        boolean correcto = isSure(user.getUsername());
        if (correcto) {
            do {
                System.out.println("Introduce un nuevo nombre de usuario");
                username = scanner.nextLine();
                if (userList.getUserByUsername(username) != null) {
                    System.out.println("CHANGE NAME NOT POSIBLE");
                }
                user.setUsername(username);
            } while (!user.isValidUserName());
        }
    }

    private boolean isSure(String actualUsername) {
        Scanner scanner = new Scanner(System.in);
        String username;
        System.out.println("Para cambiar el nombre tienes que introducir tu nombre actual ");
        do {
            System.out.print("Introduce tu nombre de usuario actual: ");
            username = scanner.nextLine();
        } while (!username.equals(actualUsername));

        return true;
    }
}
