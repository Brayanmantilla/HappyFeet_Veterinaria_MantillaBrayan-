package happyfeet.model.entities;

public class HistorialMedicoDetalle {
    private int idHistorialMedicoDetalle;
    private HistorialMedico historialMedico;
    private Inventario inventario;
    private Servicio servicio;

    public HistorialMedicoDetalle(){
    }

    public HistorialMedicoDetalle(int idHistorialMedicoDetalle, HistorialMedico historialMedico, Inventario inventario, Servicio servicio) {
        this.idHistorialMedicoDetalle = idHistorialMedicoDetalle;
        this.historialMedico = historialMedico;
        this.inventario = inventario;
        this.servicio = servicio;
    }

    public int getIdHistorialMedicoDetalle() {
        return idHistorialMedicoDetalle;
    }

    public void setIdHistorialMedicoDetalle(int idHistorialMedicoDetalle) {
        this.idHistorialMedicoDetalle = idHistorialMedicoDetalle;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "HistorialMedicoDetalle{" +
                "idHistorialMedicoDetalle=" + idHistorialMedicoDetalle +
                ", historialMedico=" + historialMedico +
                ", inventario=" + inventario +
                ", servicio=" + servicio +
                '}';
    }
}
