package happyfeet.model.entities;

import java.time.LocalDate;

public class JornadaVacunacion {
    private int idJornada;
    private String nombreJornada;
    private LocalDate fechaJornada;
    private String lugar;
    private String descripcion;
    private Empleado empleadoResponsable;
    private String estado;

    public JornadaVacunacion() {}

    public JornadaVacunacion(int idJornada, String nombreJornada, LocalDate fechaJornada,
                             String lugar, String descripcion, Empleado empleadoResponsable, String estado) {
        this.idJornada = idJornada;
        this.nombreJornada = nombreJornada;
        this.fechaJornada = fechaJornada;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.empleadoResponsable = empleadoResponsable;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdJornada() { return idJornada; }
    public void setIdJornada(int idJornada) { this.idJornada = idJornada; }

    public String getNombreJornada() { return nombreJornada; }
    public void setNombreJornada(String nombreJornada) { this.nombreJornada = nombreJornada; }

    public LocalDate getFechaJornada() { return fechaJornada; }
    public void setFechaJornada(LocalDate fechaJornada) { this.fechaJornada = fechaJornada; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Empleado getEmpleadoResponsable() { return empleadoResponsable; }
    public void setEmpleadoResponsable(Empleado empleadoResponsable) { this.empleadoResponsable = empleadoResponsable; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "JornadaVacunacion{" +
                "idJornada=" + idJornada +
                ", nombreJornada='" + nombreJornada + '\'' +
                ", fechaJornada=" + fechaJornada +
                ", estado='" + estado + '\'' +
                '}';
    }
}