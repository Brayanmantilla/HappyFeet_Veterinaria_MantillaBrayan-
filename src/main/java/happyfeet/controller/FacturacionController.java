package happyfeet.controller;

import happyfeet.model.entities.*;
import happyfeet.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FacturacionController {

    private final Scanner scanner;
    private final FacturaService facturaService;
    private final ItemsFacturaService itemsFacturaService;
    private final DuenoService duenoService;
    private final ServicioService servicioService;
    private final InventarioService inventarioService;
    private final ReporteService reporteService;

    public FacturacionController(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.facturaService = new FacturaService();
        this.itemsFacturaService = new ItemsFacturaService();
        this.duenoService = new DuenoService();
        this.servicioService = new ServicioService();
        this.inventarioService = new InventarioService();
        this.reporteService = new ReporteService();
    }

    public void generarFactura() {
        System.out.println("\n--- Generar Nueva Factura ---");

        try {
            // Seleccionar dueño
            System.out.print("ID del dueño: ");
            int idDueno = Integer.parseInt(scanner.nextLine().trim());

            Dueno dueno = duenoService.buscarPorId(idDueno);
            if (dueno == null) {
                System.out.println("❌ Dueño no encontrado.");
                return;
            }

            System.out.println("Cliente: " + dueno.getNombreDueno());

            // Crear factura
            Factura factura = new Factura();
            factura.setDueno(dueno);
            factura.setFechaEmision(LocalDate.now());
            factura.setTotalFactura(0.0);

            // Registrar factura temporal
            if (!facturaService.registrarFactura(factura)) {
                System.out.println("❌ Error al crear la factura.");
                return;
            }

            System.out.println("✅ Factura creada. Agregue los items:");

            double totalFactura = 0.0;
            boolean continuarAgregando = true;

            while (continuarAgregando) {
                System.out.println("\n¿Qué desea agregar?");
                System.out.println("[1] Servicio médico");
                System.out.println("[2] Producto del inventario");
                System.out.println("[0] Finalizar factura");
                System.out.print("Opción: ");

                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1 -> totalFactura += agregarServicioAFactura(factura);
                    case 2 -> totalFactura += agregarProductoAFactura(factura);
                    case 0 -> continuarAgregando = false;
                    default -> System.out.println("Opción inválida.");
                }
            }

            // Actualizar total de factura
            factura.setTotalFactura(totalFactura);

            // Mostrar factura generada
            mostrarFactura(factura, totalFactura);

        } catch (Exception e) {
            System.out.println("❌ Error al generar factura: " + e.getMessage());
        }
    }

    private double agregarServicioAFactura(Factura factura) {
        try {
            List<Servicio> servicios = servicioService.listarServicios();
            if (servicios.isEmpty()) {
                System.out.println("No hay servicios disponibles.");
                return 0.0;
            }

            System.out.println("\n--- Servicios Disponibles ---");
            for (Servicio s : servicios) {
                System.out.println(s.getIdServicio() + ". " + s.getNombreServicio() + " - $" + s.getPrecio());
            }

            System.out.print("ID del servicio: ");
            int idServicio = Integer.parseInt(scanner.nextLine().trim());

            Servicio servicio = servicioService.obtenerServicioPorId(idServicio);
            if (servicio == null) {
                System.out.println("❌ Servicio no encontrado.");
                return 0.0;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());

            double subtotal = servicio.getPrecio() * cantidad;

            ItemFactura item = new ItemFactura();
            item.setFactura(factura);
            item.setServicio(servicio);
            item.setInventario(null);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(servicio.getPrecio());
            item.setSubtotal(subtotal);

            if (itemsFacturaService.agregarItemFactura(item)) {
                System.out.println("✅ Servicio agregado a la factura.");
                return subtotal;
            } else {
                System.out.println("❌ Error al agregar servicio.");
                return 0.0;
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return 0.0;
        }
    }

    private double agregarProductoAFactura(Factura factura) {
        try {
            List<Inventario> productos = inventarioService.listarInventario();
            if (productos.isEmpty()) {
                System.out.println("No hay productos disponibles.");
                return 0.0;
            }

            System.out.println("\n--- Productos Disponibles ---");
            for (Inventario p : productos) {
                System.out.println(p.getIdInventario() + ". " + p.getNombreProducto() +
                        " - Stock: " + p.getCantidadStock() + " - $" + p.getPrecioVenta());
            }

            System.out.print("ID del producto: ");
            int idProducto = Integer.parseInt(scanner.nextLine().trim());

            Inventario producto = inventarioService.obtenerProductoPorId(idProducto);
            if (producto == null) {
                System.out.println("❌ Producto no encontrado.");
                return 0.0;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());

            if (cantidad > producto.getCantidadStock()) {
                System.out.println("❌ Stock insuficiente. Disponible: " + producto.getCantidadStock());
                return 0.0;
            }

            double subtotal = producto.getPrecioVenta() * cantidad;

            ItemFactura item = new ItemFactura();
            item.setFactura(factura);
            item.setInventario(producto);
            item.setServicio(null);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.getPrecioVenta());
            item.setSubtotal(subtotal);

            if (itemsFacturaService.agregarItemFactura(item)) {
                // Actualizar stock
                int nuevoStock = producto.getCantidadStock() - cantidad;
                inventarioService.actualizarStock(idProducto, nuevoStock);

                System.out.println("✅ Producto agregado a la factura.");
                return subtotal;
            } else {
                System.out.println("❌ Error al agregar producto.");
                return 0.0;
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return 0.0;
        }
    }

    private void mostrarFactura(Factura factura, double total) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           VETERINARIA HAPPY FEET");
        System.out.println("              FACTURA DE VENTA");
        System.out.println("=".repeat(50));
        System.out.println("Factura #: " + factura.getIdFactura());
        System.out.println("Fecha: " + factura.getFechaEmision());
        System.out.println("Cliente: " + factura.getDueno().getNombreDueno());
        System.out.println("Documento: " + factura.getDueno().getDoumentoDueno());
        System.out.println("-".repeat(50));

        try {
            List<ItemFactura> items = itemsFacturaService.obtenerItemsPorFactura(factura.getIdFactura());

            for (ItemFactura item : items) {
                String nombreItem = "";
                if (item.getServicio() != null) {
                    nombreItem = item.getServicio().getNombreServicio() + " (Servicio)";
                } else if (item.getInventario() != null) {
                    nombreItem = item.getInventario().getNombreProducto() + " (Producto)";
                }

                System.out.printf("%-30s %3d x $%8.2f = $%8.2f%n",
                        nombreItem,
                        item.getCantidad(),
                        item.getPrecioUnitario(),
                        item.getSubtotal());
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar items de factura.");
        }

        System.out.println("-".repeat(50));
        System.out.printf("TOTAL: $%.2f%n", total);
        System.out.println("=".repeat(50));
        System.out.println("¡Gracias por su confianza en Happy Feet!");
    }

    public void reporteServiciosSolicitados() {
        System.out.println("\n--- Reporte: Servicios Más Solicitados ---");

        Map<String, Integer> servicios = reporteService.obtenerServiciosMasSolicitados();

        if (servicios.isEmpty()) {
            System.out.println("No hay datos de servicios solicitados.");
            return;
        }

        System.out.println("┌──────────────────────────────┬───────────┐");
        System.out.println("│ Servicio                     │ Solicitudes│");
        System.out.println("├──────────────────────────────┼───────────┤");

        for (Map.Entry<String, Integer> entry : servicios.entrySet()) {
            System.out.printf("│%-30s│%11d│%n",
                    truncar(entry.getKey(), 28),
                    entry.getValue());
        }

        System.out.println("└──────────────────────────────┴───────────┘");
    }

    public void reporteDesempenoVeterinario() {
        System.out.println("\n--- Reporte: Desempeño del Equipo Veterinario ---");

        Map<String, Object> desempeño = reporteService.obtenerDesempenoVeterinario();

        @SuppressWarnings("unchecked")
        Map<String, Map<String, Integer>> citasPorVet =
                (Map<String, Map<String, Integer>>) desempeño.get("citas_por_veterinario");

        if (citasPorVet.isEmpty()) {
            System.out.println("No hay datos de desempeño veterinario.");
            return;
        }

        System.out.println("┌────────────────────┬─────────┬─────────────┬─────────────┐");
        System.out.println("│ Veterinario        │  Total  │ Completadas │ Canceladas  │");
        System.out.println("├────────────────────┼─────────┼─────────────┼─────────────┤");

        for (Map.Entry<String, Map<String, Integer>> entry : citasPorVet.entrySet()) {
            Map<String, Integer> stats = entry.getValue();
            System.out.printf("│%-20s│%9d│%13d│%13d│%n",
                    truncar(entry.getKey(), 18),
                    stats.get("total_citas"),
                    stats.get("citas_completadas"),
                    stats.get("citas_canceladas"));
        }

        System.out.println("└────────────────────┴─────────┴─────────────┴─────────────┘");
    }

    public void reporteEstadoInventario() {
        System.out.println("\n--- Reporte: Estado de Inventario ---");

        List<Inventario> stockBajo = inventarioService.reporteStockBajo();
        List<Inventario> proximosVencer = inventarioService.reporteProximosAVencer();
        List<Inventario> todosProductos = inventarioService.listarInventario();

        System.out.println("📊 RESUMEN GENERAL:");
        System.out.println("• Total productos: " + todosProductos.size());
        System.out.println("• Productos con stock bajo: " + stockBajo.size());
        System.out.println("• Productos próximos a vencer: " + proximosVencer.size());

        double valorTotalInventario = todosProductos.stream()
                .mapToDouble(p -> p.getCantidadStock() * p.getPrecioVenta())
                .sum();
        System.out.printf("• Valor total del inventario: $%.2f%n", valorTotalInventario);

        if (!stockBajo.isEmpty()) {
            System.out.println("\n🔴 PRODUCTOS CON STOCK BAJO:");
            stockBajo.forEach(p ->
                    System.out.println("• " + p.getNombreProducto() +
                            " (Stock: " + p.getCantidadStock() +
                            ", Mínimo: " + p.getStockMinimo() + ")"));
        }

        if (!proximosVencer.isEmpty()) {
            System.out.println("\n🟡 PRODUCTOS PRÓXIMOS A VENCER:");
            proximosVencer.forEach(p ->
                    System.out.println("• " + p.getNombreProducto() +
                            " (Vence: " + p.getFechaVencimiento() + ")"));
        }
    }

    public void reporteFacturacionPeriodo() {
        System.out.println("\n--- Reporte: Facturación por Período ---");

        try {
            System.out.print("Fecha inicio (YYYY-MM-DD): ");
            LocalDate inicio = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Fecha fin (YYYY-MM-DD): ");
            LocalDate fin = LocalDate.parse(scanner.nextLine().trim());

            Map<String, Object> reporte = reporteService.obtenerReporteFacturacion(inicio, fin);

            double total = (Double) reporte.get("total_facturado");
            int cantidad = (Integer) reporte.get("total_facturas");
            double promedio = (Double) reporte.get("promedio_factura");

            System.out.println("\n📊 REPORTE DE FACTURACIÓN");
            System.out.println("Período: " + inicio + " al " + fin);
            System.out.println("-".repeat(40));
            System.out.printf("Total facturado: $%.2f%n", total);
            System.out.println("Número de facturas: " + cantidad);
            System.out.printf("Promedio por factura: $%.2f%n", promedio);

        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de fecha inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "";
        return texto.length() > maxLength ? texto.substring(0, maxLength - 3) + "..." : texto;
    }
}
