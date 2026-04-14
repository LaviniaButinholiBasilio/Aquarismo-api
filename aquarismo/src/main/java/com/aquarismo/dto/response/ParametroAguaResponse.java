package com.aquarismo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class ParametroAguaResponse {
    private Long id;
    private Long tanqueId;
    private String tanqueNome;
    private LocalDateTime dataHoraMedicao;
    private Double ph;
    private Double temperatura;
    private Double amonia;
    private Double nitrito;
    private Double nitrato;
    private Double durezaTotal;
    private Double durezaCarbonato;
    private Double oxigenioDissolv;
    private Double salinidade;
    private String observacoes;
    private LocalDateTime criadoEm;
}
