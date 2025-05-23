package com.tallerwebi.dominio;
import javax.persistence.*;

@Entity
@Table(name = "excursionbd")
public class ExcursionBD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String destino;

    @Column(name = "precio_por_persona")
    private Double precioPorPersona;

    // Constructor vacío
    public ExcursionBD() {}

    // Constructor con parámetros
    public ExcursionBD(String nombre, String destino, Double precioPorPersona) {
        this.nombre = nombre;
        this.destino = destino;
        this.precioPorPersona = precioPorPersona;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getPrecioPorPersona() {
        return precioPorPersona;
    }

    public void setPrecioPorPersona(Double precioPorPersona) {
        this.precioPorPersona = precioPorPersona;
    }
}