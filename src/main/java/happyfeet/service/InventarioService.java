package happyfeet.service;

import happyfeet.model.entities.Inventario;
import happyfeet.repository.DAO.InventarioDAO;
import happyfeet.repository.interfaces.IInventarioDAO;

import java.util.List;

public class InventarioService {
    private final IInventarioDAO inventarioDAO;

    public InventarioService() {
        this.inventarioDAO = new InventarioDAO(null);
    }

    /**
     * Registra un nuevo producto en el inventario.
     */
    public boolean registrarProducto(Inventario inventario) {
        if (inventario == null) return false;
        return inventarioDAO.insertar(inventario);
    }

    /**
     * Obtiene un producto del inventario por su ID.
     */
    public Inventario obtenerProductoPorId(int id) {
        if (id <= 0) return null;
        return inventarioDAO.obtenerPorId(id);
    }

    /**
     * Lista todos los productos en inventario.
     */
    public List<Inventario> listarInventario() {
        return inventarioDAO.listarTodos();
    }

    /**
     * Actualiza los datos de un producto en inventario.
     */
    public boolean actualizarProducto(Inventario inventario) {
        if (inventario == null || inventario.getIdInventario() <= 0) return false;
        return inventarioDAO.actualizar(inventario);
    }

    /**
     * Actualiza solamente el stock de un producto.
     */
    public boolean actualizarStock(int id, int nuevoStock) {
        if (id <= 0 || nuevoStock < 0) return false;
        return inventarioDAO.actualizarStock(id, nuevoStock);
    }

    /**
     * Elimina un producto del inventario.
     */
    public boolean eliminarProducto(int id) {
        if (id <= 0) return false;
        return inventarioDAO.eliminar(id);
    }

    /**
     * Genera un reporte de productos con stock por debajo del mínimo.
     */
    public List<Inventario> reporteStockBajo() {
        return inventarioDAO.reporteStockBajo();
    }

    /**
     * Genera un reporte de productos próximos a vencer (por ejemplo, en 30 días).
     */
    public List<Inventario> reporteProximosAVencer() {
        return inventarioDAO.reporteProximosAVencer();
    }
}
