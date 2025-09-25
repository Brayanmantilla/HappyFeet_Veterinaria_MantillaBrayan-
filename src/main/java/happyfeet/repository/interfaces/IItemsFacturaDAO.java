package happyfeet.repository.interfaces;

import happyfeet.model.entities.ItemFactura;

import java.util.List;

public interface IItemsFacturaDAO {

    boolean insertarItem(ItemFactura itemFactura) throws Exception;
    List<ItemFactura> listarPorFactura(int facturaId) throws Exception;
}

