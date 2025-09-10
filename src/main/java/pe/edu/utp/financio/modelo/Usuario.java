/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.modelo;

/**
 *
 * @author User
 */
public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasenaHash;

    public Usuario(int id, String nombre, String correo, String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
    
}
