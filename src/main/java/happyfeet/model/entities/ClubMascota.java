package happyfeet.model.entities;

import java.time.LocalDate;

public class ClubMascota {
    private int idClub;
    private Dueno dueno;
    private LocalDate fechaInscripcion;
    private int puntosAcumulados;
    private String nivel;
    private String estado;

    public ClubMascota() {}

    public ClubMascota(int idClub, Dueno dueno, LocalDate fechaInscripcion,
                       int puntosAcumulados, String nivel, String estado) {
        this.idClub = idClub;
        this.dueno = dueno;
        this.fechaInscripcion = fechaInscripcion;
        this.puntosAcumulados = puntosAcumulados;
        this.nivel = nivel;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdClub() { return idClub; }
    public void setIdClub(int idClub) { this.idClub = idClub; }

    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) { this.dueno = dueno; }

    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public int getPuntosAcumulados() { return puntosAcumulados; }
    public void setPuntosAcumulados(int puntosAcumulados) { this.puntosAcumulados = puntosAcumulados; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "ClubMascota{" +
                "idClub=" + idClub +
                ", dueno=" + dueno.getNombreDueno() +
                ", puntosAcumulados=" + puntosAcumulados +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}