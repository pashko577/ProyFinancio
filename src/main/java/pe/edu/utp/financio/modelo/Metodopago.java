/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.modelo;

/**
 *
 * @author User
 */
public class Metodopago {
    private int id;
    private int idUsuario;
    private String tipo; //tranferencia bancaria, efectivo,. deposito , billeteras digitales

    public Metodopago() {
    }
    
    public Metodopago(int id, int idUsuario, String tipo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }

}
