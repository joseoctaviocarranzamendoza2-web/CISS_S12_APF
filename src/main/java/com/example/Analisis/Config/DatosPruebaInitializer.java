package com.example.Analisis.Config;

import com.example.Analisis.Models.*;
import com.example.Analisis.Database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Inicializador de datos de prueba para el sistema
 * Se ejecuta automáticamente al iniciar la aplicación si las tablas están vacías
 */
@Component
@Order(2) // Se ejecuta después de DataInitializer (usuarios)
public class DatosPruebaInitializer implements CommandLineRunner {

    @Autowired
    private CampoFutbolRepository campoFutbolRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo insertar si no hay campos en la base de datos
        if (campoFutbolRepository.count() == 0) {
            System.out.println("========================================");
            System.out.println("Insertando datos de prueba...");
            System.out.println("========================================");

            // 1. Insertar Campos de Fútbol
            CampoFutbol campo1 = new CampoFutbol();
            campo1.setNombreCampo("Cancha Fútbol 11 Premium");
            campo1.setUbicacionCampo("Av. Los Deportes 456");
            campo1.setTipoCesped("Sintético");
            campo1.setCapacidadPersonas(22);
            campo1.setPrecioHora(new BigDecimal("120.00"));
            campo1.setDisponibilidad(true);
            campoFutbolRepository.save(campo1);

            CampoFutbol campo2 = new CampoFutbol();
            campo2.setNombreCampo("Cancha Fútbol 7 Norte");
            campo2.setUbicacionCampo("Jr. Deportivo 789");
            campo2.setTipoCesped("Natural");
            campo2.setCapacidadPersonas(14);
            campo2.setPrecioHora(new BigDecimal("80.00"));
            campo2.setDisponibilidad(true);
            campoFutbolRepository.save(campo2);

            System.out.println("✓ 2 campos insertados");

            // 2. Insertar Clientes
            Cliente cliente1 = new Cliente();
            cliente1.setDni("12345678");
            cliente1.setNombreCompleto("Carlos Alberto Mendoza García");
            cliente1.setTelefono("987654321");
            cliente1.setEmail("carlos.mendoza@email.com");
            cliente1.setDireccion("Av. Principal 123, Lima");
            cliente1.setFechaRegistro(LocalDate.of(2025, 12, 1));
            cliente1.setEstado("Activo");
            clienteRepository.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setDni("87654321");
            cliente2.setNombreCompleto("María Elena Rodríguez López");
            cliente2.setTelefono("998877665");
            cliente2.setEmail("maria.rodriguez@email.com");
            cliente2.setDireccion("Jr. Los Olivos 456, Lima");
            cliente2.setFechaRegistro(LocalDate.of(2025, 12, 2));
            cliente2.setEstado("Activo");
            clienteRepository.save(cliente2);

            System.out.println("✓ 2 clientes insertados");

            // 3. Insertar Alquileres
            Alquiler alquiler1 = new Alquiler();
            alquiler1.setCliente(cliente1);
            alquiler1.setCampoFutbol(campo1);
            alquiler1.setFechaAlquiler(LocalDate.of(2025, 12, 5));
            alquiler1.setHoraInicio(LocalTime.of(15, 0));
            alquiler1.setHoraFin(LocalTime.of(17, 0));
            alquiler1.setTotalHoras(2);
            alquiler1.setPrecioTotal(new BigDecimal("240.00"));
            alquiler1.setEstado("Confirmado");
            alquiler1.setObservaciones("Partido amistoso");
            alquilerRepository.save(alquiler1);

            Alquiler alquiler2 = new Alquiler();
            alquiler2.setCliente(cliente2);
            alquiler2.setCampoFutbol(campo2);
            alquiler2.setFechaAlquiler(LocalDate.of(2025, 12, 6));
            alquiler2.setHoraInicio(LocalTime.of(18, 0));
            alquiler2.setHoraFin(LocalTime.of(20, 0));
            alquiler2.setTotalHoras(2);
            alquiler2.setPrecioTotal(new BigDecimal("160.00"));
            alquiler2.setEstado("Confirmado");
            alquiler2.setObservaciones("Entrenamiento de equipo");
            alquilerRepository.save(alquiler2);

            System.out.println("✓ 2 alquileres insertados");

            // 4. Insertar Facturas
            Factura factura1 = new Factura();
            factura1.setNumeroFactura("F-00001");
            factura1.setAlquiler(alquiler1);
            factura1.setFechaEmision(LocalDate.of(2025, 12, 5));
            // Calcular subtotal e IGV (18%)
            BigDecimal total1 = new BigDecimal("240.00");
            BigDecimal subtotal1 = total1.divide(new BigDecimal("1.18"), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal igv1 = total1.subtract(subtotal1);
            factura1.setSubtotal(subtotal1);
            factura1.setIgv(igv1);
            factura1.setTotal(total1);
            factura1.setEstadoPago("Pagado");
            facturaRepository.save(factura1);

            Factura factura2 = new Factura();
            factura2.setNumeroFactura("F-00002");
            factura2.setAlquiler(alquiler2);
            factura2.setFechaEmision(LocalDate.of(2025, 12, 6));
            BigDecimal total2 = new BigDecimal("160.00");
            BigDecimal subtotal2 = total2.divide(new BigDecimal("1.18"), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal igv2 = total2.subtract(subtotal2);
            factura2.setSubtotal(subtotal2);
            factura2.setIgv(igv2);
            factura2.setTotal(total2);
            factura2.setEstadoPago("Pagado");
            facturaRepository.save(factura2);

            System.out.println("✓ 2 facturas insertadas");

            System.out.println("========================================");
            System.out.println("RESUMEN DE DATOS INSERTADOS:");
            System.out.println("Campos: " + campoFutbolRepository.count());
            System.out.println("Clientes: " + clienteRepository.count());
            System.out.println("Alquileres: " + alquilerRepository.count());
            System.out.println("Facturas: " + facturaRepository.count());
            System.out.println("========================================");
        } else {
            System.out.println("Los datos de prueba ya existen en la base de datos.");
        }
    }
}
