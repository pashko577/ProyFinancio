/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.financio.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class Movimiento {

    private int id;
    private int idUsuario;
    private int idCategoria;
    private int idMetodoPago;
    private BigDecimal monto;
    private String categoria;
    private String descripcion;
    private Timestamp fecha;
    private String tipo;
    private String nombreMetodoPago;
    private int creadoPor;
    private String nombreCreador;


    public Movimiento() {
    }
    

    public Movimiento(int id, int idUsuario, int idCategoria,int idMetodoPago, BigDecimal monto, String categoria, String descripcion,
            Timestamp fecha, String tipo, String nombreMetodoPago) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.idMetodoPago=idMetodoPago;
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.nombreMetodoPago=nombreMetodoPago;
   
    }

    public Movimiento(int id, int idUsuario, int idCategoria, int idMetodoPago, BigDecimal monto, String categoria, String descripcion, Timestamp fecha, String tipo, String nombreMetodoPago, int creadoPor, String nombreCreador) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.idMetodoPago = idMetodoPago;
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.nombreMetodoPago = nombreMetodoPago;
        this.creadoPor = creadoPor;
        this.nombreCreador = nombreCreador;
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreMetodoPago() {
        return nombreMetodoPago;
    }

    public void setNombreMetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "id=" + id + ", idUsuario=" + idUsuario + ", idCategoria=" + idCategoria + ", idMetodoPago=" + idMetodoPago + ", monto=" + monto + ", categoria=" + categoria + ", descripcion=" + descripcion + ", fecha=" + fecha + ", tipo=" + tipo + ", nombreMetodoPago=" + nombreMetodoPago + ", creadoPor=" + creadoPor + ", nombreCreador=" + nombreCreador + '}';
    }



}
