package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

public interface IUserRepository {

    public User getUser(String username);
    public User createUser(String username, String email);
    public boolean deleteUser(String username);
}
