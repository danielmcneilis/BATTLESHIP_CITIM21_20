package es.upm.etsisi.CITIM21_20.controllers;

import es.upm.etsisi.CITIM21_20.models.Session;
import servidor.Autenticacion;
import utilidades.Cifrado;


import java.util.Iterator;
import java.util.List;

public class UserController {

    private List<Session> sessions;


    public UserController(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void login(String username, String password){
        String ciferdPassword = Cifrado.cifrar(password, Cifrado.Tipo.valueOf("SHA"));
        if (this.getSession(username) != null){
            //Comprobacion de contraseña valida
        }

    }

    private Session getSession(String username){
        Iterator<Session> it = sessions.iterator();
        while(it.hasNext()){
            Session session = it.next();
            if(session.getUser(username) != null){
                return session;
            }
        }
        return null;
    }
}
