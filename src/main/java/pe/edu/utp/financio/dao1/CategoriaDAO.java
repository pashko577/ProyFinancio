/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.financio.modelo.Categoria;

/**
 *
 * @author User
 */
public interface CategoriaDAO {
    int registrar(Categoria categoria) throws SQLException;
    List<Categoria> listarPorUsuario(int idUsuario) throws SQLException;
}
