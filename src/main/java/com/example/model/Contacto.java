package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactos")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContacto;
    private String correo;
    private String telefono;
    private String direccion;

    // ===================
    // Constructor vacío
    // ===================
    public Contacto() {
    }

    // ===================
    // Constructor sin ID
    // (para insertar nuevos usuarios sin ID)
    // ===================
    
    public Contacto(String correo, String telefono, String direccion) {
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // ===================
    // Constructor completo
    // ===================

    public Contacto(Long idContacto, String correo, String telefono, String direccion) {
        this.idContacto = idContacto;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // ===================
    // Getters y setters
    // ===================

    public Long getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Long idContacto) {
        this.idContacto = idContacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // ===================
    // Método toString
    // ===================

    @Override
    public String toString() {
        return "Contacto [idContacto=" + idContacto + ", correo=" + correo + ", telefono=" + telefono + ", direccion="
                + direccion + "]";
    }

    
}
