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
import pe.edu.utp.financio.util.Encriptacion;

/**
 *
 * @author User
 */
public class UsuarioDAOPostgres implements UsuarioDAO {

    @Override
    public int registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, dni, correo, telefono, contrasena, rol) "
                + "VALUES (?,?,?,?,?,?) RETURNING id_usuario";

        try (Connection cn = ConexionPostgres.get(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getDni());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getTelefono());

            // 🔐 Encriptar la contraseña con BCrypt
            String hash = BCrypt.hashpw(usuario.getContrasenaHash(), BCrypt.gensalt());
            ps.setString(5, hash);

            ps.setString(6, usuario.getRol());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
        }
        return -1;
    }

    @Override
    public Usuario login(String dni, String contrasenaPlano) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE dni=?";

        try (Connection cn = ConexionPostgres.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
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
}
