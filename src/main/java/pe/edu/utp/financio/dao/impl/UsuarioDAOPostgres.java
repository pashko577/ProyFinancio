/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.*;
import pe.edu.utp.financio.dao1.UsuarioDAO;
import pe.edu.utp.financio.modelo.Usuario;
import pe.edu.utp.financio.util.ConexionPostgres;
import pe.edu.utp.financio.util.Encriptacion;

/**
 *
 * @author User
 */
public class UsuarioDAOPostgres implements UsuarioDAO{
  @Override
    public int registrar(String nombre, String correo, String contrasenaPlano) throws SQLException {
        String sql = "INSERT INTO usuarios(nombre, correo, contrasena) VALUES (?,?,?) RETURNING id_usuario";
        try (Connection cn = ConexionPostgres.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, Encriptacion.hash(contrasenaPlano));
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public Usuario login(String correo, String contrasenaPlano) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try (Connection cn = ConexionPostgres.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("contrasena");
                if (Encriptacion.check(contrasenaPlano, hash)) {
                    return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        hash
                    );
                }
            }
        }
        return null;
    }
}