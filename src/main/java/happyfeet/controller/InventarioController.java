package happyfeet.controller;

import happyfeet.model.entities.Inventario;
import happyfeet.model.entities.ProductoTipo;
import happyfeet.model.entities.Proveedor;
import happyfeet.service.InventarioService;
import happyfeet.service.ProveedorService;
import happyfeet.repository.DAO.ProductoTipoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InventarioController {
    private final Scanner scanner;
    private final InventarioService inventarioService;
    private final ProveedorService proveedorService;
    private final ProductoTipoDAO productoTipoDAO;

    public InventarioController(Scanner scanner) {
        this.scanner = scanner;
        this.inventarioService = new InventarioService();
        this.proveedorService = new ProveedorService();

        Connection connection = Conexion.getConexion();
        this.productoTipoDAO = new ProductoTipoDAO(connection);
    }

    public void registrarProducto() {
        System.out.println("\n--- Registrar Nuevo Medicamento/Insumo ---");

        try {
            System.out.print("Nombre del producto: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre del producto es obligatorio.");
                return;
            }

            // Mostrar tipos de producto disponibles
            System.out.println("\n--- Tipos de Producto Disponibles ---");
            List<ProductoTipo> tipos = productoTipoDAO.listarTodos();
            if (tipos.isEmpty()) {
                System.out.println("No hay tipos de producto registrados. Contacte al administrador.");
                return;
            }

            for (ProductoTipo tipo : tipos) {
                System.out.println(tipo.getIdProductoTipo() + ". " + tipo.getNombreProductoTipo());
            }

            System.out.print("Seleccione el tipo de producto (ID): ");
            int tipoId = Integer.parseInt(scanner.nextLine().trim());

            ProductoTipo tipoSeleccionado = tipos.stream()
                    .filter(t -> t.getIdProductoTipo() == tipoId)
                    .findFirst()
                    .orElse(null);

            if (tipoSeleccionado == null) {
                System.out.println("Tipo de producto no válido.");
                return;
            }

            System.out.print("Descripción (opcional): ");
            String descripcion = scanner.nextLine().trim();
            if (descripcion.isEmpty()) descripcion = null;

            System.out.print("Fabricante: ");
            String fabricante = scanner.nextLine().trim();

            System.out.print("Número de lote: ");
            String lote = scanner.nextLine().trim();

            System.out.print("Cantidad inicial en stock: ");
            int cantidadStock = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Stock mínimo de alerta: ");
            int stockMinimo = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Fecha de vencimiento (YYYY-MM-DD) o Enter para omitir: ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate fechaVencimiento = null;
            if (!fechaStr.isEmpty()) {
                try {
                    fechaVencimiento = LocalDate.parse(fechaStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido. Producto registrado sin fecha de vencimiento.");
                }
            }

            System.out.print("Precio de venta: $");
            double precioVenta = Double.parseDouble(scanner.nextLine().trim());

            // Crear objeto Inventario
            Inventario producto = new Inventario();
            producto.setNombreProducto(nombre);
            producto.setProductoTipo(tipoSeleccionado);
            producto.setDescripcion(descripcion);
            producto.setFabricante(fabricante);
            producto.setLote(lote);
            producto.setCantidadStock(cantidadStock);
            producto.setStockMinimo(stockMinimo);
            producto.setFechaVencimiento(fechaVencimiento);
            producto.setPrecioVenta(precioVenta);

            if (inventarioService.registrarProducto(producto)) {
                System.out.println("✅ Producto registrado exitosamente!");
            } else {
                System.out.println("❌ Error al registrar el producto.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("❌ Error al registrar producto: " + e.getMessage());
        }
    }

    public void actualizarStock() {
        System.out.println("\n--- Actualizar Stock ---");

        try {
            System.out.print("ID del producto: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Inventario producto = inventarioService.obtenerProductoPorId(id);
            if (producto == null) {
                System.out.println("❌ Producto no encontrado.");
                return;
            }

            System.out.println("Producto: " + producto.getNombreProducto());
            System.out.println("Stock actual: " + producto.getCantidadStock());
            System.out.println("Stock mínimo: " + producto.getStockMinimo());

            System.out.print("Nuevo stock: ");
            int nuevoStock = Integer.parseInt(scanner.nextLine().trim());

            if (nuevoStock < 0) {
                System.out.println("❌ El stock no puede ser negativo.");
                return;
            }

            if (inventarioService.actualizarStock(id, nuevoStock)) {
                System.out.println("✅ Stock actualizado exitosamente!");
                if (nuevoStock < producto.getStockMinimo()) {
                    System.out.println("⚠️ ALERTA: El stock está por debajo del mínimo (" + producto.getStockMinimo() + ")");
                }
            } else {
                System.out.println("❌ Error al actualizar el stock.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Error: ID inválido.");
        }
    }

    public void consultarStock() {
        System.out.println("\n--- Stock Actual ---");

        List<Inventario> productos = inventarioService.listarInventario();
        if (productos.isEmpty()) {
            System.out.println("No hay productos en inventario.");
            return;
        }

        System.out.println("┌────┬──────────────────────┬─────────────┬───────┬────────┬──────────┬─────────────┐");
        System.out.println("│ ID │ Producto             │ Tipo        │ Stock │ Mínimo │ Precio   │ Vencimiento │");
        System.out.println("├────┼──────────────────────┼─────────────┼───────┼────────┼──────────┼─────────────┤");

        for (Inventario p : productos) {
            String estado = (p.getCantidadStock() < p.getStockMinimo()) ? " ⚠️" : "";
            String vencimiento = (p.getFechaVencimiento() != null) ? p.getFechaVencimiento().toString() : "N/A";

            System.out.printf("│%-4d│%-22s│%-13s│%-7d│%-8d│$%-9.2f│%-13s│%s%n",
                    p.getIdInventario(),
                    truncar(p.getNombreProducto(), 20),
                    truncar(p.getProductoTipo().getNombreProductoTipo(), 11),
                    p.getCantidadStock(),
                    p.getStockMinimo(),
                    p.getPrecioVenta(),
                    vencimiento,
                    estado);
        }

        System.out.println("└────┴──────────────────────┴─────────────┴───────┴────────┴──────────┴─────────────┘");
        System.out.println("Total de productos: " + productos.size());
    }

    public void verAlertas() {
        System.out.println("\n--- Alertas de Inventario ---");

        // Alerta de stock bajo
        List<Inventario> stockBajo = inventarioService.reporteStockBajo();
        if (!stockBajo.isEmpty()) {
            System.out.println("\n🔴 PRODUCTOS CON STOCK BAJO:");
            System.out.println("┌────┬──────────────────────┬───────┬────────┐");
            System.out.println("│ ID │ Producto             │ Stock │ Mínimo │");
            System.out.println("├────┼──────────────────────┼───────┼────────┤");

            for (Inventario p : stockBajo) {
                System.out.printf("│%-4d│%-22s│%-7d│%-8d│%n",
                        p.getIdInventario(),
                        truncar(p.getNombreProducto(), 20),
                        p.getCantidadStock(),
                        p.getStockMinimo());
            }
            System.out.println("└────┴──────────────────────┴───────┴────────┘");
        }

        // Alerta de productos próximos a vencer
        List<Inventario> proximosAVencer = inventarioService.reporteProximosAVencer();
        if (!proximosAVencer.isEmpty()) {
            System.out.println("\n🟡 PRODUCTOS PRÓXIMOS A VENCER (30 días):");
            System.out.println("┌────┬──────────────────────┬─────────────┬───────┐");
            System.out.println("│ ID │ Producto             │ Vencimiento │ Stock │");
            System.out.println("├────┼──────────────────────┼─────────────┼───────┤");

            for (Inventario p : proximosAVencer) {
                System.out.printf("│%-4d│%-22s│%-13s│%-7d│%n",
                        p.getIdInventario(),
                        truncar(p.getNombreProducto(), 20),
                        p.getFechaVencimiento().toString(),
                        p.getCantidadStock());
            }
            System.out.println("└────┴──────────────────────┴─────────────┴───────┘");
        }

        if (stockBajo.isEmpty() && proximosAVencer.isEmpty()) {
            System.out.println("✅ No hay alertas en este momento.");
        }
    }

    public void registrarProveedor() {
        System.out.println("\n--- Registrar Nuevo Proveedor ---");

        try {
            System.out.print("Nombre del proveedor: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre del proveedor es obligatorio.");
                return;
            }

            System.out.print("Persona de contacto: ");
            String contacto = scanner.nextLine().trim();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Dirección: ");
            String direccion = scanner.nextLine().trim();

            Proveedor proveedor = new Proveedor();
            proveedor.setNombreProveedor(nombre);
            proveedor.setContactoProveedor(contacto);
            proveedor.setTelefonoProveedor(telefono);
            proveedor.setEmailProveedor(email);
            proveedor.setDireccionProveedor(direccion);

            if (proveedorService.registrarProveedor(proveedor)) {
                System.out.println("✅ Proveedor registrado exitosamente!");
            } else {
                System.out.println("❌ Error al registrar el proveedor.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al registrar proveedor: " + e.getMessage());
        }
    }

    public void listarProveedores() {
        System.out.println("\n--- Lista de Proveedores ---");

        List<Proveedor> proveedores = proveedorService.listarProveedores();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        System.out.println("┌────┬──────────────────────┬──────────────────┬────────────────┐");
        System.out.println("│ ID │ Proveedor            │ Contacto         │ Teléfono       │");
        System.out.println("├────┼──────────────────────┼──────────────────┼────────────────┤");

        for (Proveedor p : proveedores) {
            System.out.printf("│%-4d│%-22s│%-18s│%-16s│%n",
                    p.getIdProveedor(),
                    truncar(p.getNombreProveedor(), 20),
                    truncar(p.getContactoProveedor(), 16),
                    truncar(p.getTelefonoProveedor(), 14));
        }

        System.out.println("└────┴──────────────────────┴──────────────────┴────────────────┘");
        System.out.println("Total de proveedores: " + proveedores.size());
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "";
        return texto.length() > maxLength ? texto.substring(0, maxLength - 3) + "..." : texto;
    }
}
