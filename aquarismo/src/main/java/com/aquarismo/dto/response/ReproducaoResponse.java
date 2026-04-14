package com.aquarismo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder
public class ReproducaoResponse {
    private Long id;
    private Long maeId;
    private String maeNome;
    private Long paiId;
    private String paiNome;
    private LocalDate dataReproducao;
    private LocalDate dataEclosao;
    private Integer quantidadeOvos;
    private Integer quantidadeFilhotes;
    private Integer filhotesVivos;
    private String observacoes;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
