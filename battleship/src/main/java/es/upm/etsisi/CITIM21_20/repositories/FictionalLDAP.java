package es.upm.etsisi.CITIM21_20.repositories;

import java.util.HashMap;

/**
 * Esta es una clase ficticia que hace de apoyo al resto del proyecto.
 * Simula el componente externo de LDAP en el que se almacenaría la contraseña cifrada del usuario enlazada a un emailUPM
 */
public class FictionalLDAP {

    private HashMap<String, String> passwords;

    public FictionalLDAP(HashMap<String, String> LDAP) {
        this.passwords = LDAP;
    }

    public boolean isValid(String password, String userEmail) {
        return userEmail.equals(passwords.get(password));
    }

}
