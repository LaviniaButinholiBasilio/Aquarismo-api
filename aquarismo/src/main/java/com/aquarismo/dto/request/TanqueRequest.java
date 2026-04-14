package com.aquarismo.dto.request;

import com.aquarismo.model.StatusTanque;
import com.aquarismo.model.TipoAgua;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TanqueRequest {

    @NotBlank(message = "Nome do tanque é obrigatório")
    private String nome;

    @NotNull(message = "Capacidade em litros é obrigatória")
    @Positive(message = "Capacidade deve ser um valor positivo")
    private Double capacidadeLitros;

    @NotNull(message = "Tipo de água é obrigatório")
    private TipoAgua tipoAgua;

    private String localizacao;
    private String descricao;
    private StatusTanque status;
}
