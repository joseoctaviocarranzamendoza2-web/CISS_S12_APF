package com.example.Analisis.Database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Analisis.Models.AlquilerImplemento;

@Repository
public interface AlquilerImplementoRepository extends JpaRepository<AlquilerImplemento, Integer> {
    List<AlquilerImplemento> findByAlquiler_CodigoAlquiler(Integer codigoAlquiler);
    List<AlquilerImplemento> findByAlquiler_Cliente_CodigoCliente(Integer codigoCliente);
}
