package happyfeet.service;

import happyfeet.model.entities.HistorialMedico;
import happyfeet.repository.DAO.HistorialMedicoDAO;
import happyfeet.repository.interfaces.IHistorialMedicoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HistorialMedicoService {

    private final IHistorialMedicoDAO historialMedicoDAO;
    private final Connection connection;

    public HistorialMedicoService() {
        // Obtiene la conexión directamente
        this.connection = Conexion.getConexion();
        this.historialMedicoDAO = new HistorialMedicoDAO(connection);
    }

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
