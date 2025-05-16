package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.NameValidator;
import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import servidor.Autenticacion;
import servidor.ExternalLDAP;
import servidor.UPMUsers;
import utilidades.Cifrado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static servidor.ObtencionDeRol.get_UPM_AccountRol;

public class UserService {

    private HashMap<String, User> userList;

    public UserService(UserRepository userList) {
        this.userList = userList;
    }

    public void userRegister(String username, String email, String password) throws IOException {
        String ciferdPassword = Cifrado.cifrar(password, Cifrado.Tipo.valueOf("SHA"));
        if (!Autenticacion.existeCuentaUPMStatic(email)) {
            throw new RuntimeException("INVALID EMAIL");
        }
        if (userList.getUser(username) != null) {
            throw new RuntimeException("INVALID USERNAME");
        }
        if(!NameValidator.isValid(username)){
            throw new RuntimeException("INVALID USERNAME");
        }
        if (username.length() < 3 || username.length() > 10) {
            throw new RuntimeException("INVALID USERNAME");
        }
        userList.createUser(username, email);
    }

    public void login(){
        String cipheredUserName = ExternalLDAP.LoginLDAP();
        while (cipheredUserName != null) {
            //No se como se gestiona la contraseña ya que existen varias clases como Correo o ExternalLDAP para cosas relacionadas con el email y password pero no entiendo muy bien lo que hacen
            String password = null;
            String ciferdPassword = Cifrado.cifrar(password, Cifrado.Tipo.valueOf("SHA"));
            if (userList.getUser(username) != null){
                //Comprobacion de contraseña valida
            }
        }

    }







}
