/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import pe.edu.utp.financio.modelo.Caja;

/**
 *
 * @author FACE
 */
public interface CajaDAO {
    int guardar(Caja caja) throws SQLException; // Guardar un registro
    boolean eliminar(int id) throws SQLException; // Eliminar un registro
}
