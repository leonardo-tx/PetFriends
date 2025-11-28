package br.com.petfriends.pedido.core.exception;

public final class PedidoImpagavelException extends CoreException {
    public PedidoImpagavelException() {
        super("pedido.impagavel", "É impossível pagar um pedido já pago ou cancelado.");
    }
}
