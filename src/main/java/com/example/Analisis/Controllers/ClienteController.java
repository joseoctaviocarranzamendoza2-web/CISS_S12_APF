package com.example.Analisis.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Analisis.Database.AlquilerImplementoRepository;
import com.example.Analisis.Database.AlquilerRepository;
import com.example.Analisis.Services.AlquilerService;
import com.example.Analisis.Services.CampoFutbolService;
import com.example.Analisis.Services.ClienteService;
import com.example.Analisis.Services.UsuarioService;
import com.example.Analisis.Models.Alquiler;
import com.example.Analisis.Models.AlquilerImplemento;
import com.example.Analisis.Models.Cliente;
import com.example.Analisis.Models.Usuario;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CampoFutbolService campoFutbolService;

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private AlquilerImplementoRepository alquilerImplementoRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Optional<Usuario> optUsuario = usuarioService.buscarPorNombreUsuario(username);

        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            Cliente cliente = usuario.getCliente();

            if (cliente != null) {
                model.addAttribute("cliente", cliente);
                model.addAttribute("usuario", usuario);

                // Fetch reservations for the client
                List<Alquiler> historialReservas = alquilerRepository.findByCliente(cliente);
                model.addAttribute("historialReservas", historialReservas);

                // Total reservations this month (simple logic: all for now, can be filtered by month)
                long totalReservas = historialReservas.size();
                model.addAttribute("totalReservas", totalReservas);

                // Next reservation
                Alquiler proximaReserva = historialReservas.stream()
                        .filter(a -> a.getEstado().equals("Pendiente") || a.getEstado().equals("Confirmado"))
                        .findFirst()
                        .orElse(null);
                model.addAttribute("proximaReserva", proximaReserva);

                // Fetch implements history
                List<AlquilerImplemento> historialImplementos = alquilerImplementoRepository.findByAlquiler_Cliente_CodigoCliente(cliente.getCodigoCliente());
                model.addAttribute("historialImplementos", historialImplementos);
                
                // Group implements by name for the UI summary
                int totalImplementosAlquilados = historialImplementos.stream().mapToInt(AlquilerImplemento::getCantidad).sum();
                model.addAttribute("totalImplementosAlquilados", totalImplementosAlquilados);
            }
        }

        return "Cliente/dashboard";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(Cliente clienteActualizado, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Usuario> optUsuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        if (optUsuario.isPresent()) {
            Cliente clienteExistente = optUsuario.get().getCliente();
            if (clienteExistente != null && clienteExistente.getCodigoCliente().equals(clienteActualizado.getCodigoCliente())) {
                clienteExistente.setEmail(clienteActualizado.getEmail());
                clienteExistente.setTelefono(clienteActualizado.getTelefono());
                clienteExistente.setDireccion(clienteActualizado.getDireccion());
                clienteService.guardar(clienteExistente);
            }
        }

        return "redirect:/cliente/dashboard";
    }

    @GetMapping("/reservar")
    public String nuevaReserva(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Usuario> optUsuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        if (optUsuario.isPresent()) {
            model.addAttribute("usuario", optUsuario.get());
            model.addAttribute("campos", campoFutbolService.listarTodos());
        }

        return "Cliente/nueva_reserva";
    }

    @PostMapping("/reservar")
    public String crearReserva(
            Integer codigoCampo, 
            java.time.LocalDate fechaAlquiler, 
            java.time.LocalTime horaInicio, 
            Integer totalHoras, 
            String metodoPago, 
            String observaciones, 
            Principal principal) {
        
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Usuario> optUsuario = usuarioService.buscarPorNombreUsuario(principal.getName());
        if (optUsuario.isPresent()) {
            Cliente cliente = optUsuario.get().getCliente();
            Optional<com.example.Analisis.Models.CampoFutbol> optCampo = campoFutbolService.buscarPorId(codigoCampo);

            if (cliente != null && optCampo.isPresent()) {
                com.example.Analisis.Models.CampoFutbol campo = optCampo.get();
                Alquiler alquiler = new Alquiler();
                
                alquiler.setCliente(cliente);
                alquiler.setCampoFutbol(campo);
                alquiler.setFechaAlquiler(fechaAlquiler);
                alquiler.setHoraInicio(horaInicio);
                alquiler.setHoraFin(horaInicio.plusHours(totalHoras));
                alquiler.setTotalHoras(totalHoras);
                
                java.math.BigDecimal precioTotal = campo.getPrecioHora().multiply(new java.math.BigDecimal(totalHoras));
                alquiler.setPrecioTotal(precioTotal);
                alquiler.setEstado("Pendiente");
                alquiler.setMetodoPago(metodoPago);
                alquiler.setObservaciones(observaciones);
                
                alquilerService.guardar(alquiler);
            }
        }

        return "redirect:/cliente/dashboard";
    }
}
