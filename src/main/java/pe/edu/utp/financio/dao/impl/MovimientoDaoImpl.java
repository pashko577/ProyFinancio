package pe.edu.utp.financio.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.MovimientoDAO;
import pe.edu.utp.financio.modelo.Movimiento;
import pe.edu.utp.financio.util.ConexionPostgres;

public class MovimientoDaoImpl implements MovimientoDAO {

    // Mapeo de ResultSet a Movimiento
    private Movimiento mapear(ResultSet rs) throws SQLException {
        Movimiento m = new Movimiento(
                rs.getInt("id_movimiento"),
                rs.getInt("id_usuario"),
                rs.getInt("id_categoria"),
                rs.getInt("id_metodopago"),
                rs.getBigDecimal("monto"),
                rs.getString("nombre_categoria"),
                rs.getString("descripcion"),
                rs.getTimestamp("fecha"),
                rs.getString("tipo_categoria"),
                rs.getString("nombre_metodo_pago")
        );
        m.setCreadoPor(rs.getInt("creado_por"));
        m.setNombreCreador(rs.getString("nombre_creador"));
        return m;
    }

    @Override
    public List<Movimiento> listarPorUsuario(int idUsuario, boolean esAdmin) throws SQLException {
        String sql = """
            SELECT m.id_movimiento, m.id_usuario, m.id_categoria, m.id_metodopago, m.monto,
                   c.nombre AS nombre_categoria, m.descripcion, m.fecha,
                   c.tipo AS tipo_categoria, mp.tipo AS nombre_metodo_pago,
                   m.creado_por, u2.nombre AS nombre_creador
            FROM movimientos m
            JOIN categorias c ON m.id_categoria = c.id_categoria
            JOIN metodopago mp ON m.id_metodopago = mp.id_metodopago
            JOIN usuarios u2 ON m.creado_por = u2.id_usuario
        """;

        if (!esAdmin) {
            // Empleado solo ve lo que creó
            sql += " WHERE m.creado_por = ?";
        }

        sql += " ORDER BY m.id_movimiento ASC";

        List<Movimiento> lista = new ArrayList<>();
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!esAdmin) {
                ps.setInt(1, idUsuario);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Movimiento> listarIngresosPorUsuario(int idUsuario, boolean esAdmin) throws SQLException {
        String sql = """
            SELECT m.id_movimiento, m.id_usuario, m.id_categoria, m.id_metodopago, m.monto,
                   c.nombre AS nombre_categoria, m.descripcion, m.fecha,
                   c.tipo AS tipo_categoria, mp.tipo AS nombre_metodo_pago,
                   m.creado_por, u2.nombre AS nombre_creador
            FROM movimientos m
            JOIN categorias c ON m.id_categoria = c.id_categoria
            JOIN metodopago mp ON m.id_metodopago = mp.id_metodopago
            JOIN usuarios u2 ON m.creado_por = u2.id_usuario
            WHERE c.tipo = 'INGRESO'
        """;

        if (!esAdmin) {
            sql += " AND m.creado_por = ?";
        }

        sql += " ORDER BY m.id_movimiento ASC";

        List<Movimiento> lista = new ArrayList<>();
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!esAdmin) {
                ps.setInt(1, idUsuario);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Movimiento> listarGastosPorUsuario(int idUsuario, boolean esAdmin) throws SQLException {
        String sql = """
            SELECT m.id_movimiento, m.id_usuario, m.id_categoria, m.id_metodopago, m.monto,
                   c.nombre AS nombre_categoria, m.descripcion, m.fecha,
                   c.tipo AS tipo_categoria, mp.tipo AS nombre_metodo_pago,
                   m.creado_por, u2.nombre AS nombre_creador
            FROM movimientos m
            JOIN categorias c ON m.id_categoria = c.id_categoria
            JOIN metodopago mp ON m.id_metodopago = mp.id_metodopago
            JOIN usuarios u2 ON m.creado_por = u2.id_usuario
            WHERE c.tipo = 'GASTO'
        """;

        if (!esAdmin) {
            sql += " AND m.creado_por = ?";
        }

        sql += " ORDER BY m.id_movimiento ASC";

        List<Movimiento> lista = new ArrayList<>();
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!esAdmin) {
                ps.setInt(1, idUsuario);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public int registrarmovimiento(Movimiento m, int idAdmin, int creadoPor) throws SQLException {
        String sql = """
            INSERT INTO movimientos (id_usuario, creado_por, id_categoria, id_metodopago, monto, descripcion, fecha)
            VALUES (?, ?, ?, ?, ?, ?, NOW())
        """;

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idAdmin);       // admin
            ps.setInt(2, creadoPor);     // empleado
            ps.setInt(3, m.getIdCategoria());
            ps.setInt(4, m.getIdMetodoPago());
            ps.setBigDecimal(5, m.getMonto());
            ps.setString(6, m.getDescripcion());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public int eliminarMovimiento(int idMovimiento) throws SQLException {
        String sql = "DELETE FROM movimientos WHERE id_movimiento = ?";
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMovimiento);
            return ps.executeUpdate();
        }
    }
}
