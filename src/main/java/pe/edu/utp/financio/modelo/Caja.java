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
    private int id;
    private double fondo;
    private double cierre;
    private LocalDate fecha;

    public Caja(int id, double fondo, double cierre, LocalDate fecha) {
        this.id = id;
        this.fondo = fondo;
        this.cierre = cierre;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Calcular resultado (cierre - fondo)
    public double getResultado() {
        return cierre - fondo;
    }

    // Saber si es ganancia o pérdida
    public String getEstado() {
        double resultado = getResultado();
        return (resultado >= 0)
                ? "Ganancia de " + resultado + " soles"
                : "Pérdida de " + Math.abs(resultado) + " soles";
    }

    @Override
    public String toString() {
        return "📌 Resumen del día (" + fecha + "):\n" +
               "Fondo de inicio (mañana): " + fondo + " soles\n" +
               "Cierre de caja (noche): " + cierre + " soles\n" +
               "Resultado: " + getEstado();
    }
}
