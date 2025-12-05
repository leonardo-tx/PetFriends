package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.*;

public final class ItemEstoque {
    private final String itemId;
    private final int quantidadeDisponivel;
    private final int quantidadeReservada;

    public ItemEstoque(String itemId, Integer quantidadeDisponivel, Integer quantidadeReservada) {
        if (itemId == null) {
            throw new ItemIdentificadorNuloException();
        }
        if (quantidadeDisponivel == null || quantidadeReservada == null) {
            throw new ItemQuantidadeNuloException();
        }
        if (quantidadeDisponivel < 0 || quantidadeReservada < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        if (quantidadeDisponivel < quantidadeReservada) {
            throw new ItemQuantidadeReservadaInvalidaException();
        }
        this.itemId = itemId;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeReservada = quantidadeReservada;
    }

    public ItemEstoque adicionarReserva(Integer quantidade) {
        if (quantidade == null) {
            throw new ItemQuantidadeNuloException();
        }
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        return new ItemEstoque(itemId, quantidadeDisponivel, quantidadeReservada + quantidade);
    }

    public ItemEstoque liberarReserva(Integer quantidade) {
        if (quantidade == null) {
            throw new ItemQuantidadeNuloException();
        }
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        return new ItemEstoque(itemId, quantidadeDisponivel, quantidadeReservada - quantidade);
    }

    public ItemEstoque consumirEstoque(Integer quantidade) {
        if (quantidade == null) {
            throw new ItemQuantidadeNuloException();
        }
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        return new ItemEstoque(
                itemId,
                quantidadeDisponivel - quantidade,
                quantidadeReservada - quantidade
        );
    }

    public ItemEstoque adicionarEstoque(Integer quantidade) {
        if (quantidade == null) {
            throw new ItemQuantidadeNuloException();
        }
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        return new ItemEstoque(itemId, quantidadeDisponivel + quantidade, quantidadeReservada);
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public int getQuantidadeReservada() {
        return quantidadeReservada;
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ItemEstoque other = (ItemEstoque) obj;
        return itemId.equals(other.itemId);
    }
}
