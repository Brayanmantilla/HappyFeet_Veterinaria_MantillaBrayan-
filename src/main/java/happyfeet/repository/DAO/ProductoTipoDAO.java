package happyfeet.repository.DAO;

import happyfeet.model.entities.ProductoTipo;
import happyfeet.repository.interfaces.IProductoTipoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoTipoDAO implements IProductoTipoDAO {
    private final Connection connection;

    // Constructor recibe la conexión
    public ProductoTipoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ProductoTipo> listarTodos() throws Exception {
        List<ProductoTipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto_tipos ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new ProductoTipo(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            throw new Exception("Error al listar productos tipos: " + e.getMessage());
        }

        return lista;
    }
}
