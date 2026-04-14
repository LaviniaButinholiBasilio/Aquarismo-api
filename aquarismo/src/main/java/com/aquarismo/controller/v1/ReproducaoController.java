package com.aquarismo.controller.v1;

import com.aquarismo.dto.request.ReproducaoRequest;
import com.aquarismo.dto.response.ReproducaoResponse;
import com.aquarismo.service.ReproducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reproducoes")
@RequiredArgsConstructor
public class ReproducaoController {

    private final ReproducaoService reproducaoService;

    @GetMapping
    public ResponseEntity<List<ReproducaoResponse>> listarTodos() {
        return ResponseEntity.ok(reproducaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReproducaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reproducaoService.buscarPorId(id));
    }

    @GetMapping("/mae/{maeId}")
    public ResponseEntity<List<ReproducaoResponse>> listarPorMae(@PathVariable Long maeId) {
        return ResponseEntity.ok(reproducaoService.listarPorMae(maeId));
    }

    @GetMapping("/pai/{paiId}")
    public ResponseEntity<List<ReproducaoResponse>> listarPorPai(@PathVariable Long paiId) {
        return ResponseEntity.ok(reproducaoService.listarPorPai(paiId));
    }

    @PostMapping
    public ResponseEntity<ReproducaoResponse> registrar(@Valid @RequestBody ReproducaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reproducaoService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReproducaoResponse> atualizar(
            @PathVariable Long id, @Valid @RequestBody ReproducaoRequest request) {
        return ResponseEntity.ok(reproducaoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reproducaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
