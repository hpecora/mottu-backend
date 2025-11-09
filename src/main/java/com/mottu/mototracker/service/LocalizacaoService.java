package com.mottu.mototracker.service;

import com.mottu.mototracker.model.Localizacao;
import com.mottu.mototracker.repository.LocalizacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LocalizacaoService {

    private final LocalizacaoRepository repo;

    public LocalizacaoService(LocalizacaoRepository repo) { this.repo = repo; }

    public List<Localizacao> listAll() { return repo.findAll(); }

    public List<Localizacao> listByMoto(Long motoId) {
        return repo.findByMoto_Id(motoId);
    }

    @Transactional
    public Localizacao save(Localizacao localizacao) {
        if (localizacao.getMoto() == null || localizacao.getMoto().getId() == null) {
            throw new IllegalArgumentException("Moto é obrigatória para salvar localização.");
        }
        return repo.save(localizacao);
    }


    @Transactional
    public Localizacao updateObservacao(Long id, String observacao) {
        var loc = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Localização não encontrada: " + id));
        loc.setObservacao(observacao);
        return repo.save(loc);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("Localização não encontrada: " + id);
        repo.deleteById(id);
    }
}