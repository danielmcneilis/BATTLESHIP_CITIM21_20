package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.List;

public interface IScoreService {
    List<IPuntuacion> getScore(User usuario);
}
