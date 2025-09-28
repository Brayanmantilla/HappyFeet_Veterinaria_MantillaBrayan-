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
import java.util.List;
import java.util.Scanner;

public class CitaController {

    private final Scanner scanner;
    private final CitaService citaService;
    private final MascotaDAO mascotaDAO;
    private final EmpleadosDAO empleadoDAO;

    public CitaController(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.citaService = new CitaService();
        this.mascotaDAO = new MascotaDAO();
        this.empleadoDAO = new EmpleadosDAO();
    }

    public void registrarCita() {
        System.out.println("\n--- Registrar Nueva Cita ---");

        int idMascota = pedirEntero("Ingrese el ID de la mascota: ");
        Mascota mascota = null;
        try {
            mascota = mascotaDAO.obtenerPorId(idMascota);
        } catch (Exception e) {
            System.out.println("❌ Error al consultar la mascota: " + e.getMessage());
            return;
        }
        if (mascota == null) {
            System.out.println("❌ La mascota con ID " + idMascota + " no existe.");
            return;
        }

        int idEmpleado = pedirEntero("Ingrese el ID del empleado (veterinario): ");
        Empleado empleado = empleadoDAO.obtenerPorId(idEmpleado);
        if (empleado == null) {
            System.out.println("❌ El empleado con ID " + idEmpleado + " no existe.");
            return;
        }

        System.out.println("Seleccione el estado inicial de la cita:");
        for (CitaEstado estado : CitaEstado.values()) {
            System.out.println(estado.getId() + " - " + estado.getNombre());
        }
        int idEstado = pedirEntero("Ingrese el ID del estado: ");
        CitaEstado estado = CitaEstado.fromId(idEstado);
        if (estado == null) {
            System.out.println("❌ Estado inválido.");
            return;
        }

        String motivo = pedirTexto("Ingrese el motivo de la cita: ");
        String fecha = pedirTexto("Ingrese la fecha de la cita (AAAA-MM-DD): ");
        LocalDate fechaCita;
        try {
            fechaCita = LocalDate.parse(fecha);
        } catch (Exception e) {
            System.out.println("❌ Fecha inválida.");
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
            System.out.println("❌ Error al registrar la cita: " + e.getMessage());
            return;
        }
        System.out.println(exito ? "✅ Cita registrada correctamente." : "❌ No se pudo registrar la cita.");
    }

    public void listarCitas() {
        List<Cita> lista = citaService.listarAgenda();
        if (lista.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        for (Cita c : lista) {
            System.out.println(c);
        }
    }

    public void actualizarEstadoCita(int idCita, CitaEstado nuevoEstado) {
        boolean exito = citaService.actualizarEstado(idCita, nuevoEstado);
        System.out.println(exito ? "✅ Estado actualizado correctamente." : "❌ No se pudo actualizar.");
    }

    public void eliminarCita() {
        int id = pedirEntero("Ingrese el ID de la cita a eliminar: ");
        boolean exito = citaService.eliminarCita(id);
        System.out.println(exito ? "✅ Cita eliminada correctamente." : "❌ No se pudo eliminar.");
    }

    private int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("⚠️ Ingrese un número válido: ");
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
