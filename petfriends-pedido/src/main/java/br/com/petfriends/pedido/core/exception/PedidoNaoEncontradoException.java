package br.com.petfriends.pedido.core.exception;

public final class PedidoNaoEncontradoException extends NotFoundException {
    public PedidoNaoEncontradoException() {
        super("pedido.nao.encontrado", "Não foi possível encontrar o pedido.");
    }
}
