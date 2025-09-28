package happyfeet.service;

import happyfeet.model.entities.ClubMascota;
import happyfeet.repository.DAO.ClubMascotaDAO;
import happyfeet.repository.interfaces.IClubMascotaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClubMascotaService {
    private final IClubMascotaDAO clubDAO;

    public ClubMascotaService() throws SQLException {
        this.clubDAO = new ClubMascotaDAO();
    }

    public boolean inscribirEnClub(ClubMascota club) {
        if (club == null) {
            throw new IllegalArgumentException("El club no puede ser nulo");
        }
        if (club.getDueno() == null) {
            throw new IllegalArgumentException("Debe especificar un dueño");
        }

        try {
            club.setFechaInscripcion(LocalDate.now());
            club.setPuntosAcumulados(0);
            club.setNivel("Bronce");
            club.setEstado("Activo");
            return clubDAO.insertar(club);
        } catch (SQLException e) {
            System.out.println("Error al inscribir en club: " + e.getMessage());
            return false;
        }
    }

    public ClubMascota obtenerPorDueno(int duenoId) {
        if (duenoId <= 0) {
            throw new IllegalArgumentException("ID de dueño inválido");
        }

        try {
            return clubDAO.obtenerPorDueno(duenoId);
        } catch (SQLException e) {
            System.out.println("Error al obtener club: " + e.getMessage());
            return null;
        }
    }

    public boolean agregarPuntos(int duenoId, int puntos) {
        if (duenoId <= 0) {
            throw new IllegalArgumentException("ID de dueño inválido");
        }
        if (puntos <= 0) {
            throw new IllegalArgumentException("Los puntos deben ser positivos");
        }

        try {
            ClubMascota club = clubDAO.obtenerPorDueno(duenoId);
            if (club != null) {
                int nuevoPuntos = club.getPuntosAcumulados() + puntos;
                club.setPuntosAcumulados(nuevoPuntos);

                // Actualizar nivel según puntos
                String nuevoNivel = calcularNivel(nuevoPuntos);
                club.setNivel(nuevoNivel);

                return clubDAO.actualizar(club);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar puntos: " + e.getMessage());
        }
        return false;
    }

    public List<ClubMascota> listarMiembros() {
        try {
            return clubDAO.listarTodos();
        } catch (SQLException e) {
            System.out.println("Error al listar miembros: " + e.getMessage());
            return List.of();
        }
    }

    private String calcularNivel(int puntos) {
        if (puntos >= 1000) return "Diamante";
        if (puntos >= 500) return "Oro";
        if (puntos >= 200) return "Plata";
        return "Bronce";
    }
}