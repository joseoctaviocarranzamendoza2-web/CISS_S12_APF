package com.example.Analisis.Database;

import com.example.Analisis.Models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    List<Factura> findByEstadoPago(String estadoPago);
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
