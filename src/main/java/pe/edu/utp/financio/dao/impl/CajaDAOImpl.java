/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.CajaDAO;
import pe.edu.utp.financio.modelo.Caja;
import pe.edu.utp.financio.util.ConexionPostgres;

/**
 *
 * @author FACE
 */
public class CajaDAOImpl implements CajaDAO{

    @Override
    public int guardarFondo(Caja caja) throws SQLException {
        String sql = "INSERT INTO caja(id_usuario, nombre, fondo, cierre, fecha_apertura) VALUES (?, ?, ?, 0, ?)";

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, caja.getIdUsuario());
            ps.setString(2, caja.getNombre());
            ps.setDouble(3, caja.getFondo());
            ps.setDate(4, Date.valueOf(caja.getFechaApertura()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public int guardarCierre(int idCaja, double cierre, java.time.LocalDate fechaCierre) throws SQLException {
        String sql = "UPDATE caja SET cierre = ?, fecha_cierre = ? WHERE id_caja = ?";

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, cierre);
            ps.setDate(2, Date.valueOf(fechaCierre));
            ps.setInt(3, idCaja);

            int rows = ps.executeUpdate();
            return rows > 0 ? idCaja : -1;
        }
    }

    @Override
    public boolean eliminar(int idCaja) throws SQLException {
        String sql = "DELETE FROM caja WHERE id_caja = ?";

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCaja);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Caja> listar() throws SQLException {
        List<Caja> lista = new ArrayList<>();
        String sql = "SELECT * FROM caja ORDER BY id_caja";

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Caja caja = new Caja(
                    rs.getInt("id_caja"),
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getDouble("fondo"),
                    rs.getDouble("cierre"),
                    rs.getDate("fecha_apertura").toLocalDate(),
                    rs.getDate("fecha_cierre") != null ? rs.getDate("fecha_cierre").toLocalDate() : null
                );
                lista.add(caja);
            }
        }
        return lista;
    }
}
