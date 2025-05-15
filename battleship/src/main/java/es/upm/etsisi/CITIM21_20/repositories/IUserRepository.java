package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;

public interface IUserRepository {

    public User getUser(String id);
    public boolean getUserByUsername (String username);
    public User createUser(String username, String id) throws IOException;
    public boolean deleteUser(String id);
}
