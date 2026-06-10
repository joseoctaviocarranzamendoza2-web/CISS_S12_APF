package com.example.Analisis.Database;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Analisis.Models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);
    boolean existsByDni(String dni);

}
