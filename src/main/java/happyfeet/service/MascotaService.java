package happyfeet.service;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.repository.DAO.MascotaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MascotaService {

    private final MascotaDAO mascotaDAO;

    public MascotaService() throws SQLException {
        this.mascotaDAO = new MascotaDAO();
    }

    /** Registrar nueva mascota */
    public boolean registrarMascota(Mascota mascota) throws SQLException {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        if (mascota.getNombreMascota() == null || mascota.getNombreMascota().isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }
        if (mascota.getRaza() == null || mascota.getRaza().getIdRaza() <= 0) {
            throw new IllegalArgumentException("Debe especificar una raza válida");
        }
        if (mascota.getIdDueno() == null || mascota.getIdDueno().getIdDueno() <= 0) {
            throw new IllegalArgumentException("Debe especificar un dueño válido");
        }

        if (mascota.getPeso() != null && mascota.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg");
        }

        if (mascota.getMicrochip() != null && !mascota.getMicrochip().isBlank()) {
            if (mascota.getMicrochip().length() < 5) {
                throw new IllegalArgumentException("El número de microchip es demasiado corto");
            }
        }

        return mascotaDAO.insertar(mascota);
    }

    public Mascota obtenerMascota(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return mascotaDAO.obtenerPorId(id);
    }

    public List<Mascota> listarMascotas() throws SQLException {
        return mascotaDAO.listarTodos();
    }

    public boolean actualizarMascota(Mascota mascota) throws SQLException {
        if (mascota == null || mascota.getIdMascota() <= 0) {
            throw new IllegalArgumentException("Mascota o ID inválido para actualización");
        }

        if (mascota.getPeso() != null && mascota.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg");
        }
        if (mascota.getMicrochip() != null && !mascota.getMicrochip().isBlank()) {
            if (mascota.getMicrochip().length() < 5) {
                throw new IllegalArgumentException("El número de microchip es demasiado corto");
            }
        }

        return mascotaDAO.actualizar(mascota);
    }

    public boolean eliminarMascota(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return mascotaDAO.eliminar(id);
    }

    public List<Mascota> buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return mascotaDAO.buscarPorNombre(nombre);
    }

    public List<Mascota> listarPorDueno(int idDueno) throws SQLException {
        if (idDueno <= 0) {
            throw new IllegalArgumentException("ID de dueño inválido");
        }
        return mascotaDAO.listarPorDueno(idDueno);
    }
}

