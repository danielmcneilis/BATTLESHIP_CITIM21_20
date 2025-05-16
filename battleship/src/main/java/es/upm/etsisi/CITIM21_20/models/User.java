package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class User implements IJugador {

    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    private List<IPuntuacion> score;


    public User(String username, String id) {
        if (!this.isValidUserName()){
            throw new RuntimeException("INVALID USERNAME");
        }
        this.username = username;
        this.id = id;
        this.score = new ArrayList<IPuntuacion>();
    }

    public boolean isValidUserName(){
        return true;
    }

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int i) {
        return false;
    }

    @Override
    public int[] realizaTurno(char[][] chars) {
        return new int[];
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {

    }

    @Override
    public String getNombre() {
        return "";
    }

    @Override
    public void addPuntuacion(IPuntuacion iPuntuacion) {

    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return List.of();
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return List.of();
    }
}
