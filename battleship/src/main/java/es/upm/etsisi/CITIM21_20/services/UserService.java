package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.NameValidator;
import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.FictionalLDAP;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import servidor.Autenticacion;
import servidor.ExternalLDAP;
import utilidades.Cifrado;

import java.io.IOException;

public class UserService {

    private IUserRepository userList;
    private FictionalLDAP ldap;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, FictionalLDAP ldap, ISessonRepository sessionList) {
        this.userList = userList;
        this.ldap = ldap;
        this.sessionList = sessionList;
    }

    public void userRegister(String username, String email, String password) throws IOException {
        String ciferdPassword = Cifrado.cifrar(password, Cifrado.Tipo.valueOf("SHA"));
        if (!Autenticacion.existeCuentaUPMStatic(email)) {
            throw new RuntimeException("INVALID EMAIL");
        }
        if (!ldap.isValid(ciferdPassword, email)){
            throw new RuntimeException("IS NOT POSSIBLE TO REEGISTER");
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
        User user = userList.createUser(username, email);
        sessionList.createSession(user);
    }

    public boolean login(String password) throws IOException {
        String cipheredUserName = ExternalLDAP.LoginLDAP();
        if (cipheredUserName == null) {
            throw new RuntimeException("INVALID USERNAME");
        }
        //String ciferdPassword = Cifrado.cifrar(password, Cifrado.Tipo.valueOf("SHA"));
        User user = userList.getUser(cipheredUserName);
        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }
        if (!ldap.isValid(password, user.getEmail())) {
            throw new RuntimeException("IS NOT POSSIBLE TO REEGISTER");
        }
        Session session = sessionList.getSession(cipheredUserName);
        session.login();
        //Ir a la pantalla principal
        return true;
    }

    public boolean logout(User user){
        Session userSession = this.sessionList.getSession(user.getUsername());
        userSession.logout();
        //Ir a la pantalla principal
        return true;
    }

    public boolean DeleteAccount(User user){
        this.userList.deleteUser(user.getUsername());
        this.sessionList.deleteSession(user.getUsername());
        //Ir a la pantalla principal
        return true;
    }

    public boolean changeUserName(String username, String newUserName) throws IOException {
        if (!NameValidator.isValid(newUserName)){
            throw new RuntimeException("INVALID USERNAME");
        }
        User user = this.userList.getUser(username);
        user.setUsername(newUserName);
        return true;
    }


}
