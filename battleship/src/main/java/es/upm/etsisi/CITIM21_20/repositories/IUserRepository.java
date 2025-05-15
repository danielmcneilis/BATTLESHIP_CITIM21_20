package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

public interface IUserRepository {

    public User getUser(String id);
    public User createUser(String username, String id);
    public boolean deleteUser(String id);
}
