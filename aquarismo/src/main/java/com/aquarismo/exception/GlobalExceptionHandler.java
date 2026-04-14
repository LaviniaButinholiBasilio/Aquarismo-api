package com.aquarismo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroDetalhe> handleNaoEncontrado(
            RecursoNaoEncontradoException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErroDetalhe.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .erro("Recurso não encontrado")
                        .mensagem(ex.getMessage())
                        .caminho(req.getRequestURI())
                        .build());
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroDetalhe> handleRegraDeNegocio(
            RegraDeNegocioException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErroDetalhe.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .erro("Regra de negócio violada")
                        .mensagem(ex.getMessage())
                        .caminho(req.getRequestURI())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDetalhe> handleValidacao(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ErroDetalhe.CampoErro> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> ErroDetalhe.CampoErro.builder()
                        .campo(f.getField())
                        .mensagem(f.getDefaultMessage())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErroDetalhe.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .erro("Erro de validação")
                        .mensagem("Um ou mais campos inválidos.")
                        .caminho(req.getRequestURI())
                        .errosDeCampo(erros)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDetalhe> handleGenerico(
            Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErroDetalhe.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .erro("Erro interno")
                        .mensagem("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                        .caminho(req.getRequestURI())
                        .build());
    }
}
