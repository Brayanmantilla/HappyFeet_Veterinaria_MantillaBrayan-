package happyfeet.model.entities;

import jdk.jfr.Event;

public class EventoTipo {
    private int idEventoTipo;
    private String nombreEventoTipo;

    public EventoTipo(){
    }

    public EventoTipo(int idEventoTipo, String nombreEventoTipo){
        this.idEventoTipo = idEventoTipo;
        this.nombreEventoTipo = nombreEventoTipo;
    }

    public int getIdEventoTipo() {
        return idEventoTipo;
    }

    public void setIdEventoTipo(int idEventoTipo) {
        this.idEventoTipo = idEventoTipo;
    }

    public String getNombreEventoTipo() {
        return nombreEventoTipo;
    }

    public void setNombreEventoTipo(String nombreEventoTipo) {
        this.nombreEventoTipo = nombreEventoTipo;
    }

    @Override
    public String toString() {
        return "EventoTipo{" +
                "idEventoTipo=" + idEventoTipo +
                ", nombreEventoTipo='" + nombreEventoTipo + '\'' +
                '}';
    }
}
