package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

import java.util.ArrayList;
import java.util.List;

public class SessionRepository implements ISessonRepository {

    private static SessionRepository instance;
    private List<Session> sessions;


    public static SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository(new ArrayList<Session>());
        }
        return instance;
    }

    private SessionRepository(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public Session getSession(String id) {
        for (Session session : sessions) {
            if (session.getUser().getId().equals(id)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public void createSession(User user, String email) {
        Session session = new Session(user, email);
        sessions.add(session);
    }

    @Override
    public boolean deleteSession(String id) {
        for (Session session : sessions) {
            if (session.getUser().getId().equals(id)) {
                sessions.remove(session);
                return true;
            }
        }
        return false;
    }
}
