/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author User
 */
public class Encriptacion {
      public static String hash(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }

    public static boolean check(String contrasena, String hash) {
        return BCrypt.checkpw(contrasena, hash);
    }
}

