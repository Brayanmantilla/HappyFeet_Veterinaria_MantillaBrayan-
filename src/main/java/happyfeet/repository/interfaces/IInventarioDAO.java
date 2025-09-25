package happyfeet.repository.interfaces;

import happyfeet.model.entities.Inventario;

import java.util.List;

public interface IInventarioDAO {
    boolean insertar(Inventario inventario);
    Inventario obtenerPorId(int id);
    List<Inventario> listarTodos();
    boolean actualizar(Inventario inventario);
    boolean actualizarStock(int id, int nuevoStock);
    boolean eliminar(int id);
    List<Inventario> reporteStockBajo();         // Productos con stock < stock_minimo
    List<Inventario> reporteProximosAVencer();   // Productos con fecha_vencimiento cercana
}
