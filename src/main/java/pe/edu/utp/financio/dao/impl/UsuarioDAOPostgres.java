/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao.impl;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
import pe.edu.utp.financio.dao1.UsuarioDAO;
import pe.edu.utp.financio.modelo.Usuario;
import pe.edu.utp.financio.util.ConexionPostgres;


/**
 *
 * @author User
 */
public class UsuarioDAOPostgres implements UsuarioDAO {

    @Override
   public int registrar(Usuario u) throws SQLException {
    String sql = "INSERT INTO usuarios (nombre, dni, correo, telefono, contrasena, rol, fecha_reg) " +
                 "VALUES (?, ?, ?, ?, ?, ?, NOW()) RETURNING id_usuario";

    try (Connection cn = ConexionPostgres.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Encriptar contraseña antes de guardar
        String hash = BCrypt.hashpw(u.getContrasenaHash(), BCrypt.gensalt());

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getDni());
        ps.setString(3, u.getCorreo());
        ps.setString(4, u.getTelefono());
        ps.setString(5, hash);
        ps.setString(6, u.getRol());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int idGenerado = rs.getInt("id_usuario");
            u.setId(idGenerado); // actualizar el objeto
            return idGenerado;
        }
    }
    return -1; // por si algo falla
}


    @Override
    public Usuario login(String dni, String contrasenaPlano) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE dni=?";

        try (Connection cn = ConexionPostgres.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("contrasena");
                if (BCrypt.checkpw(contrasenaPlano, hash)) {

                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("dni"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            hash, // contraseña encriptada
                            rs.getString("rol"),
                            rs.getTimestamp("fecha_reg")
                    );
                }
            }
        }
        return null;
    }
    
    public int obtenerIdAdmin() throws SQLException {
    String sql = "SELECT id_usuario FROM usuarios WHERE rol = 'ADMIN' LIMIT 1";
    try (Connection conn = ConexionPostgres.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_usuario");
        } else {
            throw new SQLException("No se encontró un usuario admin en la BD");
        }
    }
}

}
