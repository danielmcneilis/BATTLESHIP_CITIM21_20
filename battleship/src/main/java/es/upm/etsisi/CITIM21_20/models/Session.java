package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.time.LocalDateTime;

public class Session {

    @Getter
    private User user;
    @Getter
    private boolean active;
    @Getter
    private String id;
    private LocalDateTime lastLogin;


    public Session(User user, String id) {
        this.user = user;
        this.id = id;
        this.active = true;
        this.lastLogin = LocalDateTime.now();
    }

    public void login() {
        this.active = true;
        this.lastLogin = LocalDateTime.now();
    }

    public boolean logout() {
        if (!this.active) {
            return false;
        }
        this.active = false;
        this.lastLogin = LocalDateTime.now();
        return true;
    }
}
