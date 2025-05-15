package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IPuntuacion;

public class Score implements IPuntuacion {

    private double score;


    public Score() {
        this.score = 0;
    }

    @Override
    public long getPuntos() {
        return 0;
    }

    @Override
    public IPuntuacion clonePuntuacion() {
        return null;
    }

    @Override
    public void setPuntuacion(long l) {

    }

    @Override
    public void setPartidaId(Long aLong) {

    }
}
