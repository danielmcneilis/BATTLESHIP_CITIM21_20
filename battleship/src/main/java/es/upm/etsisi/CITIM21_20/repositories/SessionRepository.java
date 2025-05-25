package es.upm.etsisi.CITIM21_20.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.CITIM21_20.models.Session;
import es.upm.etsisi.CITIM21_20.models.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionRepository implements ISessonRepository {

    private static SessionRepository instance;
    private List<Session> sessions;
    private static final String rute = "BBDD/RepositorioSesiones";
    private ObjectMapper mapper;


    public static SessionRepository getInstance() {
        if (instance == null) {
            instance = new SessionRepository();
        }
        return instance;
    }

    private SessionRepository() {
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        inicializarFichero();
        loadSesions();
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
        saveSesions(); // me sobreescribe el fichero
    }

    @Override
    public boolean deleteSession(String id) {
        for (Session session : sessions) {
            if (session.getId().equals(id)) {
                sessions.remove(session);
                saveSesions();
                return true;
            }
        }
        return false;
    }

    private void inicializarFichero (){
        try {
            File file = new File(rute);
            if(!file.exists()){
                File carpeta = file.getParentFile();
                if(carpeta != null && !carpeta.exists()){
                    carpeta.mkdirs();
                }
                mapper.writeValue(file, new ArrayList<Session>());
            }
        }catch (IOException e){
            throw new RuntimeException ("CANT CREATE FILE", e);
        }
    }

    public void saveSesions(){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(rute), this.sessions);

        }catch (IOException e){
            throw new RuntimeException("CANT SAVE ON FILE");
        }
    }
    private void loadSesions(){
        try{
            File file = new File(rute);
            TypeReference<ArrayList<Session>> typeref = new TypeReference<ArrayList<Session>>() {};
            this.sessions = mapper.readValue(file, typeref);
        }catch (IOException e){
            throw new RuntimeException("CANT READ FILE", e);
        }
    }

}
