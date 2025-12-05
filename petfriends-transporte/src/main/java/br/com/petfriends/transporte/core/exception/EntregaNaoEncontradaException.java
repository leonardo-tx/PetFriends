package br.com.petfriends.transporte.core.exception;

public final class EntregaNaoEncontradaException extends NotFoundException {
    public EntregaNaoEncontradaException() {
        super("entrega.nao.encontrada", "Não foi possível encontrar a entrega.");
    }
}
