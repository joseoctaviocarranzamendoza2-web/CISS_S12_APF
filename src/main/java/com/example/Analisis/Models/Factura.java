package com.example.Analisis.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_factura")
    private Integer codigoFactura;

    @Column(name = "numero_factura", nullable = false, unique = true, length = 20)
    private String numeroFactura;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_alquiler", nullable = false)
    private Alquiler alquiler;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv", nullable = false, precision = 10, scale = 2)
    private BigDecimal igv;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago; // "Pagado", "Pendiente", "Anulado"

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        if (fechaEmision == null) {
            fechaEmision = LocalDate.now();
        }
        if (estadoPago == null) {
            estadoPago = "Pendiente";
        }
        // Calcular IGV (18%) y total
        if (subtotal != null && igv == null) {
            igv = subtotal.multiply(new BigDecimal("0.18"));
            total = subtotal.add(igv);
        }
    }
}
