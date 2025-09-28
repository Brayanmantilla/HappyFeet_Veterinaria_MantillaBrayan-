package happyfeet.repository.DAO;

import happyfeet.model.entities.ClubMascota;
import happyfeet.model.entities.Dueno;
import happyfeet.repository.interfaces.IClubMascotaDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClubMascotaDAO implements IClubMascotaDAO {
    private final Connection connection;
    private final DuenoDAO duenoDAO;

    public ClubMascotaDAO() throws SQLException {
        this.connection = Conexion.getConexion();
        this.duenoDAO = new DuenoDAO();
    }

    @Override
    public boolean insertar(ClubMascota club) throws SQLException {
        String sql = "INSERT INTO club_mascotas (dueno_id, fecha_inscripcion, puntos_acumulados, nivel, estado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, club.getDueno().getIdDueno());
            ps.setDate(2, Date.valueOf(club.getFechaInscripcion()));
            ps.setInt(3, club.getPuntosAcumulados());
            ps.setString(4, club.getNivel());
            ps.setString(5, club.getEstado());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        club.setIdClub(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ClubMascota obtenerPorDueno(int duenoId) throws SQLException {
        String sql = "SELECT * FROM club_mascotas WHERE dueno_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, duenoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(ClubMascota club) throws SQLException {
        String sql = "UPDATE club_mascotas SET puntos_acumulados = ?, nivel = ?, estado = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, club.getPuntosAcumulados());
            ps.setString(2, club.getNivel());
            ps.setString(3, club.getEstado());
            ps.setInt(4, club.getIdClub());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<ClubMascota> listarTodos() throws SQLException {
        String sql = "SELECT * FROM club_mascotas WHERE estado = 'Activo' ORDER BY puntos_acumulados DESC";
        List<ClubMascota> lista = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    private ClubMascota mapResultSet(ResultSet rs) throws SQLException {
        Dueno dueno = duenoDAO.obtenerPorId(rs.getInt("dueno_id"));

        return new ClubMascota(
                rs.getInt("id"),
                dueno,
                rs.getDate("fecha_inscripcion").toLocalDate(),
                rs.getInt("puntos_acumulados"),
                rs.getString("nivel"),
                rs.getString("estado")
        );
    }
}