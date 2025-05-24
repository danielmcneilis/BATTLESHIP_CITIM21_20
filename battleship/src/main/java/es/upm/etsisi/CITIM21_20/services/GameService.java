package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Movement;
import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.fis.controller.ControladorPartida;
import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.List;
import java.util.Scanner;

public class GameService implements IGameService{

    private ControladorPartida controladorPartida;
    private IUserRepository userList;

    public GameService(Scanner scanner) {
        this.controladorPartida = ControladorPartida.getInstance(scanner);
        this.userList = UserRepository.getInstance();
    }

    @Override
    public void startGame(IJugador usuariologued) {
        IPuntuacion puntuacion = new Score();
        IPuntuacion punto = new Score();
        IMovimiento movimiento = new Movement();

        puntuacion.setPuntuacion(15);
        usuariologued.addPuntuacion(puntuacion);
        punto.setPuntuacion(20);
        usuariologued.addPuntuacion(punto);
        userList.saveUsers();
        /*
        controladorPartida.crearPartida(usuariologued, puntuacion, movimiento);
        List<IPuntuacion> lista = usuariologued.getPuntuaciones();
        for(IPuntuacion score : lista){
            System.out.println(score.getPuntos());
        } // esto me esta mostrando bien la puntuacion

        this.userList.saveUsers();
        userList.getUserByUsername(usuariologued.getNombre());
        System.out.println("despues de meter fichero");

         */
        List<IPuntuacion> listation = usuariologued.getPuntuaciones();
        for(IPuntuacion score : listation){
            System.out.println(score.getPuntos());
        } // esto me esta mostrando bien la puntuacion
    }

}
