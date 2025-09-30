/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pe.edu.utp.financio.util;

import pe.edu.utp.financio.dao.impl.CategoriaDaoImpl;
import pe.edu.utp.financio.dao.impl.UsuarioDAOPostgres;
import pe.edu.utp.financio.modelo.Categoria;
import pe.edu.utp.financio.modelo.Usuario;

/**
 *
 * @author User
 */
public class RegistrarUsuariosPruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 try {
        UsuarioDAOPostgres dao = new UsuarioDAOPostgres();

        Usuario admin = new Usuario(
            0, // id_usuario (se autogenera)
            "Tonny",           // nombre
            "76319763",                // dni
            "admin@financio.com",      // correo
            "957302463",               // teléfono
            "admin123",                //contraseña en plano (será encriptada en registrar)
            "ADMIN",                // ROL
            null                       // fecha_reg (lo genera la BD)
        );
//        Usuario empleado = new Usuario(
//            0,                         // id_usuario (se autogenera)
//            "Jose",           // nombre
//            "46319763",                // dni
//            "jose@financio.com",      // correo
//            "990222176",               // teléfono
//            "empleado123",                //contraseña en plano (será encriptada en registrar)
//            "EMPLEADO",                // ROL
//            null                       // fecha_reg (lo genera la BD)
//        );
        int id = dao.registrar(admin);
        admin.setId(id);
        System.out.println("✅ Usuario ADMIN registrado con ID: " + id);
//  🔹 Registrar categorías para este usuario
//        CategoriaDaoImpl catDao = new CategoriaDaoImpl();
//        catDao.registrar(new Categoria(0, admin.getId(), "Alimentos", "INGRESO"));
//        catDao.registrar(new Categoria(0, admin.getId(), "Transporte", "INGRESO"));
//        catDao.registrar(new Categoria(0, admin.getId(), "Salud", "INGRESO"));
//
//        System.out.println("✅ Categorías creadas para el usuario ADMIN");

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
}
