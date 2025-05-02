package es.upm.etsisi.CITIM21_20;

import es.upm.etsisi.CITIM21_20.controllers.UserController;
import servidor.ExternalRRSS;

public class GraficalUserInterface {

    private UserController userController;

    public void showLogin(){
        String cipheredUserName = ExternalRRSS.LoginRRSS();
        userController.login(cipheredUserName, //contraseñaCifrada);

    }
}
