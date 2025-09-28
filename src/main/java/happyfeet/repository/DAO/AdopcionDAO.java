package happyfeet.repository.DAO;

import happyfeet.model.entities.Adopcion;
import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Mascota;
import happyfeet.repository.interfaces.IAdopcionDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdopcionDAO implements IAdopcionDAO {
    private final Connection connection;
    private final MascotaDAO mascotaDAO;
    private final DuenoDAO duenoDAO;

    public AdopcionDAO() throws SQLException {
        this.connection = Conexion.getConexion();
        this.mascotaDAO = new MascotaDAO(connection);
        this.duenoDAO = new DuenoDAO();
    }

    @Override
    public boolean insertar(Adopcion adopcion) throws SQLException {
        String sql = "INSERT INTO adopciones (mascota_id, dueno_anterior_id, dueno_nuevo_id, " +
                "fecha_disponible, fecha_adopcion, estado, motivo_adopcion, observaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, adopcion.getMascota().getIdMascota());
            ps.setInt(2, adopcion.getDuenoAnterior().getIdDueno());

            if (adopcion.getDuenoNuevo() != null) {
                ps.setInt(3, adopcion.getDuenoNuevo().getIdDueno());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setDate(4, Date.valueOf(adopcion.getFechaDisponible()));

            if (adopcion.getFechaAdopcion() != null) {
                ps.setDate(5, Date.valueOf(adopcion.getFechaAdopcion()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setString(6, adopcion.getEstado());
            ps.setString(7, adopcion.getMotivoAdopcion());
            ps.setString(8, adopcion.getObservaciones());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        adopcion.setIdAdopcion(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Adopcion> listarDisponibles() throws SQLException {
        String sql = "SELECT * FROM adopciones WHERE estado = 'Disponible'";
        List<Adopcion> lista = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(Adopcion adopcion) throws SQLException {
        String sql = "UPDATE adopciones SET dueno_nuevo_id = ?, fecha_adopcion = ?, estado = ?, observaciones = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (adopcion.getDuenoNuevo() != null) {
                ps.setInt(1, adopcion.getDuenoNuevo().getIdDueno());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            if (adopcion.getFechaAdopcion() != null) {
                ps.setDate(2, Date.valueOf(adopcion.getFechaAdopcion()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setString(3, adopcion.getEstado());
            ps.setString(4, adopcion.getObservaciones());
            ps.setInt(5, adopcion.getIdAdopcion());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Adopcion obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM adopciones WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    private Adopcion mapResultSet(ResultSet rs) throws SQLException {
        Mascota mascota = mascotaDAO.obtenerPorId(rs.getInt("mascota_id"));
        Dueno duenoAnterior = duenoDAO.obtenerPorId(rs.getInt("dueno_anterior_id"));

        Dueno duenoNuevo = null;
        int duenoNuevoId = rs.getInt("dueno_nuevo_id");
        if (!rs.wasNull()) {
            duenoNuevo = duenoDAO.obtenerPorId(duenoNuevoId);
        }

        LocalDate fechaDisponible = rs.getDate("fecha_disponible").toLocalDate();
        LocalDate fechaAdopcion = null;
        if (rs.getDate("fecha_adopcion") != null) {
            fechaAdopcion = rs.getDate("fecha_adopcion").toLocalDate();
        }

        return new Adopcion(
                rs.getInt("id"),
                mascota,
                duenoAnterior,
                duenoNuevo,
                fechaDisponible,
                fechaAdopcion,
                rs.getString("estado"),
                rs.getString("motivo_adopcion"),
                rs.getString("observaciones")
        );
    }
}