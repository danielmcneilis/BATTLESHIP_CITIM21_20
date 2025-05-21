package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;

public interface IUserService {
    User userRegister() throws IOException;
    User login();
    void logout(User user);
    void deleteAccount(User user);
    void changeUserName(User user) throws IOException;

}
