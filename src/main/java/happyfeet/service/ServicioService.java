package happyfeet.service;

import happyfeet.model.entities.Servicio;
import happyfeet.repository.DAO.ServiciosDAO;
import happyfeet.repository.interfaces.IServiciosDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.util.List;

public class ServicioService {
    private final IServiciosDAO serviciosDAO;

    public ServicioService() {
        Connection connection = Conexion.getConexion();
        this.serviciosDAO = new ServiciosDAO(connection);
    }

    public boolean registrarServicio(Servicio servicio) {
        if (servicio == null || servicio.getNombreServicio() == null || servicio.getNombreServicio().trim().isEmpty()) {
            return false;
        }
        return serviciosDAO.insertar(servicio);
    }

    public Servicio obtenerServicioPorId(int id) {
        if (id <= 0) return null;
        return serviciosDAO.obtenerPorId(id);
    }

    public List<Servicio> listarServicios() {
        return serviciosDAO.listarTodas();
    }

    public boolean actualizarServicio(Servicio servicio) {
        if (servicio == null || servicio.getIdServicio() <= 0) return false;
        return serviciosDAO.actualizar(servicio);
    }

    public boolean eliminarServicio(int id) {
        if (id <= 0) return false;
        return serviciosDAO.eliminar(id);
    }
}