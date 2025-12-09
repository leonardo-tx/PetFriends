package br.com.petfriends.transporte.core.exception;

public final class RemessaIntregavelException extends CoreException {
    public RemessaIntregavelException() {
        super("remessa.intregavel", "Não é possível entregar uma remessa que já está entregue.");
    }
}
