package es.upm.etsisi.CITIM21_20.repositories;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import es.upm.etsisi.CITIM21_20.models.Movement;
import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserRepository implements IUserRepository {

    private static UserRepository instance;
    private static final String rute = "BBDD/RepositorioUsuarios";
    private ObjectMapper mapper;
    private HashMap<String, User> userList;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        this.mapper.addMixIn(IPuntuacion.class, Score.class);
        inicializarFichero();
        loadUsers();
    }

    @Override
    public User getUser(String id) {
        return userList.get(id);
    }

    @Override
    public User createUser(String username, String id) throws IOException {
        User user = new User(username, id);
        if (user.isValidUserName()) {
            userList.put(id, user);
            saveUsers();
            return user;
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for (Map.Entry<String, User> entry : userList.entrySet()) {
            User user = entry.getValue();
            if (user.getNombre().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        this.userList.remove(id);
        saveUsers();
    }

    public ArrayList<User> valores() {
        ArrayList<User> lista = new ArrayList<>();
        for (User user : userList.values()) {
            lista.add(user);
        }
        return lista;

    }

    private void inicializarFichero (){
        try {
            File file = new File(rute);
            if(!file.exists()){
                File carpeta = file.getParentFile();
                if(carpeta != null && !carpeta.exists()){
                    carpeta.mkdirs();
                }
                mapper.writeValue(file, new HashMap<String, User>());
            }
        }catch (IOException e){
            throw new RuntimeException ("CANT CREATE FILE", e);
        }
    }



    public void saveUsers(){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(rute), this.userList);

        }catch (IOException e){
            throw new RuntimeException("CANT SAVE ON FILE");
        }
    }

    // carga a tu atributo userlist
    public void loadUsers (){
        try{
            File file = new File(rute);
            TypeReference<HashMap<String, User>> typeref = new TypeReference<HashMap<String, User>>() {};
            this.userList = mapper.readValue(file, typeref);
        }catch (IOException e){
            throw new RuntimeException("CANT READ FILE", e);
        }
    }


}
