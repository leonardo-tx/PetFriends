package br.com.petfriends.transporte.core.exception;

public final class RemessaItensVazioException extends CoreException {
    public RemessaItensVazioException() {
        super("remessa.itens.vazio", "Precisa haver pelo menos um item em uma reserva.");
    }
}
