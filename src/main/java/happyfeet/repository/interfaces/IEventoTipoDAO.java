package happyfeet.repository.interfaces;

import happyfeet.model.entities.EventoTipo;

import java.sql.SQLException;
import java.util.List;

public interface IEventoTipoDAO {
    List<EventoTipo> listarTodos() throws SQLException;
    EventoTipo obtenerPorId(int id) throws SQLException;
}
