package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

public interface ISessonRepository {

    Session getSession(String id);

    void createSession(User user, String email);

    boolean deleteSession(String id);
}
