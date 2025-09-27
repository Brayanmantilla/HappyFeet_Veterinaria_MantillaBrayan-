package happyfeet.view;

import happyfeet.controller.DuenoController;

import java.sql.SQLException;
import java.util.Scanner;

public class DuenoView {
    private final DuenoController controller;

    public DuenoView(Scanner scanner) {
        try {
            this.controller = new DuenoController(scanner);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo inicializar DuenoController: " + e.getMessage(), e);
        }
    }

    public void registrarDueno() {
        // El controller se encarga de pedir los datos por consola (usa el mismo Scanner).
        controller.registrarDueno();
    }

    public void listarDuenos() {
        controller.listarDuenos();
    }

    public void buscarDuenoPorId() {
        controller.buscarDuenoPorId();
    }

    public void actualizarDueno() {
        controller.actualizarDueno();
    }

    public void eliminarDueno() {
        controller.eliminarDueno();
    }

}
