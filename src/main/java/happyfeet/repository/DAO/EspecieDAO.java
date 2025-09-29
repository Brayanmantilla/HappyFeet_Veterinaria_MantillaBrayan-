package happyfeet.repository.DAO;

import happyfeet.model.entities.Especie;
import happyfeet.repository.interfaces.IEspecieDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO implements IEspecieDAO {
    @Override
    public boolean insertar(Especie especie) {
        String sql = "INSERT INTO especies (nombre) VALUES (?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, especie.getNombreEspecie());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar especie: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Especie obtenerPorId(int id) {
        Especie especie = null;
        String sql = "SELECT * FROM especies WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    especie = new Especie(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener especie: " + e.getMessage());
        }
        return especie;
    }

    @Override
    public List<Especie> listarTodas() {
        List<Especie> especies = new ArrayList<>();
        String sql = "SELECT * FROM especies";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                especies.add(new Especie(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar especies: " + e.getMessage());
        }
        return especies;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM especies WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar especie: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Especie especie) {
        String sql = "UPDATE especies SET nombre = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, especie.getNombreEspecie());
            ps.setInt(2, especie.getIdEspecie());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar especie: " + e.getMessage());
        }
        return false;
    }
}
