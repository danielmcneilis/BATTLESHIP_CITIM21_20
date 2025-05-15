package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    private Score score;


    public User(String username, String id, Score score) {
        if (!this.isValidUserName()){
            throw new RuntimeException("INVALID USERNAME");
        }
        this.username = username;
        this.id = id;
        this.score = score;
    }

    public boolean isValidUserName(){
        return true;
    }

}
