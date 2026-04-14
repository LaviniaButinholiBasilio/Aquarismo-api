package com.aquarismo.controller.v1;

import com.aquarismo.dto.request.PeixeRequest;
import com.aquarismo.dto.response.PeixeResponse;
import com.aquarismo.service.PeixeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/peixes")
@RequiredArgsConstructor
public class PeixeController {

    private final PeixeService peixeService;

    @GetMapping
    public ResponseEntity<List<PeixeResponse>> listarTodos() {
        return ResponseEntity.ok(peixeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeixeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(peixeService.buscarPorId(id));
    }

    @GetMapping("/tanque/{tanqueId}")
    public ResponseEntity<List<PeixeResponse>> listarPorTanque(@PathVariable Long tanqueId) {
        return ResponseEntity.ok(peixeService.listarPorTanque(tanqueId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PeixeResponse>> buscar(@RequestParam String termo) {
        return ResponseEntity.ok(peixeService.buscar(termo));
    }

    @PostMapping
    public ResponseEntity<PeixeResponse> criar(@Valid @RequestBody PeixeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(peixeService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeixeResponse> atualizar(
            @PathVariable Long id, @Valid @RequestBody PeixeRequest request) {
        return ResponseEntity.ok(peixeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        peixeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
