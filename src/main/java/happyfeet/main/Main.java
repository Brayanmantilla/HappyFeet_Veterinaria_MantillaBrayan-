package happyfeet.main;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n🌟 MENÚ PRINCIPAL");
            System.out.println("[1] Gestión de Pacientes");
            System.out.println("[2] Servicios Médicos y Citas");
            System.out.println("[3] Inventario y Farmacia");
            System.out.println("[4] Facturación y Reportes");
            System.out.println("[5] Actividades Especiales");
            System.out.println("[0] Salir");
            System.out.print("Seleccione una opción: ");
            opcion = input.nextInt();
            input.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> new PacienteView().mostrarMenu();
                case 2 -> new ServicioMedicoView().mostrarMenu();
                case 3 -> new InventarioView().mostrarMenu();
                case 4 -> new FacturacionView().mostrarMenu();
                case 5 -> new ActividadesView().mostrarMenu();
                case 0 -> System.out.println("👋 Saliendo del sistema...");
                default -> System.out.println("❌ Opción no válida");
            }
        } while (opcion != 0);
    }
}