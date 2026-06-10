package com.example.Analisis.Services;

import com.example.Analisis.Database.FacturaRepository;
import com.example.Analisis.Models.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }

    public List<Factura> listarPorEstado(String estado) {
        return facturaRepository.findByEstadoPago(estado);
    }

    public List<Factura> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return facturaRepository.findByFechaEmisionBetween(inicio, fin);
    }

    public Optional<Factura> buscarPorId(Integer id) {
        return facturaRepository.findById(id);
    }

    public Optional<Factura> buscarPorNumero(String numero) {
        return facturaRepository.findByNumeroFactura(numero);
    }

    public Factura guardar(Factura factura) {
        if (factura.getFechaEmision() == null) {
            factura.setFechaEmision(LocalDate.now());
        }
        if (factura.getEstadoPago() == null) {
            factura.setEstadoPago("Pendiente");
        }
        // Calcular IGV y total
        if (factura.getSubtotal() != null) {
            BigDecimal igv = factura.getSubtotal().multiply(new BigDecimal("0.18"));
            factura.setIgv(igv);
            factura.setTotal(factura.getSubtotal().add(igv));
        }
        return facturaRepository.save(factura);
    }

    public void eliminar(Integer id) {
        facturaRepository.deleteById(id);
    }

    public long contarTodas() {
        return facturaRepository.count();
    }

    public long contarPorEstado(String estado) {
        return facturaRepository.findByEstadoPago(estado).size();
    }

    public BigDecimal calcularIngresosPorMes(int mes, int año) {
        return facturaRepository.findAll().stream()
                .filter(f -> "Pagado".equals(f.getEstadoPago())
                          && f.getFechaEmision().getMonthValue() == mes
                          && f.getFechaEmision().getYear() == año)
                .map(Factura::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String generarNumeroFactura() {
        long count = facturaRepository.count() + 1;
        return String.format("F-%05d", count);
    }
}
