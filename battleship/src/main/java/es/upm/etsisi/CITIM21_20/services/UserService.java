package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.fis.model.IPuntuacion;
import servidor.ExternalLDAP;

import java.io.IOException;
import java.util.*;

public class UserService {

    private IUserRepository userList;
    private ISessonRepository sessionList;


    public UserService(IUserRepository userList, ISessonRepository sessionList) {
        this.userList = userList;
        this.sessionList = sessionList;
    }

    public User userRegister() throws IOException {
        String id = ExternalLDAP.LoginLDAP(); // unico para cada usuario
        Scanner scanner = new Scanner(System.in);
        String username;
        User createdUser;

        if (id == null) {
            throw new RuntimeException("ERROR IN LDAP LOGIN");
        }

        if (userList.getUser(id) != null) {
            throw new RuntimeException("USER ALREADY REGISTERED");
        }

        do {
            System.out.println("Introduce un nombre de usuario");
            username = scanner.nextLine();
            if (userList.getUserByUsername(username) != null) {
                System.out.println("USERNAME ALREADY EXISTS, TRY ANOTHER ONE :) ");
            }
            createdUser = userList.createUser(username, id);
        } while (createdUser == null);

        sessionList.createSession(createdUser, id);
        createdUser.setAdmin(false);
        return createdUser;
    }

    public User login(){
        String id = ExternalLDAP.LoginLDAP();

        if (id == null) {
            throw new RuntimeException("ERROR IN LDAP LOGIN");
        }

        User user = userList.getUser(id);
        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }

        Session session = sessionList.getSession(id);
        session.login();
        return user;
    }

    public void logout(User user) {
        Session userSession = this.sessionList.getSession(user.getId());
        if(userSession == null){
            System.out.println("USER NOT FOUND");
        }
       if (userSession.logout()){
           System.out.println("LOGOUT SUCCESSFUL");
       }
       else{
           System.out.println("LOGOUT FAILED");
       }
    }

    public void deleteAccount(User user) {
        String id = ExternalLDAP.LoginLDAP();
        if(user.getId().equals(id)){
            this.userList.deleteUser(user.getId());
            this.sessionList.deleteSession(user.getId());
        } else {
            throw new RuntimeException("ERROR LOGIN LDAP");
        }
    }

    public void changeUserName(User user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String username;
        boolean correcto = isSure(user.getUsername());
        if(correcto){
            do {
                System.out.println("Introduce un nuevo nombre de usuario");
                username = scanner.nextLine();
                if (userList.getUserByUsername(username) != null) {
                    System.out.println("CHANGE NAME NOT POSIBLE");
                }
                user.setUsername(username);
            } while (!user.isValidUserName());
        }
    }

    private boolean isSure(String actualUsername){
        Scanner scanner = new Scanner(System.in);
        String username;
        System.out.println("Para cambiar el nombre tienes que introducir tu nombre actual ");
        do {
            System.out.print("Introduce tu nombre de usuario actual: ");
            username = scanner.nextLine();
        }while (!username.equals(actualUsername));

        return true;
    }


    public List<IPuntuacion> getScore (User usuario){
        List<IPuntuacion> listapuntuaciones = new ArrayList<>();
        if(usuario.isAdmin()){
            System.out.println("SOY ADMIN");
            listapuntuaciones = adminScore();

        } else{
            System.out.println("SOY ALUMNº");
            listapuntuaciones = userScore(usuario.getUsername());

        }
        return listapuntuaciones;
    }


    private List<IPuntuacion> adminScore(){
        // el admin puede ver todas las puntuaciones, pero si puede ver todas, para que le paso un nombre como parametro
        List<IPuntuacion> listapuntuaciones = new ArrayList<>();
        for(User user : userList.valores()){
            for(IPuntuacion puntuacion : user.getPuntuaciones()){
                listapuntuaciones.add(puntuacion);
            }
        }

        return listapuntuaciones;
    }

    private List<IPuntuacion> userScore(String nombreusuario){ // ver las 10 mejores partidas suyas
        User usuario = userList.getUserByUsername(nombreusuario); // necesito sacarlo del hashmap
        List<IPuntuacion> top10 = new ArrayList<>();
        List<IPuntuacion> nueva = CloneList(usuario.getPuntuaciones());
        if(usuario == null) {
            System.out.println("USER NOT FOUND");
        }else {
            // ordenar la lista de puntuaciones //bublesort // no puedo usar for, necesito con objetos
            for (IPuntuacion puntuacion : nueva) {
                IPuntuacion maximo = new Score();
                Iterator<IPuntuacion> it = nueva.iterator();
                while (it.hasNext()) {
                    IPuntuacion x = it.next();
                    if (puntuacion.getPuntos() > x.getPuntos()) { // aqui no me haria falta el !(x.equals(maximo)) porque como los elimino
                        maximo = puntuacion;
                    }
                }
                top10.add(maximo);
                nueva.remove(maximo);
            }
        }
        return top10; // el problema con esto es que que pasa si me lo devuelven vacio, tenemos que implementar manejor de excepciones?
    }


    private List<IPuntuacion> CloneList(List<IPuntuacion> original){ // para no borrar contenido de la original
        List<IPuntuacion> clone = new ArrayList<>();
        Iterator<IPuntuacion> it = original.iterator();
        while(it.hasNext()){
            IPuntuacion x = it.next();
            clone.add(x);
        }
        return clone;
    }






}
