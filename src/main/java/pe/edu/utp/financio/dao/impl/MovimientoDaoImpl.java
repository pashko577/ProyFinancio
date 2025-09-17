/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.MovimientoDAO;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.util.ConexionPostgres;
import java.time.LocalDateTime;
/**
 *
 * @author Tonny
 */
public class MovimientoDaoImpl implements MovimientoDAO {

    @Override
    public List<Movimiento> listarPorUsuario(int idUsuario) throws SQLException {
       String sql = "SELECT * FROM movimientos WHERE id_usuario = ? ORDER BY id_movimiento ASC";
        List<Movimiento> lista = new ArrayList<>();

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movimiento m = new Movimiento(
                        rs.getInt("id_movimiento"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_categoria"),
                        rs.getBigDecimal("monto"),
                        rs.getString("categoria"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha")
                );
                lista.add(m);
            }
        }
        return lista;
    }

    @Override
    public int registrarmovimiento(Movimiento m) throws SQLException {
        String sql = "INSERT INTO movimientos (id_usuario, id_categoria, monto, categoria, descripcion, fecha) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, m.getIdUsuario());
            ps.setInt(2, m.getIdCategoria());
            ps.setBigDecimal(3, m.getMonto());
            ps.setString(4, m.getCategoria());
            ps.setString(5, m.getDescripcion());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // retorna id generado
                }
            }
        }
        return -1;
    }
    
    public int eliminarMovimiento(int idMovimiento) throws SQLException {
    String sql = "DELETE FROM movimientos WHERE id_movimiento = ?";
    
    try (Connection conn = ConexionPostgres.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idMovimiento);
        return ps.executeUpdate(); // devuelve cantidad de filas eliminadas
    }
}

    @Override
    public List<Movimiento> listarIngresosPorUsuario(int idUsuario) throws SQLException {
 String sql = """
        SELECT m.id_movimiento, m.id_usuario, m.id_categoria, m.monto,
               c.nombre AS categoria, m.descripcion, m.fecha
        FROM movimientos m
        JOIN categorias c ON m.id_categoria = c.id_categoria
        WHERE m.id_usuario = ? AND c.tipo = 'INGRESO'
        ORDER BY m.id_movimiento ASC
    """;

    return obtenerMovimientos(idUsuario, sql);
}
    

    @Override
    public List<Movimiento> listarGastosPorUsuario(int idUsuario) throws SQLException {
String sql = """
        SELECT m.id_movimiento, m.id_usuario, m.id_categoria, m.monto,
               c.nombre AS categoria, m.descripcion, m.fecha
        FROM movimientos m
        JOIN categorias c ON m.id_categoria = c.id_categoria
        WHERE m.id_usuario = ? AND c.tipo = 'GASTO'
        ORDER BY m.id_movimiento ASC
    """;

    return obtenerMovimientos(idUsuario, sql);
    }

    @Override
    public List<Movimiento> obtenerMovimientos(int idUsuario, String sql) throws SQLException {
  List<Movimiento> lista = new ArrayList<>();
    try (Connection conn = ConexionPostgres.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Movimiento m = new Movimiento(
                rs.getInt("id_movimiento"),
                rs.getInt("id_usuario"),
                rs.getInt("id_categoria"),
                rs.getBigDecimal("monto"),
                rs.getString("categoria"),
                rs.getString("descripcion"),
                rs.getTimestamp("fecha")
            );
            lista.add(m);
        }
    }
    return lista;
    }


}
