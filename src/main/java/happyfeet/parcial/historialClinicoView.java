/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parcial;

import happyfeet.controller.HistorialMedicoController;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class historialClinicoView {
    
    private final historialClinico controller;
    private final Scanner scanner;

    public historialClinicoView(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.controller = new historialClinico(scanner);
    }
    

    public void mostrarMenu() throws SQLException {
    boolean continuar = true;
    while (continuar) {
        mostrarMenuActividades();
        int opcion = leerOpcion();

        switch (opcion) {
            case 1 -> controller.generarHistorial();
            case 0 -> continuar = false;
            default -> System.out.println("Opción inválida. Intente nuevamente.");
        }
    }
}
    
    private void mostrarMenuActividades() {
        System.out.println("\n----- ACTIVIDADES ESPECIALES -----");
        System.out.println("[1] Exportar el Reporte de la mascota");
        System.out.println("[0] Volver al Menú Principal");
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
