// src/main/java/com/mottu/mototracker/controller/MotoController.java
package com.mottu.mototracker.controller;

import com.mottu.mototracker.model.Moto;
import com.mottu.mototracker.auth.dto.MotoUpdateRequest;
import com.mottu.mototracker.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    private final MotoService service;
    public MotoController(MotoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Moto>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<Moto> create(@RequestBody Moto req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> update(@PathVariable Long id, @RequestBody MotoUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Moto> patch(@PathVariable Long id, @RequestBody MotoUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
