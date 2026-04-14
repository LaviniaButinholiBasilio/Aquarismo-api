package com.aquarismo.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class ErroDetalhe {
    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String caminho;
    private List<CampoErro> errosDeCampo;

    @Data @Builder
    public static class CampoErro {
        private String campo;
        private String mensagem;
    }
}
