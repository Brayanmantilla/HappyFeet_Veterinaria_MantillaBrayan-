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
        System.out.println("\n--- Registro de nueva Mascota ---");
        controller.registrarMascota();
    }

    public void listarMascotas() {
        System.out.println("\n--- Lista de Mascotas ---");
        controller.listarMascotas();
    }

    public void editarMascota() {
        System.out.println("\n--- Editar Mascota ---");
        controller.actualizarMascota();
    }

    public void transferirPropiedad() {
        System.out.println("\n--- Transferir Propiedad de Mascota ---");
        System.out.println("Se abrirá el asistente de actualización; ingrese sólo el nuevo ID de dueño.");
        controller.actualizarMascota();
    }

    public void eliminarMascota() {
        System.out.println("\n--- Eliminar Mascota ---");
        controller.eliminarMascota();
    }

    public void mostrarDetalleMascota() {
        System.out.println("\n--- Detalle Completo de Mascota ---");
        controller.mostrarDetalleMascota();
    }
}
