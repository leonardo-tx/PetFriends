package br.com.petfriends.pedido.core.exception;

public final class DinheiroNuloException extends CoreException {
    public DinheiroNuloException() {
        super("dinheiro.nulo", "O dinheiro não pode ser nulo.");
    }
}
