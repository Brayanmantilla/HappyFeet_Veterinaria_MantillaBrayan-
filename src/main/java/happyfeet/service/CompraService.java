package happyfeet.service;

import happyfeet.model.entities.Compra;
import happyfeet.repository.DAO.CompraDAO;
import happyfeet.repository.interfaces.ICompraDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class CompraService {
    private final ICompraDAO compraDAO;
    private final Connection connection;

    public CompraService() {
        this.connection = Conexion.getConexion();
        this.compraDAO = new CompraDAO();  // El DAO maneja internamente la conexión
    }

    /**
     * Registra una nueva compra en la base de datos.
     * @param compra Objeto Compra con proveedor, fecha y total
     * @return true si la operación fue exitosa
     */
    public boolean registrarCompra(Compra compra) {
        if (compra == null || compra.getProveedor() == null) return false;
        try {
            return compraDAO.insertarCompra(compra);
        } catch (Exception e) {
            System.out.println("Error en registrarCompra: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una compra específica por su ID.
     * @param idCompra identificador de la compra
     * @return objeto Compra o null si no existe
     */
    public Compra obtenerCompraPorId(int idCompra) {
        if (idCompra <= 0) return null;
        try {
            return compraDAO.obtenerPorId(idCompra);
        } catch (Exception e) {
            System.out.println("Error en obtenerCompraPorId: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todas las compras realizadas entre dos fechas.
     * @param inicio fecha inicial (inclusive)
     * @param fin fecha final (inclusive)
     * @return lista de compras dentro del periodo
     */
    public List<Compra> listarComprasPorPeriodo(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) return Collections.emptyList();
        try {
            return compraDAO.listarPorPeriodo(inicio, fin);
        } catch (Exception e) {
            System.out.println("Error en listarComprasPorPeriodo: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Cierra la conexión asociada a este servicio.
     */
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión en CompraService: " + e.getMessage());
        }
    }
}
