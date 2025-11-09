// src/main/java/com/mottu/mototracker/service/MotoService.java
package com.mottu.mototracker.service;

import com.mottu.mototracker.model.Moto;
import com.mottu.mototracker.auth.dto.MotoUpdateRequest;
import com.mottu.mototracker.repository.MotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotoService {

    private final MotoRepository repo;

    public MotoService(MotoRepository repo) { this.repo = repo; }

    public List<Moto> list() { return repo.findAll(); }

    public Moto create(Moto m) { return repo.save(m); }

    @Transactional
    public Moto update(Long id, MotoUpdateRequest req) {
        Moto m = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        if (req.placa != null && !req.placa.isBlank()) m.setPlaca(req.placa);
        if (req.modelo != null && !req.modelo.isBlank()) m.setModelo(req.modelo);
        return m; // sujo será persistido pelo @Transactional
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("Moto não encontrada: " + id);
        repo.deleteById(id);
    }
}
