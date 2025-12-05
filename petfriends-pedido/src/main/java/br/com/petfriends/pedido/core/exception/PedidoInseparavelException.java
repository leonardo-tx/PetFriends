package br.com.petfriends.pedido.core.exception;

public final class PedidoInseparavelException extends CoreException {
    public PedidoInseparavelException() {
        super("pedido.inseparavel", "É impossível separar um pedido que não esteja em separação.");
    }
}
