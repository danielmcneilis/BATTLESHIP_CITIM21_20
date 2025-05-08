package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.IUser;
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
import java.util.Iterator;
import java.util.List;

public class UserService {

    private UserRepository userList;


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

    // PDI-----> profesores
    // PAS ----->
    public void getScoreAdmin(UPMUsers rol){ // la logica de buscar los jugadores y en la vista mostrar por pantalla
        if(rol == UPMUsers.ALUMNO){ // se que son alumnos

        }

    }

    public List<Score> UserScore(String nombreusuario){ // ver las 10 mejores partidas suyas
        User usuario = userList.getUser(nombreusuario);
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
