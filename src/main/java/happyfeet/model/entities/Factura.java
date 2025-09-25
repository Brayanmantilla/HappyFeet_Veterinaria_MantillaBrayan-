package happyfeet.model.entities;

import java.time.LocalDate;

public class Factura {
    private int idFactura;
    private Dueno dueno;
    private LocalDate fechaEmision;
    private double totalFactura;

    public Factura(){}

    public Factura(int idFactura, Dueno dueno, LocalDate fechaEmision, double totalFactura) {
        this.idFactura = idFactura;
        this.dueno = dueno;
        this.fechaEmision = fechaEmision;
        this.totalFactura = totalFactura;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", dueno=" + dueno +
                ", fechaEmision=" + fechaEmision +
                ", totalFactura=" + totalFactura +
                '}';
    }
}
