package br.com.petfriends.transporte.core.exception;

public final class RemessaIntransportavelException extends CoreException {
    public RemessaIntransportavelException() {
        super("remessa.intransportavel", "Não é possível iniciar um transporte de uma remessa que já foi transportada.");
    }
}
