package br.com.petfriends.pedido.core.exception;

public final class CEPNuloException extends CoreException {
    public CEPNuloException() {
        super("cep.nulo", "O CEP não pode ser nulo.");
    }
}
