package happyfeet.controller;

import happyfeet.model.entities.Dueno;
import happyfeet.service.DuenoService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DuenoController {

    private final DuenoService duenoService;
    private final Scanner scanner;

    public DuenoController() throws SQLException {
        this.duenoService = new DuenoService();
        this.scanner = new Scanner(System.in);
    }

    /** Registrar un nuevo dueño */
    public void registrarDueno() {
        try {
            System.out.println("\n=== REGISTRAR NUEVO DUEÑO ===");
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("Documento de identidad: ");
            String documento = scanner.nextLine();

            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            Dueno dueno = new Dueno();
            dueno.setNombreDueno(nombre);
            dueno.setDoumentoDueno(documento);
            dueno.setDireccionDueno(direccion);
            dueno.setTelefonoDueno(telefono);
            dueno.setEmailDueno(email);

            boolean exito = duenoService.registrarDueno(dueno);
            System.out.println(exito ? "✅ Dueño registrado con éxito."
                    : "❌ No se pudo registrar el dueño.");
        } catch (Exception e) {
            System.out.println("⚠️ Error al registrar: " + e.getMessage());
        }
    }

    /** Listar todos los dueños */
    public void listarDuenos() {
        try {
            System.out.println("\n=== LISTADO DE DUEÑOS ===");
            List<Dueno> lista = duenoService.listarDuenos();
            if (lista.isEmpty()) {
                System.out.println("No hay dueños registrados.");
            } else {
                for (Dueno d : lista) {
                    System.out.printf("ID:%d | Nombre:%s | Documento:%s | Tel:%s | Email:%s%n",
                            d.getIdDueno(),
                            d.getNombreDueno(),
                            d.getDoumentoDueno(),
                            d.getTelefonoDueno(),
                            d.getEmailDueno());
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al listar: " + e.getMessage());
        }
    }

    /** Buscar un dueño por ID */
    public void buscarPorId() {
        try {
            System.out.print("\nIngrese ID del dueño: ");
            int id = Integer.parseInt(scanner.nextLine());
            Dueno dueno = duenoService.buscarPorId(id);
            if (dueno != null) {
                System.out.println("✅ Dueño encontrado: ");
                System.out.println("Nombre: " + dueno.getNombreDueno());
                System.out.println("Documento: " + dueno.getDoumentoDueno());
                System.out.println("Dirección: " + dueno.getDireccionDueno());
                System.out.println("Teléfono: " + dueno.getTelefonoDueno());
                System.out.println("Email: " + dueno.getEmailDueno());
            } else {
                System.out.println("❌ No se encontró un dueño con ese ID.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Actualizar datos de un dueño */
    public void actualizarDueno() {
        try {
            System.out.print("\nIngrese ID del dueño a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Dueno dueno = duenoService.buscarPorId(id);
            if (dueno == null) {
                System.out.println("❌ Dueño no encontrado.");
                return;
            }

            System.out.println("Deje en blanco para mantener el valor actual.");

            System.out.print("Nuevo nombre (" + dueno.getNombreDueno() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isBlank()) dueno.setNombreDueno(nombre);

            System.out.print("Nuevo documento (" + dueno.getDoumentoDueno() + "): ");
            String documento = scanner.nextLine();
            if (!documento.isBlank()) dueno.setDoumentoDueno(documento);

            System.out.print("Nueva dirección (" + dueno.getDireccionDueno() + "): ");
            String direccion = scanner.nextLine();
            if (!direccion.isBlank()) dueno.setDireccionDueno(direccion);

            System.out.print("Nuevo teléfono (" + dueno.getTelefonoDueno() + "): ");
            String telefono = scanner.nextLine();
            if (!telefono.isBlank()) dueno.setTelefonoDueno(telefono);

            System.out.print("Nuevo email (" + dueno.getEmailDueno() + "): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) dueno.setEmailDueno(email);

            boolean exito = duenoService.actualizarDueno(dueno);
            System.out.println(exito ? "✅ Dueño actualizado correctamente."
                    : "❌ No se pudo actualizar el dueño.");
        } catch (Exception e) {
            System.out.println("⚠️ Error al actualizar: " + e.getMessage());
        }
    }

    /** Eliminar un dueño */
    public void eliminarDueno() {
        try {
            System.out.print("\nIngrese ID del dueño a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean exito = duenoService.eliminarDueno(id);
            System.out.println(exito ? "✅ Dueño eliminado."
                    : "❌ No se pudo eliminar el dueño. Verifique si tiene mascotas asociadas.");
        } catch (Exception e) {
            System.out.println("⚠️ Error al eliminar: " + e.getMessage());
        }
    }
}
