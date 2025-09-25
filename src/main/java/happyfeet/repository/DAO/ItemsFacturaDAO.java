package happyfeet.repository.DAO;

import happyfeet.model.entities.Factura;
import happyfeet.model.entities.Inventario;
import happyfeet.model.entities.ItemFactura;
import happyfeet.model.entities.Servicio;
import happyfeet.repository.interfaces.IItemsFacturaDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsFacturaDAO implements IItemsFacturaDAO {

        private final Connection connection;
        private final FacturaDAO facturaDAO;
        private final InventarioDAO inventarioDAO;
        private final ServiciosDAO servicioDAO;

        // Constructor recibe la conexión y pasa a los DAOs
        public ItemsFacturaDAO(Connection connection) throws SQLException {
            this.connection = connection;
            this.facturaDAO = new FacturaDAO(connection);
            this.inventarioDAO = new InventarioDAO(connection);
            this.servicioDAO = new ServiciosDAO(connection);
        }

        @Override
        public boolean insertarItem(ItemFactura itemFactura) throws Exception {
            String sql = "INSERT INTO items_factura " +
                    "(factura_id, inventario_id, servicio_id, cantidad, precio_unitario, subtotal) " +
                    "VALUES (?,?,?,?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, itemFactura.getFactura().getIdFactura());

                if (itemFactura.getInventario() != null) {
                    ps.setInt(2, itemFactura.getInventario().getIdInventario());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }

                if (itemFactura.getServicio() != null) {
                    ps.setInt(3, itemFactura.getServicio().getIdServicio());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setInt(4, itemFactura.getCantidad());
                ps.setDouble(5, itemFactura.getPrecioUnitario());
                ps.setDouble(6, itemFactura.getSubtotal());

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            itemFactura.setIdItemFactura(rs.getInt(1));
                        }
                    }
                    return true;
                }

            } catch (SQLException e) {
                throw new Exception("Error al insertar item de factura: " + e.getMessage());
            }

            return false;
        }

        @Override
        public List<ItemFactura> listarPorFactura(int facturaId) throws Exception {
            List<ItemFactura> lista = new ArrayList<>();
            String sql = "SELECT * FROM items_factura WHERE factura_id=? ORDER BY id_item_factura ASC";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, facturaId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapResultSet(rs));
                    }
                }

            } catch (SQLException e) {
                throw new Exception("Error al listar items de factura: " + e.getMessage());
            }

            return lista;
        }

        // =================== MÉTODO AUXILIAR ===================
        private ItemFactura mapResultSet(ResultSet rs) throws Exception {
            Factura factura = facturaDAO.obtenerPorId(rs.getInt("factura_id"));
            Inventario inventario = null;
            Servicio servicio = null;

            int invId = rs.getInt("inventario_id");
            if (!rs.wasNull()) {
                inventario = inventarioDAO.obtenerPorId(invId);
            }

            int servId = rs.getInt("servicio_id");
            if (!rs.wasNull()) {
                servicio = servicioDAO.obtenerPorId(servId);
            }

            return new ItemFactura(
                    rs.getInt("id_item_factura"),
                    factura,
                    inventario,
                    servicio,
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal")
            );
        }
    }

