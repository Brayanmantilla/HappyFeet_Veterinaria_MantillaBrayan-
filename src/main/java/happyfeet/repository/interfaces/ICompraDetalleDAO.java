package happyfeet.repository.interfaces;

import happyfeet.model.entities.CompraDetalle;

import java.util.List;

public interface ICompraDetalleDAO {
    void insertarDetalle(CompraDetalle detalle) throws Exception;
    List<CompraDetalle> listarPorCompra(int idCompra) throws Exception;
}
