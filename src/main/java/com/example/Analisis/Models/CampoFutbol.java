package com.example.Analisis.Models;

import java.math.BigDecimal;

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
@Table(name = "campo_futbol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampoFutbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_campo")
    private Integer codigoCampo;

    @Column(name = "nombre_campo", nullable = false, length = 100)
    private String nombreCampo;

    @Column(name = "ubicacion_campo", nullable = false, length = 150)
    private String ubicacionCampo;

    @Column(name = "tipo_cesped", nullable = false, length = 50)
    private String tipoCesped;

    @Column(name = "capacidad_personas", nullable = false)
    private Integer capacidadPersonas;

    @Column(name = "precio_hora", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioHora;

    @Column(name = "disponibilidad", nullable = false)
    private Boolean disponibilidad; // true = Disponible, false = Ocupado

    @PrePersist
    protected void onCreate() {
        if (disponibilidad == null) {
            disponibilidad = true;
        }
    }
}
