package br.com.petfriends.pedido.core.exception;

public final class ItemPedidoQuantidadeNuloException extends CoreException {
    public ItemPedidoQuantidadeNuloException() {
        super("item.pedido.quantidade.nulo", "A quantidade do item pedido não pode ser nulo.");
    }
}
