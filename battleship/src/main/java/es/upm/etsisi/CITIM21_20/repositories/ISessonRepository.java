package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

public interface ISessonRepository {

    public Session getSession(String id);
    public void createSession(User user, String email);
    public boolean deleteSession(String id);
}
