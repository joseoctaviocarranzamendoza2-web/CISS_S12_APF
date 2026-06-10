package com.example.Analisis.Controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.Analisis.Models.CampoFutbol;
import com.example.Analisis.Models.Cliente;
import com.example.Analisis.Models.Usuario;
import com.example.Analisis.Services.AlquilerService;
import com.example.Analisis.Services.CampoFutbolService;
import com.example.Analisis.Services.ClienteService;
import com.example.Analisis.Services.FacturaService;
import com.example.Analisis.Services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CampoFutbolService campoFutbolService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Menú principal del administrador
     */
    @GetMapping("/menu")
    public String menuAdmin(Model model) {
        LocalDate hoy = LocalDate.now();
        model.addAttribute("totalAlquileres", alquilerService.contarTodos());
        model.addAttribute("totalClientes", clienteService.contarTodos());
        model.addAttribute("totalCampos", campoFutbolService.contarTodos());
        model.addAttribute("ingresosHoy", alquilerService.calcularIngresosPorFecha(hoy));
        return "Admin/Menu_Admin";
    }

    /**
     * Gestión de campos/canchas
     */
    @GetMapping("/campo")
    public String gestionCampo(Model model) {
        model.addAttribute("campos", campoFutbolService.listarTodos());
        model.addAttribute("totalCampos", campoFutbolService.contarTodos());
        model.addAttribute("camposDisponibles", campoFutbolService.contarDisponibles());
        model.addAttribute("camposOcupados", campoFutbolService.contarOcupados());
        model.addAttribute("capacidadPromedio", (int) campoFutbolService.calcularCapacidadPromedio());
        return "Admin/Adm_Campo";
    }

    /**
     * Gestión de clientes
     */
    @GetMapping("/clientes")
    public String gestionClientes(Model model) {
        LocalDate hoy = LocalDate.now();
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("totalClientes", clienteService.contarTodos());
        model.addAttribute("clientesActivos", clienteService.contarActivos());
        model.addAttribute("clientesMes", clienteService.contarPorMes(hoy.getMonthValue(), hoy.getYear()));
        model.addAttribute("clientesHoy", clienteService.contarPorDia(hoy));
        return "Admin/Adm_Clientes";
    }

    /**
     * Gestión de alquileres/reservas
     */
    @GetMapping("/alquileres")
    public String gestionAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerService.listarTodos());
        model.addAttribute("totalAlquileres", alquilerService.contarTodos());
        model.addAttribute("alquileresConfirmados", alquilerService.contarPorEstado("Confirmado"));
        model.addAttribute("alquileresPendientes", alquilerService.contarPorEstado("Pendiente"));
        model.addAttribute("alquileresCancelados", alquilerService.contarPorEstado("Cancelado"));
        model.addAttribute("campos", campoFutbolService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "Admin/Adm_Alquileres";
    }

    /**
     * Gestión de facturas
     */
    @GetMapping("/facturas")
    public String gestionFacturas(Model model) {
        LocalDate hoy = LocalDate.now();
        model.addAttribute("facturas", facturaService.listarTodas());
        model.addAttribute("totalFacturas", facturaService.contarTodas());
        model.addAttribute("ingresosMes", facturaService.calcularIngresosPorMes(hoy.getMonthValue(), hoy.getYear()));
        model.addAttribute("facturasMes", facturaService.listarPorRangoFechas(
                hoy.withDayOfMonth(1), hoy.withDayOfMonth(hoy.lengthOfMonth())).size());
        model.addAttribute("facturasHoy", facturaService.listarPorRangoFechas(hoy, hoy).size());
        model.addAttribute("alquileres", alquilerService.listarPorEstado("Confirmado"));
        return "Admin/Adm_Facturas";
    }

    /**
     * Gestión de usuarios (asesores y administradores)
     */
    @GetMapping("/usuarios")
    public String gestionUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("totalUsuarios", usuarioService.contarTodos());
        model.addAttribute("totalAdministradores", usuarioService.contarPorRol("Administrador"));
        model.addAttribute("totalAsesores", usuarioService.contarPorRol("Asesor"));
        return "Admin/Adm_Usuarios";
    }

    /**
     * Reportes por día
     */
    @GetMapping("/reportes")
    public String reportesPorDia(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(6);

        model.addAttribute("ingresosTotal", alquilerService.calcularIngresosPorRango(inicioSemana, hoy));
        model.addAttribute("totalAlquileres", alquilerService.listarPorRangoFechas(inicioSemana, hoy).size());
        model.addAttribute("promedioIngresos", alquilerService.calcularIngresosPorRango(inicioSemana, hoy)
                .divide(java.math.BigDecimal.valueOf(7), 2, java.math.RoundingMode.HALF_UP));
        model.addAttribute("totalClientes", clienteService.listarTodos().stream()
                .filter(c -> c.getFechaRegistro().isAfter(inicioSemana.minusDays(1))
                        && c.getFechaRegistro().isBefore(hoy.plusDays(1)))
                .count());
        model.addAttribute("alquileresPorDia", alquilerService.listarPorRangoFechas(inicioSemana, hoy));
        model.addAttribute("campos", campoFutbolService.listarTodos());
        return "Admin/Reportes_Por_Dia";
    }

    // ==================== OPERACIONES CRUD ====================

    /**
     * Guardar nuevo campo
     */
    @PostMapping("/campo/guardar")
    public String guardarCampo(@ModelAttribute CampoFutbol campo, RedirectAttributes redirectAttributes) {
        try {
            campoFutbolService.guardar(campo);
            redirectAttributes.addFlashAttribute("mensaje", "Campo creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear campo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/campo";
    }

    /**
     * Actualizar campo existente
     */
    @PostMapping("/campo/actualizar/{id}")
    public String actualizarCampo(@PathVariable Integer id, @ModelAttribute CampoFutbol campo,
            RedirectAttributes redirectAttributes) {
        try {
            campo.setCodigoCampo(id);
            campoFutbolService.guardar(campo);
            redirectAttributes.addFlashAttribute("mensaje", "Campo actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar campo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/campo";
    }

    /**
     * Eliminar campo
     */
    @PostMapping("/campo/eliminar/{id}")
    public String eliminarCampo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            campoFutbolService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Campo eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar campo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/campo";
    }

    /**
     * Guardar nuevo cliente
     */
    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            if (cliente.getFechaRegistro() == null) {
                cliente.setFechaRegistro(LocalDate.now());
            }
            if (cliente.getEstado() == null) {
                cliente.setEstado("Activo");
            }
            clienteService.guardar(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/clientes";
    }

    /**
     * Actualizar cliente existente
     */
    @PostMapping("/clientes/actualizar/{id}")
    public String actualizarCliente(@PathVariable Integer id, @ModelAttribute Cliente cliente,
            RedirectAttributes redirectAttributes) {
        try {
            cliente.setCodigoCliente(id);
            clienteService.guardar(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/clientes";
    }

    /**
     * Eliminar cliente
     */
    @PostMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar cliente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/clientes";
    }

    /**
     * Guardar nuevo usuario
     */
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            if (usuario.getEstado() == null) {
                usuario.setEstado("Activo");
            }
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario creado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al crear usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/usuarios";
    }

    /**
     * Actualizar usuario existente
     */
    @PostMapping("/usuarios/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Integer id, @ModelAttribute Usuario usuario,
            RedirectAttributes redirectAttributes) {
        try {
            usuario.setCodigoUsuario(id);
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/usuarios";
    }

    /**
     * Eliminar usuario
     */
    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/admin/usuarios";
    }
}
