package happyfeet.repository.DAO;

import happyfeet.model.entities.Cita;
import happyfeet.model.entities.CitaServicio;
import happyfeet.model.entities.Servicio;
import happyfeet.repository.interfaces.ICitaServicio;
import happyfeet.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaServicioDAO implements ICitaServicio {
    private final CitasDAO citaDAO;
    private final ServiciosDAO servicioDAO;

    // Constructor recibe la conexión
    public CitaServicioDAO(Connection connection) {
        this.citaDAO = new CitasDAO(connection);
        this.servicioDAO = new ServiciosDAO(connection);
    }

    @Override
    public boolean insertar(CitaServicio citaServicio) throws Exception {
        String sql = "INSERT INTO citas_servicios (cita_id, servicio_id, cantidad, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, citaServicio.getCita().getIdCita());
            ps.setInt(2, citaServicio.getServicios().getIdServicio());
            ps.setInt(3, citaServicio.getCantidad());
            ps.setDouble(4, citaServicio.getPrecioCita());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        citaServicio.setIdCitaServicio(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar citaServicio: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<CitaServicio> listarPorCita(int idCita) throws Exception {
        List<CitaServicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas_servicios WHERE cita_id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita cita = citaDAO.obtenerPorId(rs.getInt("cita_id"));
                    Servicio servicio = servicioDAO.obtenerPorId(rs.getInt("servicio_id"));

                    CitaServicio cs = new CitaServicio(
                            rs.getInt("id"),
                            cita,
                            servicio,
                            rs.getInt("cantidad"),
                            rs.getDouble("precio")
                    );
                    lista.add(cs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar citaServicios por cita: " + e.getMessage());
        }
        return lista;
    }
}
