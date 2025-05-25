package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.TestSessionRepository;
import es.upm.etsisi.CITIM21_20.repositories.TestUserRepository;
import org.junit.Before;
import org.junit.Test;
import servidor.ExternalLDAP;
import utilidades.Cifrado;

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

    @Test
    // CP1: V1 = nombre usuario actual valido y unico V2 = nombre usuario nuevo valido y unico
    public void testChangeUsername_Validos() throws IOException {
        resetearTodo();
        User user = userRepository.createUser("Daniel", Cifrado.cifrar("@alumnos.upm.es"));

        assertNotNull("El usuario no deberia ser null", user);

        String validNewUsername = "dapapu7";

        assertNotNull("El nombre de usuario actual deberia existir en el repositorio", userRepository.getUserByUsername(user.getNombre()));
        assertNull("El nombre de usuario nuevo no deberia existir en el repositorio", userRepository.getUserByUsername(validNewUsername));

        userService.changeUserName(user, user.getNombre(), validNewUsername);

        assertEquals("El usuario deberia ser dapapu7", validNewUsername, user.getNombre());
        assertNotNull("El usuario deberia existir en el repositorio", userRepository.getUserByUsername(validNewUsername));
    }

    @Test(expected = RuntimeException.class)
    // CP2: V1 = nombre de usuario actual valido, N3 = nombre de usuario nuevo no valido por longitud
    public void testChangeUsername_validoActualNoValidoNuevoExistente() throws IOException {
        resetearTodo();

        User user = userRepository.createUser("dapapu7", Cifrado.cifrar("@alumnos.upm.es"));

        String notValidName = "Danieladfadsfa";

        assertNotNull("El nombre de usuario actual deberia existir en el repositorio", userRepository.getUserByUsername(user.getNombre()));

        userService.changeUserName(user, user.getNombre(), notValidName);
    }

    @Test(expected = RuntimeException.class)
    // CP3: V1 = nombre de usuario actual valido, N4 = nombre de usuario nuevo no valido vacio
    public void testChangeUsername_validoActualNoValidoNuevoVacio() throws IOException {
        resetearTodo();

        User user = userRepository.createUser("dapapu7", Cifrado.cifrar("@alumnos.upm.es"));

        String notValidName = "";

        assertNotNull("El nombre de usuario actual deberia existir en el repositorio", userRepository.getUserByUsername(user.getNombre()));

        userService.changeUserName(user, user.getNombre(), notValidName);
    }

    @Test(expected = RuntimeException.class)
    // CP4: N1 = nombre de usuario actual no valido longitud, V2 = nombre de usuario nuevo valido
    public void testChangeUsername_noValidoActualLongitudValidoNuevo() throws IOException {
        resetearTodo();

        User user = userRepository.createUser("jaldfkjlakdfljaf", Cifrado.cifrar("@alumnos.upm.es"));

        String notValidName = "Dapapu7";

        assertNull("El nombre de usuario actual no deberia existir en el repositorio", userRepository.getUserByUsername(user.getNombre()));

        userService.changeUserName(user, user.getNombre(), notValidName);
    }

    @Test(expected = RuntimeException.class)
    // CP4: N1 = nombre de usuario actual no valido longitud, V2 = nombre de usuario nuevo valido
    public void testChangeUsername_noValidoActualVacioValidoNuevo() throws IOException {
        resetearTodo();

        User user = userRepository.createUser("", Cifrado.cifrar("@alumnos.upm.es"));

        String notValidName = "Dapapu7";

        assertNull("El nombre de usuario actual no deberia existir en el repositorio", userRepository.getUserByUsername(user.getNombre()));

        userService.changeUserName(user, user.getNombre(), notValidName);
    }
}
