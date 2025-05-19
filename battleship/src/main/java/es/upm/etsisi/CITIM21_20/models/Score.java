package es.upm.etsisi.CITIM21_20.models;

import es.upm.etsisi.fis.model.IPuntuacion;
import lombok.Getter;

public class Score implements IPuntuacion {

    private long score;
    @Getter
    private long partidaAsociada;


    public Score() {
        this.score = 0;
    }

    @Override
    public long getPuntos() {
        return this.score;
    }

    @Override
    public IPuntuacion clonePuntuacion() { // no estoy seguro si seria algo asi
        IPuntuacion clone = new Score(); //  he usado esto porque no queria crear un nuevo contructor
        clone.setPuntuacion(this.score);
        clone.setPartidaId(this.partidaAsociada);
        return clone;
    }

    @Override
    public void setPuntuacion(long l) {
        this.score = l;
    }

    @Override
    public void setPartidaId(Long aLong) { // asociar la partida con la puntuacion
        this.partidaAsociada = aLong;
    }

}
