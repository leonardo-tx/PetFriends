package br.com.petfriends.pedido.core.exception;

public final class PedidoNaoInicializavelException extends CoreException {
    public PedidoNaoInicializavelException() {
        super("pedido.nao.inicializavel", "É impossível iniciar um pedido já iniciado ou cancelado.");
    }
}
