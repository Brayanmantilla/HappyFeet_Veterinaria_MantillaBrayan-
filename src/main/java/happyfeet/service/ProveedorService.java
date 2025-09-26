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

    /**
     * Inserta un nuevo proveedor en la base de datos.
     * @param proveedor objeto Proveedor con los datos a registrar
     * @return true si la inserción fue exitosa, false si falló
     */
    public boolean registrarProveedor(Proveedor proveedor) {
        if (proveedor == null) return false;
        return proveedorDAO.insertar(proveedor);
    }

    /**
     * Obtiene un proveedor por su ID.
     * @param id identificador del proveedor
     * @return objeto Proveedor si existe, null si no
     */
    public Proveedor obtenerProveedorPorId(int id) {
        return proveedorDAO.obtenerPorId(id);
    }

    /**
     * Lista todos los proveedores registrados.
     * @return lista de proveedores
     */
    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarTodos();
    }

    /**
     * Actualiza los datos de un proveedor.
     * @param proveedor objeto con los datos a actualizar
     * @return true si la actualización fue exitosa, false si falló
     */
    public boolean actualizarProveedor(Proveedor proveedor) {
        if (proveedor == null || proveedor.getIdProveedor() <= 0) return false;
        return proveedorDAO.actualizar(proveedor);
    }

    /**
     * Elimina un proveedor por su ID.
     * @param id identificador del proveedor
     * @return true si la eliminación fue exitosa, false si falló
     */
    public boolean eliminarProveedor(int id) {
        if (id <= 0) return false;
        return proveedorDAO.eliminar(id);
    }
}
