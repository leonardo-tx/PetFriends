package br.com.petfriends.pedido.core.exception;

public final class ClienteIdentificadorNuloException extends CoreException {
    public ClienteIdentificadorNuloException() {
        super("cliente.id.nulo", "O identificador do cliente não pode ser nulo.");
    }
}
