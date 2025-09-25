package happyfeet.model.entities;

import java.time.LocalDate;

public class HistorialMedico {
    private int idHistorialMedico;
    private Mascota mascota;
    private LocalDate fechaEvento;
    private EventoTipo eventoTipo;
    private String descripcion;
    private String diagnostico;

    public HistorialMedico(){
    }

    public HistorialMedico(int idHistorialMedico, Mascota mascota, LocalDate fechaEvento, EventoTipo eventoTipo, String descripcion, String diagnostico) {
        this.idHistorialMedico = idHistorialMedico;
        this.mascota = mascota;
        this.fechaEvento = fechaEvento;
        this.eventoTipo = eventoTipo;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
    }

    public int getIdHistorialMedico() {
        return idHistorialMedico;
    }

    public void setIdHistorialMedico(int idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(EventoTipo eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "HistorialMedico{" +
                "idHistorialMedico=" + idHistorialMedico +
                ", mascota=" + mascota +
                ", fechaEvento=" + fechaEvento +
                ", eventoTipo=" + eventoTipo +
                ", descripcion='" + descripcion + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
