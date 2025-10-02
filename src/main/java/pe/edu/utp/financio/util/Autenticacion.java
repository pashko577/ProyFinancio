/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.util;

import pe.edu.utp.financio.modelo.Usuario;

/**
 *
 * @author User
 */
public class Autenticacion {
    
    private static Usuario usuarioActual;

    public static void setUsuario(Usuario u) {
        usuarioActual = u;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static boolean esAdmin() {
        return usuarioActual != null && "ADMIN".equalsIgnoreCase(usuarioActual.getRol());
    }

    public static boolean esEmpleado() {
        return usuarioActual != null && "EMPLEADO".equalsIgnoreCase(usuarioActual.getRol());
    }

    // Validación genérica para módulos
    public static void validarAcceso(String modulo) throws SecurityException {
        if (esEmpleado()) {
            if (modulo.equalsIgnoreCase("USUARIOS") ||
                modulo.equalsIgnoreCase("ANALISIS") ||
                modulo.equalsIgnoreCase("EXPORTAR")) {
                throw new SecurityException("Acceso denegado al módulo: " + modulo);
            }
        }
    }
}
