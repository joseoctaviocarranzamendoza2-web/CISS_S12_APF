package com.example.Analisis.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Analisis.Database.ClienteRepository;
import com.example.Analisis.Models.Cliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    public Cliente guardar(Cliente cliente) {
        if (cliente.getFechaRegistro() == null) {
            cliente.setFechaRegistro(LocalDate.now());
        }
        if (cliente.getEstado() == null) {
            cliente.setEstado("Activo");
        }
        return clienteRepository.save(cliente);
    }

    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }

    public long contarTodos() {
        return clienteRepository.count();
    }

    public long contarActivos() {
        return clienteRepository.findAll().stream()
                .filter(c -> "Activo".equals(c.getEstado()))
                .count();
    }

    public long contarPorMes(int mes, int año) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getFechaRegistro().getMonthValue() == mes 
                          && c.getFechaRegistro().getYear() == año)
                .count();
    }

    public long contarPorDia(LocalDate fecha) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getFechaRegistro().equals(fecha))
                .count();
    }
}
