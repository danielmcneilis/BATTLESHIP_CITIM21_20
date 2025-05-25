package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;

import java.time.LocalDateTime;

public class Session {

    @Getter
    private User user;
    @Getter
    private boolean active;
    @Getter
    private String id;
    private LocalDateTime lastLogin;


    public Session() {
        this.lastLogin = LocalDateTime.now();
    }

    public Session(User user, String id) {
        this.user = user;
        this.id = id;
        this.active = true;
        this.lastLogin = LocalDateTime.now();
    }

    public void login() {
        this.active = true;
        System.out.println("LAST LOGIN: " + DateTimeFormatter.dateFormater(lastLogin));
        this.lastLogin = LocalDateTime.now();
    }

    public boolean logout() {
        if (!this.active) {
            return false;
        }
        this.active = false;
        return true;
    }

}
