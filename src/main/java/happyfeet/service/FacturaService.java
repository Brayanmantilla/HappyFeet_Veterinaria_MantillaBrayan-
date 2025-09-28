package happyfeet.service;

import happyfeet.model.entities.Factura;
import happyfeet.repository.DAO.FacturaDAO;
import happyfeet.repository.interfaces.IFacturaDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class FacturaService {

        private final IFacturaDAO facturaDAO;
        private final Connection connection;

        public FacturaService() {
            // Abrimos una conexión para el DAO
            this.connection = Conexion.getConexion();
            IFacturaDAO daoTemp = null;
            try {
                daoTemp = new FacturaDAO(connection);
            } catch (Exception e) {
                System.out.println("Error al crear FacturaDAO: " + e.getMessage());
            }
            this.facturaDAO = daoTemp;
        }

        public boolean registrarFactura(Factura factura) {
            if (factura == null || factura.getDueno() == null) return false;
            try {
                return facturaDAO.insertarFactura(factura);
            } catch (Exception e) {
                System.out.println("Error en registrarFactura: " + e.getMessage());
                return false;
            }
        }

        public Factura obtenerFacturaPorId(int id) {
            if (id <= 0) return null;
            try {
                return facturaDAO.obtenerPorId(id);
            } catch (Exception e) {
                System.out.println("Error en obtenerFacturaPorId: " + e.getMessage());
                return null;
            }
        }

        public List<Factura> listarFacturasPorPeriodo(LocalDate inicio, LocalDate fin) {
            if (inicio == null || fin == null) return Collections.emptyList();
            try {
                return facturaDAO.listarPorPeriodo(inicio, fin);
            } catch (Exception e) {
                System.out.println("Error en listarFacturasPorPeriodo: " + e.getMessage());
                return Collections.emptyList();
            }
        }

        public List<Factura> listarFacturasPorDueno(int duenoId) {
            if (duenoId <= 0) return Collections.emptyList();
            try {
                return facturaDAO.listarPorDueno(duenoId);
            } catch (Exception e) {
                System.out.println("Error en listarFacturasPorDueno: " + e.getMessage());
                return Collections.emptyList();
            }
        }

        public void cerrarConexion() {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión en FacturaService: " + e.getMessage());
            }
        }
}
