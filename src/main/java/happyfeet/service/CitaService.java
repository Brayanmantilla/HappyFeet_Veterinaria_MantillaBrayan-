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
            // Se obtiene una sola conexión para el DAO
            this.connection = Conexion.getConexion();
            this.citasDAO = new CitasDAO(connection);
        }

        /**
         * Registra una nueva cita en la base de datos.
         * @param cita Objeto Cita con todos los datos requeridos
         * @return true si la cita fue insertada correctamente
         */
        public boolean registrarCita(Cita cita) {
            if (cita == null) return false;
            try {
                return citasDAO.insertar(cita);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Obtiene una cita por su ID.
         * @param id identificador de la cita
         * @return objeto Cita o null si no se encuentra
         */
        public Cita obtenerCitaPorId(int id) {
            if (id <= 0) return null;
            try {
                return citasDAO.obtenerPorId(id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Lista toda la agenda de citas ordenada por fecha.
         * @return lista de citas
         */
        public List<Cita> listarAgenda() {
            try {
                return citasDAO.listarAgenda();
            } catch (Exception e) {
                e.printStackTrace();
                return List.of();
            }
        }

        /**
         * Actualiza el estado de una cita (Pendiente, Confirmada, Cancelada, etc.)
         * @param idCita identificador de la cita
         * @param nuevoEstado nuevo estado a asignar
         * @return true si la actualización fue exitosa
         */
        public boolean actualizarEstado(int idCita, CitaEstado nuevoEstado) {
            if (idCita <= 0 || nuevoEstado == null) return false;
            try {
                return citasDAO.actualizarEstado(idCita, nuevoEstado);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Elimina una cita por su ID.
         * @param id identificador de la cita
         * @return true si se eliminó correctamente
         */
        public boolean eliminarCita(int id) {
            if (id <= 0) return false;
            try {
                return citasDAO.eliminar(id);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Cierra la conexión manualmente si es necesario.
         */
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
