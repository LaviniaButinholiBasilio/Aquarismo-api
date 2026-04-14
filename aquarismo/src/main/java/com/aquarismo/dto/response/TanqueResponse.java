package com.aquarismo.dto.response;

import com.aquarismo.model.StatusTanque;
import com.aquarismo.model.TipoAgua;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class TanqueResponse {
    private Long id;
    private String nome;
    private Double capacidadeLitros;
    private TipoAgua tipoAgua;
    private String localizacao;
    private String descricao;
    private StatusTanque status;
    private Integer totalPeixesAtivos;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
