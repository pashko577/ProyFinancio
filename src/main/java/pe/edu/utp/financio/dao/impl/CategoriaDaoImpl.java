/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.financio.dao1.CategoriaDAO;
import pe.edu.utp.financio.modelo.Categoria;
import pe.edu.utp.financio.util.ConexionPostgres;

/**
 *
 * @author Tonny
 */
public class CategoriaDaoImpl implements CategoriaDAO{

    @Override
    public int registrar(Categoria categoria) throws SQLException {
       String sql = "INSERT INTO categorias (id_usuario, nombre, tipo) VALUES (?, ?, ?) RETURNING id_categoria";
    int idGenerado = -1;

    try (Connection conn = ConexionPostgres.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Aseguramos autoCommit
        conn.setAutoCommit(true);

        ps.setInt(1, categoria.getIdUsuario());
        ps.setString(2, categoria.getNombre());
        ps.setString(3, categoria.getTipo());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idGenerado = rs.getInt("id_categoria");
            System.out.println("Categoría insertada con ID: " + idGenerado);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return idGenerado;
    }

    @Override
    public List<Categoria> listarPorUsuario(int idUsuario,String tipo) throws SQLException {
        List<Categoria> lista = new ArrayList<>();
    String sql = "SELECT id_categoria, id_usuario, nombre, tipo FROM categorias " +
                 "WHERE id_usuario = ? AND tipo = ?";

    try (Connection conn = ConexionPostgres.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        ps.setString(2, tipo);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categoria c = new Categoria(
                rs.getInt("id_categoria"),
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("tipo")
            );
            lista.add(c);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
    }
    
}
