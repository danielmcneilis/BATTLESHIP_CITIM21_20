package es.upm.etsisi.CITIM21_20.models;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class NameValidator {

    private static final String fileName = "black_list.txt";


    public static boolean isValid(String userName) throws IOException {
        String nombreNormalizado = userName.trim().toLowerCase();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().equals(nombreNormalizado)) {
                return false;
            }
        }
        br.close();
        return true;
    }
}