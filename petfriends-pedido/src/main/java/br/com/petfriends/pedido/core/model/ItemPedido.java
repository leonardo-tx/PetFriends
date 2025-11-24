package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.ItemPedidoQuantidadeInvalidaException;
import br.com.petfriends.pedido.core.exception.ItemPedidoQuantidadeNuloException;
import br.com.petfriends.pedido.core.exception.ProdutoIdentificadorNuloException;

public final class ItemPedido {
    public static final int QUANTIDADE_MINIMA = 1;

    private final String produtoId;
    private final Dinheiro valorUnitario;
    private final int quantidade;

    public ItemPedido(String produtoId, Dinheiro valorUnitario, Integer quantidade) {
        if (quantidade == null) {
            throw new ItemPedidoQuantidadeNuloException();
        }
        if (quantidade < QUANTIDADE_MINIMA) {
            throw new ItemPedidoQuantidadeInvalidaException();
        }
        if (produtoId == null) {
            throw new ProdutoIdentificadorNuloException();
        }
        this.produtoId = produtoId;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public Dinheiro getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
