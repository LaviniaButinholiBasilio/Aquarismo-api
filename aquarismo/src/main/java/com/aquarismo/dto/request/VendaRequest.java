package com.aquarismo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VendaRequest {

    @NotNull(message = "ID do peixe é obrigatório")
    private Long peixeId;

    @NotNull(message = "Data da venda é obrigatória")
    @PastOrPresent(message = "Data da venda não pode ser futura")
    private LocalDate dataVenda;

    @NotNull(message = "Valor unitário é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valorUnitario;

    @NotBlank(message = "Nome do comprador é obrigatório")
    private String nomeComprador;

    private String telefoneComprador;

    @Email(message = "E-mail inválido")
    private String emailComprador;

    private String observacoes;
}
