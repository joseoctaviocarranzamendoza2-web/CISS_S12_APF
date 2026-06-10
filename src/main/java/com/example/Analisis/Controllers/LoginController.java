package com.example.Analisis.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    /**
     * Spring Security maneja automáticamente el login mediante /login POST
     * Este método redirige después de un login exitoso según el rol del usuario
     */
    @GetMapping("/login-success")
    public String loginSuccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated()) {
            // Verificar el rol del usuario y redirigir apropiadamente
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"))) {
                return "redirect:/admin/menu";
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASESOR"))) {
                return "redirect:/asesor/menu";
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
                return "redirect:/cliente/dashboard";
            }
        }
        
        // Si no tiene ningún rol válido, redirigir al login
        return "redirect:/login?error=true";
    }

    /**
     * Página de acceso denegado
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    /**
     * Cerrar sesión manual (Spring Security también maneja /logout automáticamente)
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
