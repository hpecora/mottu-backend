package com.mottu.mototracker.controller;

import com.mottu.mototracker.auth.dto.CreateLocalizacaoRequest;
import com.mottu.mototracker.model.Localizacao;
import com.mottu.mototracker.repository.MotoRepository;
import com.mottu.mototracker.service.LocalizacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map; // <-- faltava

@RestController
@RequestMapping("/api/localizacoes")
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;
    private final MotoRepository motoRepository; // <-- faltava

    public LocalizacaoController(LocalizacaoService localizacaoService,
                                 MotoRepository motoRepository) {
        this.localizacaoService = localizacaoService;
        this.motoRepository = motoRepository;
    }

    // GET /api/localizacoes  ou  /api/localizacoes?motoId=1
    @GetMapping
    public ResponseEntity<List<Localizacao>> list(@RequestParam(required = false) Long motoId) {
        return ResponseEntity.ok(motoId != null
                ? localizacaoService.listByMoto(motoId)
                : localizacaoService.listAll());
    }

    // POST /api/localizacoes
    @PostMapping
    public ResponseEntity<Localizacao> create(@RequestBody CreateLocalizacaoRequest req) {
        var moto = motoRepository.findById(req.getMotoId())
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + req.getMotoId()));

        var loc = new Localizacao();
        loc.setLatitude(req.getLatitude());
        loc.setLongitude(req.getLongitude());
        loc.setObservacao(req.getObservacao());
        loc.setDataHora(LocalDateTime.now());
        loc.setMoto(moto);

        return ResponseEntity.ok(localizacaoService.save(loc));
    }

    // PATCH /api/localizacoes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Localizacao> updateObs(@PathVariable Long id,
                                                 @RequestBody Map<String, String> req) {
        return ResponseEntity.ok(
                localizacaoService.updateObservacao(id, req.getOrDefault("observacao", ""))
        );
    }

    // DELETE /api/localizacoes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localizacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
