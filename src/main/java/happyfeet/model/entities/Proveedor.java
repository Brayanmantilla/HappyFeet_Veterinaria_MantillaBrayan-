package happyfeet.model.entities;

public class Proveedor {
    private int idProveedor;
    private String nombreProveedor;
    private String contactoProveedor;
    private String telefonoProveedor;
    private String emailProveedor;
    private String direccionProveedor;

    public Proveedor(){}

    public Proveedor(int idProveedor, String emailProveedor, String direccionProveedor, String telefonoProveedor, String nombreProveedor, String contactoProveedor) {
        this.idProveedor = idProveedor;
        this.emailProveedor = emailProveedor;
        this.direccionProveedor = direccionProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.nombreProveedor = nombreProveedor;
        this.contactoProveedor = contactoProveedor;
    }

    public Proveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoProveedor) {
        this.contactoProveedor = contactoProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor=" + idProveedor +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", contactoProveedor='" + contactoProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                ", emailProveedor='" + emailProveedor + '\'' +
                ", direccionProveedor='" + direccionProveedor + '\'' +
                '}';
    }
}
