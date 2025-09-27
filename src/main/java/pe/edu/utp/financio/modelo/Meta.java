package pe.edu.utp.financio.modelo;


import java.time.LocalDate;
import org.bson.types.ObjectId;

public class Meta {
    private String idMeta;
    private int idUsuario;
    private String nombre;
    private double montoObjetivo;
    private double acumulado;
    private double porcentaje;
    private LocalDate fechaLimite;
    private boolean activa;

    // Constructor completo
    public Meta(String idMeta, int idUsuario, String nombre, double montoObjetivo,
                double acumulado, double porcentaje, LocalDate fechaLimite, boolean activa) {
        this.idMeta = idMeta;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
        this.acumulado = acumulado;
        this.porcentaje = porcentaje;
        this.fechaLimite = fechaLimite;
        this.activa = activa;
    }

    // Constructor vacío
    public Meta() {}

    // Getters y Setters
    public String getIdMeta() { return idMeta; }
    public void setIdMeta(String idMeta) { this.idMeta = idMeta; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMontoObjetivo() { return montoObjetivo; }
    public void setMontoObjetivo(double montoObjetivo) { this.montoObjetivo = montoObjetivo; }

    public double getAcumulado() { return acumulado; }
    public void setAcumulado(double acumulado) { this.acumulado = acumulado; }

    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}
