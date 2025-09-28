package happyfeet.controller;

import happyfeet.model.entities.*;
import happyfeet.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ActividadesController {

    private final Scanner scanner;
    private final AdopcionService adopcionService;
    private final JornadaVacunacionService jornadaService;
    private final ClubMascotaService clubService;
    private final MascotaService mascotaService;
    private final DuenoService duenoService;
    private final EmpleadoService empleadoService;

    public ActividadesController(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.adopcionService = new AdopcionService();
        this.jornadaService = new JornadaVacunacionService();
        this.clubService = new ClubMascotaService();
        this.mascotaService = new MascotaService();
        this.duenoService = new DuenoService();
        this.empleadoService = new EmpleadoService();
    }

    public void registrarMascotaAdopcion() {
        System.out.println("\n--- Registrar Mascota para Adopción ---");

        try {
            System.out.print("ID de la mascota: ");
            int idMascota = Integer.parseInt(scanner.nextLine().trim());

            Mascota mascota = mascotaService.obtenerMascota(idMascota);
            if (mascota == null) {
                System.out.println("Mascota no encontrada.");
                return;
            }

            System.out.println("Mascota: " + mascota.getNombreMascota());
            System.out.println("Dueño actual: " + mascota.getIdDueno().getNombreDueno());

            System.out.print("Motivo de la adopción: ");
            String motivo = scanner.nextLine().trim();

            System.out.print("Observaciones adicionales: ");
            String observaciones = scanner.nextLine().trim();

            Adopcion adopcion = new Adopcion();
            adopcion.setMascota(mascota);
            adopcion.setDuenoAnterior(mascota.getIdDueno());
            adopcion.setFechaDisponible(LocalDate.now());
            adopcion.setEstado("Disponible");
            adopcion.setMotivoAdopcion(motivo);
            adopcion.setObservaciones(observaciones);

            if (adopcionService.registrarParaAdopcion(adopcion)) {
                System.out.println("Mascota registrada para adopción exitosamente!");
            } else {
                System.out.println("Error al registrar la mascota para adopción.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void generarContratoAdopcion() {
        System.out.println("\n--- Generar Contrato de Adopción ---");

        try {
            List<Adopcion> disponibles = adopcionService.listarMascotasDisponibles();
            if (disponibles.isEmpty()) {
                System.out.println("No hay mascotas disponibles para adopción.");
                return;
            }

            System.out.println("\n--- Mascotas Disponibles ---");
            for (Adopcion a : disponibles) {
                System.out.println(a.getIdAdopcion() + ". " + a.getMascota().getNombreMascota() +
                        " (" + a.getMascota().getRaza().getNombreRaza() + ") - " +
                        "Disponible desde: " + a.getFechaDisponible());
            }

            System.out.print("ID de la adopción: ");
            int idAdopcion = Integer.parseInt(scanner.nextLine().trim());

            Adopcion adopcionSeleccionada = disponibles.stream()
                    .filter(a -> a.getIdAdopcion() == idAdopcion)
                    .findFirst()
                    .orElse(null);

            if (adopcionSeleccionada == null) {
                System.out.println("Adopción no encontrada.");
                return;
            }

            System.out.print("ID del nuevo dueño: ");
            int idNuevoDueno = Integer.parseInt(scanner.nextLine().trim());

            Dueno nuevoDueno = duenoService.buscarPorId(idNuevoDueno);
            if (nuevoDueno == null) {
                System.out.println("Nuevo dueño no encontrado.");
                return;
            }

            // Actualizar adopción
            adopcionSeleccionada.setDuenoNuevo(nuevoDueno);
            adopcionSeleccionada.setFechaAdopcion(LocalDate.now());
            adopcionSeleccionada.setEstado("Adoptada");

            if (adopcionService.procesarAdopcion(adopcionSeleccionada)) {
                generarContratoTexto(adopcionSeleccionada);
            } else {
                System.out.println("Error al procesar la adopción.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generarContratoTexto(Adopcion adopcion) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                CONTRATO DE ADOPCIÓN");
        System.out.println("                VETERINARIA HAPPY FEET");
        System.out.println("=".repeat(60));
        System.out.println("Fecha del contrato: " + LocalDate.now());
        System.out.println("Número de adopción: " + adopcion.getIdAdopcion());
        System.out.println();
        System.out.println("INFORMACIÓN DE LA MASCOTA:");
        System.out.println("• Nombre: " + adopcion.getMascota().getNombreMascota());
        System.out.println("• Raza: " + adopcion.getMascota().getRaza().getNombreRaza());
        System.out.println("• Sexo: " + adopcion.getMascota().getSexo());
        if (adopcion.getMascota().getFechaNacimiento() != null) {
            System.out.println("• Fecha de nacimiento: " + adopcion.getMascota().getFechaNacimiento());
        }
        System.out.println();
        System.out.println("DUEÑO ANTERIOR:");
        System.out.println("• Nombre: " + adopcion.getDuenoAnterior().getNombreDueno());
        System.out.println("• Documento: " + adopcion.getDuenoAnterior().getDoumentoDueno());
        System.out.println();
        System.out.println("NUEVO PROPIETARIO:");
        System.out.println("• Nombre: " + adopcion.getDuenoNuevo().getNombreDueno());
        System.out.println("• Documento: " + adopcion.getDuenoNuevo().getDoumentoDueno());
        System.out.println("• Teléfono: " + adopcion.getDuenoNuevo().getTelefonoDueno());
        System.out.println("• Email: " + adopcion.getDuenoNuevo().getEmailDueno());
        System.out.println();
        System.out.println("CONDICIONES DE LA ADOPCIÓN:");
        System.out.println("• El nuevo propietario se compromete a brindar cuidado responsable");
        System.out.println("• Proporcionar alimentación, atención médica y amor");
        System.out.println("• Notificar cambios de domicilio a la veterinaria");
        System.out.println("• No abandono ni maltrato de la mascota");
        System.out.println();
        System.out.println("Motivo: " + adopcion.getMotivoAdopcion());
        if (adopcion.getObservaciones() != null && !adopcion.getObservaciones().isEmpty()) {
            System.out.println("Observaciones: " + adopcion.getObservaciones());
        }
        System.out.println();
        System.out.println("_________________________    _________________________");
        System.out.println("   Firma Dueño Anterior          Firma Nuevo Dueño");
        System.out.println();
        System.out.println("_________________________");
        System.out.println("    Veterinaria Happy Feet");
        System.out.println("=".repeat(60));
        System.out.println("Contrato generado exitosamente!");
    }
    public void registrarJornadaVacunacion() {
        System.out.println("\n--- Registrar Jornada de Vacunación ---");

        try {
            System.out.print("Nombre de la jornada: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Fecha de la jornada (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Lugar: ");
            String lugar = scanner.nextLine().trim();

            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine().trim();

            // Listar empleados veterinarios
            List<Empleado> empleados = empleadoService.listarEmpleados();
            System.out.println("\n--- Empleados Disponibles ---");
            for (Empleado e : empleados) {
                System.out.println(e.getIdEmpleado() + ". " + e.getNombreEmpleado() + " (" + e.getCargo() + ")");
            }

            System.out.print("ID del empleado responsable: ");
            int idEmpleado = Integer.parseInt(scanner.nextLine().trim());

            Empleado empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);
            if (empleado == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }

            JornadaVacunacion jornada = new JornadaVacunacion();
            jornada.setNombreJornada(nombre);
            jornada.setFechaJornada(fecha);
            jornada.setLugar(lugar);
            jornada.setDescripcion(descripcion);
            jornada.setEmpleadoResponsable(empleado);
            jornada.setEstado("Programada");

            if (jornadaService.registrarJornada(jornada)) {
                System.out.println("Jornada de vacunación registrada exitosamente!");
                System.out.println("ID de la jornada: " + jornada.getIdJornada());
            } else {
                System.out.println("Error al registrar la jornada.");
            }

        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void gestionarClubMascotas() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n----- CLUB DE MASCOTAS FRECUENTES -----");
            System.out.println("[1] Inscribir nuevo miembro");
            System.out.println("[2] Consultar puntos de un cliente");
            System.out.println("[3] Agregar puntos por compra");
            System.out.println("[4] Ver ranking de miembros");
            System.out.println("[5] Mostrar beneficios disponibles");
            System.out.println("[0] Volver al menú anterior");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> inscribirNuevoMiembro();
                case 2 -> consultarPuntos();
                case 3 -> agregarPuntosPorCompra();
                case 4 -> mostrarRanking();
                case 5 -> mostrarBeneficios();
                case 0 -> continuar = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }
    private void inscribirNuevoMiembro() {
        System.out.println("\n--- Inscribir Nuevo Miembro al Club ---");

        try {
            System.out.print("ID del dueño: ");
            int idDueno = Integer.parseInt(scanner.nextLine().trim());

            Dueno dueno = duenoService.buscarPorId(idDueno);
            if (dueno == null) {
                System.out.println("Dueño no encontrado.");
                return;
            }

            // Verificar si ya está inscrito
            ClubMascota clubExistente = clubService.obtenerPorDueno(idDueno);
            if (clubExistente != null) {
                System.out.println("Este cliente ya está inscrito en el club.");
                System.out.println("Puntos actuales: " + clubExistente.getPuntosAcumulados());
                System.out.println("Nivel: " + clubExistente.getNivel());
                return;
            }

            ClubMascota nuevoClub = new ClubMascota();
            nuevoClub.setDueno(dueno);

            if (clubService.inscribirEnClub(nuevoClub)) {
                System.out.println("Cliente inscrito exitosamente en el Club de Mascotas Frecuentes!");
                System.out.println("Cliente: " + dueno.getNombreDueno());
                System.out.println("Nivel inicial: Bronce");
                System.out.println("Puntos iniciales: 0");
            } else {
                System.out.println("Error al inscribir al cliente.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultarPuntos() {
        System.out.println("\n--- Consultar Puntos ---");

        try {
            System.out.print("ID del dueño: ");
            int idDueno = Integer.parseInt(scanner.nextLine().trim());

            ClubMascota club = clubService.obtenerPorDueno(idDueno);
            if (club == null) {
                System.out.println("Este cliente no está inscrito en el club.");
                return;
            }

            System.out.println("\n┌─────────── INFORMACIÓN DEL MIEMBRO ───────────┐");
            System.out.println("│ Cliente: " + club.getDueno().getNombreDueno());
            System.out.println("│ Puntos acumulados: " + club.getPuntosAcumulados());
            System.out.println("│ Nivel actual: " + club.getNivel());
            System.out.println("│ Estado: " + club.getEstado());
            System.out.println("│ Inscrito desde: " + club.getFechaInscripcion());
            System.out.println("└─────────────────────────────────────────────────┘");

            // Mostrar próximo nivel
            String proximoNivel = obtenerProximoNivel(club.getNivel());
            int puntosNecesarios = obtenerPuntosParaNivel(proximoNivel) - club.getPuntosAcumulados();

            if (puntosNecesarios > 0) {
                System.out.println("Para alcanzar " + proximoNivel + " necesita " + puntosNecesarios + " puntos más.");
            } else {
                System.out.println("¡Ha alcanzado el nivel máximo!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarPuntosPorCompra() {
        System.out.println("\n--- Agregar Puntos por Compra ---");

        try {
            System.out.print("ID del dueño: ");
            int idDueno = Integer.parseInt(scanner.nextLine().trim());

            ClubMascota club = clubService.obtenerPorDueno(idDueno);
            if (club == null) {
                System.out.println("Este cliente no está inscrito en el club.");
                return;
            }

            System.out.print("Monto de la compra: $");
            double montoCompra = Double.parseDouble(scanner.nextLine().trim());

            // 1 punto por cada $10 gastados
            int puntosGanados = (int) (montoCompra / 10);

            if (puntosGanados > 0) {
                if (clubService.agregarPuntos(idDueno, puntosGanados)) {
                    // Obtener datos actualizados
                    ClubMascota clubActualizado = clubService.obtenerPorDueno(idDueno);

                    System.out.println("Puntos agregados exitosamente!");
                    System.out.println("Puntos ganados: " + puntosGanados);
                    System.out.println("Total de puntos: " + clubActualizado.getPuntosAcumulados());
                    System.out.println("Nivel actual: " + clubActualizado.getNivel());

                    if (!club.getNivel().equals(clubActualizado.getNivel())) {
                        System.out.println("¡FELICITACIONES! Ha subido a nivel " + clubActualizado.getNivel() + "!");
                    }
                } else {
                    System.out.println("Error al agregar puntos.");
                }
            } else {
                System.out.println("El monto mínimo para ganar puntos es $10.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarRanking() {
        System.out.println("\n--- Ranking de Miembros del Club ---");

        List<ClubMascota> miembros = clubService.listarMiembros();
        if (miembros.isEmpty()) {
            System.out.println("No hay miembros registrados en el club.");
            return;
        }

        System.out.println("┌─────┬──────────────────────┬─────────┬───────────┐");
        System.out.println("│ Pos │ Cliente              │ Puntos  │ Nivel     │");
        System.out.println("├─────┼──────────────────────┼─────────┼───────────┤");

        for (int i = 0; i < miembros.size(); i++) {
            ClubMascota club = miembros.get(i);
            String emoji = (i == 0) ? "🥇" : (i == 1) ? "🥈" : (i == 2) ? "🥉" : "  ";

            System.out.printf("│%s%-3d│%-22s│%9d│%-11s│%n",
                    emoji, (i + 1),
                    truncar(club.getDueno().getNombreDueno(), 20),
                    club.getPuntosAcumulados(),
                    club.getNivel());
        }

        System.out.println("└─────┴──────────────────────┴─────────┴───────────┘");
        System.out.println("Total de miembros activos: " + miembros.size());
    }

    private void mostrarBeneficios() {
        System.out.println("\n--- Beneficios Disponibles ---");
        System.out.println("┌─────────────────────────────────┬─────────┬───────────┐");
        System.out.println("│ Beneficio                       │ Puntos  │ Nivel     │");
        System.out.println("├─────────────────────────────────┼─────────┼───────────┤");
        System.out.println("│ Descuento 10% en consultas      │     100 │ Bronce    │");
        System.out.println("│ Descuento 15% en medicamentos   │     200 │ Plata     │");
        System.out.println("│ Consulta gratis                 │     500 │ Oro       │");
        System.out.println("│ Cirugía con 20% descuento       │    1000 │ Diamante  │");
        System.out.println("└─────────────────────────────────┴─────────┴───────────┘");
        System.out.println("\nNiveles del Club:");
        System.out.println("• Bronce: 0-199 puntos");
        System.out.println("• Plata: 200-499 puntos");
        System.out.println("• Oro: 500-999 puntos");
        System.out.println("• Diamante: 1000+ puntos");
        System.out.println("\nGana 1 punto por cada $10 en compras");
    }

    // Métodos auxiliares
    private String obtenerProximoNivel(String nivelActual) {
        return switch (nivelActual) {
            case "Bronce" -> "Plata";
            case "Plata" -> "Oro";
            case "Oro" -> "Diamante";
            default -> "Diamante";
        };
    }

    private int obtenerPuntosParaNivel(String nivel) {
        return switch (nivel) {
            case "Plata" -> 200;
            case "Oro" -> 500;
            case "Diamante" -> 1000;
            default -> 0;
        };
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "";
        return texto.length() > maxLength ? texto.substring(0, maxLength - 3) + "..." : texto;
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void listarJornadas() {
        System.out.println("\n--- Lista de Jornadas de Vacunación ---");

        List<JornadaVacunacion> jornadas = jornadaService.listarJornadas();
        if (jornadas.isEmpty()) {
            System.out.println("No hay jornadas registradas.");
            return;
        }

        System.out.println("┌────┬──────────────────────┬─────────────┬─────────────┬──────────────┐");
        System.out.println("│ ID │ Nombre Jornada       │ Fecha       │ Estado      │ Responsable  │");
        System.out.println("├────┼──────────────────────┼─────────────┼─────────────┼──────────────┤");

        for (JornadaVacunacion j : jornadas) {
            String responsable = (j.getEmpleadoResponsable() != null)
                    ? j.getEmpleadoResponsable().getNombreEmpleado()
                    : "Sin asignar";

            System.out.printf("│%-4d│%-22s│%-13s│%-13s│%-14s│%n",
                    j.getIdJornada(),
                    truncar(j.getNombreJornada(), 20),
                    j.getFechaJornada().toString(),
                    j.getEstado(),
                    truncar(responsable, 12));
        }

        System.out.println("└────┴──────────────────────┴─────────────┴─────────────┴──────────────┘");
        System.out.println("Total de jornadas: " + jornadas.size());
    }
}