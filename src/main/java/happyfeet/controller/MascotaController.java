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

        public MascotaController(Scanner scanner) throws SQLException {
            this.mascotaService = new MascotaService();
            this.scanner = scanner;
        }

        /** Registrar una nueva mascota */
        public void registrarMascota() {
            try {
                System.out.println("\n--- Registrar nueva mascota ---");
                System.out.print("Nombre de la mascota: ");
                String nombre = scanner.nextLine();

                System.out.print("ID de la raza: ");
                int idRaza = Integer.parseInt(scanner.nextLine());

                System.out.print("Fecha de nacimiento (YYYY-MM-DD) o Enter para omitir: ");
                String fechaStr = scanner.nextLine();
                LocalDate fechaNac = fechaStr.isBlank() ? null : LocalDate.parse(fechaStr);

                System.out.print("Sexo (Macho/Hembra): ");
                sexoMascota sexo = sexoMascota.valueOf(scanner.nextLine().trim());

                System.out.print("URL de la foto (opcional): ");
                String urlFoto = scanner.nextLine();

                System.out.print("ID del dueño: ");
                int idDueno = Integer.parseInt(scanner.nextLine());

                Mascota mascota = new Mascota(
                        0,
                        nombre,
                        new Raza(idRaza),
                        fechaNac,
                        sexo,
                        urlFoto,
                        new Dueno(idDueno)
                );

                if (mascotaService.registrarMascota(mascota)) {
                    System.out.println("✅ Mascota registrada correctamente.");
                } else {
                    System.out.println("❌ Error al registrar la mascota.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        /** Listar todas las mascotas */
        public void listarMascotas() {
            try {
                System.out.println("\n--- Lista de Mascotas ---");
                List<Mascota> lista = mascotaService.listarMascotas();
                if (lista.isEmpty()) {
                    System.out.println("No hay mascotas registradas.");
                } else {
                    for (Mascota m : lista) {
                        System.out.println(m);
                    }
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Error al listar mascotas: " + e.getMessage());
            }
        }

        /** Buscar mascota por ID */
        public void buscarMascotaPorId() {
            try {
                System.out.print("Ingrese el ID de la mascota: ");
                int id = Integer.parseInt(scanner.nextLine());
                Mascota mascota = mascotaService.obtenerMascota(id);
                if (mascota != null) {
                    System.out.println("✅ Mascota encontrada: " + mascota);
                } else {
                    System.out.println("⚠️ No se encontró ninguna mascota con ese ID.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        /** Actualizar datos de una mascota */
        public void actualizarMascota() {
            try {
                System.out.print("Ingrese el ID de la mascota a actualizar: ");
                int id = Integer.parseInt(scanner.nextLine());

                Mascota mascota = mascotaService.obtenerMascota(id);
                if (mascota == null) {
                    System.out.println("⚠️ No existe una mascota con ese ID.");
                    return;
                }

                System.out.print("Nuevo nombre (" + mascota.getNombreMascota() + "): ");
                String nombre = scanner.nextLine();
                if (!nombre.isBlank()) mascota.setNombreMascota(nombre);

                System.out.print("Nueva URL de foto (" + mascota.getUrlFoto() + "): ");
                String url = scanner.nextLine();
                if (!url.isBlank()) mascota.setUrlFoto(url);

                if (mascotaService.actualizarMascota(mascota)) {
                    System.out.println("✅ Mascota actualizada correctamente.");
                } else {
                    System.out.println("❌ Error al actualizar la mascota.");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        /** Eliminar mascota por ID */
        public void eliminarMascota() {
            try {
                System.out.print("Ingrese el ID de la mascota a eliminar: ");
                int id = Integer.parseInt(scanner.nextLine());
                if (mascotaService.eliminarMascota(id)) {
                    System.out.println("✅ Mascota eliminada correctamente.");
                } else {
                    System.out.println("❌ No se pudo eliminar la mascota (verifique dependencias).");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
}
