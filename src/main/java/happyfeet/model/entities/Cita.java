package happyfeet.model.entities;

import happyfeet.model.enums.CitaEstado;

import java.time.LocalDate;

public class Cita {
    private int idCita;
    private Mascota mascota;
    private Empleado empleado;
    private CitaEstado citaEstado;
    private LocalDate fechaHoraCita;
    private String motivoCita;

    public Cita(){}

    public Cita(int idCita, Mascota mascota, Empleado empleado, CitaEstado citaEstado, LocalDate fechaHoraCita, String motivoCita) {
        this.idCita = idCita;
        this.mascota = mascota;
        this.empleado = empleado;
        this.citaEstado = citaEstado;
        this.fechaHoraCita = fechaHoraCita;
        this.motivoCita = motivoCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public CitaEstado getCitaEstado() {
        return citaEstado;
    }

    public void setCitaEstado(CitaEstado citaEstado) {
        this.citaEstado = citaEstado;
    }

    public LocalDate getFechaHoraCita() {
        return fechaHoraCita;
    }

    public void setFechaHoraCita(LocalDate fechaHoraCita) {
        this.fechaHoraCita = fechaHoraCita;
    }

    public String getMotivoCita() {
        return motivoCita;
    }

    public void setMotivoCita(String motivoCita) {
        this.motivoCita = motivoCita;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita=" + idCita +
                ", mascota=" + mascota +
                ", empleado=" + empleado +
                ", citaEstado=" + citaEstado +
                ", fechaHoraCita=" + fechaHoraCita +
                ", motivoCita='" + motivoCita + '\'' +
                '}';
    }
}
