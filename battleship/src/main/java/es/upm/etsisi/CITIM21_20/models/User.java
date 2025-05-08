package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import servidor.ExternalRRSS;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.util.ArrayList;
import java.util.List;

public class User {

    @Getter
    private String username;
    @Getter
    private String email;
    @Getter
    private UPMUsers role;
    @Getter
    private List<Score> scoreList;


    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.scoreList = new ArrayList<Score>();
        this.role = ObtencionDeRol.get_UPM_AccountRol(email);
    }


}
