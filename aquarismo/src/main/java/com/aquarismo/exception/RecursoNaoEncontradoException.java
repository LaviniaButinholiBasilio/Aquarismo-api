package com.aquarismo.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(String.format("%s com ID %d não encontrado(a).", recurso, id));
    }
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
