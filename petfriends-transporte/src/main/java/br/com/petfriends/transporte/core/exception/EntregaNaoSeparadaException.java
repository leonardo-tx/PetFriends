package br.com.petfriends.transporte.core.exception;

public final class EntregaNaoSeparadaException extends CoreException {
    public EntregaNaoSeparadaException() {
        super("entrega.nao.separada", "A entrega ainda não foi separada.");
    }
}
