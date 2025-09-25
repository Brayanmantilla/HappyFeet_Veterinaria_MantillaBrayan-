package happyfeet.repository.interfaces;

import happyfeet.model.entities.Proveedor;

import java.util.List;

public interface IProveedorDAO {
    boolean insertar(Proveedor proveedor);
    Proveedor obtenerPorId(int id);
    List<Proveedor> listarTodos();
    boolean actualizar(Proveedor proveedor);
    boolean eliminar(int id);
}
