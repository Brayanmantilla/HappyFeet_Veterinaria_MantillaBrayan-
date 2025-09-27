package happyfeet.model.entities;

public class Raza {
    private int idRaza;
    private String nombreRaza;
    private Especie especie;

    public Raza(){}

    public Raza(int idRaza){
        this.idRaza = idRaza;
    }

    public Raza(int idRaza, String nombreRaza, Especie especie) {
        this.idRaza = idRaza;
        this.nombreRaza = nombreRaza;
        this.especie = especie;
    }

    // ✅ ESTE ES EL QUE ESTAMOS USANDO EN EL DAO
    public Raza(int rId, String rNombre) {
        this.idRaza = rId;
        this.nombreRaza = rNombre;
    }

    public String getNombreRaza() { return nombreRaza; }
    public void setNombreRaza(String nombreRaza) { this.nombreRaza = nombreRaza; }

    public int getIdRaza() { return idRaza; }
    public void setIdRaza(int idRaza) { this.idRaza = idRaza; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

    @Override
    public String toString() {
        return "Raza{" +
                "idRaza=" + idRaza +
                ", nombreRaza='" + nombreRaza + '\'' +
                ", especie=" + especie +
                '}';
    }
}
