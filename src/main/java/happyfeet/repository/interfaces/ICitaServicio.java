package happyfeet.repository.interfaces;

import happyfeet.model.entities.CitaServicio;

import java.util.List;

public interface ICitaServicio {
    boolean insertar(CitaServicio citaServicio) throws Exception ;
    List<CitaServicio> listarPorCita(int idCita) throws Exception ;
}
