package happyfeet.repository.interfaces;

import happyfeet.model.entities.Mascota;

import java.util.List;

public interface IMascotaDAO {
    boolean insertar(Mascota mascota) throws Exception;
    Mascota obtenerPorId(int id) throws Exception;
    List<Mascota> listarTodos() throws Exception;
    boolean actualizar(Mascota mascota) throws Exception;
    boolean eliminar(int id) throws Exception;
    List<Mascota> buscarPorNombre(String nombre) throws Exception;
    List<Mascota> listarPorDueno(int idDueno) throws Exception;
}
