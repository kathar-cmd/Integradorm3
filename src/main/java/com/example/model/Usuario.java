package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario; // para que sea autogenerado por la BD
    private String nombre;
    private int documento;
    private String rol; // 'admin', 'usuario', etc.
    private Long idContacto;
    private String tipoDocumento;

    // ===================
    // Constructor completo
    // ===================
    
    public Usuario(Long idUsuario, String nombre, int documento, String rol, Long idContacto, String tipoDocumento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.idContacto = idContacto;
        this.tipoDocumento = tipoDocumento != null ? tipoDocumento : "Cedula";
    }

    // ===================
    // Constructor sin ID
    // (para insertar nuevos usuarios sin ID)
    // ===================
    
    public Usuario(String nombre, int documento, String rol, String tipoDocumento) {
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.tipoDocumento = tipoDocumento;
    }

    // ===================
    // Constructor vacío
    // ===================

    public Usuario() {
        this.tipoDocumento = "Cedula";
    }

    // ===================
    // Getters y setters
    // ===================

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Long idContacto) {
        this.idContacto = idContacto;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    // ===================
    // Método toString
    // ===================
    
    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", documento=" + documento + ", rol=" + rol
                + ", idContacto=" + idContacto + ", tipoDocumento=" + tipoDocumento + "]";
    }
}
