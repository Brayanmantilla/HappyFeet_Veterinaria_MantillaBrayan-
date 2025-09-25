package happyfeet.model.entities;

import java.time.LocalDate;

public class Compra {
    private int idCompra;
    private Proveedor proveedor;
    private LocalDate fechaCompra;
    private double totalCompra;

    public Compra(){}

    public Compra(int idCompra, Proveedor proveedor, LocalDate fechaCompra, double totalCompra) {
        this.idCompra = idCompra;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", proveedor=" + proveedor +
                ", fechaCompra=" + fechaCompra +
                ", totalCompra=" + totalCompra +
                '}';
    }
}
