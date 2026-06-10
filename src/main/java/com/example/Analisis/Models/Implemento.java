package com.example.Analisis.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "implemento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Implemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_implemento")
    private Integer codigoImplemento;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "stock_total", nullable = false)
    private Integer stockTotal;

    @Column(name = "precio_alquiler", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioAlquiler;
}
