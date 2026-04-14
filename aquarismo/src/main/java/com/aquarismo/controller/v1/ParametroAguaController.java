package com.aquarismo.controller.v1;

import com.aquarismo.dto.request.ParametroAguaRequest;
import com.aquarismo.dto.response.ParametroAguaResponse;
import com.aquarismo.service.ParametroAguaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parametros-agua")
@RequiredArgsConstructor
public class ParametroAguaController {

    private final ParametroAguaService parametroService;

    @GetMapping("/tanque/{tanqueId}")
    public ResponseEntity<List<ParametroAguaResponse>> listarPorTanque(@PathVariable Long tanqueId) {
        return ResponseEntity.ok(parametroService.listarPorTanque(tanqueId));
    }

    @GetMapping("/tanque/{tanqueId}/ultima")
    public ResponseEntity<ParametroAguaResponse> ultimaMedicao(@PathVariable Long tanqueId) {
        return ResponseEntity.ok(parametroService.ultimaMedicao(tanqueId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParametroAguaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(parametroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ParametroAguaResponse> registrar(@Valid @RequestBody ParametroAguaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parametroService.registrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        parametroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
