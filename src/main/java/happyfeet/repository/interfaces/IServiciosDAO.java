package happyfeet.repository.interfaces;

import happyfeet.model.entities.Especie;
import happyfeet.model.entities.Servicio;

import java.util.List;

public interface IServiciosDAO {
    boolean insertar(Servicio servicio);
    Servicio obtenerPorId(int id);
    List<Servicio> listarTodas();
    boolean eliminar(int id);
    boolean actualizar(Servicio servicio);
}
