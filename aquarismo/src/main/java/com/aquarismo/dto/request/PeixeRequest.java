package com.aquarismo.dto.request;

import com.aquarismo.model.SexoPeixe;
import com.aquarismo.model.StatusPeixe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PeixeRequest {

    @NotBlank(message = "Nome do peixe é obrigatório")
    private String nome;

    private String nomeCientifico;

    @NotBlank(message = "Espécie é obrigatória")
    private String especie;

    private SexoPeixe sexo;

    @NotNull(message = "Data de aquisição é obrigatória")
    @PastOrPresent(message = "Data de aquisição não pode ser futura")
    private LocalDate dataAquisicao;

    private LocalDate dataNascimento;

    private BigDecimal precoAquisicao;

    private String observacoes;

    private StatusPeixe status;

    @NotNull(message = "ID do tanque é obrigatório")
    private Long tanqueId;

    private Long maeId;
    private Long paiId;
}
