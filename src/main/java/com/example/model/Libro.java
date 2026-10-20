package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    private String nombre;
    private String genero;
    private String seccion;
    private int ejemplares;
    private int añoPublicacion;

    // ===================
    // Constructor vacío
    // ===================

    public Libro() {
    }

    // ===================
    // Constructor sin ID
    // (para insertar nuevos usuarios sin ID)
    // ===================

    public Libro(String nombre, String genero, String seccion, int ejemplares, int añoPublicacion) {
        this.nombre = nombre;
        this.genero = genero;
        this.seccion = seccion;
        this.ejemplares = ejemplares;
        this.añoPublicacion = añoPublicacion;
    }
    
    // ===================
    // Constructor completo
    // ===================

    public Libro(Long idLibro, String nombre, String genero, String seccion, int ejemplares, int añoPublicacion) {
        this.idLibro = idLibro;
        this.nombre = nombre;
        this.genero = genero;
        this.seccion = seccion;
        this.ejemplares = ejemplares;
        this.añoPublicacion = añoPublicacion;
    }

    // ===================
    // Getters y setters
    // ===================

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }
    
    // ===================
    // Método toString
    // ===================

    @Override
    public String toString() {
        return "Libro [idLibro=" + idLibro + ", nombre=" + nombre + ", genero=" + genero + ", seccion=" + seccion
                + ", ejemplares=" + ejemplares + ", añoPublicacion=" + añoPublicacion + "]";
    }
    
    
}
