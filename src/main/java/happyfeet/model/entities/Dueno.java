package happyfeet.model.entities;

public class Dueno {
    private int idDueno;
    private String nombreDueno;
    private String doumentoDueno;   // 🔹 Se mantiene el nombre original con el error
    private String direccionDueno;
    private String telefonoDueno;
    private String emailDueno;

    public Dueno(){}

    // ✅ Constructor completo con orden lógico
    public Dueno(int idDueno, String nombreDueno, String doumentoDueno,
                 String direccionDueno, String telefonoDueno, String emailDueno) {
        this.idDueno = idDueno;
        this.nombreDueno = nombreDueno;
        this.doumentoDueno = doumentoDueno;
        this.direccionDueno = direccionDueno;
        this.telefonoDueno = telefonoDueno;
        this.emailDueno = emailDueno;
    }

    // ✅ Constructor usado en el DAO para ID + Nombre
    public Dueno(int dId, String dNombreCompleto) {
        this.idDueno = dId;
        this.nombreDueno = dNombreCompleto;
    }

    public Dueno(int idDueno) {
        this.idDueno = idDueno;
    }

    public int getIdDueno() { return idDueno; }
    public void setIdDueno(int idDueno) { this.idDueno = idDueno; }

    public String getNombreDueno() { return nombreDueno; }
    public void setNombreDueno(String nombreDueno) { this.nombreDueno = nombreDueno; }

    public String getDoumentoDueno() { return doumentoDueno; }
    public void setDoumentoDueno(String doumentoDueno) { this.doumentoDueno = doumentoDueno; }

    public String getDireccionDueno() { return direccionDueno; }
    public void setDireccionDueno(String direccionDueno) { this.direccionDueno = direccionDueno; }

    public String getTelefonoDueno() { return telefonoDueno; }
    public void setTelefonoDueno(String telefonoDueno) { this.telefonoDueno = telefonoDueno; }

    public String getEmailDueno() { return emailDueno; }
    public void setEmailDueno(String emailDueno) { this.emailDueno = emailDueno; }

    @Override
    public String toString() {
        return "Dueno{" +
                "idDueno=" + idDueno +
                ", nombreDueno='" + nombreDueno + '\'' +
                ", doumentoDueno='" + doumentoDueno + '\'' +
                ", direccionDueno='" + direccionDueno + '\'' +
                ", telefonoDueno='" + telefonoDueno + '\'' +
                ", emailDueno='" + emailDueno + '\'' +
                '}';
    }
}
