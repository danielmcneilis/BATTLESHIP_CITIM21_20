package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

public interface ISessonRepository {

    public Session getSession(String username);
    public void createSession(User user);
    public boolean deleteSession(String username);
}
