package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements IJugador {

    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    @Getter
    @Setter
    private boolean isAdmin;
    private List<IPuntuacion> score;
    private List<IMovimiento> attackList;

    public User (){
        this.score = new ArrayList<IPuntuacion>();
        this.attackList = new ArrayList<IMovimiento>();
    }

    public User(String username, String id) throws IOException {
        this.username = username;
        this.id = id;
        this.score = new ArrayList<IPuntuacion>();
        this.attackList = new ArrayList<IMovimiento>();
        this.isAdmin = false;
        if (!this.isValidUserName()) {
            throw new RuntimeException("INVALID USERNAME");
        }
    }

    public boolean isValidUserName() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/es/upm/etsisi/CITIM21_20/black_list.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().equals(this.username)) {
                reader.close();
                return false;
            }
        }

        reader.close();

        if (this.username != null)
            return this.username.length() >= 3 && this.username.length() <= 10;

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
        int fila;
        int columna;
        do {
            fila = scanner.nextInt();
            columna = scanner.nextInt();
            if (fila < 0 || fila > 9 || columna < 0 || columna > 9) {
                System.out.println("INVALID POSITION");
            }
        } while (fila < 0 || fila > 9 || columna < 0 || columna > 9);
        int[] posicion = {fila, columna};
        return posicion;
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {
        this.attackList.add(iMovimiento);
    }

    @Override
    public String getNombre() {
        return this.username;
    }

    @Override
    public void addPuntuacion(IPuntuacion iPuntuacion) {
        this.score.add(iPuntuacion);
    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return attackList;
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return score;
    }
}
