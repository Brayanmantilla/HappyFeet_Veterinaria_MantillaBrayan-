package happyfeet.service;

import happyfeet.model.entities.ProductoTipo;
import happyfeet.repository.DAO.ProductoTipoDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.util.List;

public class ProductoTipoService {

    private final ProductoTipoDAO productoTipoDAO;

    public ProductoTipoService() {
        Connection connection = Conexion.getConexion();
        this.productoTipoDAO = new ProductoTipoDAO(connection);
    }

    public List<ProductoTipo> listarTodos() {
        try {
            return productoTipoDAO.listarTodos();
        } catch (Exception e) {
            System.out.println("Error al listar tipos de producto: " + e.getMessage());
            return List.of();
        }
    }
}