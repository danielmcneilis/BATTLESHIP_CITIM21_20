package es.upm.etsisi.CITIM21_20.models;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class DateTimeFormatter {

    public static String dateFormater(LocalDateTime localDateTime){
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(formato);
    }
}
