package happyfeet.view;

import happyfeet.controller.ActividadesController;
import java.sql.SQLException;
import java.util.Scanner;

public class ActividadesView {

    private final ActividadesController controller;
    private final Scanner scanner;

    public ActividadesView(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.controller = new ActividadesController(scanner);
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuActividades();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> controller.registrarMascotaAdopcion();
                case 2 -> controller.generarContratoAdopcion();
                case 3 -> controller.registrarJornadaVacunacion();
                case 4 -> controller.listarJornadas();  // NUEVA LLAMADA
                case 5 -> controller.gestionarClubMascotas();
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void mostrarMenuActividades() {
        System.out.println("\n----- ACTIVIDADES ESPECIALES -----");
        System.out.println("[1] Registrar mascota para adopción");
        System.out.println("[2] Generar contrato de adopción");
        System.out.println("[3] Registrar jornada de vacunación");
        System.out.println("[4] Listar jornadas de vacunación");  // NUEVA OPCIÓN
        System.out.println("[5] Gestionar Club de Mascotas Frecuentes");
        System.out.println("[0] Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
