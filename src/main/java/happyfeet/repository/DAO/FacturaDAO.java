package happyfeet.repository.DAO;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Factura;
import happyfeet.repository.interfaces.IFacturaDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO implements IFacturaDAO {
    private final DuenoDAO duenoDAO = new DuenoDAO();

    public FacturaDAO(Connection connection) throws SQLException {
    }

    @Override
    public boolean insertarFactura(Factura factura) {
        String sql = "INSERT INTO facturas (dueno_id, fecha_emision, total_factura) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, factura.getDueno().getIdDueno());
            ps.setDate(2, Date.valueOf(factura.getFechaEmision()));
            ps.setDouble(3, factura.getTotalFactura());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar factura: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Factura obtenerPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener factura: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Factura> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE fecha_emision BETWEEN ? AND ? ORDER BY fecha_emision ASC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar facturas por periodo: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Factura> listarPorDueno(int duenoId) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE dueno_id=? ORDER BY fecha_emision DESC";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar facturas por dueño: " + e.getMessage());
        }
        return lista;
    }

    // =================== MÉTODO AUXILIAR ===================
    private Factura mapResultSet(ResultSet rs) throws SQLException {
        Dueno dueno = null;
        try {
            dueno = duenoDAO.obtenerPorId(rs.getInt("dueno_id"));
        } catch (Exception e) {
            System.out.println("Error al obtener dueño: " + e.getMessage());
        }

        LocalDate fecha = rs.getDate("fecha_emision").toLocalDate();
        return new Factura(
                rs.getInt("id"),
                dueno,
                fecha,
                rs.getDouble("total_factura")
        );
    }
}
