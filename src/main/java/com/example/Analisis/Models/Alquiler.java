package com.example.Analisis.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alquiler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_alquiler")
    private Integer codigoAlquiler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_campo", nullable = false)
    private CampoFutbol campoFutbol;

    @Column(name = "fecha_alquiler", nullable = false)
    private LocalDate fechaAlquiler;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "total_horas", nullable = false)
    private Integer totalHoras;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // "Confirmado", "Pendiente", "Cancelado"

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
        if (estado == null) {
            estado = "Pendiente";
        }
    }
}
