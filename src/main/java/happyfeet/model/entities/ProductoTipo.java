package happyfeet.model.entities;

public class ProductoTipo {
    private int idProductoTipo;
    private String nombreProductoTipo;

    public ProductoTipo(){
    }

    public ProductoTipo(int idProductoTipo, String nombreProductoTipo){
        this.idProductoTipo = idProductoTipo;
        this.nombreProductoTipo = nombreProductoTipo;
    }

    public int getIdProductoTipo() {
        return idProductoTipo;
    }

    public void setIdProductoTipo(int idProductoTipo) {
        this.idProductoTipo = idProductoTipo;
    }

    public String getNombreProductoTipo() {
        return nombreProductoTipo;
    }

    public void setNombreProductoTipo(String nombreProductoTipo) {
        this.nombreProductoTipo = nombreProductoTipo;
    }

    @Override
    public String toString() {
        return "Producto_tipos{" +
                "idProductoTipo=" + idProductoTipo +
                ", nombreProductoTipo='" + nombreProductoTipo + '\'' +
                '}';
    }
}
