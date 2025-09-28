package happyfeet.repository.DAO;

import happyfeet.model.entities.Empleado;
import happyfeet.model.entities.JornadaVacunacion;
import happyfeet.repository.interfaces.IJornadaVacunacionDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JornadaVacunacionDAO implements IJornadaVacunacionDAO {
    private final Connection connection;
    private final EmpleadosDAO empleadosDAO;

    public JornadaVacunacionDAO() throws SQLException {
        this.connection = Conexion.getConexion();
        this.empleadosDAO = new EmpleadosDAO();
    }

    @Override
    public boolean insertar(JornadaVacunacion jornada) throws SQLException {
        String sql = "INSERT INTO jornadas_vacunacion (nombre_jornada, fecha_jornada, lugar, " +
                "descripcion, empleado_responsable_id, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, jornada.getNombreJornada());
            ps.setDate(2, Date.valueOf(jornada.getFechaJornada()));
            ps.setString(3, jornada.getLugar());
            ps.setString(4, jornada.getDescripcion());

            if (jornada.getEmpleadoResponsable() != null) {
                ps.setInt(5, jornada.getEmpleadoResponsable().getIdEmpleado());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setString(6, jornada.getEstado());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        jornada.setIdJornada(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<JornadaVacunacion> listarTodas() throws SQLException {
        String sql = "SELECT * FROM jornadas_vacunacion ORDER BY fecha_jornada DESC";
        List<JornadaVacunacion> lista = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(JornadaVacunacion jornada) throws SQLException {
        String sql = "UPDATE jornadas_vacunacion SET nombre_jornada = ?, fecha_jornada = ?, " +
                "lugar = ?, descripcion = ?, estado = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, jornada.getNombreJornada());
            ps.setDate(2, Date.valueOf(jornada.getFechaJornada()));
            ps.setString(3, jornada.getLugar());
            ps.setString(4, jornada.getDescripcion());
            ps.setString(5, jornada.getEstado());
            ps.setInt(6, jornada.getIdJornada());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public JornadaVacunacion obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM jornadas_vacunacion WHERE id = ?";
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

    private JornadaVacunacion mapResultSet(ResultSet rs) throws SQLException {
        Empleado empleado = null;
        int empleadoId = rs.getInt("empleado_responsable_id");
        if (!rs.wasNull()) {
            empleado = empleadosDAO.obtenerPorId(empleadoId);
        }

        return new JornadaVacunacion(
                rs.getInt("id"),
                rs.getString("nombre_jornada"),
                rs.getDate("fecha_jornada").toLocalDate(),
                rs.getString("lugar"),
                rs.getString("descripcion"),
                empleado,
                rs.getString("estado")
        );
    }
}