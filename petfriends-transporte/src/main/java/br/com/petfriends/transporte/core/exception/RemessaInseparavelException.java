package br.com.petfriends.transporte.core.exception;

public final class RemessaInseparavelException extends CoreException {
    public RemessaInseparavelException() {
        super("remessa.inseparavel", "Não é possível separar uma remessa que já foi separada.");
    }
}
