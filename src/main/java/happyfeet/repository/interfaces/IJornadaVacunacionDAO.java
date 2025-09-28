package happyfeet.repository.interfaces;

import happyfeet.model.entities.JornadaVacunacion;
import java.sql.SQLException;
import java.util.List;

public interface IJornadaVacunacionDAO {
    boolean insertar(JornadaVacunacion jornada) throws SQLException;
    List<JornadaVacunacion> listarTodas() throws SQLException;
    boolean actualizar(JornadaVacunacion jornada) throws SQLException;
    JornadaVacunacion obtenerPorId(int id) throws SQLException;
}