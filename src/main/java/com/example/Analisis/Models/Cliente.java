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

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_cliente")
    private Integer codigoCliente;

    @Column(name = "dni", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado; // "Activo" o "Inactivo"

    @Column(name = "estado_cuenta", nullable = false, length = 20)
    private String estadoCuenta; // "Activo", "Deudor"

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
        if (estado == null) {
            estado = "Activo";
        }
        if (estadoCuenta == null) {
            estadoCuenta = "Activo";
        }
    }
}
