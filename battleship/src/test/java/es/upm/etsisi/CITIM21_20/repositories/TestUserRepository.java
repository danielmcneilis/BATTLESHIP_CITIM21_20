package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestUserRepository implements IUserRepository{
    private HashMap<String, User> userList;

    public TestUserRepository(){
        this.userList = new HashMap<>();
    }

    @Override
    public User getUser(String id) {
        return userList.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        for (Map.Entry<String, User> entry : userList.entrySet()) {
            User user = entry.getValue();
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User createUser(String username, String id) throws IOException {
        User user = new User(username, id);
        if (user.isValidUserName()) {
            userList.put(id, user);
            return user;
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        this.userList.remove(id);
    }

    @Override
    public ArrayList<User> valores() {
        ArrayList<User> lista = new ArrayList<>();
        for (User user : userList.values()) {
            lista.add(user);
        }
        return lista;
    }
}
