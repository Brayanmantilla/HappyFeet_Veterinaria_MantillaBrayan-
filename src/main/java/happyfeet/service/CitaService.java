package happyfeet.service;

import happyfeet.model.entities.Cita;
import happyfeet.model.enums.CitaEstado;
import happyfeet.repository.DAO.CitasDAO;
import happyfeet.repository.interfaces.ICitasDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CitaService {
    private final ICitasDAO citasDAO;
    private final Connection connection;

    public CitaService() throws SQLException {
        // Obtener conexión con manejo de errores
        this.connection = Conexion.getConexion();

        // Verificar que la conexión no sea null
        if (this.connection == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos. Verifique las credenciales y que MySQL esté ejecutándose.");
        }

        this.citasDAO = new CitasDAO(connection);
    }

    public boolean registrarCita(Cita cita) {
        if (cita == null) return false;
        try {
            return citasDAO.insertar(cita);
        } catch (Exception e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Cita> listarAgenda() {
        try {
            // Verificar conexión antes de usarla
            if (connection == null || connection.isClosed()) {
                System.err.println("Error: Conexión a la base de datos no disponible");
                return List.of();
            }
            return citasDAO.listarAgenda();
        } catch (Exception e) {
            System.err.println("Error al listar agenda: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean actualizarEstado(int idCita, CitaEstado nuevoEstado) {
        if (idCita <= 0 || nuevoEstado == null) return false;
        try {
            return citasDAO.actualizarEstado(idCita, nuevoEstado);
        } catch (Exception e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCita(int id) {
        if (id <= 0) return false;
        try {
            return citasDAO.eliminar(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}