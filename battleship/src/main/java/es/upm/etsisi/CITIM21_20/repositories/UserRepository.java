package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserRepository implements IUserRepository{

    private static UserRepository instance;
    private static List<User> userList;

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        userList = new ArrayList<User>();
    }

    @Override
    public User getUser(String username){
        Iterator<User> it = userList.iterator();
        while(it.hasNext()){
            User user = it.next();
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User createUser(String username, String email){
        Score userScore = new Score();
        User user = new User(username, email, userScore);
        userList.add(user);
        return user;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }
}
