package com.example.Analisis.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Analisis.Database.UsuarioRepository;
import com.example.Analisis.Models.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Usuario guardar(Usuario usuario) {
        // Encriptar contraseña si es nueva o si cambió
        if (usuario.getCodigoUsuario() == null || usuario.getContrasena() != null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDate.now());
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public long contarTodos() {
        return usuarioRepository.count();
    }

    public long contarPorRol(String rol) {
        return usuarioRepository.findAll().stream()
                .filter(u -> rol.equals(u.getRol()))
                .count();
    }
}
