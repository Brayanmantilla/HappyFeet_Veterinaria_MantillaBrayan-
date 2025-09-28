package happyfeet.service;

import happyfeet.model.entities.Cita;
import happyfeet.model.enums.CitaEstado;
import happyfeet.repository.DAO.CitasDAO;
import happyfeet.repository.interfaces.ICitasDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.util.List;

public class CitaService {
    private final ICitasDAO citasDAO;
    private final Connection connection;

    public CitaService() {
        this.connection = Conexion.getConexion();
        this.citasDAO = new CitasDAO(connection);
    }

    public boolean registrarCita(Cita cita) {
        if (cita == null) return false;
        try {
            return citasDAO.insertar(cita);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cita> listarAgenda() {
        try {
            return citasDAO.listarAgenda();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean actualizarEstado(int idCita, CitaEstado nuevoEstado) {
        if (idCita <= 0 || nuevoEstado == null) return false;
        try {
            return citasDAO.actualizarEstado(idCita, nuevoEstado);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCita(int id) {
        if (id <= 0) return false;
        try {
            return citasDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
