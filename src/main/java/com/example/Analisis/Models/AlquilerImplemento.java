package com.example.Analisis.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alquiler_implemento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerImplemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_alquiler_implemento")
    private Integer codigoAlquilerImplemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_alquiler", nullable = false)
    private Alquiler alquiler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_implemento", nullable = false)
    private Implemento implemento;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "estado_devolucion", nullable = false, length = 20)
    private String estadoDevolucion; // "Entregado", "Devuelto", "Pendiente"
}
