/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao1;


import java.sql.SQLException;
import pe.edu.utp.financio.modelo.Usuario;

public interface UsuarioDAO {
    // Registrar un nuevo usuario
    int registrar(Usuario usuario) throws SQLException;
    // Login por DNI
    Usuario login(String dni, String contrasenaPlano) throws SQLException;
}