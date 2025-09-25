package happyfeet.repository.DAO;

import happyfeet.model.entities.EventoTipo;
import happyfeet.model.entities.HistorialMedico;
import happyfeet.model.entities.Mascota;
import happyfeet.repository.interfaces.IHistorialMedicoDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO implements IHistorialMedicoDAO {
    private final MascotaDAO mascotaDAO;
    private final EventoTipoDAO eventoTipoDAO;
    private final Connection connection;

    public HistorialMedicoDAO(Connection connection) {
        this.connection = connection;
        // Se asume que MascotaDAO y EventoTipoDAO están implementados correctamente
        this.mascotaDAO = new MascotaDAO(connection);
        this.eventoTipoDAO = new EventoTipoDAO(connection);
    }

    @Override
    public boolean insertarEvento(HistorialMedico historial) throws SQLException {
        String sql = "INSERT INTO historial_medico " +
                "(mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, historial.getMascota().getIdMascota());
            ps.setDate(2, Date.valueOf(historial.getFechaEvento()));
            ps.setInt(3, historial.getEventoTipo().getIdEventoTipo());
            ps.setString(4, historial.getDescripcion());
            ps.setString(5, historial.getDiagnostico());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        historial.setIdHistorialMedico(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<HistorialMedico> listarPorMascota(int idMascota) throws SQLException {
        List<HistorialMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico WHERE mascota_id = ? ORDER BY fecha_evento DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idMascota);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }

        return lista;
    }

    @Override
    public HistorialMedico obtenerPorId(int idHistorial) throws SQLException {
        String sql = "SELECT * FROM historial_medico WHERE id_historial_medico = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHistorial);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }

        return null;
    }

    // =================== MÉTODO AUXILIAR ===================
    /**
     * Mapea un ResultSet a un objeto HistorialMedico,
     * obteniendo las entidades Mascota y EventoTipo
     * a través de sus respectivos DAOs.
     */
    private HistorialMedico mapResultSet(ResultSet rs) throws SQLException {
        // Se asume que mascotaDAO.obtenerPorId y eventoTipoDAO.obtenerPorId
        // están implementados y funcionan correctamente.
        Mascota mascota = mascotaDAO.obtenerPorId(rs.getInt("mascota_id"));
        EventoTipo eventoTipo = eventoTipoDAO.obtenerPorId(rs.getInt("evento_tipo_id"));

        // Manejo de fecha nula si fuera el caso, aunque 'fecha_evento' debería ser NOT NULL.
        Date sqlDate = rs.getDate("fecha_evento");
        LocalDate fecha = (sqlDate != null) ? sqlDate.toLocalDate() : null;

        return new HistorialMedico(
                rs.getInt("id_historial_medico"),
                mascota,
                fecha,
                eventoTipo,
                rs.getString("descripcion"),
                rs.getString("diagnostico")
        );
    }
}
