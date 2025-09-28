package happyfeet.service;

import happyfeet.model.entities.JornadaVacunacion;
import happyfeet.repository.DAO.JornadaVacunacionDAO;
import happyfeet.repository.interfaces.IJornadaVacunacionDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JornadaVacunacionService {
    private final IJornadaVacunacionDAO jornadaDAO;

    public JornadaVacunacionService() throws SQLException {
        this.jornadaDAO = new JornadaVacunacionDAO();
    }

    public boolean registrarJornada(JornadaVacunacion jornada) {
        if (jornada == null) {
            throw new IllegalArgumentException("La jornada no puede ser nula");
        }
        if (jornada.getNombreJornada() == null || jornada.getNombreJornada().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la jornada es obligatorio");
        }
        if (jornada.getFechaJornada() == null) {
            throw new IllegalArgumentException("La fecha de la jornada es obligatoria");
        }
        if (jornada.getFechaJornada().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la jornada debe ser futura");
        }

        try {
            return jornadaDAO.insertar(jornada);
        } catch (SQLException e) {
            System.out.println("Error al registrar jornada: " + e.getMessage());
            return false;
        }
    }

    public List<JornadaVacunacion> listarJornadas() {
        try {
            return jornadaDAO.listarTodas();
        } catch (SQLException e) {
            System.out.println("Error al listar jornadas: " + e.getMessage());
            return List.of();
        }
    }

    public boolean actualizarJornada(JornadaVacunacion jornada) {
        if (jornada == null || jornada.getIdJornada() <= 0) {
            throw new IllegalArgumentException("Jornada inválida para actualización");
        }

        try {
            return jornadaDAO.actualizar(jornada);
        } catch (SQLException e) {
            System.out.println("Error al actualizar jornada: " + e.getMessage());
            return false;
        }
    }

    public JornadaVacunacion obtenerJornadaPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        try {
            return jornadaDAO.obtenerPorId(id);
        } catch (SQLException e) {
            System.out.println("Error al obtener jornada: " + e.getMessage());
            return null;
        }
    }
}