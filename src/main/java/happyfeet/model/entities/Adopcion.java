package happyfeet.model.entities;

import java.time.LocalDate;

public class Adopcion {
    private int idAdopcion;
    private Mascota mascota;
    private Dueno duenoAnterior;
    private Dueno duenoNuevo;
    private LocalDate fechaDisponible;
    private LocalDate fechaAdopcion;
    private String estado;
    private String motivoAdopcion;
    private String observaciones;

    public Adopcion() {}

    public Adopcion(int idAdopcion, Mascota mascota, Dueno duenoAnterior, Dueno duenoNuevo,
                    LocalDate fechaDisponible, LocalDate fechaAdopcion, String estado,
                    String motivoAdopcion, String observaciones) {
        this.idAdopcion = idAdopcion;
        this.mascota = mascota;
        this.duenoAnterior = duenoAnterior;
        this.duenoNuevo = duenoNuevo;
        this.fechaDisponible = fechaDisponible;
        this.fechaAdopcion = fechaAdopcion;
        this.estado = estado;
        this.motivoAdopcion = motivoAdopcion;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getIdAdopcion() { return idAdopcion; }
    public void setIdAdopcion(int idAdopcion) { this.idAdopcion = idAdopcion; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public Dueno getDuenoAnterior() { return duenoAnterior; }
    public void setDuenoAnterior(Dueno duenoAnterior) { this.duenoAnterior = duenoAnterior; }

    public Dueno getDuenoNuevo() { return duenoNuevo; }
    public void setDuenoNuevo(Dueno duenoNuevo) { this.duenoNuevo = duenoNuevo; }

    public LocalDate getFechaDisponible() { return fechaDisponible; }
    public void setFechaDisponible(LocalDate fechaDisponible) { this.fechaDisponible = fechaDisponible; }

    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(LocalDate fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMotivoAdopcion() { return motivoAdopcion; }
    public void setMotivoAdopcion(String motivoAdopcion) { this.motivoAdopcion = motivoAdopcion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}