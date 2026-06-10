package com.example.Analisis.Database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Analisis.Models.CampoFutbol;

@Repository
public interface CampoFutbolRepository extends JpaRepository<CampoFutbol, Integer> {
    List<CampoFutbol> findByDisponibilidad(Boolean disponibilidad);
    List<CampoFutbol> findByNombreCampoContaining(String nombre);
}
