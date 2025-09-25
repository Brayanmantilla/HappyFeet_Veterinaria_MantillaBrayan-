package happyfeet.repository.DAO;

import happyfeet.model.entities.Especie;
import happyfeet.model.entities.Raza;
import happyfeet.repository.interfaces.IRazaDAO;
import happyfeet.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO implements IRazaDAO {

    @Override
    public boolean insertar(Raza raza) {
        String sql = "INSERT INTO razas (especie_id, nombre) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, raza.getEspecie().getIdEspecie());
            ps.setString(2, raza.getNombreRaza());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar raza: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Raza> listarPorEspecie(int especieId) {
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT * FROM razas WHERE especie_id = ? ORDER BY nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, especieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Especie especie = new Especie();
                especie.setIdEspecie(rs.getInt("especie_id"));
                razas.add(new Raza(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        especie
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar razas por especie: " + e.getMessage());
        }
        return razas;
    }
}
