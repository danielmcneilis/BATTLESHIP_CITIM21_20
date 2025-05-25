package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;

public interface IUserService {
    User userRegister(String id, String username) throws IOException;

    User login(String id);

    void logout(String id);

    void deleteAccount(String id_user);

    void changeUserName(User user, String actualUsername, String newUsername) throws IOException;

}
