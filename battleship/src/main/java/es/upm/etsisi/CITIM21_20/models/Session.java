package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.time.LocalDateTime;

public class Session {

    @Getter
    private User user;
    private UPMUsers role;
    @Getter
    private boolean active;
    private LocalDateTime lastLogin;


    public Session(User user, String email) {
        this.user = user;
        this.role = ObtencionDeRol.get_UPM_AccountRol(email);
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
