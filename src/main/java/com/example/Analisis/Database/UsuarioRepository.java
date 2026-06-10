package com.example.Analisis.Database;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Analisis.Models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

}
