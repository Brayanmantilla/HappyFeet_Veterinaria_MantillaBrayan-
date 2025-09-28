package happyfeet.view;

import happyfeet.controller.HistorialMedicoController;
import happyfeet.model.entities.EventoTipo;
import happyfeet.model.entities.HistorialMedico;
import happyfeet.model.entities.Mascota;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HistorialMedicoView {

    private final HistorialMedicoController controller;
    private final Scanner scanner;

    public HistorialMedicoView(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.controller = new HistorialMedicoController(scanner);
    }

    public void mostrarMenu() throws SQLException {
        int opcion;
        do {
            System.out.println("\n----- HISTORIAL MÉDICO -----");
            System.out.println("[1] Registrar evento");
            System.out.println("[2] Listar eventos por mascota");
            System.out.println("[0] Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> controller.registrarEvento();
                case 2 -> controller.listarEventosPorMascota();
                case 0 -> System.out.println("🔙 Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 0);
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

}
