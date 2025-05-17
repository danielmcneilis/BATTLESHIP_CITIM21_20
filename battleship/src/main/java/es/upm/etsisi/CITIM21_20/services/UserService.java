package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
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
            throw new RuntimeException("ERROR LDAP");
        }

        if (userList.getUser(id) != null) {
            throw new RuntimeException("INVALID USER");
        }

        do {
            System.out.println("Introduce un nombre de usuario");
            username = scanner.nextLine();
            if (userList.getUserByUsername(username) != null) {
                throw new RuntimeException("REGISTRATION NOT POSIBLE");
            }
            createdUser = userList.createUser(username, id);
        } while (createdUser == null);

        sessionList.createSession(createdUser, id);
        return createdUser;
    }

    public User login() {
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
        return user;
    }

    public void logout(User user) {
        Session userSession = this.sessionList.getSession(user.getId());
        if(userSession == null){
            throw new RuntimeException("ERROR AL LOGOUT");
        }
        userSession.logout();
    }

    public void deleteAccount(User user) {
        this.userList.deleteUser(user.getId());
        this.sessionList.deleteSession(user.getId());
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
                    throw new RuntimeException("CHANGE NAME NOT POSIBLE");
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

//    public List<Score> getScore (User usuario){
//        UPMUsers rol =  get_UPM_AccountRol(usuario.getEmail());
//        List<Score> listapuntuaciones = new ArrayList<>();
//        if(rol == UPMUsers.ALUMNO){
//            listapuntuaciones = userScore(usuario.getUsername());
//        } else if (rol == UPMUsers.PDI) { // no tendria que ser esto admin????
//            listapuntuaciones = adminScore(usuario.getUsername());
//        }
//        return listapuntuaciones;
//    }
//
//    public boolean changeUserName(User user, String newUserName) throws IOException {
//        if (!user.isValidUserName()) {
//            throw new RuntimeException("INVALID USERNAME");
//        }
//
//        if (userList.getUserByUsername(newUserName)) {
//            throw new RuntimeException("INVALID USERNAME");
//        }
//
//        user.setUsername(newUserName);
//        return true;
//    }
//
//    // PDI-----> profesores
//    // PAS ----->
//    private List<Score> adminScore(String nombreusuario){
//        // el admin puede ver todas las puntuaciones, pero si puede ver todas, para que le paso un nombre como parametro
//        List<Score> listapuntuaciones = new ArrayList<>();
//        for (User savedUsers : userList.values()){ // me recorre el mapa entero con los valores
//            listapuntuaciones.addAll(savedUsers.getScoreList()); // me guarda toda su lista de puntuaciones
//        }
//        return listapuntuaciones;
//    }
//
//    private List<Score> userScore(String nombreusuario){ // ver las 10 mejores partidas suyas
//        User usuario = userList.get(nombreusuario); // No se si usamos el nombre de usuario o su id que devulve el LDAP
//        List<Score> top10 = new ArrayList<>();
//        List<Score> nueva = CloneList(usuario.getScoreList());
//        if(usuario != null) {
//            // ordenar la lista de puntuaciones //bublesort // no puedo usar for, necesito con objetos
//            for(int i = 0; i< 10; i++){
//                Score maximo = new Score();
//                Iterator<Score> it = usuario.getScoreList().iterator();
//                while (it.hasNext()){
//                    Score x = it.next();
//                    if(x.getPuntos() > maximo.getPuntos()){ // aqui no me haria falta el !(x.equals(maximo)) porque como los elimino
//                        maximo = x;
//                    }
//                }
//                top10.add(maximo);
//                nueva.remove(maximo);
//            }
//        }
//        return top10; // el problema con esto es que que pasa si me lo devuelven vacio, tenemos que implementar manejor de excepciones??
//
//    }
//
//
//    private List<Score> CloneList(List<Score> original){ // para no borrar contenido de la original
//        List<Score> clone = new ArrayList<>();
//        Iterator<Score> it = original.iterator();
//        while(it.hasNext()){
//            Score x = it.next();
//            clone.add(x);
//        }
//        return clone;
//    }





}
