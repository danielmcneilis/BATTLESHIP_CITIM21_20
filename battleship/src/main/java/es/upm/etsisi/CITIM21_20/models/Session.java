package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.Date;

public class Session {

    @Getter
    private User user;
    private UPMUsers role;
    @Getter
    private boolean active;
    private LocalDateTime lastLogin;


    public Session(User user) {
        this.user = user;
        this.role = ObtencionDeRol.get_UPM_AccountRol(user.getEmail());
        this.active = true;
        this.lastLogin = LocalDateTime.now();
    }

    public void login(){
        this.active = true;
        this.lastLogin = LocalDateTime.now();
    }

    public void logout(){
        this.active = false;
        this.lastLogin = LocalDateTime.now();
    }
}
