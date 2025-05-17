package es.upm.etsisi.CITIM21_20.view;
import es.upm.etsisi.CITIM21_20.services.GameService;
import es.upm.etsisi.CITIM21_20.services.UserService;

import java.io.IOException;
import java.util.Scanner;

public class CLI {

    private UserService userService;
    private GameService gameService;
    private static CLI instance;

    public static CLI getInstance(UserService userService, GameService gameService){
        if(instance == null){
            instance = new CLI(userService, gameService);
        }
        return instance;
    }

    private CLI(UserService userService, GameService gameService){
        this.gameService = gameService;
        this.userService = userService;
    }

    public void menuLogin() throws IOException {
        int option;
        do {
            mostrarMenuLogin();
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
        } while(option < 1 || option > 3 );

        switch (option){
            case 1:
                this.userService.login();
                break;
            case 2:
                userService.userRegister();
                break;
            case 3:
                break;
            default:
                System.out.println("ERROR");
        }
    }

    private void mostrarMenuLogin(){
        System.out.println("--------------MENU--------------");
        System.out.println("1-. Iniciar Sesión.");
        System.out.println("2-. Registrarse.");
        System.out.println("3-. Salir");
    }
}
