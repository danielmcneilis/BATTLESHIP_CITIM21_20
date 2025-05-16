package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
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

    private IUserRepository userList;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, ISessonRepository sessionList) {
        this.userList = userList;
    }

    public void userRegister(String username, String email) throws IOException {
        String id = ExternalLDAP.LoginLDAP();
        if (id == null) {
            throw new RuntimeException("ERROR LDAP");
        }

        if (userList.getUser(id) != null) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (userList.getUserByUsername(username)) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (username.length() < 3 || username.length() > 10) {
            throw new RuntimeException("INVALID USERNAME");
        }

        User user = userList.createUser(username, id);
        sessionList.createSession(user, email);
    }

    public boolean login() throws IOException {
        String id = ExternalLDAP.LoginLDAP();
        if (id == null) {
            throw new RuntimeException("INVALID USERNAME");
        }

        User user = userList.getUser(id);
        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }

        Session session = sessionList.getSession(id);
        session.login();
        //Ir a la pantalla principal
        return true;
    }

    public List<Score> getScore (User usuario){
        UPMUsers rol =  get_UPM_AccountRol(usuario.getEmail());
        List<Score> listapuntuaciones = new ArrayList<>();
        if(rol == UPMUsers.ALUMNO){
            listapuntuaciones = userScore(usuario.getUsername());
        } else if (rol == UPMUsers.PDI) { // no tendria que ser esto admin????
            listapuntuaciones = adminScore(usuario.getUsername());
        }
        return listapuntuaciones;
    }

    public boolean logout(User user) {
        Session userSession = this.sessionList.getSession(user.getId());
        userSession.logout();
        //Ir a la pantalla principal
        return true;
    }

    public boolean DeleteAccount(User user) {
        this.userList.deleteUser(user.getId());
        this.sessionList.deleteSession(user.getId());
        //Ir a la pantalla principal
        return true;
    }

    public boolean changeUserName(User user, String newUserName) throws IOException {
        if (user.isValidUserName()) {
            throw new RuntimeException("INVALID USERNAME");
        }

        if (userList.getUserByUsername(newUserName)) {
            throw new RuntimeException("INVALID USERNAME");
        }

        user.setUsername(newUserName);
        return true;
    }

    // PDI-----> profesores
    // PAS ----->
    private List<Score> adminScore(String nombreusuario){
        // el admin puede ver todas las puntuaciones, pero si puede ver todas, para que le paso un nombre como parametro
        List<Score> listapuntuaciones = new ArrayList<>();
        for (User savedUsers : userList.values()){ // me recorre el mapa entero con los valores
            listapuntuaciones.addAll(savedUsers.getScoreList()); // me guarda toda su lista de puntuaciones
        }
        return listapuntuaciones;
    }

    private List<Score> userScore(String nombreusuario){ // ver las 10 mejores partidas suyas
        User usuario = userList.get(nombreusuario); // No se si usamos el nombre de usuario o su id que devulve el LDAP
        List<Score> top10 = new ArrayList<>();
        List<Score> nueva = CloneList(usuario.getScoreList());
        if(usuario != null) {
            // ordenar la lista de puntuaciones //bublesort // no puedo usar for, necesito con objetos
            for(int i = 0; i< 10; i++){
                Score maximo = new Score();
                Iterator<Score> it = usuario.getScoreList().iterator();
                while (it.hasNext()){
                    Score x = it.next();
                    if(x.getPuntos() > maximo.getPuntos()){ // aqui no me haria falta el !(x.equals(maximo)) porque como los elimino
                        maximo = x;
                    }
                }
                top10.add(maximo);
                nueva.remove(maximo);
            }
        }
        return top10; // el problema con esto es que que pasa si me lo devuelven vacio, tenemos que implementar manejor de excepciones??

    }


    private List<Score> CloneList(List<Score> original){ // para no borrar contenido de la original
        List<Score> clone = new ArrayList<>();
        Iterator<Score> it = original.iterator();
        while(it.hasNext()){
            Score x = it.next();
            clone.add(x);
        }
        return clone;
    }





}
