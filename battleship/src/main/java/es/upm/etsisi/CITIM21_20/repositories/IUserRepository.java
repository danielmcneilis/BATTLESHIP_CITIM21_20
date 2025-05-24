package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;
import java.util.ArrayList;

public interface IUserRepository {


        @Override
        User getUser(String id);

        @Override
        User createUser(String username, String id) throws IOException ;

        @Override
        User getUserByUsername(String username);

        @Override
        void deleteUser(String id);

        ArrayList<User> valores();

        void inicializarFichero ();

        void saveUsers(HashMap <String, User> mapa);

        void loadUsers ();
}

