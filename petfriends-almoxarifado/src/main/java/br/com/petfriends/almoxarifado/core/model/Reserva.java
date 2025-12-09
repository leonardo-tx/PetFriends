package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.ItemQuantidadeNegativoException;
import br.com.petfriends.almoxarifado.core.exception.PedidoIdentificadorNuloException;

public final class Reserva {
    private final String pedidoId;
    private final int quantidade;

    public Reserva(String pedidoId, int quantidade) {
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        if (pedidoId == null) {
            throw new PedidoIdentificadorNuloException();
        }
        this.pedidoId = pedidoId;
        this.quantidade = quantidade;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
