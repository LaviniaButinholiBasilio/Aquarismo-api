package com.aquarismo.controller.v1;

import com.aquarismo.dto.request.TanqueRequest;
import com.aquarismo.dto.response.TanqueResponse;
import com.aquarismo.service.TanqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tanques")
@RequiredArgsConstructor
public class TanqueController {

    private final TanqueService tanqueService;

    @GetMapping
    public ResponseEntity<List<TanqueResponse>> listarTodos() {
        return ResponseEntity.ok(tanqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TanqueResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tanqueService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TanqueResponse> criar(@Valid @RequestBody TanqueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tanqueService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TanqueResponse> atualizar(
            @PathVariable Long id, @Valid @RequestBody TanqueRequest request) {
        return ResponseEntity.ok(tanqueService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tanqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
