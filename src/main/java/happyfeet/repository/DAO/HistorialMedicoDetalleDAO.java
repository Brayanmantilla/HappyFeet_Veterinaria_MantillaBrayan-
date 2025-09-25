package happyfeet.repository.DAO;

import happyfeet.model.entities.HistorialMedico;
import happyfeet.model.entities.HistorialMedicoDetalle;
import happyfeet.model.entities.Inventario;
import happyfeet.model.entities.Servicio;
import happyfeet.repository.interfaces.IHistorialMedicoDAO;
import happyfeet.repository.interfaces.IHistorialMedicoDetalle;
import happyfeet.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDetalleDAO implements IHistorialMedicoDetalle {

    private final Connection connection;
    private final HistorialMedicoDAO historialDAO;
    private final InventarioDAO inventarioDAO;
    private final ServiciosDAO serviciosDAO;

    // Constructor recibe la conexión
    public HistorialMedicoDetalleDAO(Connection connection) {
        this.connection = connection;
        this.historialDAO = new HistorialMedicoDAO(connection);
        this.inventarioDAO = new InventarioDAO(connection);
        this.serviciosDAO = new ServiciosDAO(connection); // ✅ ahora pasamos connection
    }

    @Override
    public boolean insertarDetalle(HistorialMedicoDetalle detalle) throws Exception {
        String sql = "INSERT INTO historial_medico_detalle " +
                "(historial_medico_id, inventario_id, servicio_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalle.getHistorialMedico().getIdHistorialMedico());
            ps.setInt(2, detalle.getInventario().getIdInventario());
            ps.setInt(3, detalle.getServicio().getIdServicio());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        detalle.setIdHistorialMedicoDetalle(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<HistorialMedicoDetalle> listarPorHistorial(int idHistorial) throws Exception {
        List<HistorialMedicoDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico_detalle WHERE historial_medico_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHistorial);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    // =================== MÉTODO AUXILIAR ===================
    private HistorialMedicoDetalle mapResultSet(ResultSet rs) throws Exception {
        HistorialMedico historial = historialDAO.obtenerPorId(rs.getInt("historial_medico_id"));
        Inventario inventario = inventarioDAO.obtenerPorId(rs.getInt("inventario_id"));
        Servicio servicio = serviciosDAO.obtenerPorId(rs.getInt("servicio_id"));

        return new HistorialMedicoDetalle(
                rs.getInt("id_historial_medico_detalle"),
                historial,
                inventario,
                servicio
        );
    }
}
