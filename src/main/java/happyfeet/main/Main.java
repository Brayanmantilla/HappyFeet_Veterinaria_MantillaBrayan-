package happyfeet.main;


import happyfeet.view.CitaView;
import happyfeet.view.DuenoView;
import happyfeet.view.MascotaView;

import java.sql.SQLException;
import java.util.Scanner;

    public class Main {

        private final Scanner scanner = new Scanner(System.in);
        private final DuenoView duenoView = new DuenoView(scanner);
        private final MascotaView mascotaView = new MascotaView(scanner);
        private final CitaView citaView = new CitaView(scanner); // ✅ Nueva vista para citas

        public Main() throws SQLException {
        }

        public void iniciarMenu() {
            int opcion;
            do {
                System.out.println("\n🌟 MENÚ PRINCIPAL");
                System.out.println("=========================================");
                System.out.println("     SISTEMA DE GESTIÓN INTEGRAL");
                System.out.println("          VETERINARIA HAPPY FEET");
                System.out.println("=========================================");
                System.out.println("[1] Gestión de Pacientes (Mascotas y Dueños)");
                System.out.println("[2] Servicios Médicos y Citas"); // ✅ Nuevo menú
                System.out.println("[0] Salir del Sistema");
                System.out.println("-----------------------------------------");
                System.out.print("Seleccione una opción: ");
                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> menuGestionPacientes();
                    case 2 -> citaView.mostrarMenu(); // ✅ Llamamos al submenú de citas
                    case 0 -> System.out.println("👋 Saliendo del sistema...");
                    default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
                }
            } while (opcion != 0);
        }

        private void menuGestionPacientes() {
            int opcion;
            do {
                System.out.println("\n----- GESTIÓN DE PACIENTES -----");
                System.out.println("[1] Registrar nuevo dueño");
                System.out.println("[2] Registrar nueva mascota");
                System.out.println("[3] Listar dueños");
                System.out.println("[4] Listar mascotas");
                System.out.println("[5] Consultar/editar ficha de mascota");
                System.out.println("[6] Transferir propiedad de mascota");
                System.out.println("[0] Volver al Menú Principal");
                System.out.print("Seleccione una opción: ");
                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> duenoView.registrarDueno();
                    case 2 -> mascotaView.registrarMascota();
                    case 3 -> duenoView.listarDuenos();
                    case 4 -> mascotaView.listarMascotas();
                    case 5 -> mascotaView.editarMascota();
                    case 6 -> mascotaView.transferirPropiedad();
                    case 0 -> System.out.println("🔙 Volviendo al Menú Principal...");
                    default -> System.out.println("⚠️ Opción inválida, intente de nuevo.");
                }
            } while (opcion != 0);
        }

        private int leerEntero() {
            while (!scanner.hasNextInt()) {
                System.out.print("⚠️ Ingrese un número válido: ");
                scanner.next();
            }
            int valor = scanner.nextInt();
            scanner.nextLine(); // limpiar el salto de línea pendiente
            return valor;
        }

        public static void main(String[] args) throws SQLException {
            new Main().iniciarMenu();
        }
    }
