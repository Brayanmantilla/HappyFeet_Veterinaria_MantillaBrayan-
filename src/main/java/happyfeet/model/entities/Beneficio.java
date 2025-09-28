package happyfeet.model.entities;

public class Beneficio {
    private int idBeneficio;
    private String nombreBeneficio;
    private String descripcion;
    private int puntosRequeridos;
    private String nivelMinimo;
    private boolean activo;

    public Beneficio() {}

    public Beneficio(int idBeneficio, String nombreBeneficio, String descripcion,
                     int puntosRequeridos, String nivelMinimo, boolean activo) {
        this.idBeneficio = idBeneficio;
        this.nombreBeneficio = nombreBeneficio;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
        this.nivelMinimo = nivelMinimo;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdBeneficio() { return idBeneficio; }
    public void setIdBeneficio(int idBeneficio) { this.idBeneficio = idBeneficio; }

    public String getNombreBeneficio() { return nombreBeneficio; }
    public void setNombreBeneficio(String nombreBeneficio) { this.nombreBeneficio = nombreBeneficio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPuntosRequeridos() { return puntosRequeridos; }
    public void setPuntosRequeridos(int puntosRequeridos) { this.puntosRequeridos = puntosRequeridos; }

    public String getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(String nivelMinimo) { this.nivelMinimo = nivelMinimo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Beneficio{" +
                "nombreBeneficio='" + nombreBeneficio + '\'' +
                ", puntosRequeridos=" + puntosRequeridos +
                ", nivelMinimo='" + nivelMinimo + '\'' +
                '}';
    }
}