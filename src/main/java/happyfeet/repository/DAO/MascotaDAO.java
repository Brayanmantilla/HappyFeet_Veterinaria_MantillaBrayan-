package happyfeet.repository.DAO;

import happyfeet.model.entities.Dueno;
import happyfeet.model.entities.Especie;
import happyfeet.model.entities.Mascota;
import happyfeet.model.entities.Raza;
import happyfeet.model.enums.sexoMascota;
import happyfeet.repository.interfaces.IMascotaDAO;
import happyfeet.util.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO implements IMascotaDAO {
    private final Connection connection;

    // CONSTRUCTOR 1: Para uso directo
    public MascotaDAO() throws SQLException {
        this.connection = Conexion.getConexion();
    }

    // CONSTRUCTOR 2: Para uso externo
    public MascotaDAO(Connection connection) {
        this.connection = connection;
    }

    // ============================================================
    // INSERTAR
    // ============================================================
    @Override
    public boolean insertar(Mascota mascota) throws SQLException {
        String sql = """
            INSERT INTO mascotas
                (nombre, raza_id, fecha_nacimiento, sexo, url_foto, dueno_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, mascota.getNombreMascota());
            ps.setInt(2, mascota.getRaza().getIdRaza());
            ps.setDate(3, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(4, mascota.getSexo().name());
            ps.setString(5, mascota.getUrlFoto());
            ps.setInt(6, mascota.getIdDueno().getIdDueno());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        mascota.setIdMascota(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // OBTENER POR ID
    // ============================================================
    @Override
    public Mascota obtenerPorId(int id) throws SQLException {
        String sql = SELECT_BASE + " WHERE m.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    // ============================================================
    // LISTAR TODOS
    // ============================================================
    @Override
    public List<Mascota> listarTodos() throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY m.id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        }
        return lista;
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    @Override
    public boolean actualizar(Mascota mascota) throws SQLException {
        String sql = """
            UPDATE mascotas
               SET nombre=?, raza_id=?, fecha_nacimiento=?, sexo=?, url_foto=?, dueno_id=?
             WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mascota.getNombreMascota());
            ps.setInt(2, mascota.getRaza().getIdRaza());
            ps.setDate(3, mascota.getFechaNacimiento() != null ? Date.valueOf(mascota.getFechaNacimiento()) : null);
            ps.setString(4, mascota.getSexo().name());
            ps.setString(5, mascota.getUrlFoto());
            ps.setInt(6, mascota.getIdDueno().getIdDueno());
            ps.setInt(7, mascota.getIdMascota());
            return ps.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mascotas WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ============================================================
    // BUSCAR POR NOMBRE
    // ============================================================
    @Override
    public List<Mascota> buscarPorNombre(String nombre) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE m.nombre LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    // ============================================================
    // LISTAR POR DUEÑO
    // ============================================================
    @Override
    public List<Mascota> listarPorDueno(int idDueno) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE d.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDueno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    // ============================================================
    // CONSULTA BASE
    // ============================================================
    private static final String SELECT_BASE = """
        SELECT 
            m.id          AS m_id,
            m.nombre      AS m_nombre,
            m.fecha_nacimiento AS m_fecha_nacimiento,
            m.sexo        AS m_sexo,
            m.url_foto    AS m_url_foto,
            r.id          AS r_id,
            r.nombre      AS r_nombre,
            e.id          AS e_id,
            e.nombre      AS e_nombre,
            d.id          AS d_id,
            d.nombre_completo    AS d_nombre_completo,
            d.documento_identidad AS d_documento,
            d.direccion          AS d_direccion,
            d.telefono           AS d_telefono,
            d.email              AS d_email
        FROM mascotas m
        JOIN razas    r ON m.raza_id = r.id
        JOIN especies e ON r.especie_id = e.id
        JOIN duenos   d ON m.dueno_id = d.id
    """;

    // ============================================================
    // MAPEO ResultSet -> Mascota
    // ============================================================
    private Mascota mapResultSet(ResultSet rs) throws SQLException {
        // Especie
        Especie especie = new Especie(
                rs.getInt("e_id"),
                rs.getString("e_nombre")
        );

        // Raza con especie
        Raza raza = new Raza(
                rs.getInt("r_id"),
                rs.getString("r_nombre"),
                especie
        );

        // Dueño (orden exacto del constructor)
        Dueno dueno = new Dueno(
                rs.getInt("d_id"),
                rs.getString("d_nombre_completo"),
                rs.getString("d_documento"),
                rs.getString("d_direccion"),
                rs.getString("d_telefono"),
                rs.getString("d_email")
        );

        LocalDate fecha = rs.getDate("m_fecha_nacimiento") != null
                ? rs.getDate("m_fecha_nacimiento").toLocalDate()
                : null;

        return new Mascota(
                rs.getInt("m_id"),
                rs.getString("m_nombre"),
                raza,
                fecha,
                sexoMascota.valueOf(rs.getString("m_sexo")),
                rs.getString("m_url_foto"),
                dueno
        );
    }
}
