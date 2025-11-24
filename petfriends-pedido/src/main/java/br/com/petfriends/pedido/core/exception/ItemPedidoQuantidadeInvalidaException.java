package br.com.petfriends.pedido.core.exception;

import br.com.petfriends.pedido.core.model.ItemPedido;

public final class ItemPedidoQuantidadeInvalidaException extends CoreException {
    private static final String MESSAGE = String.format(
            "A quantidade do item de um pedido não pode ser menor que %d.",
            ItemPedido.QUANTIDADE_MINIMA
    );

    public ItemPedidoQuantidadeInvalidaException() {
        super("item.pedido.quantidade.invalida", MESSAGE);
    }
}
