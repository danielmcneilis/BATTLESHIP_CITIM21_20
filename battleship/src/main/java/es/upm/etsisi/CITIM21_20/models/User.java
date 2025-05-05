package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import servidor.ExternalRRSS;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

public class User {

    @Getter
    private String username;
    @Getter
    private String email;
    @Getter
    private UPMUsers role;
    private Score score;


    public User(String username, String email, Score score) {
        this.username = username;
        this.email = email;
        this.score = score;
        this.role = ObtencionDeRol.get_UPM_AccountRol(email);
    }


}
