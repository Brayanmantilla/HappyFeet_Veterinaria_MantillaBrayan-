package happyfeet.controller;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.service.DuenoService;
import happyfeet.service.MascotaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MascotaController {
    private final MascotaService mascotaService;
    private final Scanner scanner;

    public MascotaController() throws SQLException {
        this.mascotaService = new MascotaService();
        this.scanner = new Scanner(System.in);
    }

    /** Registrar una nueva mascota */
    public void registrarMascota() {
        try {
            System.out.println("\n=== REGISTRAR NUEVA MASCOTA ===");
            System.out.print("Nombre de la mascota: ");
            String nombre = scanner.nextLine();

            System.out.print("ID de la raza: ");
            int idRaza = Integer.parseInt(scanner.nextLine());

            System.out.print("Fecha de nacimiento (YYYY-MM-DD, Enter si no aplica): ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaNac = fechaStr.isBlank() ? null
                    : LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            System.out.print("Sexo (MACHO/HEMBRA): ");
            sexoMascota sexo = sexoMascota.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("URL de foto (opcional): ");
            String urlFoto = scanner.nextLine();

            System.out.print("ID del dueño: ");
            int idDueno = Integer.parseInt(scanner.nextLine());

            Mascota mascota = new Mascota();
            mascota.setNombreMascota(nombre);
            mascota.setRaza(new Raza(idRaza));           // Solo necesitamos el ID
            mascota.setFechaNacimiento(fechaNac);
            mascota.setSexo(sexo);
            mascota.setUrlFoto(urlFoto);
            mascota.setIdDueno(new Dueno(idDueno));     // Solo necesitamos el ID

            boolean exito = mascotaService.registrarMascota(mascota);
            System.out.println(exito ? "✅ Mascota registrada con éxito."
                    : "❌ No se pudo registrar la mascota.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Listar todas las mascotas */
    public void listarMascotas() {
        try {
            System.out.println("\n=== LISTADO DE MASCOTAS ===");
            List<Mascota> lista = mascotaService.listarMascotas();
            if (lista.isEmpty()) {
                System.out.println("No hay mascotas registradas.");
            } else {
                for (Mascota m : lista) {
                    System.out.printf("ID:%d | Nombre:%s | Raza:%s | DueñoID:%d%n",
                            m.getIdMascota(),
                            m.getNombreMascota(),
                            m.getRaza().getIdRaza(),        // Puedes mostrar nombre si lo traes
                            m.getIdDueno().getIdDueno());
                }
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error al listar mascotas: " + e.getMessage());
        }
    }

    /** Buscar mascotas por nombre */
    public void buscarPorNombre() {
        try {
            System.out.print("\nIngrese nombre a buscar: ");
            String nombre = scanner.nextLine();
            List<Mascota> lista = mascotaService.buscarPorNombre(nombre);
            if (lista.isEmpty()) {
                System.out.println("No se encontraron coincidencias.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Actualizar una mascota existente */
    public void actualizarMascota() {
        try {
            System.out.print("\nIngrese ID de la mascota a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Mascota mascota = mascotaService.obtenerMascota(id);
            if (mascota == null) {
                System.out.println("❌ Mascota no encontrada.");
                return;
            }

            System.out.println("Deje en blanco para mantener el valor actual.");
            System.out.print("Nuevo nombre (" + mascota.getNombreMascota() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isBlank()) mascota.setNombreMascota(nombre);

            System.out.print("Nueva ID de raza (" + mascota.getRaza().getIdRaza() + "): ");
            String razaStr = scanner.nextLine();
            if (!razaStr.isBlank()) mascota.setRaza(new Raza(Integer.parseInt(razaStr)));

            System.out.print("Nueva fecha nacimiento (" + mascota.getFechaNacimiento() + "): ");
            String fechaStr = scanner.nextLine();
            if (!fechaStr.isBlank()) mascota.setFechaNacimiento(LocalDate.parse(fechaStr));

            System.out.print("Nuevo sexo (" + mascota.getSexo() + "): ");
            String sexoStr = scanner.nextLine();
            if (!sexoStr.isBlank()) mascota.setSexo(sexoMascota.valueOf(sexoStr.toUpperCase()));

            System.out.print("Nueva URL de foto (" + mascota.getUrlFoto() + "): ");
            String urlFoto = scanner.nextLine();
            if (!urlFoto.isBlank()) mascota.setUrlFoto(urlFoto);

            System.out.print("Nuevo ID de dueño (" + mascota.getIdDueno().getIdDueno() + "): ");
            String duenoStr = scanner.nextLine();
            if (!duenoStr.isBlank()) mascota.setIdDueno(new Dueno(Integer.parseInt(duenoStr)));

            boolean exito = mascotaService.actualizarMascota(mascota);
            System.out.println(exito ? "✅ Mascota actualizada."
                    : "❌ No se pudo actualizar.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    /** Eliminar mascota */
    public void eliminarMascota() {
        try {
            System.out.print("\nIngrese ID de la mascota a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean exito = mascotaService.eliminarMascota(id);
            System.out.println(exito ? "✅ Mascota eliminada."
                    : "❌ No se pudo eliminar.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }
}
