package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.util.List;

@Entity

public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
    private String ciudad;
    private String checkIn;
    private String checkOut;
    private Integer adult;
    private Integer children;
    private Double precio;
    private String imagen;


    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public Hotel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Hotel(Long id, String name, String ciudad, String checkIn, String checkOut, Integer adult, Integer children, Double precio) {
        this.id = id;
        this.name = name;
        this.ciudad = ciudad;
        this.checkIn=checkIn;
        this.checkOut=checkOut;
        this.adult=adult;
        this.children=children;
        this.precio= precio;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
