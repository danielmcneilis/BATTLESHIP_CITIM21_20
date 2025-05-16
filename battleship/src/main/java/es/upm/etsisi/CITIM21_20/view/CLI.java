package es.upm.etsisi.CITIM21_20.view;
import es.upm.etsisi.CITIM21_20.services.UserService;

import java.util.Scanner;

public class CLI {

    Scanner scanner;
    private UserService userService;
    private static CLI instance;

    public static CLI getInstance(Scanner scanner, UserService userService){
        if(instance == null){
            instance = new CLI(scanner, userService);
        }
        return instance;
    }

    private CLI(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public void showLogin(){


    }
}
