package happyfeet.service;

import happyfeet.model.entities.EventoTipo;
import happyfeet.model.entities.HistorialMedico;
import happyfeet.repository.DAO.EventoTipoDAO;
import happyfeet.repository.DAO.HistorialMedicoDAO;
import happyfeet.repository.interfaces.IHistorialMedicoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HistorialMedicoService {

    private final HistorialMedicoDAO historialMedicoDAO;
    private final EventoTipoDAO eventoTipoDAO;
    private final Connection connection;

    public HistorialMedicoService() {
        this.connection = Conexion.getConexion();
        this.historialMedicoDAO = new HistorialMedicoDAO(connection);
        this.eventoTipoDAO = new EventoTipoDAO(connection);
    }

    // ==================== EVENTOS ====================
    public boolean registrarEvento(HistorialMedico historial) {
        if (historial == null ||
                historial.getMascota() == null ||
                historial.getEventoTipo() == null ||
                historial.getFechaEvento() == null) {
            return false;
        }
        try {
            return historialMedicoDAO.insertarEvento(historial);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HistorialMedico> listarEventosPorMascota(int idMascota) {
        try {
            return historialMedicoDAO.listarPorMascota(idMascota);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public HistorialMedico obtenerEventoPorId(int idHistorial) {
        try {
            return historialMedicoDAO.obtenerPorId(idHistorial);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== TIPOS DE EVENTO ====================
    public List<EventoTipo> listarTiposEventos() {
        try {
            return eventoTipoDAO.listarTodos(); // Retorna todos los tipos de evento
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public EventoTipo obtenerEventoTipoPorId(int id) {
        try {
            return eventoTipoDAO.obtenerPorId(id); // Retorna un tipo de evento por ID
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== CONEXIÓN ====================
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
