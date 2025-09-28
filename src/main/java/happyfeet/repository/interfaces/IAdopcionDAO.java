package happyfeet.repository.interfaces;

import happyfeet.model.entities.Adopcion;
import java.sql.SQLException;
import java.util.List;

public interface IAdopcionDAO {
    boolean insertar(Adopcion adopcion) throws SQLException;
    List<Adopcion> listarDisponibles() throws SQLException;
    boolean actualizar(Adopcion adopcion) throws SQLException;
    Adopcion obtenerPorId(int id) throws SQLException;
}