package com.aquarismo.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ParametroAguaRequest {

    @NotNull(message = "ID do tanque é obrigatório")
    private Long tanqueId;

    private LocalDateTime dataHoraMedicao;

    @NotNull(message = "pH é obrigatório")
    @DecimalMin(value = "0.0", message = "pH mínimo é 0")
    @DecimalMax(value = "14.0", message = "pH máximo é 14")
    private Double ph;

    @NotNull(message = "Temperatura é obrigatória")
    private Double temperatura;

    private Double amonia;
    private Double nitrito;
    private Double nitrato;
    private Double durezaTotal;
    private Double durezaCarbonato;
    private Double oxigenioDissolv;
    private Double salinidade;
    private String observacoes;
}
