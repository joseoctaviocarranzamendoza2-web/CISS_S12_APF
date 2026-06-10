package com.example.Analisis.Secure;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Analisis.Database.UsuarioRepository;
import com.example.Analisis.Models.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (!"Activo".equals(usuario.getEstado())) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        String role = "ROLE_" + usuario.getRol().toUpperCase().replace(" ", "_");

        return User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}



