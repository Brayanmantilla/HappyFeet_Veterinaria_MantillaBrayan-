package happyfeet.model.entities;

import happyfeet.model.enums.sexoMascota;

import java.time.LocalDate;

public class Mascota {
    private int idMascota;
    private String nombreMascota;
    private Raza raza;
    private LocalDate fechaNacimiento;
    private sexoMascota sexo;
    private String urlFoto;
    private Dueno idDueno;

    public Mascota(){

    }

    public Mascota(int idMascota, String nombreMascota, Raza raza, LocalDate fechaNacimiento, sexoMascota sexo, String urlFoto, Dueno idDueno) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.urlFoto = urlFoto;
        this.idDueno= idDueno;
    }

    public Mascota(int idMascota, Dueno idDueno) {
        this.idMascota = idMascota;
        this.idDueno = idDueno;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public sexoMascota getSexo() {
        return sexo;
    }

    public void setSexo(sexoMascota sexo) {
        this.sexo = sexo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Dueno getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(Dueno idDueno) {
        this.idDueno = idDueno;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "idMascota=" + idMascota +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", raza=" + raza +
                ", fechaNacimiento=" + fechaNacimiento +
                ", sexo=" + sexo +
                ", urlFoto='" + urlFoto + '\'' +
                ", idDueno=" + idDueno +
                '}';
    }
}
