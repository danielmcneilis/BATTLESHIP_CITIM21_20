package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserRepository {

    private List<User> userList;


    public UserRepository() {
        userList = new ArrayList<User>();
    }

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

    public void createUser(String username, String email){
        User user = new User(username, email);
        userList.add(user);
    }
}
