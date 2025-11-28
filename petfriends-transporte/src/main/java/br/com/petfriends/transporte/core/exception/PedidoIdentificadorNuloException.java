package br.com.petfriends.transporte.core.exception;

public final class PedidoIdentificadorNuloException extends CoreException {
    public PedidoIdentificadorNuloException() {
        super("pedido.id.nulo", "O identificador do pedido não pode ser nulo.");
    }
}
