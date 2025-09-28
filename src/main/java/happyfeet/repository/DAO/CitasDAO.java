package happyfeet.repository.DAO;

import happyfeet.model.entities.Cita;
import happyfeet.model.entities.Empleado;
import happyfeet.model.entities.Mascota;
import happyfeet.model.enums.CitaEstado;
import happyfeet.repository.interfaces.ICitasDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitasDAO implements ICitasDAO {

    private final Connection connection;
    private final MascotaDAO mascotaDAO;
    private final EmpleadosDAO empleadoDAO;

    // Constructor recibe la conexión y la pasa a los DAOs
    public CitasDAO(Connection connection) {
        this.connection = connection;
        this.mascotaDAO = new MascotaDAO(connection);
        this.empleadoDAO = new EmpleadosDAO();
    }

    @Override
    public boolean insertar(Cita cita) throws Exception {
        String sql = """
            INSERT INTO citas
            (mascota_id, empleado_id, estado_id, fecha_hora, motivo)
            VALUES (?,?,?,?,?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cita.getMascota().getIdMascota());
            ps.setInt(2, cita.getEmpleado().getIdEmpleado());
            ps.setInt(3, cita.getCitaEstado().getId()); // Usamos el ID del enum
            ps.setDate(4, Date.valueOf(cita.getFechaHoraCita()));
            ps.setString(5, cita.getMotivoCita());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        cita.setIdCita(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Cita obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM citas WHERE id=?";
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

    @Override
    public List<Cita> listarAgenda() throws Exception {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha_hora ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(int idCita, CitaEstado nuevoEstado) throws Exception {
        String sql = "UPDATE citas SET estado_id=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado.getId());
            ps.setInt(2, idCita);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        // 1️⃣ Eliminar primero los servicios asociados a la cita
        String sqlServicios = "DELETE FROM citas_servicios WHERE cita_id = ?";
        try (PreparedStatement psServicios = connection.prepareStatement(sqlServicios)) {
            psServicios.setInt(1, id);
            psServicios.executeUpdate();
        }

        // 2️⃣ Luego eliminar la cita
        String sqlCita = "DELETE FROM citas WHERE id=?";
        try (PreparedStatement psCita = connection.prepareStatement(sqlCita)) {
            psCita.setInt(1, id);
            return psCita.executeUpdate() > 0;
        }
    }

    // =================== MÉTODO AUXILIAR ===================
    private Cita mapResultSet(ResultSet rs) throws Exception {
        Mascota mascota = mascotaDAO.obtenerPorId(rs.getInt("mascota_id"));
        Empleado empleado = empleadoDAO.obtenerPorId(rs.getInt("empleado_id"));
        CitaEstado estado = CitaEstado.fromId(rs.getInt("estado_id")); // Mapear ID a enum
        LocalDate fecha = rs.getDate("fecha_hora").toLocalDate();

        return new Cita(
                rs.getInt("id"),
                mascota,
                empleado,
                estado,
                fecha,
                rs.getString("motivo")
        );
    }
}