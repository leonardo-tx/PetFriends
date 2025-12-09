package br.com.petfriends.pedido.core.exception;

public final class PedidoIntregavelException extends CoreException {
    public PedidoIntregavelException() {
        super("pedido.intregavel", "É impossível entregar um pedido que não esteja enviado.");
    }
}
