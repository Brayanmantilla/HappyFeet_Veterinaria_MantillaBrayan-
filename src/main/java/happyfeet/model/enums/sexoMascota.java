package happyfeet.model.enums;

public enum sexoMascota {
    MACHO("Macho"),
    HEMBRA("Hembra");

    private final String nombre;

    sexoMascota(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return nombre;
    }

}
