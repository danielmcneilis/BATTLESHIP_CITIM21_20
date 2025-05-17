package es.upm.etsisi.CITIM21_20;


import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.SessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.CITIM21_20.services.GameService;
import es.upm.etsisi.CITIM21_20.services.UserService;
import es.upm.etsisi.CITIM21_20.view.CLI;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        UserRepository userRepository = UserRepository.getInstance();
        SessionRepository sessionRepository = SessionRepository.getInstance();
        GameService gameService = new GameService(new java.util.Scanner(System.in));
        UserService userService = new UserService(userRepository, sessionRepository);
        CLI cli = CLI.getInstance(userService, gameService);
        User pepe = userRepository.createUser("pepe", "pepe");
        sessionRepository.createSession(pepe, "pepe@alumnos.upm.es");
        cli.menuLogin();
        //gameService.startGame(pepe);

    }
}
