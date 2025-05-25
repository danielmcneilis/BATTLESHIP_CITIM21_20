package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.TestSessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.TestUserRepository;
import org.junit.Before;
import org.junit.Test;
import servidor.ExternalLDAP;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

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
    public void testUserRegister_userNameValidUnique() throws IOException {
        resetearTodo();
        String id = ExternalLDAP.LoginLDAP();

        String validUsername = "dapapu7";
        assertNull("El nombre de usuario no deberia existir en el repositorio", userRepository.getUserByUsername(validUsername));

        User result = userService.userRegister(id, validUsername);

        assertNotNull("El usuario no deberia ser null", result);
        assertEquals("El usuario deberia ser dapapu7", validUsername, result.getNombre());
        assertNotNull("El usuario deberia existir en el repositorio", userRepository.getUserByUsername(validUsername));
    }

    @Test(expected = RuntimeException.class)
    // CP2: N1 = nombre usuario es null
    public void testUserRegister_usernameEmpty() throws IOException {
        resetearTodo();

        String id = ExternalLDAP.LoginLDAP();
        String emptyName = "";

        userService.userRegister(id, emptyName);
    }

    @Test(expected = RuntimeException.class)
    // CP3: N2 = nombre de usuario con longitud mayor al permitido
    public void testUserRegister_usernameOutOfRange() throws IOException {
        resetearTodo();

        String id = ExternalLDAP.LoginLDAP();

        String notValidName = "danimjkikedavid";

        User result = userService.userRegister(id, notValidName);
    }
}
