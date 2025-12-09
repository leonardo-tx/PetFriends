package br.com.petfriends.transporte.core.exception;

public final class EntregaNaoIniciadaException extends CoreException {
    public EntregaNaoIniciadaException() {
        super("entrega.nao.iniciada", "A entrega ainda não foi iniciada.");
    }
}
