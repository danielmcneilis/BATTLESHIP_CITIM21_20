package es.upm.etsisi.CITIM21_20.repositories;

import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

import java.util.ArrayList;
import java.util.List;

public class SessionRepository implements ISessonRepository{

    private static SessionRepository instance;
    private List<Session> sessions;


    public static SessionRepository getInstance(){
        if(instance == null){
            instance = new SessionRepository();
        }
        return instance;
    }

    private SessionRepository(){
        sessions = new ArrayList<Session>();
    }

    @Override
    public Session getSession(String username) {
        return null;
    }

    @Override
    public void createSession(User user) {
    }

    @Override
    public boolean deleteSession(String username) {
        return false;
    }
}
