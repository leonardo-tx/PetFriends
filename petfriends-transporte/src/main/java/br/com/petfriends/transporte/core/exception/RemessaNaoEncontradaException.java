package br.com.petfriends.transporte.core.exception;

public final class RemessaNaoEncontradaException extends NotFoundException {
    public RemessaNaoEncontradaException() {
        super("remessa.nao.encontrada", "Não foi possível encontrar a remessa.");
    }
}
