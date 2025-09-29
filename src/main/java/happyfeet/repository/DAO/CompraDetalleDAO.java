package happyfeet.repository.DAO;

import happyfeet.model.entities.Compra;
import happyfeet.model.entities.CompraDetalle;
import happyfeet.model.entities.Inventario;
import happyfeet.repository.interfaces.ICompraDetalleDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDetalleDAO implements ICompraDetalleDAO {
    private final Connection connection;
    private final CompraDAO compraDAO;
    private final InventarioDAO inventarioDAO;

    public CompraDetalleDAO(Connection connection, CompraDAO compraDAO, InventarioDAO inventarioDAO) {
        this.connection = connection;
        this.compraDAO = compraDAO;
        this.inventarioDAO = inventarioDAO;
    }

    @Override
    public void insertarDetalle(CompraDetalle detalle) throws Exception {
        String sql = "INSERT INTO compras_detalle " +
                "(compra_id, inventario_id, cantidad_compra_detalle, precio_unitario, subtotal) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalle.getCompra().getIdCompra());
            ps.setInt(2, detalle.getInventario().getIdInventario());
            ps.setInt(3, detalle.getCantidadCompraDetalle());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    detalle.setIdCompraDetalle(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<CompraDetalle> listarPorCompra(int idCompra) throws Exception {
        String sql = "SELECT * FROM compras_detalle WHERE compra_id = ?";
        List<CompraDetalle> detalles = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCompra);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapResultSet(rs));
                }
            }
        }
        return detalles;
    }

    // =================== MÉTODO AUXILIAR ===================
    private CompraDetalle mapResultSet(ResultSet rs) throws SQLException, Exception {
        Compra compra = compraDAO.obtenerPorId(rs.getInt("compra_id"));
        Inventario inventario = inventarioDAO.obtenerPorId(rs.getInt("inventario_id"));

        return new CompraDetalle(
                rs.getInt("id_compra_detalle"),
                compra,
                inventario,
                rs.getInt("cantidad_compra_detalle"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal")
        );
    }
}
