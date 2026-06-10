package com.example.Analisis.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Página principal del sistema
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Página de login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
