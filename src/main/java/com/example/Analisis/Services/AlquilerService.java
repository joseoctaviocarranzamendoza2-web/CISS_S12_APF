package com.example.Analisis.Services;

import com.example.Analisis.Database.AlquilerRepository;
import com.example.Analisis.Models.Alquiler;
import com.example.Analisis.Models.CampoFutbol;
import com.example.Analisis.Models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<Alquiler> listarTodos() {
        return alquilerRepository.findAll();
    }

    public List<Alquiler> listarPorCliente(Cliente cliente) {
        return alquilerRepository.findByCliente(cliente);
    }

    public List<Alquiler> listarPorCampo(CampoFutbol campo) {
        return alquilerRepository.findByCampoFutbol(campo);
    }

    public List<Alquiler> listarPorFecha(LocalDate fecha) {
        return alquilerRepository.findByFechaAlquiler(fecha);
    }

    public List<Alquiler> listarPorEstado(String estado) {
        return alquilerRepository.findByEstado(estado);
    }

    public List<Alquiler> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return alquilerRepository.findByFechaAlquilerBetween(inicio, fin);
    }

    public Optional<Alquiler> buscarPorId(Integer id) {
        return alquilerRepository.findById(id);
    }

    public Alquiler guardar(Alquiler alquiler) {
        if (alquiler.getFechaRegistro() == null) {
            alquiler.setFechaRegistro(LocalDate.now());
        }
        if (alquiler.getEstado() == null) {
            alquiler.setEstado("Pendiente");
        }
        return alquilerRepository.save(alquiler);
    }

    public void eliminar(Integer id) {
        alquilerRepository.deleteById(id);
    }

    public long contarTodos() {
        return alquilerRepository.count();
    }

    public long contarPorEstado(String estado) {
        return alquilerRepository.findByEstado(estado).size();
    }

    public long contarPorFecha(LocalDate fecha) {
        return alquilerRepository.findByFechaAlquiler(fecha).size();
    }

    public BigDecimal calcularIngresosPorFecha(LocalDate fecha) {
        return alquilerRepository.findByFechaAlquiler(fecha).stream()
                .filter(a -> "Confirmado".equals(a.getEstado()))
                .map(Alquiler::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularIngresosPorRango(LocalDate inicio, LocalDate fin) {
        return alquilerRepository.findByFechaAlquilerBetween(inicio, fin).stream()
                .filter(a -> "Confirmado".equals(a.getEstado()))
                .map(Alquiler::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
