package happyfeet.main;

import happyfeet.view.CitaView;
import happyfeet.view.DuenoView;
import happyfeet.view.MascotaView;
import happyfeet.view.InventarioView;
import happyfeet.view.FacturacionView;
import happyfeet.view.ActividadesView;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final DuenoView duenoView = new DuenoView(scanner);
    private final MascotaView mascotaView = new MascotaView(scanner);
    private final CitaView citaView = new CitaView(scanner);
    private final InventarioView inventarioView = new InventarioView(scanner);
    private final FacturacionView facturacionView = new FacturacionView(scanner);
    private final ActividadesView actividadesView = new ActividadesView(scanner);


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
            System.out.println("[3] Inventario y Farmacia");
            System.out.println("[4] Facturación y Reportes");
            System.out.println("[5] Actividades Especiales");
            System.out.println("[0] Salir del Sistema");
            System.out.println("-----------------------------------------");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuGestionPacientes();
                case 2 -> citaView.mostrarMenu();
                case 3 -> inventarioView.mostrarMenu();
                case 4 -> facturacionView.mostrarMenu();
                case 5 -> actividadesView.mostrarMenu();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida, intente de nuevo.");
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
            System.out.println("[7] Ver detalle completo de mascota");
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
                case 7 -> mascotaView.mostrarDetalleMascota();
                case 0 -> System.out.println("Volviendo al Menú Principal...");
                default -> System.out.println("Opción inválida, intente de nuevo.");
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