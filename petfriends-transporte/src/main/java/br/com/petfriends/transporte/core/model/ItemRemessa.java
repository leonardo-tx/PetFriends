package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.ItemIdentificadorNuloException;
import br.com.petfriends.transporte.core.exception.ItemRemessaQuantidadeInvalidaException;

public final class ItemRemessa {
    public static final int QUANTIDADE_MINIMA = 1;

    private final String itemId;
    private final int quantidade;

    public ItemRemessa(String itemId, int quantidade) {
        if (itemId == null) {
            throw new ItemIdentificadorNuloException();
        }
        if (quantidade < QUANTIDADE_MINIMA) {
            throw new ItemRemessaQuantidadeInvalidaException();
        }
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
