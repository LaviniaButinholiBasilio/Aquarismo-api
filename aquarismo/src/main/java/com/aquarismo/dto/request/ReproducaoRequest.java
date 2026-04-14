package com.aquarismo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReproducaoRequest {

    @NotNull(message = "ID da mãe é obrigatório")
    private Long maeId;

    @NotNull(message = "ID do pai é obrigatório")
    private Long paiId;

    @NotNull(message = "Data de reprodução é obrigatória")
    @PastOrPresent(message = "Data de reprodução não pode ser futura")
    private LocalDate dataReproducao;

    private LocalDate dataEclosao;

    @Min(value = 0, message = "Quantidade de ovos não pode ser negativa")
    private Integer quantidadeOvos;

    @Min(value = 0, message = "Quantidade de filhotes não pode ser negativa")
    private Integer quantidadeFilhotes;

    @Min(value = 0, message = "Filhotes vivos não pode ser negativo")
    private Integer filhotesVivos;

    private String observacoes;
}
