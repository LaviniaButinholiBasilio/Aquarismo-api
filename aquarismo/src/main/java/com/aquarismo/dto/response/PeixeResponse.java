package com.aquarismo.dto.response;

import com.aquarismo.model.SexoPeixe;
import com.aquarismo.model.StatusPeixe;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder
public class PeixeResponse {
    private Long id;
    private String nome;
    private String nomeCientifico;
    private String especie;
    private SexoPeixe sexo;
    private LocalDate dataAquisicao;
    private LocalDate dataNascimento;
    private BigDecimal precoAquisicao;
    private String observacoes;
    private StatusPeixe status;
    private Long tanqueId;
    private String tanqueNome;
    private Long maeId;
    private String maeNome;
    private Long paiId;
    private String paiNome;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
