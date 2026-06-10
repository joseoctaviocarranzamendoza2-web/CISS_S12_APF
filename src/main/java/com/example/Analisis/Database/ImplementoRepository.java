package com.example.Analisis.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Analisis.Models.Implemento;

@Repository
public interface ImplementoRepository extends JpaRepository<Implemento, Integer> {
}
