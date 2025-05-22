package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

import java.util.ArrayList;

public class TestSessionRepository implements ISessonRepository{
    ArrayList<Session> sessions;

    public TestSessionRepository(){
        this.sessions = new ArrayList<>();
    }

    @Override
    public Session getSession(String id) {
        for (Session session : sessions) {
            if (session.getId().equals(id)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public void createSession(User user, String id) {
        Session session = new Session(user, id);
        sessions.add(session);
    }

    @Override
    public boolean deleteSession(String id) {
        for (Session session : sessions) {
            if (session.getId().equals(id)) {
                sessions.remove(session);
                return true;
            }
        }
        return false;
    }
}
