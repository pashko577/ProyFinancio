/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.dao1;


import java.sql.SQLException;
import pe.edu.utp.financio.modelo.Usuario;

public interface UsuarioDAO {
    int registrar(String nombre, String correo, String contrasenaPlano) throws SQLException;
    Usuario login(String correo, String contrasenaPlano) throws SQLException;
}