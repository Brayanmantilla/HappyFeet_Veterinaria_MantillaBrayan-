package happyfeet.model.entities;

public class ItemFactura {
    private int idItemFactura;
    private Factura factura;
    private Inventario inventario;
    private Servicio servicio;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ItemFactura(){}

    public ItemFactura(int idItemFactura, Factura factura, Inventario inventario, Servicio servicio, int cantidad, double precioUnitario, double subtotal) {
        this.idItemFactura = idItemFactura;
        this.factura = factura;
        this.inventario = inventario;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getIdItemFactura() {
        return idItemFactura;
    }

    public void setIdItemFactura(int idItemFactura) {
        this.idItemFactura = idItemFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        return "ItemFactura{" +
                "idItemFactura=" + idItemFactura +
                ", factura=" + factura +
                ", inventario=" + inventario +
                ", servicio=" + servicio +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
