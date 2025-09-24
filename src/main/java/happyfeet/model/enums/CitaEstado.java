package happyfeet.model.enums;

public enum CitaEstado {
    PROGRAMADA("Programada"),
    ENPROCESO("En proceso"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    REPROGRAMADA("Reprogramada"),
    NOASISTIO("No asistio");

    private final String citaEstado;

    CitaEstado(String citaEstado){
        this.citaEstado = citaEstado;
    }

    public String getCitaEstado(){
        return citaEstado;
    }

}
