package com.example.Analisis.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Analisis.Database.CampoFutbolRepository;
import com.example.Analisis.Models.CampoFutbol;

@Service
public class CampoFutbolService {

    @Autowired
    private CampoFutbolRepository campoFutbolRepository;

    public List<CampoFutbol> listarTodos() {
        return campoFutbolRepository.findAll();
    }

    public List<CampoFutbol> listarDisponibles() {
        return campoFutbolRepository.findByDisponibilidad(true);
    }

    public List<CampoFutbol> listarOcupados() {
        return campoFutbolRepository.findByDisponibilidad(false);
    }

    public Optional<CampoFutbol> buscarPorId(Integer id) {
        return campoFutbolRepository.findById(id);
    }

    public CampoFutbol guardar(CampoFutbol campo) {
        if (campo.getDisponibilidad() == null) {
            campo.setDisponibilidad(true);
        }
        return campoFutbolRepository.save(campo);
    }

    public void eliminar(Integer id) {
        campoFutbolRepository.deleteById(id);
    }

    public long contarTodos() {
        return campoFutbolRepository.count();
    }

    public long contarDisponibles() {
        return campoFutbolRepository.findByDisponibilidad(true).size();
    }

    public long contarOcupados() {
        return campoFutbolRepository.findByDisponibilidad(false).size();
    }

    public double calcularCapacidadPromedio() {
        List<CampoFutbol> campos = campoFutbolRepository.findAll();
        if (campos.isEmpty()) return 0.0;
        return campos.stream()
                .mapToInt(CampoFutbol::getCapacidadPersonas)
                .average()
                .orElse(0.0);
    }
}
