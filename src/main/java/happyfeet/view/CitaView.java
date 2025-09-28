package happyfeet.view;

import happyfeet.controller.CitaController;
import happyfeet.model.enums.CitaEstado;

import java.sql.SQLException;
import java.util.Scanner;

public class CitaView {

    private final CitaController controller;
    private final Scanner scanner;

    public CitaView(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.controller = new CitaController(scanner);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n----- SERVICIOS MÉDICOS Y CITAS -----");
            System.out.println("[1] Programar nueva cita");
            System.out.println("[2] Consultar citas");
            System.out.println("[3] Actualizar estado de cita");
            System.out.println("[4] Eliminar cita");
            System.out.println("[0] Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> controller.registrarCita();
                case 2 -> controller.listarCitas();
                case 3 -> actualizarEstado();
                case 4 -> controller.eliminarCita();
                case 0 -> System.out.println("🔙 Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void actualizarEstado() {
        System.out.println("\n--- Actualizar Estado de Cita ---");
        int idCita = pedirEntero("Ingrese el ID de la cita: ");

        System.out.println("Seleccione el nuevo estado:");
        for (CitaEstado estado : CitaEstado.values()) {
            System.out.println(estado.getId() + " - " + estado.getNombre());
        }

        int idEstado = leerEntero();
        CitaEstado nuevoEstado = CitaEstado.fromId(idEstado);

        if (nuevoEstado != null) {
            controller.actualizarEstadoCita(idCita, nuevoEstado);
        } else {
            System.out.println("⚠️ Estado inválido.");
        }
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠️ Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        return leerEntero();
    }
}
