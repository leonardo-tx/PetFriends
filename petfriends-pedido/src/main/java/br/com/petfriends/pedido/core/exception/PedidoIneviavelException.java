package br.com.petfriends.pedido.core.exception;

public final class PedidoIneviavelException extends CoreException {
    public PedidoIneviavelException() {
        super("pedido.ineviavel", "É impossível enviar um pedido que não esteja separado.");
    }
}
