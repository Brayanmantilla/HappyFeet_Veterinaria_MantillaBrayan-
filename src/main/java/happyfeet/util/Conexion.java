package happyfeet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

/**
 * Clase para gestionar la conexión a la base de datos
 * Solicita credenciales al usuario mediante consola
 */
public class Conexion {

    private static String baseDatos = null;
    private static String usuario = null;
    private static String password = null;
    private static boolean configurado = false;

    /**
     * Solicita las credenciales al usuario una sola vez
     */
    private static void configurarCredenciales() {
        if (!configurado) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   CONFIGURACIÓN DE BASE DE DATOS      ║");
            System.out.println("╚════════════════════════════════════════╝\n");

            System.out.print("Nombre de la base de datos: ");
            String inputBD = scanner.nextLine().trim();
            baseDatos = inputBD.isEmpty() ? "happyfeetveterinariaa_" : inputBD;

            System.out.print("Usuario de MySQL: ");
            String inputUsuario = scanner.nextLine().trim();
            usuario = inputUsuario.isEmpty() ? "root" : inputUsuario;

            System.out.print("Contraseña: ");
            password = scanner.nextLine();

            configurado = true;

            System.out.println("\nCredenciales configuradas correctamente\n");
        }
    }

    /**
     * Obtiene la conexión a la base de datos
     */
    public static Connection getConexion() {
        Connection conexion = null;

        try {
            // Configurar credenciales si no están configuradas
            configurarCredenciales();

            // Construir URL de conexión
            String url = "jdbc:mysql://localhost:3306/" + baseDatos;

            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            conexion = DriverManager.getConnection(url, usuario, password);

        } catch (Exception e) {
            System.err.println("Error al conectar a la BD: " + e.getMessage());
        }

        return conexion;
    }

    /**
     * Reinicia la configuración (útil si las credenciales son incorrectas)
     */
    public static void resetearConfiguracion() {
        configurado = false;
        baseDatos = null;
        usuario = null;
        password = null;
        System.out.println(" Configuración reseteada. Se solicitarán nuevas credenciales.");
    }

    /**
     * Método para probar la conexión
     */
    public static void main(String[] args) {
        System.out.println("Sistema de Gestión Veterinaria - Happy Feet");
        System.out.println("═══════════════════════════════════════════════\n");

        Connection conexion = Conexion.getConexion();

        if (conexion != null) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║         CONEXIÓN EXITOSA A LA BD       ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\nBase de datos: " + baseDatos);
            System.out.println("Usuario: " + usuario);

            try {
                conexion.close();
                System.out.println("\n Conexión cerrada correctamente");
            } catch (Exception e) {
                System.err.println(" Error al cerrar conexión: " + e.getMessage());
            }
        } else {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║         ERROR AL CONECTAR A LA BD      ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\nVerifica que:");
            System.out.println("   • MySQL esté ejecutándose");
            System.out.println("   • Las credenciales sean correctas");
            System.out.println("   • La base de datos exista");
        }
    }
}
