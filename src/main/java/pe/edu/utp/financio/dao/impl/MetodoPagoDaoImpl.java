/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.MetodopagoDAO;
import pe.edu.utp.financio.modelo.Metodopago;
import pe.edu.utp.financio.util.ConexionPostgres;

/**
 *
 * @author User
 */
public class MetodoPagoDaoImpl implements MetodopagoDAO {

    @Override
    public int registrar(Metodopago mp) throws SQLException {
        String sql = "INSERT INTO metodopago (id_usuario, tipo) VALUES (?, ?)";

        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, mp.getIdUsuario());
            ps.setString(2, mp.getTipo());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // retorna ID generado
                }
            }
        }
        return -1;
    }

    @Override
    public List<Metodopago> listarPorUsuario(int idUsuario) throws SQLException {
        List<Metodopago> lista = new ArrayList<>();
        String sql = "SELECT id_metodopago, tipo FROM metodopago WHERE id_usuario = ? ORDER BY id_metodopago ASC";

        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Metodopago mp = new Metodopago(
                        rs.getInt("id_metodopago"),
                        idUsuario,
                        rs.getString("tipo")
                );
                lista.add(mp);
            }
        }
        return lista;
    }

    @Override
    public boolean existeMetodopago(int idUsuario, String tipo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM metodopago WHERE id_usuario=? AND tipo=?";
        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

}
