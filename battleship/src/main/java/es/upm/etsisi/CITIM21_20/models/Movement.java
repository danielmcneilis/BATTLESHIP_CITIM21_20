package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IMovimiento;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")

public class Movement implements IMovimiento {

    @Getter
    private int fila;
    @Getter
    private int columna;
    @Getter
    private Long id;
    @Getter
    private long time;
    @Getter
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
