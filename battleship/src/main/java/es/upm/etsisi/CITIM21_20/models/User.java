package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;

public class User {

    @Getter
    private String username;
    private String email;


    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
