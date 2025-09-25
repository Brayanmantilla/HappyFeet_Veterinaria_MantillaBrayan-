package happyfeet.repository.DAO;

import happyfeet.model.entities.Inventario;
import happyfeet.model.entities.ProductoTipo;
import happyfeet.repository.interfaces.IInventarioDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements IInventarioDAO {
    public InventarioDAO(Connection connection) {
    }

    @Override
    public boolean insertar(Inventario inventario) {
        String sql = """
                INSERT INTO inventario
                (nombre_producto, producto_tipo_id, descripcion, fabricante,
                 lote, cantidad_stock, stock_minimo, fecha_vencimiento, precio_venta)
                VALUES (?,?,?,?,?,?,?,?,?)
                """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, inventario.getNombreProducto());
            ps.setInt(2, inventario.getProductoTipo().getIdProductoTipo());
            ps.setString(3, inventario.getDescripcion());
            ps.setString(4, inventario.getFabricante());
            ps.setString(5, inventario.getLote());
            ps.setInt(6, inventario.getCantidadStock());
            ps.setInt(7, inventario.getStockMinimo());
            ps.setDate(8, inventario.getFechaVencimiento() != null ?
                    Date.valueOf(inventario.getFechaVencimiento()) : null);
            ps.setDouble(9, inventario.getPrecioVenta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar inventario: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Inventario obtenerPorId(int id) {
        String sql = "SELECT * FROM inventario WHERE id = ?";
        Inventario inv = null;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                inv = mapResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener inventario: " + e.getMessage());
        }
        return inv;
    }

    @Override
    public List<Inventario> listarTodos() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario ORDER BY id";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar inventario: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Inventario inventario) {
        String sql = """
                UPDATE inventario SET
                nombre_producto=?, producto_tipo_id=?, descripcion=?, fabricante=?,
                lote=?, cantidad_stock=?, stock_minimo=?, fecha_vencimiento=?, precio_venta=?
                WHERE id=?
                """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, inventario.getNombreProducto());
            ps.setInt(2, inventario.getProductoTipo().getIdProductoTipo());
            ps.setString(3, inventario.getDescripcion());
            ps.setString(4, inventario.getFabricante());
            ps.setString(5, inventario.getLote());
            ps.setInt(6, inventario.getCantidadStock());
            ps.setInt(7, inventario.getStockMinimo());
            ps.setDate(8, inventario.getFechaVencimiento() != null ?
                    Date.valueOf(inventario.getFechaVencimiento()) : null);
            ps.setDouble(9, inventario.getPrecioVenta());
            ps.setInt(10, inventario.getIdInventario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar inventario: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizarStock(int id, int nuevoStock) {
        String sql = "UPDATE inventario SET cantidad_stock=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar stock: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar inventario: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Inventario> reporteStockBajo() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE cantidad_stock < stock_minimo ORDER BY cantidad_stock ASC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al generar reporte de stock bajo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Inventario> reporteProximosAVencer() {
        List<Inventario> lista = new ArrayList<>();
        // Ejemplo: productos que vencen en los próximos 30 días
        String sql = "SELECT * FROM inventario WHERE fecha_vencimiento IS NOT NULL " +
                "AND fecha_vencimiento <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY fecha_vencimiento ASC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al generar reporte de próximos a vencer: " + e.getMessage());
        }
        return lista;
    }

    // =================== MÉTODO AUXILIAR ===================
    private Inventario mapResultSet(ResultSet rs) throws SQLException {
        ProductoTipo tipo = new ProductoTipo();
        tipo.setIdProductoTipo(rs.getInt("producto_tipo_id")); // o el nombre real de la columna

        return new Inventario(
                rs.getInt("id"),
                rs.getString("nombre_producto"),
                tipo, // ✅ aquí pasas el objeto, no la clase
                rs.getString("descripcion"),
                rs.getString("fabricante"),
                rs.getString("lote"),
                rs.getInt("cantidad_stock"),
                rs.getInt("stock_minimo"),
                rs.getDate("fecha_vencimiento") != null ?
                        rs.getDate("fecha_vencimiento").toLocalDate() : null,
                rs.getDouble("precio_venta")
        );
    }
}
