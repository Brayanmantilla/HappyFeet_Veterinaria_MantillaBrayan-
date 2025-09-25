package happyfeet.repository.interfaces;

import happyfeet.model.entities.Cita;
import happyfeet.model.enums.CitaEstado;

import java.util.List;

public interface ICitasDAO {
    boolean insertar(Cita cita) throws Exception;
    Cita obtenerPorId(int id) throws Exception;
    List<Cita> listarAgenda() throws Exception;
    boolean actualizarEstado(int idCita, CitaEstado nuevoEstado) throws Exception;
    boolean eliminar(int id) throws Exception;
}
