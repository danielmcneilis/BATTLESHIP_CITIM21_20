package es.upm.etsisi.CITIM21_20.view;

import es.upm.etsisi.CITIM21_20.services.UserService;
import servidor.ExternalRRSS;

public class GraficalUserInterface {

    private UserService userService;

    public void showLogin(){
        String cipheredUserName = ExternalRRSS.LoginRRSS();
        userService.login(cipheredUserName, //contraseñaCifrada);

    }
}
