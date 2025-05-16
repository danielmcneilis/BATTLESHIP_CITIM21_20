package es.upm.etsisi.CITIM21_20.view;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.CITIM21_20.services.UserService;
import servidor.ExternalRRSS;
import servidor.UPMUsers;

import java.util.List;

public class GraficalUserInterface {

    private UserService userService;
    private UserRepository repositoriousuarios;

    public void showLogin() {
        String cipheredUserName = ExternalRRSS.LoginRRSS();
        userService.login(cipheredUserName, //contraseñaCifrada);

    }

    public void showScore(){
        if(/* es jugador */){ // solo las 10 mejores puntuaciones
            List<Score> nueva = userService.UserScore(/*nombreusuario*/);
            if(!(nueva.isEmpty())){
                for(int i = 0; i< nueva.size(); i++){
                    System.out.println("Id partida: " + nueva.get(i).getPartidaasociada() + " Puntuacion: " + nueva.get(i).getPuntos() );
                }
            }else{
                System.out.println("No se han registrado partidas");
            }


        }else{ // admin que pueden ver todas las puntuaciones
            repositoriousuarios.mostrarlista();// no estoy seguro como hacerlo

            for(int i = 0; i< repositoriousuarios.; i++){
                System.out.println("Id partida: " + nueva.get(i).getPartidaasociada() + " Puntuacion: " + nueva.get(i).getPuntos() );
            }


        }

    }


}
