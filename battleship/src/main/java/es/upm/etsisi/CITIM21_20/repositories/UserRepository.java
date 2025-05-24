package es.upm.etsisi.CITIM21_20.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.CITIM21_20.models.User;

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
        this.mapper = new ObjectMapper();
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
            saveUsers(userList);
            return user;
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for (Map.Entry<String, User> entry : userList.entrySet()) {
            User user = entry.getValue();
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        this.userList.remove(id);
        saveUsers(userList);
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

    private void saveUsers(HashMap <String, User> mapa){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(rute), mapa);

        }catch (IOException e){
            throw new RuntimeException("CANT SAVE ON FILE");
        }
    }

    private void loadUsers (){
        try{
            File file = new File(rute);
            TypeReference<HashMap<String, User>> typeref = new TypeReference<HashMap<String, User>>() {};
            this.userList = mapper.readValue(file, typeref);
        }catch (IOException e){
            throw new RuntimeException("CANT READ FILE", e);
        }
    }


}
