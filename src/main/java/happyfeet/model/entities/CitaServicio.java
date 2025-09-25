package happyfeet.model.entities;

public class CitaServicio {
    private int idCitaServicio;
    private Cita cita;
    private Servicio servicios;
    private int cantidad;
    private double precioCita;

    public CitaServicio(){
    }

    public CitaServicio(int idCitaServicio, Cita cita, Servicio servicios, int cantidad, double precioCita) {
        this.idCitaServicio = idCitaServicio;
        this.cita = cita;
        this.servicios = servicios;
        this.cantidad = cantidad;
        this.precioCita = precioCita;
    }

    public int getIdCitaServicio() {
        return idCitaServicio;
    }

    public void setIdCitaServicio(int idCitaServicio) {
        this.idCitaServicio = idCitaServicio;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Servicio getServicios() {
        return servicios;
    }

    public void setServicios(Servicio servicios) {
        this.servicios = servicios;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCita() {
        return precioCita;
    }

    public void setPrecioCita(double precioCita) {
        this.precioCita = precioCita;
    }

    @Override
    public String toString() {
        return "CitaServicio{" +
                "idCitaServicio=" + idCitaServicio +
                ", cita=" + cita +
                ", servicios=" + servicios +
                ", cantidad=" + cantidad +
                ", precioCita=" + precioCita +
                '}';
    }
}
