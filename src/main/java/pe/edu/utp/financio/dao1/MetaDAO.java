/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.financio.dao1;

import java.sql.SQLException;
import java.util.List;
import pe.edu.utp.financio.modelo.Meta;

/**
 *
 * @author User
 */
public interface MetaDAO {
     int registrar(Meta meta) throws SQLException;
    List<Meta> listarActivasPorUsuario(int idUsuario) throws SQLException;
    void actualizarAcumulado(int idMeta, double monto) throws SQLException;
    void desactivarSiCumplida(int idMeta) throws SQLException;
}

