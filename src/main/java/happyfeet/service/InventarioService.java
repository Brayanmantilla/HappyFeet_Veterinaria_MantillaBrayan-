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

    public boolean registrarProducto(Inventario inventario) {
        if (inventario == null) return false;
        return inventarioDAO.insertar(inventario);
    }

    public Inventario obtenerProductoPorId(int id) {
        if (id <= 0) return null;
        return inventarioDAO.obtenerPorId(id);
    }

    public List<Inventario> listarInventario() {
        return inventarioDAO.listarTodos();
    }

    public boolean actualizarProducto(Inventario inventario) {
        if (inventario == null || inventario.getIdInventario() <= 0) return false;
        return inventarioDAO.actualizar(inventario);
    }

    public boolean actualizarStock(int id, int nuevoStock) {
        if (id <= 0 || nuevoStock < 0) return false;
        return inventarioDAO.actualizarStock(id, nuevoStock);
    }

    public boolean eliminarProducto(int id) {
        if (id <= 0) return false;
        return inventarioDAO.eliminar(id);
    }

    public List<Inventario> reporteStockBajo() {
        return inventarioDAO.reporteStockBajo();
    }

    public List<Inventario> reporteProximosAVencer() {
        return inventarioDAO.reporteProximosAVencer();
    }
}
