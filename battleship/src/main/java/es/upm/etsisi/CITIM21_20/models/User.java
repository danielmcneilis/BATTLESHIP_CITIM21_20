package es.upm.etsisi.CITIM21_20.models;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    private Score score;


    public User(String username, String id, Score score) throws IOException {
        if (!this.isValidUserName()){
            throw new RuntimeException("INVALID USERNAME");
        }
        this.username = username;
        this.id = id;
        this.score = score;
    }

    public boolean isValidUserName() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("black_list.txt"));
        String line;

        while((line = reader.readLine()) != null){
            if(line.trim().equals(this.username)){
                reader.close();
                return false;
            }
        }

        reader.close();
        // No puede estar el nombre en el fichero black_list.txt

        // Tiene que tener entre 3 y 10 caracteres
        if(this.username != null)
            return this.username.length() >= 3 && this.username.length() <= 10;

        return true;
    }

}
