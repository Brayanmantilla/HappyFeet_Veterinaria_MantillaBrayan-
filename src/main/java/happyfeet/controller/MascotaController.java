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

                // 🔹 NUEVOS CAMPOS
                System.out.print("Peso actual en kg (opcional): ");
                String pesoStr = scanner.nextLine();
                double peso = pesoStr.isBlank() ? 0.0 : Double.parseDouble(pesoStr);

                System.out.print("Número de microchip (opcional MC-####-DQ): ");
                String microchip = scanner.nextLine();

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
                        peso,
                        microchip,
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
                    // Encabezado de la tabla
                    System.out.println("┌────┬──────────────────────┬─────────────────┬─────────┬──────────────┬─────────────────────┐");
                    System.out.println("│ ID │ Nombre               │ Raza            │ Sexo    │ Peso (kg)    │ Dueño               │");
                    System.out.println("├────┼──────────────────────┼─────────────────┼─────────┼──────────────┼─────────────────────┤");

                    for (Mascota m : lista) {
                        String peso = (m.getPeso() != null && m.getPeso() > 0) ?
                                String.format("%.1f", m.getPeso()) : "N/A";

                        String microchip = (m.getMicrochip() != null && !m.getMicrochip().isEmpty()) ?
                                " 🔹" : "";

                        System.out.printf("│%-4d│%-22s│%-17s│%-9s│%-14s│%-21s│%s%n",
                                m.getIdMascota(),
                                truncar(m.getNombreMascota(), 20),
                                truncar(m.getRaza().getNombreRaza(), 15),
                                m.getSexo().getNombre(),
                                peso,
                                truncar(m.getIdDueno().getNombreDueno(), 19),
                                microchip
                        );
                    }

                    System.out.println("└────┴──────────────────────┴─────────────────┴─────────┴──────────────┴─────────────────────┘");
                    System.out.println("Total de mascotas: " + lista.size());
                    System.out.println("🔹 = Tiene microchip registrado");
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Error al listar mascotas: " + e.getMessage());
            }
        }

        /** Método auxiliar para mostrar información detallada de una mascota */
        public void mostrarDetalleMascota() {
            try {
                System.out.print("Ingrese el ID de la mascota para ver detalles: ");
                int id = Integer.parseInt(scanner.nextLine());
                Mascota mascota = mascotaService.obtenerMascota(id);

                if (mascota != null) {
                    System.out.println("\n=== FICHA DETALLADA DE MASCOTA ===");
                    System.out.println("ID: " + mascota.getIdMascota());
                    System.out.println("Nombre: " + mascota.getNombreMascota());
                    System.out.println("Especie: " + mascota.getRaza().getEspecie().getNombreEspecie());
                    System.out.println("Raza: " + mascota.getRaza().getNombreRaza());
                    System.out.println("Sexo: " + mascota.getSexo().getNombre());

                    if (mascota.getFechaNacimiento() != null) {
                        System.out.println("Fecha de nacimiento: " + mascota.getFechaNacimiento());
                    }

                    if (mascota.getPeso() != null && mascota.getPeso() > 0) {
                        System.out.println("Peso: " + mascota.getPeso() + " kg");
                    }

                    if (mascota.getMicrochip() != null && !mascota.getMicrochip().isEmpty()) {
                        System.out.println("Microchip: " + mascota.getMicrochip());
                    }

                    if (mascota.getUrlFoto() != null && !mascota.getUrlFoto().isEmpty()) {
                        System.out.println("Foto: " + mascota.getUrlFoto());
                    }

                    System.out.println("\n--- INFORMACIÓN DEL DUEÑO ---");
                    System.out.println("Nombre: " + mascota.getIdDueno().getNombreDueno());
                    System.out.println("Documento: " + mascota.getIdDueno().getDoumentoDueno());
                    System.out.println("Teléfono: " + mascota.getIdDueno().getTelefonoDueno());
                    System.out.println("Email: " + mascota.getIdDueno().getEmailDueno());

                } else {
                    System.out.println("⚠️ No se encontró ninguna mascota con ese ID.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        /** Método auxiliar para truncar texto */
        private String truncar(String texto, int maxLength) {
            if (texto == null) return "";
            return texto.length() > maxLength ? texto.substring(0, maxLength - 3) + "..." : texto;
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

                System.out.print("Nuevo peso (" + mascota.getPeso() + "): ");
                String pesoStr = scanner.nextLine();
                if (!pesoStr.isBlank()) mascota.setPeso(Double.parseDouble(pesoStr));

                System.out.print("Nuevo microchip (" + mascota.getMicrochip() + "): ");
                String microchip = scanner.nextLine();
                if (!microchip.isBlank()) mascota.setMicrochip(microchip);

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
