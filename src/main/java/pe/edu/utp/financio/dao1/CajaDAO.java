/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.financio.modelo.Caja;

/**
 *
 * @author FACE
 */
public interface CajaDAO {
    int guardarFondo(Caja caja) throws SQLException;
    int guardarCierre(int idCaja, double cierre, java.time.LocalDate fechaCierre) throws SQLException;
    boolean eliminar(int idCaja) throws SQLException;
    List<Caja> listar() throws SQLException;
}
