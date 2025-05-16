package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements IJugador {

    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    private List<IPuntuacion> score;
    private List<IMovimiento> attackList;


    public User(String username, String id) {
        if (!this.isValidUserName()){
            throw new RuntimeException("INVALID USERNAME");
        }
        this.username = username;
        this.id = id;
        this.score = new ArrayList<IPuntuacion>();
        this.attackList = new ArrayList<IMovimiento>();
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
        System.out.println("INTRODUCE UNA POSICION (FILA , COLUMNA):");
        Scanner scanner = new Scanner(System.in);
        int fila = scanner.nextInt();
        int columna = scanner.nextInt();
        int[] posicion = {fila, columna};
        return posicion;
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {
        this.attackList.add(iMovimiento);
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
