package happyfeet.model.entities;

public class CompraDetalle {
    private int idCompraDetalle;
    private Compra compra;
    private Inventario inventario;
    private int cantidadCompraDetalle;
    private double precioUnitario;
    private double subtotal;

    public CompraDetalle(){
    }

    public CompraDetalle(int idCompraDetalle, Compra compra, Inventario inventario, int cantidadCompraDetalle, double precioUnitario, double subtotal) {
        this.idCompraDetalle = idCompraDetalle;
        this.compra = compra;
        this.inventario = inventario;
        this.cantidadCompraDetalle = cantidadCompraDetalle;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getIdCompraDetalle() {
        return idCompraDetalle;
    }

    public void setIdCompraDetalle(int idCompraDetalle) {
        this.idCompraDetalle = idCompraDetalle;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public int getCantidadCompraDetalle() {
        return cantidadCompraDetalle;
    }

    public void setCantidadCompraDetalle(int cantidadCompraDetalle) {
        this.cantidadCompraDetalle = cantidadCompraDetalle;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "CompraDetalle{" +
                "idCompraDetalle=" + idCompraDetalle +
                ", compra=" + compra +
                ", inventario=" + inventario +
                ", cantidadCompraDetalle=" + cantidadCompraDetalle +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
