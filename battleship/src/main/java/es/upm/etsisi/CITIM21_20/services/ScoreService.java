package es.upm.etsisi.CITIM21_20.services;

import es.upm.etsisi.CITIM21_20.models.Score;
import es.upm.etsisi.CITIM21_20.models.User;
import es.upm.etsisi.CITIM21_20.repositories.IUserRepository;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScoreService implements IScoreService{
    private IUserRepository userList;

    public ScoreService(IUserRepository userList) {
        this.userList = userList;
    }

    @Override
    public List<IPuntuacion> getScore(User usuario) {
        List<IPuntuacion> listapuntuaciones = new ArrayList<>();
        if (usuario.isAdmin()) {
            listapuntuaciones = adminScore();

        } else {
            listapuntuaciones = userScore(usuario.getNombre());

        }
        return listapuntuaciones;
    }

    private List<IPuntuacion> adminScore() {
        // el admin puede ver todas las puntuaciones, pero si puede ver todas, para que le paso un nombre como parametro
        List<IPuntuacion> listapuntuaciones = new ArrayList<>();
        for (User user : userList.valores()) {
            for (IPuntuacion puntuacion : user.getPuntuaciones()) {
                listapuntuaciones.add(puntuacion);
            }
        }

        return listapuntuaciones;
    }

    private List<IPuntuacion> userScore(String nombreusuario) { // ver las 10 mejores partidas suyas
        User usuario = userList.getUserByUsername(nombreusuario); // necesito sacarlo del hashmap
        List<IPuntuacion> top10 = new ArrayList<>();
        List<IPuntuacion> nueva = CloneList(usuario.getPuntuaciones());
        if (usuario == null) {
            System.out.println("USER NOT FOUND");
            return top10;
        }

        while (!nueva.isEmpty() && top10.size() < 10) {
            Iterator<IPuntuacion> it = nueva.iterator();
            IPuntuacion max = it.next(); // asumimos que hay al menos uno

            while (it.hasNext()) {
                IPuntuacion actual = it.next();
                if ((actual.getPuntos() > max.getPuntos()) && actual.getPuntos() != 0) {
                    max = actual;
                }
            }
            top10.add(max);
            nueva.remove(max);
        }
        return top10; // el problema con esto es que que pasa si me lo devuelven vacio, tenemos que implementar manejor de excepciones?
    }


    private List<IPuntuacion> CloneList(List<IPuntuacion> original) { // para no borrar contenido de la original
        List<IPuntuacion> clone = new ArrayList<>();
        Iterator<IPuntuacion> it = original.iterator();
        while (it.hasNext()) {
            IPuntuacion x = it.next();
            clone.add(x);
        }
        return clone;
    }
}
