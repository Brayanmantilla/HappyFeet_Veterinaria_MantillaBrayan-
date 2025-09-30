package happyfeet.controller;

import happyfeet.model.entities.Cita;
import happyfeet.model.entities.Empleado;
import happyfeet.model.entities.Mascota;
import happyfeet.model.enums.CitaEstado;
import happyfeet.repository.DAO.EmpleadosDAO;
import happyfeet.repository.DAO.MascotaDAO;
import happyfeet.service.CitaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CitaController {

    private final Scanner scanner;
    private final CitaService citaService;
    private final MascotaDAO mascotaDAO;
    private final EmpleadosDAO empleadoDAO;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CitaController(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.citaService = new CitaService();
        this.mascotaDAO = new MascotaDAO();
        this.empleadoDAO = new EmpleadosDAO();
    }

    public void registrarCita() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      REGISTRAR NUEVA CITA             ║");
        System.out.println("╚════════════════════════════════════════╝");

        int idMascota = pedirEntero("ID de la mascota: ");
        Mascota mascota = null;
        try {
            mascota = mascotaDAO.obtenerPorId(idMascota);
        } catch (Exception e) {
            System.out.println("Error al consultar la mascota: " + e.getMessage());
            return;
        }
        if (mascota == null) {
            System.out.println("La mascota con ID " + idMascota + " no existe.");
            return;
        }

        int idEmpleado = pedirEntero("ID del veterinario: ");
        Empleado empleado = empleadoDAO.obtenerPorId(idEmpleado);
        if (empleado == null) {
            System.out.println("El empleado con ID " + idEmpleado + " no existe.");
            return;
        }

        System.out.println("\nSeleccione el estado inicial:");
        for (CitaEstado estado : CitaEstado.values()) {
            System.out.println("[" + estado.getId() + "] " + estado.getNombre());
        }
        int idEstado = pedirEntero("Estado: ");
        CitaEstado estado = CitaEstado.fromId(idEstado);
        if (estado == null) {
            System.out.println("Estado inválido.");
            return;
        }

        String motivo = pedirTexto("Motivo de la cita: ");
        String fecha = pedirTexto("Fecha (AAAA-MM-DD): ");
        LocalDate fechaCita;
        try {
            fechaCita = LocalDate.parse(fecha);
        } catch (Exception e) {
            System.out.println("Fecha inválida.");
            return;
        }

        Cita nueva = new Cita();
        nueva.setMascota(mascota);
        nueva.setEmpleado(empleado);
        nueva.setCitaEstado(estado);
        nueva.setFechaHoraCita(fechaCita);
        nueva.setMotivoCita(motivo);

        boolean exito = false;
        try {
            exito = citaService.registrarCita(nueva);
        } catch (Exception e) {
            System.out.println("Error al registrar la cita: " + e.getMessage());
            return;
        }
        System.out.println(exito ? "\nCita registrada correctamente." : "\nNo se pudo registrar la cita.");
    }

    /** MÉTODO CORREGIDO - Muestra formato resumido */
    public void listarCitas() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CONSULTA DE CITAS                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        List<Cita> lista = citaService.listarAgenda();

        if (lista.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        System.out.println("Total de citas: " + lista.size() + "\n");

        for (Cita c : lista) {
            mostrarCitaResumida(c);
            System.out.println("────────────────────────────────────────────────────────────────");
        }
    }

    /** Mostrar cita en formato RESUMIDO */
    private void mostrarCitaResumida(Cita c) {
        // Línea 1: ID, Fecha y Estado
        System.out.printf(" Cita #%-3d | %-12s | Estado: %s%n",
                c.getIdCita(),
                c.getFechaHoraCita().format(FORMATO_FECHA),
                c.getCitaEstado().getNombre());

        // Línea 2: Mascota y Dueño
        System.out.printf(" Mascota: %-20s | Dueño: %s%n",
                c.getMascota().getNombreMascota(),
                c.getMascota().getIdDueno().getNombreDueno());

        // Línea 3: Veterinario y Motivo
        System.out.printf("👨‍⚕️ Vet: %-25s | 📋 Motivo: %s%n",
                c.getEmpleado().getNombreEmpleado(),
                c.getMotivoCita());
    }

    /** Mostrar cita en formato COMPLETO (opcional para ver detalles) */
    private void mostrarCitaCompleta(Cita c) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.printf("║  🆔 CITA #%-3d                                                 ║%n", c.getIdCita());
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  📅 Fecha:        %-44s║%n", c.getFechaHoraCita().format(FORMATO_FECHA));
        System.out.printf("║  📊 Estado:       %-44s║%n", c.getCitaEstado().getNombre());
        System.out.printf("║  📋 Motivo:       %-44s║%n", c.getMotivoCita());
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  🐾 Mascota:      %-44s║%n", c.getMascota().getNombreMascota());
        System.out.printf("║  🏷️  Raza:         %-44s║%n", c.getMascota().getRaza().getNombreRaza());
        System.out.printf("║  👤 Dueño:        %-44s║%n", c.getMascota().getIdDueno().getNombreDueno());
        System.out.printf("║  📱 Teléfono:     %-44s║%n", c.getMascota().getIdDueno().getTelefonoDueno());
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.printf("║  👨‍⚕️ Veterinario:  %-44s║%n", c.getEmpleado().getNombreEmpleado());
        System.out.printf("║  💼 Cargo:        %-44s║%n", c.getEmpleado().getCargo());
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    public void actualizarEstadoCita(int idCita, CitaEstado nuevoEstado) {
        boolean exito = citaService.actualizarEstado(idCita, nuevoEstado);
        System.out.println(exito ? "\n Estado actualizado correctamente." : "\n No se pudo actualizar.");
    }

    public void eliminarCita() {
        int id = pedirEntero("Ingrese el ID de la cita a eliminar: ");

        System.out.print(" ¿Está seguro? (s/n): ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        boolean exito = citaService.eliminarCita(id);
        System.out.println(exito ? "\n Cita eliminada correctamente." : "\n No se pudo eliminar.");
    }

    private int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
