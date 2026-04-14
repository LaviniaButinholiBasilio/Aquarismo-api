package com.aquarismo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder
public class VendaResponse {
    private Long id;
    private Long peixeId;
    private String peixeNome;
    private String peixeEspecie;
    private LocalDate dataVenda;
    private BigDecimal valorUnitario;
    private String nomeComprador;
    private String telefoneComprador;
    private String emailComprador;
    private String observacoes;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
