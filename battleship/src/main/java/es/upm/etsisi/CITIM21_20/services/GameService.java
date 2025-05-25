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
        IMovimiento movimiento = new Movement();



        controladorPartida.crearPartida(usuariologued, puntuacion, movimiento);

        this.userList.saveUsers();

    }

}
