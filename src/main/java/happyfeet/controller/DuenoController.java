package happyfeet.controller;

import happyfeet.model.entities.Dueno;
import happyfeet.service.DuenoService;
import happyfeet.service.MascotaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DuenoController {

    private final DuenoService duenoService;
    private final Scanner scanner;

    public DuenoController(Scanner scanner) throws SQLException {
        this.duenoService = new DuenoService();
        this.scanner = scanner;
    }

    /** Registrar un nuevo dueño */
    public void registrarDueno() {
        try {
            System.out.println("\n--- Registrar nuevo dueño ---");
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

            // ⚠️ Orden correcto según el constructor
            Dueno dueno = new Dueno(
                    0,
                    telefono,
                    email,
                    documento,
                    nombre,
                    direccion
            );

            if (duenoService.registrarDueno(dueno)) {
                System.out.println("✅ Dueño registrado correctamente.");
            } else {
                System.out.println("❌ Error al registrar dueño.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Listar todos los dueños */
    public void listarDuenos() {
        try {
            System.out.println("\n--- Lista de Dueños ---");
            List<Dueno> lista = duenoService.listarDuenos();
            if (lista.isEmpty()) {
                System.out.println("No hay dueños registrados.");
            } else {
                for (Dueno d : lista) {
                    System.out.printf("ID: %d | Nombre: %s | Documento: %s | Tel: %s | Email: %s | Dirección: %s%n",
                            d.getIdDueno(),
                            d.getNombreDueno(),
                            d.getDoumentoDueno(),
                            d.getTelefonoDueno(),
                            d.getEmailDueno(),
                            d.getDireccionDueno());
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al listar dueños: " + e.getMessage());
        }
    }

    /** Buscar un dueño por ID y mostrarlo */
    public Dueno buscarDuenoPorId() {
        try {
            System.out.print("Ingrese el ID del dueño: ");
            int id = Integer.parseInt(scanner.nextLine());
            Dueno dueno = duenoService.buscarPorId(id);
            if (dueno != null) {
                mostrarInfoDueno(dueno);
            } else {
                System.out.println("⚠️ No se encontró ningún dueño con ese ID.");
            }
            return dueno;
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            return null;
        }
    }

    /** Actualizar datos de un dueño */
    public void actualizarDueno() {
        try {
            System.out.print("Ingrese el ID del dueño a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Dueno dueno = duenoService.buscarPorId(id);

            if (dueno == null) {
                System.out.println("⚠️ No existe un dueño con ese ID.");
                return;
            }

            System.out.println("\n--- Datos actuales ---");
            mostrarInfoDueno(dueno);

            System.out.println("\nIngrese los nuevos datos (dejar vacío para mantener actual):");
            System.out.print("Nombre [" + dueno.getNombreDueno() + "]: ");
            String nombre = scanner.nextLine();
            if (!nombre.isBlank()) dueno.setNombreDueno(nombre);

            System.out.print("Documento [" + dueno.getDoumentoDueno() + "]: ");
            String documento = scanner.nextLine();
            if (!documento.isBlank()) dueno.setDoumentoDueno(documento);

            System.out.print("Dirección [" + dueno.getDireccionDueno() + "]: ");
            String direccion = scanner.nextLine();
            if (!direccion.isBlank()) dueno.setDireccionDueno(direccion);

            System.out.print("Teléfono [" + dueno.getTelefonoDueno() + "]: ");
            String telefono = scanner.nextLine();
            if (!telefono.isBlank()) dueno.setTelefonoDueno(telefono);

            System.out.print("Email [" + dueno.getEmailDueno() + "]: ");
            String email = scanner.nextLine();
            if (!email.isBlank()) dueno.setEmailDueno(email);

            if (duenoService.actualizarDueno(dueno)) {
                System.out.println("✅ Datos actualizados correctamente.");
            } else {
                System.out.println("❌ No se pudo actualizar el registro.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Eliminar un dueño */
    public void eliminarDueno() {
        try {
            System.out.print("Ingrese el ID del dueño a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("¿Está seguro de eliminar este dueño? (s/n): ");
            String confirm = scanner.nextLine();
            if (!confirm.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (duenoService.eliminarDueno(id)) {
                System.out.println("✅ Dueño eliminado correctamente.");
            } else {
                System.out.println("❌ No se pudo eliminar (verifique si tiene mascotas registradas).");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Utilidad para mostrar información de un dueño */
    private void mostrarInfoDueno(Dueno d) {
        System.out.printf("ID: %d | Nombre: %s | Documento: %s | Tel: %s | Email: %s | Dirección: %s%n",
                d.getIdDueno(),
                d.getNombreDueno(),
                d.getDoumentoDueno(),
                d.getTelefonoDueno(),
                d.getEmailDueno(),
                d.getDireccionDueno());
    }
}
