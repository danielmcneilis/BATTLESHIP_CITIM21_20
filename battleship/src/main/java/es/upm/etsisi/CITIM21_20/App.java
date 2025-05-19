package es.upm.etsisi.CITIM21_20;


import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.SessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.CITIM21_20.services.GameService;
import es.upm.etsisi.CITIM21_20.services.UserService;
import es.upm.etsisi.CITIM21_20.view.CLI;
import utilidades.Cifrado;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
//
//    public static void main(String[] args) {
//        UserRepository userRepository = UserRepository.getInstance();
//        SessionRepository sessionRepository = SessionRepository.getInstance();
//        GameService gameService = new GameService(new java.util.Scanner(System.in));
//        UserService userService = new UserService(userRepository, sessionRepository);
//        CLI cli = CLI.getInstance(userService, gameService);
//        this.startErrorHandling(cli);
//    }
//
//    private void startErrorHandling(CLI cli) {
//        boolean out = false;
//        while (!out) {
//            try {
//                out = this.e();
//            } catch (Exception exception) {
//                launchError(exception);
//            }
//        }
//    }
}
