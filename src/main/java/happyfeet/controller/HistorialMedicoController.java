package happyfeet.controller;

import happyfeet.model.entities.EventoTipo;
import happyfeet.model.entities.HistorialMedico;
import happyfeet.model.entities.Mascota;
import happyfeet.service.HistorialMedicoService;
import happyfeet.service.MascotaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HistorialMedicoController {
    private final HistorialMedicoService historialService;
    private final MascotaService mascotaService;
    private final Scanner scanner;

    public HistorialMedicoController(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.historialService = new HistorialMedicoService();
        this.mascotaService = new MascotaService();
    }

    // ================== REGISTRAR EVENTO ==================
    public void registrarEvento() throws SQLException {
        System.out.println("\n--- Registrar Evento en Historial Médico ---");

        int idMascota = pedirEntero("Ingrese el ID de la mascota: ");
        Mascota mascota = obtenerMascotaPorId(idMascota);
        if (mascota == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }

        System.out.println("Seleccione el tipo de evento:");
        List<EventoTipo> tipos = listarTiposEventos();
        for (EventoTipo tipo : tipos) {
            System.out.println(tipo.getIdEventoTipo() + " - " + tipo.getNombreEventoTipo());
        }

        int idEvento = pedirEntero("Ingrese el ID del evento: ");
        EventoTipo eventoTipo = obtenerEventoTipoPorId(idEvento);
        if (eventoTipo == null) {
            System.out.println("Tipo de evento no válido.");
            return;
        }

        System.out.print("Ingrese descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese diagnóstico: ");
        String diagnostico = scanner.nextLine();

        HistorialMedico historial = new HistorialMedico(
                0,
                mascota,
                LocalDate.now(),
                eventoTipo,
                descripcion,
                diagnostico
        );

        if (historialService.registrarEvento(historial)) {
            System.out.println("Evento registrado correctamente.");
        } else {
            System.out.println("Error al registrar el evento.");
        }
    }

    // ================== LISTAR EVENTOS ==================
    public void listarEventosPorMascota() {
        int idMascota = pedirEntero("Ingrese el ID de la mascota: ");
        List<HistorialMedico> lista = historialService.listarEventosPorMascota(idMascota);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron eventos para esta mascota.");
        } else {
            lista.forEach(e -> System.out.println(
                    "ID: " + e.getIdHistorialMedico() +
                            " | Fecha: " + e.getFechaEvento() +
                            " | Tipo: " + e.getEventoTipo().getNombreEventoTipo() +
                            " | Descripción: " + e.getDescripcion() +
                            " | Diagnóstico: " + e.getDiagnostico()
            ));
        }
    }

    // ================== MÉTODOS AUXILIARES ==================
    public Mascota obtenerMascotaPorId(int id) throws SQLException {
        return mascotaService.obtenerMascota(id);
    }

    public List<EventoTipo> listarTiposEventos() {
        return historialService.listarTiposEventos();
    }

    public EventoTipo obtenerEventoTipoPorId(int id) {
        return historialService.obtenerEventoTipoPorId(id);
    }

    private int pedirEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }
}
