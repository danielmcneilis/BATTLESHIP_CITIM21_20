package es.upm.etsisi.CITIM21_20.view;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.ISessonRepository;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.CITIM21_20.repositories.SessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.UserRepository;
import es.upm.etsisi.CITIM21_20.services.*;
import es.upm.etsisi.fis.model.IPuntuacion;
import servidor.ExternalLDAP;
import utilidades.Cifrado;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private IUserRepository userRepository;
    private ISessonRepository sessionRepository;
    private IUserService userService;
    private IGameService gameService;
    private IScoreService scoreService;
    private static CLI instance;


    public static CLI getInstance() throws IOException {
        if (instance == null) {
            instance = new CLI();
        }
        return instance;
    }

    private CLI() throws IOException {
        this.userRepository = UserRepository.getInstance();
        this.sessionRepository = SessionRepository.getInstance();
        this.gameService = new GameService(new Scanner(System.in));
        this.userService = new UserService(userRepository, sessionRepository);
        this.scoreService = new ScoreService(userRepository);
        this.excute();
    }

    public static void main(String[] args) throws IOException {
        CLI.getInstance();
    }

    private void excute() {
        this.startErrorHandling();
    }

    private void startErrorHandling() {
        boolean out = false;
        while (!out) {
            try {
                out = this.menuLogin();
            } catch (Exception exception) {
                launchError(exception);
            }
        }
    }

    private static void launchError(Exception exception) {
        System.out.println("---ERROR---> " + exception.getClass().getSimpleName() + " : " + exception.getMessage());
    }

    public boolean menuLogin() throws IOException {
        String id;
        Scanner scanner = new Scanner(System.in);
        int option;
        User userLoggeado = null;
        do {
            mostrarMenuLogin();
            option = scanner.nextInt();
        } while (option < 1 || option > 3);
        scanner.nextLine(); // limpieza de buffer
        switch (option) {
            case 1:
                id = ExternalLDAP.LoginLDAP();
                userLoggeado = this.userService.login(id);
                menuPrincipal(userLoggeado);
                return false;
            case 2:
                id = ExternalLDAP.LoginLDAP();
                System.out.println("Introduce un nombre de usuario");
                String username = scanner.nextLine();
                userLoggeado = this.userService.userRegister(id, username);

                // TODO: se puede pasar como parámetro el usuario o aumenta el acoplamiento?
                menuPrincipal(userLoggeado);
                return false;
            case 3:
                return true;
            default:
                System.out.println("ERROR");
                return false;
        }
    }

    private void menuPrincipal(User userLoggeado) throws IOException {
        int option;
        String id;
        Scanner scanner = new Scanner(System.in);
        do {
            mostrarMenuPrincipal();
            option = scanner.nextInt();
        } while (option < 1 || option > 5);
        scanner.nextLine(); // limpieza de buffer
        switch (option) {
            case 1:
                // empezar partida
                this.gameService.startGame(userLoggeado);
                menuPrincipal(userLoggeado);
                userRepository.saveUsers();
                break;
            case 2:
                //mostrar puntuacion
                List<IPuntuacion> puntuaciones = this.scoreService.getScore(userLoggeado);
                mostrarPuntuaciones(puntuaciones);
                menuPrincipal(userLoggeado);
                userRepository.saveUsers();
                break;
            case 3:
                // TODO Cambiar Nombre Usuario

                String actualUsername;
                System.out.println("Introduce tu nombre actual: ");
                actualUsername = scanner.nextLine();
                System.out.println("Introduce tu nombre nuevo: ");
                String newUsername = scanner.nextLine();
                userService.changeUserName(userLoggeado, actualUsername, newUsername);
                menuPrincipal(userLoggeado);
                userRepository.saveUsers();
                break;
            case 4:
                // cerrar sesion
                userService.logout(userLoggeado.getId());
                System.out.println("LOGOUT SUCCESSFUL");
                userRepository.saveUsers();
                break;
            case 5:
                userService.logout(userLoggeado.getId());
                userService.deleteAccount(userLoggeado.getId());
                System.out.println("Usuario borrado");
                userRepository.saveUsers();
                break;
            default:
                System.out.println("ERROR");
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("--------------BIENVENIDO--------------");
        System.out.println("1-. Comenzar Partida.");
        System.out.println("2-. Ver Puntuacion.");
        System.out.println("3-. Cambiar Nombre Usuario.");
        System.out.println("4-. Cerrar Sesion.");
        System.out.println("5-. Darse de Baja");

    }

    private void mostrarMenuLogin() {
        System.out.println("--------------MENU--------------");
        System.out.println("1-. Iniciar Sesión.");
        System.out.println("2-. Registrarse.");
        System.out.println("3-. Salir");
    }

    private void mostrarPuntuaciones(List<IPuntuacion> scores) {
        System.out.println("--------------Puntuaciones--------------");
        int i = 1;
        for (IPuntuacion puntuacion : scores) {
            System.out.println("Partida " + i + ":" + "----> " + puntuacion.getPuntos());
            i++;
        }

    }

}
