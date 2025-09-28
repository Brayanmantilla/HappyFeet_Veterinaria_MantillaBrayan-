package happyfeet.view;

import happyfeet.controller.InventarioController;
import java.util.Scanner;

public class InventarioView {

    private final InventarioController controller;
    private final Scanner scanner;

    public InventarioView(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new InventarioController(scanner);
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuInventario();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> controller.registrarProducto();
                case 2 -> controller.actualizarStock();
                case 3 -> controller.consultarStock();
                case 4 -> controller.verAlertas();
                case 5 -> menuProveedores();
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void menuProveedores() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n----- GESTIÓN DE PROVEEDORES -----");
            System.out.println("[1] Registrar nuevo proveedor");
            System.out.println("[2] Listar proveedores");
            System.out.println("[0] Volver al menú de inventario");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> controller.registrarProveedor();
                case 2 -> controller.listarProveedores();
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void mostrarMenuInventario() {
        System.out.println("\n----- INVENTARIO Y FARMACIA -----");
        System.out.println("[1] Registrar nuevo medicamento/insumo");
        System.out.println("[2] Actualizar stock");
        System.out.println("[3] Consultar stock actual");
        System.out.println("[4] Ver alertas (stock bajo / vencimientos)");
        System.out.println("[5] Gestionar proveedores");
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