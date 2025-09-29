package happyfeet.model.enums;

public enum CitaEstado {
    PROGRAMADA(1, "Programada"),
    ENPROCESO(2, "En proceso"),
    FINALIZADA(3, "Finalizada"),
    CANCELADA(4, "Cancelada"),
    REPROGRAMADA(5, "Reprogramada"),
    NOASISTIO(6, "No asistió");

    private final int id;
    private final String nombre;

    CitaEstado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static CitaEstado fromId(int id) {
        for (CitaEstado estado : values()) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null; // o lanzar excepción
    }
}
