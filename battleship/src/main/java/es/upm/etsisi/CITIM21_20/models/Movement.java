package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IMovimiento;
import lombok.Getter;

public class Movement implements IMovimiento {

    private int fila;
    private int columna;
    private Long id;
    private long time;
    private boolean impact;

    public Movement() {
        fila = 0;
         columna = 0;
         id = 0L;
         time = 0L;
    }

    @Override
    public IMovimiento cloneMovimiento() {
        IMovimiento movimiento = new Movement();
        movimiento.setColumna(columna);
        movimiento.setFila(fila);
        movimiento.setPartidaId(id);
        movimiento.setTime(time);
        return movimiento;
    }

    @Override
    public void setPartidaId(Long aLong) {
        this.id = aLong;
    }

    @Override
    public void setFila(int i) {
        this.fila = i;
    }

    @Override
    public void setColumna(int i) {
        this.columna = i;
    }

    @Override
    public void setTime(long l) {
        this.time = l;
    }


}
