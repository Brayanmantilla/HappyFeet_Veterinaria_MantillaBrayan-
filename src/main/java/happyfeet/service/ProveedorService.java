package happyfeet.service;

import happyfeet.model.entities.Proveedor;
import happyfeet.repository.DAO.ProveedorDAO;
import happyfeet.repository.interfaces.IProveedorDAO;

import java.util.List;

public class ProveedorService {
    private final IProveedorDAO proveedorDAO;

    public ProveedorService() {
        this.proveedorDAO = new ProveedorDAO();
    }

    public boolean registrarProveedor(Proveedor proveedor) {
        if (proveedor == null) return false;
        return proveedorDAO.insertar(proveedor);
    }

    public Proveedor obtenerProveedorPorId(int id) {
        return proveedorDAO.obtenerPorId(id);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarTodos();
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        if (proveedor == null || proveedor.getIdProveedor() <= 0) return false;
        return proveedorDAO.actualizar(proveedor);
    }

    public boolean eliminarProveedor(int id) {
        if (id <= 0) return false;
        return proveedorDAO.eliminar(id);
    }
}
