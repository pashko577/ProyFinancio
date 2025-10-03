/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.modelo;

import java.time.LocalDate;

/**
 *
 * @author FACE
 */
public class Caja {
    private int idCaja;
    private int idUsuario;
    private String nombre;
    private double fondo;
    private double cierre;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;

    public Caja(int idCaja, int idUsuario, String nombre, double fondo, double cierre,
                LocalDate fechaApertura, LocalDate fechaCierre) {
        this.idCaja = idCaja;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.fondo = fondo;
        this.cierre = cierre;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
    }

    // Getters y setters
    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getFondo() {
        return fondo;
    }

    public void setFondo(double fondo) {
        this.fondo = fondo;
    }

    public double getCierre() {
        return cierre;
    }

    public void setCierre(double cierre) {
        this.cierre = cierre;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
