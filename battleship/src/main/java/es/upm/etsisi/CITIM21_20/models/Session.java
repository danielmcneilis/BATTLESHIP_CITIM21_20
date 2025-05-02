package es.upm.etsisi.CITIM21_20.models;

import servidor.UPMUsers;

public class Session {

    private User user;
    private UPMUsers role;


    public Session(User user, UPMUsers role) {
        this.user = null;
        this.role = role;
    }

    public void login(User user){
        this.user = user;
    }

    public User getUser(String username){
        if (user.getUsername().equals(username)) {
            return user;
        } else {
            return null;
        }
    }
}
