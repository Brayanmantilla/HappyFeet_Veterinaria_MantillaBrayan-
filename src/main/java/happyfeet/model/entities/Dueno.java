package happyfeet.model.entities;

public class Dueno {
    private int idDueno;
    private String nombreDueno;
    private String doumentoDueno;
    private String direccionDueno;
    private String telefonoDueno;
    private String emailDueno;

    public Dueno(){}

    public Dueno(int idDueno, String telefonoDueno, String emailDueno, String doumentoDueno, String nombreDueno, String direccionDueno) {
        this.idDueno = idDueno;
        this.telefonoDueno = telefonoDueno;
        this.emailDueno = emailDueno;
        this.doumentoDueno = doumentoDueno;
        this.nombreDueno = nombreDueno;
        this.direccionDueno = direccionDueno;
    }

    public Dueno(int idDueno) {
        this.idDueno = idDueno;
    }

    public int getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(int idDueno) {
        this.idDueno = idDueno;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    public String getDoumentoDueno() {
        return doumentoDueno;
    }

    public void setDoumentoDueno(String doumentoDueno) {
        this.doumentoDueno = doumentoDueno;
    }

    public String getDireccionDueno() {
        return direccionDueno;
    }

    public void setDireccionDueno(String direccionDueno) {
        this.direccionDueno = direccionDueno;
    }

    public String getTelefonoDueno() {
        return telefonoDueno;
    }

    public void setTelefonoDueno(String telefonoDueno) {
        this.telefonoDueno = telefonoDueno;
    }

    public String getEmailDueno() {
        return emailDueno;
    }

    public void setEmailDueno(String emailDueno) {
        this.emailDueno = emailDueno;
    }

}
