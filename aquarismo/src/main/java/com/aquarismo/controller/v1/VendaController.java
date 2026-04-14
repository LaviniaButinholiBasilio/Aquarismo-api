package com.aquarismo.controller.v1;

import com.aquarismo.dto.request.VendaRequest;
import com.aquarismo.dto.response.VendaResponse;
import com.aquarismo.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listarTodos() {
        return ResponseEntity.ok(vendaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @GetMapping("/peixe/{peixeId}")
    public ResponseEntity<List<VendaResponse>> listarPorPeixe(@PathVariable Long peixeId) {
        return ResponseEntity.ok(vendaService.listarPorPeixe(peixeId));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> totalNoPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(vendaService.totalNoPeriodo(inicio, fim));
    }

    @PostMapping
    public ResponseEntity<VendaResponse> registrar(@Valid @RequestBody VendaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.registrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
