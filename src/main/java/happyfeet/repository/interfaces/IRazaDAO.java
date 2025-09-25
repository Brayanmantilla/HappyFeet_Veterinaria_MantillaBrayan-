package happyfeet.repository.interfaces;

import happyfeet.model.entities.Especie;
import happyfeet.model.entities.Raza;

import java.util.List;

public interface IRazaDAO {
    boolean insertar(Raza raza);
    List<Raza> listarPorEspecie(int especieId);
}
