package happyfeet.model.enums;

public enum sexoMascota {
    Macho("Macho"),
    Hembra("Hembra");

    private final String nombre;

    sexoMascota(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return nombre;
    }

}
