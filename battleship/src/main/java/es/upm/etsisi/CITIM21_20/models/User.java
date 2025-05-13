package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String username;
    @Getter
    private String email;
    private Score score;


    public User(String username, String email, Score score) {
        this.username = username;
        this.email = email;
        this.score = score;
    }

}
