package happyfeet.service;

import happyfeet.model.entities.Adopcion;
import happyfeet.repository.DAO.AdopcionDAO;
import happyfeet.repository.interfaces.IAdopcionDAO;

import java.sql.SQLException;
import java.util.List;

public class AdopcionService {
    private final IAdopcionDAO adopcionDAO;

    public AdopcionService() throws SQLException {
        this.adopcionDAO = new AdopcionDAO();
    }

    public boolean registrarParaAdopcion(Adopcion adopcion) {
        if (adopcion == null) {
            throw new IllegalArgumentException("La adopción no puede ser nula");
        }
        if (adopcion.getMascota() == null) {
            throw new IllegalArgumentException("Debe especificar una mascota");
        }
        if (adopcion.getDuenoAnterior() == null) {
            throw new IllegalArgumentException("Debe especificar el dueño anterior");
        }
        if (adopcion.getMotivoAdopcion() == null || adopcion.getMotivoAdopcion().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de adopción es obligatorio");
        }

        try {
            return adopcionDAO.insertar(adopcion);
        } catch (SQLException e) {
            System.out.println("Error al registrar adopción: " + e.getMessage());
            return false;
        }
    }

    public List<Adopcion> listarMascotasDisponibles() {
        try {
            return adopcionDAO.listarDisponibles();
        } catch (SQLException e) {
            System.out.println("Error al listar adopciones: " + e.getMessage());
            return List.of();
        }
    }

    public boolean procesarAdopcion(Adopcion adopcion) {
        if (adopcion == null || adopcion.getIdAdopcion() <= 0) {
            throw new IllegalArgumentException("Adopción inválida para actualización");
        }

        try {
            return adopcionDAO.actualizar(adopcion);
        } catch (SQLException e) {
            System.out.println("Error al procesar adopción: " + e.getMessage());
            return false;
        }
    }

    public Adopcion obtenerAdopcionPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        try {
            return adopcionDAO.obtenerPorId(id);
        } catch (SQLException e) {
            System.out.println("Error al obtener adopción: " + e.getMessage());
            return null;
        }
    }
}