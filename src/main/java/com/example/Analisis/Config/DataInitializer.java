package com.example.Analisis.Config;

import com.example.Analisis.Database.UsuarioRepository;
import com.example.Analisis.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen usuarios
        if (usuarioRepository.count() == 0) {
            System.out.println("Inicializando usuarios por defecto...");

            // Crear usuario administrador
            Usuario admin = new Usuario();
            admin.setNombreCompleto("Pedro Pablo Camilo");
            admin.setNombreUsuario("admin");
            admin.setEmail("pedro.camilo@canchas.com");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol("Administrador");
            admin.setTelefono("999888777");
            admin.setEstado("Activo");
            admin.setFechaRegistro(LocalDate.now());
            usuarioRepository.save(admin);

            // Crear usuario asesor
            Usuario asesor = new Usuario();
            asesor.setNombreCompleto("Ana García López");
            asesor.setNombreUsuario("asesor");
            asesor.setEmail("ana.garcia@canchas.com");
            asesor.setContrasena(passwordEncoder.encode("asesor123"));
            asesor.setRol("Asesor");
            asesor.setTelefono("987654321");
            asesor.setEstado("Activo");
            asesor.setFechaRegistro(LocalDate.now());
            usuarioRepository.save(asesor);

            System.out.println("✓ Usuarios creados exitosamente!");
            System.out.println("  - Usuario: admin | Contraseña: admin123 | Rol: Administrador");
            System.out.println("  - Usuario: asesor | Contraseña: asesor123 | Rol: Asesor");
        } else {
            System.out.println("Los usuarios ya están inicializados.");
        }
    }
}
