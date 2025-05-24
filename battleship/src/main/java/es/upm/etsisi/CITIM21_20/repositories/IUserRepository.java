package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IUserRepository {



        User getUser(String id);

        User createUser(String username, String id) throws IOException ;

        User getUserByUsername(String username);

        void deleteUser(String id);

        ArrayList<User> valores();


}

