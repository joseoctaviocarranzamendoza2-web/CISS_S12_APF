package com.example.Analisis.Models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Integer codigoUsuario;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol; // "Administrador" o "Asesor"

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado; // "Activo" o "Inactivo"

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
        if (estado == null) {
            estado = "Activo";
        }
    }
}
