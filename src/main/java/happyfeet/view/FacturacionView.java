package happyfeet.view;

import happyfeet.controller.FacturacionController;
import java.sql.SQLException;
import java.util.Scanner;

public class FacturacionView {

    private final FacturacionController controller;
    private final Scanner scanner;

    public FacturacionView(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.controller = new FacturacionController(scanner);
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuFacturacion();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> controller.generarFactura();
                case 2 -> controller.reporteServiciosSolicitados();
                case 3 -> controller.reporteDesempenoVeterinario();
                case 4 -> controller.reporteEstadoInventario();
                case 5 -> controller.reporteFacturacionPeriodo();
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void mostrarMenuFacturacion() {
        System.out.println("\n----- FACTURACIÓN Y REPORTES -----");
        System.out.println("[1] Generar factura por servicio o venta");
        System.out.println("[2] Reporte: Servicios más solicitados");
        System.out.println("[3] Reporte: Desempeño del equipo veterinario");
        System.out.println("[4] Reporte: Estado de inventario");
        System.out.println("[5] Reporte: Facturación por período");
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