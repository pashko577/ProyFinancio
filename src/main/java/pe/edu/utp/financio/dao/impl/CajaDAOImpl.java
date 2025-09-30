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
import pe.edu.utp.financio.dao1.CajaDAO;
import pe.edu.utp.financio.modelo.Caja;
import pe.edu.utp.financio.util.ConexionPostgres;

/**
 *
 * @author FACE
 */
public class CajaDAOImpl implements CajaDAO{

    @Override
    public int guardar(Caja caja) throws SQLException {
        String sql = "INSERT INTO caja(fondo, cierre,fecha) VALUES (?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setDouble(1, caja.getFondo());
            ps.setDouble(2, caja.getCierre());
            ps.setDate(3, java.sql.Date.valueOf(caja.getFecha()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // ID generado
                    }
                }
            }
        }
        return -1; // No se insertó
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM caja WHERE id=?";

        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,id);
            int rows = ps.executeUpdate();
            
        return rows >0; //true si se elimino al menos en una fila
        }
    }

    
}
