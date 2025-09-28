package happyfeet.main;

import happyfeet.view.CitaView;
import happyfeet.view.DuenoView;
import happyfeet.view.MascotaView;
import happyfeet.view.InventarioView;  // ✅ NUEVA IMPORT
import happyfeet.view.FacturacionView;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final DuenoView duenoView = new DuenoView(scanner);
    private final MascotaView mascotaView = new MascotaView(scanner);
    private final CitaView citaView = new CitaView(scanner);
    private final InventarioView inventarioView = new InventarioView(scanner);  // ✅ NUEVA VIEW
    private final FacturacionView facturacionView = new FacturacionView(scanner);


    public Main() throws SQLException {
    }

    public void iniciarMenu() {
        int opcion;
        do {
            System.out.println("\n🌟 MENÚ PRINCIPAL");
            System.out.println("=========================================");
            System.out.println("     SISTEMA DE GESTIÓN INTEGRAL");
            System.out.println("          VETERINARIA HAPPY FEET");
            System.out.println("=========================================");
            System.out.println("[1] Gestión de Pacientes (Mascotas y Dueños)");
            System.out.println("[2] Servicios Médicos y Citas");
            System.out.println("[3] Inventario y Farmacia");  // ✅ NUEVA OPCIÓN
            System.out.println("[4] Facturación y Reportes");
            System.out.println("[5] Actividades Especiales");
            System.out.println("[0] Salir del Sistema");
            System.out.println("-----------------------------------------");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuGestionPacientes();
                case 2 -> citaView.mostrarMenu();
                case 3 -> inventarioView.mostrarMenu();  // ✅ NUEVA LLAMADA
                case 4 -> facturacionView.mostrarMenu();
                case 5 -> menuActividades();
                case 0 -> System.out.println("👋 Saliendo del sistema...");
                default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void menuGestionPacientes() {
        int opcion;
        do {
            System.out.println("\n----- GESTIÓN DE PACIENTES -----");
            System.out.println("[1] Registrar nuevo dueño");
            System.out.println("[2] Registrar nueva mascota");
            System.out.println("[3] Listar dueños");
            System.out.println("[4] Listar mascotas");
            System.out.println("[5] Consultar/editar ficha de mascota");
            System.out.println("[6] Transferir propiedad de mascota");
            System.out.println("[0] Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> duenoView.registrarDueno();
                case 2 -> mascotaView.registrarMascota();
                case 3 -> duenoView.listarDuenos();
                case 4 -> mascotaView.listarMascotas();
                case 5 -> mascotaView.editarMascota();
                case 6 -> mascotaView.transferirPropiedad();
                case 0 -> System.out.println("🔙 Volviendo al Menú Principal...");
                default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // ✅ NUEVOS MENÚS TEMPORALES (HASTA QUE IMPLEMENTES LOS MÓDULOS)
    private void menuFacturacion() {
        int opcion;
        do {
            System.out.println("\n----- FACTURACIÓN Y REPORTES -----");
            System.out.println("[1] Generar factura por servicio o venta");
            System.out.println("[2] Reporte: Servicios más solicitados");
            System.out.println("[3] Reporte: Desempeño del equipo veterinario");
            System.out.println("[4] Reporte: Estado de inventario");
            System.out.println("[5] Reporte: Facturación por período");
            System.out.println("[0] Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> System.out.println("🚧 Módulo en desarrollo...");
                case 2 -> System.out.println("🚧 Módulo en desarrollo...");
                case 3 -> System.out.println("🚧 Módulo en desarrollo...");
                case 4 -> System.out.println("🚧 Módulo en desarrollo...");
                case 5 -> System.out.println("🚧 Módulo en desarrollo...");
                case 0 -> System.out.println("🔙 Volviendo al Menú Principal...");
                default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void menuActividades() {
        int opcion;
        do {
            System.out.println("\n----- ACTIVIDADES ESPECIALES -----");
            System.out.println("[1] Registrar mascota para adopción");
            System.out.println("[2] Generar contrato de adopción");
            System.out.println("[3] Registrar jornada de vacunación");
            System.out.println("[4] Gestionar Club de Mascotas Frecuentes");
            System.out.println("[0] Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> System.out.println("🚧 Módulo en desarrollo...");
                case 2 -> System.out.println("🚧 Módulo en desarrollo...");
                case 3 -> System.out.println("🚧 Módulo en desarrollo...");
                case 4 -> System.out.println("🚧 Módulo en desarrollo...");
                case 0 -> System.out.println("🔙 Volviendo al Menú Principal...");
                default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠️ Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar el salto de línea pendiente
        return valor;
    }

    public static void main(String[] args) throws SQLException {
        new Main().iniciarMenu();
    }
}