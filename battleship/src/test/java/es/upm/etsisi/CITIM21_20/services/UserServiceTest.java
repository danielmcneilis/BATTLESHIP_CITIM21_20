package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.TestSessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.TestUserRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceTest {
    private TestUserRepository userRepository;
    private TestSessionRepository sessonRepository;
    private IUserService userService;

    @Before
    public void setUp() {
        userRepository = TestUserRepository.getInstance();
        sessonRepository = TestSessionRepository.getInstance();
        userService = new UserService(userRepository, sessonRepository);
    }

    private void resetearTodo() {
        userRepository.reset();
        sessonRepository.reset();
    }

    private void simulateUserInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    // CP1: V1 = nombre usuario valido y unico
    // TODO: preguntar si es necesario comprobar que el nombre no este en la blacklist
    // TODO: preguntar si todo va en el mismo test
    public void testUserRegister_userNameValidUnique() throws IOException {
        resetearTodo();

        String validUsername = "dapapu7";
        assertNull("El nombre de usuario no deberia existir en el repositorio", userRepository.getUserByUsername(validUsername));

        simulateUserInput(validUsername + "\n");

        User result = userService.userRegister();

        assertNotNull("El usuario no deberia ser null", result);
        assertEquals("El usuario deberia ser dapapu7", validUsername, result.getUsername());
        assertNotNull("El usuario deberia existir en el repositorio", userRepository.getUserByUsername(validUsername));
    }

    @Test
    // CP2: N1 = nombre usuario es null
    // TODO error que sale al ejecutar
    public void testUserRegister_usernameEmpty() throws IOException {
        resetearTodo();

        String emptyName = "";

        simulateUserInput(emptyName + "\n");

        User result = userService.userRegister();

        assertNotNull("El usuario no deberia ser null", result);
        assertNull("No deberia existir un usuario con nombre vacio", userRepository.getUserByUsername(emptyName));
    }

    @Test
    // CP3: N2 = nombre de usuario con longitud mayor al permitido
    public void testUserRegister_usernameOutOfRange() throws IOException {
        resetearTodo();

        String emptyName = "danimjkikedavid";

        simulateUserInput(emptyName + "\n");

        User result = userService.userRegister();

        assertNotNull("El usuario no deberia ser null", result);
        assertNull("No deberia existir un usuario con nombre vacio", userRepository.getUserByUsername(emptyName));
    }
}
