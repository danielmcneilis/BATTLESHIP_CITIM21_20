package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {

    private static UserRepository instance;
    private HashMap<String, User> userList;

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository(new HashMap<>());
        }
        return instance;
    }

    private UserRepository(HashMap<String, User> userList) {
        this.userList = userList;
    }

    @Override
    public User getUser(String id){
        return userList.get(id);
    }

    @Override
    public User createUser(String username, String id) throws IOException {
        User user = new User(username, id);
        if(user.isValidUserName())
        {
            userList.put(id, user);
            return user;
        }
        return null;
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
    public boolean deleteUser(String username) {
        return false;
    }
}
