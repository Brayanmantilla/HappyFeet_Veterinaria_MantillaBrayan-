package happyfeet.service;

import happyfeet.model.entities.*;
import happyfeet.model.enums.CitaEstado;
import happyfeet.service.*;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

public class ReporteService {
    private final Connection connection;
    private final FacturaService facturaService;
    private final InventarioService inventarioService;
    private final ServicioService servicioService;

    public ReporteService() {
        this.connection = Conexion.getConexion();
        this.facturaService = new FacturaService();
        this.inventarioService = new InventarioService();
        this.servicioService = new ServicioService();
    }

    // Reporte de servicios más solicitados
    public Map<String, Integer> obtenerServiciosMasSolicitados() {
        Map<String, Integer> serviciosConteo = new HashMap<>();
        String sql = """
            SELECT s.nombre, COUNT(itf.servicio_id) as total_solicitudes
            FROM servicios s
            LEFT JOIN items_factura itf ON s.id = itf.servicio_id
            WHERE itf.servicio_id IS NOT NULL
            GROUP BY s.id, s.nombre
            ORDER BY total_solicitudes DESC
            LIMIT 10
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                serviciosConteo.put(rs.getString("nombre"), rs.getInt("total_solicitudes"));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener servicios más solicitados: " + e.getMessage());
        }

        return serviciosConteo;
    }

    // Reporte de desempeño veterinario
    public Map<String, Object> obtenerDesempenoVeterinario() {
        Map<String, Object> desempeño = new HashMap<>();

        // Citas por veterinario
        String sqlCitas = """
            SELECT e.nombre_completo, 
                   COUNT(c.id) as total_citas,
                   COUNT(CASE WHEN c.estado_id = 3 THEN 1 END) as citas_completadas,
                   COUNT(CASE WHEN c.estado_id = 4 THEN 1 END) as citas_canceladas
            FROM empleados e
            LEFT JOIN citas c ON e.id = c.empleado_id
            WHERE e.cargo LIKE '%veterinari%'
            GROUP BY e.id, e.nombre_completo
            ORDER BY total_citas DESC
            """;

        Map<String, Map<String, Integer>> citasPorVet = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sqlCitas);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Integer> stats = new HashMap<>();
                stats.put("total_citas", rs.getInt("total_citas"));
                stats.put("citas_completadas", rs.getInt("citas_completadas"));
                stats.put("citas_canceladas", rs.getInt("citas_canceladas"));

                citasPorVet.put(rs.getString("nombre_completo"), stats);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener desempeño veterinario: " + e.getMessage());
        }

        desempeño.put("citas_por_veterinario", citasPorVet);
        return desempeño;
    }

    // Reporte de facturación por período
    public Map<String, Object> obtenerReporteFacturacion(LocalDate inicio, LocalDate fin) {
        Map<String, Object> reporte = new HashMap<>();

        List<Factura> facturas = facturaService.listarFacturasPorPeriodo(inicio, fin);

        double totalFacturado = facturas.stream()
                .mapToDouble(Factura::getTotalFactura)
                .sum();

        int totalFacturas = facturas.size();
        double promedioFactura = totalFacturas > 0 ? totalFacturado / totalFacturas : 0;

        reporte.put("total_facturado", totalFacturado);
        reporte.put("total_facturas", totalFacturas);
        reporte.put("promedio_factura", promedioFactura);
        reporte.put("facturas", facturas);

        return reporte;
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}