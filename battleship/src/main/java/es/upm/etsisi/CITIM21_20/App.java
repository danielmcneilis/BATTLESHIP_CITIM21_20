package es.upm.etsisi.CITIM21_20;


import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.SessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.CITIM21_20.services.GameService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UserRepository userRepository = UserRepository.getInstance();
        SessionRepository sessionRepository = SessionRepository.getInstance();
        User pepe = userRepository.createUser("pepe", "pepe");
        sessionRepository.createSession(pepe, "pepe@alumnos.upm.es");
        GameService gameService = new GameService(new java.util.Scanner(System.in));
        gameService.startGame(pepe);

    }
}
