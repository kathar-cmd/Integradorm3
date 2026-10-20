package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;
    private Long idUsuario;
    private Long idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaEntrega;
    private int cantidadLibros;

    // ===================
    // Constructor vacío
    // ===================
    public Prestamo() {
    }

    // ===================
    // Constructor completo
    // ===================

    public Prestamo(Long idPrestamo, Long idUsuario, Long idLibro, LocalDate fechaPrestamo, LocalDate fechaEntrega,
            int cantidadLibros) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEntrega = fechaEntrega;
        this.cantidadLibros = cantidadLibros;
    }

    // ===================
    // Constructor sin ID
    // (para insertar nuevos usuarios sin ID)
    // ===================

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaEntrega, int cantidadLibros) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEntrega = fechaEntrega;
        this.cantidadLibros = cantidadLibros;
    }

    // ===================
    // Getters y setters
    // ===================

    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getCantidadLibros() {
        return cantidadLibros;
    }

    public void setCantidadLibros(int cantidadLibros) {
        this.cantidadLibros = cantidadLibros;
    }

    // ===================
    // Método toString
    // ===================

    @Override
    public String toString() {
        return "Prestamo [idPrestamo=" + idPrestamo + ", idUsuario=" + idUsuario + ", idLibro=" + idLibro
                + ", fechaPrestamo=" + fechaPrestamo + ", fechaEntrega=" + fechaEntrega + ", cantidadLibros="
                + cantidadLibros + "]";
    }
    
}
