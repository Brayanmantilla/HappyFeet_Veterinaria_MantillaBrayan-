package happyfeet.repository.DAO;

import happyfeet.model.entities.Compra;
import happyfeet.model.entities.Proveedor;
import happyfeet.repository.interfaces.ICompraDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO implements ICompraDAO {
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    public boolean insertarCompra(Compra compra) {
        String sql = "INSERT INTO compras (proveedor_id, fecha_compra, total_compra) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, compra.getProveedor().getIdProveedor());
            ps.setDate(2, Date.valueOf(compra.getFechaCompra()));
            ps.setDouble(3, compra.getTotalCompra());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar compra: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Compra obtenerPorId(int id) {
        String sql = "SELECT * FROM compras WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener compra: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Compra> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Compra> lista = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE fecha_compra BETWEEN ? AND ? ORDER BY fecha_compra ASC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Error al listar compras por periodo: " + e.getMessage());
        }
        return lista;
    }

    // =================== MÉTODO AUXILIAR ===================
    private Compra mapResultSet(ResultSet rs) throws Exception {
        Proveedor proveedor = proveedorDAO.obtenerPorId(rs.getInt("proveedor_id"));
        LocalDate fecha = rs.getDate("fecha_compra").toLocalDate();

        return new Compra(
                rs.getInt("id"),
                proveedor,
                fecha,
                rs.getDouble("total_compra")
        );
    }
}
