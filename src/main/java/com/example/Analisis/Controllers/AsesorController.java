package com.example.Analisis.Controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Analisis.Services.AlquilerService;
import com.example.Analisis.Services.CampoFutbolService;
import com.example.Analisis.Services.ClienteService;
import com.example.Analisis.Services.FacturaService;

@Controller
@RequestMapping("/asesor")
public class AsesorController {

    @Autowired
    private CampoFutbolService campoFutbolService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private FacturaService facturaService;

    /**
     * Menú principal del asesor
     */
    @GetMapping("/menu")
    public String menuAsesor(Model model) {
        LocalDate hoy = LocalDate.now();
        model.addAttribute("reservasHoy", alquilerService.contarPorFecha(hoy));
        model.addAttribute("camposDisponibles", campoFutbolService.contarDisponibles());
        model.addAttribute("proximasReservas", alquilerService.listarPorFecha(hoy));
        return "Asesor/Menu_Asesor";
    }

    /**
     * Ver campos/canchas disponibles
     */
    @GetMapping("/campo")
    public String verCampo(Model model) {
        model.addAttribute("campos", campoFutbolService.listarTodos());
        model.addAttribute("camposDisponibles", campoFutbolService.listarDisponibles());
        return "Asesor/AV_Campo";
    }

    /**
     * Gestión de clientes
     */
    @GetMapping("/clientes")
    public String gestionClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "Asesor/AV_Clientes";
    }

    /**
     * Gestión de alquileres/reservas
     */
    @GetMapping("/alquileres")
    public String gestionAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerService.listarTodos());
        model.addAttribute("campos", campoFutbolService.listarDisponibles());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "Asesor/AV_Alquileres";
    }

    /**
     * Gestión de facturas
     */
    @GetMapping("/facturas")
    public String gestionFacturas(Model model) {
        model.addAttribute("facturas", facturaService.listarTodas());
        model.addAttribute("alquileres", alquilerService.listarPorEstado("Confirmado"));
        return "Asesor/AV_Facturas";
    }
}
