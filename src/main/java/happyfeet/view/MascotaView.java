package happyfeet.view;

import happyfeet.controller.MascotaController;
import happyfeet.model.entities.Mascota;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MascotaView {
    private final MascotaController controller;

    public MascotaView(Scanner scanner) {
        try {
            this.controller = new MascotaController(scanner);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo inicializar MascotaController: " + e.getMessage(), e);
        }
    }

    public void registrarMascota() {
        // El controller pedirá nombre, raza, fechaNacimiento, sexo, urlFoto, idDueño
        controller.registrarMascota();
    }

    public void listarMascotas() {
        controller.listarMascotas();
    }


    public void editarMascota() {
        // Delegamos a actualizarFicha/actualizarMascota del controller
        controller.actualizarMascota();
    }

    /**
     * Transferencia de propiedad:
     * Si tu controller tiene un método específico para transferir, cámbialo aquí.
     * Si no, estamos reusando la actualización (el controller pedirá nuevo dueño).
     */
    public void transferirPropiedad() {
        System.out.println("\n--- Transferir propiedad de mascota ---");
        System.out.println("Se abrirá el asistente de actualización; ingrese sólo el nuevo ID de dueño.");
        controller.actualizarMascota();
    }

    public void eliminarMascota() {
        controller.eliminarMascota();
    }
}
