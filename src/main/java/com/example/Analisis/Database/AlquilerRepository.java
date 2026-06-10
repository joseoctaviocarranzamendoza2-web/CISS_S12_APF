package com.example.Analisis.Database;

import com.example.Analisis.Models.Alquiler;
import com.example.Analisis.Models.CampoFutbol;
import com.example.Analisis.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    List<Alquiler> findByCliente(Cliente cliente);
    List<Alquiler> findByCampoFutbol(CampoFutbol campoFutbol);
    List<Alquiler> findByFechaAlquiler(LocalDate fecha);
    List<Alquiler> findByEstado(String estado);
    List<Alquiler> findByFechaAlquilerBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
