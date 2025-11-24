package br.com.petfriends.pedido.core.exception;

public final class DinheiroNegativoException extends CoreException {
    public DinheiroNegativoException() {
        super("dinheiro.negativo", "O dinheiro não pode ser negativo.");
    }
}
