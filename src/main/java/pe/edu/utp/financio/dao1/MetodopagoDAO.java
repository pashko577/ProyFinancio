/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.financio.modelo.Metodopago;

/**
 *
 * @author User
 */
public interface MetodopagoDAO {
    int registrar(Metodopago mp) throws SQLException;
    List<Metodopago> listarPorUsuario(int idUsuario) throws SQLException;
    boolean existeMetodopago(int idUsuario, String tipo)throws SQLException;
 
}
