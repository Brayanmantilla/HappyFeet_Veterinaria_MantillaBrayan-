package happyfeet.repository.interfaces;

import happyfeet.model.entities.ProductoTipo;

import java.util.List;

public interface IProductoTipoDAO {
    List<ProductoTipo> listarTodos() throws Exception;

}
