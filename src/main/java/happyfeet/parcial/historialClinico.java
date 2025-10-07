/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parcial;

import happyfeet.controller.HistorialMedicoController;
import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.EventoTipo;
import happyfeet.model.entities.HistorialMedico;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.service.HistorialMedicoService;
import happyfeet.service.MascotaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class historialClinico {
    private final HistorialMedicoService historialService;
    private final MascotaService mascotaService;
    private final Scanner scanner;
    
        public historialClinico(Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.historialService = new HistorialMedicoService();
        this.mascotaService = new MascotaService();
    }
        
    public void generarHistorial() throws SQLException {
        System.out.println("\n--- Generar Historial Clinico de la MASCOTA ---");
        
        int idMascota = pedirEntero("Ingrese el ID de la mascota: ");
        Mascota mascota = obtenerMascotaPorId(idMascota);
        if (mascota == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
}
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
    
    
    //List<mascota> lista2 = MascotaService.listarMascotas(idMascota); 
}
    
    
    
    
    
     // ================== MÉTODOS AUXILIARES ==================
    public Mascota obtenerMascotaPorId(int id) throws SQLException {
        return mascotaService.obtenerMascota(id);
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
