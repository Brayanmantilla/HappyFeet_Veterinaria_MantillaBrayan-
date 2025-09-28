package happyfeet.service;

import happyfeet.model.entities.ItemFactura;
import happyfeet.repository.DAO.ItemsFacturaDAO;
import happyfeet.repository.interfaces.IItemsFacturaDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemsFacturaService {
    private final IItemsFacturaDAO itemsFacturaDAO;
    private final Connection connection;

    public ItemsFacturaService() {
        this.connection = Conexion.getConexion();
        try {
            this.itemsFacturaDAO = new ItemsFacturaDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar ItemsFacturaService: " + e.getMessage(), e);
        }
    }

    public boolean agregarItemFactura(ItemFactura item) {
        if (item == null) return false;
        try {
            return itemsFacturaDAO.insertarItem(item);
        } catch (Exception e) {
            System.out.println("Error al agregar item a factura: " + e.getMessage());
            return false;
        }
    }

    public List<ItemFactura> obtenerItemsPorFactura(int facturaId) {
        try {
            return itemsFacturaDAO.listarPorFactura(facturaId);
        } catch (Exception e) {
            System.out.println("Error al obtener items de factura: " + e.getMessage());
            return List.of();
        }
    }
}