package happyfeet.repository.interfaces;

import happyfeet.model.entities.Especie;

import java.util.List;

public interface IEspecieDAO {
    boolean insertar(Especie especie);
    Especie obtenerPorId(int id);
    List<Especie> listarTodas();
    boolean eliminar(int id);
    boolean actualizar(Especie especie);
}
