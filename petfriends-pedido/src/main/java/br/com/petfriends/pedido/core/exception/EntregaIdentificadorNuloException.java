package br.com.petfriends.pedido.core.exception;

public final class EntregaIdentificadorNuloException extends CoreException {
    public EntregaIdentificadorNuloException() {
        super("entrega.id.nulo", "O identificador da entrega não pode ser nulo.");
    }
}
