package happyfeet.repository.interfaces;

import happyfeet.model.entities.HistorialMedico;

import java.sql.SQLException;
import java.util.List;

public interface IHistorialMedicoDAO {
    boolean insertarEvento(HistorialMedico historial) throws SQLException;
    List<HistorialMedico> listarPorMascota(int idMascota) throws SQLException;
    HistorialMedico obtenerPorId(int idHistorial) throws SQLException;
}
