package happyfeet.main;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Especie;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.repository.DAO.MascotaDAO;
import happyfeet.service.MascotaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        MascotaService mascotaService = new MascotaService();

        try {
            // 1️⃣ Crear objetos de prueba (IDs deben existir en tu BD)
            Especie especie = new Especie();
            especie.setIdEspecie(1);           // ⚠️ Asegúrate de que exista en la tabla especie
            especie.setNombreEspecie("Canina"); // opcional si solo necesitas el ID

            Raza raza = new Raza();
            raza.setIdRaza(1);                  // ⚠️ Debe existir en la tabla raza
            raza.setNombreRaza("Labrador");
            raza.setEspecie(especie);

            Dueno dueno = new Dueno();
            dueno.setIdDueno(1);                // ⚠️ Debe existir en la tabla dueno

            // 2️⃣ Crear una nueva mascota
            Mascota nuevaMascota = new Mascota();
            nuevaMascota.setNombreMascota("Fido");
            nuevaMascota.setRaza(raza);
            nuevaMascota.setFechaNacimiento(LocalDate.of(2023, 6, 15));
            nuevaMascota.setSexo(sexoMascota.Hembra);
            nuevaMascota.setUrlFoto("https://ejemplo.com/fido.jpg");
            nuevaMascota.setIdDueno(dueno);

            System.out.println("👉 Insertando nueva mascota...");
            mascotaService.registrarMascota(nuevaMascota);
            System.out.println("✅ Mascota registrada con ID: " + nuevaMascota.getIdMascota());

            // 3️⃣ Listar todas las mascotas
            System.out.println("\n👉 Listando mascotas registradas...");
            List<Mascota> mascotas = mascotaService.listarMascotas();
            for (Mascota m : mascotas) {
                System.out.println(m);
            }

            // 4️⃣ Obtener una mascota por ID
            System.out.println("\n👉 Buscando mascota con ID 1...");
            Mascota encontrada = mascotaService.obtenerMascota(1);
            if (encontrada != null) {
                System.out.println("✅ Mascota encontrada: " + encontrada);
            } else {
                System.out.println("⚠️ No se encontró la mascota con ID 1.");
            }

            // 5️⃣ Actualizar una mascota existente
            if (encontrada != null) {
                encontrada.setNombreMascota("Fido Actualizado");
                mascotaService.actualizarMascota(encontrada);
                System.out.println("✅ Mascota actualizada correctamente.");
            }

            // 6️⃣ Eliminar una mascota
            System.out.println("\n👉 Eliminando mascota con ID 1...");
            mascotaService.eliminarMascota(1);
            System.out.println("✅ Mascota eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("❌ Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("⚠️ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}