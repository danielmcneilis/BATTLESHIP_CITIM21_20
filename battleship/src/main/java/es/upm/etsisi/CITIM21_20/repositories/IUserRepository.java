package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;
import java.util.ArrayList;

public interface IUserRepository {

    User getUser(String id);

    User getUserByUsername(String username);

    User createUser(String username, String id) throws IOException;

    void deleteUser(String id);

    ArrayList<User> valores();
}
